package com.jeon.bank.dto.user;

import com.jeon.bank.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserReqDto {

    @Getter
    @Setter
    public static class JoinReqDto {
        private String username;
        private String password;
        private String email;
        private String fullname;

        public User toEntity(BCryptPasswordEncoder passwordEncoder) {
            return User.builder()
                    .username(this.username)
                    .password(passwordEncoder.encode(this.password))
                    .email(this.email)
                    .fullname(this.fullname)
                    .build();
        }
    }

}
