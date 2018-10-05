package com.firstgenix.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="There is already a role having with the name provided") // 400

public class RoleExceptionHandler extends Exception {
}
