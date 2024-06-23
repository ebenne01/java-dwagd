# java-dwagd

Java Spring Boot implementation of [Day Of The Week For Any Given Date](https://github.com/ebenne01/dwagd). This version of dwagd is implemented as a web service with the following endpoints:

- /api/v1/dayofweek/{date}

  Returns the day of the week for the specified date. The date must be in ISO-8601 format (yyyy-mm-dd)

Doesn't work for days prior to 1753. The algorithm is based on a procedure defined in **The Old Farmer's Almanac** ("How to Find the Day of the Week for Any Given Date" 261).

## Works Cited

"How to Find the Day of the Week for Any Given Date." The Old Farmer's Almanac. 2019 ed. Print.
