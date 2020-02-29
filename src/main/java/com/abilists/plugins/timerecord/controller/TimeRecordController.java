package com.abilists.plugins.timerecord.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abilists.bean.AbilistsModel;
import com.abilists.core.common.bean.ConfigBean;
import com.abilists.core.controller.CommonAbilistsController;
import com.abilists.core.utility.PathUtility;
import com.abilists.plugins.InitiativeServiceImpl;
import com.abilists.plugins.timerecord.bean.para.IstTimeRecordPara;
import com.abilists.plugins.timerecord.bean.para.SltTimeRecordPara;
import com.abilists.plugins.timerecord.service.TimeRecordService;

@SessionAttributes(value = {"configBean"})
@Controller
@RequestMapping("/plugins/timerecord")
public class TimeRecordController extends CommonAbilistsController {
	final Logger logger = LoggerFactory.getLogger(InitiativeServiceImpl.class);
	
	@Autowired
	private ConfigBean configBean;

	@Autowired
	private TimeRecordService timeRecordService;

	@RequestMapping(value = {"/", "", "index"})
	public String index(HttpServletRequest request, ModelMap model) throws Exception {
		AbilistsModel abilistsModel = new AbilistsModel();
		abilistsModel.setNavi("plugins");
		abilistsModel.setMenu("timeRecord");

		// Set base URL
		configBean.setBaseURL(PathUtility.getURLBase(request));
		configBean.setContextPath(request.getContextPath());
		model.addAttribute("configBean", configBean);

		model.addAttribute("model", abilistsModel);

		return "apps/plugins/timerecord/index";
	}

	@RequestMapping(value = { "istTimeRecord" })
	public String istTimeRecord(@Valid IstTimeRecordPara istTimeRecordPara, BindingResult bindingResult, ModelMap model,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes)
			throws Exception {

		// Set user id
		this.handleSessionInfo(request.getSession(), istTimeRecordPara);

    	timeRecordService.istTimeRecord(istTimeRecordPara);

		model.addAttribute("configBean", model);

	   	return "apps/plugins/timerecord/timeRecordList";
	}

    @RequestMapping(value = {"timeRecordList"}, method=RequestMethod.GET)
	public String timeRecordList(@Validated SltTimeRecordPara sltTimeRecordPara, 
			BindingResult bindingResult, ModelMap model, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {

    	timeRecordService.sltTimeRecordList(sltTimeRecordPara);

		model.addAttribute("configBean", model);

	   	return "apps/plugins/timerecord/timeRecordList";
	}

}