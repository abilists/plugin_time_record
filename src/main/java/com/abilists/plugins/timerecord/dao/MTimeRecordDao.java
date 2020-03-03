package com.abilists.plugins.timerecord.dao;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface MTimeRecordDao {

	public int istTimeRecord(Map<String, Object> map) throws SQLException;
	public int udtTimeRecord(Map<String, Object> map) throws SQLException;
	public int dltTimeRecord(Map<String, Object> map) throws SQLException;

}