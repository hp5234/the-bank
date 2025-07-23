package com.jeon.bank.service;

import com.jeon.bank.config.dumy.DumyObject;
import com.jeon.bank.domain.user.UserRepository;
import com.jeon.bank.dto.user.UserReqDto;
import com.jeon.bank.dto.user.UserRespDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserServiceTest extends DumyObject {

    @InjectMocks // 테스트 대상 클래스
    private UserService userService;

    @Mock // 의존 객체를 Mock 객체로 생성
    private UserRepository userRepository;

    @Spy // 의존 객체를 진짜 객체로 반환하되 특정 메서드만 mocking 이 가능한 spy 객체로 생성 (사이드 이펙트 주의)
    private BCryptPasswordEncoder passwordEncoder;
    
    @Test
    public void join_test() throws Exception {
        // given 
        UserReqDto.JoinReqDto joinReqDto = new UserReqDto.JoinReqDto();
        joinReqDto.setUsername("jack");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("jack@gmail.com");
        joinReqDto.setFullname("Jack Sparrow 잭 스페로우");

        Mockito // stub 1
                .when(userRepository.findByUsername(Mockito.any()))
                .thenReturn(Optional.empty());

        Mockito // stub 2
                .when(userRepository.save(Mockito.any()))
                .thenReturn(
                    newMockUser(1L, "jack", "Jack Sparrow 잭 스페로우")
                );

        // when
        UserRespDto.JoinRespDto joinRespDto = userService.join(joinReqDto);
        System.out.println("테스트 : joinRespDto = " + joinRespDto);

        // then 
        assertThat(joinRespDto.getId()).isEqualTo(1L);
        assertThat(joinRespDto.getUsername()).isEqualTo("jack");
    }
    

}