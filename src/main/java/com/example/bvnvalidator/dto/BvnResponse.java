package com.example.bvnvalidator.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BvnResponse {

    private String code;
    private String message;
    private String imageDetail;
    private String basicDetail;
}
