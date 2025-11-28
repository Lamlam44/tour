package com.project.tour.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private UserInfoDTO user;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class UserInfoDTO {
        private String id;
        private String username;
        private String role;
    }
}
