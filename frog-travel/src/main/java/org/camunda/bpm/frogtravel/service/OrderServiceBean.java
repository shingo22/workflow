package org.camunda.bpm.frogtravel.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.impl.util.json.JSONObject;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
	  
	  OrderEntity orderObj = null;
	  
	  // Get all default values from database
	  public Collection<DestinationDB> getAll() {
			return entityManager.createQuery("FROM DestinationDB", DestinationDB.class).getResultList();
	  }  
	  
	  public void persistOrder(DelegateExecution delegateExecution) {
		    // Create new order instance
		    OrderEntity orderEntity = new OrderEntity();
		       	      
		    DestinationDB d1= new DestinationDB("2017-09-15", "2017-09-20", true, true, true, true);
		    entityManager.persist(d1);
			entityManager.flush();
			System.out.println("--------- NOW PRINT ALL FROM DATABASE ---------");
			System.out.println(getAll());		    

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
		    
		    //put value into hash map
		    orderEntity.addEquipment((Integer)1, variables.get("checkSki").toString());
		    orderEntity.addEquipment((Integer)2, variables.get("checkSnowboard").toString());
		    orderEntity.addEquipment((Integer)3, variables.get("checkVeryPopularSnowboard").toString());
		    orderEntity.addEquipment((Integer)4, variables.get("checkHelmet").toString());
		    orderEntity.addEquipment((Integer)5, variables.get("checkStick").toString());
		    orderEntity.addEquipment((Integer)6, variables.get("checkSkiSuit").toString());
		    
		    System.out.println("------------------ NOW PRINT EQUIPMENT LIST -------------------");
		    System.out.println(orderEntity.getEquipmentList());
		    	    
		    //order.setPaymentInfo((PaymentInfo) variables.get("paymentInfo"));

		    //persist
		    entityManager.persist(orderEntity);
		    entityManager.flush();
		    
		    //test output
		    System.out.println("------------------ NOW PRINT ORDER --------------------");
		    System.out.println(orderEntity);
		    
		    orderObj = orderEntity;
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
		  
		 String theArriveTime = (String) delegateExecution.getVariables().get("arriveDate"); 
		 String theReturnTime = (String) delegateExecution.getVariables().get("returnDate");
		 System.out.println("---------NOW PRINT THE ARRIVEDATE--------");
		 System.out.println(theArriveTime);
		 
		 if (theArriveTime.equals("10-01-2017")) {
			 System.out.println("THE DESTINATION IS AVALIABLE");
			
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
		 
		 System.out.println("NotifyCustomer");		 
	  }
	  
	  
		@SuppressWarnings("unchecked")
		public JsonObject constructJsonObject(JsonObject jsonObj, DelegateExecution delegateExecution) {
			   //get equipment type index from the hash map
			   HashMap<Integer, String> skiEquipmenList = orderObj.getEquipmentList();
//			   ArrayList<JsonValue> testlist = new ArrayList<JsonValue> ();
			   JsonArrayBuilder testlist = Json.createArrayBuilder();
			   ArrayList<String> list = new ArrayList<String>();
			  	   
			   System.out.println("THE SIZE OF EQUIPMENTLIST IS: " + skiEquipmenList.size());
			   for(int i=0; i<skiEquipmenList.size(); i++) {
				   if(skiEquipmenList.get(i) == "true") {
					  list.add((String) Integer.toString(i));
					  testlist.add((String) Integer.toString(i));
//					  equiplist.add(Integer.toString(i)); 
				   }
			   }
			   JsonArray jsonarraytest = testlist.build();
			   System.out.println("---------- PRINT TESTLIST --------- " + jsonarraytest);
//			   JsonArray equiplist = Json.createArrayBuilder().add("equipment", list);
//			   listObj.put("equipmentList", list);
//			   equiplist.add("equipmentList", list);
			   //construct a JSON object
			   jsonObj = Json.createObjectBuilder()
						 .add("messageName", "ExternalOrder")
						 .add("variables", Json.createObjectBuilder()
								           .add("firstName", Json.createObjectBuilder()
								        		             .add("value", (String) delegateExecution.getVariables().get("firstName"))
								        		             .add("type", "String")
								        		             .add("valueInfo", "{}"))
								           .add("lastName", Json.createObjectBuilder()
								        		   			.add("value", (String) delegateExecution.getVariables().get("lastName"))
								        		   			.add("type", "String")
								        		   			.add("valueInfo", "{}"))
								           .add("orderDate", Json.createObjectBuilder()
								        		   			 .add("value", "NO ORDER DATE")
								        		   			 .add("type", "String")
								        		   			 .add("valueInfo", "{}"))
								           .add("deliveryDate", Json.createObjectBuilder()
								        		   				.add("value", "NO DELIVERY DATE")
								        		   				.add("type", "String")
								        		   				.add("valueInfo", "{}"))
								           .add("orderType", Json.createObjectBuilder()
								        		   			 .add("value", "frog")
								        		   			 .add("type", "String")
								        		   			 .add("valueInfo", "{}"))
								           .add("isBuying", Json.createObjectBuilder()
								        		   			.add("value", "false")
								        		   			.add("type", "String")
								        		   			.add("valueInfo", "{}"))
								           .add("extProcessId", Json.createObjectBuilder()
								        		   				.add("value", (String) delegateExecution.getProcessInstanceId())
								        		   				.add("type", "String")
								        		   				.add("valueInfo", "{}"))
								           .add("equipmentList", jsonarraytest))
						 .build();  
			   System.out.println(jsonObj);
			   
			   return jsonObj;
		  }
	  
	  
	  //check equipment
	  //send message to Ski Oasis
	  public void checkEquipment(DelegateExecution delegateExecution) throws Exception {
			// build HTTP request with all variables as parameters
			HttpClient client = HttpClients.createDefault();
//			HttpPut put = new HttpPut("http://requestb.in/<your-request-bin>");
			RequestBuilder requestBuilder = RequestBuilder.get().setUri("https://requestb.in/wkyz21wk");
					
			//construct a JSON object to fulfill the requested format from SkiOasis
			JsonObject jsonObj = null;
			jsonObj = constructJsonObject(jsonObj, delegateExecution);
							 
			//assign to string entity
			StringEntity entity = new StringEntity(jsonObj.toString());

			requestBuilder.setHeader("Content-type", "application/json");
			requestBuilder.setEntity(entity);
			// execute request
			HttpUriRequest request = requestBuilder.build();
			System.out.println("---------NOW PRINT REQUEST CONTENT---------");
			System.out.println(request);
			HttpResponse response = client.execute(request);
			// log debug information
			System.out.println(request.getURI());
			System.out.println(response.getStatusLine());		
		}	
}