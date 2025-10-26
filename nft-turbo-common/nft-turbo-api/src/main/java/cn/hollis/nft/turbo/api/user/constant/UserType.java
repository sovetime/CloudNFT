package cn.hollis.nft.turbo.api.user.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum UserType {

    CUSTOMER("用户"),

    PLATFORM("平台");

    private String desc;

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
