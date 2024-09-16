package spring.boot.fainalproject.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.boot.fainalproject.API.ApiResponse;
import spring.boot.fainalproject.DTO.FacilityDTO;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Service.FacilityService;

@RestController
@RequestMapping("/api/v1/facility")
@RequiredArgsConstructor
public class FacilityController {
    private final FacilityService facilityService;

    @GetMapping("/get-all-facility")
    public ResponseEntity getAllFacility(){
        return ResponseEntity.status(200).body(facilityService.getAllFacilities());
    }
    @PostMapping("/registerFacility")
    public ResponseEntity registerFacility(@Valid @RequestBody FacilityDTO facilityDTO) {
        facilityService.registerFacility(facilityDTO);
        return ResponseEntity.status(200).body(new ApiResponse( "Facility registered successfully"));
    }

    @PutMapping("/updateFacility")
    public ResponseEntity updateFacility(@AuthenticationPrincipal User user, @Valid @RequestBody FacilityDTO facilityDTO) {
        facilityService.updateFacility(user.getId(), facilityDTO);
        return ResponseEntity.status(200).body(new ApiResponse( "Facility updated successfully"));

    }
    @DeleteMapping("/deleteFacility")
    public ResponseEntity deleteFacility(@AuthenticationPrincipal User user) {
        facilityService.deleteFacility(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse( "Customer deleted successfully"));
    }
}
