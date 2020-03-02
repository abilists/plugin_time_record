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

import base.bean.para.CommonPara;
import io.utility.letter.DateUtility;

@Service
public class TimeRecordServiceImpl extends AbilistsAbstractService implements TimeRecordService {

	final Logger logger = LoggerFactory.getLogger(TimeRecordServiceImpl.class);

	@Autowired
	private SqlSession sAbilistsDao;

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	private boolean udtTimeRecord(Map<String, Object> map) throws Exception {

		int intResult = 0;

		try {
			intResult = mAbilistsDao.getMapper(MTimeRecordDao.class).udtMTimeRecord(map);
		} catch (Exception e) {
			logger.error("Exception error", e);
		}

		if(intResult < 1) {
			logger.error("udtTimeRecord error, userId={}", map.get("userId"));
			return false;
		}

		return true;
	}

	@Override
	public List<TimeRecordModel> sltTimeRecordList(CommonPara commonPara) throws Exception {
		List<TimeRecordModel> timeRecordList = null;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", commonPara.getUserId());

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
		
		if(sltTimeRecordPara.getUtrWorkDay() == null) {
			String strToday = DateUtility.formatDate(DateUtility.getNowTime(), "yyyy-MM-dd");
			logger.info("3 workDay=" + strToday  + ", userId = " + sltTimeRecordPara.getUserId());
			map.put("utrWorkDay", strToday);
			logger.info("1 workDay=" + sltTimeRecordPara.getUtrWorkDay());
		} else {
			map.put("utrWorkDay", sltTimeRecordPara.getUtrWorkDay());
			logger.info("2 workDay=" + sltTimeRecordPara.getUtrWorkDay());
		}

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

		SltTimeRecordPara sltTimeRecordPara = new SltTimeRecordPara();
		sltTimeRecordPara.setUserId(istTimeRecordPara.getUserId());
		sltTimeRecordPara.setUtrWorkDay(istTimeRecordPara.getUtrWorkDay());

		TimeRecordModel timeRecord = this.sltTimeRecord(sltTimeRecordPara);
		if(timeRecord != null) {
			logger.warn("There is a time record in today. workday=" + DateUtility.formatDate(timeRecord.getUtrWorkDay(), "yyyy-MM-dd"));
			UdtTimeRecordPara udtTimeRecordPara = new UdtTimeRecordPara();
			udtTimeRecordPara.setUtrKind("0");
			udtTimeRecordPara.setUserId(istTimeRecordPara.getUserId());
			this.udtStartTime(udtTimeRecordPara);

			return true;
		}

		map.put("userId", istTimeRecordPara.getUserId());
		map.put("utrKind", "0");
		String strToday = DateUtility.formatDate(DateUtility.getNowTime(), "yyyy-MM-dd");
		map.put("utrWorkDay", strToday);

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

	@Override
	public boolean udtStartTime(UdtTimeRecordPara udtTimeRecordPara) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", udtTimeRecordPara.getUserId());
		map.put("utrKind", udtTimeRecordPara.getUtrKind());
		String strToday = DateUtility.formatDate(DateUtility.getNowTime(), "yyyy-MM-dd");
		map.put("utrWorkDay", strToday);		
		map.put("utrStartTime", DateUtility.getNowTime());

		return this.udtTimeRecord(map);
	}

	@Override
	public boolean udtEndTime(UdtTimeRecordPara udtTimeRecordPara) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", udtTimeRecordPara.getUserId());
		String strToday = DateUtility.formatDate(DateUtility.getNowTime(), "yyyy-MM-dd");
		map.put("utrWorkDay", strToday);
		map.put("utrEndTime", DateUtility.getNowTime());
		map.put("utrComment", udtTimeRecordPara.getUtrComment());
		map.put("utrStatus", "1");

		return this.udtTimeRecord(map);
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