package com.tracking.api.util;


import com.tracking.api.model.TrackingNumberRequest;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class TrackingNumberGenerator {

    private static final String ALLOWED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int MAX_LENGTH = 16;

    public String generate(TrackingNumberRequest request) {
        StringBuilder sb = new StringBuilder();

        // Add origin country code prefix (first 2 characters)
        if (request.getOriginCountryId() != null) {
            sb.append(request.getOriginCountryId());
        } else {
            // Add random characters if origin country is not provided
            sb.append(randomChar()).append(randomChar());
        }

        // Add destination country code (next 2 characters)
        if (request.getDestinationCountryId() != null) {
            sb.append(request.getDestinationCountryId());
        } else {
            // Add random characters if destination country is not provided
            sb.append(randomChar()).append(randomChar());
        }

        // Add timestamp component (next 6 characters)
        long timestamp = request.getCreatedAt() != null ?
                request.getCreatedAt().toEpochSecond() :
                System.currentTimeMillis() / 1000;
        String timestampStr = Long.toString(timestamp, 36).toUpperCase();
        sb.append(timestampStr.substring(Math.max(0, timestampStr.length() - 6)));

        // Add customer identifier (next 2 characters)
        if (request.getCustomerId() != null) {
            String customerId = request.getCustomerId().toString();
            sb.append(customerId.substring(0, 2).toUpperCase());
        } else {
            sb.append(randomChar()).append(randomChar());
        }

        // Add random characters to reach the desired length
        while (sb.length() < MAX_LENGTH) {
            sb.append(randomChar());
        }

        // Ensure the length is not more than 16 characters
        return sb.substring(0, Math.min(sb.length(), MAX_LENGTH));
    }

    private char randomChar() {
        return ALLOWED_CHARS.charAt(ThreadLocalRandom.current().nextInt(ALLOWED_CHARS.length()));
    }

}
