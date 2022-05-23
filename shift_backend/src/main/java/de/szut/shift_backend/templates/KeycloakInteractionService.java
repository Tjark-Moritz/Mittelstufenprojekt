package de.szut.shift_backend.templates;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.szut.shift_backend.model.Employee;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class KeycloakInteractionService {

    private String adminToken;
    private final String tokenUrl = "http://localhost:8089/auth/realms/Shiftplanner/protocol/openid-connect/token";
    private final String baseUrl = "http://localhost:8089/auth/admin/realms/Shiftplanner";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public KeycloakInteractionService(){
        adminToken = null;
    }

    private HttpHeaders getHttpBaseHeader(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return headers;
    }

    private String getAdminKey(){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_FORM_URLENCODED));

            MultiValueMap<String, String> urlData= new LinkedMultiValueMap<String, String>();
            urlData.add("grant_type","client_credentials");
            urlData.add("client_id","admin-cli");
            urlData.add("client_secret","6945a851-35e0-4c5d-9cb7-72eb7312b638");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(urlData, headers);

            ResponseEntity<Map<String,Object>> response = this.restTemplate.exchange(
                    tokenUrl,
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<Map<String,Object>>() {},
                    1);

            if (response.getStatusCode() == HttpStatus.OK
                && response.getBody().containsKey("access_token"))
                return response.getBody().get("access_token").toString();
        }
        catch (Exception e) {
            System.out.println(e);
        }

        throw new NullPointerException("Could not retrieve access_token!");
    }

    public boolean addUserToKeycloak(Employee emp){
        String url = baseUrl + "/users";
        adminToken = getAdminKey();

        HttpHeaders header = getHttpBaseHeader(adminToken);

        ObjectNode body = mapper.createObjectNode();
        body.put("username",emp.getUsername());
        body.put("firstName",emp.getFirstName());
        body.put("lastName",emp.getLastName());
        body.put("email", emp.getEmail());
        body.put("enabled",true);

        HttpEntity request = new HttpEntity(body.toPrettyString(),header);

        ResponseEntity<Map<String,Object>> response = this.restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<Map<String,Object>>() {},
                1);

        return response.getStatusCodeValue() == 201;
    }
}
