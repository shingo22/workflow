package org.camunda.bpm.frogtravel.service;


import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.engine.delegate.DelegateExecution;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.faces.validator.Validator;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ValidatorFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collection;

import org.camunda.bpm.frogtravel.persistence.*;

@Stateless
@Named
public class DestinationServiceBean {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Resource
	private ValidatorFactory validatorFactory;
	
	@Resource
	private Validator validator;

	//return destinations from database
	private Collection<DestinationDB> destinations; 
	
	public Collection<DestinationDB> getAll() {
		return entityManager.createQuery("FROM DestinationDB", DestinationDB.class).getResultList();
	}
	
	public Collection<DestinationDB> getDestinations(){
		if(destinations == null)
			destinations = this.getAll();
		return destinations;
	}
	//////////////////////////////////////
		
	
	// Initialize database with some records.
	public static List<DestinationDB> destinations(){
		DestinationDB d1= new DestinationDB("2017-09-15", "2017-09-20", true, true, true, true);
		DestinationDB d2= new DestinationDB("2017-09-06", "2017-09-25", true, true, false, true);
		DestinationDB d3= new DestinationDB("2017-09-06", "2017-09-25", true, true, true, false);
		List<DestinationDB> destinations = new ArrayList<DestinationDB>();
		destinations.add(d1);
		destinations.add(d2);
		destinations.add(d3);	
		return destinations;	
	}

	
}