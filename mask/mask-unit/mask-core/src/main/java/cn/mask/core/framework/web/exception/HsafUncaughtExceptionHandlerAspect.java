package cn.mask.core.framework.web.exception;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

public class HsafUncaughtExceptionHandlerAspect {
    @Autowired
    private HsafUncaughtExceptionHandler hsafUncaughtExceptionHandler;

    public HsafUncaughtExceptionHandlerAspect() {
    }

    public void doBefore(JoinPoint jp) {
        Thread.setDefaultUncaughtExceptionHandler(this.hsafUncaughtExceptionHandler);
    }
}
