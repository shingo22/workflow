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
		    OrderEntity orderEntity = new OrderEntity();

		    // Get all process variables
		    Map<String, Object> variables = delegateExecution.getVariables();

		    // Set order attributes
		    orderEntity.setFirstName((String) variables.get("firstName"));
		    orderEntity.setLastName((String) variables.get("lastName"));
		    orderEntity.setBirthDate((String) variables.get("birthDate"));
		    orderEntity.setEmail((String) variables.get("email"));
		    
		    orderEntity.setDestination((String) variables.get("destination"));
		    orderEntity.setArriveTime((String) variables.get("arriveDate"));
		    orderEntity.setReturnTime((String) variables.get("returnDate"));
		    
		    orderEntity.setTransferIncluded((Boolean) variables.get("isTransferIncluded"));
		    orderEntity.setEquipIncluded((Boolean) variables.get("isEquipIncluded"));
		    orderEntity.setCateringIncluded((Boolean) variables.get("isCateringIncluded"));
		    orderEntity.setInstructionIncluded((Boolean) variables.get("isInstructionIncluded"));
		    
		    //order.setPaymentInfo((PaymentInfo) variables.get("paymentInfo"));

		    //persist
		    entityManager.persist(orderEntity);
		    
		    //test output
		    System.out.println("NOW PRINT ORDER ------------------------");
		    System.out.println(orderEntity);
		    
		    entityManager.flush();

//		    // Remove no longer needed process variables
//		    delegateExecution.removeVariables(variables.keySet());
//
		    // Add newly created order id as process variable
		    delegateExecution.setVariable("orderId", orderEntity.getOrderId());
	  }
	
}