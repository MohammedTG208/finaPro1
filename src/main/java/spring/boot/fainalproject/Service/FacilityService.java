package spring.boot.fainalproject.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring.boot.fainalproject.API.ApiException;
import spring.boot.fainalproject.DTO.FacilityDTO;
import spring.boot.fainalproject.Model.Facility;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Repository.AuthRepository;
import spring.boot.fainalproject.Repository.FacilityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacilityService {
    private final FacilityRepository facilityRepository;
    private final AuthRepository authRepository;

    public List<Facility> getAllFacilities(){
        return facilityRepository.findAll();

    }
    public void registerFacility(FacilityDTO facilityDTO) {
        User user = new User();
        user.setUsername(facilityDTO.getUsername());
        user.setName(facilityDTO.getName());

        String hashedPassword = new BCryptPasswordEncoder().encode(facilityDTO.getPassword());
        user.setPassword(hashedPassword);
        user.setRole("FACILITY");

        authRepository.save(user);

        Facility facility = new Facility();

        facility.setEmail(facilityDTO.getEmail());
        facility.setPhoneNumber(facilityDTO.getPhoneNumber());

        facility.setCommericalRegister(facilityDTO.getCommericalRegister());
        facility.setLicenseNumber(facilityDTO.getLicenseNumber());

        facility.setUser(user);
        facilityRepository.save(facility);
    }

    public void updateFacility(Integer facility_id, FacilityDTO facilityDTO) {

        Facility existingFacility = facilityRepository.findFacilityById(facility_id);
        if (existingFacility == null) {
            throw new ApiException("Facility not found");
        }
        User user = existingFacility.getUser();
        user.setUsername(facilityDTO.getUsername());
        user.setName(facilityDTO.getName());
        String hashedPassword = new BCryptPasswordEncoder().encode(facilityDTO.getPassword());
        user.setPassword(hashedPassword);
        user.setRole("FACILITY");

        existingFacility.setEmail(facilityDTO.getEmail());
        existingFacility.setPhoneNumber(facilityDTO.getPhoneNumber());
        existingFacility.setCommericalRegister(facilityDTO.getCommericalRegister());
        existingFacility.setLicenseNumber(facilityDTO.getLicenseNumber());

        existingFacility.setUser(user);

        authRepository.save(user);
        facilityRepository.save(existingFacility);
    }
    public void deleteFacility(Integer facility_id) {
        Facility existingFacility = facilityRepository.findFacilityById(facility_id);
        if (existingFacility == null) {
            throw new ApiException("Facility not found");
        }

        User user = existingFacility.getUser();
        authRepository.delete(user);
        facilityRepository.delete(existingFacility);
    }
}
