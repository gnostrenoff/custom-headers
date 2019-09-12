# Custom Headers

Tomcat 8 with an additional header.

- Build jar

        mvn clean package
    
- Build docker image

        docker build -t ch-tomcat .

- Run

        docker run -it --rm -p 8888:8080 -e HEADER_KEY=X-Instrumented-By -e HEADER_VALUE=myCompagny ch-tomcat

- Check HEAD request

        curl -I http://localhost:8888
