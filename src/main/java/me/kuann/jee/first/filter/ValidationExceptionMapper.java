package me.kuann.jee.first.filter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
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
		UUID uuid = UUID.randomUUID();

		map.put("Time", new SimpleDateFormat("dd.MM.yyyy H:m:s", Locale.ENGLISH).format(new Date()));
		map.put("Detail ", "UUID: " + uuid + " " + exception.getMessage());

		Logger.getLogger(ValidationExceptionMapper.class.getName()).log(Level.SEVERE, "UUID: " + uuid + exception.getMessage(), exception);

		return Response.serverError().type(MediaType.APPLICATION_JSON).entity(map).build();
	}
}
