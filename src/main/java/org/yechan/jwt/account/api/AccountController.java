package org.yechan.jwt.account.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.yechan.jwt.account.dto.request.SignupRequest;
import org.yechan.jwt.account.dto.response.AccountInformationResponse;
import org.yechan.jwt.account.dto.response.SignupResponse;
import org.yechan.jwt.account.service.AccountService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
@Tag(name = "Account", description = "회원정보에 대한 CRUD docs")
public class AccountController {
    private final AccountService accountService;
    
    @PostMapping(value = "/signup",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "회원가입",
            description = "회원 가입을 위한 form 요청입니다."
    )
    public ResponseEntity<EntityModel<SignupResponse>> signup(
            @Validated
            @RequestBody
            SignupRequest signupRequest
    ) {
        SignupResponse signup = accountService.signup(signupRequest);
        Link link = linkTo(methodOn(AccountController.class).signup(signupRequest))
                .withSelfRel();
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(EntityModel.of(signup,link));
    }
    
    @GetMapping("/info")
    @PreAuthorize("USER")
    @Operation(
            summary = "단일 회원정보 조회 - 사용자",
            description = "인증된 사용자가 본인의 회원정보를 조회 할 떄 사용되는 요청입니다."
    )
    public ResponseEntity<AccountInformationResponse> getMyInformation() {
        AccountInformationResponse myInformation = accountService.getMyInformation();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(myInformation);
    }
}