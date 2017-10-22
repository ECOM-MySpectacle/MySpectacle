FROM metz/wildfly-mysql
ADD target/MySpectacle.war /opt/jboss/wildfly/standalone/deployments/
