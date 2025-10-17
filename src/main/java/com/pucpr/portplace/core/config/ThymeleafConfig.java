package com.pucpr.portplace.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ThymeleafConfig {

    @Bean
    public TemplateEngine templateEngine() {
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    @Bean
    public ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");         // pasta dentro de src/main/resources/
        resolver.setSuffix(".html");              // extensão padrão
        resolver.setTemplateMode("HTML");         // modo do template
        resolver.setCharacterEncoding("UTF-8");   // encoding padrão
        resolver.setCacheable(false);             // desative cache pra desenvolvimento
        return resolver;
    }
}
