package br.com.fws.contact_app_backend.configuration;

import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;

@SwaggerDefinition(
		
		info=@Info(	title="Contact API", 
					description ="Backend application to be consumed by ContactApp",
					version="1.0",
					contact = @Contact(	email="fernando.furtado@caelum.com.br", 
										name="Fernando Furtado",
										url="https://github.com/feh-wilinando/"
									  ),
					license = @License(	name="Apache 2.0", 
										url="http://www.apache.org/licenses/LICENSE-2.0"
									  )
					),
		
		basePath="/v1/",
		
		schemes=SwaggerDefinition.Scheme.HTTP,
		
		consumes=MediaType.APPLICATION_JSON,
		
		produces=MediaType.APPLICATION_JSON
		
		
		)
public interface ApiMetaData {
}
