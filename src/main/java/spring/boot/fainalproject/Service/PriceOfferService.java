package spring.boot.fainalproject.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.boot.fainalproject.API.ApiException;
import spring.boot.fainalproject.Model.PriceOffer;
import spring.boot.fainalproject.Model.RecyclingRequest;
import spring.boot.fainalproject.Model.Supplier;
import spring.boot.fainalproject.Repository.PriceOfferRepository;
import spring.boot.fainalproject.Repository.RecyclingRequestRepository;
import spring.boot.fainalproject.Repository.SupplierRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PriceOfferService {

    private final PriceOfferRepository priceOfferRepository;
    private final SupplierRepository supplierRepository;
    private final RecyclingRequestRepository recyclingRequestRepository;

    public List<PriceOffer> getAllPriceOffers() {
        return priceOfferRepository.findAll();
    }

    public PriceOffer getPriceOfferById(Integer id) {
        PriceOffer priceOffer = priceOfferRepository.findPriceOfferById(id);
        if (priceOffer == null) {
            throw new ApiException("Price Offer not found");
        }
        return priceOffer;
    }

    public void createPriceOffer(Integer recycle_id, Integer supplier_id, PriceOffer priceOffer) {
        RecyclingRequest recyclingRequest = recyclingRequestRepository.findRecyclingRequestByid(recycle_id);
        if (recyclingRequest == null) {
            throw new ApiException("Recycling Request not found");
        }

        Supplier supplier = supplierRepository.findSupplierById(supplier_id);
        if (supplier == null) {
            throw new ApiException("Supplier not found");
        }

        // Check if the supplier has already submitted a price offer for this recycling request
        if (recyclingRequest.getPrice_offer() != null) {
            for (Supplier existingSupplier : recyclingRequest.getPrice_offer().getSuppliers()) {
                if (existingSupplier.getId() == supplier.getId()) {
                    throw new ApiException("You have already submitted a price offer for this recycling request.");
                }
            }
        }

        priceOffer.setStatus("PENDING");
        recyclingRequest.setPrice_offer(priceOffer); // Set the price offer for the recycling request
        priceOfferRepository.save(priceOffer);       // Save the price offer
    }

    public void updatePriceOffer(Integer priceOfferId, Integer supplierId, PriceOffer updatedPriceOffer) {
        PriceOffer existingPriceOffer = priceOfferRepository.findPriceOfferById(priceOfferId);
        if (existingPriceOffer == null) {
            throw new ApiException("Price Offer not found");
        }

        // Ensure that the supplier is the one who created this price offer
        boolean supplierFound = false;
        for (Supplier supplier : existingPriceOffer.getSuppliers()) {
            if (supplier.getId() == supplierId) {
                supplierFound = true;
                break;
            }
        }

        if (!supplierFound) {
            throw new ApiException("You do not have permission to update this price offer.");
        }

        existingPriceOffer.setPrice(updatedPriceOffer.getPrice());
        existingPriceOffer.setStatus("PENDING");
        priceOfferRepository.save(existingPriceOffer);
    }
    public void cancelPriceOffer(Integer priceOfferId, Integer supplierId) {
        PriceOffer existingPriceOffer = priceOfferRepository.findPriceOfferById(priceOfferId);
        if (existingPriceOffer == null) {
            throw new ApiException("Price Offer not found");
        }

        // Ensure that the supplier is the one who created this price offer
        boolean supplierFound = false;
        for (Supplier supplier : existingPriceOffer.getSuppliers()) {
            if (supplier.getId()== supplierId) {
                supplierFound = true;
                break;
            }
        }

        if (!supplierFound) {
            throw new ApiException("You do not have permission to delete this price offer.");
        }
        existingPriceOffer.setStatus("CANCELLED");
        priceOfferRepository.save(existingPriceOffer);
    }

    }

