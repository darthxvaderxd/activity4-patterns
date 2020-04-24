package com.nilfactor.activity4.util;

import javax.interceptor.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

@Interceptor
public class LogInterceptor {
    @AroundConstruct
    public Object onConstruct(InvocationContext context) {
    	String className = context.getClass().getName();
    	Logger logger = Logger.getLogger(context.getClass().getName());
    	Object result = null;
    	
		try {
			logger.debug(className + " created");
			result = context.proceed();
		} catch (Exception error) {
			logger.error(className + " had the following error" + error.getMessage());
		}
		return result;
    }
    
	@AroundInvoke
	public Object onMethodCall(InvocationContext context) {
		String targetClassName = context.getTarget().getClass().getSimpleName();
		String className = context.getClass().getSimpleName();
        String methodName = context.getMethod().getName();
		Logger logger = Logger.getLogger(context.getClass().getName());
		Object[] params = (Object[]) context.getParameters();
		Object result = null;
		
		if (targetClassName != null) { // override the proxy name (Injected classes)
			className = targetClassName.replace("$Proxy$_$$_WeldSubclass", "");
		}
		
		logger.debug(className + "." + methodName + " call has started");
        try {
        	logger.debug(className + "." + methodName + " with " + params.length + " params was called");
        	result = context.proceed();
        } catch (Exception error) {
        	logger.error(className + "." + methodName + " had the following error" + error.getMessage());
        }
        
        logger.debug(className + "." + methodName + " call has finished");
        return result;
	}
}
