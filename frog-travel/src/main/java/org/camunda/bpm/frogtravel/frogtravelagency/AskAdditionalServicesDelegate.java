package org.camunda.bpm.frogtravel.frogtravelagency;

import java.util.logging.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class AskAdditionalServicesDelegate implements JavaDelegate {
	 public void execute(DelegateExecution execution) throws Exception {
		 
		 /*execution.getProcessEngineServices()
	      .getRuntimeService()
	      .createMessageCorrelation("ReceiveAdditional")
	      .processInstanceBusinessKey("businessKey")
	      .correlate();*/
	 }
}
