package com.abilists.plugins.timerecord.service;

import java.util.List;

import com.abilists.plugins.timerecord.bean.model.TimeRecordModel;
import com.abilists.plugins.timerecord.bean.para.IstTimeRecordPara;
import com.abilists.plugins.timerecord.bean.para.SltTimeRecordPara;
import com.abilists.plugins.timerecord.bean.para.UdtTimeRecordPara;

public interface TimeRecordService {

	public List<TimeRecordModel> sltTimeRecordList(SltTimeRecordPara sltSurveyPara) throws Exception;
	public TimeRecordModel sltTimeRecord(SltTimeRecordPara sltSurveyPara) throws Exception;
	public boolean istTimeRecord(IstTimeRecordPara istSurveyPara) throws IndexOutOfBoundsException, Exception;
	public boolean udtTimeRecord(UdtTimeRecordPara udtSurveyPara) throws Exception;
	public boolean dltTimeRecord(UdtTimeRecordPara udtSurveyPara) throws Exception;

}
