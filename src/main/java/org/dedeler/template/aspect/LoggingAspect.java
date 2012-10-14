package org.dedeler.template.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.dedeler.template.service.LoggingService;
import org.dedeler.template.service.LoggingService.LogLevel;
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

		final long endTime;
		long startTime = 0;

		before(joinPoint, LogLevel.DEBUG);
		Object returnValue = null;
		startTime = System.nanoTime();
		returnValue = joinPoint.proceed();
		endTime = System.nanoTime();
		final long duration = (endTime - startTime) / 1000000;
		after(joinPoint, returnValue, duration, LogLevel.DEBUG);
		return returnValue;
	}

	private void before(JoinPoint joinPoint, LogLevel level) {

		// if (joinPoint.getSignature().getDeclaringType().equals(LoggingService.class)) {
		// return;
		// }

		final String fullyQualifiedMethodName = joinPoint.getSignature().getDeclaringType() + "#" + joinPoint.getSignature().getName();

		if (joinPoint.getArgs() == null || joinPoint.getArgs().length == 0) {
			loggingService.log(level, "Entering " + fullyQualifiedMethodName);
		}
		else {
			loggingService.log(level, "Entering " + fullyQualifiedMethodName + " with arguments: [ " + extractArguments(joinPoint.getArgs()) + " ]");
		}
	}

	private void after(JoinPoint joinPoint, Object returnValue, long duration, LogLevel level) {

		// if (joinPoint.getSignature().getDeclaringType().equals(LoggingService.class)) {
		// return;
		// }

		String returnValueString = returnValue != null ? returnValue.toString() : "";
		final String fullyQualifiedMethodName = joinPoint.getSignature().getDeclaringType() + "#" + joinPoint.getSignature().getName();
		if (joinPoint.getSignature() instanceof MethodSignature) {
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			Class<?> returnType = signature.getReturnType();
			if (returnType.getName().compareTo("void") == 0) {
				returnValueString = "void";
			}
		}

		loggingService.log(level, "Returning " + fullyQualifiedMethodName + " in " + duration + "ms with return value: " + returnValueString.toString());
	}

	private String extractArguments(Object[] args) {
		if (args == null || args.length == 0) {
			return "";
		}

		StringBuffer buffer = new StringBuffer();
		for (Object object : args) {
			buffer.append(" " + object.getClass().getCanonicalName() + ":" + object.toString() + ", ");
		}

		return buffer.toString();
	}
}
