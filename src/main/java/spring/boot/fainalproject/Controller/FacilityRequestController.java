package spring.boot.fainalproject.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.boot.fainalproject.API.ApiResponse;
import spring.boot.fainalproject.Model.FacilityRequest;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Service.FacilityRequestService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/facility-request")
@RequiredArgsConstructor
public class FacilityRequestController {
    private final FacilityRequestService facilityRequestService;

    // GET all facility requests
    @GetMapping("/get-all")
    public ResponseEntity getAllFacilityRequests() {

        return ResponseEntity.status(200).body(facilityRequestService.getAllFacilityRequests());
    }

    // POST create a new facility request
    @PostMapping("/create")
    public ResponseEntity createFacilityRequest(@AuthenticationPrincipal User user,
                                                @Valid @RequestBody FacilityRequest facilityRequest) {

        facilityRequestService.createFacilityRequest(user.getId(), facilityRequest);
        return ResponseEntity.status(200).body(new ApiResponse("Facility Request Created Successfully"));
    }

    // PUT update an existing facility request
    @PutMapping("/update/{facilityRequestId}")
    public ResponseEntity updateFacilityRequest(@AuthenticationPrincipal User user,@PathVariable Integer facilityRequestId,
                                                @Valid @RequestBody FacilityRequest facilityRequest) {

        facilityRequestService.updateFacilityRequest(facilityRequestId, user.getId(), facilityRequest);
        return ResponseEntity.status(200).body(new ApiResponse("Facility Request Updated Successfully"));
    }

    @PutMapping("/cancel/{facilityRequestId}")
    public ResponseEntity cancelFacilityRequest(@AuthenticationPrincipal User user,
                                                @PathVariable Integer facilityRequestId) {
        facilityRequestService.cancelFacilityRequest(user.getId(),facilityRequestId);
        return ResponseEntity.status(200).body(new ApiResponse("Facility Request Canceled Successfully"));
    }
}
