package com.property.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.property.DTO.PropertyDTO;
import com.property.Service.PropertyService;


@RestController
@RequestMapping("/properties")  
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping("/add")
    public String addProperty(@RequestBody PropertyDTO propertyDTO) {
        return propertyService.addProperty(propertyDTO);
    }

    @GetMapping("/{id}")
    public PropertyDTO getPropertyById(@PathVariable int id) {
        return propertyService.getPropertyById(id);
    }

    @GetMapping("/all")
    public List<PropertyDTO> getAllProperties() {
        return propertyService.getAllProperties();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePropertyById(@PathVariable int id, @RequestBody PropertyDTO propertyDTO) {
        try {
            String result = propertyService.updatePropertyById(id, propertyDTO);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update property");
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProperty(@PathVariable int id) {
        try {
            String result = propertyService.deleteProperty(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error deleting property: " + e.getMessage());
        }
    }
}










































//import java.util.List;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import com.property.Entity.Property;
//import com.property.Service.PropertyService;
//
//@RestController
//@RequestMapping("/properties")  
//public class PropertyController {

   // @Autowired
   // private PropertyService propertyService;

    
//    @PostMapping("/add")
//    public ResponseEntity<String> addProperty(@RequestBody Property property) {
//        try {
//            String result = propertyService.addProperty(property);
//            return new ResponseEntity<>(result, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to add property: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping("/{id}")
//    public Property getPropertyById(@PathVariable int id) {
//        return propertyService.getPropertyById(id);
//    }
//
//    @GetMapping("/all")
//    public List<Property> getAllProperties() {
//        return propertyService.getAllProperties();
//    }
//
//    @PutMapping("/update")
//    public String updateProperty(@RequestBody Property property) {
//        return propertyService.updateProperty(property);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public String deleteProperty(@PathVariable int id) {
//        return propertyService.deleteProperty(id);
  //  }
//}