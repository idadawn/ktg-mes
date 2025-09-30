-- ----------------------------
-- 生产工单表
-- ----------------------------
drop table if exists pro_workorder;
create table pro_workorder (
  workorder_id                bigint(20)      not null auto_increment    comment '工单ID',
  workorder_code              varchar(64)     not null                   comment '工单编码',
  workorder_name              varchar(255)    not null                   comment '工单名称',
  workorder_type              varchar(64)     default 'SELF'             comment '工单类型',
  order_source                varchar(64)     not null                   comment '来源类型',
  source_code                 varchar(64)                                comment '来源单据',
  product_id                  bigint(20)      not null                   comment '产品ID',
  product_code                varchar(64)     not null                   comment '产品编号',
  product_name                varchar(255)    not null                   comment '产品名称',
  product_spc                 varchar(255)                               comment '规格型号',
  unit_of_measure             varchar(64)     not null                   comment '单位',
  unit_name                   varchar(64)                                comment '单位名称',
  batch_code                  varchar(64)                                comment '批次号',
  quantity                    decimal(14,2)   default 0 not null         comment '生产数量',
  quantity_produced           decimal(14,2)   default 0                  comment '已生产数量',
  quantity_changed            decimal(14,2)   default 0                  comment '调整数量',
  quantity_scheduled          decimal(14,2)   default 0                  comment '已排产数量', 
  client_id                   bigint(20)                                 comment '客户ID',
  client_code                 varchar(64)                                comment '客户编码',
  client_name                 varchar(255)                               comment '客户名称',
  vendor_id                   bigint(20)                                 comment '供应商ID',
  vendor_code                 varchar(64)                                comment '供应商编号',
  vendor_name                 varchar(255)                               comment '供应商名称',
  request_date                datetime        not null                   comment '需求日期',
  parent_id                   bigint(20)      default 0 not null         comment '父工单',
  ancestors                   varchar(500)    not null                   comment '所有父节点ID',
  cancel_date                 datetime                                   comment '取消日期',
  finish_date                 datetime                                   comment '完成时间',
  status                      varchar(64)     default 'PREPARE'          comment '单据状态',
  remark                      varchar(500)    default ''                 comment '备注',
  attr1                       varchar(64)     default null               comment '预留字段1',
  attr2                       varchar(255)    default null               comment '预留字段2',
  attr3                       bigint(20)      default null               comment '预留字段3',
  attr4                       bigint(20)      default null               comment '预留字段4',
  create_by                   varchar(64)     default ''                 comment '创建者',
  create_time 	              datetime                                   comment '创建时间',
  update_by                   varchar(64)     default ''                 comment '更新者',
  update_time                 datetime                                   comment '更新时间',
  primary key (workorder_id),
  unique key uk_workorder_code (workorder_code)
) engine=innodb auto_increment=200 comment = '生产工单表';

-- 插入测试数据
insert into pro_workorder values(200, 'WO20240930001', '测试工单001', 'SELF', 'MANUAL', '', 1, 'P001', '测试产品', '规格A', 'PCS', '个', 'BATCH001', 100.00, 0.00, 0.00, 0.00, 1, 'C001', '测试客户', null, null, null, '2024-09-30 00:00:00', 0, '0', null, null, 'PREPARE', '测试工单', null, null, null, null, 'admin', sysdate(), '', null);
