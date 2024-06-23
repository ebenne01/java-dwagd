/*
 * Copyright (c) 2024. Edward Bennett.  All rights reserved.
 * Use of this source code is governed by an Apache 2.0 style license
 * that can be found in the LICENSE file.
 */

package com.tzuware.dwagd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ApplicationTests {
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
	void testValidDates(@Autowired WebTestClient client) {
		VALID_DATES.forEach((k, v) -> {
      assertEquals(v,
					Objects.requireNonNull(client.get()
							.uri("/api/v1/dayofweek/" + k)
              .exchange()
              .expectStatus().isOk()
              .expectBody(String.class)
              .returnResult()
              .getResponseBody()));
		});
	}

	@Test
	void testBadDate(@Autowired WebTestClient client) {
		client.get()
				.uri("/api/v1/dayofweek/1752-12-31")
				.exchange()
				.expectStatus().isBadRequest();
	}
}
