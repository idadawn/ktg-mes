-- ----------------------------
-- 打印客户端表
-- ----------------------------
drop table if exists print_client;
create table print_client (
  client_id           bigint(20)      not null auto_increment    comment '打印机客户端ID',
  client_code         varchar(64)     not null                   comment '打印机客户端编码',
  client_name         varchar(255)    not null                   comment '打印机客户端名称',
  client_ip           varchar(64)     default null               comment '打印客户端IP',
  client_port         bigint(20)      default null               comment '打印客户端端口',
  client_token        varchar(255)    default null               comment '打印客户端token',
  status              varchar(64)     default '0'                comment '客户端状态（0正常 1停用）',
  workshop_id         bigint(20)      default null               comment '所属车间ID',
  workshop_code       varchar(64)     default null               comment '所属车间编码',
  workshop_name       varchar(255)    default null               comment '所属车间名称',
  workstation_id      bigint(20)      default null               comment '工作站ID',
  workstation_code    varchar(64)     default null               comment '工作站编码',
  workstation_name    varchar(255)    default null               comment '工作站名称',
  remark              varchar(500)    default ''                 comment '备注',
  attr1               varchar(64)     default null               comment '预留字段1',
  attr2               varchar(255)    default null               comment '预留字段2',
  attr3               bigint(20)      default null               comment '预留字段3',
  attr4               bigint(20)      default null               comment '预留字段4',
  create_by           varchar(64)     default ''                 comment '创建者',
  create_time         datetime                                   comment '创建时间',
  update_by           varchar(64)     default ''                 comment '更新者',
  update_time         datetime                                   comment '更新时间',
  primary key (client_id),
  unique key uk_client_code (client_code)
) engine=innodb auto_increment=1 comment = '打印客户端表';

-- ----------------------------
-- 初始化打印客户端表数据
-- ----------------------------
insert into print_client values(1, 'PC001', '默认打印客户端', '127.0.0.1', 8080, 'default_token', '0', null, null, null, null, null, null, '默认打印客户端', null, null, null, null, 'admin', sysdate(), '', null);
