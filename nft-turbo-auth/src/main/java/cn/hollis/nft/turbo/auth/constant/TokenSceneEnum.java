package cn.hollis.nft.turbo.auth.constant;

import lombok.Getter;

import java.util.Arrays;


//token获取的场景
public enum TokenSceneEnum {

    //下单-藏品
    BUY_COLLECTION("token:buy:clc"),

    //下单-盲盒
    BUY_BLIND_BOX("token:buy:blb");

    //场景的值
    private String scene;

    TokenSceneEnum(String scene) {
        this.scene = scene;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public static TokenSceneEnum getByScene(String scene) {
        return Arrays.stream(TokenSceneEnum.values()).filter(tokenSceneEnum -> tokenSceneEnum.getScene().equals(scene)).findFirst().orElse(null);
    }
}
