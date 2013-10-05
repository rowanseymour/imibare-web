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

import com.ijuru.imibare.Context;
import com.ijuru.kwibuka.WordSequence;
import com.ijuru.kwibuka.Wordifier;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Path("/wordifiers")
public class WordifierService {

	private static int MAX_INPUT_LENGTH = 10;

	private static int MAX_RESULTS = 10;

	/**
	 * Gets all available renderers
	 * @return the renderers
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Wordifier> getAllWordifiers() {
		return Context.getAllWordifiers();
	}

	/**
	 * Gets the wordifier for the given locale
	 * @param locale the locale
	 * @return the wordifier
	 */
	@GET
	@Path("/{locale}")
	@Produces(MediaType.APPLICATION_JSON)
	public Wordifier getWordifier(@PathParam("locale") Locale locale) {
		return Context.getWordifierByLocale(locale);
	}

	/**
	 * Gets the rendered number for the renderer with the given locale. This method is synchronized as it's memory
	 * intensive
	 * @param locale the locale
	 * @param number the number
	 * @return the rendered number
	 */
	@GET
	@Path("/{locale}/{number}")
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized List<WordSequence> wordify(@PathParam("locale") Locale locale, @PathParam("number") String number) {

		if (number.length() > MAX_INPUT_LENGTH) {
			return Collections.emptyList();
		}

		Wordifier wordifier = Context.getWordifierByLocale(locale);
		List<WordSequence> sequences = wordifier.wordify(number);

		return sequences.subList(0, Math.min(sequences.size(), MAX_RESULTS));
	}
}