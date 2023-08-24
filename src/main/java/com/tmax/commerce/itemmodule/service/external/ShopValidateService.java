package com.tmax.commerce.itemmodule.service.external;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopValidateService { // TODO: shopValidate
//    private final RestTemplate restTemplate;
//
//    public boolean isShopIdValid(UUID shopId) {
//        String shopUrl = "http://tmaxcommerce-shops/" + shopId;
//        ResponseEntity<Void> response = restTemplate.getForEntity(shopUrl, Void.class);
//
//        return response.getStatusCode() == HttpStatus.OK;
//    }
}