-- ----------------------------
-- 产品制程表
-- ----------------------------
drop table if exists pro_route_product;
create table pro_route_product (
  record_id                      bigint(20)      not null auto_increment    comment '记录ID',
  route_id                       bigint(20)      not null                   comment '工艺路线ID',
  item_id                        bigint(20)      not null                   comment '产品物料ID',
  item_code                      varchar(64)     not null                   comment '产品物料编码',
  item_name                      varchar(255)    not null                   comment '产品物料名称',
  specification                  varchar(500)    default null               comment '规格型号',
  unit_of_measure                varchar(64)     not null                   comment '单位',
  unit_name                      varchar(64)                                comment '单位名称',
  quantity                       bigint(20)      default 1                  comment '生产数量',
  production_time                decimal(12,2)   default 1                  comment '生产用时',
  time_unit_type                 varchar(64)     default 'MINUTE'           comment '时间单位',
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
) engine=innodb auto_increment=200 comment = '产品制程表';
