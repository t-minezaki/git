CREATE TABLE sys_menu (
  menu_id bigserial,
  parent_id int8,
  name varchar(50),
  url varchar(200),
  perms varchar(500),
  type int,
  icon varchar(50),
  order_num int,
  PRIMARY KEY (menu_id)
);

CREATE TABLE sys_user_token (
  user_id bigserial,
  token varchar(100) NOT NULL,
  expire_time timestamp,
  update_time timestamp,
  PRIMARY KEY (user_id),
  UNIQUE (token)
);

CREATE TABLE sys_captcha (
  uuid varchar(36) NOT NULL,
  code varchar(6) NOT NULL,
  expire_time timestamp,
  PRIMARY KEY (uuid)
);

CREATE TABLE sys_role (
  role_id bigserial,
  role_name varchar(100),
  remark varchar(100),
  create_user_id int8,
  create_time timestamp,
  PRIMARY KEY (role_id)
);

CREATE TABLE sys_user_role (
  id bigserial,
  user_id int8,
  role_id int8,
  PRIMARY KEY (id)
);

CREATE TABLE sys_role_menu (
  id bigserial,
  role_id int8,
  menu_id int8,
  PRIMARY KEY (id)
);


CREATE TABLE sys_log (
  id bigserial,
  username varchar(50),
  operation varchar(50),
  method varchar(200),
  params varchar(5000),
  time int8 NOT NULL,
  ip varchar(64),
  create_date timestamp,
  PRIMARY KEY (id)
);

--INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (1, 0, 'システム', NULL, NULL, 0, 'system', 0);
--INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (2, 1, 'ユーザー管理', 'sys/user', NULL, 1, 'admin', 1);
--INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (3, 1, '役割管理', 'sys/role', NULL, 1, 'role', 2);
--INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (4, 1, 'メニュー管理', 'sys/menu', NULL, 1, 'menu', 3);
--INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (5, 1, 'ログ管理', 'sys/log', 'sys:log:list', 1, 'log', 4);
--INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (6, 0, 'student管理', 'student/F10301.html', 'student:*', 1, 'admin', 5);
--INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (7, 0, 'mentor管理', 'mentor/F20001.html', 'mentor:*', 1, 'admin', 6);
--INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (8, 0, 'guard管理', 'guard/F30001.html', 'guard:*', 1, 'admin', 7);


--INSERT INTO "public"."sys_role"("role_id", "role_name", "remark", "create_user_id", "create_time") VALUES (1, 'mentor', 'メンター', 1, '2018-11-13 00:00:00');
--INSERT INTO "public"."sys_role"("role_id", "role_name", "remark", "create_user_id", "create_time") VALUES (2, 'student', '生徒', 1, '2018-11-13 00:00:00');
--INSERT INTO "public"."sys_role"("role_id", "role_name", "remark", "create_user_id", "create_time") VALUES (3, 'guard', '保護者', 1, '2018-11-13 00:00:00');
--INSERT INTO "public"."sys_role"("role_id", "role_name", "remark", "create_user_id", "create_time") VALUES (4, 'admin', '学研本部', 1, '2018-11-13 00:00:00');


--INSERT INTO "public"."sys_role_menu"("id", "role_id", "menu_id") VALUES (1, 1, 1);
--INSERT INTO "public"."sys_role_menu"("id", "role_id", "menu_id") VALUES (2, 1, 2);
--INSERT INTO "public"."sys_role_menu"("id", "role_id", "menu_id") VALUES (3, 1, 3);
--INSERT INTO "public"."sys_role_menu"("id", "role_id", "menu_id") VALUES (4, 1, 4);
--INSERT INTO "public"."sys_role_menu"("id", "role_id", "menu_id") VALUES (5, 1, 5);
--INSERT INTO "public"."sys_role_menu"("id", "role_id", "menu_id") VALUES (6, 2, 6);
--INSERT INTO "public"."sys_role_menu"("id", "role_id", "menu_id") VALUES (7, 3, 7);
--INSERT INTO "public"."sys_role_menu"("id", "role_id", "menu_id") VALUES (8, 4, 8);

--INSERT INTO "public"."sys_user_role"("id", "user_id", "role_id") VALUES (1, 1, 1);
--INSERT INTO "public"."sys_user_role"("id", "user_id", "role_id") VALUES (2, 2, 2);
--INSERT INTO "public"."sys_user_role"("id", "user_id", "role_id") VALUES (3, 3, 3);
--INSERT INTO "public"."sys_user_role"("id", "user_id", "role_id") VALUES (4, 4, 4);

--psw student/student
--INSERT INTO "public"."mst_usr"("id", "usr_id", "usr_password", "fst_login_flg", "usr_nm", "role_div", "org_id", "pw_deadl_day", "usr_sts", "spec_auth_flg", "after_usr_id", "cret_datime", "cret_usr_id", "upd_datime", "upd_usr_id", "del_flg") VALUES (1, 'admin', 'bf9e46e9b962c6b425f0685626cd64bb45febfac300c4b0322d0586954c20a26', '1', 'テスト　太郎１', '60', 'GKGP0001', '2020-11-13', '1', '0', 'admin', '2018-11-13 00:00:00', 'systemtest', '2018-11-13 00:00:00', 'systemtest', 0);
--INSERT INTO "public"."mst_usr"("id", "usr_id", "usr_password", "fst_login_flg", "usr_nm", "role_div", "org_id", "pw_deadl_day", "usr_sts", "spec_auth_flg", "after_usr_id", "cret_datime", "cret_usr_id", "upd_datime", "upd_usr_id", "del_flg") VALUES (2, 'student', '71f93501f279f6df2c2e22e388ce358f845393451d866ada9765253f23ad2ddc', '1', 'テスト　太郎１', '60', 'GKGP0001', '2020-11-13', '1', '0', 'student', '2018-11-13 00:00:00', 'systemtest', '2018-11-13 00:00:00', 'systemtest', 0);
--INSERT INTO "public"."mst_usr"("id", "usr_id", "usr_password", "fst_login_flg", "usr_nm", "role_div", "org_id", "pw_deadl_day", "usr_sts", "spec_auth_flg", "after_usr_id", "cret_datime", "cret_usr_id", "upd_datime", "upd_usr_id", "del_flg") VALUES (3, 'mentor', '9f560d754721b596db9da23f03ae68447177c387576f7a13700c0ee684ada713', '1', 'テスト　太郎１', '60', 'GKGP0001', '2020-11-13', '1', '0', 'student', '2018-11-13 00:00:00', 'systemtest', '2018-11-13 00:00:00', 'systemtest', 0);
--INSERT INTO "public"."mst_usr"("id", "usr_id", "usr_password", "fst_login_flg", "usr_nm", "role_div", "org_id", "pw_deadl_day", "usr_sts", "spec_auth_flg", "after_usr_id", "cret_datime", "cret_usr_id", "upd_datime", "upd_usr_id", "del_flg") VALUES (4, 'guard', '09056736d02e0797821379b26d56196c4975bf8d8f66ec000c5f71da1ed4188f', '1', 'テスト　太郎１', '60', 'GKGP0001', '2020-11-13', '1', '0', 'student', '2018-11-13 00:00:00', 'systemtest', '2018-11-13 00:00:00', 'systemtest', 0);
--2019/05/13 START ADD
-- バッチ
CREATE TABLE sys_async_task (
  id bigserial,
  task_type int,
  status int,
  context text,
  lrs_id varchar(50),
  PRIMARY KEY (id)
);
COMMENT ON COLUMN sys_async_task.task_type IS 'タイプ';
COMMENT ON COLUMN sys_async_task.status IS '状態　0:新規,1:送信完了,2:送信失敗';
COMMENT ON COLUMN sys_async_task.context IS 'コンテキスト';
COMMENT ON COLUMN sys_async_task.lrs_id IS 'LRS ID';
COMMENT ON TABLE sys_async_task IS '非同期タスクマスタ';

CREATE TABLE sys_schedule_job (
  job_id bigserial,
  bean_name varchar(200),
  method_name varchar(100),
  params varchar(2000),
  cron_expression varchar(100),
  status int,
  remark varchar(255),
  create_time timestamp,
  create_user_id integer,
  PRIMARY KEY (job_id)
);
COMMENT ON TABLE sys_schedule_job IS 'スケジュールジョブ';
COMMENT ON COLUMN sys_schedule_job.job_id IS 'ジョブID';
COMMENT ON COLUMN sys_schedule_job.bean_name IS 'Bean名';
COMMENT ON COLUMN sys_schedule_job.method_name IS '方法名';
COMMENT ON COLUMN sys_schedule_job.params IS 'パラメータ';
COMMENT ON COLUMN sys_schedule_job.cron_expression IS 'Cron書式';
COMMENT ON COLUMN sys_schedule_job.status IS '状態';
COMMENT ON COLUMN sys_schedule_job.remark IS '注釈';
COMMENT ON COLUMN sys_schedule_job.create_time IS '作成日時';
COMMENT ON COLUMN sys_schedule_job.create_user_id IS '作成ユーザＩＤ';

CREATE TABLE sys_schedule_job_log (
  log_id bigserial,
  job_id int8 NOT NULL,
  bean_name varchar(200),
  method_name varchar(100),
  params varchar(2000),
  status int NOT NULL,
  error varchar(2000),
  times int NOT NULL,
  create_time timestamp,
  create_user_id integer,
  PRIMARY KEY (log_id)
);
CREATE INDEX index_job_id on sys_schedule_job_log(job_id);

COMMENT ON TABLE sys_schedule_job_log IS 'スケジュールジョブログ';
COMMENT ON COLUMN sys_schedule_job_log.log_id IS 'ログID';
COMMENT ON COLUMN sys_schedule_job_log.job_id IS 'ジョブID';
COMMENT ON COLUMN sys_schedule_job_log.bean_name IS 'Bean名';
COMMENT ON COLUMN sys_schedule_job_log.method_name IS '方法名';
COMMENT ON COLUMN sys_schedule_job_log.params IS 'パラメータ';
COMMENT ON COLUMN sys_schedule_job_log.status IS '状態';
COMMENT ON COLUMN sys_schedule_job_log.error IS 'エラー情報';
COMMENT ON COLUMN sys_schedule_job_log.times IS '実行時間';
COMMENT ON COLUMN sys_schedule_job_log.create_time IS '作成日時';
COMMENT ON COLUMN sys_schedule_job_log.create_user_id IS '作成ユーザーID';

--  quartzのcreate
CREATE TABLE qrtz_job_details
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  JOB_NAME  VARCHAR(200) NOT NULL,
  JOB_GROUP VARCHAR(200) NOT NULL,
  DESCRIPTION VARCHAR(250) NULL,
  JOB_CLASS_NAME   VARCHAR(250) NOT NULL,
  IS_DURABLE BOOL NOT NULL,
  IS_NONCONCURRENT BOOL NOT NULL,
  IS_UPDATE_DATA BOOL NOT NULL,
  REQUESTS_RECOVERY BOOL NOT NULL,
  JOB_DATA BYTEA NULL,
  PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
);

CREATE TABLE qrtz_triggers
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  JOB_NAME  VARCHAR(200) NOT NULL,
  JOB_GROUP VARCHAR(200) NOT NULL,
  DESCRIPTION VARCHAR(250) NULL,
  NEXT_FIRE_TIME BIGINT NULL,
  PREV_FIRE_TIME BIGINT NULL,
  PRIORITY INTEGER NULL,
  TRIGGER_STATE VARCHAR(16) NOT NULL,
  TRIGGER_TYPE VARCHAR(8) NOT NULL,
  START_TIME BIGINT NOT NULL,
  END_TIME BIGINT NULL,
  CALENDAR_NAME VARCHAR(200) NULL,
  MISFIRE_INSTR SMALLINT NULL,
  JOB_DATA BYTEA NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
  REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP)
);

CREATE TABLE qrtz_simple_triggers
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  REPEAT_COUNT BIGINT NOT NULL,
  REPEAT_INTERVAL BIGINT NOT NULL,
  TIMES_TRIGGERED BIGINT NOT NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

CREATE TABLE qrtz_cron_triggers
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  CRON_EXPRESSION VARCHAR(120) NOT NULL,
  TIME_ZONE_ID VARCHAR(80),
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

CREATE TABLE qrtz_simprop_triggers
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  STR_PROP_1 VARCHAR(512) NULL,
  STR_PROP_2 VARCHAR(512) NULL,
  STR_PROP_3 VARCHAR(512) NULL,
  INT_PROP_1 INT NULL,
  INT_PROP_2 INT NULL,
  LONG_PROP_1 BIGINT NULL,
  LONG_PROP_2 BIGINT NULL,
  DEC_PROP_1 NUMERIC(13,4) NULL,
  DEC_PROP_2 NUMERIC(13,4) NULL,
  BOOL_PROP_1 BOOL NULL,
  BOOL_PROP_2 BOOL NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

CREATE TABLE qrtz_blob_triggers
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  BLOB_DATA BYTEA NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

CREATE TABLE qrtz_calendars
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  CALENDAR_NAME  VARCHAR(200) NOT NULL,
  CALENDAR BYTEA NOT NULL,
  PRIMARY KEY (SCHED_NAME,CALENDAR_NAME)
);


CREATE TABLE qrtz_paused_trigger_grps
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_GROUP  VARCHAR(200) NOT NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP)
);

CREATE TABLE qrtz_fired_triggers
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  ENTRY_ID VARCHAR(95) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  INSTANCE_NAME VARCHAR(200) NOT NULL,
  FIRED_TIME BIGINT NOT NULL,
  SCHED_TIME BIGINT NOT NULL,
  PRIORITY INTEGER NOT NULL,
  STATE VARCHAR(16) NOT NULL,
  JOB_NAME VARCHAR(200) NULL,
  JOB_GROUP VARCHAR(200) NULL,
  IS_NONCONCURRENT BOOL NULL,
  REQUESTS_RECOVERY BOOL NULL,
  PRIMARY KEY (SCHED_NAME,ENTRY_ID)
);

CREATE TABLE qrtz_scheduler_state
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  INSTANCE_NAME VARCHAR(200) NOT NULL,
  LAST_CHECKIN_TIME BIGINT NOT NULL,
  CHECKIN_INTERVAL BIGINT NOT NULL,
  PRIMARY KEY (SCHED_NAME,INSTANCE_NAME)
);

CREATE TABLE qrtz_locks
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  LOCK_NAME  VARCHAR(40) NOT NULL,
  PRIMARY KEY (SCHED_NAME,LOCK_NAME)
);

create index idx_qrtz_j_req_recovery on qrtz_job_details(SCHED_NAME,REQUESTS_RECOVERY);
create index idx_qrtz_j_grp on qrtz_job_details(SCHED_NAME,JOB_GROUP);

create index idx_qrtz_t_j on qrtz_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);
create index idx_qrtz_t_jg on qrtz_triggers(SCHED_NAME,JOB_GROUP);
create index idx_qrtz_t_c on qrtz_triggers(SCHED_NAME,CALENDAR_NAME);
create index idx_qrtz_t_g on qrtz_triggers(SCHED_NAME,TRIGGER_GROUP);
create index idx_qrtz_t_state on qrtz_triggers(SCHED_NAME,TRIGGER_STATE);
create index idx_qrtz_t_n_state on qrtz_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
create index idx_qrtz_t_n_g_state on qrtz_triggers(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
create index idx_qrtz_t_next_fire_time on qrtz_triggers(SCHED_NAME,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_st on qrtz_triggers(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_misfire on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_st_misfire on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
create index idx_qrtz_t_nft_st_misfire_grp on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

create index idx_qrtz_ft_trig_inst_name on qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME);
create index idx_qrtz_ft_inst_job_req_rcvry on qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
create index idx_qrtz_ft_j_g on qrtz_fired_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);
create index idx_qrtz_ft_jg on qrtz_fired_triggers(SCHED_NAME,JOB_GROUP);
create index idx_qrtz_ft_t_g on qrtz_fired_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
create index idx_qrtz_ft_tg on qrtz_fired_triggers(SCHED_NAME,TRIGGER_GROUP);


commit;

--2019/05/13 START END
--2019/06/27 START
-- add amdin user start
INSERT INTO mst_usr (id, usr_id, usr_password, fst_login_flg, usr_nm, role_div, org_id, pw_up_flg, usr_sts, spec_auth_flg, after_usr_id, cret_datime, cret_usr_id, upd_datime, upd_usr_id, del_flg, error_count, lock_flg) values(nextval('mst_usr_id_seq'), 'admin', 'bf9e46e9b962c6b425f0685626cd64bb45febfac300c4b0322d0586954c20a26', '1', 'システム管理者', '5', 'SYS', '0', '1', '0', 'admin', '2018-11-13 00:00:00', 'admin', '2018-11-13 00:00:00', 'admin', '0', '0', '0');
INSERT INTO mst_org (id, org_id, upplev_org_id, level, org_nm, brand_cd, cret_datime, cret_usr_id, upd_datime, upd_usr_id, del_flg, mgr_flg) VALUES (nextval('mst_org_id_seq'), 'SYS', null, 0, 'システム管理', 'SYS', '2019-02-06 15:13:50.512', 'admin', '2019-02-06 15:13:50.512', 'admin', 0, '0');
INSERT INTO sys_role (role_id, role_name, remark, create_user_id, create_time) VALUES (nextval('sys_role_role_id_seq'), 'sys', 'システム管理', 1, '2019-01-31 09:35:14.617191');
INSERT INTO sys_user_role (id, user_id, role_id) values(nextval('sys_user_role_id_seq'), (select id from mst_usr where usr_id = 'admin'), (select role_id from sys_role where role_name = 'sys'));

INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (nextval('sys_menu_menu_id_seq'), 0, 'システム', NULL, NULL, 0, 'fa fa-cogs', 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (nextval('sys_menu_menu_id_seq'), (select menu_id from sys_menu where name = 'システム'), 'ユーザー管理', 'modules/sys/user.html', 'sys:user', 1, 'fa fa-user-circle', 1);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (nextval('sys_menu_menu_id_seq'), (select menu_id from sys_menu where name = 'システム'), '役割管理', 'modules/sys/role.html', 'sys:role', 1, 'fa fa-filter', 2);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (nextval('sys_menu_menu_id_seq'), (select menu_id from sys_menu where name = 'システム'), 'ログ管理', 'modules/sys/log.html', 'sys:log', 1, 'fa fa-file-o', 4);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (nextval('sys_menu_menu_id_seq'), (select menu_id from sys_menu where name = 'システム'), 'バッチ', 'modules/job/schedule.html', 'sys:job', 1, 'fa fa-asterisk', 5);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (nextval('sys_menu_menu_id_seq'), (select menu_id from sys_menu where name = 'システム'), 'メニュー管理', 'modules/sys/menu.html', 'sys:menu', 1, 'fa fa-list-ol', 3);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (nextval('sys_menu_menu_id_seq'), -1, 'mentor管理', 'mentor/F20001.html', 'mentor:*', 1, 'fa fa-user', 6);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (nextval('sys_menu_menu_id_seq'), -1, 'guard管理', 'guard/F30001.html', 'guard:*', 1, 'fa fa-user', 7);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (nextval('sys_menu_menu_id_seq'), -1, 'student管理', 'student/F10301.html', 'student:*', 1, 'fa fa-user', 5);

INSERT INTO sys_role_menu(id, role_id, menu_id) VALUES (nextval('sys_role_menu_id_seq'), (select role_id from sys_role where role_name = 'sys'), (select menu_id from sys_menu where name = 'システム'));
INSERT INTO sys_role_menu(id, role_id, menu_id) VALUES (nextval('sys_role_menu_id_seq'), (select role_id from sys_role where role_name = 'sys'), (select menu_id from sys_menu where name = 'ユーザー管理'));
INSERT INTO sys_role_menu(id, role_id, menu_id) VALUES (nextval('sys_role_menu_id_seq'), (select role_id from sys_role where role_name = 'sys'), (select menu_id from sys_menu where name = '役割管理'));
INSERT INTO sys_role_menu(id, role_id, menu_id) VALUES (nextval('sys_role_menu_id_seq'), (select role_id from sys_role where role_name = 'sys'), (select menu_id from sys_menu where name = 'ログ管理'));
INSERT INTO sys_role_menu(id, role_id, menu_id) VALUES (nextval('sys_role_menu_id_seq'), (select role_id from sys_role where role_name = 'sys'), (select menu_id from sys_menu where name = 'バッチ'));
INSERT INTO sys_role_menu(id, role_id, menu_id) VALUES (nextval('sys_role_menu_id_seq'), (select role_id from sys_role where role_name = 'sys'), (select menu_id from sys_menu where name = 'メニュー管理'));

--add admin user end
ALTER TABLE sys_log RENAME parames  TO params;
COMMENT ON COLUMN sys_log.params IS 'パーマ';

ALTER TABLE sys_user_online ADD COLUMN user_cd character varying(50);
COMMENT ON COLUMN sys_user_online.user_cd IS 'ユーザーCD';
ALTER TABLE sys_user_online ADD COLUMN login_type character varying(1);
COMMENT ON COLUMN sys_user_online.login_type IS 'ログインタイプ 0：本システム 1：LEシステム';
--2019/06/27 end

ALTER TABLE mst_addr ALTER COLUMN postcd_bnum TYPE varchar(4);
ALTER TABLE mst_block ALTER COLUMN block_type_div TYPE CHAR(3);
ALTER TABLE mst_notice_deliver ALTER COLUMN stu_id set DEFAULT not NULL;
ALTER TABLE mst_org ALTER COLUMN brand_cd TYPE VARCHAR(5);
ALTER TABLE stu_term_plan ALTER COLUMN plan_learn_seasn_id SET NOT NULL;
ALTER TABLE stu_textb_choc_out ALTER COLUMN subjt_div TYPE CHAR(2);
ALTER TABLE sys_user_online ALTER COLUMN login_type set DEFAULT 0;
-- 2019/10/31