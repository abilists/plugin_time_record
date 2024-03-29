package com.abilists.plugins.timerecord.bean.model;

import java.io.Serializable;
import java.util.Date;

import base.bean.model.BasicModel;

public class TimeRecordModel extends BasicModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private long utrNo;
	private String utrKind;
	private Date utrWorkDay;
	private Date utrStartTime;
	private Date utrEndTime;
	private String utrWorkHour;
	private String utrBreakHour;
	private String utrComment;
	private String utrPermit;
	private String utrWeekday;
	private String utrLunchFlag;
	private String utrStatus;
	private String userId;
	private Date insertTime;
	private Date updateTime;

	public long getUtrNo() {
		return utrNo;
	}
	public void setUtrNo(long utrNo) {
		this.utrNo = utrNo;
	}
	public String getUtrKind() {
		return utrKind;
	}
	public void setUtrKind(String utrKind) {
		this.utrKind = utrKind;
	}
	public Date getUtrWorkDay() {
		return utrWorkDay;
	}
	public void setUtrWorkDay(Date utrWorkDay) {
		this.utrWorkDay = utrWorkDay;
	}
	public Date getUtrStartTime() {
		return utrStartTime;
	}
	public void setUtrStartTime(Date utrStartTime) {
		this.utrStartTime = utrStartTime;
	}
	public Date getUtrEndTime() {
		return utrEndTime;
	}
	public void setUtrEndTime(Date utrEndTime) {
		this.utrEndTime = utrEndTime;
	}
	public String getUtrWorkHour() {
		return utrWorkHour;
	}
	public void setUtrWorkHour(String utrWorkHour) {
		this.utrWorkHour = utrWorkHour;
	}
	public String getUtrBreakHour() {
		return utrBreakHour;
	}
	public void setUtrBreakHour(String utrBreakHour) {
		this.utrBreakHour = utrBreakHour;
	}
	public String getUtrComment() {
		return utrComment;
	}
	public void setUtrComment(String utrComment) {
		this.utrComment = utrComment;
	}
	public String getUtrPermit() {
		return utrPermit;
	}
	public void setUtrPermit(String utrPermit) {
		this.utrPermit = utrPermit;
	}
	public String getUtrWeekday() {
		return utrWeekday;
	}
	public void setUtrWeekday(String utrWeekday) {
		this.utrWeekday = utrWeekday;
	}
	public String getUtrLunchFlag() {
		return utrLunchFlag;
	}
	public void setUtrLunchFlag(String utrLunchFlag) {
		this.utrLunchFlag = utrLunchFlag;
	}
	public String getUtrStatus() {
		return utrStatus;
	}
	public void setUtrStatus(String utrStatus) {
		this.utrStatus = utrStatus;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}