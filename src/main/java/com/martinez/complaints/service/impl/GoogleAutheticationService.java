package com.martinez.complaints.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.martinez.complaints.dto.CitizenDto;
import com.martinez.complaints.exception.GoogleSignInException;
import com.martinez.complaints.service.AuthenticationService;
import com.martinez.complaints.service.CitizenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Slf4j
@Service
public class GoogleAutheticationService implements AuthenticationService {

    private final CitizenService citizenService;
    private final GoogleIdTokenVerifier googleIdTokenVerifier;

    public GoogleAutheticationService(CitizenService citizenService, GoogleIdTokenVerifier googleIdTokenVerifier) {
        this.citizenService = citizenService;
        this.googleIdTokenVerifier = googleIdTokenVerifier;
    }

    @Override
    public long authenticate(String jwt) {
        var payload = getGoogleIdToken(jwt).getPayload();
        var email = payload.getEmail();

        var citizenId = citizenService.getCitizenIdByEmail(email);// 0 if citizen hasn't been registered
        if ( citizenId != 0) {
            return citizenId;
        }

        var citizenDto = CitizenDto.builder()
                .email(payload.getEmail())
                .firstName((String) payload.get("given_name"))
                .lastName((String) payload.get("family_name"))
                .build();

        return citizenService.create(citizenDto);
    }

    private GoogleIdToken getGoogleIdToken(String jwt) {
        try {
            var googleIdToken = googleIdTokenVerifier.verify(jwt);
            if (googleIdToken == null)
                throw new GoogleSignInException("Error has ocurred during google authentication");

            return googleIdToken;
        } catch (GeneralSecurityException | IOException e) {
            throw new GoogleSignInException("Error has ocurred during google authentication", e);
        }
    }

}
