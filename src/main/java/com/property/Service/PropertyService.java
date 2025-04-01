package com.property.Service;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.property.DTO.AddressDTO;
import com.property.DTO.PropertyDTO;
import com.property.Dao.PropertyDao;
import com.property.Dao.VendorDao;
import com.property.Entity.Address;
import com.property.Entity.Property;
import com.property.Entity.User;
import com.property.Entity.Vendor;

@Service
public class PropertyService {
	@Autowired
	private PropertyDao propertyDao;
	
	@Autowired
    private VendorDao vendorDao;

	// PropertyDao propertyDao = new PropertyDao(); // Assuming a manual DAO layer

	public String addProperty(PropertyDTO propertyDTO) {
	    if (propertyDTO.getAddress() == null) {
	        return "Error: Property must have an address!";
	    }

	    String propertyNumber = propertyDTO.getAddress().getProperty_number();
	    String street = propertyDTO.getAddress().getStreet();

	    // Check if a property with the same property number and street already exists
	    Property existingProperty = propertyDao.findByAddress(propertyNumber, street);

	    if (existingProperty != null) {
	        return "Property with this address already exists!";
	    }

	    // Convert DTO to Entity and save the property
	    Property property = convertToEntity(propertyDTO);
	    return propertyDao.addProperty(property);
	}

	public PropertyDTO getPropertyById(int id) {
		Property property = propertyDao.getPropertyById(id);
		return convertToDTO(property);
	}

	public List<PropertyDTO> getAllProperties() {
		List<Property> properties = propertyDao.getAllProperties();
		return properties.stream().map(this::convertToDTO).collect(Collectors.toList());
	}


    @Transactional
    public String updatePropertyById(int id, PropertyDTO propertyDTO) {
        Property property = propertyDao.getPropertyById(id);
        if (property == null) {
            throw new RuntimeException("Property not found with ID: " + id);
        }

        // Update Property fields
        if (propertyDTO.getProperty_type() != null) {
            property.setProperty_type(propertyDTO.getProperty_type());
        }
        if (propertyDTO.getDescription() != null) {
            property.setDescription(propertyDTO.getDescription());
        }
        if (propertyDTO.getPrice() != null) {
            property.setPrice(propertyDTO.getPrice());
        }

        // Update Address
        if (propertyDTO.getAddress() != null) {
            Address address = property.getProp_address();
            if (address == null) {
                address = new Address();
            }
            if (propertyDTO.getAddress().getStreet() != null) {
                address.setStreet(propertyDTO.getAddress().getStreet());
            }
            if (propertyDTO.getAddress().getCity() != null) {
                address.setCity(propertyDTO.getAddress().getCity());
            }
            if (propertyDTO.getAddress().getState() != null) {
                address.setState(propertyDTO.getAddress().getState());
            }
            if (propertyDTO.getAddress().getZip() != null) {
                address.setZipCode(propertyDTO.getAddress().getZip());
            }
            if (propertyDTO.getAddress().getProperty_number() != null) {
                address.setProperty_number(propertyDTO.getAddress().getProperty_number());
            }

            property.setProp_address(address);
        }
        if (propertyDTO.getVendor_id() > 0) {
            Vendor vendor = vendorDao.getVendorById(propertyDTO.getVendor_id());
            if (vendor == null) {
                throw new RuntimeException("Vendor not found with ID: " + propertyDTO.getVendor_id());
            }
            property.setVendor(vendor);
        }

        propertyDao.updateProperty(property);
        return "Property updated successfully!";
    }


    public String deleteProperty(int id) {
        try {
            propertyDao.deleteProperty(id);
            
            // Verify deletion actually occurred
            Property deletedProperty = propertyDao.getPropertyById(id);
            if (deletedProperty != null) {
                throw new RuntimeException("Property still exists in database after deletion");
            }
            
            return "Property and its Address deleted successfully!";
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete property: " + e.getMessage());
        }
    }

    
	// Helper methods to convert between DTO and Entity
	private Property convertToEntity(PropertyDTO dto) {
	    Property property = new Property();
	    property.setProperty_id(dto.getProperty_id());
	    property.setProperty_type(dto.getProperty_type());
	    property.setDescription(dto.getDescription());
	    property.setPrice(dto.getPrice());

	    Address address = new Address();
	    if (dto.getAddress() != null) {
	        address.setId(dto.getAddress().getId());
	        address.setStreet(dto.getAddress().getStreet());
	        address.setCity(dto.getAddress().getCity());
	        address.setState(dto.getAddress().getState());
	        address.setZipCode(dto.getAddress().getZip());
	        address.setProperty_number(dto.getAddress().getProperty_number()); // Fix applied ✅
	    }
	    property.setProp_address(address);

	    Vendor vendor = new Vendor();
	    vendor.setId(dto.getVendor_id());
	    property.setVendor(vendor);

	    if (dto.getUser_id() != null) {
	        User user = new User();
	        user.setId(dto.getUser_id());
	        property.setBookedUser(user);
	    }

	    return property;
	}

	private PropertyDTO convertToDTO(Property property) {
	    PropertyDTO dto = new PropertyDTO();
	    dto.setProperty_id(property.getProperty_id());
	    dto.setProperty_type(property.getProperty_type());
	    dto.setDescription(property.getDescription());
	    dto.setPrice(property.getPrice());

	    AddressDTO addressDTO = new AddressDTO();
	    if (property.getProp_address() != null) {
	        addressDTO.setId(property.getProp_address().getId());
	        addressDTO.setStreet(property.getProp_address().getStreet());
	        addressDTO.setCity(property.getProp_address().getCity());
	        addressDTO.setState(property.getProp_address().getState());
	        addressDTO.setZip(property.getProp_address().getZipCode());
	        addressDTO.setProperty_number(property.getProp_address().getProperty_number()); // Fix applied ✅
	    }
	    dto.setAddress(addressDTO);

	    dto.setVendor_id(property.getVendor().getId());
	    if (property.getBookedUser() != null) {
	        dto.setUser_id(property.getBookedUser().getId());
	    }

	    return dto;
	}
}
//import java.util.List;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import com.property.Dao.PropertyDao;
//import com.property.Dao.UserDao;
//import com.property.Entity.Property;
//import com.property.Entity.User;
//
//@Service
//public class PropertyService {
//	@Autowired
//	private PropertyDao propertyDao;
//	
//	
//	
//	public String addProperty(Property property) {
//        propertyDao.addProperty(property);
//        return "Property added successfully";
//    }
//
//    public Property getPropertyById(int id) {
//        return propertyDao.getPropertyById(id);
//    }
//
//    public List<Property> getAllProperties() {
//        return propertyDao.getAllProperties();
//    }
//
//    public String updateProperty(Property property) {
//        propertyDao.updateProperty(property);
//        return "Property updated successfully";
//    }
//
//    public String deleteProperty(int id) {
//        propertyDao.deleteProperty(id);
//        return "Property deleted successfully";
//    }
//}