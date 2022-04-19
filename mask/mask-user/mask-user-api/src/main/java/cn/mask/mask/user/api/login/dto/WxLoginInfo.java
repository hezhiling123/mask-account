package cn.mask.mask.user.api.login.dto;

import lombok.Data;

/**
 * @author hezhiling
 */
@Data
public class WxLoginInfo {
    private String code;
    private UserInfo userInfo;
}
