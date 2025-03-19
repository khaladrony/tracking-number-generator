package com.tracking.api.model;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class TrackingNumberRequest {
    @Pattern(regexp = "^[A-Z]{2}$", message = "Origin country code must be in ISO 3166-1 alpha-2 format")
    private String originCountryId;

    @Pattern(regexp = "^[A-Z]{2}$", message = "Destination country code must be in ISO 3166-1 alpha-2 format")
    private String destinationCountryId;

    private Double weight;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime createdAt;

    private UUID customerId;

    private String customerName;

    private String customerSlug;
}
