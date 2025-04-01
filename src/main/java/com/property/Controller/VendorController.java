package com.property.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.property.Entity.User;
import com.property.Entity.Vendor;
import com.property.Service.VendorService;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;

  

    @PostMapping("/addVendor")
    public String addVendor(@RequestBody Vendor vendor) {
        return vendorService.addVendor(vendor);
    }

    @GetMapping("/all-vendors")
    public List<Vendor> getVendors() {
        return vendorService.getVendors();
    }

    @GetMapping("/vendor-by-id/{id}")
    public Vendor getVendorById(@PathVariable int id) {
        return vendorService.getVendorById(id);
    }
    
    @PutMapping("/updatevendor/{id}")
    public ResponseEntity<Vendor> updateVendor(@PathVariable int id, @RequestBody Vendor vendor) {
        Vendor updatedVendor = vendorService.updateVendor(id, vendor);
        if (updatedVendor != null) {
            return ResponseEntity.ok(updatedVendor);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // User not found
        }
    }
    @DeleteMapping("/deletevendor/{id}")
    public String deleteVendor(@PathVariable int id) {
        return vendorService.deleteVendor(id);
    }
}