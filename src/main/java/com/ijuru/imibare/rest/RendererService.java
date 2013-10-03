/**
 * Copyright 2011 Rowan Seymour
 *
 * This file is part of Imibare.
 *
 * Imibare is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Imibare is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Imibare. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ijuru.imibare.rest;

import com.ijuru.imibare.NumberRendererFactory;
import com.ijuru.imibare.UnsupportedLanguageException;
import com.ijuru.imibare.lang.NounClassification;
import com.ijuru.imibare.renderer.NumberRenderer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.Locale;

@Path("/renderers")
public class RendererService {

	/**
	 * Gets all available renderers
	 * @return the renderers
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<NumberRenderer> getAllRenderers() {
		return NumberRendererFactory.getAllRenderers();
	}

	/**
	 * Gets the renderer for the given locale
	 * @param locale the locale
	 * @return the renderer
	 * @throws UnsupportedLanguageException if no render is registered for the locale
	 */
	@GET
	@Path("/{locale}")
	@Produces(MediaType.APPLICATION_JSON)
	public NumberRenderer getRenderer(@PathParam("locale") Locale locale) throws UnsupportedLanguageException {
		return NumberRendererFactory.getRendererByLocale(locale);
	}

	/**
	 * Gets the rendered number for the renderer with the given locale
	 * @param locale the locale
	 * @param number the number
	 * @return the rendered number
	 * @throws UnsupportedLanguageException if no render is registered for the locale
	 */
	@GET
	@Path("/{locale}/{number}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getRenderer(@PathParam("locale") Locale locale, @PathParam("number") Long number) throws UnsupportedLanguageException {
		NumberRenderer renderer = NumberRendererFactory.getRendererByLocale(locale);
		return renderer.render(number, null);
	}

	/**
	 * Gets the rendered number for the renderer with the given locale
	 * @param locale the locale
	 * @param number the number
	 * @param classification the noun classification
	 * @return the rendered number
	 * @throws UnsupportedLanguageException if no render is registered for the locale
	 */
	@GET
	@Path("/{locale}/{number}/{classification}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getRenderer(@PathParam("locale") Locale locale, @PathParam("number") Long number, @PathParam("classification") String classification) throws UnsupportedLanguageException {
		NumberRenderer renderer = NumberRendererFactory.getRendererByLocale(locale);
		return renderer.render(number, NounClassification.parse(classification));
	}
}