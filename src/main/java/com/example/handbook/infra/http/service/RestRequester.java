package com.example.handbook.infra.http.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.uuid.Generators;
import lombok.extern.log4j.Log4j2;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import com.example.handbook.config.ApplicationProperties;
import com.example.handbook.infra.http.dto.WarmUpBRq;
import com.example.handbook.infra.http.dto.WarmUpRq;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Log4j2
@Service
public class RestRequester {

    private final ApplicationProperties appProp;
    private final SSLContext sslContext;
    private final ObjectMapper objectMapper;

    public RestRequester(ApplicationProperties appProp, ObjectMapper objectMapper) {
        this.sslContext = getSSlContextTrustAll();
        this.appProp = appProp;
        this.objectMapper = objectMapper;
    }

    public void sendWarmUpARq() throws JsonProcessingException {
        String msg = objectMapper.writeValueAsString(createWarmUpRq());
        sendPostRequest(msg, appProp.getWarmupUrl() + appProp.getWarmupAPath());
    }

    public void sendWarmUpBRq() throws JsonProcessingException {
        String msg = objectMapper.writeValueAsString(createWarmUpBRq());
        sendPostRequest(msg, appProp.getWarmupUrl() + appProp.getWarmupBPath());
    }

    private SSLContext getSSlContextTrustAll() {
        try {
            return SSLContextBuilder.create()
                    .loadTrustMaterial(TrustAllStrategy.INSTANCE)
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            log.error("Error initializing SSLContext for HTTPS requests [{} || {}]", e.getMessage(), Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    private void sendPostRequest(String jsonMsg, String url) {
        try (CloseableHttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).build()) {

            HttpPost postRequest = new HttpPost(url);
            log.info("Send warmup request to: {}", postRequest.getURI());
            postRequest.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            postRequest.setEntity(new StringEntity(jsonMsg));

            try (CloseableHttpResponse response = httpClient.execute(postRequest)) {
                log.info("Response status code: {}", response.getStatusLine().getStatusCode());
            }

        } catch (Exception e) {
            log.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()) + "]");
        }
    }

    private WarmUpRq createWarmUpRq() {
        UUID requestUUID = Generators.timeBasedEpochGenerator().generate();
        return WarmUpRq.builder()
                .profileStatus("ALL")
                .clientType("A")
                .ClientInfo1Type("ALL")
                .requestId(requestUUID.toString())
                .value2s(List.of())
                .build();
    }

    private WarmUpBRq createWarmUpBRq() {
        UUID requestUUID = Generators.timeBasedEpochGenerator().generate();
        return WarmUpBRq.builder()
                .profileStatus("ALL")
                .requestId(requestUUID.toString())
                .value2s(List.of())
                .build();
    }
}
