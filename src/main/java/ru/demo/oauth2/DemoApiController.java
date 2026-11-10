package ru.demo.oauth2;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DemoApiController {
    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of(
                "roles", "One Spring app with Authorization Server, OAuth2 Client and JWT Resource Server",
                "loginClientFlow", "/oauth2/authorization/local",
                "resourceServerExample", "/api/user/me (Bearer JWT required)");
    }

    @GetMapping("/api/user/me")
    public Map<String, Object> protectedUserApi(@AuthenticationPrincipal Jwt jwt) {
        return Map.of("message", "JWT validated by the Resource Server filter chain",
                "subject", jwt.getSubject(), "scopes", jwt.getClaimAsStringList("scope"));
    }

    @GetMapping("/api/admin/hello")
    public Map<String, String> adminApi() {
        return Map.of("message", "Admin-only endpoint");
    }

    @GetMapping("/api/client/token-info")
    public Map<String, Object> clientTokenInfo(
            @RegisteredOAuth2AuthorizedClient("local") OAuth2AuthorizedClient authorizedClient) {
        // Never expose token value in a real API response.
        var token = authorizedClient.getAccessToken();
        return Map.of("clientRole", "OAuth2 Client",
                "tokenType", token.getTokenType().getValue(),
                "expiresAt", token.getExpiresAt().toString(),
                "nextStep", "POST this access token as Authorization: Bearer <token> to /api/user/me");
    }
}
