package org.camunda.bpm.frogtravel.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
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
	  DestinationDB currentDestination = null;
	  
	  // Get all default values from database
	  public Collection<DestinationDB> getAll() {
			return entityManager.createQuery("FROM DestinationDB", DestinationDB.class).getResultList();
	  }
	  
	  public Collection<String> getAllDestination() {
		  Collection<DestinationDB> dess = getAll();
		  ArrayList<String> allDestination = new ArrayList<String>();
		  for(DestinationDB des: dess) {
			  allDestination.add(des.getLocation());
		  }
		  
		  return allDestination;
	  }
	  
	  public DestinationDB getDestinationInfo(String destination) {
		  Collection<DestinationDB> dess = getAll();
		  DestinationDB info = null;
		  for(DestinationDB des: dess) {
			  if(des.getLocation().equals(destination)){
				info = des; 
			  }
		  }
		  return info;
	  }
	  
	  public void persistInitialData(){
		    DestinationDB d1= new DestinationDB("SkiPalace", "2017-11-01", "2017-04-10", true, true, true, true);
		    entityManager.persist(d1);
		    DestinationDB d2= new DestinationDB("SkiHeaven", "2017-10-15", "2018-03-31", true, true, true, true);
		    entityManager.persist(d2);
		    DestinationDB d3= new DestinationDB("SkiHell", "2017-11-15", "2018-04-20", true, true, true, true);
		    entityManager.persist(d3);	    
			entityManager.flush();
	  }
	  
	  public void persistOrder(DelegateExecution delegateExecution) {
		    // Create new order instance
		    OrderEntity orderEntity = new OrderEntity();
		    
			System.out.println("--------- NOW PRINT ALL FROM DATABASE ---------");
//			System.out.println(getAll());			
			Collection<DestinationDB> dess = getAll();
		    for(DestinationDB des: dess) {
		    	System.out.println("Location is " + des.getLocation() + " Arrival Time is " + des.getArrivalTime() + " Return Time is " + des.getReturnTime()+" ");
		    }

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
		    //put value into hash map
		    orderEntity.addEquipment((Integer)1, variables.get("atomicVantage").toString());
		    orderEntity.addEquipment((Integer)2, variables.get("blizzardQuattro").toString());
		    orderEntity.addEquipment((Integer)3, variables.get("rossignolAlias").toString());
		    orderEntity.addEquipment((Integer)4, variables.get("nordicaNMove").toString());
		    orderEntity.addEquipment((Integer)5, variables.get("giroDiscordHelmet").toString());
		    orderEntity.addEquipment((Integer)6, variables.get("motocrossMtbATVt").toString());
		    
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
		    
		    // Set initial value to true if customer chose equipment service
		    if(orderEntity.isEquipIncluded() == true) {
		    	delegateExecution.setVariable("isEquipmentIncluded", true);
		    	System.out.println("Customer needs ski equipment service");
		    } else {
		    	delegateExecution.setVariable("isEquipmentIncluded", false);
		    }
	  }	
	  
	  
	  //check accommodation
	  public void checkAccommodation(DelegateExecution delegateExecution) {

		  int arriveDate = Integer.parseInt(delegateExecution.getVariables().get("arriveDate").toString().replaceAll("-", ""));
		  int returnDate = Integer.parseInt(delegateExecution.getVariables().get("returnDate").toString().replaceAll("-", ""));
		  
		  DestinationDB destinationInfo = getDestinationInfo(delegateExecution.getVariables().get("destination").toString());
		  
		  int openDate = Integer.parseInt(destinationInfo.getArrivalTime().replaceAll("-", ""));
		  int closeDate = Integer.parseInt(destinationInfo.getReturnTime().replaceAll("-", ""));
		
		  if(openDate<=arriveDate && returnDate<=closeDate  && arriveDate<=returnDate){
			  System.out.println("THE DESTINATION IS AVALIABLE");
			  delegateExecution.setVariable("isAccommodationAvailable", true); 
		  }
		  else {
			  delegateExecution.setVariable("isAccommodationAvailable", false);	
			  
			  // Get all process variables
			  Map<String, Object> variables = delegateExecution.getVariables();
			  
			  String mailContent = "Name: "+ variables.get("firstName") + " " + variables.get("lastName") + " \r\n";
			  mailContent = mailContent + "Birth Date: "+ variables.get("birthDate") + "\r\n";
			  mailContent = mailContent + "Destination: "+ variables.get("destination") + "\r\n";
			  mailContent = mailContent + "Arrive Date: "+ variables.get("arriveDate") + "\r\n";
			  mailContent = mailContent + "Return Date: "+ variables.get("returnDate") + "\r\n";
			  
			  mailContent = "Sorry, the accommodation on the date you selected is not available.  \r\n\r\n" + mailContent;
			  mailContent = mailContent + "\r\n\r\nFrog Travel";
			  
			  delegateExecution.setVariable("AccomMail", mailContent);  
			  
		  }
		  
	  } 
		  	  
	  
	  //check transportation
	  public void checkTransfer(DelegateExecution delegateExecution) {	  
		  System.out.println("Transfer");	
		 
		  DestinationDB destinationInfo = getDestinationInfo(delegateExecution.getVariables().get("destination").toString());
		  if(destinationInfo.isTransferIncluded() == true) {
			  System.out.println("Transfer to this plcase is available");
			  delegateExecution.setVariable("isTransferAvaliable", true);
		  }
		  else {
			  delegateExecution.setVariable("isTransferAvaliable", false);
			  delegateExecution.setVariable("everythingAvailable", false);
		  }			 
	  }
	  
	  //check catering
	  public void checkCatering(DelegateExecution delegateExecution) {	  
			 System.out.println("Catering");
			 
			  DestinationDB destinationInfo = getDestinationInfo(delegateExecution.getVariables().get("destination").toString());
			  if(destinationInfo.isCateringIncluded() == true) {
				  System.out.println("Catering in this plcase is available");
				  delegateExecution.setVariable("isCateringAvaliable", true);
			  }
			  else {
				  delegateExecution.setVariable("isCateringAvaliable", false);
				  delegateExecution.setVariable("everythingAvailable", false);
			  }			 
	  }
	  
	  //check instruction
	  public void checkInstruction(DelegateExecution delegateExecution) {	  
			 System.out.println("Instruction");	
			 
			  DestinationDB destinationInfo = getDestinationInfo(delegateExecution.getVariables().get("destination").toString());
			  if(destinationInfo.isInstructionIncluded() == true) {
				  System.out.println("Ski instruction in this plcase is available");
				  delegateExecution.setVariable("isInstructionAvaliable", true);
			  }
			  else {
				  delegateExecution.setVariable("isInstructionAvaliable", false);
				  delegateExecution.setVariable("everythingAvailable", false);
			  }			 
	  }
	  
	  
	  //check equipment
	  //send message to Ski Oasis
	  public void checkEquipment(DelegateExecution delegateExecution) throws Exception {
			// build HTTP request with all variables as parameters
		    CloseableHttpClient client = HttpClients.createDefault();
			HttpPut httpput = new HttpPut("http://10.67.14.195:8081/skioasis-0.1.0-SNAPSHOT/register-order");
			//RequestBuilder requestBuilder = RequestBuilder.get().setUri("https://10.67.27.25:8081/skioasis-0.1.0-SNAPSHOT/register-order");
					
			//construct a JSON object to fulfill the requested format from SkiOasis
			JsonObject jsonObj = null;
			jsonObj = constructJsonObject(jsonObj, delegateExecution);
							 
			//assign to string entity
			StringEntity entity = new StringEntity(jsonObj.toString());

			//requestBuilder.setHeader("Content-type", "application/json");
			//requestBuilder.setEntity(entity);
			httpput.setHeader("Content-type", "application/json");
			httpput.setEntity(entity);
			//HttpUriRequest request = httpput.build();

			// execute request
			//HttpUriRequest request = requestBuilder.build();
			System.out.println("---------NOW PRINT REQUEST CONTENT---------");
			//System.out.println(request);
			HttpResponse response = client.execute(httpput);
			// log debug information
			//System.out.println(request.getURI());
			System.out.println(response.getStatusLine());	
			client.close();
		}	
	  
	  
	  @SuppressWarnings("unchecked")
	  public JsonObject constructJsonObject(JsonObject jsonObj, DelegateExecution delegateExecution) {
			   //get equipment type index from the hash map
			   HashMap<Integer, String> skiEquipmenList = orderObj.getEquipmentList();
			   JsonArrayBuilder equiplist = Json.createArrayBuilder();
			  	   
			   System.out.println("THE SIZE OF EQUIPMENTLIST IS: " + skiEquipmenList.size());
			   for(int i=0; i<skiEquipmenList.size(); i++) {
				   if(skiEquipmenList.get(i) == "true") {
					  equiplist.add((String) Integer.toString(i)); 
				   }
			   }
			   JsonArray jsonarraytest = equiplist.build();
			   System.out.println("---------- PRINT TESTLIST --------- " + jsonarraytest);
			   
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
	  
	  //rejection email
	  public void writeREmail(DelegateExecution delegateExecution) {
		  // Get all process variables
		  Map<String, Object> variables = delegateExecution.getVariables();
		  
		  String mailContent = "Name: "+ variables.get("firstName") + " " + variables.get("lastName") + " \r\n";
		  mailContent = mailContent + "Birth Date: "+ variables.get("birthDate") + "\r\n";
		  mailContent = mailContent + "Destination: "+ variables.get("destination") + "\r\n";
		  mailContent = mailContent + "Arrive Date: "+ variables.get("arriveDate") + "\r\n";
		  mailContent = mailContent + "Return Date: "+ variables.get("returnDate") + "\r\n";
		  
		  mailContent = mailContent + "\r\nOptions:\r\n";
		  if ((Boolean) variables.get("isTransferIncluded") == true) 
			  mailContent = mailContent + "   - Transportation \r\n";
		  if ((Boolean) variables.get("isCateringIncluded") == true) 
			  mailContent = mailContent + "   - Catering \r\n";
		  if ((Boolean) variables.get("isInstructionIncluded") == true) 
			  mailContent = mailContent + "   - Instruction \r\n";
			
		  mailContent = mailContent + "\r\nEquipments (provided by Ski Oasis):\r\n";
		  if ((Boolean) variables.get("atomicVantage") == true) 
			  mailContent = mailContent + "   - Atomic Vantage X 75 CTI Skis with XT 12 Bindings 2018 \r\n";
		  if ((Boolean) variables.get("blizzardQuattro") == true) 
			  mailContent = mailContent + "   - Blizzard Quattro 8.4 Ti Skis with Xcell 12 Bindings 2018 \r\n";
		  if ((Boolean) variables.get("rossignolAlias") == true) 
			  mailContent = mailContent + "   - Rossignol Alias 120 Ski Boots 2018 \r\n";
		  if ((Boolean) variables.get("nordicaNMove") == true) 
			  mailContent = mailContent + "   - Nordica N-Move 100 Ski-Boots 2017 \r\n";
		  if ((Boolean) variables.get("giroDiscordHelmet") == true) 
			  mailContent = mailContent + "   - Giro Discord Helmet \r\n";
		  if ((Boolean) variables.get("motocrossMtbATVt") == true) 
			  mailContent = mailContent + "   - Motocross Mtb ATV \r\n";
		  
		  mailContent = "Sorry, the options on the date you selected is not available.  \r\n\r\n" + mailContent;
		  mailContent = mailContent + "\r\n\r\nFrog Travel";
		  
		  delegateExecution.setVariable("rejectionMail", mailContent);  
		  
	  }
	  
}