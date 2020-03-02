package com.abilists.plugins.timerecord.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.abilists.bean.AbilistsModel;
import com.abilists.bean.model.works.UserReportsModel;
import com.abilists.bean.para.works.SltReportsPara;
import com.abilists.core.common.bean.CommonBean;
import com.abilists.core.controller.AbstractBaseController;
import com.abilists.core.controller.CommonAbilistsController;
import com.abilists.plugins.timerecord.bean.PluginsModel;
import com.abilists.plugins.timerecord.bean.model.TimeRecordModel;
import com.abilists.plugins.timerecord.bean.para.IstTimeRecordPara;
import com.abilists.plugins.timerecord.bean.para.SltTimeRecordPara;
import com.abilists.plugins.timerecord.bean.para.UdtTimeRecordPara;
import com.abilists.plugins.timerecord.service.TimeRecordService;

import base.bean.para.CommonPara;
import io.utility.security.TokenUtility;

@Controller
@RequestMapping("/plugins/timerecord")
public class TimeRecordController extends CommonAbilistsController {
	final Logger logger = LoggerFactory.getLogger(TimeRecordController.class);

	@Autowired
	private TimeRecordService timeRecordService;
	@Autowired
	private CommonBean commonBean;

	@RequestMapping(value = {"/", "", "index"})
	public String index(@Validated CommonPara commonPara, HttpServletRequest request, ModelMap model) throws Exception {
		AbilistsModel abilistsModel = new AbilistsModel();
		abilistsModel.setNavi("plugins");
		abilistsModel.setMenu("timeRecord");

		PluginsModel pluginsModel = new PluginsModel();

		// Set user id
		this.handleSessionInfo(request.getSession(), commonPara);

		pluginsModel.setTimeRecordList(timeRecordService.sltTimeRecordList(commonPara));

		model.addAttribute("model", abilistsModel);
		model.addAttribute("plugins", pluginsModel);

		return "apps/plugins/timerecord/index";
	}

	@RequestMapping(value = "/sltTimeRecordAjax")
	public @ResponseBody TimeRecordModel sltTimeRecordAjax(@RequestBody SltTimeRecordPara sltTimeRecordPara,
			HttpSession session) throws Exception {

		// Set user id
		this.handleSessionInfo(session, sltTimeRecordPara);

		// Get user Reports.
		TimeRecordModel timeRecord = timeRecordService.sltTimeRecord(sltTimeRecordPara);

		// Get key and token
		String token = TokenUtility.generateToken(TokenUtility.SHA_256);
		String key = this.makeKey(sltTimeRecordPara.getUserId(), AbstractBaseController.PREFIX_UDT_KEY);
		commonBean.addTokenExpireMap(key, token);

		// Set a token
		timeRecord.setToken(token);

		return timeRecord;
	}

	@RequestMapping(value = { "istTimeRecord" })
	public String istTimeRecord(@Valid IstTimeRecordPara istTimeRecordPara, BindingResult bindingResult, ModelMap model,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes)
			throws Exception {

		AbilistsModel abilistsModel = new AbilistsModel();
		abilistsModel.setNavi("plugins");
		abilistsModel.setMenu("timeRecord");

		// Set language in Locale.
		Locale locale = RequestContextUtils.getLocale(request);

		Map<String, String> mapErrorMessage = new HashMap<String, String>();
		// If it occurs errors, set the default value.
		if (bindingResult.hasErrors()) {
			logger.error("udtProjects - There are parameter errors.");
			response.setStatus(400);
			mapErrorMessage = this.handleErrorMessages(bindingResult.getAllErrors(), locale);
			model.addAttribute("mapErrorMessage",  mapErrorMessage);
			return "apps/errors/parameterErrors";
		}

		// Set user id
		this.handleSessionInfo(request.getSession(), istTimeRecordPara);

		// Execute the transaction
		if (!timeRecordService.istTimeRecord(istTimeRecordPara)) {
			logger.error("istTimeRecord - inserting is error. userId={}", istTimeRecordPara.getUserId());
			mapErrorMessage.put("errorMessage", message.getMessage("parameter.insert.error.message", null, locale));
			model.addAttribute("mapErrorMessage", mapErrorMessage);
			return "apps/errors/systemErrors";
		}

		return "redirect:/plugins/timerecord";
	}

	@RequestMapping(value = { "udtTimeRecord" })
	public String udtTimeRecord(@Valid UdtTimeRecordPara udtTimeRecordPara, BindingResult bindingResult, ModelMap model,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes)
			throws Exception {

		AbilistsModel abilistsModel = new AbilistsModel();
		abilistsModel.setNavi("plugins");
		abilistsModel.setMenu("timeRecord");

		// Set language in Locale.
		Locale locale = RequestContextUtils.getLocale(request);

		Map<String, String> mapErrorMessage = new HashMap<String, String>();
		// If it occurs errors, set the default value.
		if (bindingResult.hasErrors()) {
			logger.error("udtTimeRecord - There are parameter errors.");
			response.setStatus(400);
			mapErrorMessage = this.handleErrorMessages(bindingResult.getAllErrors(), locale);
			model.addAttribute("mapErrorMessage",  mapErrorMessage);
			return "apps/errors/parameterErrors";
		}

		// Set user id
		this.handleSessionInfo(request.getSession(), udtTimeRecordPara);

		// Execute the transaction
		if (!timeRecordService.udtEndTime(udtTimeRecordPara)) {
			logger.error("udtTimeRecord - updating is error. userId={}, utrNo={}", udtTimeRecordPara.getUserId(), udtTimeRecordPara.getUtrNo());
			mapErrorMessage.put("errorMessage", message.getMessage("parameter.update.error.message", null, locale));
			model.addAttribute("mapErrorMessage", mapErrorMessage);
			return "apps/errors/systemErrors";
		}

		return "redirect:/plugins/timerecord";
	}

    @RequestMapping(value = {"sltTimeRecordList"}, method=RequestMethod.GET)
	public String timeRecordList(@Validated SltTimeRecordPara sltTimeRecordPara, 
			BindingResult bindingResult, ModelMap model, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {

    	sltTimeRecordPara.setUserId("njoonk");

    	timeRecordService.sltTimeRecordList(sltTimeRecordPara);

	   	return "apps/plugins/timerecord/index";
	}

}