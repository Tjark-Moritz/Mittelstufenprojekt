package de.szut.shift_backend.templates;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.model.dto.UpdatePasswordDto;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.JsonWebToken;
import org.keycloak.representations.account.UserRepresentation;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@SuppressWarnings("rawtypes")
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

            MultiValueMap<String, String> urlData= new LinkedMultiValueMap<>();
            urlData.add("grant_type","client_credentials");
            urlData.add("client_id","admin-cli");
            urlData.add("client_secret","e933cafc-ecd1-4786-bd77-c30bd781ace1");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(urlData, headers);

            ResponseEntity<Map<String,Object>> response = this.restTemplate.exchange(
                    tokenUrl,
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<>() {},
                    1);

            if (response.getStatusCode() == HttpStatus.OK
                && Objects.requireNonNull(response.getBody()).containsKey("access_token"))
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

        ArrayNode arrNode = mapper.createArrayNode();
        ObjectNode cred = mapper.createObjectNode();
        cred.put("type","password");
        cred.put("value", "shift123");
        cred.put("temporary", false);

        ObjectNode body = mapper.createObjectNode();
        body.put("username",emp.getUsername());
        body.put("credentials", arrNode.add(cred));
        body.put("firstName",emp.getFirstName());
        body.put("lastName",emp.getLastName());
        body.put("email", emp.getEmail());
        body.put("enabled",true);

        HttpEntity request = new HttpEntity(body.toPrettyString(),header);

        ResponseEntity<Map<String,Object>> response = this.restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {},
                1);

        return response.getStatusCodeValue() == 201;
    }

    public void updateUserPassword(String subjectId, UpdatePasswordDto pwUpdate) {
        String url =  baseUrl + "/users/" + subjectId + "/reset-password";

        HttpHeaders header = getHttpBaseHeader(getAdminKey());

        ObjectNode body = mapper.createObjectNode();
        body.put("type","password");
        body.put("value", pwUpdate.getNewPassword());
        body.put("temporary", false);

        HttpEntity request = new HttpEntity(body.toPrettyString(),header);

        ResponseEntity<Map<String,Object>> response = this.restTemplate.exchange(
                url,
                HttpMethod.PUT,
                request,
                new ParameterizedTypeReference<>() {},
                1);
    }

    public String getSubjectIdByUsername(String username){
        String url =  baseUrl + "/users";

        HttpHeaders header = getHttpBaseHeader(getAdminKey());

        ObjectNode body = mapper.createObjectNode();
        body.put("username", username);

        HttpEntity request = new HttpEntity(body.toPrettyString(),header);

        ResponseEntity<List<UserRepresentation>> response = this.restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {},
                1);
        Optional<UserRepresentation> keyUser =  Objects.requireNonNull(response.getBody()).stream()
                                                .filter(user -> user.getUsername().equals(username))
                                                .findAny();


        return keyUser.map(UserRepresentation::getId).orElse(null);
    }
}
