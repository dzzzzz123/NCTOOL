/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : ry-vue

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 08/03/2023 09:13:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `config_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '参数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-purple', 'Y', 'admin', '2023-02-09 15:06:46', 'admin', '2023-02-09 17:03:25', '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO `sys_config` VALUES (2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2023-02-09 15:06:46', '', NULL, '初始化密码 123456');
INSERT INTO `sys_config` VALUES (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-light', 'Y', 'admin', '2023-02-09 15:06:46', 'admin', '2023-02-09 17:04:36', '深色主题theme-dark，浅色主题theme-light');
INSERT INTO `sys_config` VALUES (4, '账号自助-验证码开关', 'sys.account.captchaEnabled', 'false', 'Y', 'admin', '2023-02-09 15:06:46', '', NULL, '是否开启验证码功能（true开启，false关闭）');
INSERT INTO `sys_config` VALUES (5, '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'false', 'Y', 'admin', '2023-02-09 15:06:46', '', NULL, '是否开启注册用户功能（true开启，false关闭）');

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(0) NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '性别男');
INSERT INTO `sys_dict_data` VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '性别女');
INSERT INTO `sys_dict_data` VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '性别未知');
INSERT INTO `sys_dict_data` VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '显示菜单');
INSERT INTO `sys_dict_data` VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '隐藏菜单');
INSERT INTO `sys_dict_data` VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '默认分组');
INSERT INTO `sys_dict_data` VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '系统分组');
INSERT INTO `sys_dict_data` VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '系统默认是');
INSERT INTO `sys_dict_data` VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '系统默认否');
INSERT INTO `sys_dict_data` VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '通知');
INSERT INTO `sys_dict_data` VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '公告');
INSERT INTO `sys_dict_data` VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '关闭状态');
INSERT INTO `sys_dict_data` VALUES (18, 99, '其他', '0', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '其他操作');
INSERT INTO `sys_dict_data` VALUES (19, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '新增操作');
INSERT INTO `sys_dict_data` VALUES (20, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '修改操作');
INSERT INTO `sys_dict_data` VALUES (21, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '删除操作');
INSERT INTO `sys_dict_data` VALUES (22, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '授权操作');
INSERT INTO `sys_dict_data` VALUES (23, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '导出操作');
INSERT INTO `sys_dict_data` VALUES (24, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '导入操作');
INSERT INTO `sys_dict_data` VALUES (25, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '强退操作');
INSERT INTO `sys_dict_data` VALUES (26, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '生成操作');
INSERT INTO `sys_dict_data` VALUES (27, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '清空操作');
INSERT INTO `sys_dict_data` VALUES (28, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (29, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '停用状态');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '用户性别列表');
INSERT INTO `sys_dict_type` VALUES (2, '菜单状态', 'sys_show_hide', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '菜单状态列表');
INSERT INTO `sys_dict_type` VALUES (3, '系统开关', 'sys_normal_disable', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '系统开关列表');
INSERT INTO `sys_dict_type` VALUES (4, '任务状态', 'sys_job_status', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '任务状态列表');
INSERT INTO `sys_dict_type` VALUES (5, '任务分组', 'sys_job_group', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '任务分组列表');
INSERT INTO `sys_dict_type` VALUES (6, '系统是否', 'sys_yes_no', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '系统是否列表');
INSERT INTO `sys_dict_type` VALUES (7, '通知类型', 'sys_notice_type', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '通知类型列表');
INSERT INTO `sys_dict_type` VALUES (8, '通知状态', 'sys_notice_status', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '通知状态列表');
INSERT INTO `sys_dict_type` VALUES (9, '操作类型', 'sys_oper_type', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '操作类型列表');
INSERT INTO `sys_dict_type` VALUES (10, '系统状态', 'sys_common_status', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '登录状态列表');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(0) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(0) NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由参数',
  `is_frame` int(0) NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int(0) NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2031 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, 'system', NULL, '', 1, 0, 'M', '0', '0', '', 'system', 'admin', '2023-02-09 15:06:46', '', NULL, '系统管理目录');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', '', 1, 0, 'C', '0', '0', 'system:user:list', 'user', 'admin', '2023-02-09 15:06:46', '', NULL, '用户管理菜单');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', '', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples', 'admin', '2023-02-09 15:06:46', '', NULL, '角色管理菜单');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', '', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', '2023-02-09 15:06:46', '', NULL, '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (1000, '用户查询', 100, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:user:query', '#', 'admin', '2023-02-09 15:06:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1001, '用户新增', 100, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:user:add', '#', 'admin', '2023-02-09 15:06:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1002, '用户修改', 100, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit', '#', 'admin', '2023-02-09 15:06:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1003, '用户删除', 100, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove', '#', 'admin', '2023-02-09 15:06:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1004, '用户导出', 100, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:user:export', '#', 'admin', '2023-02-09 15:06:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1005, '用户导入', 100, 6, '', '', '', 1, 0, 'F', '0', '0', 'system:user:import', '#', 'admin', '2023-02-09 15:06:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1006, '重置密码', 100, 7, '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', '2023-02-09 15:06:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1007, '角色查询', 101, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:role:query', '#', 'admin', '2023-02-09 15:06:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1008, '角色新增', 101, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:role:add', '#', 'admin', '2023-02-09 15:06:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1009, '角色修改', 101, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit', '#', 'admin', '2023-02-09 15:06:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1010, '角色删除', 101, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', 'admin', '2023-02-09 15:06:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1011, '角色导出', 101, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:role:export', '#', 'admin', '2023-02-09 15:06:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1012, '菜单查询', 102, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query', '#', 'admin', '2023-02-09 15:06:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1013, '菜单新增', 102, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add', '#', 'admin', '2023-02-09 15:06:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1014, '菜单修改', 102, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit', '#', 'admin', '2023-02-09 15:06:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1015, '菜单删除', 102, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', 'admin', '2023-02-09 15:06:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2000, 'NC代码', 0, 4, 'NCCode', 'system/NCCode/index', NULL, 1, 0, 'C', '0', '0', '', 'edit', 'admin', '2023-02-13 10:24:02', 'admin', '2023-02-21 09:44:30', '');
INSERT INTO `sys_menu` VALUES (2008, '刀具管理', 0, 2, 'tools', 'system/tools/index', NULL, 1, 0, 'C', '0', '0', 'system:tools:list', 'dashboard', 'admin', '2023-02-14 11:29:35', 'admin', '2023-02-17 15:13:31', '刀具管理菜单');
INSERT INTO `sys_menu` VALUES (2009, '刀具管理查询', 2008, 1, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:tools:query', '#', 'admin', '2023-02-14 11:29:35', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2010, '刀具管理新增', 2008, 2, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:tools:add', '#', 'admin', '2023-02-14 11:29:35', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2011, '刀具管理修改', 2008, 3, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:tools:edit', '#', 'admin', '2023-02-14 11:29:35', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2012, '刀具管理删除', 2008, 4, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:tools:remove', '#', 'admin', '2023-02-14 11:29:35', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2013, '刀具管理导出', 2008, 5, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:tools:export', '#', 'admin', '2023-02-14 11:29:35', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2021, '刀具加工参数', 0, 3, 'pocket', 'system/pocket/index', NULL, 1, 0, 'C', '0', '0', 'system:pocket:list', 'example', 'admin', '2023-02-21 09:29:44', 'admin', '2023-02-21 09:33:52', '刀具加工参数菜单');
INSERT INTO `sys_menu` VALUES (2022, '刀具加工参数查询', 2021, 1, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:pocket:query', '#', 'admin', '2023-02-21 09:29:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2023, '刀具加工参数新增', 2021, 2, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:pocket:add', '#', 'admin', '2023-02-21 09:29:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2024, '刀具加工参数修改', 2021, 3, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:pocket:edit', '#', 'admin', '2023-02-21 09:29:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2025, '刀具加工参数删除', 2021, 4, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:pocket:remove', '#', 'admin', '2023-02-21 09:29:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2026, '刀具加工参数导出', 2021, 5, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:pocket:export', '#', 'admin', '2023-02-21 09:29:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2027, 'NC代码转换', 2000, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'system:NcCode:transform', '#', 'admin', '2023-02-23 13:59:06', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2028, '上传NC代码', 2000, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'system:NcCode:upload', '#', 'admin', '2023-02-28 10:11:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2029, '显示已转换NC代码列表', 2000, 3, 'system:NcCode:newTapList', NULL, NULL, 1, 0, 'F', '0', '0', 'system:NcCode:newTapList', '#', 'admin', '2023-03-01 15:35:20', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2030, 'NC代码比较', 2000, 4, '', NULL, NULL, 1, 0, 'F', '0', '0', 'system.NcCode.compare', '#', 'admin', '2023-03-01 15:35:56', '', NULL, '');

-- ----------------------------
-- Table structure for sys_program_number
-- ----------------------------
DROP TABLE IF EXISTS `sys_program_number`;
CREATE TABLE `sys_program_number`  (
  `number_one` bigint(0) NOT NULL COMMENT '加工图参数1',
  `number_two` int(0) NOT NULL COMMENT '加工图参数2'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_program_number
-- ----------------------------
INSERT INTO `sys_program_number` VALUES (306, 2);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(0) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '部门树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '1', 1, 1, '0', '0', 'admin', '2023-02-09 15:06:46', '', NULL, '超级管理员');
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', 3, '2', 1, 1, '0', '0', 'admin', '2023-02-09 15:06:46', 'admin', '2023-03-03 09:57:46', '普通角色');
INSERT INTO `sys_role` VALUES (100, '测试角色', 'test', 2, '1', 1, 1, '0', '2', 'admin', '2023-02-09 16:54:10', 'admin', '2023-02-14 14:59:42', '无');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(0) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 2000);
INSERT INTO `sys_role_menu` VALUES (2, 2008);
INSERT INTO `sys_role_menu` VALUES (2, 2009);
INSERT INTO `sys_role_menu` VALUES (2, 2013);
INSERT INTO `sys_role_menu` VALUES (2, 2021);
INSERT INTO `sys_role_menu` VALUES (2, 2022);
INSERT INTO `sys_role_menu` VALUES (2, 2026);
INSERT INTO `sys_role_menu` VALUES (2, 2027);
INSERT INTO `sys_role_menu` VALUES (2, 2028);
INSERT INTO `sys_role_menu` VALUES (2, 2029);
INSERT INTO `sys_role_menu` VALUES (2, 2030);

-- ----------------------------
-- Table structure for sys_tool_pocket
-- ----------------------------
DROP TABLE IF EXISTS `sys_tool_pocket`;
CREATE TABLE `sys_tool_pocket`  (
  `tool_pocket_id` bigint(0) NOT NULL COMMENT '刀具加工参数id',
  `toolId` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '刀具ID',
  `feat_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '特征名称',
  `parameter` json NOT NULL COMMENT '参数',
  PRIMARY KEY (`tool_pocket_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_tool_pocket
-- ----------------------------
INSERT INTO `sys_tool_pocket` VALUES (6924, 'T004FR', 'DRILL_5MM_TH', '{\"Coolant\": \"FLOOD\", \"FeedRate\": \"330\", \"PeckDepth\": \"1\", \"SpindelSpeed\": \"34240\"}');
INSERT INTO `sys_tool_pocket` VALUES (6965, 'T013FR', 'RGH_SPRUE_RN_RND_PL', '{\"Coolant\": \"FLOOD\", \"FeedRate\": \"1000\", \"PeckDepth\": \"0.2\", \"SpindelSpeed\": \"6000\"}');
INSERT INTO `sys_tool_pocket` VALUES (6966, 'T022FR', 'FIN_6MM_SPRUE_RN_RND_PL', '{\"Coolant\": \"FLOOD\", \"FeedRate\": \"132\", \"PeckDepth\": \"0\", \"SpindelSpeed\": \"3850\"}');
INSERT INTO `sys_tool_pocket` VALUES (7024, 'T042FR', 'DRILL_VALUE_RUN_S2', '{\"Coolant\": \"FLOOD\", \"FeedRate\": \"149\", \"PeckDepth\": \"1.5\", \"SpindelSpeed\": \"1494\"}');
INSERT INTO `sys_tool_pocket` VALUES (7044, 'T042FR', 'DRILL_VALUE_RUN_S1', '{\"Coolant\": \"FLOOD\", \"FeedRate\": \"149\", \"PeckDepth\": \"1.5\", \"SpindelSpeed\": \"1494\"}');
INSERT INTO `sys_tool_pocket` VALUES (7063, 'T021FR', 'RGH_SPRUE_RN_RND_PL_S2', '{\"Coolant\": \"FLOOD\", \"FeedRate\": \"1000\", \"PeckDepth\": \"0.2\", \"SpindelSpeed\": \"6000\"}');
INSERT INTO `sys_tool_pocket` VALUES (7103, 'T011FR', 'RGH_CB_S', '{\"Coolant\": \"FLOOD\", \"FeedRate\": \"2750\", \"PeckDepth\": \"0.3\", \"SpindelSpeed\": \"2000\"}');

-- ----------------------------
-- Table structure for sys_tools
-- ----------------------------
DROP TABLE IF EXISTS `sys_tools`;
CREATE TABLE `sys_tools`  (
  `tool_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '刀具id',
  `tool_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '刀具描述',
  `pocket_6800` bigint(0) NOT NULL COMMENT '刀具在机床6800的位置',
  `pocket_650` bigint(0) NOT NULL COMMENT '刀具在机床650的位置',
  `pocket_7000` bigint(0) NOT NULL COMMENT '刀具在机床7000的位置',
  `pocket_haas` bigint(0) NOT NULL COMMENT '刀具在机床haas的位置',
  `tool_sap` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '刀具参数',
  `del_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标准，1删除，0正常',
  PRIMARY KEY (`tool_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_tools
-- ----------------------------
INSERT INTO `sys_tools` VALUES (1, '25.4mm INdexmill,3 Ins.x 30 Reach (Iscar)', 1, 1, 1, 27, '.008.346', '0');
INSERT INTO `sys_tools` VALUES (2, '# 5 HSS Long Center Drill', 2, 2, 2, 38, '.002.661', '0');
INSERT INTO `sys_tools` VALUES (3, '# 2 HSS Long Center Drill', 3, 3, 3, 7, '.002.256', '0');
INSERT INTO `sys_tools` VALUES (4, '13.5mm Sumo-Chamdrill Body x 8D', 4, 4, 4, 38, '008.361', '0');
INSERT INTO `sys_tools` VALUES (5, '6mm Carbide Ballmill Hlx Flutes 39mm Reach', 5, 16, 16, 37, '3787', '0');
INSERT INTO `sys_tools` VALUES (6, '6mm Carbide Ballmill 2Fls x 8 F/L (Iscar)', 6, 41, 41, 36, '.003.336', '0');
INSERT INTO `sys_tools` VALUES (7, '10.5mm Sumo-Drill x 5D', 7, 7, 7, 38, '.008.398', '0');
INSERT INTO `sys_tools` VALUES (8, '9.00mm Sumo-Drill x 5D', 8, 8, 8, 38, '008.396', '0');
INSERT INTO `sys_tools` VALUES (9, '8.70mm Sumo-Drill x 5D', 9, 9, 9, 25, '008.395', '0');
INSERT INTO `sys_tools` VALUES (10, '6.9 Carbide Drill x 50 F/L (OSG)', 10, 10, 10, 8, '.002.656', '0');
INSERT INTO `sys_tools` VALUES (11, '6.0 Carbide Drill x 33 F/L (OS)', 11, 11, 11, 3, '002.359', '0');
INSERT INTO `sys_tools` VALUES (12, '5.8mm Carbide Drill x 28 F/L (Mitsubishi)', 12, 12, 12, 32, '002.641', '0');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '马斯特', '00', 'master@163.com', '15888888888', '1', '/profile/avatar/2023/02/17/blob_20230217171420A001.png', '$2a$10$3gzBd0qcsq4t8E5IdOg4p.RT6cUGWwMZZxiU5xVPuTPsnFcQrKTTu', '0', '0', '127.0.0.1', '2023-03-07 10:41:56', 'admin', '2023-02-09 15:06:46', '', '2023-03-07 10:41:56', '管理员');
INSERT INTO `sys_user` VALUES (2, 'dz', '马斯特', '00', 'master@163.com', '15666666666', '1', '', '$2a$10$43zeusYlA2mLiGEWq9dla.P1JYfQRmWMegYq6hEfrGSv815o8qdYe', '0', '0', '127.0.0.1', '2023-03-03 09:58:57', 'admin', '2023-02-09 15:06:46', 'admin', '2023-03-03 09:58:57', '测试员');
INSERT INTO `sys_user` VALUES (100, 'test', 'test', '00', '', '', '0', '', '$2a$10$e1IF.ZBrDz/XUfYkt9U98.3rFulUe.c6W9JgvVU.ctomk0yQ1x8HO', '0', '2', '127.0.0.1', '2023-02-16 09:27:19', 'admin', '2023-02-13 13:44:12', '', '2023-02-16 09:27:18', NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);

SET FOREIGN_KEY_CHECKS = 1;
