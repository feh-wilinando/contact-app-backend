# Contatct App Backend

This is __Rest API__ to be consumed by ContactApp (_IP-67_).

Was built on wildfly-swarm, and uses _JAX-RS_, _CDI_, _JPA_, _Maven_, _Swagger_ and database _MySql_.

## Usage

Before running application than execute `mvn clean package`.

To running execute `java -jar target/contatc-app-backend-swarm.jar`.

(or `mvn clean package && java -jar target/contatc-app-backend-swarm.jar`)

---

By default application running on port 8080. 

Accessing "/" will be redirected to swagger-ui for documentation and test __API__.
