package com.abilists.plugins.timerecord.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.abilists.core.service.AbilistsAbstractService;
import com.abilists.plugins.timerecord.bean.model.TimeRecordModel;
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
	@Autowired
	private SqlSession mAbilistsBatchDao;
	@Autowired
    private Configuration configuration;

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	private boolean udtTimeRecord(Map<String, Object> map) throws Exception {

		int intResult = 0;

		try {
			intResult = mAbilistsDao.getMapper(MTimeRecordDao.class).udtTimeRecord(map);
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
	public int sltTimeRecordSum(CommonPara commonPara) throws Exception {
		int sum = 0;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", commonPara.getUserId());

		try {
			sum = sAbilistsDao.getMapper(STimeRecordDao.class).sltTimeRecordSum(map);
		} catch (Exception e) {
			logger.error("Exception error", e);
		}

		return sum;
	}

	@Override
	public List<TimeRecordModel> sltTimeRecordList(CommonPara commonPara) throws Exception {
		List<TimeRecordModel> timeRecordList = null;

		// Get now page
		int nowPage = commonPara.getNowPage();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", commonPara.getUserId());
		map.put("nowPage", (nowPage - 1) * configuration.getInt("paging.row.ten"));
		map.put("row", configuration.getInt("paging.row.ten"));

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
			// For insert first
			String strToday = DateUtility.formatDate(DateUtility.getNowTime(), "yyyy-MM-dd");
			map.put("utrWorkDay", strToday);
		} else {
			map.put("utrWorkDay", sltTimeRecordPara.getUtrWorkDay());
			map.put("utrNo", sltTimeRecordPara.getUtrNo());
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

	public TimeRecordModel sltTimeRecordTop(SltTimeRecordPara sltTimeRecordPara) throws Exception {
		TimeRecordModel timeRecord = null;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", sltTimeRecordPara.getUserId());

		try {
			sqlSessionSlaveFactory.setDataSource(getDispersionDb());
			timeRecord = sAbilistsDao.getMapper(STimeRecordDao.class).sltTimeRecordTop(map);

		} catch (Exception e) {
			logger.error("sltOptions Exception error", e);
		}

		return timeRecord;
	}

	@Transactional(rollbackFor = {IndexOutOfBoundsException.class, Exception.class}, propagation = Propagation.REQUIRES_NEW)
	@Override
	public boolean istStartTimeRecored(Map<String, Object> map) throws Exception {

		int intResult = 0;

		try {
			intResult = mAbilistsDao.getMapper(MTimeRecordDao.class).istTimeRecord(map);
		} catch (IndexOutOfBoundsException ie) {
			logger.error("IndexOutOfBoundsException error", ie);
			return false;
		} catch (Exception e) {
			logger.error("Exception error", e);
			return false;
		}
		
		if(intResult < 1) {
			logger.error("istServey error, userId={}", map.get("userId"));
			return false;
		}
	
		return true;
	}

	@Transactional(rollbackFor = {IndexOutOfBoundsException.class, Exception.class}, propagation = Propagation.REQUIRES_NEW)
	@Override
	public boolean istStartTime(CommonPara commonPara) throws Exception {
		// Check if there is the today data
		SltTimeRecordPara sltTimeRecordPara = new SltTimeRecordPara();
		sltTimeRecordPara.setUserId(commonPara.getUserId());
		TimeRecordModel timeRecord = this.sltTimeRecordTop(sltTimeRecordPara);

		// For the start time
		Date nowTime = DateUtility.getNowTime();
		// For the work day
		String strToday = DateUtility.formatDate(nowTime, "yyyy-MM-dd");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", commonPara.getUserId());
		if(timeRecord == null) {
			// Use this first time after installed
			map.put("utrKind", "0");
			map.put("utrWorkDay", strToday);
			map.put("utrStartTime", nowTime);
			mAbilistsBatchDao.getMapper(MTimeRecordDao.class).istTimeRecord(map);
		} else {
			String strResultDay = DateUtility.formatDate(timeRecord.getUtrWorkDay(), "yyyy-MM-dd");
			if(strToday.equals(strResultDay)) {
				logger.info("The start time is already recorded.");
				return true;
			} else {
				int diffDays = DateUtility.minusDateDays(nowTime, timeRecord.getUtrWorkDay());
				for(int i = diffDays - 1; 0 < i; i--) {
					String beforeDays = DateUtility.formatDate(DateUtility.getDayInMillisBefore(i), "yyyy-MM-dd");
					map.put("utrWorkDay", beforeDays);
					mAbilistsBatchDao.getMapper(MTimeRecordDao.class).istTimeRecord(map);
					logger.info("3, no data day=" + beforeDays);
				}
				map.put("utrKind", "0");
				// Can select it with [2020-03-12] so that [2020-03-12 00:00:00] is going to be saved into DB
				map.put("utrWorkDay", strToday);
				map.put("utrStartTime", nowTime);
				mAbilistsBatchDao.getMapper(MTimeRecordDao.class).istTimeRecord(map);
			}
		}

		return true;
	}

	@Transactional(rollbackFor = {IllegalArgumentException.class, Exception.class}, propagation = Propagation.REQUIRES_NEW)
	@Override
	public boolean udtEndTime(CommonPara commonPara) throws Exception {

		SltTimeRecordPara sltTimeRecordPara = new SltTimeRecordPara();
		sltTimeRecordPara.setUserId(commonPara.getUserId());

		TimeRecordModel timeRecord = this.sltTimeRecord(sltTimeRecordPara);
		if(timeRecord == null) {
			logger.error("There is no today starting time information. userId={}", commonPara.getUserId());
			return false;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Date endDate = DateUtility.getNowTime();
			// End date(when the last time you worked) - Start date
			long diff = endDate.getTime() - timeRecord.getUtrStartTime().getTime();
			String diffTime = DateUtility.getDurationBreakdown(diff);

			map.put("userId", commonPara.getUserId());
			String strToday = DateUtility.formatDate(endDate, "yyyy-MM-dd");
			map.put("utrWorkDay", strToday);
			// Insert the end time with now() in SQL if there is data on utrEndTime
			map.put("utrEndTime", DateUtility.getNowTime());
			map.put("utrWorkHour", diffTime);
			map.put("utrStatus", "1");

		} catch (Exception e) {
			logger.error("udtEndTime, Exception error", e);
			return false;
		}

		return this.udtTimeRecord(map);
	}

	@Transactional(rollbackFor = {IllegalArgumentException.class, Exception.class}, propagation = Propagation.REQUIRES_NEW)
	@Override
	public boolean udtTimeRecord(UdtTimeRecordPara udtTimeRecordPara) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			map.put("utrNo", udtTimeRecordPara.getUtrNo());
			map.put("userId", udtTimeRecordPara.getUserId());
			map.put("utrKind", udtTimeRecordPara.getUtrKind());
			map.put("utrWorkDay", udtTimeRecordPara.getUtrWorkDay());

			String [] strDay = udtTimeRecordPara.getUtrWorkDay().split("-");
			String [] strStartTime = udtTimeRecordPara.getUtrStartTime().split(":");	
			Date startDate = DateUtility.getDaySet(Integer.parseInt(strDay[0]), Integer.parseInt(strDay[1]), Integer.parseInt(strDay[2]), 
					Integer.parseInt(strStartTime[0]), Integer.parseInt(strStartTime[1]), Integer.parseInt(strStartTime[2]));

			String [] strEndTime = udtTimeRecordPara.getUtrEndTime().split(":");		
			Date endDate = DateUtility.getDaySet(Integer.parseInt(strDay[0]), Integer.parseInt(strDay[1]), Integer.parseInt(strDay[2]), 
					Integer.parseInt(strEndTime[0]), Integer.parseInt(strEndTime[1]), Integer.parseInt(strEndTime[2]));

			map.put("utrStartTime", startDate);
			map.put("utrEndTime", endDate);
			
			// End date(when the last time you worked) - Start date
			long diff = endDate.getTime() - startDate.getTime();
			String diffTime = DateUtility.getDurationBreakdown(diff);
			map.put("utrWorkHour", diffTime);
			map.put("utrComment", udtTimeRecordPara.getUtrComment());
			map.put("utrStatus", "1");
		} catch (Exception e) {
			logger.error("udtTimeRecord, Exception error", e);
			return false;
		}

		return this.udtTimeRecord(map);
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean dltTimeRecord(UdtTimeRecordPara udtSurveyPara) throws Exception {

		int intResult = 0;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", udtSurveyPara.getUserId());

		try {
			intResult = mAbilistsDao.getMapper(MTimeRecordDao.class).dltTimeRecord(map);
		} catch (Exception e) {
			logger.error("Exception error", e);
		}

		if(intResult < 1) {
			logger.error("dltTimeRecord error, userId={}", udtSurveyPara.getUserId());
			return false;
		}

		return true;
	}

}