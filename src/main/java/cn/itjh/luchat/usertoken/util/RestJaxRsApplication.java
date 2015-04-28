package cn.itjh.luchat.usertoken.util;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("resources")
public class RestJaxRsApplication extends ResourceConfig {
    public RestJaxRsApplication() {
        final String myRestPackage = "cn.itjh.luchat.usertoken.server";
        final String jacksonPackage = "org.codehaus.jackson.jaxrs";
        final String swaggerJaxrsJsonPackage = "com.wordnik.swagger.jaxrs.json";
        final String swaggerJaxrsListingPackage = "com.wordnik.swagger.jaxrs.listing";
        packages(swaggerJaxrsJsonPackage, swaggerJaxrsListingPackage, jacksonPackage, myRestPackage);
        // enable multipart
        // register(MultiPartFeature.class);
    }
}
