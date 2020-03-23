 package com.abilists.plugins.timerecord.admin.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abilists.bean.AdminAbilistsModel;
import com.abilists.core.controller.AbstractBaseController;
import com.abilists.plugins.timerecord.admin.service.AdminTimeRecordService;
import com.abilists.plugins.timerecord.bean.PluginsModel;

import base.bean.para.CommonPara;

@Controller
@RequestMapping("/admin/plugins/timerecord")
public class TimeRecordAdminController extends AbstractBaseController {

	final Logger logger = LoggerFactory.getLogger(TimeRecordAdminController.class);

	@Autowired
	private AdminTimeRecordService adminTimeRecordService;
    @Autowired
    ServletContext servletContext;

	@RequestMapping(value = {"/", "", "index"})
	public String pluginList(@Valid CommonPara commonPara, HttpSession session, 
			ModelMap model, HttpServletRequest request) throws Exception {

		AdminAbilistsModel adminAbilistsModel = new AdminAbilistsModel();
		adminAbilistsModel.setNavi("plugins");
		adminAbilistsModel.setMenu("timerecord");
		
		PluginsModel pluginsModel = new PluginsModel();

		// Set user id
		this.handleSessionInfo(request.getSession(), commonPara);

		// Set Paging list
		commonPara.setUserId(null);
		int intSum = adminTimeRecordService.sltTimeRecordSum(commonPara);
		adminAbilistsModel.setPaging(adminTimeRecordService.makePaging(commonPara, intSum));

		// Get time recorded list
		pluginsModel.setTimeRecordList(adminTimeRecordService.sltTimeRecordList(commonPara));

		model.addAttribute("model", adminAbilistsModel);
		model.addAttribute("plugins", pluginsModel);

		return "apps/timerecord/admin/index";
	}

}