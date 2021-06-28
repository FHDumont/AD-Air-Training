FROM node:10-slim

RUN apt-get -y update && \
    apt-get -y install vim && apt-get clean all && \
    echo 'alias ll="ls -la"' >> /etc/bash.bashrc && /bin/bash -c "source /etc/bash.bashrc" && \
    apt-get clean all

RUN  mkdir /logs

COPY src/package.json /src/package.json
RUN cd /src; npm install

COPY src/index.js /src/index.js
COPY src/services/* /src/services/

CMD ["/bin/bash", "-c", "export UNIQUE_HOST_ID=$(sed -rn \"1s#.*/##; 1s/(.{12}).*/\\1/p\" /proc/self/cgroup) && node /src/index.js"]
