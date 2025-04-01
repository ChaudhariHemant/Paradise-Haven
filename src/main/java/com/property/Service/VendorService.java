package com.property.Service;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.property.Dao.VendorDao;
import com.property.Entity.Vendor;

@Service
public class VendorService {
    
    @Autowired
    private VendorDao vendorDao;

    // Add a single vendor
    public String addVendor(Vendor vendor) {
        if (vendorDao.vendorExistsByEmail(vendor.getEmail())) {
            return "Vendor with this email already exists!";
        }
        return vendorDao.addVendor(vendor);
    }


    public List<Vendor> getVendors() {
        return vendorDao.getAllVendors();
    }

    @Transactional(readOnly = true)  // Ensures session stays open for lazy loading
    public Vendor getVendorById(int id) {
        Vendor vendor = vendorDao.getVendorById(id);
        if (vendor != null) {
            Hibernate.initialize(vendor.getProperties()); // Force initialization of lazy field
        }
        return vendor;
    }

    public String deleteVendor(int id) {
        return vendorDao.deleteVendor(id);
    }

    public Vendor updateVendor(int id, Vendor vendor) {
        return vendorDao.updateVendor(id,vendor);
    }
}