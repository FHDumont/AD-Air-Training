FROM golang:1.9.2

RUN apt-get -y update && \
    apt-get clean all && \
    apt-get -y install tar && \
    apt-get -y install lbzip2

RUN mkdir -p /usr/local/go/src

ADD golang-sdk-x64-linux.tar /usr/local/go/src/

RUN mkdir /src

ADD go-services.go /src

WORKDIR /src

RUN go build ./go-services.go

CMD ["/bin/sh", "-c", "export UNIQUE_HOST_ID=$(sed -rn \"1s#.*/##; 1s/(.{12}).*/\\1/p\" /proc/self/cgroup) && /src/go-services"]
