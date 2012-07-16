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
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ijuru.imibare.NumberRendererFactory;
import com.ijuru.imibare.UnsupportedLanguageException;
import com.ijuru.imibare.lang.NounAttributes;
import com.ijuru.imibare.renderer.NumberRenderer;

public class RenderServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String strVal = request.getParameter("val");
		String strLang = request.getParameter("lang");
		String strAttrs = request.getParameter("attrs");
		
		if (strVal == null || strVal.length() == 0) {
			out.write("ERROR: No value specified");
			return;
		}
		else if (strLang == null || strLang.length() == 0) {
			out.write("ERROR: No language specified");
			return;
		}
		
		try {
			long val = Long.parseLong(strVal);
			
			NumberRenderer renderer = NumberRendererFactory.getRendererByLocale(new Locale(strLang));
			NounAttributes attributes = strAttrs != null ? NounAttributes.parse(strAttrs) : new NounAttributes();
			
			out.write(renderer.render(val, attributes));
		}
		catch (NumberFormatException ex) {
			out.write("ERROR: Invalid value specified");
			return;
		} 
		catch (UnsupportedLanguageException e) {
			out.write("ERROR: Invalid language specified");
			return;
		}
	}	
}
