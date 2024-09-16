package spring.boot.fainalproject.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.boot.fainalproject.API.ApiException;
import spring.boot.fainalproject.Model.Facility;
import spring.boot.fainalproject.Model.FacilityRequest;
import spring.boot.fainalproject.Repository.FacilityRepository;
import spring.boot.fainalproject.Repository.FacilityRequestRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacilityRequestService {
    private final FacilityRequestRepository facilityRequestRepository;
    private final FacilityRepository facilityRepository;

    public List<FacilityRequest> getAllFacilityRequests() {
        return facilityRequestRepository.findAll();
    }

    public void createFacilityRequest(Integer facility_id, FacilityRequest facilityRequest) {
        Facility facility = facilityRepository.findFacilityById(facility_id);
        if (facility == null) {
            throw new ApiException("Facility Request not found with Facility id: " + facility_id);
        }
        facilityRequest.setFacility(facility);
        facilityRequest.setStatus("PENDING");

        facilityRequestRepository.save(facilityRequest);
    }

    public void updateFacilityRequest(Integer facilityRequest_id, Integer facility_id, FacilityRequest facilityRequest) {

        Facility facility = facilityRepository.findFacilityById(facility_id);

        if (facility == null) {
            throw new ApiException("Facility not found with id: " + facility_id);
        }

        FacilityRequest facilityRequest1 = facilityRequestRepository.findFacilityRequestById(facilityRequest_id);

        if (facilityRequest1 == null) {
            throw new ApiException("Facility request not found with id: " + facilityRequest_id);
        }

        if (facilityRequest1.getFacility() == null || !facilityRequest1.getFacility().getId().equals(facility_id)) {
            throw new ApiException("Sorry, you don't have permission to update this request.");
        }

        facilityRequest1.setFacility(facility);
        facilityRequest1.setDescription(facilityRequest.getDescription());
        facilityRequest1.setProductName(facilityRequest.getProductName());
        facilityRequest1.setQuantity(facilityRequest.getQuantity());


        facilityRequestRepository.save(facilityRequest1);
    }
    public void cancelFacilityRequest(Integer facilityRequestId, Integer userId) {
        FacilityRequest facilityRequest = facilityRequestRepository.findFacilityRequestById(facilityRequestId);

        if (facilityRequest == null) {
            throw new ApiException("Facility request not found with id: " + facilityRequestId);
        }
        Facility facility = facilityRequest.getFacility();
        if (facility == null || !facility.getUser().getId().equals(userId)) {
            throw new ApiException("Sorry, you don't have permission to cancel this request.");
        }
        facilityRequest.setStatus("CANCELLED");

        facilityRequestRepository.save(facilityRequest);
    }
}
