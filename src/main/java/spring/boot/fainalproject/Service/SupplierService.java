package spring.boot.fainalproject.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring.boot.fainalproject.API.ApiException;
import spring.boot.fainalproject.DTO.SupplierDTO;
import spring.boot.fainalproject.Model.Supplier;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Repository.AuthRepository;
import spring.boot.fainalproject.Repository.OrderRepository;
import spring.boot.fainalproject.Repository.SupplierRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final AuthRepository  authRepository;
    private final OrderRepository orderRepository;

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }
    public void registerSupplier(SupplierDTO supplierDTO) {
        User user = new User();
        user.setUsername(supplierDTO.getUsername());
        user.setName(supplierDTO.getName());
        String hash = new BCryptPasswordEncoder().encode(supplierDTO.getPassword());
        user.setPassword(hash);
        user.setRole("SUPPLIER");
        authRepository.save(user);

        Supplier supplier = new Supplier();
        supplier.setEmail(supplierDTO.getEmail());
        supplier.setPhoneNumber(supplierDTO.getPhoneNumber());
        supplier.setCommercialRegister(supplierDTO.getCommericalRegister());
        supplier.setLicenseNumber(supplierDTO.getLicenseNumber());

        supplier.setUser(user);
        supplierRepository.save(supplier);
    }
    public void updateSupplier(Integer supplier_id, SupplierDTO supplierDTO) {
        Supplier supplier = supplierRepository.findSupplierById(supplier_id);
        if (supplier == null) {
            throw new ApiException("Supplier not found");
        }
        User user = supplier.getUser();
        user.setUsername(supplierDTO.getUsername());
        user.setName(supplierDTO.getName());
        String hash = new BCryptPasswordEncoder().encode(supplierDTO.getPassword());
        user.setPassword(hash);
        user.setRole("SUPPLIER");

        supplier.setEmail(supplierDTO.getEmail());
        supplier.setPhoneNumber(supplierDTO.getPhoneNumber());
        supplier.setCommercialRegister(supplierDTO.getCommericalRegister());
        supplier.setLicenseNumber(supplierDTO.getLicenseNumber());

        supplier.setUser(user);

        authRepository.save(user);
        supplierRepository.save(supplier);
    }

    public void deleteSupplier(Integer supplier_id) {
        Supplier supplier = supplierRepository.findSupplierById(supplier_id);
        if (supplier == null) {
            throw new ApiException("Supplier not found");
        }
        User user = supplier.getUser();
        authRepository.delete(user);
        supplierRepository.delete(supplier);
    }

    //search about supplier by name for customer and facility display all his product.
    //extra 11
    public Supplier searchSupplierByName(String SupplierName) {
        User user = authRepository.findUserByUsername(SupplierName);
        if (supplierRepository.findSupplierById(user.getId()) == null) {
            throw new ApiException("Supplier not found");
        }else {
            return supplierRepository.findSupplierById(user.getId());
        }
    }
}
