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

package com.ijuru.imibare.web;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ijuru.imibare.NumberRendererFactory;
import com.ijuru.imibare.UnsupportedLanguageException;
import com.ijuru.imibare.lang.NounAttributes;
import com.ijuru.imibare.renderer.NumberRenderer;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * JSON interface
 */
public class JsonServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String number = request.getParameter("number");
		String langs = request.getParameter("langs");
		String attrs = request.getParameter("attrs");

		Map<String, Object> output = handleRequest(number, langs, attrs);

		// Write to response as JSON
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getWriter(), output);
	}

	/**
	 * Handles the request and generates the result as an map
	 * @param number the numeric value
	 * @param langs the languages
	 * @param attrs the noun attributes
	 * @return the result
	 */
	private Map<String, Object> handleRequest(String number, String langs, String attrs) {
		Map<String, Object> output = new LinkedHashMap<String, Object>();

		if (StringUtils.isEmpty(number)) {
			output.put("error", "No number specified");
			return output;
		}

		if (StringUtils.isEmpty(langs)) {
			output.put("error", "No languages specified");
			return output;
		}

		String[] langCodes = langs.split(",");

		try {
			long num = Long.parseLong(number);
			output.put("number", number);

			NounAttributes attributes = attrs != null ? NounAttributes.parse(attrs) : new NounAttributes();

			Map<String, String> renderings = new LinkedHashMap<String, String>();

			for (String langCode : langCodes) {
				NumberRenderer renderer = NumberRendererFactory.getRendererByLocale(new Locale(langCode));
				renderings.put(langCode, renderer.render(num, attributes));
			}

			output.put("renderings", renderings);
		}
		catch (NumberFormatException ex) {
			output.put("error", "Invalid value specified");
		}
		catch (UnsupportedLanguageException e) {
			output.put("error", "Invalid language specified");
		}

		return output;
	}
}