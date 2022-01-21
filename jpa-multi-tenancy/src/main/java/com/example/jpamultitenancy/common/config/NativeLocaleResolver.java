package com.example.jpamultitenancy.common.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import static com.example.jpamultitenancy.common.constant.Constants.BOTTOM_LINE;
import static com.example.jpamultitenancy.common.constant.Constants.LANG;

/**
 * @ClassName: NativeLocaleResolver @Author: amy @Description:
 *             NativeLocaleResolver @Date: 2021/7/2 @Version: 1.0
 */
@Component("localeResolver")
public class NativeLocaleResolver implements LocaleResolver {

	@Override
	public Locale resolveLocale(HttpServletRequest httpServletRequest) {
		String language = httpServletRequest.getParameter(LANG);
		Locale locale = Locale.getDefault();
		if (StringUtils.isNotEmpty(language)) {
			String[] split = language.split(BOTTOM_LINE);
			locale = new Locale(split[0], split[1]);
		}
		return locale;
	}

	@Override
	public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Locale locale) {
	}
}
