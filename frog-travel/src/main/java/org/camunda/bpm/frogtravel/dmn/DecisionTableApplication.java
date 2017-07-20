package org.camunda.bpm.frogtravel.dmn;

import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.application.impl.ServletProcessApplication;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;


import org.camunda.bpm.frogtravel.persistence.OrderEntity;


//@ProcessApplication("Frog App DMN")
public class DecisionTableApplication extends ServletProcessApplication {
	
	
	 @PostDeploy
	 public void evaluateDecisionTable(ProcessEngine processEngine) {
		
		 DecisionService decisionService = processEngine.getDecisionService();

//		 VariableMap variables = Variables.createVariables()
//				 .putValue("isEquipmentIncluded", orderEntity.isEquipIncluded())
//				 .putValue("containCatering", orderEntity.isCateringIncluded())
//				 .putValue("containInstructor", orderEntity.isInstructionIncluded())
//				 .putValue("containTransportation", orderEntity.isTransferIncluded());
//	      
//	    DmnDecisionTableResult dishDecisionResult = decisionService.evaluateDecisionTableByKey("dish", variables);
//	    String desiredDish = dishDecisionResult.getSingleEntry();
//
//	    System.out.println("Desired dish: " + desiredDish);
	  }

}