FROM openjdk:8
VOLUME /tmp
EXPOSE 9001
ADD target/product-service-1.0.jar product-service-1.0.jar 
ENTRYPOINT ["java","-jar","/product-service-1.0.jar"]