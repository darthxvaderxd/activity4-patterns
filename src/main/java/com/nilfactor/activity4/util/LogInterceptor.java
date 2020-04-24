package com.nilfactor.activity4.util;

import javax.interceptor.*;
import org.apache.log4j.Logger;

@Interceptor
public class LogInterceptor {
    @AroundConstruct
    public Object onConstruct(InvocationContext context) {
    	String className = context.getClass().getName();
    	Logger logger = Logger.getLogger(context.getClass().getName());
    	
    	System.out.println(className + " created");
		try {
			logger.debug(className + " created");
			return context.proceed();
		} catch (Exception error) {
			logger.error(className + " had the following error" + error.getMessage());
		}
		return null;
    }
    
	@AroundInvoke
	public Object onMethodCall(InvocationContext context) {
		String targetClassName = context.getTarget().getClass().getSimpleName();
		String className = context.getClass().getSimpleName();
        String methodName = context.getMethod().getName();
		Logger logger = Logger.getLogger(context.getClass().getName());
		Object[] params = (Object[]) context.getParameters();
		
		if (targetClassName != null) { // override the proxy name (Injected classes)
			className = targetClassName.replace("$Proxy$_$$_WeldSubclass", "");
		}
		
		System.out.println(className + "." + methodName + " with " + params.length + " params was called");
        try {
        	logger.debug(className + "." + methodName + " with " + params.length + " params was called");
        	return context.proceed();
        } catch (Exception error) {
        	logger.error(className + "." + methodName + " had the following error" + error.getMessage());
        }
        return null;
	}
}
