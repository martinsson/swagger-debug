package my.own.swagger.debug;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.config.SwaggerConfig;
import com.wordnik.swagger.jaxrs.config.DefaultJaxrsScanner;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.reader.ClassReaders;

public class Main {
	
	private static final URI BASE_URI = URI.create("http://localhost:8085");


	public static void main(String[] args) {
		HttpServer server = null;
		try {
			SwaggerConfig swaggerConfig = new SwaggerConfig();
            ConfigFactory.setConfig(swaggerConfig);
            swaggerConfig.setBasePath(BASE_URI.toString());
            swaggerConfig.setApiVersion("1.0.0");
            ScannerFactory.setScanner(new DefaultJaxrsScanner());
            ClassReaders.setReader(new DefaultJaxrsApiReader());

			ResourceConfig resourceConfig = new ResourceConfig();
			resourceConfig.register(com.wordnik.swagger.jaxrs.listing.ApiListingResource.class);
			resourceConfig.register(com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider.class);
			resourceConfig.register(com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON.class);
			resourceConfig.register(com.wordnik.swagger.jaxrs.listing.ResourceListingProvider.class);
			resourceConfig.packages("my.own.swagger.debug")
					.register(new LoggingFilter())
					.property("contextConfigLocation", "file:src/main/webapp/WEB-INF/spring-config/applicationContext.xml");
			server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig);
			server.getServerConfiguration().addHttpHandler(
		            new StaticHttpHandler(getTemplatePath()), "/api-docs-ui");
			System.in.read();


		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (server != null) {
				server.shutdownNow();
			}
		}
		
	}


	private static String getTemplatePath() throws URISyntaxException {
		    return new File("../../../../src/main/webapp/")
		    		.getPath();
	 }
}
