/*
 * Copyright (c) 2024. Edward Bennett.  All rights reserved.
 * Use of this source code is governed by an Apache 2.0 style license
 * that can be found in the LICENSE file.
 */

package com.tzuware.dwagd;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.AbstractMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/dayofweek")
public class Application {

  private static final Map<Integer, DayOfWeek> DAYS_OF_WEEK = Map.ofEntries(
      new AbstractMap.SimpleEntry<>(0, DayOfWeek.SATURDAY),
      new AbstractMap.SimpleEntry<>(1, DayOfWeek.SUNDAY),
      new AbstractMap.SimpleEntry<>(2, DayOfWeek.MONDAY),
      new AbstractMap.SimpleEntry<>(3, DayOfWeek.TUESDAY),
      new AbstractMap.SimpleEntry<>(4, DayOfWeek.WEDNESDAY),
      new AbstractMap.SimpleEntry<>(5, DayOfWeek.THURSDAY),
      new AbstractMap.SimpleEntry<>(6, DayOfWeek.FRIDAY)
  );

  private static final Map<Integer, Month> MONTHS = Map.ofEntries(
      new AbstractMap.SimpleEntry<>(1, new Month(java.time.Month.JANUARY, 1, -1)),
      new AbstractMap.SimpleEntry<>(2, new Month(java.time.Month.FEBRUARY, 4, -1)),
      new AbstractMap.SimpleEntry<>(3, new Month(java.time.Month.MARCH, 4, 0)),
      new AbstractMap.SimpleEntry<>(4, new Month(java.time.Month.APRIL, 0, 0)),
      new AbstractMap.SimpleEntry<>(5, new Month(java.time.Month.MAY, 2, 0)),
      new AbstractMap.SimpleEntry<>(6, new Month(java.time.Month.JUNE, 5, 0)),
      new AbstractMap.SimpleEntry<>(7, new Month(java.time.Month.JULY, 0, 0)),
      new AbstractMap.SimpleEntry<>(8, new Month(java.time.Month.AUGUST, 3, 0)),
      new AbstractMap.SimpleEntry<>(9, new Month(java.time.Month.SEPTEMBER, 6, 0)),
      new AbstractMap.SimpleEntry<>(10, new Month(java.time.Month.OCTOBER, 1, 0)),
      new AbstractMap.SimpleEntry<>(11, new Month(java.time.Month.NOVEMBER, 4, 0)),
      new AbstractMap.SimpleEntry<>(12, new Month(java.time.Month.DECEMBER, 6, 0))
  );

  private static String calculateDayOfWeek(LocalDate date) throws Exception {
    int yearVal = date.getYear();
    int twoDigitYear = yearVal % 100;
    int monthVal = date.getMonthValue();
    int dayVal = date.getDayOfMonth();
    Month month = MONTHS.get(monthVal);

    if (yearVal < 1753) {
      throw new Exception("Year must be greater than 1752");
    }

    int sum = 0;

    sum += twoDigitYear;
    sum += twoDigitYear / 4;
    sum += dayVal;
    sum += month.keyNum();

    if (date.isLeapYear()) {
      sum += month.leapYearAdj();
    }

    if (yearVal < 1800) {
      sum += 4;
    } else if (yearVal < 1900) {
      sum += 2;
    } else if (yearVal > 1999) {
      sum -= 1;
    }

    int dayNum = sum % 7;

    return DAYS_OF_WEEK.get(dayNum).getDisplayName(TextStyle.FULL, Locale.getDefault());
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @GetMapping("/{date}")
  ResponseEntity getDayOfWeek(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
    try {
      return ResponseEntity.ok(calculateDayOfWeek(date));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}

record Month (java.time.Month month, int keyNum, int leapYearAdj) { }
