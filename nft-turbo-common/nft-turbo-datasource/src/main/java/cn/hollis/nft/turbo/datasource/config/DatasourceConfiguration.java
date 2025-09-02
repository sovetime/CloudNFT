package cn.hollis.nft.turbo.datasource.config;

import cn.hollis.nft.turbo.datasource.handler.DataObjectHandler;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan(basePackages = "cn.hollis.nft.turbo.*.infrastructure.mapper")
public class DatasourceConfiguration {

    @Bean
    public DataObjectHandler myMetaObjectHandler() {
        return new DataObjectHandler();
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //乐观锁插件
        //用于处理并发更新时的数据一致性问题，基于版本号机制实现乐观锁
        //作用位置，所有带version 字段的实体类，执行更新操作时会检测版本号，防止并发冲突
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        //防全表更新与删除插件，防止误操作导致的全表更新或删除
        //作用位置，所有的update和delete操作，会检测是否存在主键或者unique索引，如果存在，则进行拦截，不允许执行
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        //分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
