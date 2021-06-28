FROM mysql:5.7

#create a director and stage our data
RUN mkdir -p /usr/local/promotions/database/MySQL

#add our db
ADD artifacts/promotions.sql /usr/local/promotions/database/MySQL/promotions.sql

ADD artifacts/myproc.sql /usr/local/promotions/database/MySQL/myproc.sql

#this is the script that will initialize our mysql instance, it's automatically executed by the
#docker container since we put it into /docker-entrypoint-initdb.d
ADD artifacts/init-promotions-db.sh /docker-entrypoint-initdb.d/init-promotions-db.sh
