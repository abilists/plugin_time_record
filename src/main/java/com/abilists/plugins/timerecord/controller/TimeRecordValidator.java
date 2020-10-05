package com.abilists.plugins.timerecord.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;

import com.abilists.bean.para.profile.IstUserCareerPara;
import com.abilists.bean.para.profile.UdtUserCareerPara;
import com.abilists.core.controller.BaseValidator;

public class TimeRecordValidator implements BaseValidator {

	final Logger logger = LoggerFactory.getLogger(TimeRecordValidator.class);

	@Override
	public <T> Map<String, String> validateBusiness(T para, MessageSource message, Locale locale) throws IOException {
		Map<String, String> mapErrorMessage = new HashMap<>();

		StringBuffer sbStart = new StringBuffer();
		StringBuffer sbEnd = new StringBuffer();
		if (para instanceof UdtUserCareerPara) {
			UdtUserCareerPara udtUserCareerPara = (UdtUserCareerPara)para;

			if(udtUserCareerPara.getUcPresent() == null) {
				String startDate = sbStart.append(udtUserCareerPara.getUcStartYear()).append(udtUserCareerPara.getUcStartMonth()).toString(); 
				String endDate = sbEnd.append(udtUserCareerPara.getUcEndYear()).append(udtUserCareerPara.getUcEndMonth()).toString();
				if(Integer.parseInt(startDate) >=  Integer.parseInt(endDate)) {
					mapErrorMessage.put("errorMessage", message.getMessage("parameter.error.message", null, locale));
					logger.warn("Invalidate your career date in udtUserCareer. startDate={}, endDate={}", startDate, endDate );
				}
			} else {
				// Still working here, so set the default end date forcedly.
				udtUserCareerPara.setUcEndYear("0");
				udtUserCareerPara.setUcEndMonth("0");
			}

		} else if(para instanceof IstUserCareerPara) {
			IstUserCareerPara istUserCareerPara = (IstUserCareerPara)para;

			if(istUserCareerPara.getUcPresent() == null) {
				String startDate = sbStart.append(istUserCareerPara.getUcStartYear()).append(istUserCareerPara.getUcStartMonth()).toString(); 
				String endDate = sbEnd.append(istUserCareerPara.getUcEndYear()).append(istUserCareerPara.getUcEndMonth()).toString();
				if(Integer.parseInt(startDate) >=  Integer.parseInt(endDate)) {
					mapErrorMessage.put("errorMessage", message.getMessage("parameter.error.message", null, locale));
					logger.warn("Invalidate your career date in istUserCareer. startDate={}, endDate={}", startDate, endDate );
				}
			} else {
				// Still working here, so set the default end date forcedly.
				istUserCareerPara.setUcEndYear("0");
				istUserCareerPara.setUcEndMonth("0");
			}

		}

		return mapErrorMessage;
	}

	@Override
	public <T> Map<String, String> validateBusiness(T para, HttpSession session) throws IOException {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

}
