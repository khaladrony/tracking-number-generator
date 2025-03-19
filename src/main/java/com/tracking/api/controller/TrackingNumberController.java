package com.tracking.api.controller;


import com.tracking.api.model.TrackingNumberRequest;
import com.tracking.api.model.TrackingNumberResponse;
import com.tracking.api.service.TrackingNumberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Tracking Number", description = "Tracking Number Generator API")
public class TrackingNumberController {

    private final TrackingNumberService trackingNumberService;

    @Operation(
            summary = "Generate Next Tracking Number",
            description = "Generates a unique tracking number for a parcel based on provided parameters"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully generated tracking number",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TrackingNumberResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input parameters",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @GetMapping("/next-tracking-number")
    public ResponseEntity<TrackingNumberResponse> getNextTrackingNumber(
            @Valid @ModelAttribute TrackingNumberRequest request) {
        TrackingNumberResponse response = trackingNumberService.generateTrackingNumber(request);
        return ResponseEntity.ok(response);
    }
}
