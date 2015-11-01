package com.affecto.jsonstat.elements;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * Represents an JSON-Stat "updated" element, which can be either a LocalDate, LocalDateTime, or ZonedDateTime.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class UpdatedElement {

    public LocalDate localDate;

    public LocalDateTime localDateTime;

    public ZonedDateTime zonedDateTime;

    public boolean isLocalDate() {
        return localDate != null;
    }

    public boolean isLocalDateTime() {
        return localDateTime != null;
    }

    public boolean isZonedDateTime() {
        return zonedDateTime != null;
    }

    public UpdatedElement setLocalDate(final LocalDate localDate) {
        this.localDate = localDate;
        this.localDateTime = null;
        this.zonedDateTime = null;
        return this;
    }

    public UpdatedElement setLocalDateTime(final LocalDateTime localDateTime) {
        this.localDate = null;
        this.localDateTime = localDateTime;
        this.zonedDateTime = null;
        return this;
    }

    public UpdatedElement setZonedDateTime(final ZonedDateTime zonedDateTime) {
        this.localDate = null;
        this.localDateTime = null;
        this.zonedDateTime = zonedDateTime;
        return this;
    }

}
