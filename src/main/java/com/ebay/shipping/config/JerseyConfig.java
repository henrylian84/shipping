package com.ebay.shipping.config;

import com.ebay.shipping.web.AdminController;
import com.ebay.shipping.web.AppController;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;


import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;


@Configuration
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {
    @PostConstruct
    public void init() {
        register(AppController.class);
        register(AdminController.class);
        this.SwaggerConfig();
    }

    private void SwaggerConfig() {
        this.register(ApiListingResource.class);
        this.register(SwaggerSerializers.class);

        BeanConfig swaggerConfigBean = new BeanConfig();
        swaggerConfigBean.setTitle("Ebay Shipping Eligibility Service API");
        swaggerConfigBean.setVersion("v1");
        swaggerConfigBean.setContact("Henry Lian");
        swaggerConfigBean.setSchemes(new String[] { "http", "https" });
        swaggerConfigBean.setResourcePackage("com.ebay.shipping.web");
        swaggerConfigBean.setPrettyPrint(true);
        swaggerConfigBean.setBasePath("/api");
        swaggerConfigBean.setScan(true);
    }
}
