package cn.mask.core.unit;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.UUID;

import cn.mask.core.bizcode.MaskBizCodeMapper;
import cn.mask.core.framework.utils.CurrentUser;
import cn.mask.core.framework.web.WrapperResponse;
import cn.mask.core.framework.web.context.MaskContextHolder;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class MaskLogHandler {
    private static final Logger logger = LoggerFactory.getLogger(MaskLogHandler.class);
    private static final String CALL_FROM_Mask_REST_PATH_CONTROLLER = "MaskRestPathController";
    private static final String CALL_FROM_CONTROLLER = "controller";
    private static final String CALL_FROM_RPC = "rpc";
    private static final String LOG_CONTEXT_KEY = "_logContext";
    private static final String MULTIPART_FORM_DATA = "multipart/form-data";
    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${areaCode}")
    private String areaCode;
    @Value("${mask.trace.defaultBizCode}")
    private String defaultBizCode;
    @Value("${log_collection_parameter:true}")
    private boolean log_collection_parameter;
    @Autowired
    MaskBizCodeMapper maskBizCodeMapper;
    private static final ThreadLocal<LogInfo> logContextHolder = new ThreadLocal<>();

    public MaskLogHandler() {
    }

    private static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    private String generateTraceID() {
        UUID uuid = UUID.randomUUID();
        if (this.applicationName != null && this.applicationName.length() > 10) {
            this.applicationName = this.applicationName.substring(0, 10);
        }

        return this.applicationName + "-" + uuid;
    }

    private void packTraceInfo(LogContext logContext, String url, String ClassName) {
        logContext.setAreaCode(this.areaCode);
        logContext.setBizCode(this.maskBizCodeMapper.transBizCode(url) == null ? this.defaultBizCode : this.maskBizCodeMapper.transBizCode(url));
        logContext.setAppSign(ClassName == null ? "L" : (ClassName.startsWith("L_") ? "L" : "N"));
    }

    private void packHttpLogInfo(LogInfo logInfo, JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            logInfo.setRequestUrl(request.getRequestURL().toString());
            logInfo.setRequestUri(request.getRequestURI());
            logInfo.setClientIP(getIpAddr(request));
            logInfo.setRequestMethod(request.getMethod());
            logInfo.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            logInfo.setClazz(joinPoint.getSignature().getDeclaringType().getSimpleName());
            if (this.log_collection_parameter && joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
                String requestParams = "";
                logger.debug("request.getMethod()=" + request.getMethod() + ";request.getContentType()=" + request.getContentType());
                if ("GET".equalsIgnoreCase(request.getMethod())) {
                    requestParams = Arrays.toString(joinPoint.getArgs());
                } else {
                    try {
                        if (request.getContentType() == null || !request.getContentType().contains("multipart/form-data")) {
                            requestParams = JSONObject.toJSONString(joinPoint.getArgs()[0]);
                        }
                    } catch (Exception var7) {
                        requestParams = "";
                    }
                }

                logInfo.setRequestParams(requestParams);
            }

        }
    }

    private void packRpcLogInfo(LogInfo logInfo, JoinPoint joinPoint) {
        logInfo.setRequestUrl("");
        logInfo.setClientIP("");
        logInfo.setRequestMethod("rpc");
        logInfo.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logInfo.setClazz(joinPoint.getSignature().getDeclaringType().getSimpleName());
        if (this.log_collection_parameter && joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
            String requestParams = Arrays.toString(joinPoint.getArgs());
            logInfo.setRequestParams(requestParams);
        }

    }

    private LogContext generateLogContext(LogInfo logInfo) {
        LogContext logContext = new LogContext();
        logContext.setTraceID(logInfo.getTraceID());
        boolean hasLogContext = false;
        if (MaskContextHolder.getContext().getProperties().containsKey("_logContext")) {
            LogContext pre_logContext = (LogContext)MaskContextHolder.getContext().getProperty("_logContext");
            if (pre_logContext != null && pre_logContext.getBizCode() != null) {
                logContext.setAreaCode(pre_logContext.getAreaCode());
                logContext.setBizCode(pre_logContext.getBizCode());
                logContext.setAppSign(pre_logContext.getAppSign());
                hasLogContext = true;
            }
        }

        if (!hasLogContext) {
            this.packTraceInfo(logContext, logInfo.getRequestUri(), logInfo.getClazz());
        }

        return logContext;
    }

    private void doBefore(LogInfo logInfo) {
        logger.debug("====[request data]====");
        logger.debug(logInfo.toString());
        logger.debug(logInfo.getLogString());
        logContextHolder.set(logInfo);
        MaskContextHolder.getContext().addProperty("_logContext", this.generateLogContext(logInfo));
    }

    public void doControllerBefore(JoinPoint joinPoint) {
        logger.debug("MaskLogHandler.doControllerBefore");

        try {
            LogInfo logInfo = new LogInfo();
            LogContext logContext = (LogContext)MaskContextHolder.getContext().getProperty("_logContext");
            if (logContext == null) {
                logInfo.setTraceID(this.generateTraceID());
            } else {
                logInfo.setTraceID(logContext.getTraceID());
            }

            MDC.put("traceID", logInfo.getTraceID());
            CurrentUser curUser = MaskContextHolder.getContext().getCurrentUser();
            logInfo.setUserAcctId(curUser.getUserAcctID());
            logInfo.setRequestTime((new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(Calendar.getInstance().getTime()));
            this.packHttpLogInfo(logInfo, joinPoint);
            logInfo.setCallFrom("controller");
            this.doBefore(logInfo);
        } catch (Exception var5) {
            logger.error("MaskLogHandler.doControllerBefore.exception", var5);
        }

    }

    private LogInfo getLogInfo4Service() {
        LogInfo logInfo = (LogInfo)logContextHolder.get();
        if (logInfo != null) {
            return logInfo;
        } else {
            LogContext logContext = (LogContext)MaskContextHolder.getContext().getProperty("_logContext");
            if (logContext != null) {
                logInfo = new LogInfo();
                logInfo.setTraceID(logContext.getTraceID());
                ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
                if (attributes != null) {
                    logInfo.setCallFrom("MaskRestPathController");
                } else {
                    logInfo.setCallFrom("rpc");
                }
            }

            if (logInfo == null) {
                logInfo = new LogInfo();
                logInfo.setTraceID(this.generateTraceID());
                logInfo.setCallFrom("MaskRestPathController");
            }

            return logInfo;
        }
    }

    public void doServiceBefore(JoinPoint joinPoint) {
        logger.debug("MaskLogHandler.doServiceBefore");

        try {
            LogInfo logInfo = this.getLogInfo4Service();
            String callFrom = logInfo.getCallFrom();
            logger.debug("callFrom:" + callFrom);
            if ("controller".equals(callFrom)) {
                return;
            }

            if ("rpc".equals(callFrom)) {
                MDC.put("traceID", logInfo.getTraceID());
                logInfo.setRequestTime((new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(Calendar.getInstance().getTime()));
                this.packRpcLogInfo(logInfo, joinPoint);
            } else if ("MaskRestPathController".equals(callFrom)) {
                MDC.put("traceID", logInfo.getTraceID());
                logInfo.setRequestTime((new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(Calendar.getInstance().getTime()));
                this.packHttpLogInfo(logInfo, joinPoint);
            }

            CurrentUser curUser = MaskContextHolder.getContext().getCurrentUser();
            logInfo.setUserAcctId(curUser.getUserAcctID());
            this.doBefore(logInfo);
        } catch (Exception var5) {
            logger.error("MaskLogHandler.doServiceBefore.exception", var5);
        }

    }

    private static void doAfterReturning(Object ret, LogInfo logInfo) {
        if (ret != null && ret.getClass().isAssignableFrom(WrapperResponse.class)) {
            logInfo.setCode(((WrapperResponse)ret).getCode());
            logger.debug(JSONObject.toJSONString(ret));
        }

        logger.debug("====[response data]====");
        logger.info(logInfo.getLogString());
        MDC.remove("traceID");
    }

    public void doControllerAfterReturning(Object ret) {
        logger.debug("MaskLogHandler.doControllerAfterReturning");

        try {
            LogInfo logInfo = (LogInfo)logContextHolder.get();
            if (logInfo == null) {
                return;
            }

            doAfterReturning(ret, logInfo);
        } catch (Exception var3) {
            logger.error("MaskLogHandler.doControllerAfterReturning.exception", var3);
        }

    }

    public void doServiceAfterReturning(Object ret) {
        logger.debug("MaskLogHandler.doServiceAfterReturning");

        try {
            LogInfo logInfo = (LogInfo)logContextHolder.get();
            if (logInfo == null) {
                return;
            }

            if ("controller".equals(logInfo.getCallFrom())) {
                return;
            }

            doAfterReturning(ret, logInfo);
        } catch (Exception var3) {
            logger.error("MaskLogHandler.doServiceAfterReturning.exception", var3);
        }

    }

    private static void doAround(long startTime, LogInfo logInfo) throws Throwable {
        long timeConsuming = System.currentTimeMillis() - startTime;
        logger.debug("[timeConsuming:] " + timeConsuming);
        logInfo.setTimeConsuming(timeConsuming);
    }

    public Object doControllerAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug("MaskLogHandler.doControllerAround");
        long startTime = System.currentTimeMillis();
        Object object = joinPoint.proceed();

        try {
            LogInfo logInfo = logContextHolder.get();
            if (logInfo == null) {
                return object;
            }

            doAround(startTime, logInfo);
        } catch (Exception var6) {
            logger.error("MaskLogHandler.doControllerAround.exception", var6);
        }

        return object;
    }

    public Object doServiceAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug("MaskLogHandler.doServiceAround");
        long startTime = System.currentTimeMillis();
        Object object = joinPoint.proceed();

        try {
            LogInfo logInfo = logContextHolder.get();
            if (logInfo == null) {
                return object;
            }

            if ("controller".equals(logInfo.getCallFrom())) {
                return object;
            }

            doAround(startTime, logInfo);
        } catch (Exception var6) {
            logger.error("MaskLogHandler.doServiceAround.exception", var6);
        }

        return object;
    }

    public String getBaseLogString() {
        try {
            LogInfo logInfo = (LogInfo)logContextHolder.get();
            return logInfo == null ? null : logInfo.getBaseLogString();
        } catch (Exception var2) {
            logger.error("MaskLogHandler.getBaseLogString.exception", var2);
            return null;
        }
    }
}
