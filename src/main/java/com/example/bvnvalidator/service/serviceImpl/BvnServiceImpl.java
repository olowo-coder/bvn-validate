package com.example.bvnvalidator.service.serviceImpl;

import com.example.bvnvalidator.constant.ResponseCode;
import com.example.bvnvalidator.dto.BvnRequest;
import com.example.bvnvalidator.dto.BvnResponse;
import com.example.bvnvalidator.model.Bvn;
import com.example.bvnvalidator.model.RequestResponse;
import com.example.bvnvalidator.repository.RequestResponseRepository;
import com.example.bvnvalidator.service.BvnService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class BvnServiceImpl implements BvnService {

    private Map<String, Bvn> mockDb = new HashMap<>();

    private final RequestResponseRepository requestResponseRepository;

    @Autowired
    public BvnServiceImpl(RequestResponseRepository requestResponseRepository) {
        this.requestResponseRepository = requestResponseRepository;
    }

    @PostConstruct
    public void init(){
        Bvn bvn = new Bvn();
        bvn.setBvnNumber("12345678901");
        bvn.setImageDetail("https://192.168.0.2/image");
        bvn.setBasicDetail("Alex Smith");
        mockDb.put(bvn.getBvnNumber(), bvn);
    }

    @Override
    public BvnResponse validate(BvnRequest request) {
        BvnResponse response = null;
        if(request.getBvn().isEmpty()){
            response = BvnResponse.builder().code(ResponseCode.EMPTY_BVN.getCode())
                    .message(ResponseCode.EMPTY_BVN.getMessage()).build();
        }else if(request.getBvn().length() != 11){
            response = BvnResponse.builder().code(ResponseCode.BVN_LESS_THAN.getCode())
                    .message(ResponseCode.BVN_LESS_THAN.getMessage()).build();
        } else if(request.getBvn().length() == 11 && !request.getBvn().matches("\\d{11}")){
            response = BvnResponse.builder().code(ResponseCode.NON_DIGIT_BVN.getCode())
                    .message(ResponseCode.NON_DIGIT_BVN.getMessage()).build();
        } else if(!mockDb.containsKey(request.getBvn())){
            response = BvnResponse.builder().code(ResponseCode.BVN_NOT_EXIST.getCode())
                    .message(ResponseCode.BVN_NOT_EXIST.getMessage()).build();
        } else {
            response = BvnResponse.builder().code(ResponseCode.SUCCESS.getCode())
                    .message(ResponseCode.SUCCESS.getMessage())
                    .imageDetail(Base64.getEncoder().encodeToString(mockDb.get(request.getBvn()).getImageDetail().getBytes()))
                    .basicDetail(Base64.getEncoder().encodeToString(mockDb.get(request.getBvn()).getBasicDetail().getBytes())).build();
        }
        logsReqRes(request, response);
        return response;
    }

    @Async
    public void logsReqRes(BvnRequest request, BvnResponse response) {
        ObjectMapper mapper = new ObjectMapper();
        RequestResponse requestResponse = new RequestResponse();
        try{
            requestResponse.setRequest(mapper.writeValueAsString(request));
            requestResponse.setResponse(mapper.writeValueAsString(response));
        }catch (Exception ex){
            log.info("Unable to process {}", requestResponse);
        }
        requestResponseRepository.save(requestResponse);
        log.info("Save and log request and response {}", requestResponse);
    }


}
