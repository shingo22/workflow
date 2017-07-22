package org.camunda.bpm.frogtravel.service;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.camunda.bpm.engine.delegate.DelegateExecution;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Stateless
@Named
public class PaymentServiceBean {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	public void writeEmail(DelegateExecution delegateExecution) {
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
		if ((Boolean) variables.get("checkSki") == true) 
			mailContent = mailContent + "   - Ski \r\n";
		if ((Boolean) variables.get("checkSnowboard") == true) 
			mailContent = mailContent + "   - Snowboard \r\n";
		if ((Boolean) variables.get("checkVeryPopularSnowboard") == true) 
			mailContent = mailContent + "   - Very popular snowboard \r\n";
		if ((Boolean) variables.get("checkHelmet") == true) 
			mailContent = mailContent + "   - Helmet \r\n";
		if ((Boolean) variables.get("checkStick") == true) 
			mailContent = mailContent + "   - Stick \r\n";
		if ((Boolean) variables.get("checkSkiSuit") == true) 
			mailContent = mailContent + "   - Ski suit \r\n";

		
		// For an email asking for payment
		String askMail = "Please confirm your order and complete the payment.  \r\n \r\n" + mailContent;
		
		askMail = askMail + "\r\n\r\nIf you agree on the content, you can pay just by clicking on the url below. \r\n\r\n";
				
		askMail = askMail + "http://localhost:8080"
	    		+ "/frog-travel/customer?id=" + delegateExecution.getProcessInstanceId();
	    delegateExecution.setVariable("askForPaymentMail", askMail);
	        
	    // For a confirmation email
	    String confirmMail = "Your payment is completed. Thank you so much!!  \r\n\r\n" + mailContent;
	    delegateExecution.setVariable("confirmMail", confirmMail);
	}
	
	public void sendOrderToOasis(DelegateExecution delegateExecution) throws ClientProtocolException, IOException {
		JsonObject jsonObj = Json.createObjectBuilder()
						.add("messageName", "FinalOrder")
						.add("processInstanceId", delegateExecution.getProcessInstanceId().toString())
						.build();
		
        System.out.println("===== HTTP POST Start =====");
         
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://www.example.com");
         
        StringEntity entity = new StringEntity(jsonObj.toString());
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");
         
        //CloseableHttpResponse response = client.execute(httpPost);
        //assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();

        System.out.println("===== HTTP POST End =====");
	}
	
	public void sendPaymentToOasis(DelegateExecution delegateExecution) throws ClientProtocolException, IOException {
		JsonObject jsonObj = Json.createObjectBuilder()
						.add("messageName", "Payment")
						.add("processInstanceId", delegateExecution.getProcessInstanceId().toString())
						.build();
		
        System.out.println("===== HTTP POST Start =====");
         
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://www.example.com");
         
        StringEntity entity = new StringEntity(jsonObj.toString());
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");
         
        //CloseableHttpResponse response = client.execute(httpPost);
        //assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();

        System.out.println("===== HTTP POST End =====");
	}
}
