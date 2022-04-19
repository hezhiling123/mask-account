package cn.mask.account.service.common;

import cn.mask.core.utils.response.HttpResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2022-04-01 17:14:36
 */
public interface CommonService {
    HttpResponseBody<Map<String, Object>> upload(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception;
    HttpResponseBody<List<Map<String, String>>> handleFileUpload(HttpServletRequest request) throws Exception;
    Object error404();
    Object error500();
}
