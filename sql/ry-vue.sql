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

 Date: 30/06/2023 11:08:03
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
) ENGINE = InnoDB AUTO_INCREMENT = 2035 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 5, 'system', NULL, '', 1, 0, 'M', '0', '0', '', 'system', 'admin', '2023-02-09 15:06:46', 'admin', '2023-06-07 09:14:37', '系统管理目录');
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
INSERT INTO `sys_menu` VALUES (2000, 'NC代码转换', 0, 3, 'NCCode', 'system/NCCode/index', NULL, 1, 0, 'C', '0', '0', '', 'edit', 'admin', '2023-02-13 10:24:02', 'admin', '2023-06-06 18:52:08', '');
INSERT INTO `sys_menu` VALUES (2009, '刀具管理查询', 0, 1, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:tools:query', '#', 'admin', '2023-02-14 11:29:35', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2021, '刀具加工参数', 0, 2, 'pocket', 'system/pocket/index', NULL, 1, 0, 'C', '0', '0', 'system:pocket:list', 'example', 'admin', '2023-02-21 09:29:44', 'admin', '2023-06-06 18:52:15', '刀具加工参数菜单');
INSERT INTO `sys_menu` VALUES (2022, '刀具加工参数查询', 2021, 1, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:pocket:query', '#', 'admin', '2023-02-21 09:29:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2023, '刀具加工参数新增', 2021, 2, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:pocket:add', '#', 'admin', '2023-02-21 09:29:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2024, '刀具加工参数修改', 2021, 3, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:pocket:edit', '#', 'admin', '2023-02-21 09:29:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2025, '刀具加工参数删除', 2021, 4, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:pocket:remove', '#', 'admin', '2023-02-21 09:29:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2026, '刀具加工参数导出', 2021, 5, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:pocket:export', '#', 'admin', '2023-02-21 09:29:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2027, 'NC代码转换', 2000, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'system:NcCode:transform', '#', 'admin', '2023-02-23 13:59:06', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2028, '上传NC代码', 2000, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'system:NcCode:upload', '#', 'admin', '2023-02-28 10:11:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2029, '显示已转换NC代码列表', 2000, 3, 'system:NcCode:newTapList', NULL, NULL, 1, 0, 'F', '0', '0', 'system:NcCode:newTapList', '#', 'admin', '2023-03-01 15:35:20', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2030, 'NC代码比较', 2000, 4, '', NULL, NULL, 1, 0, 'F', '0', '0', 'system.NcCode.compare', '#', 'admin', '2023-03-01 15:35:56', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2031, 'NC代码比较', 0, 4, 'NCCompare', 'system/NCCompare/index', NULL, 1, 0, 'C', '0', '0', '', 'log', 'admin', '2023-03-08 17:07:39', 'admin', '2023-06-06 18:52:03', '');
INSERT INTO `sys_menu` VALUES (2032, '上传到dnc', 2000, 5, '', NULL, NULL, 1, 0, 'F', '0', '0', 'system:NcCode:ToDNC', '#', 'admin', '2023-03-22 09:50:53', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2033, '查询Tap文件', 2000, 6, '', NULL, NULL, 1, 0, 'F', '0', '0', 'system:NcCode:checkTap', '#', 'admin', '2023-04-04 10:31:26', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2034, '插入tap文件', 2000, 7, '', NULL, NULL, 1, 0, 'F', '0', '0', 'system:NcCode:insertTap', '#', 'admin', '2023-04-04 13:49:57', '', NULL, '');

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
INSERT INTO `sys_program_number` VALUES (306, 1308);

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
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', 3, '2', 1, 1, '0', '0', 'admin', '2023-02-09 15:06:46', 'admin', '2023-06-30 09:55:25', '普通角色');
INSERT INTO `sys_role` VALUES (101, '工程师', 'Engineer', 2, '1', 0, 1, '0', '0', 'admin', '2023-06-30 09:55:16', '', NULL, NULL);

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
INSERT INTO `sys_role_menu` VALUES (2, 2009);
INSERT INTO `sys_role_menu` VALUES (2, 2021);
INSERT INTO `sys_role_menu` VALUES (2, 2022);
INSERT INTO `sys_role_menu` VALUES (2, 2026);
INSERT INTO `sys_role_menu` VALUES (2, 2027);
INSERT INTO `sys_role_menu` VALUES (2, 2028);
INSERT INTO `sys_role_menu` VALUES (2, 2029);
INSERT INTO `sys_role_menu` VALUES (2, 2030);
INSERT INTO `sys_role_menu` VALUES (2, 2032);
INSERT INTO `sys_role_menu` VALUES (2, 2033);
INSERT INTO `sys_role_menu` VALUES (2, 2034);
INSERT INTO `sys_role_menu` VALUES (101, 2000);
INSERT INTO `sys_role_menu` VALUES (101, 2009);
INSERT INTO `sys_role_menu` VALUES (101, 2021);
INSERT INTO `sys_role_menu` VALUES (101, 2022);
INSERT INTO `sys_role_menu` VALUES (101, 2023);
INSERT INTO `sys_role_menu` VALUES (101, 2024);
INSERT INTO `sys_role_menu` VALUES (101, 2025);
INSERT INTO `sys_role_menu` VALUES (101, 2026);
INSERT INTO `sys_role_menu` VALUES (101, 2027);
INSERT INTO `sys_role_menu` VALUES (101, 2028);
INSERT INTO `sys_role_menu` VALUES (101, 2029);
INSERT INTO `sys_role_menu` VALUES (101, 2030);
INSERT INTO `sys_role_menu` VALUES (101, 2031);
INSERT INTO `sys_role_menu` VALUES (101, 2032);
INSERT INTO `sys_role_menu` VALUES (101, 2033);
INSERT INTO `sys_role_menu` VALUES (101, 2034);

-- ----------------------------
-- Table structure for sys_tap_name
-- ----------------------------
DROP TABLE IF EXISTS `sys_tap_name`;
CREATE TABLE `sys_tap_name`  (
  `tapName` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'tap文件文件名'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_tap_name
-- ----------------------------
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');
INSERT INTO `sys_tap_name` VALUES ('2996430');
INSERT INTO `sys_tap_name` VALUES ('3041743');
INSERT INTO `sys_tap_name` VALUES ('3047867');
INSERT INTO `sys_tap_name` VALUES ('3047868');

-- ----------------------------
-- Table structure for sys_tool_pocket
-- ----------------------------
DROP TABLE IF EXISTS `sys_tool_pocket`;
CREATE TABLE `sys_tool_pocket`  (
  `toolId` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '刀具ID',
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '刀具描述',
  `parameter` json NOT NULL COMMENT '参数',
  PRIMARY KEY (`toolId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_tool_pocket
-- ----------------------------
INSERT INTO `sys_tool_pocket` VALUES ('T002FR', '3.2mm Carbide Drill', '{\"FeedRate\": \"100\", \"PeckDepth\": \"0.5\", \"SpindelSpeed\": \"3500\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T003FR', '4.3mm Carbide Drill', '{\"FeedRate\": \"50\", \"PeckDepth\": \"0.4\", \"SpindelSpeed\": \"3200\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T004FR', '4.8MM CARBIDE DRILL', '{\"FeedRate\": \"300\", \"PeckDepth\": \"1\", \"SpindelSpeed\": \"3113\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T005FR', '5.0mm Carbide Drill', '{\"FeedRate\": \"100\", \"PeckDepth\": \"0.8\", \"SpindelSpeed\": \"3000\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T006FR', '5.1mm Carbide Drill', '{\"FeedRate\": \"100\", \"PeckDepth\": \"0.8\", \"SpindelSpeed\": \"3000\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T007FR', '6.0mm Carbide Drill', '{\"FeedRate\": \"80\", \"PeckDepth\": \"0.8\", \"SpindelSpeed\": \"2500\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T008FR', '3.0mm Carbide Drill', '{\"FeedRate\": \"100\", \"PeckDepth\": \"0.5\", \"SpindelSpeed\": \"3500\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T011FR', '10.0mm Carbide Feedmill R2', '{\"FeedRate\": \"700\", \"PeckDepth\": \"0.3\", \"SpindelSpeed\": \"2500\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T012FR', '5.0mm Carbide Feedmill R0.6', '{\"FeedRate\": \"350\", \"PeckDepth\": \"0.2\", \"SpindelSpeed\": \"6000\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T013FR', '4.0mm Carbide Ballmill Rough HM', '{\"FeedRate\": \"300\", \"PeckDepth\": \"0.2\", \"SpindelSpeed\": \"6000\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T014FR', '4.0mm Carbide Ballmill Straight Flutes', '{\"FeedRate\": \"50\", \"PeckDepth\": \"0.4\", \"SpindelSpeed\": \"2500\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T018FR', '6.0mm Carbide Feedmill R1.5', '{\"FeedRate\": \"700\", \"PeckDepth\": \"0.25\", \"SpindelSpeed\": \"6000\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T020FR', '6.0mm Carbide Endmill Finish', '{\"FeedRate\": \"150\", \"PeckDepth\": \"0\", \"SpindelSpeed\": \"4500\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T021FR', '6.0mm Carbide Ballmill Rough HM', '{\"FeedRate\": \"350\", \"PeckDepth\": \"0.2\", \"SpindelSpeed\": \"6000\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T022FR', '6.0mm Carbide Ballmill Straight Flutes', '{\"FeedRate\": \"60\", \"PeckDepth\": \"0\", \"SpindelSpeed\": \"2500\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T026FR', '6.77mm Carbide Ballmil Straight Flutes', '{\"FeedRate\": \"140\", \"PeckDepth\": \"0\", \"SpindelSpeed\": \"3000\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T028FR', '5.0mm Carbide Ballmill Straight Flutes', '{\"FeedRate\": \"100\", \"PeckDepth\": \"0.8\", \"SpindelSpeed\": \"3000\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T029FR', '7.0mm Carbide Ballmill Straight Flutes', '{\"FeedRate\": \"70\", \"PeckDepth\": \"0\", \"SpindelSpeed\": \"2500\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T030FR', '8.0mm Carbide Ballmill Straight Flutes', '{\"FeedRate\": \"70\", \"PeckDepth\": \"0\", \"SpindelSpeed\": \"1800\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T033FR', '10.0mm Carbide Ballmill Rgh HM', '{\"FeedRate\": \"500\", \"PeckDepth\": \"0.2\", \"SpindelSpeed\": \"4200\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T036FR', '4.77 Carbide Ballmill Straight Flutes', '{\"FeedRate\": \"70\", \"PeckDepth\": \"0.8\", \"SpindelSpeed\": \"3000\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T039FR', '6.0MM CARBIDE STEP DRILL', '{\"FeedRate\": \"120\", \"PeckDepth\": \"0.2\", \"SpindelSpeed\": \"6000\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T041FR', '5.77 Carbide Ballmill Straight Flutes', '{\"FeedRate\": \"50\", \"PeckDepth\": \"0.7\", \"SpindelSpeed\": \"3000\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T042FR', '11.0MM CARBIDE STEP DRILL', '{\"FeedRate\": \"100\", \"PeckDepth\": \"0.2\", \"SpindelSpeed\": \"5500\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T043FR', '13.5mm Sumo-Drill', '{\"FeedRate\": \"80\", \"PeckDepth\": \"1\", \"SpindelSpeed\": \"1100\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T054FR', '9.5MM CARBIDE STEP DRILL', '{\"FeedRate\": \"100\", \"PeckDepth\": \"0.2\", \"SpindelSpeed\": \"5500\"}');
INSERT INTO `sys_tool_pocket` VALUES ('T08F', '3MM CARBIDE DRILL', '{\"FeedRate\": \"100\", \"PeckDepth\": \"0.5\", \"SpindelSpeed\": \"3500\"}');

-- ----------------------------
-- Table structure for sys_tools
-- ----------------------------
DROP TABLE IF EXISTS `sys_tools`;
CREATE TABLE `sys_tools`  (
  `tool_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '刀具id',
  `tool_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '刀具描述',
  `NH6300` bigint(0) NOT NULL COMMENT '刀具在机床6800的位置',
  `NV7000` bigint(0) NOT NULL COMMENT '刀具在机床650的位置',
  `MAZAK655` bigint(0) NOT NULL COMMENT '刀具在机床7000的位置',
  `del_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标准，1删除，0正常',
  PRIMARY KEY (`tool_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 133 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_tools
-- ----------------------------
INSERT INTO `sys_tools` VALUES (1, '25.4mm Indexmill, 3 Ins. x 40 Reach (Iscar)\r\n25.4mm Indexmill, 3 Ins. x 40 Reach (Iscar)\r\n', 1, 1, 1, '0');
INSERT INTO `sys_tools` VALUES (2, '# 5 HSS Long Center Drill', 2, 2, 2, '0');
INSERT INTO `sys_tools` VALUES (3, '# 2 HSS Long Center Drill', 3, 3, 3, '0');
INSERT INTO `sys_tools` VALUES (4, '13.5mm Sumo-Chamdrill Body x 8D', 4, 4, 4, '0');
INSERT INTO `sys_tools` VALUES (5, '6mm Carbide Ballmill Hlx ', 5, 14, 6, '0');
INSERT INTO `sys_tools` VALUES (6, '6mm Carbide Ballmill, 2Fls x 8 F/L (Iscar)', 6, 14, 6, '0');
INSERT INTO `sys_tools` VALUES (7, '10.5mm Sumo-Drill x 5D', 7, 7, 7, '0');
INSERT INTO `sys_tools` VALUES (8, '9.00mm Sumo-Drill x 5D', 8, 8, 8, '0');
INSERT INTO `sys_tools` VALUES (9, '8.70mm Sumo-Drill x 5D', 9, 9, 9, '0');
INSERT INTO `sys_tools` VALUES (10, '6.9 Carbide Drill x 50 F/L (OSG)', 10, 10, 10, '0');
INSERT INTO `sys_tools` VALUES (11, '6.0 Carbide Drill x 44 F/L (OS)', 11, 11, 11, '0');
INSERT INTO `sys_tools` VALUES (12, '5.8mm Carbide Drill x 28 F/L (Mitsubishi)', 12, 300, 300, '0');
INSERT INTO `sys_tools` VALUES (13, '5.1 Carbide Drill x 28 F/L (Dormer)', 13, 13, 13, '0');
INSERT INTO `sys_tools` VALUES (14, '4.8mm Carbide Drill x 39 F/L (OSG)', 14, 301, 301, '0');
INSERT INTO `sys_tools` VALUES (15, '4.3 Carbide Drill x 24 F/L (Guhring)', 15, 15, 15, '0');
INSERT INTO `sys_tools` VALUES (16, '3.0mm Carbide Drill x 33 F/L', 16, 16, 16, '0');
INSERT INTO `sys_tools` VALUES (17, '2.0mm Drill HSS TiN', 17, 17, 17, '0');
INSERT INTO `sys_tools` VALUES (18, '1.6mm Drill HSS TiN', 18, 18, 18, '0');
INSERT INTO `sys_tools` VALUES (19, '7.5mm Carbide Drill x 41 F/L (Guhring)', 19, 302, 302, '0');
INSERT INTO `sys_tools` VALUES (20, 'Empty', 20, 20, 14, '0');
INSERT INTO `sys_tools` VALUES (21, '4.15mm FEEDMILL', 21, 21, 21, '0');
INSERT INTO `sys_tools` VALUES (22, '1.6mm short drill', 22, 22, 18, '0');
INSERT INTO `sys_tools` VALUES (23, '22.0mm DR Drill x 3D', 23, 23, 23, '0');
INSERT INTO `sys_tools` VALUES (24, '10.46mm x 45º Indexable Chamfer', 24, 24, 24, '0');
INSERT INTO `sys_tools` VALUES (25, '12.7mm x 90º MM Chamfer Tool (Iscar)', 25, 25, 25, '0');
INSERT INTO `sys_tools` VALUES (26, '1/2\" Indexable Endmill (Seco)', 26, 26, 26, '0');
INSERT INTO `sys_tools` VALUES (27, '20.0mm MM Carbide CSink Tool', 27, 27, 27, '0');
INSERT INTO `sys_tools` VALUES (28, '25.4 Hi Helix Endmill', 28, 28, 28, '0');
INSERT INTO `sys_tools` VALUES (29, 'Empty', 29, 29, 56, '0');
INSERT INTO `sys_tools` VALUES (30, '5/8\" Indexable Endmill (Seco)', 30, 30, 30, '0');
INSERT INTO `sys_tools` VALUES (31, '12.7mm Endmill, 4Fls x 50mm F/L', 31, 31, 31, '0');
INSERT INTO `sys_tools` VALUES (32, '14.0mm Carbide Slotdrill x 75 F/L', 32, 32, 32, '0');
INSERT INTO `sys_tools` VALUES (33, 'Empty', 33, 20, 56, '0');
INSERT INTO `sys_tools` VALUES (34, '9.53mm Carbide Endmill x 19 F/L', 34, 34, 34, '0');
INSERT INTO `sys_tools` VALUES (35, '18.0mm Locator Form Tool', 35, 35, 35, '0');
INSERT INTO `sys_tools` VALUES (36, '6.0 Carbide Endmill, 4Fs x 13 F/L (Hanita)', 36, 36, 36, '0');
INSERT INTO `sys_tools` VALUES (37, 'Empty', 37, 29, 56, '0');
INSERT INTO `sys_tools` VALUES (38, '5.2mm Carbide Ballmill ', 38, 38, 38, '0');
INSERT INTO `sys_tools` VALUES (39, '4.0mm Carbide EndMill, 3Fs x 6 F/L (Guhring)', 39, 39, 39, '0');
INSERT INTO `sys_tools` VALUES (40, '6.0mm Carbide Drill x 43 F/L (OSG)', 40, 57, 40, '0');
INSERT INTO `sys_tools` VALUES (41, 'Empty', 41, 33, 5, '0');
INSERT INTO `sys_tools` VALUES (42, '6.0mm R1.5 Feedmill  (short)', 42, 303, 303, '0');
INSERT INTO `sys_tools` VALUES (43, '12.2mm ENDMILL ', 43, 43, 56, '0');
INSERT INTO `sys_tools` VALUES (44, '10mm Carbide Ballmill ', 44, 304, 44, '0');
INSERT INTO `sys_tools` VALUES (45, '7.5MM FEEDMILL ', 45, 45, 45, '0');
INSERT INTO `sys_tools` VALUES (46, '7.7MM BallMILL ', 46, 46, 46, '0');
INSERT INTO `sys_tools` VALUES (47, '12.7mm Step Chamfer Tool (Body#008_090)', 47, 47, 47, '0');
INSERT INTO `sys_tools` VALUES (48, '5.2mm FEEDMILL ', 48, 48, 48, '0');
INSERT INTO `sys_tools` VALUES (49, '3.2mm Carbide Ballmill ', 49, 49, 49, '0');
INSERT INTO `sys_tools` VALUES (50, 'Empty', 50, 50, 56, '0');
INSERT INTO `sys_tools` VALUES (51, '4.0mm Carbide Drill x 33 F/L (OSG)', 51, 305, 305, '0');
INSERT INTO `sys_tools` VALUES (52, 'M10x1.5 Spiral M/C Tap (Prototyp)', 52, 52, 52, '0');
INSERT INTO `sys_tools` VALUES (53, 'M8x1.25 Spiral M/C Tap (Prototyp)', 53, 53, 53, '0');
INSERT INTO `sys_tools` VALUES (54, 'M6x1.0 Spiral M/C Tap (Prototyp)', 54, 54, 54, '0');
INSERT INTO `sys_tools` VALUES (55, 'M5x0.8 Spiral M/C Tap (Prototyp)', 55, 55, 55, '0');
INSERT INTO `sys_tools` VALUES (56, 'Empty', 56, 59, 56, '0');
INSERT INTO `sys_tools` VALUES (57, '4mm FeedMill x 13 Reach (Epic)', 57, 306, 306, '0');
INSERT INTO `sys_tools` VALUES (58, '4.3mm FEEDMILL for FLAT HE', 58, 58, 58, '0');
INSERT INTO `sys_tools` VALUES (59, '4.0mm Carbide Ballmill, 2fl x 14 F/L (Epic)', 59, 307, 307, '0');
INSERT INTO `sys_tools` VALUES (60, '9mmx90º chamfer tool', 60, 60, 60, '0');
INSERT INTO `sys_tools` VALUES (61, 'EMpty', 61, 61, 61, '0');
INSERT INTO `sys_tools` VALUES (62, '7.0mm Carbide Ballmill x 12 F/L', 62, 62, 62, '0');
INSERT INTO `sys_tools` VALUES (63, '8.0mm Carbide Drill x 41 F/L (Guhring)', 63, 63, 63, '0');
INSERT INTO `sys_tools` VALUES (64, '8.0mm BALLMILL', 64, 64, 64, '0');
INSERT INTO `sys_tools` VALUES (65, '9.5mm Sumo-Drill x 5D', 65, 19, 19, '0');
INSERT INTO `sys_tools` VALUES (66, '4.2mm Carbide ballmill roughing Ramping', 66, 41, 59, '0');
INSERT INTO `sys_tools` VALUES (67, '1.0mm Carbide Ballmill(ENGRAVING)', 67, 56, 12, '0');
INSERT INTO `sys_tools` VALUES (68, '17.0mm DR Drill x 4D', 68, 68, 68, '0');
INSERT INTO `sys_tools` VALUES (69, '6mm Mitsubishi Carbide Ballmill (ST-T92)', 69, 69, 69, '0');
INSERT INTO `sys_tools` VALUES (70, '4mm Epic Feedmill (ST-T57)', 70, 70, 70, '0');
INSERT INTO `sys_tools` VALUES (71, 'Empty', 71, 71, 71, '0');
INSERT INTO `sys_tools` VALUES (72, '3\" Mitsubishi Face Cutter', 72, 72, 72, '0');
INSERT INTO `sys_tools` VALUES (73, '6.0mm R1.5 Feedmill  (Long)', 73, 73, 73, '0');
INSERT INTO `sys_tools` VALUES (74, 'Empty', 74, 74, 74, '0');
INSERT INTO `sys_tools` VALUES (75, '9.5mm Carbide Drill x 47 F/L (Guhring)', 75, 75, 75, '0');
INSERT INTO `sys_tools` VALUES (76, '6.9mm Carbide Drill (ST-T10)', 76, 76, 76, '0');
INSERT INTO `sys_tools` VALUES (77, '19.05mm Carbide Endmill x 38 F/L', 77, 37, 37, '0');
INSERT INTO `sys_tools` VALUES (78, 'Empty', 78, 78, 78, '0');
INSERT INTO `sys_tools` VALUES (79, '4mm Carbide Ballmill Hlx Flutes 25.0mm Reach', 79, 79, 79, '0');
INSERT INTO `sys_tools` VALUES (80, '4.2m Epic Carbide Ballmill (ST-T58)', 80, 80, 80, '0');
INSERT INTO `sys_tools` VALUES (81, '#1 HSS Center Drill', 81, 12, 29, '0');
INSERT INTO `sys_tools` VALUES (82, '20.0mm MM 60 DEG Carbide CSink Tool', 82, 82, 82, '0');
INSERT INTO `sys_tools` VALUES (83, 'M12 x 1.75 Spiral M/C Tap (Prototyp)', 83, 51, 51, '0');
INSERT INTO `sys_tools` VALUES (84, '19.05mm Indexmill, 3 Ins. X 24 Reach (Iscar)', 84, 40, 33, '0');
INSERT INTO `sys_tools` VALUES (85, '25.4mm Carbide Endmill x 76 F/L', 85, 85, 85, '0');
INSERT INTO `sys_tools` VALUES (86, '11.5mm Sumo-Drill x 5D', 86, 5, 86, '0');
INSERT INTO `sys_tools` VALUES (87, '8.0mm Carbide Ballmill, 2Fs x 20 F/L', 87, 87, 87, '0');
INSERT INTO `sys_tools` VALUES (88, '11.0mm Carbide Drill 55 F/L', 88, 6, 50, '0');
INSERT INTO `sys_tools` VALUES (89, '10.mm Carbide Drill ', 89, 89, 89, '0');
INSERT INTO `sys_tools` VALUES (90, '6.75mm Carbide Ballmill x 13 F/L (For 7mm HE)', 90, 90, 90, '0');
INSERT INTO `sys_tools` VALUES (91, '3.2MM DRILL', 91, 91, 91, '0');
INSERT INTO `sys_tools` VALUES (92, '6.0mm Mitsubishi Carbide Ballmill', 92, 92, 92, '0');
INSERT INTO `sys_tools` VALUES (93, 'Coolant Jet 2mm Gate', 93, 93, 93, '0');
INSERT INTO `sys_tools` VALUES (94, '4.0mm Carbide Endmill x 11mm F/L', 94, 39, 39, '0');
INSERT INTO `sys_tools` VALUES (95, '6.5mm Carbide Drill x 41 F/L (Guhring)', 95, 95, 95, '0');
INSERT INTO `sys_tools` VALUES (96, '5.0mm Carbide Drill x 28 F/L (Guhring)', 96, 44, 22, '0');
INSERT INTO `sys_tools` VALUES (97, '5.5mm Carbide Ballmill , 2Fs x 22 F/L( Roughing)', 97, 97, 97, '0');
INSERT INTO `sys_tools` VALUES (98, '5.5 Carbide Ballmill, 2Fs x 22 F/L (Finishing)', 98, 98, 98, '0');
INSERT INTO `sys_tools` VALUES (99, '5.0mm x 12\" OAL Gundrill (Botek)', 99, 99, 99, '0');
INSERT INTO `sys_tools` VALUES (100, 'PROBE', 100, 100, 100, '0');
INSERT INTO `sys_tools` VALUES (101, 'PROBE', 101, 101, 101, '0');
INSERT INTO `sys_tools` VALUES (102, '25.4 Mitsubishi Feedmill R1.5 x 69 Reach', 102, 102, 102, '0');
INSERT INTO `sys_tools` VALUES (103, '25.4 Mitsubishi Feedmill (ST-T102)', 103, 103, 103, '0');
INSERT INTO `sys_tools` VALUES (104, '19.05 Mitsubishi Feedmill R1.5 x 60 Reach', 104, 104, 104, '0');
INSERT INTO `sys_tools` VALUES (105, '19.05 Mitsubishi Feedmill (ST-T104)', 105, 105, 105, '0');
INSERT INTO `sys_tools` VALUES (106, '15.875 Carbide Finishred x 31 F/L', 106, 106, 106, '0');
INSERT INTO `sys_tools` VALUES (107, '15.875 Carbide Finishred (ST-T106)', 107, 107, 107, '0');
INSERT INTO `sys_tools` VALUES (108, '90º x 6.35mm Shank Chamfer Tool', 108, 60, 60, '0');
INSERT INTO `sys_tools` VALUES (109, '9.5mm x 15\" OAL GunDrill (Botek)', 109, 109, 109, '0');
INSERT INTO `sys_tools` VALUES (110, '19.05mm Indexmill (ST-T84)', 110, 110, 110, '0');
INSERT INTO `sys_tools` VALUES (111, '4.0 Carbide ballmill (ST-T59)', 111, 111, 111, '0');
INSERT INTO `sys_tools` VALUES (112, '15.88mm DR Drill x 4D', 112, 112, 112, '0');
INSERT INTO `sys_tools` VALUES (113, '19.05 ISCAR Indexable endmill (ST-T84)', 113, 113, 113, '0');
INSERT INTO `sys_tools` VALUES (114, '5.0mm Carbide EndMill (ST-T118)', 114, 114, 114, '0');
INSERT INTO `sys_tools` VALUES (115, '5.2mm Carbide Ballmill (ST-T119)', 115, 115, 115, '0');
INSERT INTO `sys_tools` VALUES (116, '11/64 Carbide Ballmill Ramping (ST-T66)', 116, 116, 116, '0');
INSERT INTO `sys_tools` VALUES (117, '4.0mm Carbide End Mill (ST-T39)', 117, 117, 117, '0');
INSERT INTO `sys_tools` VALUES (118, '5.0mm Carbide EndMill, 3fltx 10 F/L', 118, 118, 118, '0');
INSERT INTO `sys_tools` VALUES (119, '5.2mm Carbide Ballmill, 2Fs x 13 F/L (Guhring)', 119, 119, 119, '0');
INSERT INTO `sys_tools` VALUES (120, '20.00mm Carbide Finishred x 42 F/L', 120, 120, 120, '0');
INSERT INTO `sys_tools` VALUES (121, '7.5MM FEEDMILL ', 121, 121, 121, '0');
INSERT INTO `sys_tools` VALUES (122, '7.7MM BALLMILL ', 122, 122, 122, '0');
INSERT INTO `sys_tools` VALUES (123, '8.2MM BALLMILL ', 123, 123, 123, '0');
INSERT INTO `sys_tools` VALUES (124, 'Empty', 124, 124, 124, '0');
INSERT INTO `sys_tools` VALUES (125, 'Empty', 125, 125, 125, '0');
INSERT INTO `sys_tools` VALUES (126, 'Empty', 126, 126, 126, '0');
INSERT INTO `sys_tools` VALUES (127, 'Empty', 127, 127, 127, '0');
INSERT INTO `sys_tools` VALUES (128, 'Empty', 128, 128, 128, '0');
INSERT INTO `sys_tools` VALUES (129, 'Empty', 129, 129, 129, '0');
INSERT INTO `sys_tools` VALUES (130, 'Empty', 130, 130, 130, '0');
INSERT INTO `sys_tools` VALUES (131, '1.0MM CARBIDE BALLMILL(MMC)', 131, 56, 12, '0');

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
INSERT INTO `sys_user` VALUES (1, 'admin', '马斯特', '00', 'master@163.com', '15888888888', '1', '/profile/avatar/2023/02/17/blob_20230217171420A001.png', '$2a$10$Tf8Er56RukTQTbfXfbSE8eaelVwHOwLb2RJh8U4/54V1onyW8Azsi', '0', '0', '127.0.0.1', '2023-06-30 11:01:07', 'admin', '2023-02-09 15:06:46', '', '2023-06-30 11:01:07', '管理员');
INSERT INTO `sys_user` VALUES (2, 'common', '马斯特', '00', 'master@163.com', '15666666666', '1', '', '$2a$10$T3aBr2PuGwx3e2e9DAljb.5MPwxBNZ/3w6hm.Xqtl06uljVNlw/ma', '0', '0', '127.0.0.1', '2023-06-07 09:51:30', 'admin', '2023-02-09 15:06:46', 'admin', '2023-06-07 09:51:29', '测试员');
INSERT INTO `sys_user` VALUES (101, 'JCHu', '胡工', '00', '18888888888@qq.com', '18888888888', '0', '', '$2a$10$JL1ZHB9hAGTVM18H3nzeDOuYRrO0p5/EU0TnS1DnHnYXGoTVS.Tue', '0', '0', '127.0.0.1', '2023-06-30 10:13:08', 'admin', '2023-06-30 09:21:35', 'admin', '2023-06-30 10:13:08', NULL);
INSERT INTO `sys_user` VALUES (102, 'dz', 'DZ', '00', '18888888888@qq.com', '18888888888', '0', '', '$2a$10$sVnbcEPvj0LwsMuih.eUWeuV9eMiaTMRYeXbxwS1Xlv7cc75jZg0u', '0', '2', '', NULL, 'admin', '2023-06-30 09:52:34', 'admin', '2023-06-30 09:53:02', NULL);
INSERT INTO `sys_user` VALUES (103, '用户', 'users', '00', '18888888888@qq.com', '18888888888', '0', '', '$2a$10$JYgQ8PgVbL9Hlpd0/Wc/wO8wSuZAIkOUz9GYD7E.WJsPHqDwSuM9q', '0', '2', '', NULL, 'admin', '2023-06-30 10:57:12', '', NULL, NULL);
INSERT INTO `sys_user` VALUES (104, 'Users', '测试用户', '00', '18888888888@qq.com', '18888888888', '0', '', '$2a$10$9nCobmgzfgUMIOxvGfh0B.nJm6IKxfuLKOzhdUKMQa7fzJ8fhZVI.', '0', '0', '127.0.0.1', '2023-06-30 10:57:53', 'admin', '2023-06-30 10:57:41', '', '2023-06-30 10:57:53', NULL);
INSERT INTO `sys_user` VALUES (105, 'dz', 'dz', '00', '18888888888@qq.com', '18888888888', '0', '', '$2a$10$zkmo6eBUv6jXHYFldgj3COp6.II5IwQz.I7UBf7ClAaIWWprMg4wG', '0', '0', '127.0.0.1', '2023-06-30 11:01:29', 'admin', '2023-06-30 11:01:20', '', '2023-06-30 11:01:28', NULL);

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
INSERT INTO `sys_user_role` VALUES (101, 101);
INSERT INTO `sys_user_role` VALUES (104, 101);
INSERT INTO `sys_user_role` VALUES (105, 101);

SET FOREIGN_KEY_CHECKS = 1;
