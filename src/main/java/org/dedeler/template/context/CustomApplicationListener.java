package org.dedeler.template.context;

import java.util.Arrays;

import org.dedeler.template.model.Role;
import org.dedeler.template.model.User;
import org.dedeler.template.service.RoleService;
import org.dedeler.template.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class CustomApplicationListener implements ApplicationListener<ApplicationEvent> {

	private boolean initialized = false;

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (!initialized) {
			if (event instanceof ContextRefreshedEvent) {
				try {
					System.out.println("=====================================================");
					// Thanks: http://stackoverflow.com/a/8687676/878361
					ApplicationContext applicationContext = ((ContextRefreshedEvent) event).getApplicationContext();

					RoleService roleService = applicationContext.getBean(RoleService.class);
					UserService userService = applicationContext.getBean(UserService.class);
					
					Role role = new Role("ROLE_USER");

					User user = new User();
					user.setUsername("admin");
					user.setPassword("7lLEodyoRSvB9W6Rhjc+xfabU0ITmcdbjaW4MfARG5TOb/N7TeMxDB85j/HSm8t1h6pTrATIXySR+yQ5jMo39Q==");// admin
					user.setFirstName("Destan");
					user.setAuthorities(Arrays.asList(role));

					roleService.save(role);
					userService.save(user);

					initialized = true;
					System.out.println("----------------------------------------------------");
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

}
