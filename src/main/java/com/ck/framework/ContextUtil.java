package com.ck.framework;

import com.ck.framework.context.AppContext;



public class ContextUtil {
    private final static ThreadLocal<AppContext> contextLocal = new ThreadLocal<>();

    public static AppContext getAppContext(){
        AppContext context = contextLocal.get();
        if(context == null){
            context = new AppContext();
            contextLocal.set(context);
        }
        return context;
    }
    public static void cleanContext(){
        AppContext context = contextLocal.get();
        if(context != null){
            context.clear();
        }
        contextLocal.remove();
    }
}
