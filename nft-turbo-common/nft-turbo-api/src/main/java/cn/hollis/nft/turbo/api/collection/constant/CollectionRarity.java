package cn.hollis.nft.turbo.api.collection.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;

//藏品稀有度
@AllArgsConstructor
@Getter
public enum CollectionRarity {

    COMMON("普通"),

    RARE("稀有"),

    EPIC("史诗"),

    LEGENDARY("传说"),

    UNIQUE("独特"),

    MYTHICAL("神话");

    private String value;
}
