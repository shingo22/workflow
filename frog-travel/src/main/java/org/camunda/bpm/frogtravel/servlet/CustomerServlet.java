package org.camunda.bpm.frogtravel.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.util.json.HTTP;
import org.camunda.bpm.engine.impl.util.json.JSONException;
import org.camunda.bpm.engine.impl.util.json.JSONObject;
import org.camunda.bpm.engine.runtime.ProcessInstance;

public class CustomerServlet extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String processId = request.getParameter("id");
		
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		
		try {
			runtimeService.createMessageCorrelation("ReceivePayment")
			.processInstanceId(processId).correlateWithResult();
			
			response.getWriter().append("Your order is confirmed.");
		}
		catch (Exception e){ response.getWriter().append("Error Occurred."); }
		
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
	
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		RuntimeService runtimeService = processEngine.getRuntimeService();

		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
				while ((line = reader.readLine()) != null)
			    jb.append(line);
		} catch (Exception e) { /*report an error*/ }
		
		try {
			JSONObject jsonObject = new JSONObject(jb.toString());
			
			runtimeService.createMessageCorrelation(jsonObject.get("messageName").toString())
			.processInstanceId(jsonObject.get("processInstanceId").toString()).correlateWithResult();
			
			
		} catch (JSONException e) {
			System.out.println("test3");
		    // crash and burn
		    throw new IOException("Error parsing JSON request string");
		}
		
		
		
	}
}