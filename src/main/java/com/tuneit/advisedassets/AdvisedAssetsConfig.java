package com.tuneit.advisedassets;

import com.liferay.portal.kernel.portlet.BaseJSPSettingsConfigurationAction;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.servlet.http.HttpServletRequest;

public class AdvisedAssetsConfig extends BaseJSPSettingsConfigurationAction {

    @Override
    public String getJspPath(HttpServletRequest request) {
        return "/html/edit.jsp";
    }

    public void processAction(PortletConfig config, ActionRequest actionRequest,
                              ActionResponse actionResponse) throws Exception {

        String portletResource = ParamUtil
                .getString(actionRequest, "portletResource");
        PortletPreferences prefs = PortletPreferencesFactoryUtil
                .getPortletSetup(actionRequest, portletResource);

        String str;
        int n;

        str = actionRequest.getParameter(AdvisedAssetsConfig.maxAssetsListSize);
        try {
            n = Integer.parseInt(str);
            if (n >= 0) {
                prefs.setValue(AdvisedAssetsConfig.maxAssetsListSize, str);
            }
        } catch (NumberFormatException ex) {
        }

        str = actionRequest.getParameter(AdvisedAssetsConfig.minFavor);
        try {
            n = Integer.parseInt(str);
            if (n >= 0) {
                prefs.setValue(AdvisedAssetsConfig.minFavor, str);
            }
        } catch (NumberFormatException ex) {
        }


        str = actionRequest.getParameter(AdvisedAssetsConfig.maxBannersNumber);
        try {
            n = Integer.parseInt(str);
            if (n >= 0) {
                prefs.setValue(AdvisedAssetsConfig.maxBannersNumber, str);
            }
        } catch (NumberFormatException ex) {
        }

        str = actionRequest.getParameter(AdvisedAssetsConfig.bannersTagRegexp);
        prefs.setValue(AdvisedAssetsConfig.bannersTagRegexp, str);

        str = actionRequest.getParameter(AdvisedAssetsConfig.repeatBanner);
        if (str != null && str.matches("on")) {
            prefs.setValue(AdvisedAssetsConfig.repeatBanner, Boolean.TRUE.toString());
        } else {
            prefs.setValue(AdvisedAssetsConfig.repeatBanner, Boolean.FALSE.toString());
        }

        prefs.store();
        SessionMessages.add(actionRequest, config.getPortletName() + ".doConfigure");
    }

//    public String render(PortletConfig config, RenderRequest request,
//                         RenderResponse response) throws Exception {
//        return "/html/edit.jsp";
//    }

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
