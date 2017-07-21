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

public class SkiOasisServlet extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.getWriter().append("Served attttttttttt: ").append(request.getContextPath());
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
	
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		
		System.out.println("test");

		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
				while ((line = reader.readLine()) != null)
			    jb.append(line);
		} catch (Exception e) { /*report an error*/ }
		
		try {
			JSONObject jsonObject = new JSONObject(jb.toString());
			
			System.out.println(jsonObject.get("messageName"));
			System.out.println(jsonObject.get("processIdInstance"));
			
			runtimeService.createMessageCorrelation(jsonObject.get("messageName").toString())
			.processInstanceId(jsonObject.get("processIdInstance").toString()).correlateWithResult();
			
			
		} catch (JSONException e) {
			System.out.println("test3");
		    // crash and burn
		    throw new IOException("Error parsing JSON request string");
		}
		
		
		
	}
}