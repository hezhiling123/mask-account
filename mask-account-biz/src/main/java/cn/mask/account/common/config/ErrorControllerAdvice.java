package cn.mask.account.common.config;

import cn.mask.core.exception.BusinessException;
import cn.mask.core.utils.response.HttpResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chenlin
 * @date 2017/4/10
 */
@ControllerAdvice
public class ErrorControllerAdvice {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object errorHandle(Exception e) {
        String errMsg = e.getMessage();
        if (e instanceof BusinessException || (e instanceof RuntimeException && errMsg.contains("MaskRuntimeException"))) {
            logger.error(errMsg);
        } else {
            logger.error("", e);
        }
        return HttpResponseBody.failResponse(errMsg);
    }


}
