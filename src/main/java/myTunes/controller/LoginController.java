package myTunes.controller;

import lombok.RequiredArgsConstructor;
import myTunes.business.login.LoginUseCase;
import myTunes.domain.login.LoginRequest;
import myTunes.domain.login.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class LoginController {
    private final LoginUseCase loginUseCase;
    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request){
        LoginResponse loginResponse = loginUseCase.login(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponse);
    }
}
