package spring.boot.fainalproject.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.boot.fainalproject.DTO.SupplierDTO;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Service.SupplierService;

;

@RestController
@RequestMapping("/api/v1/supplier")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity getAllSuppliers() {
       return ResponseEntity.status(200).body(supplierService.getAllSuppliers());
    }
    @PostMapping("/register")
    public ResponseEntity registerSupplier(@Valid @RequestBody SupplierDTO supplierDTO) {
        supplierService.registerSupplier(supplierDTO);
        return ResponseEntity.status(200).body(supplierService.getAllSuppliers());
    }
    @PutMapping
    public ResponseEntity updateSupplier(@AuthenticationPrincipal User user, @Valid @RequestBody SupplierDTO supplierDTO) {
        supplierService.updateSupplier(user.getId(), supplierDTO);
        return ResponseEntity.status(200).body(supplierService.getAllSuppliers());
    }
    @DeleteMapping
    public ResponseEntity deleteSupplier(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.status(200).body(supplierService.getAllSuppliers());
    }

    @GetMapping("search-supplier-by-name/{name}")
    public ResponseEntity searchSupplierByName(@PathVariable String name) {
        return ResponseEntity.status(200).body(supplierService.searchSupplierByName(name));
    }
}
