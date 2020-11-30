package com.inventory.sys.services.apicall;

import com.inventory.sys.messageDTO.RentalItemsDTO;
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
public class RestRentalApi {

    private RestTemplate restTemplate;
    final String baseUrl = "http://localhost:8084/api/v1/payment/create-payment";
    public static String accessToken = "";

    @Autowired
    public RestRentalApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean initiatePayment(RentalItemsDTO rentalItemsDTO) throws URISyntaxException {
        URI uri = new URI(baseUrl);
        HttpEntity<RentalItemsDTO> request = new HttpEntity<>(rentalItemsDTO, getHeader());
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        boolean isStatucCheck = result.getStatusCodeValue() == 200 ? true : false;
        return isStatucCheck;
    }

    private HttpHeaders getHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);
        return headers;
    }
}
