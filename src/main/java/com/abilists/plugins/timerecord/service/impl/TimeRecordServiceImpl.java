package com.abilists.plugins.timerecord.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.abilists.core.service.AbilistsAbstractService;
import com.abilists.plugins.timerecord.bean.model.TimeRecordModel;
import com.abilists.plugins.timerecord.bean.para.IstTimeRecordPara;
import com.abilists.plugins.timerecord.bean.para.SltTimeRecordPara;
import com.abilists.plugins.timerecord.bean.para.UdtTimeRecordPara;
import com.abilists.plugins.timerecord.dao.MTimeRecordDao;
import com.abilists.plugins.timerecord.dao.STimeRecordDao;
import com.abilists.plugins.timerecord.service.TimeRecordService;

@Service
public class TimeRecordServiceImpl extends AbilistsAbstractService implements TimeRecordService {

	final Logger logger = LoggerFactory.getLogger(TimeRecordServiceImpl.class);

	@Autowired
	private SqlSession sAbilistsDao;

	@Override
	public List<TimeRecordModel> sltServeyList(SltTimeRecordPara sltSurveyPara) throws Exception {
		List<TimeRecordModel> surveyList = null;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", sltSurveyPara.getUserId());

		try {
			sqlSessionSlaveFactory.setDataSource(getDispersionDb());
			surveyList = sAbilistsDao.getMapper(STimeRecordDao.class).sltPluginsMSurveyList(map);

		} catch (Exception e) {
			logger.error("sltOptions Exception error", e);
		}

		return surveyList;
	}

	@Override
	public TimeRecordModel sltServey(SltTimeRecordPara sltSurveyPara) throws Exception {
		TimeRecordModel survey = null;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", sltSurveyPara.getUserId());

		try {
			sqlSessionSlaveFactory.setDataSource(getDispersionDb());
			survey = sAbilistsDao.getMapper(STimeRecordDao.class).sltPluginsMSurvey(map);
			if(survey == null) {
				logger.error("There is no user default options data. please insert your default data.");
			}
		} catch (Exception e) {
			logger.error("sltOptions Exception error", e);
		}

		return survey;
	}

	@Transactional(rollbackFor = {IndexOutOfBoundsException.class, Exception.class}, propagation = Propagation.REQUIRES_NEW)
	@Override
	public boolean istServey(IstTimeRecordPara istSurveyPara) throws Exception {
		try {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", istSurveyPara.getUserId());

		} catch (IndexOutOfBoundsException ie) {
			logger.error("IndexOutOfBoundsException error", ie);
			return false;
		} catch (Exception e) {
			logger.error("Exception error", e);
			return false;
		}

		return true;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean udtServey(UdtTimeRecordPara udtSurveyPara) throws Exception {

		int intResult = 0;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", udtSurveyPara.getUserId());

		try {
			intResult = mAbilistsDao.getMapper(MTimeRecordDao.class).udtPluginsMSurvey(map);
		} catch (Exception e) {
			logger.error("Exception error", e);
		}
	
		if(intResult < 1) {
			logger.error("udtServey error, userId={}", udtSurveyPara.getUserId());
			return false;
		}
	
		return true;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean dltServey(UdtTimeRecordPara udtSurveyPara) throws Exception {

		int intResult = 0;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", udtSurveyPara.getUserId());

		try {
			intResult = mAbilistsDao.getMapper(MTimeRecordDao.class).udtPluginsMSurvey(map);
		} catch (Exception e) {
			logger.error("Exception error", e);
		}

		if(intResult < 1) {
			logger.error("dltServey error, userId={}", udtSurveyPara.getUserId());
			return false;
		}
	
		return true;
	}

}