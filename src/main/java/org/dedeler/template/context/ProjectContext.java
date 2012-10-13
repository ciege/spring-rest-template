package org.dedeler.template.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author destan
 */
public class ProjectContext {

	private static final ApplicationContext APPLICATION_CONTEXT;

	static {
		APPLICATION_CONTEXT = new ClassPathXmlApplicationContext("classpath:/spring/springBeans.xml");
	}

	public static ApplicationContext getApplicationContext() {
		return APPLICATION_CONTEXT;
	}

	public static MessageSource getMessageSource() {
		return APPLICATION_CONTEXT.getBean("messageSource", MessageSource.class);
	}
}
