package org.yechan.jwt.account.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.yechan.jwt.account.common.JwtFilter;
import org.yechan.jwt.account.dto.request.SignupRequest;
import org.yechan.jwt.account.dto.response.SignupResponse;
import org.yechan.jwt.account.service.AccountService;
import org.yechan.jwt.global.config.SecurityConfig;

import java.time.LocalDateTime;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@Import({SecurityConfig.class})
@WebMvcTest(controllers=AccountController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc(addFilters = false)
class AccountControllerTest {
    @Autowired
    MockMvc mockMvc;
    
    @MockBean
    AccountService accountService;
    
    @MockBean
    JwtFilter jwtFilter;
    
    @Test
    @DisplayName("회원 생성 - 201")
    void signup() throws Exception {
        SignupRequest signupRequest = SignupRequest.builder()
                .username("test")
                .password("qwe")
                .phone("000-0000-0000")
                .build();
        
        LocalDateTime now = LocalDateTime.now();
        SignupResponse signupResponse = SignupResponse.builder()
                .authorities(Set.of("USER"))
                .username("test")
                .createdDate(now)
                .modifiedDate(now)
                .build();
        
        when(accountService.signup(any())).thenReturn(signupResponse);
        
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String req = objectMapper.writeValueAsString(signupRequest);
        
        mockMvc.perform(
                        post("/account/signup")
                                .content(req)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaTypes.HAL_JSON)
                )
                .andDo(log())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$._links.self").exists());
        
    }
}