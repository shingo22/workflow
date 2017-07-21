package org.camunda.bpm.frogtravel.servlet;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.impl.util.json.JSONObject;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SkiOasisSend {
	
	
	public void sendToSkiOasis(DelegateExecution delegateExecution) throws Exception {
		// build HTTP request with all variables as parameters
		HttpClient client = HttpClients.createDefault();
//		HttpPut put = new HttpPut("http://requestb.in/<your-request-bin>");
		RequestBuilder requestBuilder = RequestBuilder.get().setUri("http://requestb.in/<your-request-bin>");
		
		requestBuilder.addParameter("ProcessInstanceId", delegateExecution.getProcessInstanceId());
		requestBuilder.addParameter("TEST MESSAGE", "TEST FOR API");
		
//		JSONObject jsonObj = ;
		
		// execute request
		HttpUriRequest request = requestBuilder.build();
		HttpResponse response = client.execute(request);
		// log debug information
		System.out.println(request.getURI());
		System.out.println(response.getStatusLine());
	}

}