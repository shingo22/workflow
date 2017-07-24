package org.camunda.bpm.frogtravel.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.util.json.HTTP;
import org.camunda.bpm.engine.impl.util.json.JSONException;
import org.camunda.bpm.engine.impl.util.json.JSONObject;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;

public class SkiOasisServlet extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.getWriter().append("Frog Travel");
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{

		Enumeration<String> headerNames = request.getHeaderNames();

        if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                        System.out.println("Header: " + request.getHeader(headerNames.nextElement()));
                }
        }
		
        
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
				while ((line = reader.readLine()) != null)
			    jb.append(line);
		} catch (Exception e) { /*report an error*/ }
		
		System.out.println(jb.toString());
		
		try {
			
			try{
				Thread.sleep(1000);
				
				ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
				RuntimeService runtimeService = processEngine.getRuntimeService();
				
				RepositoryService repositoryService = processEngine.getRepositoryService();
				// query for latest process definition with given name
			    ProcessDefinition myProcessDefinition =
			        repositoryService.createProcessDefinitionQuery()
			            .processDefinitionName("Frog Travel Agency")
			            .latestVersion()
			            .singleResult();
				
				List<ProcessInstance> processInstances =
				        runtimeService.createProcessInstanceQuery()
				            .processDefinitionId(myProcessDefinition.getId())
				            .active() // we only want the unsuspended process instances
				            .list();
				if (processInstances.size() != 0){
					for (ProcessInstance a: processInstances){
						System.out.println("instance: " + a.getProcessInstanceId().toString());
					}
				} else {
					System.out.println("no instances exists");
				}
				
				
				JSONObject jsonObject = new JSONObject(jb.toString());
				byte[] idBytes = jsonObject.get("processInstanceId").toString().getBytes("UTF-8");
				String instanceId = new String(idBytes, "UTF-8");
			
				// the case when equipment is not available
				if (jsonObject.get("messageName").toString().equals("SkiOasisAvailability")){
					if(jsonObject.get("isAvailable").equals("false")){
						runtimeService.setVariable(instanceId, "everythingAvailable", false);
					}
					runtimeService.setVariable(instanceId, "processInstanceId_ski", jsonObject.get("processInstanceId_ski").toString());
				}
			
			runtimeService.createMessageCorrelation(jsonObject.get("messageName").toString())
			.processInstanceId(instanceId).correlateWithResult();
			}catch( Exception e){System.out.println("error2"); e.printStackTrace();}
			
		} catch (JSONException e) {
		    // crash and burn
		    throw new IOException("Error parsing JSON request string");
		}
		
		
		
	}
}