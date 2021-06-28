#!/bin/sh

#create our db monitoring user
mysql --user=root --password=l3tm31n -e "CREATE USER 'monitor'@'localhost' IDENTIFIED BY 'appd123'; GRANT ALL PRIVILEGES ON *.* TO 'monitor'@'localhost'"
mysql --user=root --password=l3tm31n -e "CREATE USER 'monitor'@'%' IDENTIFIED BY 'appd123'; GRANT ALL PRIVILEGES ON *.* TO 'monitor'@'%'"

#creates our user to support remote attach
mysql --user=root --password=l3tm31n -e "CREATE USER 'promotions'@'%' IDENTIFIED BY 'promotions'; GRANT ALL PRIVILEGES ON *.* TO 'promotions'@'%' WITH GRANT OPTION"

#create and populate our database, we assume we added the .sql data file when the container was built
mysql --user=root --password=l3tm31n -e "CREATE DATABASE Promotions"
mysql -p Promotions --user=promotions --password=promotions < /usr/local/promotions/database/MySQL/promotions.sql

mysql -p Promotions --user=promotions --password=promotions < /usr/local/promotions/database/MySQL/myproc.sql
