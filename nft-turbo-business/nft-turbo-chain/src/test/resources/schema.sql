/******************************************/
/*   DatabaseName = nfturbo   */
/*   TableName = chain_operate_info   */
/******************************************/
CREATE TABLE `chain_operate_info` (
      `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID（自增主键）',
      `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
      `gmt_modified` datetime DEFAULT NULL COMMENT '最后更新时间',
      `chain_type` varchar(64) DEFAULT NULL COMMENT '链类型',
      `biz_id` varchar(128) DEFAULT NULL COMMENT '业务id',
      `biz_type` varchar(64) DEFAULT NULL COMMENT '业务类型',
      `operate_type` varchar(64) DEFAULT NULL COMMENT '操作类型',
      `state` varchar(64) DEFAULT NULL COMMENT '状态',
      `operate_time` datetime DEFAULT NULL COMMENT '操作时间',
      `succeed_time` datetime DEFAULT NULL COMMENT '成功时间',
      `param` text COMMENT '入参',
      `result` text COMMENT '返回结果',
      `out_biz_id` varchar(128) DEFAULT NULL COMMENT '外部业务id',
      `deleted` int DEFAULT NULL COMMENT '是否逻辑删除，0为未删除，非0为已删除',
      `lock_version` int DEFAULT NULL COMMENT '乐观锁版本号',
      PRIMARY KEY (`id`)
)  ;
