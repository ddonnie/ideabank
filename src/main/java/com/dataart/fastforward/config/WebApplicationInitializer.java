package com.dataart.fastforward.config;

import com.dataart.fastforward.config.root.DbConfig;
import com.dataart.fastforward.config.root.SecurityConfig;
import com.dataart.fastforward.config.servlet.WebMvcConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Created by Orlov on 25.10.2016.
 */

public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
                SecurityConfig.class,
                DbConfig.class} ;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {WebMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}

