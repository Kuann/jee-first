package me.kuann.jee.first.lang;

import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@RequestScoped
public class ClientLocalProducer {

	@Inject
	private HttpServletRequest request;
	
	@Produces
	@ClientLocal
	public Locale getClientLocal() {
		return request.getLocale();
	}
}
