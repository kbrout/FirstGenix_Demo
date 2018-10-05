package com.firstgenix.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No User availabe in this ID")  // 404

public class ExceptionHandler_update extends Exception{

}
