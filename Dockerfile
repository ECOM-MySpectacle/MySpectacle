FROM metz/wildfly-mysql
ADD target/MyApplication.war /opt/jboss/wildfly/standalone/deployments/
