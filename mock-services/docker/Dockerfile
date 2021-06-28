FROM openjdk:8-jdk-alpine

COPY startup.sh /startup.sh

RUN chmod +x /startup.sh

COPY bashrc /root/.bashrc

ADD java-services.jar java-services.jar

ENTRYPOINT ["/bin/sh", "/startup.sh"]