package com.example.bvnvalidator.service.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.bvnvalidator.dto.BvnRequest;
import com.example.bvnvalidator.dto.BvnResponse;
import com.example.bvnvalidator.model.RequestResponse;
import com.example.bvnvalidator.repository.RequestResponseRepository;

import java.time.LocalDateTime;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BvnServiceImpl.class})
@ExtendWith(SpringExtension.class)
class BvnServiceImplTest {
    @Autowired
    private BvnServiceImpl bvnServiceImpl;

    @MockBean
    private RequestResponseRepository requestResponseRepository;


    @Test
    void testSuccess() {
        RequestResponse requestResponse = new RequestResponse();
        requestResponse.setDate(LocalDateTime.of(1, 1, 1, 1, 1));
        requestResponse.setId("42");
        requestResponse.setRequest("Request");
        requestResponse.setResponse("Response");
        when(requestResponseRepository.save(any())).thenReturn(requestResponse);
        BvnRequest bvnRequest = mock(BvnRequest.class);
        when(bvnRequest.getBvn()).thenReturn("12345678901");
        doNothing().when(bvnRequest).setBvn(any());
        bvnRequest.setBvn("12345678901");
        BvnResponse actualValidateResult = bvnServiceImpl.validate(bvnRequest);
        assertEquals(Base64.getEncoder().encodeToString("Alex Smith".getBytes()),
                actualValidateResult.getBasicDetail());
        assertEquals("Success", actualValidateResult.getMessage());
        assertEquals(Base64.getEncoder().encodeToString("https://192.168.0.2/image".getBytes()), actualValidateResult.getImageDetail());
        assertEquals("00", actualValidateResult.getCode());
        verify(requestResponseRepository).save(any());
        verify(bvnRequest, atLeast(1)).getBvn();
        verify(bvnRequest).setBvn(any());
    }

    @Test
    void testNotExist() {
        RequestResponse requestResponse = new RequestResponse();
        requestResponse.setDate(LocalDateTime.of(1, 1, 1, 1, 1));
        requestResponse.setId("42");
        requestResponse.setRequest("Request");
        requestResponse.setResponse("Response");
        when(requestResponseRepository.save(any())).thenReturn(requestResponse);

        BvnRequest bvnRequest = new BvnRequest();
        bvnRequest.setBvn("12345678906");
        BvnResponse actualValidateResult = bvnServiceImpl.validate(bvnRequest);
        assertNull(actualValidateResult.getBasicDetail());
        assertEquals("The searched BVN does not exist", actualValidateResult.getMessage());
        assertNull(actualValidateResult.getImageDetail());
        assertEquals("01", actualValidateResult.getCode());
        verify(requestResponseRepository).save(any());
    }

    @Test
    void testContainsNonDigits() {
        RequestResponse requestResponse = new RequestResponse();
        requestResponse.setDate(LocalDateTime.of(1, 1, 1, 1, 1));
        requestResponse.setId("42");
        requestResponse.setRequest("Request");
        requestResponse.setResponse("Response");
        when(requestResponseRepository.save((RequestResponse) any())).thenReturn(requestResponse);
        BvnRequest bvnRequest = mock(BvnRequest.class);
        when(bvnRequest.getBvn()).thenReturn("1234567890D");
        doNothing().when(bvnRequest).setBvn(any());
        bvnRequest.setBvn("1234567890D");
        BvnResponse actualValidateResult = bvnServiceImpl.validate(bvnRequest);
        assertNull(actualValidateResult.getBasicDetail());
        assertEquals("The searched BVN is invalid", actualValidateResult.getMessage());
        assertNull(actualValidateResult.getImageDetail());
        assertEquals("400", actualValidateResult.getCode());
        verify(requestResponseRepository).save(any());
        verify(bvnRequest, atLeast(1)).getBvn();
        verify(bvnRequest).setBvn(any());
    }

    @Test
    void validateLessThanEleven() {
        RequestResponse requestResponse = new RequestResponse();
        requestResponse.setDate(LocalDateTime.of(1, 1, 1, 1, 1));
        requestResponse.setId("42");
        requestResponse.setRequest("Request");
        requestResponse.setResponse("Response");
        when(requestResponseRepository.save(any())).thenReturn(requestResponse);
        BvnRequest bvnRequest = mock(BvnRequest.class);
        when(bvnRequest.getBvn()).thenReturn("123456789");
        doNothing().when(bvnRequest).setBvn(any());
        bvnRequest.setBvn("123456789");
        BvnResponse actualValidateResult = bvnServiceImpl.validate(bvnRequest);
        assertNull(actualValidateResult.getBasicDetail());
        assertEquals("The searched BVN is invalid", actualValidateResult.getMessage());
        assertNull(actualValidateResult.getImageDetail());
        assertEquals("02", actualValidateResult.getCode());
        verify(requestResponseRepository).save(any());
        verify(bvnRequest, atLeast(1)).getBvn();
        verify(bvnRequest).setBvn(any());
    }

    @Test
    void validateEmpty() {
        RequestResponse requestResponse = new RequestResponse();
        requestResponse.setDate(LocalDateTime.of(1, 1, 1, 1, 1));
        requestResponse.setId("42");
        requestResponse.setRequest("Request");
        requestResponse.setResponse("Response");
        when(requestResponseRepository.save((RequestResponse) any())).thenReturn(requestResponse);
        BvnRequest bvnRequest = mock(BvnRequest.class);
        when(bvnRequest.getBvn()).thenReturn("");
        doNothing().when(bvnRequest).setBvn(any());
        bvnRequest.setBvn("");
        BvnResponse actualValidateResult = bvnServiceImpl.validate(bvnRequest);
        assertNull(actualValidateResult.getBasicDetail());
        assertEquals("One or more of your request parameters failed validation. please retry",
                actualValidateResult.getMessage());
        assertNull(actualValidateResult.getImageDetail());
        assertEquals("400", actualValidateResult.getCode());
        verify(requestResponseRepository).save(any());
        verify(bvnRequest, atLeast(1)).getBvn();
        verify(bvnRequest).setBvn(any());
    }
}

