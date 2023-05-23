package cn.mask.mask.product.biz.common.exception;

import cn.mask.mask.common.core.framework.web.WrapperResponse;
import cn.mask.mask.common.core.framework.web.enums.ResultCode;
import cn.mask.mask.common.core.framework.web.exception.MaskException;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.rmi.AccessException;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2022-04-12 22:22:49
 */
@ControllerAdvice
@Order(1000)
@ResponseBody
public class UserHandlerExceptionResolver {

    /**
     * 异常
     *
     * @param e {@link Exception}
     * @return {@link WrapperResponse}
     */
    @ExceptionHandler(Exception.class)
    private WrapperResponse<Object> handleException(Exception e) {
        return WrapperResponse.error(ResultCode.SYS_ERR, e.getMessage(), null);
    }

    /**
     * 权限异常
     *
     * @param e {@link AccessException}
     * @return {@link WrapperResponse}
     */
    @ExceptionHandler(AccessException.class)
    private WrapperResponse<Object> handleAccessException(AccessException e) {
        return WrapperResponse.error(ResultCode.ACCESS_PERMISSION_EXCEPTION, e.getMessage(), null);
    }

    /**
     * mask异常类
     *
     * @param e {@link MaskException}
     * @return {@link WrapperResponse}
     */
    @ExceptionHandler(MaskException.class)
    private WrapperResponse<Object> handleMaskException(MaskException e) {
        return WrapperResponse.error(e.getCode(), e.getMsg(), null);
    }

    /**
     * 非法参数异常
     *
     * @param e {@link IllegalArgumentException}
     * @return {@link WrapperResponse}
     */
    @ExceptionHandler(IllegalArgumentException.class)
    private WrapperResponse<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        return WrapperResponse.error(ResultCode.REQUEST_PARAMETER_ERR, e.getMessage(), null);
    }

    /**
     * 参数校验异常
     *
     * @param e {@link MethodArgumentNotValidException}
     * @return {@link WrapperResponse}
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private WrapperResponse<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return WrapperResponse.error(ResultCode.INVALID_METHOD_ARGUMENT, e.getMessage(), null);
    }

    /**
     * validation Exception (以form-data形式传参)
     *
     * @param e {@link BindException}
     * @return {@link WrapperResponse}
     */
    @ExceptionHandler(BindException.class)
    private WrapperResponse<Object> handleBindException(BindException e) {
        return WrapperResponse.error(ResultCode.BIND_EXCEPTION, e.getMessage(), null);
    }
}
