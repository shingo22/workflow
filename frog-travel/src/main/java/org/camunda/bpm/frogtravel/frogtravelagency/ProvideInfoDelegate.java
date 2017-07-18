package org.camunda.bpm.frogtravel.frogtravelagency;

import java.util.logging.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class ProvideInfoDelegate implements JavaDelegate {

  private final static Logger LOGGER = Logger.getLogger("START-PROCESS");

  public void execute(DelegateExecution execution) throws Exception {
    LOGGER.info("Now a customer has visited our website...");
  }

}