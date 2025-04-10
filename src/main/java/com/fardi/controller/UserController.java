package com.fardi.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fardi.dto.AuthRequest;
import com.fardi.dto.CreateUser;
import com.fardi.entity.User;
import com.fardi.service.JwtService;
import com.fardi.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Slf4j
public class UserController {

	private final UserService userService;
	
	private final JwtService jwtService;
	
	private final AuthenticationManager authenticationManager;
	
	public UserController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
		this.userService = userService;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}
	
	@GetMapping("/welcome")
    public String welcome() {
        return "Hello World! this is FOLSDEV";
    }
	
	@PostMapping("/add-new-user")
	public User addUser(@RequestBody CreateUser reqeust) {
		return userService.createUser(reqeust);
	}
	
	@PostMapping("/generate-token")
    public String generateToken(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(request.username());
        }
        log.info("invalid username " + request.username());
        throw new UsernameNotFoundException("invalid username {} " + request.username());
    }

	@GetMapping("/user")
	public String getUserString() {
		return "Hello, I am User";
	}
	
	
	@GetMapping("/admin")
	public String getAdminString() {
		return "Hello, I am Admin";
	}
	
}
