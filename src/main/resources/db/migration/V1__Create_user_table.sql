CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `token` char(50) NOT NULL,
  `create_date` bigint(20) NOT NULL,
  `update_date` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;