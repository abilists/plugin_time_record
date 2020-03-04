package com.abilists.plugins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.abilists.core.service.AbilistsAbstractService;
import com.abilists.plugins.dao.MInitiativeDao;
import com.abilists.plugins.service.PluginService;

@Service
@Component
public class InitiativeServiceImpl extends AbilistsAbstractService implements PluginService {

	final Logger logger = LoggerFactory.getLogger(InitiativeServiceImpl.class);
	final String INITIATIVE_SQL = "/sqlMap/plugins/initiativeSql.txt";
	final String MY_DRIVER = "org.h2.Driver";
	final String MY_URL = "jdbc:h2:~/.abilists/h2db/v1.3/abilists;IGNORECASE=TRUE;DB_CLOSE_DELAY=10;MODE=MYSQL;AUTO_RECONNECT=TRUE;INIT=create schema if not exists abilists\\;SET SCHEMA abilists;AUTO_SERVER=TRUE";

	@Override
	public String createTables(String tableName) throws Exception {
		logger.info("====================================Start to create======================================");
		String strReadResult = null;
	    StringBuffer sbSql = new StringBuffer();

		try (InputStream is = this.getClass().getResourceAsStream(INITIATIVE_SQL)) {
			if(is == null) {
				logger.error("The initiative SQL file has not been loaded. please check the file path or format.");
				return "false";
			}
			BufferedReader bfReader = new BufferedReader(new InputStreamReader(is));
			while((strReadResult = bfReader.readLine()) != null) {
				sbSql.append(strReadResult);
			}
		} catch (IOException e) {
			logger.error("IOException.", e);
			return "false";
		}
		logger.info("====================================End to create========================================");

		Connection conn = null;
		PreparedStatement preparedStmt = null;
	    try {
	      Class.forName(MY_DRIVER);
	      conn = DriverManager.getConnection(MY_URL, "sa", "admin");
	      preparedStmt = conn.prepareStatement(sbSql.toString());
	      preparedStmt.execute();
	    }
	    catch (Exception e) {
	      logger.error("Exception", e);
	      return "false";
	    } finally {
	    	if(preparedStmt != null) {
	    		preparedStmt.close();
	    	}
	    	if(conn != null) {
	    		conn.close();
	    	}
		}
		return "true";
	}

	/**
	 * org.apache.ibatis.binding.BindingException: Type interface com.abilists.plugins.dao.MInitiativeDao is not known to the MapperRegistry.
	 * 
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public String createTables2(String tableName) throws Exception {

		int intResult = 0;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", tableName);

		try {

			MInitiativeDao mInitiativeDao = mAbilistsDao.getMapper(MInitiativeDao.class);
			intResult = mInitiativeDao.initiativeTables(map);
		} catch (Exception e) {
			logger.error("Exception error", e);
		}

		if(intResult < 1) {
			logger.error("createTables error, tableName={}", tableName);
			return "false";
		}

		return "true";
	}

	public String createTables3(String tableName) throws Exception {

		int intResult = 0;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", tableName);

		try {

			// InputStream isResource = new FileInputStream("masterPluginsMapH2.xml");

//	        String resource = "masterPluginsMapH2.xml";
//	        Reader reader = Resources.getResourceAsReader(resource);
//	        SqlSessionFactory sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder().build(reader);
//
//	        Configuration configuration = sqlSessionFactoryBuilder.getConfiguration();
//			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("sqlMap/plugins/mInitiativeSql.xml");
//			XMLMapperBuilder mapperParser = new XMLMapperBuilder(inputStream, configuration, "sqlMap/plugins/mInitiativeSql.xml", configuration.getSqlFragments());
//			mapperParser.parse();
//
////			SqlSession sqlSession = sqlSessionFactoryBuilder.openSession();
//			MInitiativeDao mInitiativeDao = mAbilistsDao.getMapper(MInitiativeDao.class);
//
//			if(mInitiativeDao == null) {
//				logger.info("mInitiativeDao is null");
//			}
//
//			intResult = mInitiativeDao.initiativeTables(map);
		} catch (Exception e) {
			logger.error("Exception error", e);
		}
		
		if(intResult < 1) {
			logger.error("createTables error, tableName={}", tableName);
			return "false";
		}

		return "true";
	}

	@Override
	public String dropTables(String arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}