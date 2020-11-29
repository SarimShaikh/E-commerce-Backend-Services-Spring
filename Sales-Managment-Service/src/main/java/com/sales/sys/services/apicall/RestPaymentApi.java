package com.sales.sys.services.apicall;

import com.sales.sys.messageDTO.PaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class RestPaymentApi {

    private RestTemplate restTemplate;
    final String baseUrl = "http://localhost:8084/api/v1/payment/create-payment";
    public static String accessToken = "";

    @Autowired
    public RestPaymentApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean initiatePayment(PaymentDTO paymentDTO) throws URISyntaxException {
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<PaymentDTO> request = new HttpEntity<>(paymentDTO, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        boolean isStatucCheck = result.getStatusCodeValue()==200 ? true : false;
        return isStatucCheck;
    }

}
