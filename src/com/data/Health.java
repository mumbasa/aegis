package com.data;



public class Health extends Customer {

	private static final long serialVersionUID = 1L;
	private boolean verified;
	private boolean smoker;
	private boolean drinker;
	private boolean slippedDisk;
	private String slippedDdiskCondition;
	private boolean respiratory;
	private String respiratoryCondition;
	private boolean bowels;
	private String bowelsCondition;
	private boolean heart;
	private String heartCondition;
	private int bpRate;
	private boolean STD;
	private String STDCondition;
	private boolean special;
	private String specialCondition;
	private boolean disabled;
	private String disabledConditon;
	private String drinkingFrequency;
	private String smokingFrequency;
	private boolean familySickness;
	private boolean beenAdmitted;
	private String admissionReason;
	private String familySicknessName;
	private String policyNumber;
	public boolean isSmoker() {
		return smoker;
	}
	public void setSmoker(String smoker) {
		this.smoker = smoker.equals("Y")?true:false;
	}
	public boolean isDrinker() {
		return drinker;
	}
	public void setDrinker(String drinker) {
		this.drinker = drinker.equals("Y")?true:false;
	}
	public boolean isSlippedDisk() {
		return slippedDisk;
	}
	public void setSlippedDisk(String slippedDisk) {
		this.slippedDisk = slippedDisk.equals("Y")?true:false;
	}
	public String getSlippedDdiskCondition() {
		return slippedDdiskCondition;
	}
	public void setSlippedDdiskCondition(String slippedDdiskCondition) {
		this.slippedDdiskCondition = slippedDdiskCondition;
	}
	public boolean isRespiratory() {
		return respiratory;
	}
	public void setRespiratory(String respiratory) {
		this.respiratory = respiratory.equals("Y")?true:false;
	}
	public String getRespiratoryCondition() {
		return respiratoryCondition;
	}
	public void setRespiratoryCondition(String respiratoryCondition) {
		this.respiratoryCondition = respiratoryCondition;
	}
	public boolean isBowels() {
		return bowels;
	}
	public void setBowels(String bowels) {
		
		this.bowels = bowels.equals("Y")?true:false;
	}
	public String getBowelsCondition() {
		return bowelsCondition;
	}
	public void setBowelsCondition(String bowelsCondition) {
		this.bowelsCondition = bowelsCondition;
	}
	public boolean isHeart() {
		return heart;
	}
	public void setHeart(String heart) {
		this.heart = heart.equals("Y")?true:false;
	}
	public String getHeartCondition() {
		return heartCondition;
	}
	public void setHeartCondition(String heartCondition) {
		this.heartCondition = heartCondition;
	}
	public int getBpRate() {
		return bpRate;
	}
	public void setBpRate(int bpRate) {
		this.bpRate = bpRate;
	}
	public boolean isSTD() {
		return STD;
	}
	public void setSTD(String sTD) {
		STD = sTD.equals("Y")?true:false;
	}
	public String getSTDCondition() {
		return STDCondition;
	}
	public void setSTDCondition(String sTDCondition) {
		STDCondition = sTDCondition;
	}
	public boolean isSpecial() {
		return special;
	}
	public void setSpecial(String special) {
		this.special = special.equals("Y")?true:false;
	}
	public String getSpecialCondition() {
		return specialCondition;
	}
	public void setSpecialCondition(String specialCondition) {
		this.specialCondition = specialCondition;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled.equals("Y")?true:false;
	}
	public String getDisabledConditon() {
		return disabledConditon;
	}
	public void setDisabledConditon(String disabledConditon) {
		this.disabledConditon = disabledConditon;
	}
	public String getDrinkingFrequency() {
		return drinkingFrequency;
	}
	public void setDrinkingFrequency(String drinkingFrequency) {
		this.drinkingFrequency = drinkingFrequency;
	}
	public String getSmokingFrequency() {
		return smokingFrequency;
	}
	public void setSmokingFrequency(String smokingFrequency) {
		this.smokingFrequency = smokingFrequency;
	}
	public boolean isFamilySickness() {
		return familySickness;
	}
	public void setFamilySickness(String familySickness) {
		this.familySickness = familySickness.equals("Y")?true:false;
	}
	public boolean isBeenAdmitted() {
		return beenAdmitted;
	}
	public void setBeenAdmitted(String beenAdmitted) {
		this.beenAdmitted = beenAdmitted.equals("Y")?true:false;
	}
	public String getAdmissionReason() {
		return admissionReason;
	}
	public void setAdmissionReason(String admissionReason) {
		this.admissionReason = admissionReason;
	}
	public String getFamilySicknessName() {
		return familySicknessName;
	}
	public void setFamilySicknessName(String familySicknessName) {
		this.familySicknessName = familySicknessName;
	}
	public String getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(String verified) {
		this.verified = verified.equals("Y")?true:false;
	}

	
}
