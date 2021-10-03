package com.Filmkritik.authservice.controller;

import java.util.Objects;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Filmkritik.authservice.dto.JwtTokenRequest;
import com.Filmkritik.authservice.dto.JwtTokenResponse;
import com.Filmkritik.authservice.dto.TokenRefreshRequest;
import com.Filmkritik.authservice.dto.UserDto;
import com.Filmkritik.authservice.entities.RefreshTokenEntity;
import com.Filmkritik.authservice.exception.AuthenticationException;
import com.Filmkritik.authservice.exception.TokenRefreshException;
import com.Filmkritik.authservice.service.AuthService;
import com.Filmkritik.authservice.service.JwtUserDetailsService;
import com.Filmkritik.authservice.utilities.JwtTokenUtil;
import com.Filmkritik.authservice.utilities.JwtUserDetails;

@RestController
@CrossOrigin
public class AuthenticationController {

	private static final Logger logger = Logger.getLogger(AuthenticationController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Value("${jwt.http.request.header}")
	private String tokenHeader;

	@Autowired
	private AuthService authService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<JwtTokenResponse> createAuthenticationToken(
			@RequestBody JwtTokenRequest authenticationRequest) throws Exception {

		logger.info("Requested to authenticate user: " + authenticationRequest.getUsername());
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
						authenticationRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		logger.info("User :" + authenticationRequest.getUsername() + " Authenticated");
		return authService.createAndPersistRefreshTokenForUser(authentication).map(RefreshTokenEntity::getToken)
				.map(refreshToken -> {
					return ResponseEntity.ok(authService.generateToken(authentication, refreshToken));
				}).orElseThrow(
						() -> new AuthException("Couldn't create refresh token for: [" + authenticationRequest + "]"));

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserDto user) throws Exception {
		logger.info("Requested to register user: " + user.getUsername());
		return ResponseEntity.ok(userDetailsService.save(user));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	public ResponseEntity<JwtTokenResponse> refreshAndGetAuthenticationToken(@RequestBody TokenRefreshRequest request) {
		logger.info(
				"Requested to refresh JWT token for User :" + jwtTokenUtil.getUsernameFromToken(request.getToken()));
		return authService.validateRefreshToken(request.getRefreshToken()).map(refreshToken -> {
			return ResponseEntity.ok(authService.refreshJwtToken(request.getToken(), request.getRefreshToken()));
		}).orElseThrow(() -> new TokenRefreshException(request.getRefreshToken(),
				"Unexpected error during token refresh. Please logout and login again."));

	}

	@ExceptionHandler({ AuthenticationException.class })
	public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}

}
