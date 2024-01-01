package org.yechan.jwt.account.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.yechan.jwt.account.dto.SignupRequest;
import org.yechan.jwt.account.dto.SignupResponse;
import org.yechan.jwt.account.entity.Account;
import org.yechan.jwt.account.service.AccountService;
import org.yechan.jwt.account.service.TokenProvider;
import org.yechan.jwt.global.config.SecurityConfig;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class AccountControllerTest {
    @Autowired
    MockMvc mockMvc;
    
    ObjectMapper objectMapper=new ObjectMapper();
    
    @MockBean
    AccountService accountService;
    
    @MockBean
    TokenProvider tokenProvider;
    
    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    
    @Test
    void signup() throws Exception {
        SignupRequest signupRequest = SignupRequest.builder()
                .phone("010-8744-5809")
                .username("test")
                .password("qwe123").build();
        
        Account expectedAccount = Account.builder()
                .username("test")
                .password("qwe123")
                .phone("010-8744-5809")
                .build();
        SignupResponse signupResponse = SignupResponse.builder()
                .username(expectedAccount.getUsername())
                .createdDate(expectedAccount.getCreatedDate())
                .modifiedDate(expectedAccount.getModifiedDate())
                .build();
        given(accountService.signup(any(SignupRequest.class))).willReturn(SignupResponse.builder()
                        .username(expectedAccount.getUsername())
                        .createdDate(expectedAccount.getCreatedDate())
                        .modifiedDate(expectedAccount.getModifiedDate())
                .build());
        
        String reqBody = objectMapper.writeValueAsString(signupRequest);
        
        mockMvc.perform(post("/account/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqBody))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(signupResponse)))
                .andDo(log());
    }
}