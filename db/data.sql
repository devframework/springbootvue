/*
Navicat MySQL Data Transfer

Source Server         : 本机数据库
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : springboot_vue

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2020-07-25 12:39:43
*/

 
-- ----------------------------
-- Records of sys_dic
-- ----------------------------
INSERT INTO `sys_dic` VALUES ('1279634870106804225', 'USER_TYPE', '用户类型', '数据字典编辑测试', null, '1', '2020-07-04 23:35:02.650000', '1', '2020-07-04 23:36:44.333000', '1', null);
INSERT INTO `sys_dic` VALUES ('1279635464821362689', 'USER_TYPE', '用户类型', '用户类型数据字典', null, '1', '2020-07-04 23:37:23.948000', '1', '2020-07-04 23:37:23.948000', '1', null);
INSERT INTO `sys_dic` VALUES ('1279636532280119298', '111', '111', '11', null, '1', '2020-07-04 23:41:38.449000', '1', '2020-07-04 23:41:38.449000', '1', null);
INSERT INTO `sys_dic` VALUES ('1279664577309265922', null, null, '1', null, '1', '2020-07-05 01:33:04.906000', '1', '2020-07-05 01:33:04.906000', '1', null);
INSERT INTO `sys_dic` VALUES ('1279669012961845249', 'USER_TYPE', '用户类型', '', null, '1', '2020-07-05 01:50:42.448000', '1', '2020-07-05 01:50:42.448000', '1', null);
INSERT INTO `sys_dic` VALUES ('1279680576448258050', '222', '222', '', null, '1', '2020-07-05 02:36:39.398000', '1', '2020-07-05 02:36:39.398000', '1', null);
INSERT INTO `sys_dic` VALUES ('1279715378723446785', 'USER_TYPE', '用户类型', '用户类型', null, '0', '2020-07-05 04:54:56.907000', '1', '2020-07-05 04:54:56.907000', '1', null);
INSERT INTO `sys_dic` VALUES ('1284775224163700737', 'WLFOW_TYPE', '业务单据类型', '', null, '0', '2020-07-19 09:00:58.068000', '1', '2020-07-19 09:00:58.068000', '1', null);
 
-- ----------------------------
-- Records of sys_dic_item
-- ----------------------------
INSERT INTO `sys_dic_item` VALUES ('1279717028599095298', '1279715378723446785', 'administrator', 'administrator', '系统管理员', 'administrator', null, '0', '2020-07-05 05:01:30.268000', '1', '2020-07-05 05:01:30.268000', '1', null);
INSERT INTO `sys_dic_item` VALUES ('1281843241980809217', '1279715378723446785', '111', '11', '111', '1', null, '1', '2020-07-11 01:50:19.042000', '1', '2020-07-11 01:50:19.042000', '1', null);
INSERT INTO `sys_dic_item` VALUES ('1284775425855197186', '1284775224163700737', 'holiday', 'holiday', '请假单', '请假单', null, '0', '2020-07-19 09:01:46.157000', '1', '2020-07-19 09:01:46.157000', '1', null);

 
-- ----------------------------
-- Records of sys_func
-- ----------------------------
INSERT INTO `sys_func` VALUES ('1279284552609193985', '9', 'sysuser-add', '用户新增', '/sysUser/save', '用户新增', '0', '0', null, null, null, null, null, null);
INSERT INTO `sys_func` VALUES ('1279290606889926658', '9', 'sysuser-list', '用户列表', '/sysUser/list', '用户列表', '2', '0', null, null, null, null, null, null);
INSERT INTO `sys_func` VALUES ('1279290752067371010', '9', 'sysuer-enable', '用户作废/启用', '/sysUser/enable', '用户作废/启用', null, '0', null, null, null, null, null, null);
INSERT INTO `sys_func` VALUES ('1279291097967427585', '9', 'sysuser-save', '用户保存', '/sysUser/save', '用户保存', '0', '0', null, null, null, null, null, null);
INSERT INTO `sys_func` VALUES ('1279291246219296770', '9', 'sysuser-get', '用户详情', '/sysUser/get', '用户详情', null, '0', null, null, null, null, null, null);
INSERT INTO `sys_func` VALUES ('1279291325902684162', '9', 'sysuser-delete', '用户删除', '/sysUser/delete', 'delete', null, '0', null, null, null, null, null, null);
INSERT INTO `sys_func` VALUES ('1279291622343507969', '9', 'sysuser-rest-password', '密码重置', '/sysUser/rest-password', '密码重置', '6', '0', null, null, null, null, null, null);
INSERT INTO `sys_func` VALUES ('1279291751133806593', '10', 'sysrole-save', '角色保存', '/sysRole/save', '角色保存', null, '0', null, null, null, null, null, null);
INSERT INTO `sys_func` VALUES ('1279291881551495169', '10', 'sysrole-list', '角色查询', '/sysRole/list', '角色查询', '2', '0', null, null, null, null, null, null);
INSERT INTO `sys_func` VALUES ('1279292157318594561', '10', 'sysrole-get', '角色明细', '/sysRole/get', '角色明细', null, '0', null, null, null, null, null, null);
INSERT INTO `sys_func` VALUES ('1279292250792853506', '10', 'sysrole-delete', '删除功能', '/sysRole/delete', '删除功能', '3', '0', null, null, null, null, null, null);
INSERT INTO `sys_func` VALUES ('1279292444045410306', '1277971356380254209', 'sysmenu-save', '菜单保存', '/sysMenu/save', '菜单保存', '1', '0', null, null, null, null, null, null);
INSERT INTO `sys_func` VALUES ('1279292699939897346', '1277971356380254209', 'sysmenu-delete', '菜单删除', '/sysMenu/delete', '菜单删除', '2', '0', null, null, null, null, null, null);
INSERT INTO `sys_func` VALUES ('1279292916915437570', '1277971356380254209', 'sysfunc-menu-func-list', '菜单方法查询', '/sysFunc/menu-func-list', '菜单方法查询', '3', '0', null, null, null, null, null, null);
INSERT INTO `sys_func` VALUES ('1279293012860141570', '1277971356380254209', 'sysfunc-save', '方法保存', '/sysFunc/save', '方法保存', null, '0', null, null, null, null, null, null);
INSERT INTO `sys_func` VALUES ('1279293236651425793', '1277971356380254209', 'sysfunc-delete', '方法删除', '/sysFunc/delete', '方法删除', '5', '0', null, null, null, null, null, null);
INSERT INTO `sys_func` VALUES ('1281217135324676097', '1279624648692809729', 'sysdic-save', '数据字典保存', '/sysDic/save', '数据字典保存', null, '0', '2020-07-09 08:22:23.581000', '1', '2020-07-09 08:25:54.000000', '1', null, null);
INSERT INTO `sys_func` VALUES ('1281217507116171265', '1279624648692809729', 'sysdic-list', '数据字典集合', '/sysDic/list', '数据字典集合', null, '0', '2020-07-09 08:23:52.223000', '1', '2020-07-09 08:25:58.508000', '1', null, null);
INSERT INTO `sys_func` VALUES ('1281217627291369474', '1279624648692809729', 'sysdic-delete', '数据字典删除', '/sysDic/delete', '数据字典删除', null, '0', '2020-07-09 08:24:20.875000', '1', '2020-07-09 08:26:03.317000', '1', null, null);
INSERT INTO `sys_func` VALUES ('1281217794157559809', '1279624648692809729', 'sysdicitem-list', '数据字典子项查询', '/sysDicItem/list', '数据字典子项查询', null, '0', '2020-07-09 08:25:00.659000', '1', '2020-07-09 08:25:00.659000', '1', null, null);
INSERT INTO `sys_func` VALUES ('1281218191270068226', '1279624648692809729', 'sysdicitem-save', '数据字典子项保存', '/sysDicItem/save', '', null, '0', '2020-07-09 08:26:35.339000', '1', '2020-07-09 08:26:35.339000', '1', null, null);
INSERT INTO `sys_func` VALUES ('1281218350645231618', '1279624648692809729', 'sysdicitem-delete', '数据字典子项删除', '/sysDicItem/delete', '数据字典子项删除', null, '0', '2020-07-09 08:27:13.337000', '1', '2020-07-09 08:27:13.337000', '1', null, null);
INSERT INTO `sys_func` VALUES ('1281219018881744898', '1280165003439849474', 'syslog-list', '日志查询', '/sysLog/list', '日志查询', null, '0', '2020-07-09 08:29:52.656000', '1', '2020-07-09 08:29:52.656000', '1', null, null);

 
-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', null, '0', 'root', 'PC', '系统菜单管理', 'rootMenu', null, null, null, '0', '0', null, null, null, null, null);
INSERT INTO `sys_menu` VALUES ('2', null, '1', 'module', 'PC', '系统管理', 'sysManager', null, null, 'el-icon-setting', '1', '0', null, null, null, null, null);
INSERT INTO `sys_menu` VALUES ('9', null, '2', 'menu', 'PC', '账号管理', 'sysuser', '/sysuser', null, 'el-icon-user', '1', '0', null, null, null, null, null);
INSERT INTO `sys_menu` VALUES ('10', null, '2', 'menu', 'PC', '角色管理', 'sysrole', '/sysrole', null, 'el-icon-star-on', '2', '0', null, null, null, null, null);
INSERT INTO `sys_menu` VALUES ('1277971356380254209', null, '2', 'menu', 'PC', '菜单管理', 'sysmenu', '/sysmenu', null, 'el-icon-star-on', '10', '0', null, null, null, null, null);
INSERT INTO `sys_menu` VALUES ('1279040738241974274', null, '1', 'menu', 'PC', '应用管理', 'Application', null, null, null, null, '1', null, null, null, null, null);
INSERT INTO `sys_menu` VALUES ('1279624648692809729', null, '2', 'menu', 'PC', '数据字典', 'sysdic', '/sysdic', null, 'el-icon-star-on', null, '0', '2020-07-04 22:54:25.182000', '1', '2020-07-04 22:54:33.056000', '1', null);
INSERT INTO `sys_menu` VALUES ('1280165003439849474', null, '2', null, 'PC', '日志管理', 'syslog', '/syslog', null, 'el-icon-star-on', null, '0', '2020-07-06 10:41:35.792000', '1', '2020-07-06 10:41:35.792000', '1', null);
INSERT INTO `sys_menu` VALUES ('1281215864568004610', null, '2', null, 'PC', '1', 'el-icon-star-on', '1', null, 'el-icon-star-on', '11', '1', '2020-07-09 08:17:20.609000', '1', '2020-07-09 08:17:20.609000', '1', null);
INSERT INTO `sys_menu` VALUES ('1281216516471898113', null, '2', null, 'PC', '流水编码', 'SysSequence', '/syssequence', null, 'el-icon-star-on', '11', '0', '2020-07-09 08:19:56.036000', '1', '2020-07-09 08:21:19.325000', '1', null);
INSERT INTO `sys_menu` VALUES ('1281782550129299457', null, '2', null, 'PC', '定时任务', 'quartz', '/sysjob', null, 'el-icon-alarm-clock', null, '0', '2020-07-10 21:49:08.977000', '1', '2020-07-18 04:40:01.664000', '1', null);
INSERT INTO `sys_menu` VALUES ('1284349997831172097', null, '1', null, 'PC', '流程管理', 'workflow', '', null, 'el-icon-s-check', '2', '0', '2020-07-18 04:51:16.209000', '1', '2020-07-18 04:51:16.209000', '1', null);
INSERT INTO `sys_menu` VALUES ('1284350309245661185', null, '1284349997831172097', null, 'PC', '流程设计', 'wflowdesign', '/wflowdesign', null, 'el-icon-edit-outline', null, '0', '2020-07-18 04:52:30.457000', '1', '2020-07-18 06:58:24.850000', '1', null);
INSERT INTO `sys_menu` VALUES ('1284350939137847297', null, '1284349997831172097', null, 'PC', '流程定义', 'wflowdefine', '/wflowdefine', null, 'el-icon-s-order', null, '0', '2020-07-18 04:55:00.635000', '1', '2020-07-18 04:55:00.635000', '1', null);
INSERT INTO `sys_menu` VALUES ('1284351086005596162', null, '1284349997831172097', null, 'PC', '流程签核', 'wflowtask', '/wflowtask', null, 'el-icon-s-check', null, '0', '2020-07-18 04:55:35.650000', '1', '2020-07-18 04:55:35.650000', '1', null);
INSERT INTO `sys_menu` VALUES ('1284355693385695233', null, '1284349997831172097', null, 'PC', '签核历史', 'wflowHistory', '/wflowhistory', null, 'el-icon-coin', null, '0', '2020-07-18 05:13:54.135000', '1', '2020-07-18 05:13:54.135000', '1', null);
INSERT INTO `sys_menu` VALUES ('1284491806838415361', null, '1', null, 'PC', '业务处理', 'business', '', null, 'el-icon-s-platform', null, '0', '2020-07-18 14:14:46.111000', '1', '2020-07-18 14:14:46.111000', '1', null);
INSERT INTO `sys_menu` VALUES ('1284492077085810689', null, '1284491806838415361', null, 'PC', '单据申请', 'DocumentsApply', '/holiday', null, 'el-icon-s-order', null, '0', '2020-07-18 14:15:50.544000', '1', '2020-07-18 14:15:50.544000', '1', null);

 
-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '系统管理员', '1', 'administrator', '0', '2020-06-27 18:09:37.000000', null, '2020-07-11 01:45:53.551000', '1', null, null);
INSERT INTO `sys_role` VALUES ('1278697713124974593', '普通用户', null, 'normaluser', '0', null, null, '2020-07-10 21:19:36.152000', '1', null, null);

 
-- ----------------------------
-- Records of sys_role_func
-- ----------------------------
INSERT INTO `sys_role_func` VALUES ('1279575943071580161', '1278697713124974593', '1279284552609193985', '0', '2020-07-04 19:40:52.858000', '1', '2020-07-04 19:40:52.858000', '1', null, null);
INSERT INTO `sys_role_func` VALUES ('1279575943100940290', '1278697713124974593', '1279290606889926658', '0', '2020-07-04 19:40:52.864000', '1', '2020-07-04 19:40:52.864000', '1', null, null);
INSERT INTO `sys_role_func` VALUES ('1279575943109328897', '1278697713124974593', '1279290752067371010', '0', '2020-07-04 19:40:52.865000', '1', '2020-07-04 19:40:52.865000', '1', null, null);
INSERT INTO `sys_role_func` VALUES ('1279575943109328898', '1278697713124974593', '1279291097967427585', '0', '2020-07-04 19:40:52.866000', '1', '2020-07-04 19:40:52.866000', '1', null, null);
INSERT INTO `sys_role_func` VALUES ('1279575943117717506', '1278697713124974593', '1279291246219296770', '0', '2020-07-04 19:40:52.867000', '1', '2020-07-04 19:40:52.867000', '1', null, null);
INSERT INTO `sys_role_func` VALUES ('1279575943117717507', '1278697713124974593', '1279291325902684162', '0', '2020-07-04 19:40:52.868000', '1', '2020-07-04 19:40:52.868000', '1', null, null);
INSERT INTO `sys_role_func` VALUES ('1279575943117717508', '1278697713124974593', '1279291622343507969', '0', '2020-07-04 19:40:52.868000', '1', '2020-07-04 19:40:52.869000', '1', null, null);
INSERT INTO `sys_role_func` VALUES ('1281842478579732481', '1278697713124974593', '1279291751133806593', '0', '2020-07-11 01:47:17.033000', '1', '2020-07-11 01:47:17.033000', '1', null, null);
INSERT INTO `sys_role_func` VALUES ('1281842478592315393', '1278697713124974593', '1279291881551495169', '0', '2020-07-11 01:47:17.035000', '1', '2020-07-11 01:47:17.035000', '1', null, null);
INSERT INTO `sys_role_func` VALUES ('1281842478592315394', '1278697713124974593', '1279292157318594561', '0', '2020-07-11 01:47:17.035000', '1', '2020-07-11 01:47:17.035000', '1', null, null);
INSERT INTO `sys_role_func` VALUES ('1281842478592315395', '1278697713124974593', '1279292250792853506', '0', '2020-07-11 01:47:17.035000', '1', '2020-07-11 01:47:17.035000', '1', null, null);

 
-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1279035017534279682', '1278697713124974593', '2', '0', '2020-07-03 07:51:26.157000', null, null, null, null, null);
INSERT INTO `sys_role_menu` VALUES ('1279035017542668289', '1278697713124974593', '9', '0', '2020-07-03 07:51:26.157000', null, null, null, null, null);
INSERT INTO `sys_role_menu` VALUES ('1279049253316718594', '1278697713124974593', '1279040738241974274', '1', '2020-07-03 08:47:58.799000', null, null, null, null, null);
INSERT INTO `sys_role_menu` VALUES ('1279050223123636225', '1', '2', '0', '2020-07-03 08:51:50.074000', null, null, null, null, null);
INSERT INTO `sys_role_menu` VALUES ('1279050223157190658', '1', '9', '0', '2020-07-03 08:51:50.074000', null, null, null, null, null);
INSERT INTO `sys_role_menu` VALUES ('1279050223157190659', '1', '10', '0', '2020-07-03 08:51:50.074000', null, null, null, null, null);
INSERT INTO `sys_role_menu` VALUES ('1279050223157190660', '1', '13', '0', '2020-07-03 08:51:50.074000', null, null, null, null, null);
INSERT INTO `sys_role_menu` VALUES ('1279050223157190661', '1', '53', '0', '2020-07-03 08:51:50.074000', null, null, null, null, null);
INSERT INTO `sys_role_menu` VALUES ('1279050223165579265', '1', '1277971356380254209', '0', '2020-07-03 08:51:50.074000', null, null, null, null, null);
INSERT INTO `sys_role_menu` VALUES ('1279050223165579266', '1', '1279040738241974274', '0', '2020-07-03 08:51:50.074000', null, null, null, null, null);
INSERT INTO `sys_role_menu` VALUES ('1279050223165579267', '1', '1279040886867136514', '0', '2020-07-03 08:51:50.074000', null, null, null, null, null);
INSERT INTO `sys_role_menu` VALUES ('1279050223173967873', '1', '1279041032740835330', '0', '2020-07-03 08:51:50.074000', null, null, null, null, null);
INSERT INTO `sys_role_menu` VALUES ('1279335087773306882', '1278697713124974593', '10', '1', '2020-07-04 03:43:47.087000', null, null, null, null, null);
INSERT INTO `sys_role_menu` VALUES ('1279335087802667010', '1278697713124974593', '1277971356380254209', '1', '2020-07-04 03:43:47.087000', null, null, null, null, null);
INSERT INTO `sys_role_menu` VALUES ('1281842478407766018', '1278697713124974593', '10', '0', '2020-07-11 01:47:16.991000', '1', '2020-07-11 01:47:16.991000', '1', null, null);
INSERT INTO `sys_role_menu` VALUES ('1284489065026039810', '1278697713124974593', '1284349997831172097', '0', '2020-07-18 14:03:52.412000', '1', '2020-07-18 14:03:52.412000', '1', null, null);
INSERT INTO `sys_role_menu` VALUES ('1284489065026039811', '1278697713124974593', '1284350309245661185', '0', '2020-07-18 14:03:52.413000', '1', '2020-07-18 14:03:52.413000', '1', null, null);
INSERT INTO `sys_role_menu` VALUES ('1284489065034428418', '1278697713124974593', '1284350939137847297', '0', '2020-07-18 14:03:52.414000', '1', '2020-07-18 14:03:52.414000', '1', null, null);
INSERT INTO `sys_role_menu` VALUES ('1284489065034428419', '1278697713124974593', '1284351086005596162', '0', '2020-07-18 14:03:52.414000', '1', '2020-07-18 14:03:52.414000', '1', null, null);
INSERT INTO `sys_role_menu` VALUES ('1284489065034428420', '1278697713124974593', '1284355693385695233', '0', '2020-07-18 14:03:52.415000', '1', '2020-07-18 14:03:52.415000', '1', null, null);

 

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES ('20180716073920', '1', '1', null, '0', null, null, null, null, null);
INSERT INTO `sys_role_user` VALUES ('1279362490042073089', '1278687336492371970', '1', null, '0', null, null, null, null, null);
INSERT INTO `sys_role_user` VALUES ('1279362490063044610', '1278687336492371970', '1278697713124974593', null, '0', null, null, null, null, null);
INSERT INTO `sys_role_user` VALUES ('1284489161402757122', '1279577615655149569', '1278697713124974593', null, '0', '2020-07-18 14:04:15.390000', '1', '2020-07-18 14:04:15.390000', '1', null);

 
-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '1', 'admin', '超级管理员', '$2a$10$itN/FXVW.3c3ckaPAhilyOp46xIQIoMOqPHziQvStGbjFKNFA/ll2', '1', '1152232809@qq.com', '13025161555', 'admin', '', '', '', '2018-04-15 10:20:23.000000', '9999-12-31 00:00:00.000000', '1', null, '', '0', '2018-04-15 10:02:55.000000', null, '2020-07-10 21:19:08.369000', '1', null);
INSERT INTO `sys_user` VALUES ('1279577531316084738', null, 'user', 'user', '$2a$10$5ARs7OQtCL.2B32rovGYU.wGFU93P4DTN5qCzAnK2mX4M77qwSl5y', null, '1', '1', null, null, '1', null, null, '9999-12-31 00:00:00.000000', '1', null, null, '1', '2020-07-04 19:47:11.525000', '1', '2020-07-04 19:47:11.525000', '1', null);
INSERT INTO `sys_user` VALUES ('1279577615655149569', null, 'user', 'user', '$2a$10$Gi3bZ2NZO7QOBO1odhuamOfmrbJZmvrOP5J6w7LQwgihW2DWEcdJ6', null, '1', '1', null, null, '1', null, null, '9999-12-31 00:00:00.000000', '1', null, null, '0', '2020-07-04 19:47:31.633000', '1', '2020-07-10 21:19:20.330000', '1', null);

 
-- ----------------------------
-- Records of sys_user_column
-- ----------------------------
INSERT INTO `sys_user_column` VALUES ('1279655436612227073', '1', 'table_sysdic_-sysdic', '[{\"label\":\"字典编号\",\"prop\":\"dicCode\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":0},{\"label\":\"字典名称\",\"prop\":\"dicName\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":0},{\"label\":\"字典描述\",\"prop\":\"description\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"创建人\",\"prop\":\"createByName\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"创建时间\",\"prop\":\"createTime\",\"show\":true,\"fixed\":false,\"sortable\":false}]', null, 'f6589e318b8279c59e00fcd14086028b', null, '0', '2020-07-05 00:56:45.593000', '1', '2020-07-05 02:40:26.783000', '1', null);
INSERT INTO `sys_user_column` VALUES ('1279655436629004290', '1', 'table_sysdic_-sysdic', '[{\"label\":\"字典编号\",\"prop\":\"dicCode\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"字典名称\",\"prop\":\"dicName\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"字典描述\",\"prop\":\"description\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"创建人\",\"prop\":\"createByName\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"创建时间\",\"prop\":\"createTime\",\"show\":true,\"fixed\":false,\"sortable\":false}]', null, 'f6589e318b8279c59e00fcd14086028b', null, '0', '2020-07-05 00:56:45.597000', '1', '2020-07-05 00:56:45.597000', '1', null);
INSERT INTO `sys_user_column` VALUES ('1279655456296095746', '1', 'table_sysdic_item_-sysdic', '[{\"label\":\"编码\",\"prop\":\"dicItemCode\",\"show\":true,\"fixed\":true,\"sortable\":false,\"width\":0},{\"label\":\"名称\",\"prop\":\"dicItemName\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":0},{\"label\":\"值\",\"prop\":\"dicItemValue\",\"show\":true,\"fixed\":false,\"sortable\":false}]', null, 'bb9dc05f9c997e8ba59bdfd7770c57a3', null, '0', '2020-07-05 00:56:50.286000', '1', '2020-07-05 05:01:39.241000', '1', null);
INSERT INTO `sys_user_column` VALUES ('1279665627088994305', '1', 'table_user_-sysuser', '[{\"label\":\"账号\",\"prop\":\"loginName\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"昵称\",\"prop\":\"nickName\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"手机\",\"prop\":\"mobilePhone\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"邮箱\",\"prop\":\"email\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"QQ\",\"prop\":\"qq\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"是否有效\",\"prop\":\"isEnable\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"到期时间\",\"prop\":\"expireTime\",\"show\":true,\"fixed\":false,\"sortable\":false}]', null, '87082ebde2420284fabb636228e0f418', null, '0', '2020-07-05 01:37:15.193000', '1', '2020-07-05 01:37:15.193000', '1', null);
INSERT INTO `sys_user_column` VALUES ('1279665858828484609', '1', 'table_user_-sysmenu', '[{\"label\":\"方法代码\",\"prop\":\"funcCode\",\"show\":true,\"fixed\":false,\"sortable\":true},{\"label\":\"方法名称\",\"prop\":\"funcName\",\"show\":true,\"fixed\":false,\"sortable\":true},{\"label\":\"方法URL\",\"prop\":\"funcUrl\",\"show\":true,\"fixed\":false,\"sortable\":true},{\"label\":\"创建时间\",\"prop\":\"createTime\",\"show\":true,\"fixed\":false,\"sortable\":true},{\"label\":\"创建人\",\"prop\":\"createBy\",\"show\":true,\"fixed\":false,\"sortable\":true}]', null, '513a141ca9030d2a7ac8a185b0340373', null, '0', '2020-07-05 01:38:10.443000', '1', '2020-07-05 01:38:10.443000', '1', null);
INSERT INTO `sys_user_column` VALUES ('1279681605306834945', '1', 'table_user_-sysrole', '[{\"label\":\"角色代码\",\"prop\":\"roleCode\",\"show\":true,\"fixed\":false,\"sortable\":true},{\"label\":\"角色名称\",\"prop\":\"roleName\",\"show\":true,\"fixed\":false,\"sortable\":true},{\"label\":\"创建时间\",\"prop\":\"createTime\",\"show\":true,\"fixed\":false,\"sortable\":true},{\"label\":\"创建人\",\"prop\":\"createBy\",\"show\":true,\"fixed\":false,\"sortable\":true}]', null, '80360c98c20a7f66464f2a34852cf772', null, '0', '2020-07-05 02:40:44.696000', '1', '2020-07-05 02:40:44.696000', '1', null);
INSERT INTO `sys_user_column` VALUES ('1280165038357430273', '1', 'table_user_-syslog', '[{\"label\":\"用户名称\",\"prop\":\"userName\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"IP地址\",\"prop\":\"ip\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"访问URL\",\"prop\":\"url\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"方法描述\",\"prop\":\"description\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"方法\",\"prop\":\"method\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"访问时间\",\"prop\":\"createTime\",\"show\":true,\"fixed\":false,\"sortable\":false}]', null, '9a20f555a1d1ab5c5494ca11886e0641', null, '0', '2020-07-06 10:41:44.117000', '1', '2020-07-07 05:22:37.504000', '1', null);
INSERT INTO `sys_user_column` VALUES ('1281216900867276801', '1', 'table_user_-syssequence', '[{\"label\":\"编码\",\"prop\":\"code\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"名称\",\"prop\":\"name\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"前缀\",\"prop\":\"prefix\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"分隔符\",\"prop\":\"separator\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"日期格式\",\"prop\":\"dateFormat\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"数字长度\",\"prop\":\"numLength\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"初始值\",\"prop\":\"initValue\",\"show\":true,\"fixed\":false,\"sortable\":false}]', null, 'f7617f836d55a2792c581ccefcc2759c', null, '0', '2020-07-09 08:21:27.683000', '1', '2020-07-10 21:28:17.286000', '1', null);
INSERT INTO `sys_user_column` VALUES ('1281773332835905537', '1', 'table_job_-syssequence', '[{\"label\":\"编码\",\"prop\":\"code\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"名称\",\"prop\":\"name\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"前缀\",\"prop\":\"prefix\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"分隔符\",\"prop\":\"separator\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"日期格式\",\"prop\":\"dateFormat\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"数字长度\",\"prop\":\"numLength\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"初始值\",\"prop\":\"initValue\",\"show\":true,\"fixed\":false,\"sortable\":false}]', null, 'f7617f836d55a2792c581ccefcc2759c', null, '0', '2020-07-10 21:12:31.403000', '1', '2020-07-10 21:12:31.403000', '1', null);
INSERT INTO `sys_user_column` VALUES ('1281792019101929474', '1', 'table_user_-sysjob', '[{\"label\":\"任务名称\",\"prop\":\"jobName\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"任务分组\",\"prop\":\"jobGroup\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"任务描述\",\"prop\":\"description\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"类路径\",\"prop\":\"jobClassName\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"执行频率\",\"prop\":\"cronExpression\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"状态\",\"prop\":\"triggerState\",\"show\":true,\"fixed\":false,\"sortable\":false}]', null, '05ba46640ef8206cff291b8cda7b65ce', null, '0', '2020-07-10 22:26:46.555000', '1', '2020-07-10 22:35:13.821000', '1', null);
INSERT INTO `sys_user_column` VALUES ('1283392675998965761', '1', 'table_user_-wflowdefine', '[{\"label\":\"流程定义id\",\"prop\":\"id\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"流程名称\",\"prop\":\"name\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"流程key\",\"prop\":\"key\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"流程分类\",\"prop\":\"category\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"部署ID\",\"prop\":\"deploymentId\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"描述\",\"prop\":\"description\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"版本\",\"prop\":\"version\",\"show\":true,\"fixed\":false,\"sortable\":false}]', null, '22e6c85b24c9d1f2f69fdfa12bf68852', null, '0', '2020-07-15 13:27:12.898000', '1', '2020-07-16 13:18:37.007000', '1', null);
INSERT INTO `sys_user_column` VALUES ('1284113463488024578', '1', 'table_user_-wflowtask', '[{\"label\":\"任务id\",\"prop\":\"id\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"当前签核节点\",\"prop\":\"name\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"当前签核人\",\"prop\":\"assignee\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"流程分类\",\"prop\":\"category\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"业务单据 \",\"prop\":\"businessKey\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"描述\",\"prop\":\"description\",\"show\":true,\"fixed\":false,\"sortable\":false}]', null, '7383a431704d4d8492240bd7c710ed36', null, '0', '2020-07-17 13:11:22.027000', '1', '2020-07-19 02:03:26.009000', '1', null);
INSERT INTO `sys_user_column` VALUES ('1284124009994248193', '1279577615655149569', 'table_user_-wflowtask', '[{\"label\":\"任务id\",\"prop\":\"id\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"当前签核节点\",\"prop\":\"name\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"当前签核人\",\"prop\":\"assignee\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"流程分类\",\"prop\":\"category\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"业务单据 \",\"prop\":\"businessKey\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"描述\",\"prop\":\"description\",\"show\":true,\"fixed\":false,\"sortable\":false}]', null, '7383a431704d4d8492240bd7c710ed36', null, '0', '2020-07-17 13:53:16.510000', '1279577615655149569', '2020-07-19 00:48:12.544000', '1279577615655149569', null);
INSERT INTO `sys_user_column` VALUES ('1284355718417301505', '1', 'table_user_-wflowhistory', '[{\"label\":\"任务id\",\"prop\":\"id\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"签核节点\",\"prop\":\"activityName\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"签核类型\",\"prop\":\"activityType\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"签核人\",\"prop\":\"assignee\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"开始时间\",\"prop\":\"startTime\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"结束时间\",\"prop\":\"endTime\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"拒绝理由\",\"prop\":\"deleteReason\",\"show\":true,\"fixed\":false,\"sortable\":false}]', null, 'c2510070ff77b9e0c1f23b699e8955da', null, '0', '2020-07-18 05:14:00.104000', '1', '2020-07-19 02:03:27.923000', '1', null);
INSERT INTO `sys_user_column` VALUES ('1284421869813944321', '1', 'table_wflowDefine_-wflowdefine', '[{\"label\":\"流程定义id\",\"prop\":\"id\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"流程名称\",\"prop\":\"name\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"流程key\",\"prop\":\"key\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"流程分类\",\"prop\":\"category\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"部署ID\",\"prop\":\"deploymentId\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"描述\",\"prop\":\"description\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"版本\",\"prop\":\"version\",\"show\":true,\"fixed\":false,\"sortable\":false}]', null, '22e6c85b24c9d1f2f69fdfa12bf68852', null, '0', '2020-07-18 09:36:51.825000', '1', '2020-07-18 09:36:51.825000', '1', null);
INSERT INTO `sys_user_column` VALUES ('1284422029369462786', '1', 'table_wflowDefine_-wflowdefine', '[{\"label\":\"流程定义id\",\"prop\":\"id\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"流程名称\",\"prop\":\"name\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"流程key\",\"prop\":\"key\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"流程分类\",\"prop\":\"category\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"部署ID\",\"prop\":\"deploymentId\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"描述\",\"prop\":\"description\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"版本\",\"prop\":\"version\",\"show\":true,\"fixed\":false,\"sortable\":false}]', null, '22e6c85b24c9d1f2f69fdfa12bf68852', null, '0', '2020-07-18 09:37:29.866000', '1', '2020-07-18 09:37:29.866000', '1', null);
INSERT INTO `sys_user_column` VALUES ('1284489185507422210', '1279577615655149569', 'table_wflowDefine_-wflowdefine', '[{\"label\":\"流程定义id\",\"prop\":\"id\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"流程名称\",\"prop\":\"name\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"流程key\",\"prop\":\"key\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"流程分类\",\"prop\":\"category\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"部署ID\",\"prop\":\"deploymentId\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"描述\",\"prop\":\"description\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"版本\",\"prop\":\"version\",\"show\":true,\"fixed\":false,\"sortable\":false}]', null, '22e6c85b24c9d1f2f69fdfa12bf68852', null, '0', '2020-07-18 14:04:21.138000', '1279577615655149569', '2020-07-18 14:04:21.138000', '1279577615655149569', null);
INSERT INTO `sys_user_column` VALUES ('1284490986164117506', '1279577615655149569', 'table_user_-wflowhistory', '[{\"label\":\"任务id\",\"prop\":\"id\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"签核节点\",\"prop\":\"activityName\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"签核类型\",\"prop\":\"activityType\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"签核人\",\"prop\":\"assignee\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"开始时间\",\"prop\":\"startTime\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"结束时间\",\"prop\":\"endTime\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"拒绝理由\",\"prop\":\"deleteReason\",\"show\":true,\"fixed\":false,\"sortable\":false}]', null, 'c2510070ff77b9e0c1f23b699e8955da', null, '0', '2020-07-18 14:11:30.447000', '1279577615655149569', '2020-07-19 00:44:47.584000', '1279577615655149569', null);
INSERT INTO `sys_user_column` VALUES ('1284491342856118274', '1279577615655149569', 'table_user_-sysuser', '[{\"label\":\"账号\",\"prop\":\"loginName\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"昵称\",\"prop\":\"nickName\",\"show\":true,\"fixed\":false,\"sortable\":false,\"width\":200},{\"label\":\"手机\",\"prop\":\"mobilePhone\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"邮箱\",\"prop\":\"email\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"QQ\",\"prop\":\"qq\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"是否有效\",\"prop\":\"isEnable\",\"show\":true,\"fixed\":false,\"sortable\":false},{\"label\":\"到期时间\",\"prop\":\"expireTime\",\"show\":true,\"fixed\":false,\"sortable\":false}]', null, '87082ebde2420284fabb636228e0f418', null, '0', '2020-07-18 14:12:55.489000', '1279577615655149569', '2020-07-18 14:12:55.489000', '1279577615655149569', null);
INSERT INTO `sys_user_column` VALUES ('1285222463810138114', '1279577615655149569', 'table_user_-sysrole', '[{\"label\":\"角色代码\",\"prop\":\"roleCode\",\"show\":true,\"fixed\":false,\"sortable\":true},{\"label\":\"角色名称\",\"prop\":\"roleName\",\"show\":true,\"fixed\":false,\"sortable\":true},{\"label\":\"创建时间\",\"prop\":\"createTime\",\"show\":true,\"fixed\":false,\"sortable\":true},{\"label\":\"创建人\",\"prop\":\"createBy\",\"show\":true,\"fixed\":false,\"sortable\":true}]', null, '80360c98c20a7f66464f2a34852cf772', null, '0', '2020-07-20 14:38:08.309000', '1279577615655149569', '2020-07-20 14:38:08.309000', '1279577615655149569', null);
