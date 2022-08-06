package com.example.bvnvalidator.controller;

import com.example.bvnvalidator.dto.BvnRequest;
import com.example.bvnvalidator.dto.BvnResponse;
import com.example.bvnvalidator.service.BvnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bv-service/svalidate/wrapper")
public class BvnController {

    private final BvnService bvnService;

    @Autowired
    public BvnController(BvnService bvnService) {
        this.bvnService = bvnService;
    }

    @PostMapping
    ResponseEntity<BvnResponse> validate(@RequestBody BvnRequest request){
        return ResponseEntity.ok(bvnService.validate(request));
    }
}
