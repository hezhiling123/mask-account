package cn.mask.core.framework.web.context;

import cn.mask.core.framework.web.context.impl.MaskContextImpl;

public class MaskContextHolder {
    private static final ThreadLocal<MaskContext> contextHolder = new ThreadLocal<>();

    public MaskContextHolder() {
    }

    public static void setContext(MaskContext maskContext) {
        contextHolder.set(maskContext);
    }

    public static MaskContext getContext() {
        MaskContext obj = contextHolder.get();
        if (obj == null) {
            obj = new MaskContextImpl();
            setContext(obj);
        }

        return obj;
    }
}
