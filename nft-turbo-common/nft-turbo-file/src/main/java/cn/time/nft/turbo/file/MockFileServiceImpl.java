package cn.time.nft.turbo.file;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * oss 服务
 *
 * @author time
 */
@Slf4j
@Setter
public class MockFileServiceImpl implements FileService {


    @Override
    public boolean upload(String path, InputStream fileStream) {
        return true;
    }

}
