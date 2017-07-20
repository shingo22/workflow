package org.camunda.bpm.frogtravel.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
		    Order orderEntity = new Order();

		    // Get all process variables
		    Map<String, Object> variables = delegateExecution.getVariables();

		    // Set order attributes
		    orderEntity.setCustomer((Customer) variables.get("customer"));
		    orderEntity.setPaymentInfo((PaymentInfo) variables.get("paymentInfo"));
		    orderEntity.setServiceSelection((ServiceSelection) variables.get("ServiceSelection"));

		    /*
		      Persist order instance and flush. After the flush the
		      id of the order instance is set.
		    */
		    entityManager.persist(orderEntity);
		    entityManager.flush();

//		    // Remove no longer needed process variables
//		    delegateExecution.removeVariables(variables.keySet());
//
//		    // Add newly created order id as process variable
//		    delegateExecution.setVariable("orderId", orderEntity.getOrderId());
	  }
	
}