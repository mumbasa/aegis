package com.data;
import java.io.Serializable;

/**
 *
 * @author Bryanic
 */
public class Trustee extends Customer implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private String relationship;
    public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	public String getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}
	private String policyNumber;
    private String dateAdded;
    private int relationship_id;
    public int getRelationship_id() {
		return relationship_id;
	}
	public void setRelationship_id(int relationship_id) {
		this.relationship_id = relationship_id;
	}
	public String getRegion_id() {
		return region_id;
	}
	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}
	private String region_id;
}
