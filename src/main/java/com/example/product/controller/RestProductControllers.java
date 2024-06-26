package com.example.product.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.product.entity.Products;
import com.example.product.repository.RestApiRepository;
import com.example.product.repository.RestProductRepository;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class RestProductControllers {

    @Autowired
    private RestProductRepository restProductRepository;

    @Autowired
    private RestApiRepository restApiRepository;

    @GetMapping("/")
    public String getMethodName() {
        return "Test API...";
    }

    @GetMapping("/Products")
    public List<Products> product() {

        String response_access = "";
        ResponseEntity responseEntity = null;

        try {
            response_access = restApiRepository.getKeyToken();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response_access == "OK") {
            return restProductRepository.getSkus();
        }
        return null;
    }

    @GetMapping("/Products/{sku}")
    public ResponseEntity<Products> getProductBySku(@PathVariable String sku) {

        String response_access = "";
        ResponseEntity responseEntity = null;

        try {
            response_access = restApiRepository.getKeyToken();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response_access == "OK") {
            List<Products> product = restProductRepository.findBySKU(sku);
            if (product.size() != 0) {
                responseEntity = ResponseEntity.ok(product.get(0));
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }

        return responseEntity;

    }

}
