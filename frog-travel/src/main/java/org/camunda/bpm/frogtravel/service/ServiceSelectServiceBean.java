package org.camunda.bpm.frogtravel.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.frogtravel.persistence.Customer;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Date;
import java.util.Map;


@Stateless
@Named
public class ServiceSelectServiceBean {
	@PersistenceContext
	private EntityManager entityManager;
	
	public void persistCustomer(DelegateExecution delegateExecution) {
		Customer customerEntity = new Customer();
		
		// Get all process variables
	    Map<String, Object> variables = delegateExecution.getVariables();
	    
		
	
	}

}
