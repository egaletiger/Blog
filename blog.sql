CREATE DATABASE IF NOT EXISTS blog DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
USE blog;
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
	`id` 				int(10)													NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
    `title`				varchar(32)  CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
    `content`   		text 		 CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '博客内容',
    `first_picture` 	varchar(32)  CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章开头的图片',
	`views` 			int(10) 												NULL DEFAULT NULL COMMENT '浏览次数',
    `appreciation`  	tinyint(1) 		 										NULL DEFAULT NULL COMMENT '是否可以赞赏',
    `share_statement` 	tinyint(1) 												NULL DEFAULT NULL COMMENT '是否开启转发版权显示',
    `commentable` 		tinyint(1) 											    NULl DEFAULT NULL COMMENT '是否可评论',
    `published` 		tinyint(1) 											    NULl DEFAULT NULL COMMENT '是否已发布',
    `recommend` 		tinyint(1) 												NULl DEFAULT NULL COMMENT '是否被推荐',
    `create_time` 		datetime  NULL DEFAULT NULL COMMENT '创建日期',
	`update_time` 		datetime  NULL DEFAULT NULL COMMENT '更改日期',
    `type_id`           int(10)      NULL DEFAULT NULL COMMENT '类型主键id', 
	`tag_id`            int(10)      NULL DEFAULT NULL COMMENT '标签主键id', 
    `user_id`           int(10)      NULL DEFAULT NULL COMMENT '作者主键id', 
	CONSTRAINT `fk_blog_type` FOREIGN KEY(`type_id`) REFERENCES `type`(`id`),
    CONSTRAINT `fk_blog_tag` FOREIGN KEY(`tag_id`) REFERENCES `tag`(`id`),
    CONSTRAINT `fk_blog_user` FOREIGN KEY(`user_id`) REFERENCES `user`(`id`),
	PRIMARY KEY(`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;	
  
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`(
	`id`   int(10) 												  NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
    `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型名称',
     PRIMARY KEY(`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`(
	`id`   int(10) 												  NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
    `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签名称',
	PRIMARY KEY(`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`(
	`id`  		  int(10) 											     NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
    `nick_name`   varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论用户昵称',
	`email`       varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
    `content`     varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论内容',
    `avator`      varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',  
    `create_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论时间',
	PRIMARY KEY(`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
	`id`  		   int(10) 											      NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
    `nick_name`    varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
    `username`     varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
    `password`     varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
	`email`        varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
    `content`      varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论内容',
    `avator`       varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',  
	`create_time`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建日期',
	`update_time`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更改日期',
    PRIMARY KEY(`id`) USING BTREE
)ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;
  
  CREATE TABLE `blog_comment` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `blog_id` int(10) NOT NULL COMMENT '博客id',
  `comment_id` int(10) NOT NULL COMMENT '评论id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


CREATE TABLE `blog_tag` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `blog_id` int(10) DEFAULT NULL COMMENT '博客主键id',
  `tag_id` int(10) DEFAULT NULL COMMENT '标签主键id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


