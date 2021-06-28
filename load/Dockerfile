FROM node:6.14.2-slim

RUN echo 'alias ll="ls -la"' >> /etc/bash.bashrc && /bin/bash -c "source /etc/bash.bashrc"

RUN  mkdir /logs

COPY src/package.json /src/package.json
RUN cd /src; npm install

COPY src/index.js /src/index.js
COPY src/base-load/* /src/base-load/
COPY src/ticketing-load/* /src/ticketing-load/

CMD ["node", "/src/index.js"]