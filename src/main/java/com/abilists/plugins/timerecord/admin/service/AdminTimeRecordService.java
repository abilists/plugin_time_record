package com.abilists.plugins.timerecord.admin.service;

import com.abilists.bean.admin.plugins.PluginBean;
import com.abilists.bean.para.admin.plugins.SltPluginPara;
import com.abilists.core.service.PagingService;

public interface AdminTimeRecordService extends PagingService {

	public PluginBean sltPlugin(SltPluginPara sltPluginPara) throws Exception;

}