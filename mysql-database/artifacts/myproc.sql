USE `Promotions`;
DROP procedure IF EXISTS `getCustomers`;

DELIMITER $$
USE `Promotions`$$
CREATE PROCEDURE `getCustomers`(IN recordLimit int, IN sleepSeconds float(12,6))
BEGIN

	Select SLEEP(sleepSeconds);
    Select c.* from customers c, address_book a where c.customers_firstname like '%p%' limit 10;

END$$

DELIMITER ; 