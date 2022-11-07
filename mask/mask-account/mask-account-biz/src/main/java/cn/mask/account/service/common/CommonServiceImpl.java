package cn.mask.account.service.common;

import cn.mask.account.common.fs.FastDFSClientService;
import cn.mask.account.service.BaseService;
import cn.mask.core.framework.utils.response.HttpResponseBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 图片上传
 *
 * @author Jack
 * @date 2020/9/9
 */
@RestController
public class CommonServiceImpl extends BaseService implements CommonService {

    @Resource
    private FastDFSClientService dfsClient;

    /**
     * 图片上传
     *
     * @param file
     * @return
     * @throws Exception
     * @author Jack
     * @date 2020/9/9
     * @version
     */
    @Override
    @RequestMapping(value = "/api/upload", method = RequestMethod.POST)
    public HttpResponseBody<Map<String, Object>> upload(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        String imgUrl = dfsClient.uploadFile(file);
        Map<String, Object> result = new HashMap<>();
        result.put("fileUrl", imgUrl);
        return HttpResponseBody.successResponse("上传成功", result);
    }

    /**
     * 批量上传文件
     *
     * @param request   request
     * @return
     * @throws Exception
     */
    @Override
    @RequestMapping(value = "/api/batchUpload", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponseBody<List<Map<String, String>>> handleFileUpload(HttpServletRequest request) throws Exception {
        List<Map<String, String>> urls = new ArrayList<>();
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("file");
        MultipartFile file = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                String imgUrl = dfsClient.uploadFile(file);
                String fileName = file.getOriginalFilename();
                String fileIndex = i + "";
                Map<String, String> img = new HashMap<>();
                img.put("imgUrl", imgUrl);
                img.put("dfsPath", dfsClient.getDfsPath());
                img.put("fileName", fileName);
                img.put("fileIndex", fileIndex);
                urls.add(img);
            }
        }
        return HttpResponseBody.successResponse("上传成功", urls);
    }

    @Override
    @RequestMapping(value = "/404.do", method = {RequestMethod.GET, RequestMethod.POST})
    public Object error404() {
        return "大写的404";
    }

    @Override
    @RequestMapping(value = "/500.do", method = {RequestMethod.GET, RequestMethod.POST})
    public Object error500() {
        return "大写的500";
    }

}

