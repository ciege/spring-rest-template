package org.dedeler.template.context;

/**
 * @author destan
 */
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {
	private static ApplicationContext context;

	public static ApplicationContext getContext() {
		if (context != null) {
			return context;
		}
		else {
			throw new IllegalStateException("The Spring application context is not yet available. "
					+ "The call to this method has been performed before the application context " + "provider was initialized");
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (context == null) {
			ApplicationContextProvider.context = applicationContext;
		}
		else {
			throw new IllegalStateException("The application context provider was already initialized. "
					+ "It is illegal to place try to initialize the context provider twice or create "
					+ "two different beans of this type (even if the contexts are different)");
		}
	}

}