-- ----------------------------
-- 1、仓库表
-- ----------------------------
drop table if exists wm_warehouse;
create table wm_warehouse (
  warehouse_id           bigint(20)      not null auto_increment    comment '仓库ID',
  warehouse_code         varchar(64)     not null UNIQUE            comment '仓库编码',
  warehouse_name         varchar(255)    not null                   comment '仓库名称',
  location               varchar(500)                               comment '位置',
  area                   double(12,2)                               comment '面积',
  user_id                 bigint(20)                                comment '用户ID',
  user_name              varchar(64)                                comment '用户名',
  charge                 varchar(64)                                comment '仓管名称',
  manager_id             bigint(20)                                 comment '主管ID',
  manager_name           varchar(64)                                comment '主管用户名',
  manager_nick           varchar(64)                                comment '主管名称',
  enable_flag            char(1)         default 'N'                comment '是否启用',
  remark                 varchar(500)    default ''                 comment '备注',
  attr1                  varchar(64)     default null               comment '预留字段1',
  attr2                  varchar(255)     default null              comment '预留字段2',
  attr3                  int(11)         default 0                  comment '预留字段3',
  attr4                  int(11)         default 0                  comment '预留字段4',
  create_by              varchar(64)     default ''                 comment '创建者',
  create_time 	         datetime                                   comment '创建时间',
  update_by              varchar(64)     default ''                 comment '更新者',
  update_time            datetime                                   comment '更新时间',
  primary key (warehouse_id)
) engine=innodb auto_increment=200 comment = '仓库表';



-- ----------------------------
-- 2、库区表
-- ----------------------------
drop table if exists wm_storage_location;
create table wm_storage_location (
  location_id           bigint(20)      not null auto_increment     comment '库区ID',
  location_code         varchar(64)     not null UNIQUE             comment '库区编码',
  location_name         varchar(255)    not null                    comment '库区名称',
  warehouse_id          bigint(20)      not null                    comment '仓库ID',
  area                  double(12,2)                                comment '面积',
  area_flag             char(1)         default 'Y'                 comment '是否开启库位管理',
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (location_id)
) engine=innodb auto_increment=200 comment = '库区表';


-- ----------------------------
-- 3、库位表
-- ----------------------------
drop table if exists wm_storage_area;
create table wm_storage_area (
  area_id               bigint(20)      not null auto_increment     comment '库位ID',
  area_code             varchar(64)     not null UNIQUE             comment '库位编码',
  area_name             varchar(255)    not null                    comment '库位名称',
  location_id           bigint(20)      not null                    comment '库区ID',
  area                  double(8,2)                                 comment '面积',
  max_loa               double(8,2)                                 comment '最大载重量',
  position_x            int(11)                                     comment '库位位置X',
  position_y            int(11)                                     comment '库位位置y',
  position_z            int(11)                                     comment '库位位置z',
  enable_flag           char(1)                                     comment '是否启用',
  frozen_flag           char(1)         default 'N'                 comment '是否冻结',
  product_mixing        char(1)         default 'Y'                 comment '是否允许产品混放',
  batch_mixing          char(1)         default 'Y'                 comment '是否允许批次混放',
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (area_id)
) engine=innodb auto_increment=200 comment = '库位表';




-- ----------------------------
-- 4、库存事务表
-- ----------------------------
drop table if exists wm_transaction;
create table wm_transaction (
  transaction_id        bigint(20)      not null auto_increment     comment '事务ID',
  transaction_type      varchar(64)     not null                    comment '事务类型',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',   
  unit_name             varchar(128)                                comment '单位名称',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',
  warehouse_id          bigint(20)      not null                    comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称', 
  pakcage_id            bigint(20)                                  comment '容器ID',
  package_code          varchar(64)                                 comment '容器编号',
  source_doc_type       varchar(64)                                 comment '单据类型',
  source_doc_id         bigint(20)                                  comment '单据ID',
  source_doc_code       varchar(64)                                 comment '单据编号',
  source_doc_line_id    bigint(20)                                  comment '单据行ID',
  material_stock_id     bigint(20)      not null                    comment '库存记录ID',
  transaction_flag      int(1)          default 1                   comment '库存方向',
  transaction_quantity  double(12,2)                                comment '事务数量',
  transaction_date      datetime                                    comment '事务日期',
  related_transaction_id bigint(20)                                 comment '关联的事务ID', 
  erp_date              datetime                                    comment 'ERP账期',
  recpt_date            datetime                                    comment '接收日期',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (transaction_id)
) engine=innodb auto_increment=200 comment = '库存事务表';



-- ----------------------------
-- 5、库存记录表
-- ----------------------------
drop table if exists wm_material_stock;
create table wm_material_stock (
  material_stock_id     bigint(20)      not null auto_increment     comment '事务ID',
  item_type_id          bigint(20)                                  comment '物料类型ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',   
  unit_name             varchar(128)                                comment '单位名称',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',
  workorder_id          bigint(20)                                  comment '生产工单ID',
  workorder_code        varchar(64)                                 comment '生产工单编号',
  vendor_id             bigint(20)                                  comment '供应商ID',
  vendor_code           varchar(64)                                 comment '供应商编号',
  vendor_name           varchar(255)                                comment '供应商名称',
  vendor_nick           varchar(64)                                 comment '供应商简称',
  client_id             bigint(20)                                  comment '客户ID',
  client_code           varchar(64)                                 comment '客户编码',
  client_name           varchar(255)                                comment '客户名称',
  client_nick           varchar(255)                                comment '客户简称',
  warehouse_id          bigint(20)      not null                    comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称', 
  package_id            bigint(20)                                  comment '容器ID',
  package_code          varchar(64)                                 comment '容器编号',
  quantity_onhand       double(12,2)                                comment '在库数量',
  quantity_reserved     double(12,2)                                comment '保留数量',
  production_date       datetime                                    comment '生产日期',
  recpt_date            datetime                                    comment '入库时间',
  expire_date           datetime                                    comment '库存有效期',
  frozen_flag           char(1)         default 'N' not null        comment '是否冻结',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (material_stock_id)
) engine=innodb auto_increment=200 comment = '库存记录表';


-- ----------------------------
-- 6、到货通知单
-- ----------------------------
drop table if exists wm_arrival_notice;
create table wm_arrival_notice (
  notice_id             bigint(20)      not null auto_increment     comment '通知单ID',
  notice_code           varchar(64)     not null                    comment '通知单编号',
  notice_name           varchar(255)    not null                    comment '通知单名称', 
  po_code               varchar(64)                                 comment '采购订单编号',  
  vendor_id             bigint(20)                                  comment '供应商ID',
  vendor_code           varchar(64)                                 comment '供应商编码',
  vendor_name           varchar(255)                                comment '供应商名称',
  vendor_nick           varchar(255)                                comment '供应商简称',
  arrival_date          datetime                                    comment '到货日期',
  contact               varchar(64)                                 comment '联系人',
  tel                   varchar(128)                                comment '联系方式',
  status                varchar(64)     default 'PREPARE'           comment '单据状态',  
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (notice_id)
) engine=innodb auto_increment=200 comment = '到货通知单表';


-- ----------------------------
-- 7、到货通知单行表
-- ----------------------------
drop table if exists wm_arrival_notice_line;
create table wm_arrival_notice_line (
  line_id               bigint(20)      not null auto_increment     comment '行ID',
  notice_id             bigint(20)                                  comment '通知单ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(128)                                comment '单位名称',
  quantity_arrival      double(12,2)    not null                    comment '到货数量',
  quantity_quanlified   double(12,2)                                comment '合格数量',
  iqc_check             char(1)                                     comment '是否来料检验',
  iqc_id                bigint(20)                                  comment '来料检验单ID',
  iqc_code              varchar(64)                                 comment '来料检验单编号',                 
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (line_id)
) engine=innodb auto_increment=200 comment = '到货通知单行表';



-- ----------------------------
-- 6、物料入库单表
-- ----------------------------
drop table if exists wm_item_recpt;
create table wm_item_recpt (
  recpt_id              bigint(20)      not null auto_increment     comment '入库单ID',
  recpt_code            varchar(64)     not null                    comment '入库单编号',
  recpt_name            varchar(255)    not null                    comment '入库单名称',
  iqc_id                bigint(20)                                  comment '来料检验单ID',
  iqc_code              varchar(64)                                 comment '来料检验单编号',  
  notice_id             bigint(20)                                  comment '到货通知单ID',
  notice_code           varchar(64)                                 comment '到货通知单编号',
  po_code               varchar(64)                                 comment '采购订单编号',  
  vendor_id             bigint(20)                                  comment '供应商ID',
  vendor_code           varchar(64)                                 comment '供应商编码',
  vendor_name           varchar(255)                                comment '供应商名称',
  vendor_nick           varchar(255)                                comment '供应商简称',
  warehouse_id          bigint(20)                                  comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称', 
  recpt_date            datetime                                    comment '入库日期',
  status                varchar(64)     default 'PREPARE'           comment '单据状态',  
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (recpt_id)
) engine=innodb auto_increment=200 comment = '物料入库单表';


-- ----------------------------
-- 7、物料入库单行表
-- ----------------------------
drop table if exists wm_item_recpt_line;
create table wm_item_recpt_line (
  line_id               bigint(20)      not null auto_increment     comment '行ID',
  recpt_id              bigint(20)                                  comment '入库单ID',
  notice_line_id        bigint(20)                                  comment '到货通知单行ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(128)                                comment '单位名称',
  quantity_recived      double(12,2)    not null                    comment '入库数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',
  warehouse_id          bigint(20)                                  comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称', 
  produce_date          datetime                                    comment '生产日期',
  expire_date           datetime                                    comment '有效期', 
  lot_number            varchar(128)                                comment '生产批号',
  iqc_check             char(1)                                     comment '是否来料检验',
  iqc_id                bigint(20)                                  comment '来料检验单ID',
  iqc_code              varchar(64)                                 comment '来料检验单编号',                 
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (line_id)
) engine=innodb auto_increment=200 comment = '物料入库单行表';


-- ----------------------------
-- 7、物料入库单明细表
-- ----------------------------
drop table if exists wm_item_recpt_detail;
create table wm_item_recpt_detail (
  detail_id             bigint(20)      not null auto_increment     comment '明细ID',
  line_id               bigint(20)                                  comment '行ID',
  recpt_id              bigint(20)                                  comment '入库单ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(128)                                comment '单位名称',
  quantity              double(12,2)    not null                    comment '入库数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',
  warehouse_id          bigint(20)                                  comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称',         
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (detail_id)
) engine=innodb auto_increment=200 comment = '物料入库单明细表';


-- ----------------------------
-- 8、采购退货表
-- ----------------------------
drop table if exists wm_rt_vendor;
create table wm_rt_vendor (
  rt_id              bigint(20)      not null auto_increment        comment '退货单ID',
  rt_code            varchar(64)     not null                       comment '退货单编号',
  rt_name            varchar(255)    not null                       comment '退货单名称',  
  po_code               varchar(64)                                 comment '采购订单编号',  
  vendor_id             bigint(20)                                  comment '供应商ID',
  vendor_code           varchar(64)                                 comment '供应商编码',
  vendor_name           varchar(255)                                comment '供应商名称',
  vendor_nick           varchar(255)                                comment '供应商简称',
  rt_reason             varchar(255)                                comment '退货原因',
  transport_code        varchar(128)                                comment '运输单号',
  transport_tel         varchar(128)                                comment '联系方式',
  batch_code            varchar(255)                                comment '批次号',
  rt_date               datetime                                    comment '退货日期',
  status                varchar(64)     default 'PREPARE'           comment '单据状态',  
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (rt_id)
) engine=innodb auto_increment=200 comment = '采购退货表';


-- ----------------------------
-- 9、采购退货行表
-- ----------------------------
drop table if exists wm_rt_vendor_line;
create table wm_rt_vendor_line (
  line_id               bigint(20)      not null auto_increment     comment '行ID',
  rt_id                 bigint(20)                                  comment '退货单ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(128)                                comment '单位名称',
  quantity_rted         double(12,2)    not null                    comment '退货数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号', 
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (line_id)
) engine=innodb auto_increment=200 comment = '供应商退货行表';


-- ----------------------------
-- 9、采购退货单明细表
-- ----------------------------
drop table if exists wm_rt_vendor_detail;
create table wm_rt_vendor_detail (
  detail_id             bigint(20)      not null auto_increment     comment '明细ID',
  line_id               bigint(20)                                  comment '行ID',
  rt_id                 bigint(20)                                  comment '退货单ID',
  material_stock_id     bigint(20)                                  comment '库存记录ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(128)                                comment '单位名称',
  quantity              double(12,2)    not null                    comment '退货数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',
  warehouse_id          bigint(20)                                  comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称',         
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (detail_id)
) engine=innodb auto_increment=200 comment = '采购退货单明细表';


-- ----------------------------
-- 10、生产领料单头表
-- ----------------------------
drop table if exists wm_issue_header;
create table wm_issue_header (
  issue_id              bigint(20)      not null auto_increment     comment '领料单ID',
  issue_code            varchar(64)     not null                    comment '领料单编号',
  issue_name            varchar(255)    not null                    comment '领料单名称',
  workstation_id        bigint(20)                                  comment '工作站ID',
  workstation_code      varchar(64)                                 comment '工作站编号',    
  workstation_name      varchar(255)                                comment '工作站名称',
  workorder_id          bigint(20)                                  comment '生产工单ID',
  workorder_code        varchar(64)                                 comment '生产工单编码',
  task_id               bigint(20)                                  comment '生产任务ID',
  task_code             varchar(64)                                 comment '生产任务编码',
  client_id             bigint(20)                                  comment '客户ID',
  client_code           varchar(64)                                 comment '客户编码',
  client_name           varchar(255)                                comment '客户名称',
  client_nick           varchar(255)                                comment '客户简称',
  required_time         datetime                                    comment '需求时间',
  issue_date            datetime                                    comment '领料日期',
  status                varchar(64)     default 'PREPARE'           comment '单据状态',  
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (issue_id)
) engine=innodb auto_increment=200 comment = '生产领料单头表';



-- ----------------------------
-- 11、生产领料单行表
-- ----------------------------
drop table if exists wm_issue_line;
create table wm_issue_line (
  line_id               bigint(20)      not null auto_increment     comment '行ID',
  issue_id              bigint(20)                                  comment '领料单ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(128)                                comment '单位名称',
  quantity_issued       double(12,2)    not null                    comment '领料数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (line_id)
) engine=innodb auto_increment=200 comment = '生产领料单行表';


-- ----------------------------
-- 11、生产领料单明细表
-- ----------------------------
drop table if exists wm_issue_detail;
create table wm_issue_detail (
  detail_id             bigint(20)      not null auto_increment     comment '明细行ID',
  issue_id              bigint(20)                                  comment '领料单ID',
  line_id               bigint(20)      not null                    comment '行ID',
  material_stock_id     bigint(20)                                  comment '库存记录ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(128)                                comment '单位名称',
  quantity              double(12,2)    not null                    comment '领料数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',
  warehouse_id          bigint(20)                                  comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称', 
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (detail_id)
) engine=innodb auto_increment=200 comment = '生产领料单明细表';



-- ----------------------------
-- 10、生产退料单头表
-- ----------------------------
drop table if exists wm_rt_issue;
create table wm_rt_issue (
  rt_id                 bigint(20)      not null auto_increment     comment '退料单ID',
  rt_code               varchar(64)     not null                    comment '退料单编号',
  rt_name               varchar(255)                                comment '退料单名称', 
  workorder_id          bigint(20)                                  comment '生产工单ID',
  workorder_code        varchar(64)                                 comment '生产工单编码', 
  workstation_id        bigint(20)                                  comment '工作站ID',
  workstation_code      varchar(64)                                 comment '工作站编号',    
  workstation_name      varchar(255)                                comment '工作站名称',   
  rt_type               varchar(64)                                 comment '退料类型',     
  rt_date               datetime                                    comment '退料日期',
  status                varchar(64)     default 'PREPARE'           comment '单据状态',  
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (rt_id)
) engine=innodb auto_increment=200 comment = '生产退料单头表';



-- ----------------------------
-- 11、生产退料单行表
-- ----------------------------
drop table if exists wm_rt_issue_line;
create table wm_rt_issue_line (
  line_id               bigint(20)      not null auto_increment     comment '行ID',
  rt_id                 bigint(20)                                  comment '退料单ID',
  material_stock_id     bigint(20)                                  comment '库存记录ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(128)                                comment '单位名称',
  quantity_rt           double(12,2)    not null                    comment '退料数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',
  ipqc_id               bigint(20)                                  comment '对应的质检单ID',
  ipqc_code             varchar(64)                                 comment '对应的质检单编号',
  qc_flag               char(1)         default 'N'                 comment '是否检验',
  quality_status        varchar(64)                                 comment '质量状态',
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (line_id)
) engine=innodb auto_increment=200 comment = '生产退料单行表';


-- ----------------------------
-- 11、生产领料单明细表
-- ----------------------------
drop table if exists wm_rt_issue_detail;
create table wm_rt_issue_detail (
  detail_id             bigint(20)      not null auto_increment     comment '明细行ID',
  rt_id                 bigint(20)                                  comment '退料单ID',
  line_id               bigint(20)      not null                    comment '行ID',
  material_stock_id     bigint(20)                                  comment '库存记录ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(128)                                comment '单位名称',
  quantity              double(12,2)    not null                    comment '领料数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',
  warehouse_id          bigint(20)                                  comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称', 
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (detail_id)
) engine=innodb auto_increment=200 comment = '生产退料单明细表';


-- ----------------------------
-- 10、物料消耗记录表
-- ----------------------------
drop table if exists wm_item_consume;
create table wm_item_consume (
  record_id             bigint(20)      not null auto_increment     comment '记录ID',
  workorder_id          bigint(20)                                  comment '生产工单ID',
  workorder_code        varchar(64)                                 comment '生产工单编码', 
  workorder_name        varchar(255)                                comment '生产工单名称',
  task_id               bigint(20)                                  comment '生产任务ID',
  task_code             varchar(64)                                 comment '生产任务编号',
  task_name             varchar(255)                                comment '生产任务名称',
  workstation_id        bigint(20)                                  comment '工作站ID',
  workstation_code      varchar(64)                                 comment '工作站编号',    
  workstation_name      varchar(255)                                comment '工作站名称',
  process_id            bigint(20)                                  comment '工序ID',
  process_code          varchar(64)                                 comment '工序编号',
  process_name          varchar(255)                                comment '工序名称',
  feedback_id           bigint(20)                                  comment '关联的报工单',
  consume_date          datetime                                    comment '消耗日期',
  status                varchar(64)     default 'PREPARE'           comment '单据状态',  
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (record_id)
) engine=innodb auto_increment=200 comment = '物料消耗记录表';


-- ----------------------------
-- 11、物料消耗记录行表
-- ----------------------------
drop table if exists wm_item_consume_line;
create table wm_item_consume_line (
  line_id               bigint(20)      not null auto_increment     comment '行ID',
  record_id             bigint(20)                                  comment '消耗记录ID',
  material_stock_id     bigint(20)                                  comment '库存ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(64)                                 comment '单位名称',
  quantity_consume      double(12,2)    not null                    comment '消耗数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',    
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (line_id)
) engine=innodb auto_increment=200 comment = '物料消耗记录行表';


-- ----------------------------
-- 11、物料消耗记录明细表
-- ----------------------------
drop table if exists wm_item_consume_detail;
create table wm_item_consume_detail (
  detail_id             bigint(20)      not null auto_increment     comment '明细ID',
  line_id               bigint(20)                                  comment '行ID',
  record_id             bigint(20)                                  comment '消耗记录ID',
  material_stock_id     bigint(20)                                  comment '库存ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(64)                                 comment '单位名称',
  quantity              double(12,2)    not null                    comment '消耗数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',   
  warehouse_id          bigint(20)                                  comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称',  
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (detail_id)
) engine=innodb auto_increment=200 comment = '物料消耗记录明细表';




-- ----------------------------
-- 8、产品产出记录表（入线边库）
-- ----------------------------
drop table if exists wm_product_produce;
create table wm_product_produce (
  record_id             bigint(20)      not null auto_increment     comment '产品产出记录ID',
  workorder_id          bigint(20)                                  comment '生产工单ID',
  workorder_code        varchar(64)                                 comment '生产工单编码', 
  workorder_name        varchar(255)                                comment '生产工单名称',
  feedback_id           bigint(20)                                  comment '报工单ID',
  task_id               bigint(20)                                  comment '生产任务ID',
  task_code             varchar(64)                                 comment '生产任务编号',
  task_name             varchar(255)                                comment '生产任务名称',
  workstation_id        bigint(20)                                  comment '工作站ID',
  workstation_code      varchar(64)                                 comment '工作站编号',    
  workstation_name      varchar(255)                                comment '工作站名称',
  process_id            bigint(20)                                  comment '工序ID',
  process_code          varchar(64)                                 comment '工序编号',
  process_name          varchar(255)                                comment '工序名称',
  produce_date          datetime                                    comment '生产日期',
  status                varchar(64)     default 'PREPARE'           comment '单据状态',  
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (record_id)
) engine=innodb auto_increment=200 comment = '产品产出记录表';




-- ----------------------------
-- 11、产品产出记录表行表
-- ----------------------------
drop table if exists wm_product_produce_line;
create table wm_product_produce_line (
  line_id               bigint(20)      not null auto_increment     comment '行ID',
  record_id             bigint(20)                                  comment '产出记录ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(64)                                 comment '单位名称',
  quantity_produce      double(12,2)    not null                    comment '产出数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',  
  expire_date           datetime                                    comment '过期日期',
  lot_number            varchar(128)                                comment '生产批号',
  quality_status        varchar(64)                                 comment '质量状态',
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (line_id)
) engine=innodb auto_increment=200 comment = '产品产出记录表行表';



-- ----------------------------
-- 11、产品产出记录明细表
-- ----------------------------
drop table if exists wm_product_produce_detail;
create table wm_product_produce_detail (
  detail_id             bigint(20)      not null auto_increment     comment '行ID',
  line_id               bigint(20)                                  comment '明细ID',
  record_id             bigint(20)                                  comment '产出记录ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(64)                                 comment '单位名称',
  quantity              double(12,2)    not null                    comment '产出数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',
  warehouse_id          bigint(20)                                  comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称',   
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (detail_id)
) engine=innodb auto_increment=200 comment = '产品产出记录明细表';



-- ----------------------------
-- 12、产品入库录表（线边库入正式库）
-- ----------------------------
drop table if exists wm_product_recpt;
create table wm_product_recpt (
  recpt_id              bigint(20)      not null auto_increment     comment '入库单ID',
  recpt_code            varchar(64)     not null                    comment '入库单编号',
  recpt_name            varchar(255)                                comment '入库单名称',
  workorder_id          bigint(20)                                  comment '生产工单ID',
  workorder_code        varchar(64)                                 comment '生产工单编码', 
  workorder_name        varchar(255)                                comment '生产工单名称', 
  item_id               bigint(20)                                  comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(64)                                 comment '单位名称',
  recpt_date            datetime                                    comment '入库日期',
  status                varchar(64)     default 'PREPARE'           comment '单据状态',  
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (recpt_id)
) engine=innodb auto_increment=200 comment = '产品入库录表';



-- ----------------------------
-- 13、产品入库记录表行表
-- ----------------------------
drop table if exists wm_product_recpt_line;
create table wm_product_recpt_line (
  line_id               bigint(20)      not null auto_increment     comment '行ID',
  recpt_id              bigint(20)                                  comment '入库记录ID',
  material_stock_id     bigint(20)                                  comment '库存记录ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(64)                                 comment '单位名称',
  quantity_recived      double(12,2)    not null                    comment '入库数量',
  workorder_id          bigint(20)                                  comment '生产工单ID',
  workorder_code        varchar(64)                                 comment '生产工单编码', 
  workorder_name        varchar(255)                                comment '生产工单名称',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (line_id)
) engine=innodb auto_increment=200 comment = '产品入库记录表行表';



-- ----------------------------
-- 13、产品入库记录表行表
-- ----------------------------
drop table if exists wm_product_recpt_detail;
create table wm_product_recpt_detail (
  detail_id             bigint(20)      not null auto_increment     comment '明细ID',
  line_id               bigint(20)      not null                    comment '行ID',
  recpt_id              bigint(20)                                  comment '入库记录ID',
  material_stock_id     bigint(20)                                  comment '库存记录ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(64)                                 comment '单位名称',
  quantity              double(12,2)    not null                    comment '入库数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',
  warehouse_id          bigint(20)                                  comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称', 
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (detail_id)
) engine=innodb auto_increment=200 comment = '产品入库记录明细表';



-- ----------------------------
-- 14、发货通知单表
-- ----------------------------
drop table if exists wm_sales_notice;
create table wm_sales_notice (
  notice_id              bigint(20)      not null auto_increment    comment '通知单ID',
  notice_code            varchar(64)     not null                   comment '通知单编号',
  notice_name            varchar(255)    not null                   comment '通知单名称', 
  so_code               varchar(64)                                 comment '销售订单编号',  
  client_id             bigint(20)                                  comment '客户ID',
  client_code           varchar(64)                                 comment '客户编码',
  client_name           varchar(255)                                comment '客户名称',
  client_nick           varchar(255)                                comment '客户简称',
  sales_date            datetime                                    comment '发货日期',
  recipient             varchar(64)                                 comment '收货人',
  tel                   varchar(64)                                 comment '联系方式',
  address               varchar(255)                                comment '收货地址',  
  status                varchar(64)     default 'PREPARE'           comment '单据状态',  
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (notice_id)
) engine=innodb auto_increment=200 comment = '发货通知单表';


-- ----------------------------
-- 15、发货通知单行表
-- ----------------------------
drop table if exists wm_sales_notice_line;
create table wm_sales_notice_line (
  line_id               bigint(20)      not null auto_increment     comment '行ID',
  notice_id             bigint(20)                                  comment '通知单ID',  
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(64)                                 comment '单位名称',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',
  quantity_sales        double(12,2)    not null                    comment '发货数量',
  oqc_check             char(1)                                     comment '是否检验',
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (line_id)
) engine=innodb auto_increment=200 comment = '发货通知单行表';




-- ----------------------------
-- 14、产品销售出库单表
-- ----------------------------
drop table if exists wm_product_sales;
create table wm_product_sales (
  sales_id              bigint(20)      not null auto_increment     comment '出库单ID',
  sales_code            varchar(64)     not null                    comment '出库单编号',
  sales_name            varchar(255)    not null                    comment '出库单名称', 
  notice_id             bigint(20)                                  comment '通知单ID',
  notice_code           varchar(64)                                 comment '通知单编号',
  so_code               varchar(64)                                 comment '销售订单编号',  
  client_id             bigint(20)                                  comment '客户ID',
  client_code           varchar(64)                                 comment '客户编码',
  client_name           varchar(255)                                comment '客户名称',
  client_nick           varchar(255)                                comment '客户简称',
  recipient             varchar(128)                                comment '收货人',
  tel                   varchar(128)                                comment '联系方式',
  address               varchar(256)                                comment '收货地址',
  carrier               varchar(128)                                comment '承运商',
  shipping_number       varchar(128)                                comment '运输单号',
  sales_date            datetime                                    comment '出库日期',
  status                varchar(64)     default 'PREPARE'           comment '单据状态',  
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (sales_id)
) engine=innodb auto_increment=200 comment = '销售出库单表';


-- ----------------------------
-- 15、产品销售出库行表
-- ----------------------------
drop table if exists wm_product_sales_line;
create table wm_product_sales_line (
  line_id               bigint(20)      not null auto_increment     comment '行ID',
  sales_id              bigint(20)                                  comment '出库记录ID',
  material_stock_id     bigint(20)                                  comment '库存记录ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(64)                                 comment '单位名称',
  quantity_sales        double(12,2)    not null                    comment '出库数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',  
  oqc_check             char(1)                                     comment '是否检验',
  oqc_id                bigint(20)                                  comment '出货检验单ID',
  oqc_code              varchar(64)                                 comment '出货检验单编号', 
  quality_status        varchar(64)                                 comment '质量状态',
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (line_id)
) engine=innodb auto_increment=200 comment = '产品销售出库行表';


-- ----------------------------
-- 13、产品入库记录表行表
-- ----------------------------
drop table if exists wm_product_sales_detail;
create table wm_product_sales_detail (
  detail_id             bigint(20)      not null auto_increment     comment '明细ID',
  line_id               bigint(20)      not null                    comment '行ID',
  sales_id              bigint(20)                                  comment '销售出库单ID',
  material_stock_id     bigint(20)                                  comment '库存记录ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(64)                                 comment '单位名称',
  quantity              double(12,2)    not null                    comment '出库数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',
  warehouse_id          bigint(20)                                  comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称', 
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (detail_id)
) engine=innodb auto_increment=200 comment = '产品销售出库记录明细表';








-- ----------------------------
-- 16、产品销售退货单表
-- ----------------------------
drop table if exists wm_rt_sales;
create table wm_rt_sales (
  rt_id                 bigint(20)      not null auto_increment     comment '退货单ID',
  rt_code               varchar(64)     not null                    comment '退货单编号',
  rt_name               varchar(255)    not null                    comment '退货单名称',
  so_code               varchar(64)                                 comment '销售订单编号',  
  client_id             bigint(20)                                  comment '客户ID',
  client_code           varchar(64)                                 comment '客户编码',
  client_name           varchar(255)                                comment '客户名称',
  client_nick           varchar(255)                                comment '客户简称',
  rt_date               datetime                                    comment '退货日期',
  rt_reason             varchar(255)                                comment '退货原因',
  status                varchar(64)     default 'PREPARE'           comment '单据状态',  
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (rt_id)
) engine=innodb auto_increment=200 comment = '产品销售退货单表';



-- ----------------------------
-- 17、产品销售退货行表
-- ----------------------------
drop table if exists wm_rt_sales_line;
create table wm_rt_sales_line (
  line_id               bigint(20)      not null auto_increment     comment '行ID',
  rt_id                 bigint(20)                                  comment '退货单ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(64)                                 comment '单位名称',  
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',
  quantity_rted         double(12,2)    not null                    comment '退货数量',
  quality_status        varchar(64)                                 comment '质量状态',
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (line_id)
) engine=innodb auto_increment=200 comment = '产品销售退货行表';



-- ----------------------------
-- 13、销售退货记录明细表
-- ----------------------------
drop table if exists wm_rt_sales_detail;
create table wm_rt_sales_detail (
  detail_id             bigint(20)      not null auto_increment     comment '明细ID',
  line_id               bigint(20)      not null                    comment '行ID',
  rt_id                 bigint(20)                                  comment '销售退货单ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(64)                                 comment '单位名称',
  quantity              double(12,2)    not null                    comment '退货数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',
  warehouse_id          bigint(20)                                  comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称', 
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (detail_id)
) engine=innodb auto_increment=200 comment = '销售退货记录明细表';





-- ----------------------------
-- 18、条码清单表
-- ----------------------------
drop table if exists wm_barcode;
create table wm_barcode (
  barcode_id            bigint(20)      not null auto_increment     comment '条码ID',
  barcode_formart       varchar(64)     not null                    comment '条码格式',
  barcode_type          varchar(64)     not null                    comment '条码类型',
  barcode_content       varchar(255)    not null                    comment '条码内容',
  bussiness_id          bigint(20)      not null                    comment '业务ID',
  bussiness_code        varchar(64)                                 comment '业务编码',
  bussiness_name        varchar(255)                                comment '业务名称',
  barcode_url           varchar(255)                                comment '条码地址',
  enable_flag           char(1)         default 'Y'                 comment '是否生效',
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (barcode_id)
) engine=innodb auto_increment=200 comment = '条码清单表';




-- ----------------------------
-- 18、条码配置
-- ----------------------------
drop table if exists wm_barcode_config;
create table wm_barcode_config (
  config_id             bigint(20)      not null auto_increment     comment '配置ID',
  barcode_formart       varchar(64)     not null                    comment '条码格式',
  barcode_type          varchar(64)     not null                    comment '条码类型',
  content_formart       varchar(255)    not null                    comment '内容格式',
  content_example       varchar(255)                                comment '内容样例',
  auto_gen_flag         char(1)         default 'Y'                 comment '是否自动生成',
  default_template      varchar(255)                                comment '默认的打印模板',
  enable_flag           char(1)         default 'Y'                 comment '是否生效',
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (config_id)
) engine=innodb auto_increment=200 comment = '条码配置';



-- ----------------------------
-- 19、装箱单表
-- ----------------------------
drop table if exists wm_package;
create table wm_package (
  package_id            bigint(20)      not null auto_increment     comment '装箱单ID',
  parent_id             bigint(20)      not null default 0          comment '父箱ID',
  ancestors             varchar(255)    not null default 0          comment '所有父节点ID',
  package_code          varchar(64)                                 comment '装箱单编号',
  barcode_id            bigint(20)                                  comment '条码ID',
  barcode_content       varchar(255)                                comment '条码内容',
  barcode_url           varchar(255)                                comment '条码地址',
  package_date          datetime        not null                    comment '装箱日期',
  so_code               varchar(64)                                 comment '销售订单编号',
  invoice_code          varchar(255)                                comment '发票编号',
  client_id             bigint(20)                                  comment '客户ID',
  client_code           varchar(64)                                 comment '客户编码',
  client_name           varchar(255)                                comment '客户名称',
  client_nick           varchar(255)                                comment '客户简称',
  package_length        double(12,4)                                comment '箱长度',
  package_width         double(12,4)                                comment '箱宽度',
  package_height        double(12,4)                                comment '箱高度',
  size_unit             varchar(64)                                 comment '尺寸单位',
  net_weight            double(12,4)                                comment '净重',
  cross_weight          double(12,4)                                comment '毛重',
  weight_unit           varchar(64)                                 comment '重量单位',
  inspector             varchar(64)                                 comment '检查员用户名',
  inspector_name        varchar(64)                                 comment '检查员名称',
  status                varchar(64)     default 'PREPARE'           comment '状态',
  enable_flag           char(1)         default 'Y'                 comment '是否生效',
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (package_id)
) engine=innodb auto_increment=200 comment = '装箱单表';



-- ----------------------------
-- 20、装箱明细表
-- ----------------------------
drop table if exists wm_package_line;
create table wm_package_line (
  line_id               bigint(20)      not null auto_increment     comment '明细行ID',
  package_id            bigint(20)      not null                    comment '装箱单ID',
  material_stock_id     bigint(20)                                  comment '库存记录ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  quantity_package      double(12,2)    not null                    comment '装箱数量',
  workorder_id          bigint(20)                                  comment '生产工单ID',
  workorder_code        varchar(64)                                 comment '生产工单编号',
  batch_code            varchar(255)                                comment '批次号',
  warehouse_id          bigint(20)                                  comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称',   
  expire_date           datetime                                    comment '有效期',
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (line_id)
) engine=innodb auto_increment=200 comment = '装箱明细表';



-- ----------------------------
-- 21、转移单表
-- ----------------------------
drop table if exists wm_transfer;
create table wm_transfer (
  transfer_id           bigint(20)      not null auto_increment     comment '转移单ID',
  transfer_code         varchar(64)     not null                    comment '转移单编号',
  transfer_name         varchar(255)                                comment '转移单名称',  
  transfer_type         varchar(64)     not null                    comment '转移单类型',
  delivery_flag         char(1)         default 'N'                 comment '是否配送',
  recipient             varchar(64)                                 comment '收货人',
  tel                   varchar(128)                                comment '联系方式',
  destination           varchar(255)                                comment '目的地',
  carrier               varchar(64)                                 comment '承运商',
  shipping_number       varchar(128)                                comment '运输单号',
  confirm_flag          char(1)         default 'N'                 comment '是否已确认',       
  transfer_date         datetime                                    comment '转移日期',  
  status                varchar(64)     default 'PREPARE'           comment '单据状态',  
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (transfer_id)
) engine=innodb auto_increment=200 comment = '转移单表';


-- ----------------------------
-- 22、转移单行表
-- ----------------------------
drop table if exists wm_transfer_line;
create table wm_transfer_line (
  line_id               bigint(20)      not null auto_increment     comment '明细行ID',
  transfer_id           bigint(20)      not null                    comment '装箱单ID',
  material_stock_id     bigint(20)      not null                    comment '库存记录ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(64)                                 comment '单位名称',
  quantity_transfer     double(12,2)    not null                    comment '转移数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',
  warehouse_id          bigint(20)                                  comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称', 
  quality_status        varchar(64)                                 comment '质量状态',
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (line_id)
) engine=innodb auto_increment=200 comment = '转移单行表';


-- ----------------------------
-- 13、销售退货记录明细表
-- ----------------------------
drop table if exists wm_transfer_detail;
create table wm_transfer_detail (
  detail_id             bigint(20)      not null auto_increment     comment '明细ID',
  line_id               bigint(20)      not null                    comment '行ID',
  transfer_id           bigint(20)                                  comment '转移单ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(64)                                 comment '单位名称',
  quantity              double(12,2)    not null                    comment '转移数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',
  warehouse_id          bigint(20)                                  comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称', 
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (detail_id)
) engine=innodb auto_increment=200 comment = '转移调拨单明细表';



-- ----------------------------
-- 23、SN码表
-- ----------------------------
drop table if exists wm_sn;
create table wm_sn (
  sn_id                 bigint(20)      not null auto_increment     comment 'SN码ID',
  sn_code               varchar(64)     not null                    comment 'SN码',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  batch_code            varchar(255)                                comment '批次号',
  gen_date              datetime                                    comment '生成时间',
  workorder_id          bigint(20)                                  comment '生产工单ID',
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (sn_id)
) engine=innodb auto_increment=200 comment = 'SN码表';



-- ----------------------------
-- 24、库存盘点方案表
-- ----------------------------
drop table if exists wm_stock_taking_plan;
create table wm_stock_taking_plan (
  plan_id               bigint(20)      not null auto_increment     comment '盘点方案ID',
  plan_code             varchar(64)     not null                    comment '盘点方案编号', 
  plan_name             varchar(255)                                comment '盘点方案名称',    
  taking_type           varchar(64)     not null                    comment '盘点类型',  
  start_time            datetime                                    comment '开始时间',
  end_time              datetime                                    comment '结束时间',
  blind_flag            char(1)         default 'N'                 comment '是否盲盘',
  frozen_flag           char(1)         default 'Y'                 comment '是否库存冻结',
  enable_flag           char(1)         default 'Y'                 comment '是否启用',
  data_sql              text                                        comment '数据过滤SQL',
  status                varchar(64)     default 'PREPARE'           comment '单据状态',  
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (plan_id)
) engine=innodb auto_increment=200 comment = '库存盘点方案表';


-- ----------------------------
-- 24、库存盘点方案参数表
-- ----------------------------
drop table if exists wm_stock_taking_param;
create table wm_stock_taking_param (
  param_id              bigint(20)      not null auto_increment     comment '参数ID',
  plan_id               bigint(20)      not null                    comment '方案ID', 
  param_type            varchar(64)                                 comment '条件类型',
  param_value_id        bigint(20)                                  comment '条件值ID',
  param_value_code      varchar(64)                                 comment '条件值编码',
  param_value_name      varchar(128)                                comment '条件值名称',
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (param_id)
) engine=innodb auto_increment=200 comment = '库存盘点方案参数表';



-- ----------------------------
-- 24、库存盘点任务表
-- ----------------------------
drop table if exists wm_stock_taking;
create table wm_stock_taking (
  taking_id             bigint(20)      not null auto_increment     comment '盘点单ID',
  taking_code           varchar(64)     not null                    comment '盘点单编号', 
  taking_name           varchar(255)                                comment '盘点单名称',
  taking_date           datetime        not null                    comment '盘点日期',
  taking_type           varchar(64)     not null                    comment '盘点类型',
  user_id               bigint(20)                                  comment '盘点人ID',
  user_name             varchar(64)                                 comment '盘点人用户名',
  nick_name             varchar(64)                                 comment '盘点人',
  blind_flag            char(1)         default 'N'                 comment '是否盲盘',
  frozen_flag           char(1)         default 'Y'                 comment '是否库存冻结',
  plan_id               bigint(20)                                  comment '方案ID',
  plan_code             varchar(64)                                 comment '方案编号',
  plan_name             varchar(128)                                comment '方案名称',  
  start_time            datetime                                    comment '开始时间',
  end_time              datetime                                    comment '结束时间',
  status                varchar(64)     default 'PREPARE'           comment '单据状态',  
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (taking_id)
) engine=innodb auto_increment=200 comment = '库存盘点任务表';



-- ----------------------------
-- 25、库存盘点明细表
-- ----------------------------
drop table if exists wm_stock_taking_line;
create table wm_stock_taking_line (
  line_id               bigint(20)      not null auto_increment     comment '行ID',
  taking_id             bigint(20)                                  comment '报废单ID',
  material_stock_id     bigint(20)                                  comment '库存ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(64)                                 comment '单位名称',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(128)                                comment '批次编号',
  quantity              int(11)         not null default 1          comment '数量',
  taking_quantity       int(11)                                     comment '盘点数量',
  warehouse_id          bigint(20)                                  comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称', 
  taking_status         varchar(64)     not null default 'LOSS'     comment '盘点状态',
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (line_id)
) engine=innodb auto_increment=200 comment = '库存盘点明细表';


-- ----------------------------
-- 26、库存盘点结果表
-- ----------------------------
drop table if exists wm_stock_taking_result;
create table wm_stock_taking_result (
  result_id             bigint(20)      not null auto_increment     comment '结果ID',
  taking_id             bigint(20)                                  comment '盘点单ID',
  line_id               bigint(20)                                  comment '行ID',
  material_stock_id     bigint(20)                                  comment '库存ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(64)                                 comment '单位名称',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(128)                                comment '批次编号',
  warehouse_id          bigint(20)                                  comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称', 
  quantity              int(11)         not null default 1          comment '数量',
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (result_id)
) engine=innodb auto_increment=200 comment = '库存盘点结果表';



-- ----------------------------
-- 27、外协领料单头表
-- ----------------------------
drop table if exists wm_outsource_issue;
create table wm_outsource_issue (
  issue_id              bigint(20)      not null auto_increment     comment '领料单ID',
  issue_code            varchar(64)     not null                    comment '领料单编号',
  issue_name            varchar(255)    not null                    comment '领料单名称',
  workorder_id          bigint(20)                                  comment '生产工单ID',
  workorder_code        varchar(64)                                 comment '生产工单编码',  
  vendor_id             bigint(20)                                  comment '供应商ID',
  vendor_code           varchar(64)                                 comment '供应商编码',
  vendor_name           varchar(255)                                comment '供应商名称',
  vendor_nick           varchar(255)                                comment '供应商简称',
  issue_date            datetime                                    comment '领料日期',
  status                varchar(64)     default 'PREPARE'           comment '单据状态',  
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (issue_id)
) engine=innodb auto_increment=200 comment = '外协领料单头表';


-- ----------------------------
-- 28、外协领料单行表
-- ----------------------------
drop table if exists wm_outsource_issue_line;
create table wm_outsource_issue_line (
  line_id               bigint(20)      not null auto_increment     comment '行ID',
  issue_id              bigint(20)                                  comment '领料单ID',
  material_stock_id     bigint(20)                                  comment '库存ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(64)                                 comment '单位名称',
  quantity_issued       double(12,2)    not null                    comment '领料数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号', 
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (line_id)
) engine=innodb auto_increment=200 comment = '外协领料单行表';


-- ----------------------------
-- 28、外协领料单明细表
-- ----------------------------
drop table if exists wm_outsource_issue_detail;
create table wm_outsource_issue_detail (
  detail_id             bigint(20)      not null auto_increment     comment '明细ID',
  line_id               bigint(20)      not null                    comment '行ID',
  issue_id              bigint(20)                                  comment '领料单ID',
  material_stock_id     bigint(20)                                  comment '库存ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(64)                                 comment '单位名称',
  quantity              double(12,2)    not null                    comment '退货数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',
  warehouse_id          bigint(20)                                  comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称', 
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (detail_id)
) engine=innodb auto_increment=200 comment = '外协领料单明细表';



-- ----------------------------
-- 29、外协入库单表
-- ----------------------------
drop table if exists wm_outsource_recpt;
create table wm_outsource_recpt (
  recpt_id              bigint(20)      not null auto_increment     comment '入库单ID',
  recpt_code            varchar(64)     not null                    comment '入库单编号',
  recpt_name            varchar(255)    not null                    comment '入库单名称',
  workorder_id          bigint(20)                                  comment '外协工单ID',
  workorder_code        varchar(64)                                 comment '外协工单编号',  
  vendor_id             bigint(20)                                  comment '供应商ID',
  vendor_code           varchar(64)                                 comment '供应商编码',
  vendor_name           varchar(255)                                comment '供应商名称',
  vendor_nick           varchar(255)                                comment '供应商简称',
  recpt_date            datetime                                    comment '入库日期',
  status                varchar(64)     default 'PREPARE'           comment '单据状态',  
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (recpt_id)
) engine=innodb auto_increment=200 comment = '外协入库单表';


-- ----------------------------
-- 30、外协入库单行表
-- ----------------------------
drop table if exists wm_outsource_recpt_line;
create table wm_outsource_recpt_line (
  line_id               bigint(20)      not null auto_increment     comment '行ID',
  recpt_id              bigint(20)                                  comment '入库单ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(64)                                 comment '单位名称',
  quantity_recived      double(12,2)    not null                    comment '入库数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',
  produce_date          datetime                                    comment '生产日期',
  lot_number            varchar(128)                                comment '生产批号',
  expire_date           datetime                                    comment '有效期', 
  quality_status        varchar(64)     default 'NT'                comment '质量状态',
  iqc_check             char(1)                                     comment '是否检验',
  iqc_id                bigint(20)                                  comment '来料检验单ID',
  iqc_code              varchar(64)                                 comment '来料检验单编号',                 
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (line_id)
) engine=innodb auto_increment=200 comment = '外协入库单行表';


-- ----------------------------
-- 28、外协入库单明细表
-- ----------------------------
drop table if exists wm_outsource_recpt_detail;
create table wm_outsource_recpt_detail (
  detail_id             bigint(20)      not null auto_increment     comment '明细ID',
  line_id               bigint(20)      not null                    comment '行ID',
  recpt_id              bigint(20)                                  comment '领料单ID',
  material_stock_id     bigint(20)                                  comment '库存ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(64)                                 comment '单位名称',
  quantity              double(12,2)    not null                    comment '入库数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',
  warehouse_id          bigint(20)                                  comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称', 
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (detail_id)
) engine=innodb auto_increment=200 comment = '外协入库单明细表';





-- ----------------------------
-- 31、批次记录表
-- ----------------------------
drop table if exists wm_batch;
create table wm_batch (
  batch_id              bigint(20)      not null auto_increment     comment '批次ID',
  batch_code            varchar(64)     not null                    comment '批次编号',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  produce_date          datetime                                    comment '生产日期',
  expire_date           datetime                                    comment '有效期',
  recpt_date            datetime                                    comment '入库日期',
  vendor_id             bigint(20)                                  comment '供应商ID',
  vendor_code           varchar(64)                                 comment '供应商编码',
  vendor_name           varchar(255)                                comment '供应商名称',
  vendor_nick           varchar(255)                                comment '供应商简称',
  client_id             bigint(20)                                  comment '客户ID',
  client_code           varchar(64)                                 comment '客户编码',
  client_name           varchar(255)                                comment '客户名称',
  client_nick           varchar(255)                                comment '客户简称',
  co_code               varchar(64)                                 comment '销售订单编号',
  po_code               varchar(64)                                 comment '采购订单编号',
  workorder_id          bigint(20)                                  comment '生产工单ID',
  workorder_code        varchar(64)                                 comment '生产工单编码',  
  task_id               bigint(20)                                  comment '生产任务ID',
  task_code             varchar(64)                                 comment '生产任务编号',
  workstation_id        bigint(20)                                  comment '工作站ID',
  workstation_code      varchar(64)                                 comment '工作站编码',
  tool_id               bigint(20)                                  comment '工具ID',
  tool_code             varchar(64)                                 comment '工具编号',
  mold_id               bigint(20)                                  comment '模具ID',
  mold_code             varchar(64)                                 comment '模具编号',
  lot_number            varchar(128)                                comment '生产批号',
  quality_status        varchar(64)                                 comment '质量状态',
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (batch_id)
) engine=innodb auto_increment=200 comment = '批次记录表';



-- ----------------------------
-- 32、备料通知单
-- ----------------------------
drop table if exists wm_materialrequest_notice;
create table wm_materialrequest_notice (
  notice_id             bigint(20)      not null auto_increment     comment '通知ID',
  workstation_id        bigint(20)                                  comment '工作站ID',
  workstaiton_code      varchar(64)                                 comment '工作站编号',
  workorder_id          bigint(20)                                  comment '生产工单ID',
  workorder_code        varchar(64)                                 comment '生产工单编号', 
  user_id               bigint(20)                                  comment '需求人',
  user_name             varchar(64)                                 comment '用户名',
  nick_name             varchar(128)                                comment '昵称',
  request_time          datetime                                    comment '需求时间',
  start_time            datetime                                    comment '开始备料时间',
  end_time              datetime                                    comment '完成时间',
  status                varchar(64)                                 comment '状态',
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (notice_id)
) engine=innodb auto_increment=200 comment = '备料通知单';


-- ----------------------------
-- 33、备料通知单明细
-- ----------------------------
drop table if exists wm_materialrequest_notice_line;
create table wm_materialrequest_notice_line (
  line_id               bigint(20)      not null auto_increment     comment '行ID',
  notice_id             bigint(20)      not null                    comment '通知ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(64)                                 comment '单位名称',
  quantity              int(11)         not null default 1          comment '需求数量',
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (line_id)
) engine=innodb auto_increment=200 comment = '备料通知单明细';


-- ----------------------------
-- 34、杂项入库单表
-- ----------------------------
drop table if exists wm_misc_recpt;
create table wm_misc_recpt (
  recpt_id              bigint(20)      not null auto_increment     comment '杂项入库单ID',
  recpt_code            varchar(64)     not null                    comment '杂项入库单编号',
  recpt_name            varchar(255)    not null                    comment '杂项入库单名称',
  misc_type             varchar(64)                                 comment '杂项事务类型',
  source_doc_id         bigint(20)                                  comment '来源单据ID',
  source_doc_code       varchar(64)                                 comment '来源单据编号',
  source_doc_type       varchar(64)                                 comment '来源单据类型', 
  recpt_date            datetime                                    comment '入库日期',
  status                varchar(64)     default 'PREPARE'           comment '单据状态',  
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (recpt_id)
) engine=innodb auto_increment=200 comment = '杂项入库单表';



-- ----------------------------
-- 35、杂项入库单行表
-- ----------------------------
drop table if exists wm_misc_recpt_line;
create table wm_misc_recpt_line (
  line_id               bigint(20)      not null auto_increment     comment '行ID',
  recpt_id              bigint(20)                                  comment '杂项入库单ID',
  source_doc_line_id    bigint(20)                                  comment '来源单据行ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(128)                                comment '单位名称',
  quantity_recived      double(12,2)    not null                    comment '入库数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',
  warehouse_id          bigint(20)                                  comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称', 
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (line_id)
) engine=innodb auto_increment=200 comment = '杂项入库单行表';


-- ----------------------------
-- 7、杂项入库单明细表
-- ----------------------------
drop table if exists wm_misc_recpt_detail;
create table wm_misc_recpt_detail (
  detail_id             bigint(20)      not null auto_increment     comment '明细ID',
  line_id               bigint(20)                                  comment '行ID',
  recpt_id              bigint(20)                                  comment '杂项入库单ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(128)                                comment '单位名称',
  quantity              double(12,2)    not null                    comment '入库数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',
  warehouse_id          bigint(20)                                  comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称',         
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (detail_id)
) engine=innodb auto_increment=200 comment = '杂项入库单明细表';



-- ----------------------------
-- 34、杂项出库单表
-- ----------------------------
drop table if exists wm_misc_issue;
create table wm_misc_issue (
  issue_id              bigint(20)      not null auto_increment     comment '杂项出库单ID',
  issue_code            varchar(64)     not null                    comment '杂项出库单编号',
  issue_name            varchar(255)    not null                    comment '杂项出库单名称',
  misc_type             varchar(64)                                 comment '杂项事务类型',
  source_doc_id         bigint(20)                                  comment '来源单据ID',
  source_doc_code       varchar(64)                                 comment '来源单据编号',
  source_doc_type       varchar(64)                                 comment '来源单据类型', 
  issue_date            datetime                                    comment '出库日期',
  status                varchar(64)     default 'PREPARE'           comment '单据状态',  
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (issue_id)
) engine=innodb auto_increment=200 comment = '杂项出库单表';



-- ----------------------------
-- 35、杂项出库单行表
-- ----------------------------
drop table if exists wm_misc_issue_line;
create table wm_misc_issue_line (
  line_id               bigint(20)      not null auto_increment     comment '行ID',
  issue_id              bigint(20)                                  comment '杂项出库单ID',
  source_doc_line_id    bigint(20)                                  comment '来源单据行ID',
  material_stock_id     bigint(20)                                  comment '库存记录ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(128)                                comment '单位名称',
  quantity_issued       double(12,2)    not null                    comment '出库数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',
  warehouse_id          bigint(20)                                  comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称', 
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (line_id)
) engine=innodb auto_increment=200 comment = '杂项出库单行表';


-- ----------------------------
-- 7、杂项出库单明细表
-- ----------------------------
drop table if exists wm_misc_issue_detail;
create table wm_misc_issue_detail (
  detail_id             bigint(20)      not null auto_increment     comment '明细ID',
  line_id               bigint(20)                                  comment '行ID',
  issue_id              bigint(20)                                  comment '杂项出库单ID',
  material_stock_id     bigint(20)                                  comment '库存记录ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  unit_name             varchar(128)                                comment '单位名称',
  quantity              double(12,2)    not null                    comment '入库数量',
  batch_id              bigint(20)                                  comment '批次ID',
  batch_code            varchar(255)                                comment '批次号',
  warehouse_id          bigint(20)                                  comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称',         
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time           datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (detail_id)
) engine=innodb auto_increment=200 comment = '杂项出库单明细表';