package cn.hollis.nft.turbo.user.infrastructure.mapper;

import cn.hollis.nft.turbo.user.infrastructure.util.AesUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


//AES加密类型处理器
public class AesEncryptTypeHandler extends BaseTypeHandler<String> {

    //设置非空参数，将明文参数加密后设置到PreparedStatement中
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        // 这里使用你的加密方法进行加密
        ps.setString(i, encrypt(parameter));
    }

    //从ResultSet中获取指定列名的加密数据并解密返回
    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String encrypted = rs.getString(columnName);
        return encrypted == null ? null : decrypt(encrypted);
    }

    //从ResultSet中获取指定列名的加密数据并解密返回
    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String encrypted = rs.getString(columnIndex);
        return encrypted == null ? null : decrypt(encrypted);
    }

    //从CallableStatement中获取指定列名的加密数据并解密返回
    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String encrypted = cs.getString(columnIndex);
        return encrypted == null ? null : decrypt(encrypted);
    }

    //加密
    private String encrypt(String data) {
        return AesUtil.encrypt(data);
    }

    //解密
    private String decrypt(String data) {
        return AesUtil.decrypt(data);
    }
}
