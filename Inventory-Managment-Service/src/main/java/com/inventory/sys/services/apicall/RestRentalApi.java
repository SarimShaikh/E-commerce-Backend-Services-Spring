package com.inventory.sys.services.apicall;

import com.inventory.sys.messageDTO.RentalItemsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class RestRentalApi {

    private RestTemplate restTemplate;
    final String baseUrl = "http://localhost:8083/api/v1/sales/rental-items";
    public static String accessToken = "";

    @Autowired
    public RestRentalApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<List<RentalItemsDTO>> getAllRentalItems() throws URISyntaxException {
        URI uri = new URI(baseUrl);
        HttpEntity<String> request = new HttpEntity<>(getHeader());
        ResponseEntity<List<RentalItemsDTO>> result = restTemplate.exchange(uri, HttpMethod.GET, request, new ParameterizedTypeReference<List<RentalItemsDTO>>() {
        });

        return result;
    }

    private HttpHeaders getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);
        return headers;
    }
}
