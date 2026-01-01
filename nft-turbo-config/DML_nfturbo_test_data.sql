-- users 测试数据导入

INSERT INTO `users` (`id`, `gmt_create`, `gmt_modified`, `nick_name`, `password_hash`, `state`, `invite_code`,
                     `telephone`, `inviter_id`, `last_login_time`, `profile_photo_url`, `block_chain_url`,
                     `block_chain_platform`, `certification`, `real_name`, `id_card_no`, `user_role`, `deleted`,
                     `lock_version`)
VALUES (29, '2024-05-26 12:07:38', '2024-06-10 14:14:20', '藏家_zH9sQA0bob1', 'e7beea81b7a03b38508428fbeeb3c69a',
        'ACTIVE', null, '18000000000', null, null,
        'https://nfturbo-file.oss-cn-hangzhou.aliyuncs.com/profile/29/O1CN014qjUuW1IKL1Ur3fGI_!!2213143710874.jpg_Q75.jpg_.avif',
        'iaa1w57n8adlxcvgas93c5x4j36msen3uxl0jxg7mg', 'WEN_CHANG', 1, '446ad47811888a04c6610741aff349c1',
        '670c02c9ce418d783fad1622c007ace8ac5f47acb1a393455f794d541f80d58c', 'CUSTOMER', 0, 10);

INSERT INTO `users` (`id`, `gmt_create`, `gmt_modified`, `nick_name`, `password_hash`, `state`, `invite_code`,
                     `telephone`, `inviter_id`, `last_login_time`, `profile_photo_url`, `block_chain_url`,
                     `block_chain_platform`, `certification`, `real_name`, `id_card_no`, `user_role`, `deleted`,
                     `lock_version`)
VALUES (36, '2024-07-06 14:13:26', '2024-07-06 14:13:26', '藏家_R3qWhY3333', '34347c343003e57232a5d21f14fe399e',
        'ACTIVE', null, '13333333333', null, null, null, null, null, null, null, null, 'ADMIN', 0, 0);

-- collection 测试数据导入

INSERT INTO `collection` (`id`, `gmt_create`, `gmt_modified`, `name`, `cover`, `class_id`, `price`, `quantity`,
                          `detail`, `saleable_inventory`, `identifier`, `occupied_inventory`, `state`, `create_time`,
                          `sale_time`, `sync_chain_time`, `deleted`, `lock_version`, `creator_id`, `version`)
VALUES (10000, '2024-03-11 16:03:25', '2024-06-15 13:26:53', 'Founders Pirate',
        'https://raw.seadn.io/files/bc3324323debe57a2313ce73568dbbe1.png', 'id1718176052022', 1.090000, 100, null, 99,
        null, 0, 'SUCCEED', '2024-03-11 16:04:14', '2024-03-11 16:04:16', '2024-03-11 16:04:19', 0, 1, null, 1);
INSERT INTO `collection` (`id`, `gmt_create`, `gmt_modified`, `name`, `cover`, `class_id`, `price`, `quantity`,
                          `detail`, `saleable_inventory`, `identifier`, `occupied_inventory`, `state`, `create_time`,
                          `sale_time`, `sync_chain_time`, `deleted`, `lock_version`, `creator_id`, `version`)
VALUES (10001, '2024-06-10 13:41:37', '2024-06-10 13:41:37', 'Sheep',
        'https://openseauserdata.com/files/a9bdc2c2a50c093f02b2780bb5bd3f5b.svg', 'id1718176255339', 0.180000, 100,
        null, 100, null, 0, 'SUCCEED', '2024-06-10 13:41:37', '2024-06-10 13:41:37', '2024-06-10 13:41:37', 0, 0, null,
        1);
INSERT INTO `collection` (`id`, `gmt_create`, `gmt_modified`, `name`, `cover`, `class_id`, `price`, `quantity`,
                          `detail`, `saleable_inventory`, `identifier`, `occupied_inventory`, `state`, `create_time`,
                          `sale_time`, `sync_chain_time`, `deleted`, `lock_version`, `creator_id`, `version`)
VALUES (10002, '2024-06-10 13:45:58', '2024-06-15 13:33:09', 'Computers Cant Jump',
        'https://i.seadn.io/s/raw/files/174ed0549200ba2ada5542ce3883c45e.jpg?auto=format&dpr=1&w=1000',
        'id1718176300107', 11.180000, 100, null, 96, null, 2, 'SUCCEED', '2024-06-10 13:45:58', '2024-06-10 13:45:58',
        '2024-06-10 13:45:58', 0, 6, null, 1);
INSERT INTO `collection` (`id`, `gmt_create`, `gmt_modified`, `name`, `cover`, `class_id`, `price`, `quantity`,
                          `detail`, `saleable_inventory`, `identifier`, `occupied_inventory`, `state`, `create_time`,
                          `sale_time`, `sync_chain_time`, `deleted`, `lock_version`, `creator_id`, `version`)
VALUES (10003, '2024-06-10 13:45:58', '2024-06-15 13:49:44', 'Absolute Zero Block',
        'https://dl.openseauserdata.com/cache/originImage/files/498954ed62ee36d6de62465f01d5e6bf.png',
        'id1718176365343', 123.180000, 100, null, 98, null, 2, 'SUCCEED', '2024-06-10 13:45:58', '2024-06-10 13:45:58',
        '2024-06-10 13:45:58', 0, 4, null, 1);
INSERT INTO `collection` (`id`, `gmt_create`, `gmt_modified`, `name`, `cover`, `class_id`, `price`, `quantity`,
                          `detail`, `saleable_inventory`, `identifier`, `occupied_inventory`, `state`, `create_time`,
                          `sale_time`, `sync_chain_time`, `deleted`, `lock_version`, `creator_id`, `version`)
VALUES (10004, '2024-06-10 13:45:59', '2024-06-15 12:56:48', 'TinFun',
        'https://i.seadn.io/s/raw/files/69e7150ba6e4e0dfe353b5e0d20b8405.png?auto=format&dpr=1&w=1000',
        'id1718176399803', 999.990000, 100, null, 96, null, 2, 'SUCCEED', '2024-06-10 13:45:59', '2024-06-10 13:45:59',
        '2024-06-10 13:45:59', 0, 6, null, 1);
INSERT INTO `collection` (`id`, `gmt_create`, `gmt_modified`, `name`, `cover`, `class_id`, `price`, `quantity`,
                          `detail`, `saleable_inventory`, `identifier`, `occupied_inventory`, `state`, `create_time`,
                          `sale_time`, `sync_chain_time`, `deleted`, `lock_version`, `creator_id`, `version`)
VALUES (10005, '2024-06-10 13:45:59', '2024-06-15 12:59:38', 'The Sanctuary NFT',
        'https://raw.seadn.io/files/d76915fff5e09588d78a1c26e7e78f5c.png', 'id1718176436482', 11.180000, 100, null, 99,
        null, 1, 'SUCCEED', '2024-06-10 13:45:59', '2024-06-10 13:45:59', '2024-06-10 13:45:59', 0, 2, null, 1);
INSERT INTO `collection` (`id`, `gmt_create`, `gmt_modified`, `name`, `cover`, `class_id`, `price`, `quantity`,
                          `detail`, `saleable_inventory`, `identifier`, `occupied_inventory`, `state`, `create_time`,
                          `sale_time`, `sync_chain_time`, `deleted`, `lock_version`, `creator_id`, `version`)
VALUES (10006, '2024-06-10 13:45:59', '2024-06-15 13:03:10', 'M_Common_1064',
        'https://i.seadn.io/gcs/files/a4ed55b5f114d184c77c0da379b480f1.png?auto=format&dpr=1&w=1000', 'id1718176465288',
        32.180000, 100, null, 99, null, 1, 'SUCCEED', '2024-06-10 13:45:59', '2024-06-10 13:45:59',
        '2024-06-10 13:45:59', 0, 2, null, 1);
INSERT INTO `collection` (`id`, `gmt_create`, `gmt_modified`, `name`, `cover`, `class_id`, `price`, `quantity`,
                          `detail`, `saleable_inventory`, `identifier`, `occupied_inventory`, `state`, `create_time`,
                          `sale_time`, `sync_chain_time`, `deleted`, `lock_version`, `creator_id`, `version`)
VALUES (10007, '2024-06-16 15:05:19', '2024-06-16 15:05:19', '测试藏品',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', 'id1718521517068', 10.000000, 100, null, 100,
        null, 0, 'INIT', null, '2024-06-16 15:05:17', null, 0, 0, null, 0);

-- collection还需要再 redis 中初始化内存
--    set "clc:inventory:10000" "99"
--    set "clc:inventory:10001" "100"
--    set "clc:inventory:10002" "100"
--    set "clc:inventory:10003" "100"
--    set "clc:inventory:10004" "96"
--    set "clc:inventory:10005" "100"
--    set "clc:inventory:10006" "100"
--    set "clc:inventory:10007" "100"


-- blind_box 测试数据导入

INSERT INTO `blind_box` (`id`, `gmt_create`, `gmt_modified`, `name`, `cover`, `detail`, `identifier`, `state`,
                         `quantity`, `price`, `saleable_inventory`, `occupied_inventory`, `create_time`, `sale_time`,
                         `allocate_rule`, `sync_chain_time`, `creator_id`, `collection_configs`, `deleted`,
                         `lock_version`)
VALUES (22, '2024-12-19 21:26:51', '2024-12-31 16:34:48', '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '漫威超级英雄盲盒',
        '0bf5ba64-f389-4579-abe0-81c4486a17e9', 'SUCCEED', 200, 199.900000, 184, 16, '2024-12-19 21:26:11',
        '2024-12-19 00:00:00', 'RANDOM', '2024-12-19 21:27:14', '30',
        '[{"collectionCover":"https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0","collectionDetail":"美国队长","collectionName":"美国队长","quantity":40,"rarity":"RARE","referencePrice":249.9},{"collectionCover":"https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E","collectionDetail":"钢铁侠","collectionName":"钢铁侠","quantity":50,"rarity":"COMMON","referencePrice":199.9},{"collectionCover":"https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg","collectionDetail":"蜘蛛侠","collectionName":"蜘蛛侠","quantity":70,"rarity":"COMMON","referencePrice":199.9},{"collectionCover":"https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh","collectionDetail":"黑寡妇","collectionName":"黑寡妇","quantity":30,"rarity":"EPIC","referencePrice":299.9},{"collectionCover":"https://gw.alicdn.com/imgextra/i2/732283339/O1CN01t2EMAJ1aXJQjsayjX_!!2-item_pic.png_.webp","collectionDetail":"绿巨人","collectionName":"绿巨人","quantity":10,"rarity":"LEGENDARY","referencePrice":399.9}]',
        0, 37);
-- blind_box还需要再 redis 中初始化内存
--    set "blb:inventory:22" "184"

INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3482, '2024-12-19 21:27:07', '2024-12-31 16:19:37', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        '15', 'ASSIGNED', '29', 199.900000, '1018740074660384481280003', 0, 0, 'RARE', 249.900000, null,
        '2024-12-31 16:19:37');
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3708, '2024-12-19 21:27:08', '2024-12-31 16:34:11', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', '16', 'ASSIGNED', '29',
        199.900000, '1018740111252960665600003', 0, 0, 'COMMON', 199.900000, null, '2024-12-31 16:34:11');
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3424, '2024-12-19 21:27:07', '2024-12-19 21:27:07', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3427, '2024-12-19 21:27:07', '2024-12-19 21:27:07', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3430, '2024-12-19 21:27:07', '2024-12-19 21:27:07', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3433, '2024-12-19 21:27:07', '2024-12-19 21:27:07', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3436, '2024-12-19 21:27:07', '2024-12-19 21:27:07', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3439, '2024-12-19 21:27:07', '2024-12-19 21:27:07', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3442, '2024-12-19 21:27:07', '2024-12-19 21:27:07', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3445, '2024-12-19 21:27:07', '2024-12-19 21:27:07', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3448, '2024-12-19 21:27:07', '2024-12-19 21:27:07', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3450, '2024-12-19 21:27:07', '2024-12-19 21:27:07', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3456, '2024-12-19 21:27:07', '2024-12-19 21:27:07', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3459, '2024-12-19 21:27:07', '2024-12-19 21:27:07', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3462, '2024-12-19 21:27:07', '2024-12-19 21:27:07', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3465, '2024-12-19 21:27:07', '2024-12-19 21:27:07', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3468, '2024-12-19 21:27:07', '2024-12-19 21:27:07', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3471, '2024-12-19 21:27:07', '2024-12-19 21:27:07', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3474, '2024-12-19 21:27:07', '2024-12-19 21:27:07', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3476, '2024-12-19 21:27:07', '2024-12-19 21:27:07', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3479, '2024-12-19 21:27:07', '2024-12-19 21:27:07', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3485, '2024-12-19 21:27:07', '2024-12-19 21:27:07', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3488, '2024-12-19 21:27:07', '2024-12-19 21:27:07', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3491, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3493, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3499, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3502, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3504, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3507, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3510, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3512, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3515, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3518, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3521, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3524, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3527, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3531, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3533, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3536, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        null, 'INIT', null, 199.900000, null, 0, 0, 'RARE', 249.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3540, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3543, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3546, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3548, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3551, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3554, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3556, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3562, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3565, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3568, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3570, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3573, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3576, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3578, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3581, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3584, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3587, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3590, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3593, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3596, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3598, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3601, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3607, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3609, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3612, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3614, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3617, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3620, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3623, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3626, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3628, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3631, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3634, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3637, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3640, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3643, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3645, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3648, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3651, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3654, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3657, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3660, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3662, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3665, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3668, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3671, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3675, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3678, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', null, 'INIT', null, 199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3681, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3684, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3686, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3692, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3698, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3700, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3703, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3706, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3711, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3718, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3720, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3722, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3731, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3734, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3736, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3739, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3745, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3751, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3754, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3757, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3759, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3762, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3765, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3768, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3773, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3776, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3779, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3782, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3784, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3787, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3790, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3793, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3796, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3798, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3801, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3804, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3807, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3809, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3812, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3815, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3818, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3821, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3823, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3825, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3828, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3831, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3834, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3837, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3840, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3845, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3848, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3851, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3854, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3857, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3859, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3862, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3865, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3868, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3871, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3874, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', null, 'INIT', null,
        199.900000, null, 0, 0, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3877, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3879, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3882, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3885, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3888, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3891, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3894, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3896, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3899, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3902, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3905, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3908, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3911, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3913, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3916, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3919, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3922, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3925, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3928, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3929, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3932, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3935, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3938, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3941, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3943, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3946, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3949, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3952, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3957, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', null, 'INIT', null, 199.900000, null, 0, 0, 'EPIC', 299.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3960, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '绿巨人',
        'https://gw.alicdn.com/imgextra/i2/732283339/O1CN01t2EMAJ1aXJQjsayjX_!!2-item_pic.png_.webp', '绿巨人', null,
        'INIT', null, 199.900000, null, 0, 0, 'LEGENDARY', 399.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3963, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '绿巨人',
        'https://gw.alicdn.com/imgextra/i2/732283339/O1CN01t2EMAJ1aXJQjsayjX_!!2-item_pic.png_.webp', '绿巨人', null,
        'INIT', null, 199.900000, null, 0, 0, 'LEGENDARY', 399.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3966, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '绿巨人',
        'https://gw.alicdn.com/imgextra/i2/732283339/O1CN01t2EMAJ1aXJQjsayjX_!!2-item_pic.png_.webp', '绿巨人', null,
        'INIT', null, 199.900000, null, 0, 0, 'LEGENDARY', 399.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3969, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '绿巨人',
        'https://gw.alicdn.com/imgextra/i2/732283339/O1CN01t2EMAJ1aXJQjsayjX_!!2-item_pic.png_.webp', '绿巨人', null,
        'INIT', null, 199.900000, null, 0, 0, 'LEGENDARY', 399.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3971, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '绿巨人',
        'https://gw.alicdn.com/imgextra/i2/732283339/O1CN01t2EMAJ1aXJQjsayjX_!!2-item_pic.png_.webp', '绿巨人', null,
        'INIT', null, 199.900000, null, 0, 0, 'LEGENDARY', 399.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3974, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '绿巨人',
        'https://gw.alicdn.com/imgextra/i2/732283339/O1CN01t2EMAJ1aXJQjsayjX_!!2-item_pic.png_.webp', '绿巨人', null,
        'INIT', null, 199.900000, null, 0, 0, 'LEGENDARY', 399.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3977, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '绿巨人',
        'https://gw.alicdn.com/imgextra/i2/732283339/O1CN01t2EMAJ1aXJQjsayjX_!!2-item_pic.png_.webp', '绿巨人', null,
        'INIT', null, 199.900000, null, 0, 0, 'LEGENDARY', 399.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3980, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '绿巨人',
        'https://gw.alicdn.com/imgextra/i2/732283339/O1CN01t2EMAJ1aXJQjsayjX_!!2-item_pic.png_.webp', '绿巨人', null,
        'INIT', null, 199.900000, null, 0, 0, 'LEGENDARY', 399.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3982, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '绿巨人',
        'https://gw.alicdn.com/imgextra/i2/732283339/O1CN01t2EMAJ1aXJQjsayjX_!!2-item_pic.png_.webp', '绿巨人', null,
        'INIT', null, 199.900000, null, 0, 0, 'LEGENDARY', 399.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3984, '2024-12-19 21:27:08', '2024-12-19 21:27:08', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '绿巨人',
        'https://gw.alicdn.com/imgextra/i2/732283339/O1CN01t2EMAJ1aXJQjsayjX_!!2-item_pic.png_.webp', '绿巨人', null,
        'INIT', null, 199.900000, null, 0, 0, 'LEGENDARY', 399.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3559, '2024-12-19 21:27:08', '2024-12-31 16:15:48', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', '13', 'OPENING', '29', 199.900000, '1018740063055584133120003', 0, 1, 'COMMON', 199.900000, null,
        '2024-12-31 16:15:02');
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3728, '2024-12-19 21:27:08', '2024-12-30 20:53:00', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', '1', 'OPENING', '29',
        199.900000, '1018722540488317460480003', 0, 2, 'COMMON', 199.900000, null, null);
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3453, '2024-12-19 21:27:07', '2024-12-30 21:55:11', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        '6', 'SUCCEED', '29', 199.900000, '1018737294402937323520003', 0, 2, 'RARE', 249.900000, '2024-12-30 21:55:11',
        '2024-12-30 21:54:52');
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3496, '2024-12-19 21:27:08', '2024-12-30 21:33:24', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '美国队长',
        'https://pic.baike.soso.com/ugc/baikepic2/0/20240508031216-2041033223_jpeg_499_773_29199.jpg/0', '美国队长',
        '3', 'SUCCEED', '29', 199.900000, '1018737238688613048320003', 0, 2, 'RARE', 249.900000, '2024-12-30 21:33:24',
        '2024-12-30 21:32:45');
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3604, '2024-12-19 21:27:08', '2024-12-31 14:55:59', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '钢铁侠',
        'https://respic.3d66.com/coverimg/cache/6b89/af09a92692276bc9ec9b135fc62db9cf.jpg!medium-size-2?v=9522969&k=D41D8CD98F00B204E9800998ECF8427E',
        '钢铁侠', '9', 'SUCCEED', '29', 199.900000, '1018737328301470023680003', 0, 2, 'COMMON', 199.900000,
        '2024-12-31 14:55:59', '2024-12-30 22:08:20');
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3689, '2024-12-19 21:27:08', '2024-12-31 15:13:03', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', '11', 'SUCCEED', '29',
        199.900000, '1018739899476217978880003', 0, 2, 'COMMON', 199.900000, '2024-12-31 15:13:03',
        '2024-12-31 15:10:01');
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3695, '2024-12-19 21:27:08', '2024-12-31 15:12:52', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', '10', 'SUCCEED', '29',
        199.900000, '1018739899005155696640003', 0, 2, 'COMMON', 199.900000, '2024-12-31 15:12:52',
        '2024-12-31 15:09:51');
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3715, '2024-12-19 21:27:08', '2024-12-31 15:12:56', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', '8', 'SUCCEED', '29',
        199.900000, '1018737327842361507840003', 0, 2, 'COMMON', 199.900000, '2024-12-31 15:12:56',
        '2024-12-30 22:08:08');
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3725, '2024-12-19 21:27:08', '2024-12-30 21:38:55', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', '4', 'SUCCEED', '29',
        199.900000, '1018737253626207354880003', 0, 2, 'COMMON', 199.900000, '2024-12-30 21:38:55',
        '2024-12-30 21:38:40');
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3742, '2024-12-19 21:27:08', '2024-12-31 15:04:56', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', '7', 'SUCCEED', '29',
        199.900000, '1018737327306287513600003', 0, 2, 'COMMON', 199.900000, '2024-12-31 15:04:56',
        '2024-12-30 22:07:57');
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3748, '2024-12-19 21:27:08', '2024-12-30 21:51:31', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', '5', 'SUCCEED', '29',
        199.900000, '1018737285410055905280003', 0, 2, 'COMMON', 199.900000, '2024-12-30 21:51:31',
        '2024-12-30 21:51:19');
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3770, '2024-12-19 21:27:08', '2024-12-30 21:09:54', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', '2', 'SUCCEED', '29',
        199.900000, '1018737179813672263680003', 0, 2, 'COMMON', 199.900000, '2024-12-30 21:09:54',
        '2024-12-30 21:09:20');
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3842, '2024-12-19 21:27:08', '2024-12-31 16:20:17', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '蜘蛛侠',
        'https://p6.itc.cn/images01/20210409/42bc205b66a143f78b2bac7b8f6c4c83.jpeg', '蜘蛛侠', '14', 'SUCCEED', '29',
        199.900000, '1018740074203834490880003', 0, 2, 'COMMON', 199.900000, '2024-12-31 16:20:17',
        '2024-12-31 16:19:27');
INSERT INTO `blind_box_item` (`id`, `gmt_create`, `gmt_modified`, `blind_box_id`, `name`, `cover`, `collection_name`,
                              `collection_cover`, `collection_detail`, `collection_serial_no`, `state`, `user_id`,
                              `purchase_price`, `order_id`, `deleted`, `lock_version`, `rarity`, `reference_price`,
                              `opened_time`, `assign_time`)
VALUES (3954, '2024-12-19 21:27:08', '2024-12-31 15:10:21', 22, '漫威超级英雄盲盒',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '黑寡妇',
        'https://static.wikia.nocookie.net/marvelcutaiwan/images/2/2f/BlackWidow-Avengers.png/revision/latest?cb=20160526140811&path-prefix=zh',
        '黑寡妇', '12', 'SUCCEED', '29', 199.900000, '1018739899918255677440003', 0, 2, 'EPIC', 299.900000,
        '2024-12-31 15:10:21', '2024-12-31 15:10:12');

-- order 测试数据导入

INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1001543705900875776, '2024-05-26 17:37:07', '2024-06-04 20:54:41', '1017946640492154920960003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'token:orderList:9d9da746-f00a-4f7a-a21e-b7fbedd86a81', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'CLOSED',
        0.000000, null, '2024-05-26 17:37:07', null, '2024-06-04 20:54:41', null, null, 'CANCEL', 0, 2, null);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1004855511855661057, '2024-06-04 20:57:03', '2024-06-04 20:57:18', '1017979758553002762240003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'token_orderList:42641e1a-8247-4ad7-b35d-3ef8b4f71bc8', '10', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品112', 0.020000, 1, 0.020000, 'CLOSED',
        0.000000, null, '2024-06-04 20:57:03', null, '2024-06-04 20:57:18', null, null, 'CANCEL', 0, 2, 7);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1004855795877150721, '2024-06-04 20:58:11', '2024-06-04 20:58:18', '1017979761393301544960003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'token_orderList:4ce5ab4d-046a-49f5-9923-a1898018deee', '10', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品112', 0.020000, 1, 0.020000, 'CLOSED',
        0.000000, null, '2024-06-04 20:58:11', null, '2024-06-04 20:58:18', null, null, 'CANCEL', 0, 2, 7);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1004856172097830913, '2024-06-04 20:59:41', '2024-06-04 20:59:49', '1017979765155508346880003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'token_orderList:c922ef17-eb73-4c39-a9a5-5d57a80afaf3', '10', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品112', 0.020000, 1, 0.020000, 'CLOSED',
        0.000000, null, '2024-06-04 20:59:41', null, '2024-06-04 20:59:49', null, null, 'CANCEL', 0, 2, 7);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1004857041690296321, '2024-06-04 21:03:08', '2024-06-07 16:38:04', '1017979773851433000960003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'token_orderList:38a7d289-b1b1-470e-a824-9e5eacdda93e', '10', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品112', 0.020000, 1, 0.020000, 'CLOSED',
        0.000000, null, '2024-06-04 21:03:08', null, '2024-06-07 16:38:04', null, null, 'TIME_OUT', 0, 2, 7);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1005874184473018368, '2024-06-07 16:24:54', '2024-06-07 16:59:31', '1017989945277304791040003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'token:orderList:d275e2c3-1de4-4d49-a093-28ac665062c3', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'CLOSED',
        0.000000, null, '2024-06-07 16:24:54', null, '2024-06-07 16:59:31', null, null, 'TIME_OUT', 0, 2, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1005876332883607552, '2024-06-07 16:33:26', '2024-06-07 17:14:12', '1017989966761788211200003', '29',
        'CUSTOMER', '0', 'PLATFORM', '9650ac7c-1b68-4925-9047-965e20771c8f', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'CLOSED',
        0.000000, null, '2024-06-07 16:33:26', null, '2024-06-07 17:14:13', null, null, 'TIME_OUT', 0, 2, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1005887411059687424, '2024-06-07 17:17:27', '2024-06-10 19:11:38', '1017990077545017057280003', '29',
        'CUSTOMER', '0', 'PLATFORM', '0f30b677-2e48-4afb-b53c-bb5ded25fba4', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'CLOSED',
        0.000000, null, '2024-06-07 17:17:27', null, '2024-06-10 19:11:39', null, null, 'TIME_OUT', 0, 2, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1005887567536586753, '2024-06-07 17:18:05', '2024-06-10 19:11:33', '1017990079109869936640003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'ba8b9634-2ee7-4377-a7e3-8aa212502b55', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'CLOSED',
        0.000000, null, '2024-06-07 17:18:05', null, '2024-06-10 19:11:33', null, null, 'CANCEL', 0, 2, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1005887608061952000, '2024-06-07 17:18:14', '2024-06-09 14:42:40', '1017990079515123589120003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'dd07c03b-d0dd-49a7-a191-c1ba352eead9', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'CLOSED',
        0.000000, null, '2024-06-07 17:18:14', null, '2024-06-09 14:42:41', null, null, 'TIME_OUT', 0, 2, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1005887672289329153, '2024-06-07 17:18:30', '2024-06-07 20:01:09', '1017990080157439303680003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'dd1260f2-38b2-491f-b55e-da00d2f91a89', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'PAID',
        0.020000, '2024-06-07 17:26:44', '2024-06-07 17:18:30', null, null, null, '1799008022500888576', null, 0, 2, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1005913921061650432, '2024-06-07 19:02:48', '2024-06-07 19:03:07', '1017990342645120573440003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'd2162b57-e98b-407f-b0e5-2fa553e61c8d', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'PAID',
        0.020000, '2024-06-07 19:03:06', '2024-06-07 19:02:48', null, null, null, '1799034280647479296', null, 0, 2, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1005918426281017344, '2024-06-07 19:20:42', '2024-06-07 19:20:58', '1017990387695762432000003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'a88850b1-78bf-4817-96ab-e4627c5972d0', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'PAID',
        0.020000, '2024-06-07 19:20:57', '2024-06-07 19:20:42', null, null, null, '1799038787368402944', null, 0, 2, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1005922873589628928, '2024-06-07 19:38:22', '2024-06-07 19:38:39', '1017990432168974417920003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'c060e601-4738-40ce-8d13-dab5c6a5b905', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'PAID',
        0.020000, '2024-06-07 19:38:38', '2024-06-07 19:38:22', null, null, null, '1799043223415398400', null, 0, 2, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1005923340918980608, '2024-06-07 19:40:14', '2024-06-07 19:40:30', '1017990436843735941120003', '29',
        'CUSTOMER', '0', 'PLATFORM', '08883983-611f-4c97-9151-8cbc92f44444', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'PAID',
        0.020000, '2024-06-07 19:40:29', '2024-06-07 19:40:14', null, null, null, '1799043690602143744', null, 0, 2, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1005923585572732928, '2024-06-07 19:41:12', '2024-06-07 19:41:23', '1017990439290273464320003', '29',
        'CUSTOMER', '0', 'PLATFORM', '8ce7ec44-0f39-4dca-a450-2dcb8568dac3', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'PAID',
        0.020000, '2024-06-07 19:41:23', '2024-06-07 19:41:12', null, null, null, '1799043936312860672', null, 0, 2, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1005923809535983616, '2024-06-07 19:42:05', '2024-06-07 19:42:19', '1017990441529905971200003', '29',
        'CUSTOMER', '0', 'PLATFORM', '22e60005-c5d2-4979-a13c-8cba712b1c7c', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'PAID',
        0.020000, '2024-06-07 19:42:19', '2024-06-07 19:42:05', null, null, null, '1799044158740996096', null, 0, 2, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1005940074635853824, '2024-06-07 20:46:43', '2024-06-07 20:46:56', '1017990604178765619200003', '29',
        'CUSTOMER', '0', 'PLATFORM', '5b31a219-bcbc-431a-800c-50e132252817', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'PAID',
        0.020000, '2024-06-07 20:46:55', '2024-06-07 20:46:43', null, null, null, '1799060424671358976', null, 0, 2, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1005942315644092416, '2024-06-07 20:55:38', '2024-06-07 20:55:56', '1017990626589477191680003', '29',
        'CUSTOMER', '0', 'PLATFORM', '735bbc14-0219-4c49-a9e0-44fec9b4f2c2', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'PAID',
        0.020000, '2024-06-07 20:55:55', '2024-06-07 20:55:38', null, null, 'WECHAT', '1799062665461452800', null, 0, 2,
        6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1005980826694844416, '2024-06-07 23:28:39', '2024-06-07 23:29:13', '1017991011699187834880003', '29',
        'CUSTOMER', '0', 'PLATFORM', '9df1c08f-420a-4d47-a4e2-f4a2b6d3292a', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'PAID',
        0.020000, '2024-06-07 23:29:12', '2024-06-07 23:28:39', null, null, 'WECHAT', '1799101182044524544', null, 0, 2,
        6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1005995601734664192, '2024-06-08 00:27:22', '2024-06-08 00:27:51', '1017991159450634649600003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'b1e440b6-b49e-4e1e-aaa8-42646f1aada0', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'PAID',
        0.020000, '2024-06-08 00:27:50', '2024-06-08 00:27:22', null, null, 'WECHAT', '1799115962813681664', null, 0, 2,
        6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1006008961561788416, '2024-06-08 01:20:27', '2024-06-09 14:42:36', '1017991293048109015040003', '29',
        'CUSTOMER', '0', 'PLATFORM', '1804d803-a520-45d0-b2d3-aae4e63c7ee3', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'CLOSED',
        0.000000, null, '2024-06-08 01:20:27', null, '2024-06-09 14:42:37', null, null, 'TIME_OUT', 0, 2, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1006008968801157121, '2024-06-08 01:20:29', '2024-06-08 01:20:44', '1017991293122515968000003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'd8cdfbae-bca6-4b3f-b906-e2a0b3915ab7', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'PAID',
        0.020000, '2024-06-08 01:20:43', '2024-06-08 01:20:29', null, null, 'WECHAT', '1799129318308175872', null, 0, 2,
        6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1006528550813040641, '2024-06-09 11:45:07', '2024-06-09 11:45:29', '1017996488942592860160003', '29',
        'CUSTOMER', '0', 'PLATFORM', '4f9f50ef-0816-4979-8a84-6ac7ba7f9baf', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'PAID',
        0.020000, '2024-06-09 11:45:28', '2024-06-09 11:45:07', null, null, 'WECHAT', '1799648912726810624', null, 0, 2,
        6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1006542063862284288, '2024-06-09 12:38:49', '2024-06-09 12:39:18', '1017996624071196672000003', '29',
        'CUSTOMER', '0', 'PLATFORM', '81159903-6b18-44d5-bb3f-83d63f43d65e', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'PAID',
        0.020000, '2024-06-09 12:39:17', '2024-06-09 12:38:49', null, null, 'WECHAT', '1799662477818875904', null, 0, 2,
        6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1006544821956902912, '2024-06-09 12:49:46', '2024-06-09 12:50:00', '1017996651652059054080003', '29',
        'CUSTOMER', '0', 'PLATFORM', '86ba61dd-54b5-4399-9dfe-1d7a21ebe2ae', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'PAID',
        0.020000, '2024-06-09 12:49:59', '2024-06-09 12:49:46', null, null, 'WECHAT', '1799665173158404096', null, 0, 2,
        6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1006556746988126208, '2024-06-09 13:37:09', '2024-06-09 13:37:37', '1017996770902497157120003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'b6bfe7ab-fca5-4edd-9e65-73931a003028', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'PAID',
        0.020000, '2024-06-09 13:37:36', '2024-06-09 13:37:10', null, null, 'WECHAT', '1799677097963134976', null, 0, 2,
        6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1006561736469250048, '2024-06-09 13:56:59', '2024-06-09 13:57:11', '1017996820799195832320003', '29',
        'CUSTOMER', '0', 'PLATFORM', '4c6c3966-88dd-456f-81d6-7b2353c83151', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'PAID',
        0.020000, '2024-06-09 13:57:11', '2024-06-09 13:56:59', null, null, 'WECHAT', '1799682085732982784', null, 0, 2,
        6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1006562490798047232, '2024-06-09 13:59:59', '2024-06-09 14:00:10', '1017996828342483804160003', '29',
        'CUSTOMER', '0', 'PLATFORM', '14551f05-20d1-41b6-b3e5-ffd4e9c39479', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'PAID',
        0.020000, '2024-06-09 14:00:09', '2024-06-09 13:59:59', null, null, 'WECHAT', '1799682839772372992', null, 0, 2,
        6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1006564035144974336, '2024-06-09 14:06:07', '2024-06-09 14:40:08', '1017996843784527052800003', '29',
        'CUSTOMER', '0', 'PLATFORM', '2b04a5cb-d773-4750-83b8-f52c7a13b46b', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'FINISH',
        0.020000, '2024-06-09 14:06:19', '2024-06-09 14:06:07', '2024-06-09 14:40:05', null, 'WECHAT',
        '1799684385469849600', null, 0, 3, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1006569658590953472, '2024-06-09 14:28:28', '2024-06-09 14:40:08', '1017996900018148024320003', '29',
        'CUSTOMER', '0', 'PLATFORM', '41946959-3b68-4efe-8de3-ddd292925e8a', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'FINISH',
        0.020000, '2024-06-09 14:28:42', '2024-06-09 14:28:28', '2024-06-09 14:40:09', null, 'WECHAT',
        '1799690016771792896', null, 0, 3, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1006572629651357696, '2024-06-09 14:40:16', '2024-06-09 14:40:48', '1017996929730891202560003', '29',
        'CUSTOMER', '0', 'PLATFORM', '9a2c3521-b740-4ab5-8aa8-496149766170', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'FINISH',
        0.020000, '2024-06-09 14:40:41', '2024-06-09 14:40:16', '2024-06-09 14:40:49', null, 'WECHAT',
        '1799692980026593280', null, 0, 3, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1006574033778507777, '2024-06-09 14:45:51', '2024-06-10 19:11:18', '1017996943772288532480003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'd0c799d1-dd58-41f3-b62f-c5a9ba283ccc', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'CLOSED',
        0.000000, null, '2024-06-09 14:45:51', null, '2024-06-10 19:11:19', null, null, 'TIME_OUT', 0, 2, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1006574791953481728, '2024-06-09 14:48:52', '2024-06-10 14:13:24', '1017996951354080215040003', '29',
        'CUSTOMER', '0', 'PLATFORM', '1d7f02df-cacd-4683-a53f-57c4b19e8ef7', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'CLOSED',
        0.000000, null, '2024-06-09 14:48:52', null, '2024-06-10 14:13:24', null, null, 'TIME_OUT', 0, 2, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1006575099274330113, '2024-06-09 14:50:05', '2024-06-09 14:50:33', '1017996954427288698880003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'edf7a43a-40cf-4e1d-9e2e-a31243d22719', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'FINISH',
        0.020000, '2024-06-09 14:50:25', '2024-06-09 14:50:05', '2024-06-09 14:50:34', null, 'WECHAT',
        '1799695449444044800', null, 0, 3, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1006576736411844608, '2024-06-09 14:56:35', '2024-06-09 14:56:51', '1017996970798621900800003', '29',
        'CUSTOMER', '0', 'PLATFORM', '98980784-4928-4a43-8b23-606bce5d223d', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'FINISH',
        0.020000, '2024-06-09 14:56:44', '2024-06-09 14:56:35', '2024-06-09 14:56:52', null, 'WECHAT',
        '1799697085507817472', null, 0, 3, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1006577623385505793, '2024-06-09 15:00:07', '2024-06-09 15:00:29', '1017996979668400455680003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'a192838d-12bb-4b01-90b5-599ff26dc8ed', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'FINISH',
        0.020000, '2024-06-09 15:00:22', '2024-06-09 15:00:07', '2024-06-09 15:00:29', null, 'WECHAT',
        '1799697972640862208', null, 0, 3, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1006578034028838912, '2024-06-09 15:01:45', '2024-06-09 15:02:04', '1017996983774875729920003', '29',
        'CUSTOMER', '0', 'PLATFORM', '4fea5e73-cbb0-4048-a344-9d7c5128ab85', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'FINISH',
        0.020000, '2024-06-09 15:01:57', '2024-06-09 15:01:45', '2024-06-09 15:02:04', null, 'WECHAT',
        '1799698396370423808', null, 0, 3, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1006578554386776065, '2024-06-09 15:03:49', '2024-06-09 15:04:08', '1017996988978413158400003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'b837a51e-eda6-41d4-b62a-c00815ad07a7', '9', 'COLLECTION',
        'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', '测试藏品11', 0.020000, 1, 0.020000, 'FINISH',
        0.020000, '2024-06-09 15:04:02', '2024-06-09 15:03:49', '2024-06-09 15:04:09', null, 'WECHAT',
        '1799698904648765440', null, 0, 3, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1007003557880135680, '2024-06-10 19:12:37', '2024-06-10 19:13:00', '1018001239013346754560003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'cba99dfd-2bc7-4787-8992-263fbcbe3e7d', '9', 'COLLECTION',
        'https://i.seadn.io/gcs/files/acf1d43f965f76416a0d79f6fdd638db.png?auto=format&dpr=1&w=1000', 'Milady',
        0.020000, 1, 0.020000, 'FINISH', 0.020000, '2024-06-10 19:12:52', '2024-06-10 19:12:38', '2024-06-10 19:13:00',
        null, 'WECHAT', '1800123914873982976', null, 0, 3, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1007003896071061505, '2024-06-10 19:13:58', '2024-06-10 19:13:58', '1018001242395297955840003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'e3bb2158-2315-4652-955e-64d7c5ccd6aa', '9', 'COLLECTION',
        'https://i.seadn.io/gcs/files/acf1d43f965f76416a0d79f6fdd638db.png?auto=format&dpr=1&w=1000', 'Milady',
        0.020000, 1, 0.020000, 'CONFIRM', 0.000000, null, '2024-06-10 19:13:58', null, null, null, null, null, 0, 1, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1007043498072866816, '2024-06-10 21:51:20', '2024-06-10 21:51:36', '1018001638415316008960003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'c088e379-9bc9-4f76-8dce-b1bd4290e2b9', '9', 'COLLECTION',
        'https://i.seadn.io/gcs/files/acf1d43f965f76416a0d79f6fdd638db.png?auto=format&dpr=1&w=1000', 'Milady',
        0.020000, 1, 0.020000, 'FINISH', 0.020000, '2024-06-10 21:51:28', '2024-06-10 21:51:20', '2024-06-10 21:51:37',
        null, 'WECHAT', '1800163847051399168', null, 0, 3, 6);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1007782787341615105, '2024-06-12 22:49:00', '2024-06-12 22:49:00', '1018009031307919605760003', '29',
        'CUSTOMER', '0', 'PLATFORM', '555c6a77-bf51-4de3-9452-4fb59f99df9b', '10004', 'COLLECTION',
        'https://i.seadn.io/s/raw/files/69e7150ba6e4e0dfe353b5e0d20b8405.png?auto=format&dpr=1&w=1000', 'TinFun',
        999.990000, 1, 999.990000, 'CONFIRM', 0.000000, null, '2024-06-12 22:49:00', null, null, null, null, null, 0, 1,
        1);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1008717699469017088, '2024-06-15 12:44:01', '2024-06-15 12:44:01', '1018018380427558584320003', '29',
        'CUSTOMER', '0', 'PLATFORM', '551462eb-086f-4ea9-90ef-8c43b8d79d96', '10004', 'COLLECTION',
        'https://i.seadn.io/s/raw/files/69e7150ba6e4e0dfe353b5e0d20b8405.png?auto=format&dpr=1&w=1000', 'TinFun',
        999.990000, 1, 999.990000, 'CONFIRM', 0.000000, null, '2024-06-15 12:44:01', null, null, null, null, null, 0, 1,
        1);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1008718970624147456, '2024-06-15 12:49:04', '2024-06-15 12:51:42', '1018018393139849175040003', '29',
        'CUSTOMER', '0', 'PLATFORM', '18ad1af2-a9fc-49c1-b8e3-4a46f32bae8e', '10004', 'COLLECTION',
        'https://i.seadn.io/s/raw/files/69e7150ba6e4e0dfe353b5e0d20b8405.png?auto=format&dpr=1&w=1000', 'TinFun',
        999.990000, 1, 999.990000, 'PAID', 999.990000, '2024-06-15 12:51:42', '2024-06-15 12:49:04', null, null, 'MOCK',
        '1801839320376946688', null, 0, 2, 1);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1008720622659829760, '2024-06-15 12:55:38', '2024-06-15 12:56:48', '1018018409660373852160003', '29',
        'CUSTOMER', '0', 'PLATFORM', '048e47c5-8f86-4f4b-bf97-a2adb190d7c4', '10004', 'COLLECTION',
        'https://i.seadn.io/s/raw/files/69e7150ba6e4e0dfe353b5e0d20b8405.png?auto=format&dpr=1&w=1000', 'TinFun',
        999.990000, 1, 999.990000, 'PAID', 999.990000, '2024-06-15 12:56:48', '2024-06-15 12:55:38', null, null, 'MOCK',
        '1801840974673375232', null, 0, 2, 1);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1008721290955063296, '2024-06-15 12:58:17', '2024-06-15 12:58:18', '1018018416343410155520003', '29',
        'CUSTOMER', '0', 'PLATFORM', '91e5665d-9ae8-4713-94d8-f4e7ce2ec2b6', '10003', 'COLLECTION',
        'https://dl.openseauserdata.com/cache/originImage/files/498954ed62ee36d6de62465f01d5e6bf.png',
        'Absolute Zero Block', 123.180000, 1, 123.180000, 'PAID', 123.180000, '2024-06-15 12:58:18',
        '2024-06-15 12:58:17', null, null, 'MOCK', '1801841640191995904', null, 0, 2, 1);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1008721628651061248, '2024-06-15 12:59:37', '2024-06-15 12:59:38', '1018018419720999280640003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'de4d76fd-7d90-48b3-a1f7-36228b8d20c8', '10005', 'COLLECTION',
        'https://raw.seadn.io/files/d76915fff5e09588d78a1c26e7e78f5c.png', 'The Sanctuary NFT', 11.180000, 1, 11.180000,
        'PAID', 11.180000, '2024-06-15 12:59:38', '2024-06-15 12:59:37', null, null, 'MOCK', '1801841976432570368',
        null, 0, 2, 1);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1008722511942123520, '2024-06-15 13:03:08', '2024-06-15 13:03:09', '1018018428553867960320003', '29',
        'CUSTOMER', '0', 'PLATFORM', '9f518350-200a-4e40-b374-32c6b5192bd1', '10006', 'COLLECTION',
        'https://i.seadn.io/gcs/files/a4ed55b5f114d184c77c0da379b480f1.png?auto=format&dpr=1&w=1000', 'M_Common_1064',
        32.180000, 1, 32.180000, 'PAID', 32.180000, '2024-06-15 13:03:10', '2024-06-15 13:03:08', null, null, 'MOCK',
        '1801842862256992256', null, 0, 2, 1);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1008723732321009664, '2024-06-15 13:07:59', '2024-06-15 13:07:59', '1018018440757069660160003', '29',
        'CUSTOMER', '0', 'PLATFORM', '3f8d7ba0-8dbd-499b-9359-30f993caf93a', '10002', 'COLLECTION',
        'https://i.seadn.io/s/raw/files/174ed0549200ba2ada5542ce3883c45e.jpg?auto=format&dpr=1&w=1000',
        'Computers Cant Jump', 11.180000, 1, 11.180000, 'CONFIRM', 0.000000, null, '2024-06-15 13:07:59', null, null,
        null, null, null, 0, 1, 1);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1008724865378680832, '2024-06-15 13:12:29', '2024-06-15 13:12:50', '1018018452087646412800003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'b23cccbe-5c13-4687-94f1-1ce1e2be99ce', '10002', 'COLLECTION',
        'https://i.seadn.io/s/raw/files/174ed0549200ba2ada5542ce3883c45e.jpg?auto=format&dpr=1&w=1000',
        'Computers Cant Jump', 11.180000, 1, 11.180000, 'PAID', 11.180000, '2024-06-15 13:12:50', '2024-06-15 13:12:29',
        null, null, 'MOCK', '1801845288045309952', null, 0, 2, 1);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1008728490532405248, '2024-06-15 13:26:53', '2024-06-15 13:26:54', '1018018488337897881600003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'dfdfcb34-3408-4c8b-9514-0f55711e8e4f', '10000', 'COLLECTION',
        'https://raw.seadn.io/files/bc3324323debe57a2313ce73568dbbe1.png', 'Founders Pirate', 1.090000, 1, 1.090000,
        'CONFIRM', 0.000000, null, '2024-06-15 13:26:54', null, null, null, null, null, 0, 1, 1);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1008729467129954305, '2024-06-15 13:30:46', '2024-06-15 13:30:46', '1018018498105802752000003', '29',
        'CUSTOMER', '0', 'PLATFORM', '5787dd65-25fa-4907-91d2-481a5cfb5bdd', '10002', 'COLLECTION',
        'https://i.seadn.io/s/raw/files/174ed0549200ba2ada5542ce3883c45e.jpg?auto=format&dpr=1&w=1000',
        'Computers Cant Jump', 11.180000, 1, 11.180000, 'CONFIRM', 0.000000, null, '2024-06-15 13:30:46', null, null,
        null, null, null, 0, 1, 1);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1008730039459512320, '2024-06-15 13:33:03', '2024-06-15 13:33:09', '1018018503826875392000003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'd152593a-3a7f-4382-9b4b-be76ffd52101', '10002', 'COLLECTION',
        'https://i.seadn.io/s/raw/files/174ed0549200ba2ada5542ce3883c45e.jpg?auto=format&dpr=1&w=1000',
        'Computers Cant Jump', 11.180000, 1, 11.180000, 'PAID', 11.180000, '2024-06-15 13:33:09', '2024-06-15 13:33:03',
        null, null, 'MOCK', '1801850400236572672', null, 0, 2, 1);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `buyer_type`, `seller_id`,
                                `seller_type`, `identifier`, `goods_id`, `goods_type`, `goods_pic_url`, `goods_name`,
                                `item_price`, `item_count`, `order_amount`, `order_state`, `paid_amount`,
                                `pay_succeed_time`, `order_confirmed_time`, `order_finished_time`, `order_closed_time`,
                                `pay_channel`, `pay_stream_id`, `close_type`, `deleted`, `lock_version`,
                                `snapshot_version`)
VALUES (1008734219658067968, '2024-06-15 13:49:39', '2024-06-15 13:49:44', '1018018545629280419840003', '29',
        'CUSTOMER', '0', 'PLATFORM', 'ced8cf9e-6509-478a-b1f0-4a1a5c0d34c1', '10003', 'COLLECTION',
        'https://dl.openseauserdata.com/cache/originImage/files/498954ed62ee36d6de62465f01d5e6bf.png',
        'Absolute Zero Block', 123.180000, 1, 123.180000, 'FINISH', 123.180000, '2024-06-15 13:49:44',
        '2024-06-15 13:49:40', '2024-06-15 13:49:45', null, 'MOCK', '1801854572398878720', null, 0, 3, 1);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `reverse_buyer_id`,
                                `buyer_type`, `seller_id`, `seller_type`, `identifier`, `goods_id`, `goods_type`,
                                `goods_pic_url`, `goods_name`, `item_price`, `item_count`, `order_amount`,
                                `order_state`, `paid_amount`, `pay_succeed_time`, `order_confirmed_time`,
                                `order_finished_time`, `order_closed_time`, `pay_channel`, `pay_stream_id`,
                                `close_type`, `deleted`, `lock_version`, `snapshot_version`)
VALUES (1079133706796400640, '2024-12-26 20:12:05', '2024-12-26 20:12:54', '1018722540488317460480003', '29', '92',
        'CUSTOMER', '0', 'PLATFORM', '4c2f92b6-9fca-48ec-a1ea-cf2b201345b0', '22', 'BLIND_BOX',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '漫威超级英雄盲盒',
        199.900000, 1, 199.900000, 'PAID', 199.900000, '2024-12-26 20:12:54', '2024-12-26 20:12:05', null, null, 'MOCK',
        '1872254242545643520', null, 0, 1, 0);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `reverse_buyer_id`,
                                `buyer_type`, `seller_id`, `seller_type`, `identifier`, `goods_id`, `goods_type`,
                                `goods_pic_url`, `goods_name`, `item_price`, `item_count`, `order_amount`,
                                `order_state`, `paid_amount`, `pay_succeed_time`, `order_confirmed_time`,
                                `order_finished_time`, `order_closed_time`, `pay_channel`, `pay_stream_id`,
                                `close_type`, `deleted`, `lock_version`, `snapshot_version`)
VALUES (1080597640741126144, '2024-12-30 21:09:14', '2024-12-30 21:09:19', '1018737179813672263680003', '29', '92',
        'CUSTOMER', '0', 'PLATFORM', 'e1aefa74-2825-401b-b194-a564fd9fe147', '22', 'BLIND_BOX',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '漫威超级英雄盲盒',
        199.900000, 1, 199.900000, 'PAID', 199.900000, '2024-12-30 21:09:19', '2024-12-30 21:09:14', null, null, 'MOCK',
        '1873717991668363264', null, 0, 1, 0);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `reverse_buyer_id`,
                                `buyer_type`, `seller_id`, `seller_type`, `identifier`, `goods_id`, `goods_type`,
                                `goods_pic_url`, `goods_name`, `item_price`, `item_count`, `order_amount`,
                                `order_state`, `paid_amount`, `pay_succeed_time`, `order_confirmed_time`,
                                `order_finished_time`, `order_closed_time`, `pay_channel`, `pay_stream_id`,
                                `close_type`, `deleted`, `lock_version`, `snapshot_version`)
VALUES (1080603533767606272, '2024-12-30 21:32:39', '2024-12-30 21:32:45', '1018737238688613048320003', '29', '92',
        'CUSTOMER', '0', 'PLATFORM', 'f06d5dcc-c25f-47ce-92dc-ca380c8c16d2', '22', 'BLIND_BOX',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '漫威超级英雄盲盒',
        199.900000, 1, 199.900000, 'PAID', 199.900000, '2024-12-30 21:32:44', '2024-12-30 21:32:37', null, null, 'MOCK',
        '1873723886947184640', null, 0, 1, 0);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `reverse_buyer_id`,
                                `buyer_type`, `seller_id`, `seller_type`, `identifier`, `goods_id`, `goods_type`,
                                `goods_pic_url`, `goods_name`, `item_price`, `item_count`, `order_amount`,
                                `order_state`, `paid_amount`, `pay_succeed_time`, `order_confirmed_time`,
                                `order_finished_time`, `order_closed_time`, `pay_channel`, `pay_stream_id`,
                                `close_type`, `deleted`, `lock_version`, `snapshot_version`)
VALUES (1080605021613064192, '2024-12-30 21:38:34', '2024-12-30 21:39:00', '1018737253626207354880003', '29', '92',
        'CUSTOMER', '0', 'PLATFORM', '4b30f54f-acb9-4cc6-9683-046a525e3e8d', '22', 'BLIND_BOX',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '漫威超级英雄盲盒',
        199.900000, 1, 199.900000, 'FINISH', 199.900000, '2024-12-30 21:38:39', '2024-12-30 21:38:33',
        '2024-12-30 21:39:00', null, 'MOCK', '1873725374352240640', null, 0, 2, 0);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `reverse_buyer_id`,
                                `buyer_type`, `seller_id`, `seller_type`, `identifier`, `goods_id`, `goods_type`,
                                `goods_pic_url`, `goods_name`, `item_price`, `item_count`, `order_amount`,
                                `order_state`, `paid_amount`, `pay_succeed_time`, `order_confirmed_time`,
                                `order_finished_time`, `order_closed_time`, `pay_channel`, `pay_stream_id`,
                                `close_type`, `deleted`, `lock_version`, `snapshot_version`)
VALUES (1080608206385840128, '2024-12-30 21:51:13', '2024-12-30 21:51:36', '1018737285410055905280003', '29', '92',
        'CUSTOMER', '0', 'PLATFORM', '2e8e6dc6-2607-4e66-9442-4d1d22b68916', '22', 'BLIND_BOX',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '漫威超级英雄盲盒',
        199.900000, 1, 199.900000, 'FINISH', 199.900000, '2024-12-30 21:51:18', '2024-12-30 21:51:11',
        '2024-12-30 21:51:36', null, 'MOCK', '1873728556344193024', null, 0, 2, 0);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `reverse_buyer_id`,
                                `buyer_type`, `seller_id`, `seller_type`, `identifier`, `goods_id`, `goods_type`,
                                `goods_pic_url`, `goods_name`, `item_price`, `item_count`, `order_amount`,
                                `order_state`, `paid_amount`, `pay_succeed_time`, `order_confirmed_time`,
                                `order_finished_time`, `order_closed_time`, `pay_channel`, `pay_stream_id`,
                                `close_type`, `deleted`, `lock_version`, `snapshot_version`)
VALUES (1080609099118280704, '2024-12-30 21:54:46', '2024-12-31 14:48:57', '1018737294402937323520003', '29', '92',
        'CUSTOMER', '0', 'PLATFORM', '1e9781ec-703c-448a-9de1-728854a36785', '22', 'BLIND_BOX',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '漫威超级英雄盲盒',
        199.900000, 1, 199.900000, 'FINISH', 199.900000, '2024-12-30 21:54:51', '2024-12-30 21:54:46',
        '2024-12-31 14:48:57', null, 'MOCK', '1873729451614187520', null, 0, 2, 0);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `reverse_buyer_id`,
                                `buyer_type`, `seller_id`, `seller_type`, `identifier`, `goods_id`, `goods_type`,
                                `goods_pic_url`, `goods_name`, `item_price`, `item_count`, `order_amount`,
                                `order_state`, `paid_amount`, `pay_succeed_time`, `order_confirmed_time`,
                                `order_finished_time`, `order_closed_time`, `pay_channel`, `pay_stream_id`,
                                `close_type`, `deleted`, `lock_version`, `snapshot_version`)
VALUES (1080612388438278145, '2024-12-30 22:07:50', '2024-12-31 15:05:01', '1018737327306287513600003', '29', '92',
        'CUSTOMER', '0', 'PLATFORM', '81840d86-539a-4ee6-b080-73ff7a052908', '22', 'BLIND_BOX',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '漫威超级英雄盲盒',
        199.900000, 1, 199.900000, 'FINISH', 199.900000, '2024-12-30 22:07:56', '2024-12-30 22:07:50',
        '2024-12-31 15:05:01', null, 'MOCK', '1873732745321426944', null, 0, 2, 0);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `reverse_buyer_id`,
                                `buyer_type`, `seller_id`, `seller_type`, `identifier`, `goods_id`, `goods_type`,
                                `goods_pic_url`, `goods_name`, `item_price`, `item_count`, `order_amount`,
                                `order_state`, `paid_amount`, `pay_succeed_time`, `order_confirmed_time`,
                                `order_finished_time`, `order_closed_time`, `pay_channel`, `pay_stream_id`,
                                `close_type`, `deleted`, `lock_version`, `snapshot_version`)
VALUES (1080612441886294016, '2024-12-30 22:08:03', '2024-12-31 15:13:01', '1018737327842361507840003', '29', '92',
        'CUSTOMER', '0', 'PLATFORM', 'c392f78a-a19e-44ca-9696-3c7892ffdd91', '22', 'BLIND_BOX',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '漫威超级英雄盲盒',
        199.900000, 1, 199.900000, 'FINISH', 199.900000, '2024-12-30 22:08:08', '2024-12-30 22:08:03',
        '2024-12-31 15:13:01', null, 'MOCK', '1873732793832747008', null, 0, 2, 0);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `reverse_buyer_id`,
                                `buyer_type`, `seller_id`, `seller_type`, `identifier`, `goods_id`, `goods_type`,
                                `goods_pic_url`, `goods_name`, `item_price`, `item_count`, `order_amount`,
                                `order_state`, `paid_amount`, `pay_succeed_time`, `order_confirmed_time`,
                                `order_finished_time`, `order_closed_time`, `pay_channel`, `pay_stream_id`,
                                `close_type`, `deleted`, `lock_version`, `snapshot_version`)
VALUES (1080612487683899393, '2024-12-30 22:08:14', '2024-12-31 14:56:04', '1018737328301470023680003', '29', '92',
        'CUSTOMER', '0', 'PLATFORM', '7b42cb9b-e154-4874-b3ee-f5cb701a124f', '22', 'BLIND_BOX',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '漫威超级英雄盲盒',
        199.900000, 1, 199.900000, 'FINISH', 199.900000, '2024-12-30 22:08:19', '2024-12-30 22:08:14',
        '2024-12-31 14:56:04', null, 'MOCK', '1873732841719115776', null, 0, 2, 0);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `reverse_buyer_id`,
                                `buyer_type`, `seller_id`, `seller_type`, `identifier`, `goods_id`, `goods_type`,
                                `goods_pic_url`, `goods_name`, `item_price`, `item_count`, `order_amount`,
                                `order_state`, `paid_amount`, `pay_succeed_time`, `order_confirmed_time`,
                                `order_finished_time`, `order_closed_time`, `pay_channel`, `pay_stream_id`,
                                `close_type`, `deleted`, `lock_version`, `snapshot_version`)
VALUES (1080869559654678528, '2024-12-31 15:09:45', '2024-12-31 15:12:57', '1018739899005155696640003', '29', '92',
        'CUSTOMER', '0', 'PLATFORM', '55fd6f82-ff0d-4044-ae6d-62a35a231a1d', '22', 'BLIND_BOX',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '漫威超级英雄盲盒',
        199.900000, 1, 199.900000, 'FINISH', 199.900000, '2024-12-31 15:09:50', '2024-12-31 15:09:44',
        '2024-12-31 15:12:57', null, 'MOCK', '1873989911009734656', null, 0, 2, 0);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `reverse_buyer_id`,
                                `buyer_type`, `seller_id`, `seller_type`, `identifier`, `goods_id`, `goods_type`,
                                `goods_pic_url`, `goods_name`, `item_price`, `item_count`, `order_amount`,
                                `order_state`, `paid_amount`, `pay_succeed_time`, `order_confirmed_time`,
                                `order_finished_time`, `order_closed_time`, `pay_channel`, `pay_stream_id`,
                                `close_type`, `deleted`, `lock_version`, `snapshot_version`)
VALUES (1080869605431312385, '2024-12-31 15:09:56', '2024-12-31 15:13:08', '1018739899476217978880003', '29', '92',
        'CUSTOMER', '0', 'PLATFORM', 'f13ff735-1745-4875-a917-3288b6ad36be', '22', 'BLIND_BOX',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '漫威超级英雄盲盒',
        199.900000, 1, 199.900000, 'FINISH', 199.900000, '2024-12-31 15:10:00', '2024-12-31 15:09:55',
        '2024-12-31 15:13:09', null, 'MOCK', '1873989956828311552', null, 0, 2, 0);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `reverse_buyer_id`,
                                `buyer_type`, `seller_id`, `seller_type`, `identifier`, `goods_id`, `goods_type`,
                                `goods_pic_url`, `goods_name`, `item_price`, `item_count`, `order_amount`,
                                `order_state`, `paid_amount`, `pay_succeed_time`, `order_confirmed_time`,
                                `order_finished_time`, `order_closed_time`, `pay_channel`, `pay_stream_id`,
                                `close_type`, `deleted`, `lock_version`, `snapshot_version`)
VALUES (1080869649408589824, '2024-12-31 15:10:06', '2024-12-31 15:10:26', '1018739899918255677440003', '29', '92',
        'CUSTOMER', '0', 'PLATFORM', 'c1004c5d-5b27-40f0-94b7-23a60e252d57', '22', 'BLIND_BOX',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '漫威超级英雄盲盒',
        199.900000, 1, 199.900000, 'FINISH', 199.900000, '2024-12-31 15:10:11', '2024-12-31 15:10:06',
        '2024-12-31 15:10:27', null, 'MOCK', '1873990003183759360', null, 0, 2, 0);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `reverse_buyer_id`,
                                `buyer_type`, `seller_id`, `seller_type`, `identifier`, `goods_id`, `goods_type`,
                                `goods_pic_url`, `goods_name`, `item_price`, `item_count`, `order_amount`,
                                `order_state`, `paid_amount`, `pay_succeed_time`, `order_confirmed_time`,
                                `order_finished_time`, `order_closed_time`, `pay_channel`, `pay_stream_id`,
                                `close_type`, `deleted`, `lock_version`, `snapshot_version`)
VALUES (1080885963346935808, '2024-12-31 16:14:56', '2024-12-31 16:15:01', '1018740063055584133120003', '29', '92',
        'CUSTOMER', '0', 'PLATFORM', '31e9f7f3-0344-4bc4-a2ff-122f8c6b0e27', '22', 'BLIND_BOX',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '漫威超级英雄盲盒',
        199.900000, 1, 199.900000, 'PAID', 199.900000, '2024-12-31 16:15:01', '2024-12-31 16:14:55', null, null, 'MOCK',
        '1874006318246178816', null, 0, 1, 0);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `reverse_buyer_id`,
                                `buyer_type`, `seller_id`, `seller_type`, `identifier`, `goods_id`, `goods_type`,
                                `goods_pic_url`, `goods_name`, `item_price`, `item_count`, `order_amount`,
                                `order_state`, `paid_amount`, `pay_succeed_time`, `order_confirmed_time`,
                                `order_finished_time`, `order_closed_time`, `pay_channel`, `pay_stream_id`,
                                `close_type`, `deleted`, `lock_version`, `snapshot_version`)
VALUES (1080887078884999168, '2024-12-31 16:19:22', '2024-12-31 16:20:22', '1018740074203834490880003', '29', '92',
        'CUSTOMER', '0', 'PLATFORM', '94e39897-254a-4e25-a8ae-9fc9ea3a69ae', '22', 'BLIND_BOX',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '漫威超级英雄盲盒',
        199.900000, 1, 199.900000, 'FINISH', 199.900000, '2024-12-31 16:19:27', '2024-12-31 16:19:21',
        '2024-12-31 16:20:22', null, 'MOCK', '1874007430613348352', null, 0, 2, 0);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `reverse_buyer_id`,
                                `buyer_type`, `seller_id`, `seller_type`, `identifier`, `goods_id`, `goods_type`,
                                `goods_pic_url`, `goods_name`, `item_price`, `item_count`, `order_amount`,
                                `order_state`, `paid_amount`, `pay_succeed_time`, `order_confirmed_time`,
                                `order_finished_time`, `order_closed_time`, `pay_channel`, `pay_stream_id`,
                                `close_type`, `deleted`, `lock_version`, `snapshot_version`)
VALUES (1080887123629834241, '2024-12-31 16:19:32', '2024-12-31 16:19:37', '1018740074660384481280003', '29', '92',
        'CUSTOMER', '0', 'PLATFORM', 'df0a9a45-a1fa-4b7b-abb1-0fcee6dd219b', '22', 'BLIND_BOX',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '漫威超级英雄盲盒',
        199.900000, 1, 199.900000, 'PAID', 199.900000, '2024-12-31 16:19:37', '2024-12-31 16:19:32', null, null, 'MOCK',
        '1874007474603208704', null, 0, 1, 0);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `reverse_buyer_id`,
                                `buyer_type`, `seller_id`, `seller_type`, `identifier`, `goods_id`, `goods_type`,
                                `goods_pic_url`, `goods_name`, `item_price`, `item_count`, `order_amount`,
                                `order_state`, `paid_amount`, `pay_succeed_time`, `order_confirmed_time`,
                                `order_finished_time`, `order_closed_time`, `pay_channel`, `pay_stream_id`,
                                `close_type`, `deleted`, `lock_version`, `snapshot_version`)
VALUES (1080887755761778688, '2024-12-31 16:22:03', '2024-12-31 16:22:18', '1018740080981829754880003', '29', '92',
        'CUSTOMER', '0', 'PLATFORM', 'a2fc6505-e546-4226-8406-605b390c94b2', '22', 'BLIND_BOX',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '漫威超级英雄盲盒',
        199.900000, 1, 199.900000, 'CLOSED', 0.000000, null, '2024-12-31 16:22:03', null, '2024-12-31 16:22:19', null,
        null, 'CANCEL', 0, 1, 0);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `reverse_buyer_id`,
                                `buyer_type`, `seller_id`, `seller_type`, `identifier`, `goods_id`, `goods_type`,
                                `goods_pic_url`, `goods_name`, `item_price`, `item_count`, `order_amount`,
                                `order_state`, `paid_amount`, `pay_succeed_time`, `order_confirmed_time`,
                                `order_finished_time`, `order_closed_time`, `pay_channel`, `pay_stream_id`,
                                `close_type`, `deleted`, `lock_version`, `snapshot_version`)
VALUES (1080890783814385664, '2024-12-31 16:34:05', '2024-12-31 16:34:10', '1018740111252960665600003', '29', '92',
        'CUSTOMER', '0', 'PLATFORM', 'dea7d2f6-88cd-4484-b4a9-b84b11517669', '22', 'BLIND_BOX',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '漫威超级英雄盲盒',
        199.900000, 1, 199.900000, 'PAID', 199.900000, '2024-12-31 16:34:10', '2024-12-31 16:34:05', null, null, 'MOCK',
        '1874011134657736704', null, 0, 1, 0);
INSERT INTO `trade_order_0003` (`id`, `gmt_create`, `gmt_modified`, `order_id`, `buyer_id`, `reverse_buyer_id`,
                                `buyer_type`, `seller_id`, `seller_type`, `identifier`, `goods_id`, `goods_type`,
                                `goods_pic_url`, `goods_name`, `item_price`, `item_count`, `order_amount`,
                                `order_state`, `paid_amount`, `pay_succeed_time`, `order_confirmed_time`,
                                `order_finished_time`, `order_closed_time`, `pay_channel`, `pay_stream_id`,
                                `close_type`, `deleted`, `lock_version`, `snapshot_version`)
VALUES (1080890914714419201, '2024-12-31 16:34:36', '2024-12-31 16:34:48', '1018740112567958855680003', '29', '92',
        'CUSTOMER', '0', 'PLATFORM', '6bb5bc10-1fde-4268-8a76-da39106dcb3b', '22', 'BLIND_BOX',
        'https://files.nowre.com/articles/2019/03/1522924460-avengers-infinity-war-poster.jpg', '漫威超级英雄盲盒',
        199.900000, 1, 199.900000, 'CLOSED', 0.000000, null, '2024-12-31 16:34:36', null, '2024-12-31 16:34:48', null,
        null, 'CANCEL', 0, 1, 0);


-- 核对可能会用到的数据

-- 1. 微信有，支付订单没有（单边账）
INSERT INTO `wechat_transaction` (date, `app_id`, `mch_id`, `wechat_order_no`, `mch_order_no`, `user_id`, type, status,
                                  amount, `gmt_create`, `gmt_modified`, `lock_version`, `deleted`)
VALUES ('2025-10-01 11:00:00', 'wxf8b4f85f3a794e7a', '1234567890', 'wx1234567890123', 'MCH20230501001', 'user123',
        'JSAPI', 'SUCCESS', 100.00, NOW(), NOW(), 1, 0);

-- 2. 支付订单有，微信没有（单边账）
INSERT INTO `pay_order` (`pay_order_id`, `payer_id`, `payer_type`, `payee_id`, `payee_type`, `biz_no`, `biz_type`,
                         `order_amount`, `paid_amount`, `pay_channel`, `order_state`, `pay_succeed_time`, `gmt_create`,
                         `gmt_modified`, `lock_version`, `deleted`)
VALUES ('PO20230501001', 'user456', 'CUSTOMER', 'merchant789', 'PLATFORM', 'BIZ20230501001', 'TRADE_ORDER', 200.00, 200.00,
        'WECHAT', 'PAID', '2025-10-01 11:00:00', NOW(), NOW(), 1, 0);

-- 3. 双方都有，但状态不一致
INSERT INTO `pay_order` (`pay_order_id`, `payer_id`, `payer_type`, `payee_id`, `payee_type`, `biz_no`, `biz_type`,
                         `order_amount`, `paid_amount`, `channel_stream_id`, `pay_channel`, `order_state`,
                         `pay_succeed_time`, `gmt_create`, `gmt_modified`, `lock_version`, `deleted`)
VALUES ('PO20230501002', 'user789', 'CUSTOMER', 'merchant123', 'PLATFORM', 'BIZ20230501002', 'TRADE_ORDER', 300.00, 300.00,
        'wx9876543210987654', 'WECHAT', 'PAID', '2025-10-01 11:00:00', NOW(), NOW(), 1, 0);

INSERT INTO `wechat_transaction` (date, `app_id`, `mch_id`, `wechat_order_no`, `mch_order_no`, `user_id`, type, status,
                                  amount, `gmt_create`, `gmt_modified`, `lock_version`, `deleted`)
VALUES ('2025-10-01 11:00:00', 'wxf8b4f85f3a794e7a', '1234567890', 'wx1234567890456', 'PO20230501002', 'user789',
        'JSAPI', 'INIT', 300.00, NOW(), NOW(), 1, 0);


INSERT INTO `pay_order` (`pay_order_id`, `payer_id`, `payer_type`, `payee_id`, `payee_type`, `biz_no`, `biz_type`,
                         `order_amount`, `paid_amount`, `channel_stream_id`, `pay_channel`, `order_state`,
                         `pay_succeed_time`, `gmt_create`, `gmt_modified`, `lock_version`, `deleted`)
VALUES ('PO20230501112', '29', 'CUSTOMER', 'merchant123', 'PLATFORM', '1018018440757069660160003', 'TRADE_ORDER', 300.00, 0.00,
        null, 'WECHAT', 'PAYING', null, NOW(), NOW(), 1, 0);

INSERT INTO `wechat_transaction` (date, `app_id`, `mch_id`, `wechat_order_no`, `mch_order_no`, `user_id`, type, status,
                                  amount, `gmt_create`, `gmt_modified`, `lock_version`, `deleted`)
VALUES ('2025-10-01 11:00:00', 'wxf8b4f85f3a794e7a', '1234567890', 'wx12343127890456', 'PO20230501112', 'user789',
        'JSAPI', 'SUCCESS', 300.00, NOW(), NOW(), 1, 0);

-- 4. 双方都有，状态一致但金额不一致
INSERT INTO `pay_order` (`pay_order_id`, `payer_id`, `payer_type`, `payee_id`, `payee_type`, `biz_no`, `biz_type`,
                         `order_amount`, `paid_amount`, `channel_stream_id`, `pay_channel`, `order_state`,
                         `pay_succeed_time`, `gmt_create`, `gmt_modified`, `lock_version`, `deleted`)
VALUES ('PO20230501003', 'user345', 'CUSTOMER', 'merchant456', 'PLATFORM', 'BIZ20230501003', 'TRADE_ORDER', 400.00, 400.00,
        'wx1122334455667788', 'WECHAT', 'PAID', '2025-10-01 11:00:00', NOW(), NOW(), 1, 0);

INSERT INTO `wechat_transaction` (date, `app_id`, `mch_id`, `wechat_order_no`, `mch_order_no`, `user_id`, type, status,
                                  amount, `gmt_create`, `gmt_modified`, `lock_version`, `deleted`)
VALUES ('2025-10-01 11:00:00', 'wxf8b4f85f3a794e7a', '1234567890', 'wx1267890123456', 'PO20230501003', 'user345',
        'JSAPI', 'SUCCESS', 399.00, NOW(), NOW(), 1, 0);