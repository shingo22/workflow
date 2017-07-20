package org.camunda.bpm.frogtravel.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DestinationDB implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int destinationId;
	
	private String arrivalTime;
	private String returnTime;
	private boolean isTransferIncluded;
	private boolean isCateringIncluded;
	private boolean isInstructionIncluded;
	private boolean isAccomodationIncluded;
	

	public DestinationDB(){

	}
	
	public DestinationDB(String arrivalTime, String returnTime, boolean isTransferIncluded, boolean isCateringIncluded, boolean isInstructionIncluded, boolean isAccomodationIncluded){
		this.arrivalTime = arrivalTime;
		this.returnTime = returnTime;
		this.isTransferIncluded = isTransferIncluded;
		this.isCateringIncluded = isCateringIncluded;
		this.isInstructionIncluded = isInstructionIncluded;
		this.isAccomodationIncluded = isAccomodationIncluded;
	}
	
	//Initialize Destination TBL
	private void initializeDestination(){
		
	} 
	
	static{
		DestinationDB des1 = new DestinationDB("2017-09-06", "2017-09-25", true, true, false, true);
		DestinationDB des2 = new DestinationDB("2017-09-10", "2017-09-20", false, true, false, true);
	}
	
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
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
	public boolean isInstructionIncluded() {
		return isInstructionIncluded;
	}
	public void setInstructionIncluded(boolean isInstructionIncluded) {
		this.isInstructionIncluded = isInstructionIncluded;
	}
	
	
}