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

        if (request.getOriginCountryId() != null) {
            sb.append(request.getOriginCountryId());
        } else {
            sb.append(randomChar()).append(randomChar());
        }

        if (request.getDestinationCountryId() != null) {
            sb.append(request.getDestinationCountryId());
        } else {
            sb.append(randomChar()).append(randomChar());
        }

        long timestamp = request.getCreatedAt() != null ?
                request.getCreatedAt().toEpochSecond() :
                System.currentTimeMillis() / 1000;
        String timestampStr = Long.toString(timestamp, 36).toUpperCase();
        sb.append(timestampStr.substring(Math.max(0, timestampStr.length() - 6)));

        if (request.getCustomerId() != null) {
            String customerId = request.getCustomerId().toString();
            sb.append(customerId.substring(0, 2).toUpperCase());
        } else {
            sb.append(randomChar()).append(randomChar());
        }

        while (sb.length() < MAX_LENGTH) {
            sb.append(randomChar());
        }

        return sb.substring(0, Math.min(sb.length(), MAX_LENGTH));
    }

    private char randomChar() {
        return ALLOWED_CHARS.charAt(ThreadLocalRandom.current().nextInt(ALLOWED_CHARS.length()));
    }

}
