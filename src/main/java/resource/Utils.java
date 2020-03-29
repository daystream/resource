package resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class Utils {
    public static String requestTokenText(String code){
        RestTemplate restTemplate = new RestTemplate();
        String credentials = "clientapp:123";
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Basic " + encodedCredentials);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        String access_token_url = "http://localhost:8080/oauth/token";
        access_token_url += "?client_id=clientapp";
        access_token_url += "&code=" + code;
        access_token_url += "&grant_type=authorization_code";
        ResponseEntity<String> response = restTemplate.exchange(access_token_url, HttpMethod.POST, request, String.class);
        return response.getBody();
    }
    public static String getAccessToken(String tokenText){
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(tokenText);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String token = node.path("access_token").asText();
        return token;
    }
}
