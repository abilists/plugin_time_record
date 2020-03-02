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

import io.utility.letter.DateUtility;

@Service
public class TimeRecordServiceImpl extends AbilistsAbstractService implements TimeRecordService {

	final Logger logger = LoggerFactory.getLogger(TimeRecordServiceImpl.class);

	@Autowired
	private SqlSession sAbilistsDao;

	@Override
	public List<TimeRecordModel> sltTimeRecordList(SltTimeRecordPara sltTimeRecordPara) throws Exception {
		List<TimeRecordModel> timeRecordList = null;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", sltTimeRecordPara.getUserId());

		try {
			sqlSessionSlaveFactory.setDataSource(getDispersionDb());
			timeRecordList = sAbilistsDao.getMapper(STimeRecordDao.class).sltTimeRecordList(map);

		} catch (Exception e) {
			logger.error("sltOptions Exception error", e);
		}

		return timeRecordList;
	}

	@Override
	public TimeRecordModel sltTimeRecord(SltTimeRecordPara sltTimeRecordPara) throws Exception {
		TimeRecordModel timeRecord = null;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", sltTimeRecordPara.getUserId());

		try {
			sqlSessionSlaveFactory.setDataSource(getDispersionDb());
			timeRecord = sAbilistsDao.getMapper(STimeRecordDao.class).sltTimeRecord(map);
			if(timeRecord == null) {
				logger.error("There is no user default options data. please insert your default data.");
			}
		} catch (Exception e) {
			logger.error("sltOptions Exception error", e);
		}

		return timeRecord;
	}

	@Transactional(rollbackFor = {IndexOutOfBoundsException.class, Exception.class}, propagation = Propagation.REQUIRES_NEW)
	@Override
	public boolean istTimeRecord(IstTimeRecordPara istTimeRecordPara) throws Exception {

		int intResult = 0;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", istTimeRecordPara.getUserId());
//		map.put("utrKind", "");
//		map.put("utrMonth", "");
//		map.put("utrDay", "");
//		map.put("utrStandardAmpm", "");
//		map.put("utrStandardHour", "");
//		map.put("utrStartTime", "");
//		map.put("utrEndTime", "");
//		map.put("utrWorkHour", "");
//		map.put("utrComment", "");

		logger.info("now >> " + DateUtility.getNowTime());

		try {
			intResult = mAbilistsDao.getMapper(MTimeRecordDao.class).istMTimeRecord(map);
		} catch (IndexOutOfBoundsException ie) {
			logger.error("IndexOutOfBoundsException error", ie);
			return false;
		} catch (Exception e) {
			logger.error("Exception error", e);
			return false;
		}
		
		if(intResult < 1) {
			logger.error("istServey error, userId={}", istTimeRecordPara.getUserId());
			return false;
		}
	
		return true;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean udtTimeRecord(UdtTimeRecordPara udtTimeRecordPara) throws Exception {

		int intResult = 0;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", udtTimeRecordPara.getUserId());

		try {
			intResult = mAbilistsDao.getMapper(MTimeRecordDao.class).udtMTimeRecord(map);
		} catch (Exception e) {
			logger.error("Exception error", e);
		}
	
		if(intResult < 1) {
			logger.error("udtServey error, userId={}", udtTimeRecordPara.getUserId());
			return false;
		}
	
		return true;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean dltTimeRecord(UdtTimeRecordPara udtSurveyPara) throws Exception {

		int intResult = 0;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", udtSurveyPara.getUserId());

		try {
			intResult = mAbilistsDao.getMapper(MTimeRecordDao.class).dltMTimeRecord(map);
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