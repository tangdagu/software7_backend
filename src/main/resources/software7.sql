CREATE TABLE IF NOT EXISTS `tables`(
                                       `table_id` INT UNSIGNED AUTO_INCREMENT,
                                       `table_name` VARCHAR(100) NOT NULL,
    `table_type` VARCHAR(100) NOT NULL,
    `table_description` VARCHAR(255) NOT NULL,
    `table_create_id` INT,
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
    `task_create_date` timestamp,
    PRIMARY KEY ( `task_id` )
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;