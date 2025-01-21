package com.example.productdealcheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class ProductDealCheckController {

    @Value("${products.json.path}")
    private String productsJsonPath;
    
    
    @GetMapping("/products")
    public ResponseEntity<Map<String, List<Map<String, Object>>>> getProducts() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<Map<String, Object>>> products = mapper.readValue(
                new ClassPathResource("products.json").getFile(),
                Map.class
        );
        return ResponseEntity.ok(products);
    }
    

    @GetMapping("/getdealers/{product}")
    public ResponseEntity<List<String>> getDealers(@PathVariable String product) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<Map<String, Object>>> products = mapper.readValue(
                new ClassPathResource("products.json").getFile(),
                Map.class
        );

        List<String> dealers = null;
        for (Map<String, Object> item : products.get("products")) {
            if (item.get("product").equals(product)) {
                dealers = (List<String>) item.get("Dealers");
                break;
            }
        }
        return dealers != null ? ResponseEntity.ok(dealers) : ResponseEntity.notFound().build();
    }
}
