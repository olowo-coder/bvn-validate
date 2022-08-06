package com.example.bvnvalidator.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Document(collection = "RequestResponse")
public class RequestResponse {

    @Id
    private String id;
    private String request;
    private String response;
    private LocalDateTime date = LocalDateTime.now();
}
