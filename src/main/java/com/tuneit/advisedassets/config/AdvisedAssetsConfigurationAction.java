package com.tuneit.advisedassets.config;

import aQute.bnd.annotation.metatype.Configurable;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import java.util.Map;

@Component(
    configurationPid = "com.tuneit.advisedassets.config.AdvisedAssetsConfiguration",
    configurationPolicy = ConfigurationPolicy.OPTIONAL,
    immediate = true,
    property = {
        "javax.portlet.name=advisedassets"
    },
    service = ConfigurationAction.class
)
public class AdvisedAssetsConfigurationAction extends DefaultConfigurationAction {

    private volatile AdvisedAssetsConfiguration configuration;

    @Activate
    @Modified
    protected void activate(Map<Object, Object> properties) {
        configuration = Configurable.createConfigurable(AdvisedAssetsConfiguration.class, properties);
    }

    @Override
    public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
        int maxAssetsListSizeValue = ParamUtil.getInteger(actionRequest, maxAssetsListSize);
        int maxBannersNumberValue = ParamUtil.getInteger(actionRequest, maxBannersNumber);
        int minFavorValue = ParamUtil.getInteger(actionRequest, minFavor);
        String bannersTagRegexpValue = ParamUtil.getString(actionRequest, bannersTagRegexp);
        boolean repeatBannerValue = ParamUtil.getBoolean(actionRequest, repeatBanner);

        if (maxAssetsListSizeValue >= 0) {
            setPreference(actionRequest, maxAssetsListSize, Integer.toString(maxAssetsListSizeValue));
        } else {
            setPreference(actionRequest, maxAssetsListSize, maxAssetsListSizeDefault.toString());
        }

        if (maxBannersNumberValue >= 0) {
            setPreference(actionRequest, maxBannersNumber, Integer.toString(maxBannersNumberValue));
        } else {
            setPreference(actionRequest, maxBannersNumber, maxBannersNumberDefault.toString());
        }

        if (minFavorValue >= 0) {
            setPreference(actionRequest, minFavor, Integer.toString(minFavorValue));
        } else {
            setPreference(actionRequest, minFavor, minFavorDefault.toString());
        }

        setPreference(actionRequest, bannersTagRegexp, bannersTagRegexpValue);
        setPreference(actionRequest, repeatBanner, Boolean.toString(repeatBannerValue));

        super.processAction(portletConfig, actionRequest, actionResponse);
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
