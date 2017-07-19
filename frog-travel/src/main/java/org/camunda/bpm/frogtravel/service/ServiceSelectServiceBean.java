package org.camunda.bpm.frogtravel.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.frogtravel.persistence.Customer;
import org.camunda.bpm.frogtravel.persistence.PaymentInfo;
import org.camunda.bpm.frogtravel.persistence.ServiceSelection;

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
		ServiceSelection serviceSelectEntity = new ServiceSelection();
		
		// Get all process variables
	    Map<String, Object> variables = delegateExecution.getVariables();
	    
	    // Set order attributes
	    serviceSelectEntity.setArriveTime((Date) variables.get("arriveDate"));
	    serviceSelectEntity.setReturnTime((Date) variables.get("returnDate"));
	    serviceSelectEntity.setDestination((String) variables.get("destination"));

	    /*
	      Persist order instance and flush. After the flush the
	      id of the order instance is set.
	    */
	    entityManager.persist(serviceSelectEntity);
	    entityManager.flush();
	    
	    
	
	}

}
