package com.abilists.plugins.timerecord.bean;

import java.util.List;

import com.abilists.plugins.timerecord.bean.model.TimeRecordModel;

import base.bean.model.CommonModel;

public class PluginsModel extends CommonModel {

	private TimeRecordModel timeRecord;
	private List<TimeRecordModel> timeRecordList;

	public TimeRecordModel getTimeRecord() {
		return timeRecord;
	}
	public void setTimeRecord(TimeRecordModel timeRecord) {
		this.timeRecord = timeRecord;
	}
	public List<TimeRecordModel> getTimeRecordList() {
		return timeRecordList;
	}
	public void setTimeRecordList(List<TimeRecordModel> timeRecordList) {
		this.timeRecordList = timeRecordList;
	}

}
