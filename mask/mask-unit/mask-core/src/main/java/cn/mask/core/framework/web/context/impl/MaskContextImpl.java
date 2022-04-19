package cn.mask.core.framework.web.context.impl;

import cn.mask.core.framework.utils.CurrentUser;
import cn.mask.core.framework.web.context.MaskContext;

import java.util.HashMap;
import java.util.Map;

public class MaskContextImpl implements MaskContext {
    private static final long serialVersionUID = 8383356012441014698L;
    private CurrentUser currentUser = new CurrentUser();
    private Map<Object, Object> properties = new HashMap<>();

    public MaskContextImpl() {
    }

    @Override
    public CurrentUser getCurrentUser() {
        return this.currentUser;
    }

    @Override
    public void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public void removeCurrentUser() {
        this.currentUser = null;
    }

    @Override
    public Object getProperty(Object obj) {
        return this.properties.get(obj);
    }

    @Override
    public void addProperty(Object obj, Object obj1) {
        this.properties.put(obj, obj1);
    }

    @Override
    public void removeProperty(Object obj) {
        this.properties.remove(obj);
    }

    @Override
    public Map<Object, Object> getProperties() {
        return this.properties;
    }

    @Override
    public void setProperties(Map map) {
        this.properties = map;
    }

    @Override
    public void removeAllProperties() {
        this.properties.clear();
    }
}
