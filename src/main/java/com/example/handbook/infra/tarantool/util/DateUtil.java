package com.example.handbook.infra.tarantool.util;


import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;

import static java.time.temporal.ChronoField.*;


@Log4j2
@UtilityClass
public class DateUtil {

    private final ZoneId MOSCOW_ZONE_ID = ZoneId.of("Europe/Moscow");
    private final DateTimeFormatter INSTANT_FORMATTER_READER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .appendLiteral('T')
            .appendValue(HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(MINUTE_OF_HOUR, 2)
            .optionalStart()
            .appendLiteral(':')
            .appendValue(SECOND_OF_MINUTE, 2)
            .optionalStart()
            .appendFraction(NANO_OF_SECOND, 0, 6, true)
            .parseLenient()
            .appendOffsetId()
            .toFormatter();

    private final DateTimeFormatter INSTANT_FORMATTER_WRITER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .appendLiteral('T')
            .appendValue(HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(MINUTE_OF_HOUR, 2)
            .optionalStart()
            .appendLiteral(':')
            .appendValue(SECOND_OF_MINUTE, 2)
            .optionalStart()
            .appendFraction(NANO_OF_SECOND, 6, 6, true)
            .parseLenient()
            .appendOffsetId()
            .toFormatter();

    public String toString(Instant instant) {
        return instant.atZone(MOSCOW_ZONE_ID).format(INSTANT_FORMATTER_WRITER);
    }

    public Instant toInstant(String strInstant) {
        try {
            return INSTANT_FORMATTER_READER.parse(strInstant, Instant::from);
        } catch (Exception e) {
            log.error("Can't parse {} to Instant", strInstant);
            log.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
