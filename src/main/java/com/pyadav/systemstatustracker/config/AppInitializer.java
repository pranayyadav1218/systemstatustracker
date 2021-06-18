package com.pyadav.systemstatustracker.config;
 
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.security.access.SecurityConfig;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
 
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
 
   @Override
   protected Class<?>[] getRootConfigClasses() {
      return new Class[] { HibernateJpaAutoConfiguration.class, SecurityConfig.class };
   }
 
   @Override
   protected Class<?>[] getServletConfigClasses() {
      return new Class[] { WebMvcConfigurer.class };
   }
 
   @Override
   protected String[] getServletMappings() {
      return new String[] { "/" };
   }
}