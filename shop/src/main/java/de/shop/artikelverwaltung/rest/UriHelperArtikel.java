package de.shop.artikelverwaltung.rest;

import java.net.URI;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.artikelverwaltung.rest.ArtikelResource;



@ApplicationScoped
public class UriHelperArtikel {
	public URI getUriArtikel(Artikel artikel, UriInfo uriInfo) {
		final UriBuilder ub = uriInfo.getBaseUriBuilder()
		                             .path(ArtikelResource.class)
		                             .path(ArtikelResource.class, "findArtikelById");
		final URI artikelUri = ub.build(artikel.getId());
		return artikelUri;
	}
	
	public void updateUriArtikel(Artikel artikel, UriInfo uriInfo) {
		// URL fuer Bestellungen setzen
		final UriBuilder ub = uriInfo.getBaseUriBuilder()
                                     .path(ArtikelResource.class)
                                     .path(ArtikelResource.class, "findArtikelByArtikelId");
		final URI artikelUri = ub.build(artikel.getId());
		artikel.setBestellungenUri(artikelUri);
	}

}

	
	
