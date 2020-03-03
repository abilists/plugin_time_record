package com.abilists.plugins.timerecord.bean.para;

import java.util.Date;

import base.bean.para.CommonPara;

public class UdtTimeRecordPara extends CommonPara {

	private String utrNo;
	private String utrKind;
	private String utrWorkDay;
	private String utrStartTime;
	private String utrEndTime;
	private String utrComment;
	private String utrStatus;

	public String getUtrNo() {
		return utrNo;
	}
	public void setUtrNo(String utrNo) {
		this.utrNo = utrNo;
	}
	public String getUtrKind() {
		return utrKind;
	}
	public void setUtrKind(String utrKind) {
		this.utrKind = utrKind;
	}
	public String getUtrWorkDay() {
		return utrWorkDay;
	}
	public void setUtrWorkDay(String utrWorkDay) {
		this.utrWorkDay = utrWorkDay;
	}
	public String getUtrStartTime() {
		return utrStartTime;
	}
	public void setUtrStartTime(String utrStartTime) {
		this.utrStartTime = utrStartTime;
	}
	public String getUtrEndTime() {
		return utrEndTime;
	}
	public void setUtrEndTime(String utrEndTime) {
		this.utrEndTime = utrEndTime;
	}
	public String getUtrComment() {
		return utrComment;
	}
	public void setUtrComment(String utrComment) {
		this.utrComment = utrComment;
	}
	public String getUtrStatus() {
		return utrStatus;
	}
	public void setUtrStatus(String utrStatus) {
		this.utrStatus = utrStatus;
	}

}
