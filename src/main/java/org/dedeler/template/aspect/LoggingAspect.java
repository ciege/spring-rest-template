package org.dedeler.template.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.dedeler.template.annotation.Logged;
import org.dedeler.template.service.LoggingService;
import org.dedeler.template.service.LoggingService.LogLevel;
import org.dedeler.template.service.LoggingService.LogType;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class LoggingAspect {

	@Autowired
	private LoggingService loggingService;

	@Pointcut("within(@org.dedeler.template.annotation.Logged * && !org.dedeler.template.service.LoggingService )")
	public void loggedAnnotatedClasses() {}

	@Around(value = "loggedAnnotatedClasses()")
	public Object loggedClasses(ProceedingJoinPoint joinPoint) throws Throwable {
		return around(joinPoint);
	}

	private Object around(ProceedingJoinPoint joinPoint) throws Throwable {

		Logged logged = joinPoint.getTarget().getClass().getAnnotation(Logged.class);

		final long endTime;
		long startTime = 0;

		before(joinPoint, logged.type(), logged.level());
		Object returnValue = null;
		startTime = System.nanoTime();
		returnValue = joinPoint.proceed();
		endTime = System.nanoTime();
		final long duration = (endTime - startTime) / 1000000;
		after(joinPoint, returnValue, duration, logged.type(), logged.level());
		return returnValue;
	}

	private void before(JoinPoint joinPoint, LogType type, LogLevel level) {

		final String fullyQualifiedMethodName = joinPoint.getSignature().getDeclaringType().getSimpleName() + "#"
				+ joinPoint.getSignature().getName();

		if (joinPoint.getArgs() == null || joinPoint.getArgs().length == 0) {
			loggingService.log(type, level, "Entering " + fullyQualifiedMethodName);
		}
		else {
			loggingService.log(type, level, "\nEntering " + fullyQualifiedMethodName + "\nArguments: [ " + extractArguments(joinPoint.getArgs())
					+ " ]\n");
		}
	}

	private void after(JoinPoint joinPoint, Object returnValue, long duration, LogType type, LogLevel level) {

		String returnValueString = returnValue != null ? returnValue.toString() : "";
		final String fullyQualifiedMethodName = joinPoint.getSignature().getDeclaringType().getSimpleName() + "#"
				+ joinPoint.getSignature().getName();
		if (joinPoint.getSignature() instanceof MethodSignature) {
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			Class<?> returnType = signature.getReturnType();
			if (returnType.getName().compareTo("void") == 0) {
				returnValueString = "void";
			}
		}

		loggingService.log(type, level, "\nReturning " + fullyQualifiedMethodName + "\nExecution time:  " + duration + "ms\nReturn value: "
				+ returnValueString.toString() + "\n");
	}

	private String extractArguments(Object[] args) {
		if (args == null || args.length == 0) {
			return "";
		}

		StringBuffer buffer = new StringBuffer();
		for (Object object : args) {
			if(object !=null){
				buffer.append(" " + object.getClass().getCanonicalName() + ":" + object.toString() + ", ");				
			}
		}

		return buffer.toString();
	}
}
