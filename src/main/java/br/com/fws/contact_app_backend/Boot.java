package br.com.fws.contact_app_backend;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.swagger.SwaggerArchive;

public class Boot {

	public static void main(String[] args) throws Exception {
		
		Swarm swarm = new Swarm(args);
		
		swarm.fraction(
					new DatasourcesFraction()
					.dataSource("swarmDS", (ds) -> {
						
						  ds.driverName("mysql");
				          ds.connectionUrl("jdbc:mysql://localhost/user_info?createDatabaseIfNotExist=true&&useSSL=false");
				          ds.userName("root");												
					})
				);
		
		
		swarm.start();
		
		JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
		
		
		
		
		SwaggerArchive swaggerArchive = deployment.as(SwaggerArchive.class);						
		swaggerArchive.setResourcePackages("br.com.fws.contact_app_backend");									
		swaggerArchive.setPrettyPrint(true);
		
		ClassLoader classLoader = Boot.class.getClassLoader();
        
		
	    deployment.addAsWebInfResource(classLoader.getResource("beans.xml"),"beans.xml");	    
	    deployment.addAsWebInfResource(classLoader.getResource("persistence.xml"),"classes/META-INF/persistence.xml");		
	    
	    deployment.addPackages(true, Package.getPackage("br.com.fws.contact_app_backend"));
		
		deployment.addAllDependencies();

		swarm.deploy(deployment);
	
	}
}
