package br.com.fws.contact_app_backend.configuration;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import br.com.fws.contact_app_backend.resource.ContatoResource;
import br.com.fws.contact_app_backend.resource.PerfilSocialResource;
import br.com.fws.contact_app_backend.resource.TelefoneResource;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import io.swagger.models.Contact;
import io.swagger.models.Info;
import io.swagger.models.License;



@ApplicationPath("/v1")
public class JaxRsConfiguration extends Application{
	
	public JaxRsConfiguration() {
		BeanConfig config = new BeanConfig();
		
		config.setResourcePackage("br.com.fws.contact_app_backend.resource");
		config.setScan(true);
		config.setPrettyPrint(true);
							
		
		String basePath = JaxRsConfiguration.class.getAnnotation(ApplicationPath.class).value();
		
		config.setInfo(new Info()				
							.title("Contact API")			
							
							.description("Backend application to be consumed by ContactApp")
							
							.version("1.0")
													
							.contact(
									new Contact()
											.email("fernando.furtado@caelum.com.br")
											.name("Fernando Furtado")
											.url("https://github.com/feh-wilinando/")
									)
						
							
							.license(
									new License()
										.name("Apache 2.0")
										.url("http://www.apache.org/licenses/LICENSE-2.0")
									)
			);
		config.setBasePath(basePath);
		config.setSchemes(new String[]{"http"});				
		
	}
	
	
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
		
		classes.add(ContatoResource.class);
		classes.add(PerfilSocialResource.class);
		classes.add(TelefoneResource.class);
		
		classes.add(ApiListingResource.class);
		classes.add(SwaggerSerializers.class);
		return classes; 
	}
	
}
