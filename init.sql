-- Role: tester
INSERT INTO sys_role (role_name, role_key, sort_order, data_scope, status, deleted)
SELECT '测试员', 'tester', 2, 1, 1, 0
WHERE NOT EXISTS (SELECT 1 FROM sys_role WHERE role_key='tester' AND deleted=0);

-- Bind tester role to all menus/buttons
INSERT IGNORE INTO sys_role_menu(role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r
JOIN sys_menu m ON m.deleted=0 AND m.status=1
WHERE r.role_key='tester' AND r.deleted=0;

-- Create test user zhangsan (password 123456, same hash as admin)
INSERT INTO sys_user (dept_id, username, password, nickname, email, phone, avatar, sex, status, remark, deleted)
SELECT NULL, 'zhangsan',
       '$2a$10$FbJuoHXA.L2G5P0N7I3piuHvHP3kSjG/uCC6.sItpfu3lY7QcAOku',
       '张三', NULL, NULL, NULL, 1, 1, NULL, 0
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE username='zhangsan' AND deleted=0);

-- Bind zhangsan -> tester
INSERT IGNORE INTO sys_user_role(user_id, role_id)
SELECT u.user_id, r.role_id
FROM sys_user u
JOIN sys_role r ON r.role_key='tester' AND r.deleted=0
WHERE u.username='zhangsan' AND u.deleted=0;

-- Dept test data
INSERT INTO sys_dept (parent_id, dept_name, dept_code, leader_id, phone, email, sort_order, status, deleted)
SELECT 0, '总部', 'HQ', NULL, '010-00000000', 'hq@example.com', 1, 1, 0
WHERE NOT EXISTS (SELECT 1 FROM sys_dept WHERE dept_code='HQ' AND deleted=0);

SELECT dept_id INTO @hq_id
FROM sys_dept WHERE dept_code='HQ' AND deleted=0 LIMIT 1;

INSERT INTO sys_dept (parent_id, dept_name, dept_code, leader_id, phone, email, sort_order, status, deleted)
SELECT @hq_id, '人事部', 'HR', NULL, '010-00000001', 'hr@example.com', 1, 1, 0
WHERE NOT EXISTS (SELECT 1 FROM sys_dept WHERE dept_code='HR' AND deleted=0);

INSERT INTO sys_dept (parent_id, dept_name, dept_code, leader_id, phone, email, sort_order, status, deleted)
SELECT @hq_id, '财务部', 'FIN', NULL, '010-00000002', 'fin@example.com', 2, 1, 0
WHERE NOT EXISTS (SELECT 1 FROM sys_dept WHERE dept_code='FIN' AND deleted=0);

INSERT INTO sys_dept (parent_id, dept_name, dept_code, leader_id, phone, email, sort_order, status, deleted)
SELECT @hq_id, '技术部', 'TECH', NULL, '010-00000003', 'tech@example.com', 3, 1, 0
WHERE NOT EXISTS (SELECT 1 FROM sys_dept WHERE dept_code='TECH' AND deleted=0);

INSERT INTO sys_dept (parent_id, dept_name, dept_code, leader_id, phone, email, sort_order, status, deleted)
SELECT @hq_id, '行政部', 'ADMIN', NULL, '010-00000004', 'admin@example.com', 4, 1, 0
WHERE NOT EXISTS (SELECT 1 FROM sys_dept WHERE dept_code='ADMIN' AND deleted=0);
