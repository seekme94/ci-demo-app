# Base image containing Java runtime
FROM openjdk:8-jdk-alpine

LABEL maintainer="owais.hussain@ihsinformatics.com"

# Expose port 8080 to public
EXPOSE 8080

# Deploy the web archive file to tomcat directory
COPY /target/ci-demo-app.war /usr/local/tomcat/webapps/ci-demo-app.war

# Run Catalina service
CMD ["catalina.sh", "run"]
