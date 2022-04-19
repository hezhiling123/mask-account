CREATE TABLE `sys_oauth2_client` (
  `id` int(8) NOT NULL COMMENT 'id',
  `client_id` varchar(64) NOT NULL COMMENT '客户端id',
  `client_name` varchar(32) NOT NULL COMMENT '客户端名称',
  `client_secret` varchar(32) NOT NULL COMMENT '客户端秘钥',
  `client_type` varchar(8) NOT NULL COMMENT '客户端类型',
  `logout_url` varchar(255) DEFAULT NULL COMMENT '登出地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8