package cz.muni.fi.pa165.sportsClub;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
@Import(ServiceApplicationContext.class)
// we should specify this more latter when we know the scope
@ComponentScan(basePackages = { "cz.muni.fi.pa165.sportsClub" })
public class RootWebContext extends WebMvcConfigurerAdapter {

	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}
}


