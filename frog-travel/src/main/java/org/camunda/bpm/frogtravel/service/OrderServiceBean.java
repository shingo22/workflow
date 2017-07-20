package org.camunda.bpm.frogtravel.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Date;
import java.util.Map;

import org.camunda.bpm.frogtravel.persistence.*;

@Stateless
@Named
public class OrderServiceBean {

	  // Inject the entity manager
	  @PersistenceContext
	  private EntityManager entityManager;
	  
	  public void persistOrder(DelegateExecution delegateExecution) {
		    // Create new order instance
		    Order order = new Order();

		    // Get all process variables
		    Map<String, Object> variables = delegateExecution.getVariables();

		    // Set order attributes
		    order.setFirstName((String) variables.get("firstName"));
		    order.setLastName((String) variables.get("lastName"));
		    order.setBirthDate((Date) variables.get("birthDate"));
		    order.setEmail((String) variables.get("email"));
		    
		    order.setDestination((String) variables.get("destination"));
		    order.setArriveTime((String) variables.get("arriveDate"));
		    order.setReturnTime((String) variables.get("returnDate"));
		    
		    order.setTransferIncluded((Boolean) variables.get("isTransferIncluded"));
		    order.setEquipIncluded((Boolean) variables.get("isEquipIncluded"));
		    order.setCateringIncluded((Boolean) variables.get("isCateringIncluded"));
		    order.setInstructionIncluded((Boolean) variables.get("isInstructionIncluded"));
		    
		    //order.setPaymentInfo((PaymentInfo) variables.get("paymentInfo"));

		    /*
		      Persist order instance and flush. After the flush the
		      id of the order instance is set.
		    */
		    entityManager.persist(order);
		    entityManager.flush();

//		    // Remove no longer needed process variables
//		    delegateExecution.removeVariables(variables.keySet());
//
		    // Add newly created order id as process variable
		    delegateExecution.setVariable("orderId", order.getOrderId());
	  }
	
}