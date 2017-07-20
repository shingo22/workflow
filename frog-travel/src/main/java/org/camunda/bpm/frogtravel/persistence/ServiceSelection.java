package org.camunda.bpm.frogtravel.persistence;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.TimeZone;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class ServiceSelection implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int serviceId;
	
	private String destination; 
	private String arriveTime;
	private String returnTime;
	
	private boolean isTransferIncluded;
	private boolean isCateringIncluded;
	private boolean isEquipIncluded;
	private boolean isInstructionIncluded;
	
	
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
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

}
