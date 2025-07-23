package com.jeon.bank.service;

import com.jeon.bank.domain.user.User;
import com.jeon.bank.domain.user.UserRepository;
import com.jeon.bank.dto.user.UserReqDto;
import com.jeon.bank.dto.user.UserRespDto;
import com.jeon.bank.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserRespDto.JoinRespDto join(UserReqDto.JoinReqDto joinReqDto) {
        userRepository
                .findByUsername(joinReqDto.getUsername())
                .ifPresent(user -> {
                    throw new CustomApiException("동일한 username 이 존재합니다.");
                });
        User userPS = userRepository.save(joinReqDto.toEntity(passwordEncoder));
        return new UserRespDto.JoinRespDto(userPS);
    }
}
