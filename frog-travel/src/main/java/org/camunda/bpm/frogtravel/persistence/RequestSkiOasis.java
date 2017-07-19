package org.camunda.bpm.frogtravel.persistence;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.TimeZone;

import javax.persistence.Entity;

@Entity
public class RequestSkiOasis implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5885128018789230805L;
	private String destination;
	private Date arriveTime;
	private Date returnTime;
	
	private boolean isTransferIncluded;
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

	public boolean getIsTransferIncluded() {
		return isTransferIncluded;
	}

	public void setAccomodationIncluded(boolean isTransferIncluded) {
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
