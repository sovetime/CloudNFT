package cn.hollis.nft.turbo.datasource.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;


@Setter
@Getter
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键
    @TableId(type = IdType.AUTO)
    private Long id;

    //是否删除
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;

    //乐观锁版本号
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer lockVersion;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    //修改时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @Override
    public String toString() {
        return new StringJoiner(", ", BaseEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("deleted=" + deleted)
                .add("lockVersion=" + lockVersion)
                .toString();
    }
}
