package com.data;

import java.io.Serializable;

public class GroupMember implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String groupMemberNumber;
	private String groupId;
	private String lastName;
	private String middleName;
	private String firstName;
	private String staffNumber;
	private String gender;
	private String dob;
	private int age;
	private String telephone;
	private String email;
	private String occupationClass;
	private int occupationClassId;
	private double salary;
	private double sumAssured;
	@SuppressWarnings("unused")
	private Double name;
	
	public String getName(){
		return getLastName()+" "+getFirstName();	
				}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getStaffNumber() {
		return staffNumber;
	}
	public void setStaffNumber(String staffNumber) {
		this.staffNumber = staffNumber;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getOccupationClassId() {
		return occupationClassId;
	}
	public void setOccupationClassId(int occupationClassId) {
		this.occupationClassId = occupationClassId;
	}
	public String getOccupationClass() {
		return occupationClass;
	}
	public void setOccupationClass(String occupationClass) {
		this.occupationClass = occupationClass;
	}
	public String getGroupMemberNumber() {
		return groupMemberNumber;
	}
	public void setGroupMemberNumber(String groupMemberNumber) {
		this.groupMemberNumber = groupMemberNumber;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public double getSumAssured() {
		return sumAssured;
	}
	public void setSumAssured(double mult) {
		this.sumAssured = salary*mult;
	}
	
	
}
