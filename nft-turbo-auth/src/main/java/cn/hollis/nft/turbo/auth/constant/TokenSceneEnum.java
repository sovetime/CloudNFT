package cn.hollis.nft.turbo.auth.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


//token获取的场景
@Getter
@AllArgsConstructor
public enum TokenSceneEnum {

    //下单-藏品
    BUY_COLLECTION("buy"),


    //下单-盲盒
    BUY_BLIND_BOX("buyBb");

    //场景的值
    private String scene;

    public void setScene(String scene) {
        this.scene = scene;
    }

    public static TokenSceneEnum getByScene(String scene) {
        return Arrays.stream(TokenSceneEnum.values()).filter(tokenSceneEnum -> tokenSceneEnum.getScene().equals(scene)).findFirst().orElse(null);
    }
}
