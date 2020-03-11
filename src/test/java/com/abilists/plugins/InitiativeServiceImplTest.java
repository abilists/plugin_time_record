package com.abilists.plugins;

import java.util.HashMap;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.abilists.plugins.service.PluginService;

@WebAppConfiguration()
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@FixMethodOrder(MethodSorters.DEFAULT)
public class InitiativeServiceImplTest {

	@Autowired
	private InitiativeServiceImpl initiativeService;

	@Autowired
	private BasicDataSource mysqlMaster;

	@Test
	public void createTablesTest() throws Exception {

        HashMap<String, String> mapHash = new HashMap<>();
        mapHash.put(PluginService.TABLE_NAME, mysqlMaster.getUrl());
        mapHash.put(PluginService.DB_URL, mysqlMaster.getUrl());
        mapHash.put(PluginService.DB_USERNAME, mysqlMaster.getUsername());
        mapHash.put(PluginService.DB_PASSWORD, mysqlMaster.getPassword());
        
		initiativeService.createTables(mapHash);

	}

}