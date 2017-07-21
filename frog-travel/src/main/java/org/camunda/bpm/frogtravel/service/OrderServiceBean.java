package org.camunda.bpm.frogtravel.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
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
	  
//	  @Inject
//	  private TaskForm taskForm;
	  
//	  OrderEntity orderObj;
	  
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
		    
//		    orderObj = orderEntity;

//		    // Remove no longer needed process variables
//		    delegateExecution.removeVariables(variables.keySet());
//
		    // Add newly created order id as process variable
		    delegateExecution.setVariable("orderId", orderEntity.getOrderId());
		    
		    // Set initial value for availability of everything
		    delegateExecution.setVariable("everythingAvailable", true);
	  }
	  
	  
	  //check accommodation
	  public void checkAccommodation(DelegateExecution delegateExecution) {	  
		 // Get all process variables
		 
		 String theArriveTime = (String) delegateExecution.getVariables().get("arriveDate"); 
		 String theReturnTime = (String) delegateExecution.getVariables().get("returnDate");
		 System.out.println("NOW PRINT THE ARRIVEDATE--------");
		 System.out.println(theArriveTime);
		 
		 if (theArriveTime.equals("10-01-2017")) {
			 System.out.println("THE DESTINATION IS AVALIABLE FROM " + theArriveTime + " TO " + theReturnTime);
			
			 delegateExecution.setVariable("isApproved", true);
		 } else {
			 delegateExecution.setVariable("isApproved", false);		 
		 }
		 
	  }
	  
	  //check transportation
	  public void checkTransfer(DelegateExecution delegateExecution) {	  
		 
		 System.out.println("Transfer");		 
	  }
	  
	  //check catering
	  public void checkCatering(DelegateExecution delegateExecution) {	  
		 
		 System.out.println("Catering");		 
	  }
	  
	  //check equipment
	  public void checkEquipment(DelegateExecution delegateExecution) {	  
		 
		 System.out.println("Equipment");		 
	  }
	  
	  //check instruction
	  public void checkInstruction(DelegateExecution delegateExecution) {	  
		 
		 System.out.println("Instruction");		 
	  }
	  
	  //check instruction
	  public void sendOrderDetails(DelegateExecution delegateExecution) {	  
		 
		 System.out.println("OrderDetails");		 
	  }
	  
	  //check instruction
	  public void nortifyCustomer(DelegateExecution delegateExecution) {	  
		 
		 System.out.println("nortifyCustomer");		 
	  }
	  
	  //send message to Ski Oasis
	  public void sendToSkiOasis(DelegateExecution delegateExecution) throws Exception {
			// build HTTP request with all variables as parameters
			HttpClient client = HttpClients.createDefault();
//			HttpPut put = new HttpPut("http://requestb.in/<your-request-bin>");
			RequestBuilder requestBuilder = RequestBuilder.get().setUri("https://requestb.in/154sr8r1");
			
			requestBuilder.addParameter("ProcessInstanceId", delegateExecution.getProcessInstanceId());
			requestBuilder.addParameter("TEST MESSAGE", "TEST FOR API");
			
//			JSONObject jsonObj = ;
			
			// execute request
			HttpUriRequest request = requestBuilder.build();
			System.out.println("NOW PRINT REQUEST CONTENT---------");
			System.out.println(request);
			HttpResponse response = client.execute(request);
			// log debug information
			System.out.println(request.getURI());
			System.out.println(response.getStatusLine());
		}
	
}