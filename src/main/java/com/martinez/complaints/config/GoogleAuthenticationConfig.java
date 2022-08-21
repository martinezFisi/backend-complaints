package com.martinez.complaints.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Configuration
public class GoogleAuthenticationConfig {

    @Value("#{environment['google.client.id']}")
    private String clientId;

    @Bean
    public GoogleIdTokenVerifier googleIdTokenVerifier(HttpServletRequest httpServletRequest) {
        return new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(clientId))
                .build();
    }

}
