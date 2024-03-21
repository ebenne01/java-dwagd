/*
 * Copyright (c) 2024. Edward Bennett.  All rights reserved.
 * Use of this source code is governed by an Apache 2.0 style license
 * that can be found in the LICENSE file.
 */

package com.tzuware.dwagd;

import java.util.AbstractMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ApplicationTests {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private static final Map<String, String> VALID_DATES = Map.ofEntries(
			new AbstractMap.SimpleEntry<>("1804-12-23", "Sunday"),
			new AbstractMap.SimpleEntry<>("1753-01-01", "Monday"),
			new AbstractMap.SimpleEntry<>("2019-10-29", "Tuesday"),
			new AbstractMap.SimpleEntry<>("1937-08-18", "Wednesday"),
			new AbstractMap.SimpleEntry<>("2037-04-02", "Thursday"),
			new AbstractMap.SimpleEntry<>("1949-10-21", "Friday"),
			new AbstractMap.SimpleEntry<>("1992-06-20", "Saturday")
	);

	@Test
	void contextLoads() {
	}

	@Test
	void testValidDates() {
		VALID_DATES.forEach((k, v) -> Assertions.assertEquals(v, restTemplate
        .getForObject(("http://localhost:" + port + "/dayofweek/" + k), String.class)));
	}
}
