package com.example.bvnvalidator.service;

import com.example.bvnvalidator.dto.BvnRequest;
import com.example.bvnvalidator.dto.BvnResponse;

public interface BvnService {
    BvnResponse validate(BvnRequest request);
}
