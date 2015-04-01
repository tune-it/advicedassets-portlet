package org.geoshark.advicedassets;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

public class AdvicedAssetsConfig implements ConfigurationAction {

	public void processAction(PortletConfig config, ActionRequest actionRequest,
			ActionResponse actionResponse) throws Exception {
		
		  String portletResource = ParamUtil
				  .getString(actionRequest, "portletResource"); 
		  PortletPreferences prefs = PortletPreferencesFactoryUtil
				  .getPortletSetup(actionRequest, portletResource);
		  
		  String str;
		  int n;
		  
		  str = actionRequest.getParameter(AdvicedAssetsConfig.maxAssetsListSize);
		  try {
			  n = Integer.parseInt(str);
			  if(n >= 0) {
				  prefs.setValue(AdvicedAssetsConfig.maxAssetsListSize, str);
			  }
		  }
		  catch(NumberFormatException ex) { }
		  
		  str = actionRequest.getParameter(AdvicedAssetsConfig.minFavor);
		  try {
			  n = Integer.parseInt(str);
			  if(n >= 0) {
				  prefs.setValue(AdvicedAssetsConfig.minFavor, str);
			  }
		  }
		  catch(NumberFormatException ex) { }
		  
		  
		  
		  str = actionRequest.getParameter(AdvicedAssetsConfig.maxBannersNumber);
		  try {
			  n = Integer.parseInt(str);
			  if(n >= 0) {
				  prefs.setValue(AdvicedAssetsConfig.maxBannersNumber, str);
			  }
		  }
		  catch(NumberFormatException ex) { }
		  
		  str = actionRequest.getParameter(AdvicedAssetsConfig.bannersTagRegexp);
		  prefs.setValue(AdvicedAssetsConfig.bannersTagRegexp, str);
		  
		  str = actionRequest.getParameter(AdvicedAssetsConfig.repeatBanner);
		  if(str != null && str.matches("on")) {
			  prefs.setValue(AdvicedAssetsConfig.repeatBanner, Boolean.TRUE.toString());
		  } else {
			  prefs.setValue(AdvicedAssetsConfig.repeatBanner, Boolean.FALSE.toString());
		  }
		  
		  prefs.store();
		  SessionMessages.add(actionRequest, config.getPortletName() + ".doConfigure");
	}

	public String render(PortletConfig config, RenderRequest request,
			RenderResponse respone) throws Exception {
		return "/html/edit.jsp";
	}
	
	public static final String maxAssetsListSize = "maxAssetsListSize";
	public static final String maxBannersNumber = "maxBannersNumber";
	public static final String bannersTagRegexp = "bannersTagRegexp";
	public static final String repeatBanner = "repeatBanner";
	public static final String minFavor = "minFavor";
	
	public static final Integer maxAssetsListSizeDefault = 5;
	public static final Integer maxBannersNumberDefault = 1;
	public static final Integer minFavorDefault = 1;
	public static final String bannersTagRegexpDefault = "banner";
	public static final Boolean repeatBannerDefault = false;
}
