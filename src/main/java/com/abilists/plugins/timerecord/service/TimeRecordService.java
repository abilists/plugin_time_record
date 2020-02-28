package com.abilists.plugins.timerecord.service;

import java.util.List;

import com.abilists.plugins.timerecord.bean.model.TimeRecordModel;
import com.abilists.plugins.timerecord.bean.para.IstTimeRecordPara;
import com.abilists.plugins.timerecord.bean.para.SltTimeRecordPara;
import com.abilists.plugins.timerecord.bean.para.UdtTimeRecordPara;

public interface TimeRecordService {

	public List<TimeRecordModel> sltServeyList(SltTimeRecordPara sltSurveyPara) throws Exception;
	public TimeRecordModel sltServey(SltTimeRecordPara sltSurveyPara) throws Exception;
	public boolean istServey(IstTimeRecordPara istSurveyPara) throws IndexOutOfBoundsException, Exception;
	public boolean udtServey(UdtTimeRecordPara udtSurveyPara) throws Exception;
	public boolean dltServey(UdtTimeRecordPara udtSurveyPara) throws Exception;

}
