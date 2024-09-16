package spring.boot.fainalproject.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.boot.fainalproject.API.ApiException;
import spring.boot.fainalproject.Model.Facility;
import spring.boot.fainalproject.Model.RecyclingRequest;
import spring.boot.fainalproject.Model.Supplier;
import spring.boot.fainalproject.Repository.FacilityRepository;
import spring.boot.fainalproject.Repository.RecyclingRequestRepository;
import spring.boot.fainalproject.Repository.SupplierRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecyclingRequestService {

    private final RecyclingRequestRepository recyclingRequestRepository;
    private final FacilityRepository facilityRepository;
    private final SupplierRepository supplierRepository;

    // Get all recycling requests
    public List<RecyclingRequest> getAllRecyclingRequests() {
        return recyclingRequestRepository.findAll();
    }

    // Get a specific recycling request by ID
    public RecyclingRequest getRecyclingRequestById(Integer id) {
        RecyclingRequest recyclingRequest = recyclingRequestRepository.findRecyclingRequestByid(id);
        if (recyclingRequest == null) {
            throw new ApiException("Recycling request not found");
        }
        return recyclingRequest;
    }

    // Add a new recycling request
    public void addRecyclingRequest(RecyclingRequest recyclingRequest, Integer facilityId) {
        Facility facility = facilityRepository.findFacilityById(facilityId);

        if (facility == null ) {
            throw new ApiException("Both Facility and Supplier do not exist");
        }
        recyclingRequest.setFacility_recycle(facility);
        recyclingRequestRepository.save(recyclingRequest);
    }

    // Update an existing recycling request
    public void updateRecyclingRequest(Integer id, RecyclingRequest updatedRequest, Integer facilityId, Integer supplierId) {
        RecyclingRequest recyclingRequest = recyclingRequestRepository.findRecyclingRequestByid(id);
        if (recyclingRequest == null) {
            throw new ApiException("Recycling request not found");
        }

        Facility facility = facilityRepository.findFacilityById(facilityId);
        Supplier supplier = supplierRepository.findSupplierById(supplierId);

        if (facility == null && supplier == null) {
            throw new ApiException("Both Facility and Supplier do not exist");
        }

        recyclingRequest.setProductName(updatedRequest.getProductName());
        recyclingRequest.setQuantity(updatedRequest.getQuantity());
        recyclingRequest.setDescription(updatedRequest.getDescription());

        if (facility != null) {
            recyclingRequest.setFacility_recycle(facility);
        }

        if (supplier != null) {
            recyclingRequest.setSupplier_recycle(supplier);
        }

        recyclingRequestRepository.save(recyclingRequest);
    }

    // Delete a recycling request
    public void deleteRecyclingRequest(Integer id) {
        RecyclingRequest recyclingRequest = recyclingRequestRepository.findRecyclingRequestByid(id);
        if (recyclingRequest == null) {
            throw new ApiException("Recycling request not found");
        }
        recyclingRequestRepository.delete(recyclingRequest);
    }
}
