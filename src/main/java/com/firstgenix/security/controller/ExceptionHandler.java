package com.firstgenix.security.controller;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionHandler implements ExceptionMapper<RegExceptionHandler> {
	@Override
	public Response toResponse(RegExceptionHandler err) {
		return Response.status(Status.BAD_REQUEST).entity(err.getMessage()).build();

	}

}
