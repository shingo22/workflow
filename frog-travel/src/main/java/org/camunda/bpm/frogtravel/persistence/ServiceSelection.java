package org.camunda.bpm.frogtravel.persistence;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class ServiceSelection implements java.io.Serializable{
	
	private static final long serialVersionUID = 5885128018789230805L;
	
	private String destination;
	private Date arriveTime;
	private Date returnTime;
	private boolean isAccomodationIncluded;
	private boolean isCateringIncluded;
	private boolean isEquipIncluded;
	private boolean isInstructionIncluded;


	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public boolean isAccomodationIncluded() {
		return isAccomodationIncluded;
	}

	public void setAccomodationIncluded(boolean isAccomodationIncluded) {
		this.isAccomodationIncluded = isAccomodationIncluded;
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
