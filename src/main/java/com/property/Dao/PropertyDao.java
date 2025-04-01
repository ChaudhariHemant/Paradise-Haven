package com.property.Dao;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.property.Entity.Address;
import com.property.Entity.Property;
import com.property.Entity.User;
import com.property.Entity.Vendor;

@Repository
@Transactional
public class PropertyDao {
	@Autowired
	SessionFactory sessionFactory;
	

//	public Property findByEmail(String prop_address) {
//	    try (Session session = seesionfactory.openSession()) {
//	        Query<Property> query = session.createQuery("FROM User WHERE prop_address = :prop_address", Property.class);
//	        query.setParameter("prop_address", prop_address);
//	        return query.uniqueResult();  // Returns the user if exists, otherwise null
//	    }
//	}
	
	public Property findByAddress(String propertyNumber, String street) {
	    try (Session session = sessionFactory.openSession()) {
	        Query<Property> query = session.createQuery(
	            "FROM Property p WHERE p.prop_address.property_number = :property_number AND p.prop_address.street = :street", 
	            Property.class);
	        query.setParameter("property_number", propertyNumber);
	        query.setParameter("street", street);

	        return query.uniqueResult(); // Returns the property if it exists, otherwise null
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	 public Property getPropertyById1(int id) {
	        try (Session session = sessionFactory.openSession()) {
	            return session.get(Property.class, id);
	        }
	    }
	
	 
	public String addProperty(Property property) {
		Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            property.setBookedUser(null); // Initially, no user is assigned
            session.save(property);
            tx.commit();
            return "Property added successfully with ID: " + property.getProperty_id();
        } catch (Exception e) {
            tx.rollback();
            return "Error: " + e.getMessage();
        } finally {
            session.close();
        }
    }

    public Property getPropertyById(int id) {
        Session session = sessionFactory.openSession();
        Property property = session.get(Property.class, id);
        session.close();
        return property;
    }

      public List<Property> getAllProperties() {
        Session session = sessionFactory.openSession();
        List<Property> properties = session.createQuery("from Property", Property.class).list();
        session.close();
        return properties;
    }

      public void updateProperty(Property property) {
          Session session = sessionFactory.openSession();
          Transaction tx = session.beginTransaction();
          try {
              session.merge(property);
              tx.commit();
          } catch (Exception e) {
              tx.rollback();
              throw new RuntimeException("Failed to update property: " + e.getMessage());
          } finally {
              session.close();
          }
      }
      private Session currentSession() {
          return sessionFactory.getCurrentSession();
      }

      public void deleteProperty(int id) {
    	    Session session = sessionFactory.openSession();
    	    Transaction transaction = null;
    	    try {
    	        transaction = session.beginTransaction();
    	        
    	        // 1. First get the address ID
    	        Integer addressId = (Integer) session.createNativeQuery(
    	            "SELECT property_address_id FROM property WHERE property_id = :id")
    	            .setParameter("id", id)
    	            .uniqueResult();
    	        
    	        // 2. First delete the property
    	        session.createNativeQuery("DELETE FROM property WHERE property_id = :id")
    	            .setParameter("id", id)
    	            .executeUpdate();
    	        
    	        // 3. Then delete the address if it exists
    	        if (addressId != null) {
    	            session.createNativeQuery("DELETE FROM address WHERE id = :addressId")
    	                .setParameter("addressId", addressId)
    	                .executeUpdate();
    	        }
    	        
    	        transaction.commit();
    	    } catch (Exception e) {
    	        if (transaction != null) transaction.rollback();
    	        throw new RuntimeException("Failed to delete property", e);
    	    } finally {
    	        session.close();
    	    }
    	}
//      public boolean verifyDeletion(int propertyId) {
//    	    Session session = sessionFactory.openSession();
//    	    try {
//    	        // Check if property exists
//    	        Property property = session.get(Property.class, propertyId);
//    	        if (property == null) {
//    	            System.out.println("Property successfully deleted");
//    	            
//    	            // If property had an address, verify it's gone too
//    	            if (property != null && property.getProp_address() != null) {
//    	                Address address = session.get(Address.class, property.getProp_address().getId());
//    	                if (address == null) {
//    	                    System.out.println("Address successfully deleted");
//    	                } else {
//    	                    System.out.println("ADDRESS STILL EXISTS IN DATABASE!");
//    	                }
//    	            }
//    	            return true;
//    	        }
//    	        return false;
//    	    } finally {
//    	        session.close();
//    	    }
//    	}
}