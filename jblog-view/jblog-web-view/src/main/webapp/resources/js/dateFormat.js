/*
 * Copyright (C) 2012-2013  Olexandr Tyshkovets
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
﻿function showDate() {
	var d = new Date();
	var monthName = new Array("January","February","March","April","May","June","July","August","September","October","November","December");
	var today = monthName[d.getMonth()] + " " + d.getDate() + ", " + d.getFullYear();
	return today;
}

function populateDropDownDate(dayField, monthField, yearField) {
	var monthName = new Array("January","February","March","April","May","June","July","August","September","October","November","December");

	var day = document.getElementById(dayField);
	var month = document.getElementById(monthField);
	var year = document.getElementById(yearField);
	
	for (var i = 1; i <= 31; i++) {
		day.options[i] = new Option(i, i);
	}
	day.options[0] = new Option("Select day", 0, true, true);
	
	for (var i = 1; i <= 12; i++) {
		month.options[i] = new Option(monthName[i - 1], i);
	}
	month.options[0] = new Option("Select month", 0, true, true);

	var date = new Date();
	var currentYear = date.getFullYear();
	var minYear = currentYear - 73;
	var maxYear = currentYear - 17;
	for (var i = 0; maxYear - i > minYear; i++) {
		year.options[i] = new Option(maxYear - i, maxYear - i);
	}
	year.options[0] = new Option("Select year", 0, true, true);
}