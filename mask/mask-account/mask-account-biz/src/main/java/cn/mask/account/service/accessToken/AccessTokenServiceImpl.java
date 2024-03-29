package cn.mask.account.service.accessToken;

import cn.mask.account.common.constants.RedisKey;
import cn.mask.account.common.utils.ShiroCacheUtil;
import cn.mask.account.service.BaseService;
import cn.mask.core.framework.web.WrapperResponse;
import cn.mask.mask.user.api.authorize.service.AuthorizeService;
import cn.mask.mask.user.api.login.dto.UserInfo;
import cn.mask.mask.user.api.user.service.UserService;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.common.utils.JSONUtils;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AccessToken管理
 *
 * @author Jack
 */
@RestController
@RequestMapping("/api")
public class AccessTokenServiceImpl extends BaseService implements AccessTokenService {

    @Autowired
    private AuthorizeService authorizeService;
    @Autowired
    private UserService userService;
    @Resource
    private ShiroCacheUtil shiroCacheUtil;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    @RequestMapping("/accessToken")
    public HttpEntity<Object> token(HttpServletRequest request) throws OAuthSystemException {
        try {
            // 构建Oauth请求
            OAuthTokenRequest oAuthTokenRequest = new OAuthTokenRequest(request);

            //检查提交的客户端id是否正确
            if (!authorizeService.checkClientId(oAuthTokenRequest.getClientId())) {
                OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                        .setErrorDescription("客户端验证失败，如错误的client_id/client_secret")
                        .buildJSONMessage();
                return new ResponseEntity<>(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
            }

            // 检查客户端安全Key是否正确
            if (!authorizeService.checkClientSecret(oAuthTokenRequest.getClientSecret())) {
                OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                        .setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT)
                        .setErrorDescription("客户端验证失败，如错误的client_id/client_secret")
                        .buildJSONMessage();
                return new ResponseEntity<>(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
            }

            String authCode = oAuthTokenRequest.getParam(OAuth.OAUTH_CODE);

            // 检查验证类型，此处只检查AUTHORIZATION类型，其他的还有PASSWORD或者REFRESH_TOKEN
            if (oAuthTokenRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.AUTHORIZATION_CODE.toString())) {
                if (!shiroCacheUtil.checkAuthCode(authCode)) {
                    OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                            .setError(OAuthError.TokenResponse.INVALID_GRANT)
                            .setErrorDescription("error grant code")
                            .buildJSONMessage();
                    return new ResponseEntity<>(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
                }
            }

            //生成Access Token
            OAuthIssuer issuer = new OAuthIssuerImpl(new MD5Generator());
            final String accessToken = issuer.accessToken();
            shiroCacheUtil.addAccessToken(accessToken, shiroCacheUtil.getUsernameByAuthCode(authCode));
            logger.info("accessToken generated : {}", accessToken);

            //需要保存clientSessionId和clientId的关系到redis，便于在Logout时通知系统logout
            redisTemplate.opsForHash().put(RedisKey.CLIENT_SESSIONS, request.getParameter("sid"),
                    oAuthTokenRequest.getClientId());

            // 生成OAuth响应
            OAuthResponse response = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
                    .setAccessToken(accessToken).setExpiresIn(String.valueOf(authorizeService.getExpireIn()))
                    .buildJSONMessage();

            return new ResponseEntity<>(JSONUtils.parseJSON(response.getBody()), HttpStatus.valueOf(response.getResponseStatus()));
        } catch (OAuthProblemException e) {
            e.printStackTrace();
            OAuthResponse res = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e).buildBodyMessage();
            return new ResponseEntity<>(res.getBody(), HttpStatus.valueOf(res.getResponseStatus()));
        }
    }

    @Override
    @RequestMapping("/userInfo")
    public ResponseEntity<Object> userInfo(HttpServletRequest request) throws OAuthSystemException {
        try {

            //构建OAuth资源请求
            OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
            //获取Access Token
            String accessToken = oauthRequest.getAccessToken();

            //验证Access Token
            if (!shiroCacheUtil.checkAccessToken(accessToken)) {
                // 如果不存在/过期了，返回未验证错误，需重新验证
                OAuthResponse oauthResponse = OAuthRSResponse
                        .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                        .setRealm("fxb")
                        .setError(OAuthError.ResourceResponse.INVALID_TOKEN)
                        .buildHeaderMessage();

                HttpHeaders headers = new HttpHeaders();
                headers.add(OAuth.HeaderType.WWW_AUTHENTICATE, oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
                return new ResponseEntity<>(headers, HttpStatus.UNAUTHORIZED);
            }
            //返回用户名
            String username = shiroCacheUtil.getUsernameByAccessToken(accessToken);
            UserInfo user = userService.getUserByUserId(username);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (OAuthProblemException e) {
            //检查是否设置了错误码
            String errorCode = e.getError();
            if (OAuthUtils.isEmpty(errorCode)) {
                OAuthResponse oauthResponse = OAuthRSResponse
                        .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                        .setRealm("fxb")
                        .buildHeaderMessage();

                HttpHeaders headers = new HttpHeaders();
                headers.add(OAuth.HeaderType.WWW_AUTHENTICATE, oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
                return new ResponseEntity<>(headers, HttpStatus.UNAUTHORIZED);
            }

            OAuthResponse oauthResponse = OAuthRSResponse
                    .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                    .setRealm("fxb")
                    .setError(e.getError())
                    .setErrorDescription(e.getDescription())
                    .setErrorUri(e.getUri())
                    .buildHeaderMessage();

            HttpHeaders headers = new HttpHeaders();
            headers.add(OAuth.HeaderType.WWW_AUTHENTICATE, oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @RequestMapping("logout")
    public HttpEntity<Object> logout(HttpServletRequest request) {
        shiroCacheUtil.removeUser(this.getSessionUser().getId());
        SecurityUtils.getSubject().logout();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
