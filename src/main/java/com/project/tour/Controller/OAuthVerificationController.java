package com.project.tour.Controller;

import com.project.tour.Service.OAuthVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuthVerificationController {

    private final OAuthVerificationService oAuthVerificationService;

    @GetMapping("/verify")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void verify(@RequestParam("token") String token) {
        oAuthVerificationService.verify(token);
    }

    @GetMapping("/resend")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resend(@RequestParam("email") String email) {
        oAuthVerificationService.resend(email);
    }
}
