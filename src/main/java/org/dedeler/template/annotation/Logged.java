package org.dedeler.template.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.dedeler.template.service.LoggingService.LogLevel;
import org.dedeler.template.service.LoggingService.LogType;

/**
 * Enables public method logging to satisfy enterprise bros
 * 
 * @author destan
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Logged {

	LogLevel level() default LogLevel.DEBUG;

	LogType type();
}