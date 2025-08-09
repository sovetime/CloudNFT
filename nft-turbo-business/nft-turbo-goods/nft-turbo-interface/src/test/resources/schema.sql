
/******************************************/
/*   DatabaseName = nfturbo   */
/*   TableName = goods_book   */
/******************************************/
CREATE TABLE IF NOT EXISTS `goods_book` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID（自增主键）',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '最后更新时间',
  `goods_id` varchar(128) DEFAULT NULL COMMENT '商品名称',
  `goods_type` varchar(128)  DEFAULT NULL COMMENT '商品类型',
  `buyer_id` varchar(128) DEFAULT NULL COMMENT '买家id',
  `buyer_type` varchar(128)  DEFAULT NULL COMMENT '买家类型',
  `identifier` varchar(128)  DEFAULT NULL COMMENT '幂等号',
  `book_succeed_time` datetime DEFAULT NULL COMMENT '预定成功时间',
  `deleted` int DEFAULT NULL COMMENT '是否逻辑删除，0为未删除，非0为已删除',
  `lock_version` int DEFAULT NULL COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`)
) ;


/******************************************/
/*   DatabaseName = nfturbo   */
/*   TableName = collection   */
/******************************************/
CREATE TABLE IF NOT EXISTS  `collection` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID（自增主键）',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '最后更新时间',
  `name` varchar(512) DEFAULT NULL COMMENT '藏品名称',
  `cover` varchar(512) DEFAULT NULL COMMENT '藏品封面',
  `class_id` varchar(128) DEFAULT NULL COMMENT '藏品类目ID',
  `price` decimal(10,0) DEFAULT NULL COMMENT '价格',
  `quantity` bigint DEFAULT NULL COMMENT '藏品数量',
  `detail` text COMMENT '藏品详情',
  `saleable_inventory` bigint DEFAULT NULL COMMENT '可销售库存',
  `occupied_inventory` bigint DEFAULT NULL COMMENT '已占用库存',
  `identifier` varchar(128) DEFAULT NULL COMMENT '幂等号',
   `version` int DEFAULT NULL COMMENT '版本号',
  `state` varchar(128) DEFAULT NULL COMMENT '状态',
  `creator_id` varchar(128) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '藏品创建时间',
  `sale_time` datetime DEFAULT NULL COMMENT '藏品发售时间',
  `sync_chain_time` datetime DEFAULT NULL COMMENT '藏品上链时间',
  `book_start_time` datetime DEFAULT NULL COMMENT '预约开始时间',
  `book_end_time` datetime DEFAULT NULL COMMENT '预约结束时间',
   `can_book` int DEFAULT NULL COMMENT '是否可以预约',
  `deleted` int DEFAULT NULL COMMENT '是否逻辑删除，0为未删除，非0为已删除',
  `lock_version` int DEFAULT NULL COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`)
)
;
/******************************************/
/*   DatabaseName = nfturbo   */
/*   TableName = held_collection   */
/******************************************/
CREATE TABLE IF NOT EXISTS  `held_collection` (
   `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID（自增主键）',
   `gmt_create` datetime NOT NULL COMMENT '创建时间',
   `gmt_modified` datetime NOT NULL COMMENT '最后更新时间',
   `collection_id` bigint unsigned DEFAULT NULL COMMENT '藏品id',
   `serial_no` varchar(256) DEFAULT NULL COMMENT '藏品编号',
   `nft_id` varchar(256) DEFAULT NULL COMMENT 'NFT唯一编号',
   `pre_id` varchar(128) DEFAULT NULL COMMENT '上一个持有人id',
   `user_id` varchar(128) DEFAULT NULL COMMENT '持有人id',
   `state` varchar(128) DEFAULT NULL COMMENT '状态',
   `name` varchar(512) DEFAULT NULL COMMENT '藏品名称',
   `cover` varchar(512) DEFAULT NULL COMMENT '藏品封面',
   `purchase_price` decimal(10,0) DEFAULT NULL COMMENT '购入价格',
   `biz_type` varchar(128) DEFAULT NULL COMMENT ' 业务类型',
   `biz_no` varchar(128) DEFAULT NULL COMMENT '业务单据号',
   `tx_hash` varchar(256) DEFAULT NULL COMMENT '交易hash',
   `hold_time` datetime DEFAULT NULL COMMENT '藏品持有时间',
   `sync_chain_time` datetime DEFAULT NULL COMMENT '藏品同步时间',
   `delete_time` datetime DEFAULT NULL COMMENT '藏品销毁时间',
   `deleted` int DEFAULT NULL COMMENT '是否逻辑删除，0为未删除，非0为已删除',
   `lock_version` int DEFAULT NULL COMMENT '乐观锁版本号',
    `reference_price` decimal(18,6) DEFAULT NULL COMMENT ' 参考价格',
    `rarity` varchar(64) DEFAULT NULL COMMENT ' 稀有度',
   PRIMARY KEY (`id`)
)
;

/******************************************/
/*   DatabaseName = nfturbo   */
/*   TableName = collection_stream   */
/******************************************/
CREATE TABLE IF NOT EXISTS  `collection_stream` (
     `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID（自增主键）',
     `collection_id` bigint unsigned NOT NULL  COMMENT '藏品ID',
     `gmt_create` datetime NOT NULL COMMENT '创建时间',
     `gmt_modified` datetime NOT NULL COMMENT '最后更新时间',
     `name` varchar(512)  DEFAULT NULL COMMENT '藏品名称',
     `cover` varchar(512)   DEFAULT NULL COMMENT '藏品封面',
     `class_id` varchar(128)  DEFAULT NULL COMMENT '藏品类目ID',
     `stream_type` varchar(128)  DEFAULT NULL COMMENT '流水类型',
     `price` decimal(10,0) DEFAULT NULL COMMENT '价格',
     `quantity` bigint DEFAULT NULL COMMENT '藏品数量',
     `detail` text  COMMENT '详情',
     `saleable_inventory` bigint DEFAULT NULL COMMENT '可销售库存',
     `occupied_inventory` bigint DEFAULT NULL COMMENT '已占用库存',
     `state` varchar(128)  DEFAULT NULL COMMENT '状态',
     `create_time` datetime DEFAULT NULL COMMENT '藏品创建时间',
     `sale_time` datetime DEFAULT NULL COMMENT '藏品发售时间',
     `sync_chain_time` datetime DEFAULT NULL COMMENT '藏品上链时间',
     `identifier` varchar(128) DEFAULT NULL COMMENT '幂等号',
     `deleted` int DEFAULT NULL COMMENT '是否逻辑删除，0为未删除，非0为已删除',
     `lock_version` int DEFAULT NULL COMMENT '乐观锁版本号',
     PRIMARY KEY (`id`)
) ;

/******************************************/
/*   DatabaseName = nfturbo   */
/*   TableName = collection_snapshot   */
/******************************************/
CREATE TABLE IF NOT EXISTS  `collection_snapshot` (
       `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID（自增主键）',
       `gmt_create` datetime NOT NULL COMMENT '创建时间',
       `gmt_modified` datetime NOT NULL COMMENT '最后更新时间',
       `collection_id` bigint NOT NULL COMMENT '藏品id',
       `name` varchar(512) DEFAULT NULL COMMENT '藏品名称',
       `cover` varchar(512) DEFAULT NULL COMMENT '藏品封面',
       `class_id` varchar(128) DEFAULT NULL COMMENT '藏品类目ID',
       `price` decimal(18,6) DEFAULT NULL COMMENT '价格',
       `quantity` bigint DEFAULT NULL COMMENT '藏品数量',
       `detail` text COMMENT '详情',
       `saleable_inventory` bigint DEFAULT NULL COMMENT '可销售库存',
       `create_time` datetime DEFAULT NULL COMMENT '藏品创建时间',
       `sale_time` datetime DEFAULT NULL COMMENT '藏品发售时间',
       `sync_chain_time` datetime DEFAULT NULL COMMENT '藏品上链时间',
       `deleted` int DEFAULT NULL COMMENT '是否逻辑删除，0为未删除，非0为已删除',
       `lock_version` int DEFAULT NULL COMMENT '乐观锁版本号',
       `creator_id` varchar(128) DEFAULT NULL COMMENT '创建者',
       `version` int DEFAULT NULL COMMENT '修改版本',
       PRIMARY KEY (`id`)
) ;

/******************************************/
/*   DatabaseName = nfturbo   */
/*   TableName = collection_inventory_stream   */
/******************************************/
CREATE TABLE IF NOT EXISTS  `collection_inventory_stream` (
   `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID（自增主键）',
   `gmt_create` datetime NOT NULL COMMENT '创建时间',
   `gmt_modified` datetime NOT NULL COMMENT '最后更新时间',
   `collection_id` bigint DEFAULT NULL COMMENT '藏品id',
   `changed_quantity` bigint DEFAULT NULL COMMENT '本次变更的数量',
   `price` decimal(18,6) DEFAULT NULL COMMENT '价格',
   `quantity` bigint DEFAULT NULL COMMENT '藏品数量',
   `state` varchar(128)  DEFAULT NULL COMMENT '状态',
   `saleable_inventory` bigint DEFAULT NULL COMMENT '可售库存',
   `occupied_inventory` bigint DEFAULT NULL COMMENT '已占库存',
   `stream_type` varchar(128)  DEFAULT NULL COMMENT '流水类型',
   `identifier` varchar(128) DEFAULT NULL COMMENT '幂等号',
   `deleted` int DEFAULT NULL COMMENT '是否逻辑删除，0为未删除，非0为已删除',
   `lock_version` int DEFAULT NULL COMMENT '乐观锁版本号',
   `extend_info` varchar(512) DEFAULT NULL COMMENT '扩展信息',
   PRIMARY KEY (`id`)
) ;

/******************************************/
/*   DatabaseName = nfturbo   */
/*   TableName = collection_airdrop_stream   */
/******************************************/
CREATE TABLE IF NOT EXISTS `collection_airdrop_stream` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID（自增主键）',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '最后更新时间',
  `collection_id` bigint DEFAULT NULL COMMENT '藏品id',
  `recipient_user_id` varchar(128) DEFAULT NULL COMMENT '接收用户ID',
  `quantity` bigint DEFAULT NULL COMMENT '藏品空投数量',
  `stream_type` varchar(128) DEFAULT NULL COMMENT '流水类型',
  `identifier` varchar(128)  DEFAULT NULL COMMENT '幂等号',
  `deleted` int DEFAULT NULL COMMENT '是否逻辑删除，0为未删除，非0为已删除',
  `lock_version` int DEFAULT NULL COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`)
) ;

/******************************************/
/*   DatabaseName = nfturbo   */
/*   TableName = blind_box   */
/******************************************/
CREATE TABLE IF NOT EXISTS  `blind_box` (
 `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID（自增主键）',
 `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
 `gmt_modified` datetime DEFAULT NULL COMMENT '最后更新时间',
 `name` varchar(512) DEFAULT NULL COMMENT '盲盒名称',
 `cover` varchar(512) DEFAULT NULL COMMENT '盲盒封面',
 `detail` text COMMENT '详情',
 `identifier` varchar(128) DEFAULT NULL COMMENT '幂等号',
 `state` varchar(128) DEFAULT NULL COMMENT '状态',
 `quantity` bigint DEFAULT NULL COMMENT '盲盒数量',
 `price` decimal(18,6) DEFAULT NULL COMMENT '价格',
 `saleable_inventory` bigint DEFAULT NULL COMMENT '可销售库存',
 `occupied_inventory` bigint DEFAULT NULL COMMENT '已占用库存',
 `create_time` datetime DEFAULT NULL COMMENT '盲盒创建时间',
 `sale_time` datetime DEFAULT NULL COMMENT '盲盒发售时间',
 `allocate_rule` varchar(512) DEFAULT NULL COMMENT '盲盒分配规则',
 `sync_chain_time` datetime DEFAULT NULL COMMENT '上链时间',
 `creator_id` varchar(128) DEFAULT NULL COMMENT '创建者',
`book_start_time` datetime DEFAULT NULL COMMENT '预约开始时间',
`book_end_time` datetime DEFAULT NULL COMMENT '预约结束时间',
`can_book` int DEFAULT NULL COMMENT '是否可以预约',
 `collection_configs` text COMMENT '藏品配置',
 `deleted` int DEFAULT NULL COMMENT '是否逻辑删除，0为未删除，非0为已删除',
 `lock_version` int DEFAULT NULL COMMENT '乐观锁版本号',
 PRIMARY KEY (`id`)
) ;


/******************************************/
/*   DatabaseName = nfturbo   */
/*   TableName = blind_box_inventory_stream   */
/******************************************/
CREATE TABLE IF NOT EXISTS  `blind_box_inventory_stream` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID（自增主键）',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '最后更新时间',
  `blind_box_id` bigint DEFAULT NULL COMMENT '盲盒id',
  `changed_quantity` bigint DEFAULT NULL COMMENT '本次变更的数量',
  `price` decimal(18,6) DEFAULT NULL COMMENT '价格',
  `quantity` bigint DEFAULT NULL COMMENT '藏品数量',
  `state` varchar(128)  DEFAULT NULL COMMENT '状态',
  `saleable_inventory` bigint DEFAULT NULL COMMENT '可售库存',
  `occupied_inventory` bigint DEFAULT NULL COMMENT '已占库存',
  `stream_type` varchar(128)  DEFAULT NULL COMMENT '流水类型',
  `identifier` varchar(128)  DEFAULT NULL COMMENT '幂等号',
  `deleted` int DEFAULT NULL COMMENT '是否逻辑删除，0为未删除，非0为已删除',
  `lock_version` int DEFAULT NULL COMMENT '乐观锁版本号',
  `extend_info` varchar(512)   DEFAULT NULL COMMENT '扩展信息',
  PRIMARY KEY (`id`)
) ;

/******************************************/
/*   DatabaseName = nfturbo   */
/*   TableName = blind_box_item   */
/******************************************/
CREATE TABLE IF NOT EXISTS  `blind_box_item` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID（自增主键）',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '最后更新时间',
  `blind_box_id` bigint DEFAULT NULL COMMENT '盲盒id',
  `name` varchar(512) DEFAULT NULL COMMENT '盲盒名称',
  `cover` varchar(512) DEFAULT NULL COMMENT '盲盒封面',
  `collection_name` varchar(512)    DEFAULT NULL COMMENT '藏品名称',
  `collection_cover` varchar(512) DEFAULT NULL COMMENT '藏品封面',
  `collection_detail` text COMMENT '藏品详情',
  `collection_serial_no` varchar(128) DEFAULT NULL COMMENT '持有藏品的序列号',
  `state` varchar(128) DEFAULT NULL COMMENT '状态',
  `user_id` varchar(128) DEFAULT NULL COMMENT '持有人id',
  `purchase_price` decimal(18,6) DEFAULT NULL COMMENT '购入价格',
  `order_id` varchar(128) DEFAULT NULL COMMENT '订单号',
  `deleted` int DEFAULT NULL COMMENT '是否逻辑删除，0为未删除，非0为已删除',
  `lock_version` int DEFAULT NULL COMMENT '乐观锁版本号',
  `rarity` varchar(32) DEFAULT NULL COMMENT '稀有度',
  `reference_price` decimal(18,6) DEFAULT NULL COMMENT '市场参考价',
  `opened_time` datetime DEFAULT NULL COMMENT ' 开盒时间',
  `assign_time` datetime DEFAULT NULL COMMENT ' 分配时间',
  PRIMARY KEY (`id`)
) ;

CREATE TABLE IF NOT EXISTS  `held_collection_stream` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  `held_collection_id` bigint NOT NULL COMMENT '持有藏品的id',
  `stream_type` varchar(64) NOT NULL COMMENT '流水类型',
  `operator` varchar(64) NOT NULL COMMENT '操作者',
  `identifier` varchar(128) NOT NULL COMMENT '幂等号',
  `deleted` tinyint NULL COMMENT ' 逻辑删除',
  `lock_version` int NULL COMMENT ' 版本号',
  PRIMARY KEY (`id`)
)