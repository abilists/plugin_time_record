package com.abilists.plugins.timerecord.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abilists.core.service.AbilistsAbstractService;
import com.abilists.plugins.timerecord.admin.service.AdminTimeRecordService;
import com.abilists.plugins.timerecord.bean.model.TimeRecordModel;
import com.abilists.plugins.timerecord.dao.STimeRecordDao;

import base.bean.para.CommonPara;

@Service
public class AdminTimeRecordServiceImpl extends AbilistsAbstractService implements AdminTimeRecordService  {

	final Logger logger = LoggerFactory.getLogger(AdminTimeRecordServiceImpl.class);

	@Autowired
	private SqlSession sAbilistsDao;
	@Autowired
    private Configuration configuration;

	@Override
	public List<TimeRecordModel> sltTimeRecordList(CommonPara commonPara) throws Exception {
		List<TimeRecordModel> timeRecordList = null;

		// Get now page
		int nowPage = commonPara.getNowPage();

		Map<String, Object> map = new HashMap<String, Object>();
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
	public int sltTimeRecordSum(CommonPara commonPara) throws Exception {
		int sum = 0;

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			sum = sAbilistsDao.getMapper(STimeRecordDao.class).sltTimeRecordSum(map);
		} catch (Exception e) {
			logger.error("Exception error", e);
		}

		return sum;
	}

}
