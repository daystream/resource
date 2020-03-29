package resource.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RestController
public class HelloController {

    @GetMapping("/api/hi")
    @PostAuthorize ("hasRole('ROLE_USER')")
    public String say(@RequestParam("code") String code, @RequestParam("state") String state
            , OAuth2Authentication auth, HttpServletRequest httpServletRequest) {
        String token=httpServletRequest.getHeader("Authorization");
        return String.format("Hello %s ,%s .%s", state, code,token);
    }

    @RequestMapping("/user/my")
    @PostAuthorize ("hasRole('ROLE_USER')")
    public String my(OAuth2Authentication auth) {
        return "Hello "+auth.getUserAuthentication().getPrincipal();
    }

}