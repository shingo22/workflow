package org.camunda.bpm.frogtravel.service;

import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.engine.delegate.DelegateExecution;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.frogtravel.persistence.*;

@Stateless
@Named
public class OrderServiceBean {

	  // Inject the entity manager
	  @PersistenceContext
	  private EntityManager entityManager;
	  
	  @Inject
	  private TaskForm taskForm;
	  
	  OrderEntity orderObj;
	  
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
		    
		    
		    //orderEntity.setEquipmentList((List<String>) variables.get("skiEquipmentType"));
		    
		    orderEntity.addEquipment((Integer)1, variables.get("checkSki").toString());
		    orderEntity.addEquipment((Integer)2, variables.get("checkSnowboard").toString());
		    orderEntity.addEquipment((Integer)3, variables.get("checkVeryPopularSnowboard").toString());
		    orderEntity.addEquipment((Integer)4, variables.get("checkHelmet").toString());
		    orderEntity.addEquipment((Integer)5, variables.get("checkStick").toString());
		    orderEntity.addEquipment((Integer)6, variables.get("checkSkiSuit").toString());
		    
		    System.out.println("NOW PRINT EQUIPMENT LIST -----------------------");
		    System.out.println(orderEntity.getEquipmentList());
		    	    
		    //order.setPaymentInfo((PaymentInfo) variables.get("paymentInfo"));

		    //persist
		    entityManager.persist(orderEntity);
		    entityManager.flush();
		    
		    //test output
		    System.out.println("NOW PRINT ORDER ------------------------");
		    System.out.println(orderEntity);
		    
		    orderObj = orderEntity;

//		    // Remove no longer needed process variables
//		    delegateExecution.removeVariables(variables.keySet());
//
		    // Add newly created order id as process variable
		    delegateExecution.setVariable("orderId", orderEntity.getOrderId());
	  }
	  
	  
	  //check accommodation
	  public void checkAccommodation(DelegateExecution delegateExecution) {	  
		 // Get all process variables
		 
		 
		 if ((String) delegateExecution.getVariables().get("arriveDate") == "10-01-2017") {
			 System.out.println("THE DESTINATION IS AVALIABLE FROM " + orderObj.getArriveTime() + " TO " + orderObj.getReturnTime());
			
			 delegateExecution.setVariable("isApproved", true);
		 } else {
			 delegateExecution.setVariable("isApproved", false);		 
		 }
		 
	  }
	
}