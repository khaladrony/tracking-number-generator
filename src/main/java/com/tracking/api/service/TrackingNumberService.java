package com.tracking.api.service;


import com.tracking.api.model.TrackingNumberRequest;
import com.tracking.api.model.TrackingNumberResponse;
import com.tracking.api.util.TrackingNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class TrackingNumberService {

    private final TrackingNumberGenerator trackingNumberGenerator;
    private final RedisTemplate<String, String> redisTemplate;
    private static final String TRACKING_NUMBER_KEY = "tracking:number:";

    public TrackingNumberResponse generateTrackingNumber(TrackingNumberRequest request) {
        String trackingNumber;
        boolean isUnique = false;

        do {
            trackingNumber = trackingNumberGenerator.generate(request);
            isUnique = redisTemplate.opsForValue().setIfAbsent(TRACKING_NUMBER_KEY + trackingNumber, "1");
        } while (!isUnique);

        return TrackingNumberResponse.builder()
                .trackingNumber(trackingNumber)
                .createdAt(OffsetDateTime.now())
                .originCountryId(request.getOriginCountryId())
                .destinationCountryId(request.getDestinationCountryId())
                .build();
    }
}
