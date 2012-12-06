package org.dedeler.template.aspect;

import java.lang.annotation.Annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.dedeler.template.annotation.PartialUpdateTarget;

@Aspect
public class PartialFieldAspect {

	@Pointcut("execution(* org.dedeler.template.controller.*.update*(..,@org.dedeler.template.annotation.PartialUpdateTarget (*),..))")
	public void updateMethods(){}
	
	@Around("updateMethods()")
	public Object updateMethods2(ProceedingJoinPoint joinPoint){
		return around(joinPoint);
	}

	private Object around(ProceedingJoinPoint joinPoint){
		before(joinPoint);
		return null;
	}
	
	private void before(JoinPoint joinPoint){
		Object[] args = joinPoint.getArgs();
		Integer a = args.length;
		System.out.println("#####################################XXX#########"+a.toString());
	}
	
	private void asd(JoinPoint joinPoint){
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		String methodName = signature.getMethod().getName();
		Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
		try {
			Annotation[][] parameterAnnotations = joinPoint.getTarget().getClass().getMethod(methodName,parameterTypes).getParameterAnnotations();
//			for (Annotation[] annotations : parameterAnnotations) {
//				for (Annotation annotation : annotations) {
//					if(annotation.annotationType().equals(PartialUpdateTarget.class)){
//						
//					}
//				}
//			}
		}
		catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
