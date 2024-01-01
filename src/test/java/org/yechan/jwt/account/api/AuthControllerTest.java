package org.yechan.jwt.account.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.yechan.jwt.account.config.JwtFilter;
import org.yechan.jwt.account.dto.AuthenticationResponse;
import org.yechan.jwt.account.dto.LoginFormRequest;
import org.yechan.jwt.account.entity.Account;
import org.yechan.jwt.account.repository.AccountRepository;
import org.yechan.jwt.account.service.AuthService;
import org.yechan.jwt.account.service.TokenProvider;
import org.yechan.jwt.global.config.SecurityConfig;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class AuthControllerTest {
    MockMvc mockMvc;
    
    ObjectMapper objectMapper = new ObjectMapper();
    
    @Autowired
    AccountRepository accountRepository;
    
    @MockBean
    AuthService authService;
    
    @MockBean
    TokenProvider tokenProvider;
    @Autowired
    JwtFilter jwtFilter;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    Account expectedAccount;
    String password="qwe123";
    
    @Autowired
    WebApplicationContext context;
    
    @BeforeEach
    void setUp() {
        
        mockMvc= MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(jwtFilter)
                .build();
        
        
        expectedAccount = Account.builder()
                .username("test")
                .password(passwordEncoder.encode(password))
                .phone("010-8744-5809")
                .build();
        accountRepository.save(expectedAccount);
    }
    
    @Test
    void login() throws Exception {
        LoginFormRequest requestDto = LoginFormRequest.builder()
                .username(expectedAccount.getUsername())
                .password(password)
                .build();
        
        given(authService.login(requestDto)).willReturn(new AuthenticationResponse("Bearer","access","refresh"));
        
        String reqBody = objectMapper.writeValueAsString(requestDto);
        
        
        
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqBody))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(AuthenticationResponse.builder()
                        .grantType("Bearer")
                        .accessToken("access")
                        .refreshToken("refresh")
                        .build())))
                .andDo(log());
    }
}