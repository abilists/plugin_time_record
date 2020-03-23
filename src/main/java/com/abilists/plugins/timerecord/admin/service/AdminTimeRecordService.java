package com.abilists.plugins.timerecord.admin.service;

import java.util.List;

import com.abilists.core.service.PagingService;
import com.abilists.plugins.timerecord.bean.model.TimeRecordModel;

import base.bean.para.CommonPara;

public interface AdminTimeRecordService extends PagingService {

	public List<TimeRecordModel> sltTimeRecordList(CommonPara commonPara) throws Exception;
	public int sltTimeRecordSum(CommonPara commonPara) throws Exception;

}