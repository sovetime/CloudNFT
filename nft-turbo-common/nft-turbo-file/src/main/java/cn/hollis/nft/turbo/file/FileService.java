package cn.hollis.nft.turbo.file;

import java.io.InputStream;


//文件 服务
public interface FileService {

    //文件上传
    public boolean upload(String path, InputStream fileStream);

}
