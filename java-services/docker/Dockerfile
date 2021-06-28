FROM openjdk:8-jre-alpine

RUN apk update && \
    apk --no-cache add vim procps binutils curl bash  

COPY startup.sh /startup.sh

COPY log4j.properties /log4j.properties

RUN chmod +x /startup.sh

# RUN chmod +w /etc/resolv.conf

COPY bashrc /root/.bashrc

ADD java-services.jar java-services.jar

ENTRYPOINT ["/bin/bash", "/startup.sh"]