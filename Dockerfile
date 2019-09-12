FROM tomcat:8-jdk8-openjdk

COPY target/ch-agent.jar /opt/customheaders/ch-agent.jar
ENV CATALINA_OPTS "$CATALINA_OPTS -javaagent:/opt/customheaders/ch-agent.jar"
