package com.tracking.api.model;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class TrackingNumberResponse {
    private String trackingNumber;
    private OffsetDateTime createdAt;
    private String originCountryId;
    private String destinationCountryId;
}
