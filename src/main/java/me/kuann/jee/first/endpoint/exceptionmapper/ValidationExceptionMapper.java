package me.kuann.jee.first.endpoint.exceptionmapper;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
		Map<String, Object> map = new HashMap<>();
		map.put("Time", LocalDateTime.now().toString());
		map.put("Detai", exception.getMessage());

		Logger.getLogger(ValidationExceptionMapper.class.getName()).log(Level.SEVERE, exception.getMessage(), exception);

		return Response.serverError().type(MediaType.APPLICATION_JSON).entity(map).build();
	}
}
