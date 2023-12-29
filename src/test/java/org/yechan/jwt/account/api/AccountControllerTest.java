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
import org.yechan.jwt.account.dto.AccountDto;
import org.yechan.jwt.account.entity.Account;
import org.yechan.jwt.account.service.AccountService;
import org.yechan.jwt.account.service.TokenProvider;
import org.yechan.jwt.global.config.SecurityConfig;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        LocalDateTime now = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
        AccountDto accountDto = AccountDto.builder()
                .phone("010-8744-5809")
                .username("test")
                .createdDateTime(now)
                .modifiedDateTime(now)
                .password("qwe123").build();
        
        Account expectedAccount = Account.builder()
                .username("test")
                .password("qwe123")
                .phone("010-8744-5809")
                .createdDateTime(now)
                .modifiedDateTime(now)
                .build();
        
        given(accountService.signup(any(AccountDto.class))).willReturn(expectedAccount);
        
        String reqBody = objectMapper.writeValueAsString(accountDto);
        
        mockMvc.perform(post("/account/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqBody))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedAccount)))
                .andDo(log());
    }
}