package org.camunda.bpm.frogtravel.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Date;
import java.util.Map;

import org.camunda.bpm.frogtravel.persistence.Customer;

@Stateless
public class UserServiceBean {
	@PersistenceContext
	private EntityManager entityManager;
	
	
	public void persistCustomer(DelegateExecution delegateExecution) {
		Customer customerEntity = new Customer();
		
		// Get all process variables
	    Map<String, Object> variables = delegateExecution.getVariables();
	    // Set order attributes
	    customerEntity.setFirstName((String) variables.get("firstname"));
	    customerEntity.setLastName((String) variables.get("lastname"));
	    customerEntity.setBirthDate((Date) variables.get("birthdate"));
	    customerEntity.setEmail((String) variables.get("email"));
	    
	    //persist entity
	    entityManager.persist(customerEntity);
	    entityManager.flush();
	    
//	    // Remove no longer needed process variables
//	    delegateExecution.removeVariables(variables.keySet());
//
//	    // Add newly created order id as process variable
//	    delegateExecution.setVariable();
	    
	   		
	}
	
	
}
