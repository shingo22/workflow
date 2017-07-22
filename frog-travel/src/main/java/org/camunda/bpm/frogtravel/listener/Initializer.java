package org.camunda.bpm.frogtravel.listener;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
 
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.camunda.bpm.frogtravel.persistence.DestinationDB;
import org.camunda.bpm.frogtravel.persistence.OrderEntity;
import org.camunda.bpm.frogtravel.service.OrderServiceBean;

import javax.ejb.EJB;


@WebListener
public class Initializer implements ServletContextListener {
    private static Log logger = LogFactory.getLog(Initializer.class);
 
	// Inject the entity manager
    @EJB
	private OrderServiceBean orderService;
    
    public void contextDestroyed(ServletContextEvent ev) {
        // nothing to do..
    }
 
    public void contextInitialized(ServletContextEvent ev) {
        ServletContext context = ev.getServletContext();
        logger.info("WebApp START!!");
        context.log("+*+*+*+*+*+*+START+*+*+*+*+*+*+");
        
        orderService.persistInitialData();
        
        
    }
}