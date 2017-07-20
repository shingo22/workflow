package org.camunda.bpm.frogtravel.persistence;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.TimeZone;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import java.io.Serializable;

@Entity
public class Order implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int orderId;
	
	private String firstName;
	private String lastName;
	private Date birthDate;
	private String email;	
	
	private String destination; 
	private String arriveTime;
	private String returnTime;
	
	private boolean isTransferIncluded;
	private boolean isCateringIncluded;
	private boolean isEquipIncluded;
	private boolean isInstructionIncluded;
	
	//payment information
	protected PaymentInfo paymentInfo;
	
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}
	public boolean isTransferIncluded() {
		return isTransferIncluded;
	}
	public void setTransferIncluded(boolean isTransferIncluded) {
		this.isTransferIncluded = isTransferIncluded;
	}
	public boolean isCateringIncluded() {
		return isCateringIncluded;
	}
	public void setCateringIncluded(boolean isCateringIncluded) {
		this.isCateringIncluded = isCateringIncluded;
	}
	public boolean isEquipIncluded() {
		return isEquipIncluded;
	}
	public void setEquipIncluded(boolean isEquipIncluded) {
		this.isEquipIncluded = isEquipIncluded;
	}
	public boolean isInstructionIncluded() {
		return isInstructionIncluded;
	}
	public void setInstructionIncluded(boolean isInstructionIncluded) {
		this.isInstructionIncluded = isInstructionIncluded;
	}
	
	//payment information
	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}
	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

}
