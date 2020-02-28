package com.abilists.plugins.timerecord.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.abilists.plugins.timerecord.bean.model.TimeRecordModel;

@Repository
public interface STimeRecordDao {

	public List<TimeRecordModel> sltTimeRecordList(Map<String, Object> map) throws SQLException;
	public TimeRecordModel sltTimeRecord(Map<String, Object> map) throws SQLException;

}