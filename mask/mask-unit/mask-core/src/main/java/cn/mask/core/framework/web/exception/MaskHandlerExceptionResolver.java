package cn.mask.core.framework.web.exception;

import cn.mask.core.framework.web.WrapperResponse;
import cn.mask.core.framework.web.context.MaskContextHolder;
import cn.mask.core.framework.web.exception.MaskException;
import cn.mask.core.unit.LogContext;
import cn.mask.core.unit.MaskLogHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2022-04-12 22:22:49
 */
public class MaskHandlerExceptionResolver extends AbstractHandlerExceptionResolver {
    private final Logger log = LoggerFactory.getLogger(MaskHandlerExceptionResolver.class);
    private String viewType = "json";
    private String defaultErrorView;
    private String defaultErrorMessage;
    private final Map<Integer, String> exceptionMappings = new HashMap<>();
    private final Map<String, String> userDefinedExceptions = new HashMap<>();
    private final Map<String, Integer> ude_errCode = new HashMap<>();
    private final Map<String, String> ude_errMsg = new HashMap<>();
    private final String LOG_CONTEXT_KEY = "_logContext";

    @Autowired
    ExceptionSeq exceptionSeq;
    @Autowired
    MaskLogHandler maskLogHandler;

    public MaskHandlerExceptionResolver() {
    }

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception ex) {
        ModelAndView mv;
        Integer errorCode = WrapperResponse.FAIL;
        LogContext logContext = (LogContext) MaskContextHolder.getContext().getProperty(this.LOG_CONTEXT_KEY);
        String traceID = "";
        String traceInfo = "[MaskException]";
        if (logContext != null && logContext.getTraceID() != null) {
            traceID = logContext.getTraceID();
            traceInfo = traceInfo + "[traceID:" + traceID + "]";
        }
        String exseq = this.exceptionSeq.getExceptionSeq();
        String exmsg = "";
        String ex_clz = this.getExceptionClz(ex);
        this.log.debug("ex=" + ex + ";ex.getCause()=" + ex.getCause());
        boolean ex_match = false;
        String viewName;
        if (ex.getCause() != null) {
            viewName = ex.getCause().getClass().getName();
            if (this.ude_errCode.containsKey(viewName)) {
                errorCode = (Integer) this.ude_errCode.get(viewName);
                exmsg = (String) this.ude_errMsg.get(viewName);
                this.log.error(this.maskLogHandler.getBaseLogString() + traceInfo + ",异常流水号exseq=" + exseq + ",错误码=" + errorCode + ",错误信息:" + exmsg, ex.getCause());
                ex_match = true;
            }
        }
        if (!ex_match && this.ude_errCode.containsKey(ex_clz)) {
            errorCode = (Integer) this.ude_errCode.get(ex_clz);
            exmsg = (String) this.ude_errMsg.get(ex_clz);
            this.log.error(this.maskLogHandler.getBaseLogString() + traceInfo + ",异常流水号exseq=" + exseq + ",错误码=" + errorCode + ",错误信息:" + exmsg, ex);
            ex_match = true;
        }
        if (!ex_match) {
            if (ex instanceof UndeclaredThrowableException) {
                Throwable undeclaredThrowable = ((UndeclaredThrowableException) ex).getUndeclaredThrowable();
                if (undeclaredThrowable instanceof MaskException) {
                    Integer code = ((MaskException) undeclaredThrowable).getCode();
                    exmsg = ((MaskException) undeclaredThrowable).getMsg();
                    errorCode = code;
                }
            } else {
                int code;
                if (ex instanceof MaskException) {
                    code = ((MaskException) ex).getCode();
                    exmsg = ((MaskException) ex).getMsg();
                    errorCode = code;
                    this.log.error(this.maskLogHandler.getBaseLogString() + traceInfo + ",异常流水号exseq=" + exseq + ",错误码=" + errorCode + ",错误信息:" + exmsg, ex);
                } else if (ex.getCause() == null) {
                    this.log.error(this.maskLogHandler.getBaseLogString()  + traceInfo + ",异常流水号exseq=" + exseq + ",错误码=" + errorCode + ",错误信息:" + ex.getMessage(), ex);
                } else if (ex.getCause() instanceof MaskException) {
                    code = ((MaskException)ex.getCause()).getCode();
                    exmsg = ((MaskException)ex.getCause()).getMessage();
                    errorCode = code;
                    this.log.error(this.maskLogHandler.getBaseLogString() + traceInfo + ",异常流水号exseq=" + exseq + ",错误码=" + errorCode + ",错误信息:" + exmsg, ex.getCause());
                } else {
                    this.log.error(this.maskLogHandler.getBaseLogString() +  traceInfo + ",异常流水号exseq=" + exseq + ",错误码=" + errorCode + ",错误信息:" + ex.getMessage(), ex.getCause());
                }
            }
        }
        if (this.viewType.equalsIgnoreCase("page")) {
            viewName = this.defaultErrorView;
            if (this.exceptionMappings != null && this.exceptionMappings.containsKey(errorCode)) {
                viewName = (String) this.exceptionMappings.get(errorCode);
            }
            mv = new ModelAndView();
            mv.setViewName(viewName);
        } else {
            mv = new ModelAndView(new MappingJackson2JsonView());
        }
        mv.addObject("code", errorCode);
        mv.addObject("type", WrapperResponse.ResponseType.TYPE_ERROR.getType());
        if (exmsg.length() > 0) {
            mv.addObject("message", exmsg + ",异常流水号:" + exseq);
        } else if (this.defaultErrorMessage != null) {
            mv.addObject("message", this.defaultErrorMessage + ",异常流水号:" + exseq);
        } else {
            mv.addObject("message", ex.getMessage() + ",异常流水号:" + exseq);
        }
        return mv;
    }

    private String getExceptionClz(Exception ex) {
        return ex.getClass().getName();
    }
    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public void setDefaultErrorView(String defaultErrorView) {
        this.defaultErrorView = defaultErrorView;
    }

    public void setExceptionMappings(Map<Integer, String> exceptionMappings) {
        this.exceptionMappings.putAll(exceptionMappings);
    }

    public void addExceptionMappings(Integer errorCode, String errorView) {
        this.exceptionMappings.put(errorCode, errorView);
    }

    public void setUserDefinedExceptions(Map<String, String> userDefinedExceptions) {
        this.userDefinedExceptions.putAll(userDefinedExceptions);
        if (!userDefinedExceptions.isEmpty()) {
            for (String k : userDefinedExceptions.keySet()) {
                String v = (String) userDefinedExceptions.get(k);
                if (!v.contains("|")) {
                    this.log.error("无效的自定义异常拦截配置，ex_clz=" + k + ";ex_msg=" + v);
                } else {
                    this.ude_errCode.put(k, Integer.parseInt(v.substring(0, v.indexOf("|"))));
                    this.ude_errMsg.put(k, v.substring(v.indexOf("|") + 1));
                }
            }
        }

    }

    public void addUserDefinedExceptions(String ex_clz, String ex_msg) {
        this.userDefinedExceptions.put(ex_clz, ex_msg);
        if (!ex_msg.contains("|")) {
            this.log.error("无效的自定义异常拦截配置，ex_clz=" + ex_clz + ";ex_msg=" + ex_msg);
        } else {
            this.ude_errCode.put(ex_clz, Integer.parseInt(ex_msg.substring(0, ex_msg.indexOf("|"))));
            this.ude_errMsg.put(ex_clz, ex_msg.substring(ex_msg.indexOf("|") + 1));
        }

    }

    public void setDefaultErrorMessage(String defaultErrorMessage) {
        this.defaultErrorMessage = defaultErrorMessage;
    }
}
