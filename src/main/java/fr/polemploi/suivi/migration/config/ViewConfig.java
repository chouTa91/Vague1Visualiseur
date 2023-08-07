package fr.polemploi.suivi.migration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * Classe de configuration de l'application.
 *
 * @author jbourrea
 *
 */
@Configuration
public class ViewConfig implements WebMvcConfigurer {

	@Bean
	ClassLoaderTemplateResolver templateResolver() {
		ClassLoaderTemplateResolver configurer = new ClassLoaderTemplateResolver();
		configurer.setPrefix("/webapp");
		configurer.setSuffix(".html");
		configurer.setTemplateMode(TemplateMode.HTML);
		configurer.setCharacterEncoding("UTF-8");
		configurer.setOrder(0);
		configurer.setCheckExistence(true);
		return configurer;
	}
}