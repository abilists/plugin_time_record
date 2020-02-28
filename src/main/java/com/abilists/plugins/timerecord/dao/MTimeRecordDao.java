package com.abilists.plugins.timerecord.dao;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface MTimeRecordDao {

	public int istMTimeRecord(Map<String, Object> map) throws SQLException;
	public int udtMTimeRecord(Map<String, Object> map) throws SQLException;
	public int dltMTimeRecord(Map<String, Object> map) throws SQLException;

}