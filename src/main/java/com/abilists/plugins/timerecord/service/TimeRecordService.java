package com.abilists.plugins.timerecord.service;

import java.util.List;
import java.util.Map;

import com.abilists.core.service.PagingService;
import com.abilists.plugins.timerecord.bean.model.TimeRecordModel;
import com.abilists.plugins.timerecord.bean.para.SltTimeRecordPara;
import com.abilists.plugins.timerecord.bean.para.UdtTimeRecordPara;

import base.bean.para.CommonPara;

public interface TimeRecordService extends PagingService {

	public List<TimeRecordModel> sltTimeRecordList(CommonPara commonPara) throws Exception;
	public int sltTimeRecordSum(CommonPara commonPara) throws Exception;
	public TimeRecordModel sltTimeRecord(SltTimeRecordPara sltTimeRecordPara) throws Exception;

	public boolean istStartTimeRecored(Map<String, Object> map) throws Exception;
	public boolean istStartTime(CommonPara commonPara) throws Exception;
	public boolean udtEndTime(CommonPara commonPara) throws Exception;
	public boolean udtTimeRecord(UdtTimeRecordPara udtTimeRecordPara) throws Exception;
	public boolean dltTimeRecord(UdtTimeRecordPara udtSurveyPara) throws Exception;

}
