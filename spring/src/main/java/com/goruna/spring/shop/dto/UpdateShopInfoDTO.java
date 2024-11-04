package com.goruna.spring.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateShopInfoDTO {

    private String shopName;
    private String shopAddress;
    private String shopIntroduction;

}