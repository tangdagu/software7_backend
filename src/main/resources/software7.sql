CREATE DATABASE IF NOT EXISTS software7
DEFAULT CHARACTER SET utf8;
use software7;

CREATE TABLE IF NOT EXISTS `tables`(
                                       `table_id` INT UNSIGNED AUTO_INCREMENT,
                                       `table_name` VARCHAR(100) NOT NULL,
    `table_type` VARCHAR(100) NOT NULL,
    `table_description` VARCHAR(255) NOT NULL,
    `table_create_id` INT,
    `time_slice` INT,
    `table_create_date` TIMESTAMP,
    PRIMARY KEY ( `table_id` )
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS `algorithm`(
                                          `algorithm_id` INT UNSIGNED AUTO_INCREMENT,
                                          `algorithm_name` VARCHAR(100) NOT NULL,
    `algorithm_type` VARCHAR(100) NOT NULL,
    `algorithm_description` VARCHAR(255) NOT NULL,
    `algorithm_deploy_path` VARCHAR(255) NOT NULL,
    `algorithm_upload_date` timestamp,
    PRIMARY KEY ( `algorithm_id` )
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS `task`(
                                     `task_id` INT UNSIGNED AUTO_INCREMENT,
                                     `task_name` VARCHAR(100) NOT NULL,
    `task_type` VARCHAR(100) NOT NULL,
    `task_description` VARCHAR(255) NOT NULL,
    `table_name` VARCHAR(100) NOT NULL,
    `algorithm_name` VARCHAR(100) NOT NULL ,
    `disease` VARCHAR(100) NOT NULL ,
    `factor` VARCHAR(10000),
    `time_slice` INT,
    `index_name` varchar(1000),
    `task_create_date` timestamp,
    PRIMARY KEY ( `task_id` )
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;
create table if not exists `factor`(
                                       `factor_id` int unsigned auto_increment,
                                       `factor_name` varchar(100) not null,
    `category` varchar(100) not null,
    primary key(`factor_id`)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;


SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `uid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
                         `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
                         `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
                         `role` int(4) NOT NULL COMMENT '用户角色：0=普通用户，1 = 管理员',
                         `create_time` datetime(0) NOT NULL COMMENT '创建用户的时间',
                         `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新用户时间',
                         PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `user_log`;
CREATE TABLE `user_log`  (
                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
                             `uid` int(11) NOT NULL COMMENT '用户id',
                             `op_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
                             `op_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作描述',
                             `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 104 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;
