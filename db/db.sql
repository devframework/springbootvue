/*
Navicat MySQL Data Transfer

Source Server         : 本机数据库
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : springboot_vue

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2020-07-25 12:32:57
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for base_table
-- ----------------------------
DROP TABLE IF EXISTS `base_table`;
CREATE TABLE `base_table` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `tenant_id` bigint(32) DEFAULT NULL COMMENT '项目编码',
  `is_del` int(1) DEFAULT '0' COMMENT '是否删除 1已删除 0未删除',
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(32) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for sys_dic
-- ----------------------------
DROP TABLE IF EXISTS `sys_dic`;
CREATE TABLE `sys_dic` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `dic_code` varchar(100) DEFAULT NULL COMMENT '编码',
  `dic_name` varchar(100) DEFAULT NULL COMMENT '名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `tenant_id` bigint(32) DEFAULT NULL COMMENT '项目编码',
  `is_del` int(1) DEFAULT '0' COMMENT '是否删除 1已删除 0未删除',
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(32) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据库字典组';

-- ----------------------------
-- Table structure for sys_dic_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dic_item`;
CREATE TABLE `sys_dic_item` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `dic_id` bigint(32) DEFAULT NULL COMMENT '数据字典id',
  `dic_item_code` varchar(100) DEFAULT NULL COMMENT '编码',
  `dic_item_value` varchar(255) DEFAULT NULL COMMENT '值',
  `dic_item_name` varchar(100) DEFAULT NULL COMMENT '名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `tenant_id` bigint(32) DEFAULT NULL COMMENT '项目编码',
  `is_del` int(1) DEFAULT '0' COMMENT '是否删除 1已删除 0未删除',
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(32) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据库字典组-子级';

-- ----------------------------
-- Table structure for sys_func
-- ----------------------------
DROP TABLE IF EXISTS `sys_func`;
CREATE TABLE `sys_func` (
  `id` bigint(32) NOT NULL COMMENT '菜单ID',
  `menu_id` bigint(50) DEFAULT NULL COMMENT '菜单id',
  `func_code` varchar(255) DEFAULT NULL COMMENT '方法代码',
  `func_name` varchar(100) DEFAULT NULL COMMENT '方法名称',
  `func_url` varchar(200) DEFAULT NULL COMMENT '方法URL',
  `func_desc` varchar(255) DEFAULT NULL COMMENT '方法描述',
  `sort_no` int(11) DEFAULT NULL COMMENT '方法排序',
  `is_del` int(1) DEFAULT '0' COMMENT '是否删除 1已删除 0未删除',
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(32) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint(32) DEFAULT NULL COMMENT '项目编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for sys_language
-- ----------------------------
DROP TABLE IF EXISTS `sys_language`;
CREATE TABLE `sys_language` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `tenant_id` bigint(32) DEFAULT NULL COMMENT '项目编码',
  `is_del` int(1) DEFAULT '0' COMMENT '是否删除 1已删除 0未删除',
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(32) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `language_code` varchar(30) DEFAULT NULL COMMENT '多语言编码',
  `language_cn` varchar(255) DEFAULT NULL COMMENT '中文简体',
  `language_en` varchar(255) DEFAULT NULL COMMENT '英文',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(32) NOT NULL COMMENT '主键',
  `user_id` bigint(32) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(200) DEFAULT NULL COMMENT '用户名',
  `ip` varchar(255) DEFAULT NULL COMMENT '用户ip',
  `url` varchar(255) DEFAULT NULL COMMENT '原始请求',
  `target` varchar(255) DEFAULT NULL COMMENT '操作目标',
  `description` varchar(255) DEFAULT NULL COMMENT '方法描述',
  `method` varchar(255) DEFAULT NULL COMMENT '操作方法',
  `params` longtext COMMENT '创建时间',
  `result` longtext COMMENT '操作参数',
  `tenant_id` bigint(32) DEFAULT NULL COMMENT '项目编码',
  `is_del` int(1) DEFAULT '0' COMMENT '是否删除 1已删除 0未删除',
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(32) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作记录';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(32) NOT NULL COMMENT '菜单ID',
  `tenant_id` bigint(32) DEFAULT NULL COMMENT '项目编码',
  `parent_id` bigint(32) DEFAULT NULL COMMENT '父级节点',
  `menu_type` varchar(50) DEFAULT NULL COMMENT '菜单类型',
  `menu_platform` varchar(50) DEFAULT 'PC' COMMENT '菜单平台 PC pc端 webapp移动端网页 app移动端app',
  `menu_name` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `menu_code` varchar(255) DEFAULT NULL COMMENT '菜单代码',
  `menu_url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `menu_desc` varchar(255) DEFAULT NULL COMMENT '菜单备注',
  `menu_icon` varchar(100) DEFAULT NULL COMMENT '菜单图标',
  `sort_no` int(11) DEFAULT NULL COMMENT '菜单排序',
  `is_del` int(1) DEFAULT '0' COMMENT '是否删除 1已删除 0未删除',
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(32) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(32) NOT NULL COMMENT 'COLUMN_COMMENT',
  `role_name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `role_level` int(11) DEFAULT NULL COMMENT '角色等级',
  `role_code` varchar(20) DEFAULT NULL COMMENT '角色代码',
  `is_del` int(1) DEFAULT '0' COMMENT '是否删除 1已删除 0未删除',
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(32) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint(32) DEFAULT NULL COMMENT '项目编码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限管理-角色管理';

-- ----------------------------
-- Table structure for sys_role_func
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_func`;
CREATE TABLE `sys_role_func` (
  `id` bigint(32) NOT NULL,
  `role_id` bigint(32) NOT NULL COMMENT '权限id',
  `func_id` bigint(32) NOT NULL COMMENT '方法ID',
  `is_del` int(1) DEFAULT '0' COMMENT '是否删除 1已删除 0未删除',
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(32) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint(32) DEFAULT NULL COMMENT '项目编码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(32) NOT NULL,
  `role_id` bigint(32) NOT NULL COMMENT '权限id',
  `menu_id` bigint(32) NOT NULL COMMENT '菜单ID',
  `is_del` int(1) DEFAULT '0' COMMENT '是否删除 1已删除 0未删除',
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(32) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint(32) DEFAULT NULL COMMENT '项目编码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `id` bigint(32) NOT NULL,
  `user_id` bigint(32) NOT NULL,
  `role_id` bigint(32) NOT NULL,
  `tenant_id` bigint(32) DEFAULT NULL COMMENT '项目编码',
  `is_del` int(1) DEFAULT '0' COMMENT '是否删除 1已删除 0未删除',
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(32) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色用户关系表';

-- ----------------------------
-- Table structure for sys_sequence
-- ----------------------------
DROP TABLE IF EXISTS `sys_sequence`;
CREATE TABLE `sys_sequence` (
  `id` bigint(32) DEFAULT NULL,
  `code` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '序列号代码',
  `name` varchar(100) CHARACTER SET utf8 NOT NULL COMMENT '名称',
  `prefix` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '前缀',
  `separator` varchar(5) CHARACTER SET utf8 DEFAULT NULL COMMENT '分隔符',
  `date_format` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '日期格式',
  `num_length` int(5) NOT NULL DEFAULT '4' COMMENT '数字部分长度',
  `init_value` int(5) NOT NULL DEFAULT '1' COMMENT '初始值',
  `is_del` int(1) DEFAULT '0' COMMENT '是否删除 1已删除 0未删除',
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(32) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint(32) DEFAULT NULL COMMENT '租户编码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='sys_sequence';

-- ----------------------------
-- Table structure for sys_sequence_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_sequence_log`;
CREATE TABLE `sys_sequence_log` (
  `id` bigint(32) DEFAULT NULL,
  `sequence_id` bigint(32) DEFAULT NULL COMMENT '序列号ID',
  `prefix` varchar(30) DEFAULT NULL COMMENT '自定义前缀',
  `flow_code` varchar(35) DEFAULT NULL COMMENT '流水编码',
  `is_del` int(1) DEFAULT '0' COMMENT '是否删除 1已删除 0未删除',
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(32) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint(32) DEFAULT NULL COMMENT '租户编码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='生成的队列号';

-- ----------------------------
-- Table structure for sys_sequence_queue
-- ----------------------------
DROP TABLE IF EXISTS `sys_sequence_queue`;
CREATE TABLE `sys_sequence_queue` (
  `id` bigint(32) DEFAULT NULL,
  `sequence_id` bigint(32) DEFAULT NULL COMMENT '序列号ID',
  `prefix` varchar(30) DEFAULT NULL COMMENT '自定义前缀',
  `current_code` varchar(35) DEFAULT NULL COMMENT '当前流水码编号',
  `current_value` bigint(5) DEFAULT NULL COMMENT '当前数值',
  `is_del` int(1) DEFAULT '0' COMMENT '是否删除 1已删除 0未删除',
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(32) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint(32) DEFAULT NULL COMMENT '租户编码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='生成的队列号';

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant` (
  `id` bigint(32) NOT NULL,
  `tenant_code` varchar(32) DEFAULT NULL COMMENT '租户编码',
  `tenant_name` varchar(50) DEFAULT NULL COMMENT '租户名称',
  `description` varchar(255) DEFAULT NULL COMMENT '租户描述',
  `tenant_url` varchar(100) DEFAULT NULL COMMENT '租户url地址',
  `tenant_db` varchar(255) DEFAULT NULL COMMENT '租户数据库',
  `expiration_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '服务到期时间',
  `start_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '开始时间',
  `is_del` int(1) DEFAULT '0' COMMENT '是否删除 1已删除 0未删除',
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(32) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租户信息';

-- ----------------------------
-- Table structure for sys_upload
-- ----------------------------
DROP TABLE IF EXISTS `sys_upload`;
CREATE TABLE `sys_upload` (
  `id` bigint(32) NOT NULL DEFAULT '0',
  `tenant_id` bigint(32) DEFAULT NULL COMMENT '项目编码',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `url` varchar(2000) DEFAULT NULL COMMENT '地址',
  `sort` int(64) DEFAULT NULL COMMENT '排序',
  `is_del` int(1) DEFAULT '0' COMMENT '是否删除 1已删除 0 未删除',
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(32) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `memo` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='上传文件';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `tenant_id` bigint(32) DEFAULT NULL COMMENT '项目编码',
  `login_name` varchar(20) DEFAULT NULL COMMENT '登录名',
  `nick_name` varchar(100) DEFAULT NULL COMMENT '昵称',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `gender` varchar(2) DEFAULT NULL COMMENT '性别',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `mobile_phone` varchar(15) DEFAULT NULL COMMENT '手机号码',
  `work_num` varchar(32) DEFAULT NULL COMMENT '工号',
  `wechat` varchar(65) DEFAULT NULL COMMENT '微信号',
  `qq` varchar(20) DEFAULT NULL COMMENT 'QQ号',
  `fax` varchar(20) DEFAULT NULL COMMENT '传真',
  `last_login_time` datetime(6) DEFAULT NULL COMMENT '最后登录时间',
  `expire_time` datetime(6) DEFAULT NULL COMMENT '有效期',
  `is_enable` int(11) DEFAULT '1' COMMENT '是否禁用 1启用 0禁用',
  `user_img` varchar(500) DEFAULT NULL COMMENT '用户头像',
  `user_skin` text COMMENT '用户皮肤',
  `is_del` int(1) DEFAULT '0' COMMENT '是否删除 1已删除 0 未删除',
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(32) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户基本信息';

-- ----------------------------
-- Table structure for sys_user_column
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_column`;
CREATE TABLE `sys_user_column` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `user_id` bigint(32) DEFAULT NULL COMMENT '用户ID',
  `model_code` varchar(255) DEFAULT NULL COMMENT '模型',
  `table_columns` text COMMENT '定义的值',
  `defalut` int(1) DEFAULT NULL COMMENT '默认',
  `column_value` text COMMENT '列表值',
  `tenant_id` bigint(32) DEFAULT NULL COMMENT '项目编码',
  `is_del` int(1) DEFAULT '0' COMMENT '是否删除 1已删除 0未删除',
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(32) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
