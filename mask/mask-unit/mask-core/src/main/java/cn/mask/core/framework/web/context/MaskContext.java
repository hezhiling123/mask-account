package cn.mask.core.framework.web.context;

import cn.mask.core.framework.utils.CurrentUser;

import java.io.Serializable;
import java.util.Map;

public interface MaskContext extends Serializable {
    String MASK_CONTEXT_KEY = "MaskContext";

    CurrentUser getCurrentUser();

    void setCurrentUser(CurrentUser var1);

    void removeCurrentUser();

    Object getProperty(Object var1);

    void addProperty(Object var1, Object var2);

    void removeProperty(Object var1);

    Map<Object, Object> getProperties();

    void setProperties(Map<Object, Object> var1);

    void removeAllProperties();
}
