-- ----------------------------
-- 工艺组成表
-- ----------------------------
drop table if exists pro_route_process;
create table pro_route_process (
  record_id                      bigint(20)      not null auto_increment    comment '记录ID',
  route_id                       bigint(20)      not null                   comment '工艺路线ID',
  process_id                     bigint(20)      not null                   comment '工序ID',
  process_code                   varchar(64)                                comment '工序编码',
  process_name                   varchar(255)                               comment '工序名称',
  order_num                      int(4)          default 1                  comment '序号',
  next_process_id                bigint(20)      not null                   comment '工序ID',
  next_process_code              varchar(64)                                comment '工序编码',
  next_process_name              varchar(255)                               comment '工序名称',
  link_type                      varchar(64)     default 'SS'               comment '与下一道工序关系',
  default_pre_time               int(11)         default 0                  comment '准备时间',
  default_suf_time               int(11)         default 0                  comment '等待时间',
  color_code                     char(7)         default '#00AEF3'          comment '甘特图显示颜色',
  key_flag                       varchar(64)     default 'N'                comment '关键工序',
  is_check                       char(1)         default 'N'                comment '是否检验',
  remark                         varchar(500)    default ''                 comment '备注',
  attr1                          varchar(64)     default null               comment '预留字段1',
  attr2                          varchar(255)    default null               comment '预留字段2',
  attr3                          bigint(20)      default null               comment '预留字段3',
  attr4                          bigint(20)      default null               comment '预留字段4',
  create_by                      varchar(64)     default ''                 comment '创建者',
  create_time 	                 datetime                                   comment '创建时间',
  update_by                      varchar(64)     default ''                 comment '更新者',
  update_time                    datetime                                   comment '更新时间',
  primary key (record_id)
) engine=innodb auto_increment=200 comment = '工艺组成表';
