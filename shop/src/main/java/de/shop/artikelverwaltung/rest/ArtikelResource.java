package de.shop.artikelverwaltung.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import java.net.URI;
import java.util.Collection;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.util.LocaleHelper;
import de.shop.util.Mock;
import de.shop.util.NotFoundException;

@Path("/artikel")
@Produces(APPLICATION_JSON)
@Consumes
@RequestScoped
public class ArtikelResource {
	@Context
	private UriInfo uriInfo;
	
	@Context
	private HttpHeaders headers;
	
	@Inject
	private UriHelperArtikel uriHelperArtikel;
	
	@Inject
	private LocaleHelper localeHelper;
	
	@GET
	@Produces(TEXT_PLAIN)
	@Path("version")
	public String getVersion() {
		return "1.0";
	}
	
	@GET
	@Path("{id:[1-9][0-9]*}")
	public Artikel findArtikelById(@PathParam("id") Long id) {
		@SuppressWarnings("unused")
		final Locale locale = localeHelper.getLocale(headers);
		
		// TODO Anwendungskern statt Mock, Verwendung von Locale
		final Artikel artikel = Mock.findArtikelById(id);
		if (artikel == null) {
			throw new NotFoundException("Kein Artikel mit der ID " + id + " gefunden.");
		}
		// URLs innerhalb des gefundenen Artikel anpassen
		uriHelperArtikel.updateUriArtikel(artikel, uriInfo);
		
		return artikel;
	}
	
	@GET
	public Collection<Artikel> findArtikelByName(@QueryParam("Name") @DefaultValue("") String name) {
		@SuppressWarnings("unused")
		final Locale locale = localeHelper.getLocale(headers);
		
		Collection<Artikel> artikel = null;
		if ("".equals(name)) {
			// TODO Anwendungskern statt Mock, Verwendung von Locale
			artikel = Mock.findAllArtikel();
			if (artikel.isEmpty()) {
				throw new NotFoundException("Keine Artikel vorhanden.");
			}
		}
		else {
			// TODO Anwendungskern statt Mock, Verwendung von Locale
			artikel = Mock.findArtikelByName(name);
			if (artikel.isEmpty()) {
				throw new NotFoundException("Kein Artikel mit dem Name " + name + " gefunden.");
			}
		}
		
		//for (Artikel artikel : artikel) {
		//	uriHelperArtikel.updateUriArtikel(artikel, uriInfo);
		//}
		
		return artikel;
	}
		
	@POST
	@Consumes(APPLICATION_JSON)
	@Produces
	public Response createArtikel(Artikel artikel) {
		@SuppressWarnings("unused")
		final Locale locale = localeHelper.getLocale(headers);
		
		// TODO Anwendungskern statt Mock, Verwendung von Locale
		artikel = Mock.createArtikel(artikel);
		final URI artikelUri = uriHelperArtikel.getUriArtikel(artikel, uriInfo);
		return Response.created(artikelUri).build();
	}
	
	@PUT
	@Consumes(APPLICATION_JSON)
	@Produces
	public Response updateArtikel(Artikel artikel) {
		@SuppressWarnings("unused")
		final Locale locale = localeHelper.getLocale(headers);
		
		// TODO Anwendungskern statt Mock, Verwendung von Locale
		Mock.updateArtikel(artikel);
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("{id:[1-9][0-9]*}")
	@Produces
	public Response deleteArtikel(@PathParam("id") Long artikelId) {
		@SuppressWarnings("unused")
		final Locale locale = localeHelper.getLocale(headers);
		
		// TODO Anwendungskern statt Mock, Verwendung von Locale
		Mock.deleteArtikel(artikelId);
		return Response.noContent().build();
	}
}
