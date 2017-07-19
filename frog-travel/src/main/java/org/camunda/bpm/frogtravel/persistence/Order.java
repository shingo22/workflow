package org.camunda.bpm.frogtravel.persistence;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.TimeZone;

import javax.persistence.Entity;

@Entity
public class Order implements java.io.Serializable {
	private static final long serialVersionUID = 5885128018789230805L;
	
	private long id;
	
	private Customer customer;
	private PaymentInfo paymentInfo;
	private ServiceSelection serviceSelection;
	
	
	public ServiceSelection getServiceSelection() {
		return serviceSelection;
	}
	public void setServiceSelection(ServiceSelection serviceSelection) {
		this.serviceSelection = serviceSelection;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}
	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

}
