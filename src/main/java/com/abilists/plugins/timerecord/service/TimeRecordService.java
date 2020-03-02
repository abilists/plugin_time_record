package com.abilists.plugins.timerecord.service;

import java.util.List;

import com.abilists.plugins.timerecord.bean.model.TimeRecordModel;
import com.abilists.plugins.timerecord.bean.para.IstTimeRecordPara;
import com.abilists.plugins.timerecord.bean.para.SltTimeRecordPara;
import com.abilists.plugins.timerecord.bean.para.UdtTimeRecordPara;

import base.bean.para.CommonPara;

public interface TimeRecordService {

	public List<TimeRecordModel> sltTimeRecordList(CommonPara commonPara) throws Exception;
	public TimeRecordModel sltTimeRecord(SltTimeRecordPara sltTimeRecordPara) throws Exception;
	public boolean istTimeRecord(IstTimeRecordPara istTimeRecordPara) throws IndexOutOfBoundsException, Exception;

	public boolean udtStartTime(UdtTimeRecordPara udtTimeRecordPara) throws Exception;
	public boolean udtEndTime(UdtTimeRecordPara udtTimeRecordPara) throws Exception;
	
	public boolean dltTimeRecord(UdtTimeRecordPara udtSurveyPara) throws Exception;

}
