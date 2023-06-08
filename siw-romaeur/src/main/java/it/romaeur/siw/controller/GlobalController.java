package it.romaeur.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import it.romaeur.siw.model.Credentials;
import it.romaeur.siw.service.CredentialsService;


@ControllerAdvice
public class GlobalController {
    @Autowired
    private CredentialsService credentialsService;

    @ModelAttribute("credentials")
    public Credentials getCredentials() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return credentialsService.getCredentials(user.getUsername());
        }
        return null;
    }
}