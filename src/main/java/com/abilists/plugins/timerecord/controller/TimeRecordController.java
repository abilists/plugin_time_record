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
import com.abilists.core.common.bean.CommonBean;
import com.abilists.core.controller.AbstractBaseController;
import com.abilists.core.controller.CommonAbilistsController;
import com.abilists.plugins.timerecord.bean.PluginsModel;
import com.abilists.plugins.timerecord.bean.model.TimeRecordModel;
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
	public String index(@Validated SltTimeRecordPara sltTimeRecordPara, HttpServletRequest request, ModelMap model) throws Exception {
		AbilistsModel abilistsModel = new AbilistsModel();
		abilistsModel.setNavi("plugins");
		abilistsModel.setMenu("timerecord");

		PluginsModel pluginsModel = new PluginsModel();

		// Set user id
		this.handleSessionInfo(request.getSession(), sltTimeRecordPara);

		// Get today infomation
		pluginsModel.setTimeRecord(timeRecordService.sltTimeRecord(sltTimeRecordPara));

		// Set Paging list
		int intSum = timeRecordService.sltTimeRecordSum(sltTimeRecordPara);
		abilistsModel.setPaging(timeRecordService.makePaging(sltTimeRecordPara, intSum));

		// Get time recorded list
		pluginsModel.setTimeRecordList(timeRecordService.sltTimeRecordList(sltTimeRecordPara));

		// Get key and token
		String token = TokenUtility.generateToken(TokenUtility.SHA_256);
		String key = this.makeKey(sltTimeRecordPara.getUserId(), AbstractBaseController.PREFIX_IST_KEY);
		commonBean.addTokenExpireMap(key, token);
		abilistsModel.setToken(token);

		model.addAttribute("model", abilistsModel);
		model.addAttribute("plugins", pluginsModel);

		return "apps/timerecord/index";
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

	@RequestMapping(value = { "startTime" })
	public String startTime(@Valid CommonPara commonPara, BindingResult bindingResult, ModelMap model,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes)
			throws Exception {

		AbilistsModel abilistsModel = new AbilistsModel();
		abilistsModel.setNavi("plugins");
		abilistsModel.setMenu("timerecord");

		// Set language in Locale.
		Locale locale = RequestContextUtils.getLocale(request);

		// Set user id
		this.handleSessionInfo(request.getSession(), commonPara);

		Map<String, String> mapErrorMessage = new HashMap<String, String>();
		// Validate token
		String key = this.makeKey(commonPara.getUserId(), AbstractBaseController.PREFIX_IST_KEY);
		if (!commonPara.getToken().equals(commonBean.getTokenExpireMap(key))) {
			logger.error("startTime - token is wrong. parameter token=" + commonPara.getToken() + ", server token=" + commonBean.getTokenExpireMap(key));
			mapErrorMessage.put("errorMessage", message.getMessage("parameter.error.token.message", null, locale));
			model.addAttribute("errorMessage", mapErrorMessage);
			return "apps/errors/parameterErrors";
		}

		// Execute the transaction
		if (!timeRecordService.istStartTime(commonPara)) {
			logger.error("istStartTime - inserting is error. userId={}", commonPara.getUserId());
			mapErrorMessage.put("errorMessage", message.getMessage("parameter.insert.error.message", null, locale));
			model.addAttribute("mapErrorMessage", mapErrorMessage);
			return "apps/errors/systemErrors";
		}

		return "redirect:/plugins/timerecord";
	}

	@RequestMapping(value = { "endTime" })
	public String endTime(@Valid CommonPara commonPara, BindingResult bindingResult, ModelMap model,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes)
			throws Exception {

		AbilistsModel abilistsModel = new AbilistsModel();
		abilistsModel.setNavi("plugins");
		abilistsModel.setMenu("timerecord");

		// Set language in Locale.
		Locale locale = RequestContextUtils.getLocale(request);

		// Set user id
		this.handleSessionInfo(request.getSession(), commonPara);

		Map<String, String> mapErrorMessage = new HashMap<String, String>();
		// Validate token
		String key = this.makeKey(commonPara.getUserId(), AbstractBaseController.PREFIX_IST_KEY);
		if (!commonPara.getToken().equals(commonBean.getTokenExpireMap(key))) {
			logger.error("udtTimeRecord - token is wrong. parameter token=" + commonPara.getToken() + ", server token=" + commonBean.getTokenExpireMap(key));
			mapErrorMessage.put("errorMessage", message.getMessage("parameter.error.token.message", null, locale));
			model.addAttribute("errorMessage", mapErrorMessage);
			return "apps/errors/parameterErrors";
		}

		// Execute the transaction
		if (!timeRecordService.udtEndTime(commonPara)) {
			logger.error("endTime - updating is error. userId={}", commonPara.getUserId());
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
		abilistsModel.setMenu("timerecord");

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

		// Validate token
		String key = this.makeKey(udtTimeRecordPara.getUserId(), AbstractBaseController.PREFIX_UDT_KEY);
		if (!udtTimeRecordPara.getToken().equals(commonBean.getTokenExpireMap(key))) {
			logger.error("udtTimeRecord - token is wrong. parameter token=" + udtTimeRecordPara.getToken() + ", server token=" + commonBean.getTokenExpireMap(key));
			mapErrorMessage.put("errorMessage", message.getMessage("parameter.error.token.message", null, locale));
			model.addAttribute("errorMessage", mapErrorMessage);
			return "apps/errors/parameterErrors";
		}

		// Execute the transaction
		if (!timeRecordService.udtTimeRecord(udtTimeRecordPara)) {
			logger.error("udtTimeRecord - updating is error. userId={}, utrNo={}", udtTimeRecordPara.getUserId(), udtTimeRecordPara.getUtrNo());
			mapErrorMessage.put("errorMessage", message.getMessage("parameter.update.error.message", null, locale));
			model.addAttribute("mapErrorMessage", mapErrorMessage);
			return "apps/errors/systemErrors";
		}

		return "redirect:/plugins/timerecord?nowPage=" + udtTimeRecordPara.getNowPage() + "&allCount=" + udtTimeRecordPara.getAllCount();
	}

    @RequestMapping(value = {"sltTimeRecordList"}, method=RequestMethod.GET)
	public String timeRecordList(@Validated SltTimeRecordPara sltTimeRecordPara, 
			BindingResult bindingResult, ModelMap model, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {

		// Set user id
		this.handleSessionInfo(request.getSession(), sltTimeRecordPara);

    	timeRecordService.sltTimeRecordList(sltTimeRecordPara);

	   	return "apps/timerecord/index";
	}

}