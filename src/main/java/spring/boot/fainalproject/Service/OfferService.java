package spring.boot.fainalproject.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.boot.fainalproject.API.ApiException;
import spring.boot.fainalproject.Model.FacilityRequest;
import spring.boot.fainalproject.Model.Offer;
import spring.boot.fainalproject.Model.Supplier;
import spring.boot.fainalproject.Repository.FacilityRequestRepository;
import spring.boot.fainalproject.Repository.OfferRepository;
import spring.boot.fainalproject.Repository.SupplierRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    private final FacilityRequestRepository requestRepository;
    private final SupplierRepository supplierRepository;

    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    public void createOffer(Integer facilityRequest_id,Integer supplier_id, Offer offer) {
        FacilityRequest facilityRequest= requestRepository.findFacilityRequestById(facilityRequest_id);
        Supplier supplier = supplierRepository.findSupplierById(supplier_id);

        offer.setStatus("PENDING");
        offer.setFacilityRequest(facilityRequest);
        offer.setSupplier(supplier);
        offerRepository.save(offer);

    }

    public void updateOffer(Integer offerId, Integer supplierId, Offer updatedOffer) {
        Offer existingOffer = offerRepository.findOfferById(offerId);

        Supplier supplier = existingOffer.getSupplier();

        if (supplier.getId()!= supplierId) {
            throw new ApiException("You are not authorized to update this offer");
        }
        // Update the offer's fields
        existingOffer.setSupplier(existingOffer.getSupplier());
        existingOffer.setFacilityRequest(existingOffer.getFacilityRequest());
        existingOffer.setPrice(updatedOffer.getPrice());
        existingOffer.setStatus(updatedOffer.getStatus());

        offerRepository.save(existingOffer);
    }


    public void cancelOffer(Integer offerId, Integer supplierId) {
        Offer existingOffer = offerRepository.findOfferById(offerId);

        Supplier supplier = existingOffer.getSupplier();

        // Ensure the supplier making the cancellation is the owner of the offer
        if (supplier.getId()!=supplierId) {
            throw new ApiException("You are not authorized to cancel this offer");
        }

        // Update the status to "CANCELED"
        existingOffer.setStatus("CANCELED");

        offerRepository.save(existingOffer);
    }

}
