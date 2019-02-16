package com.tuneit.advisedassets.portlet;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.tuneit.advisedassets.config.AdvisedAssetsConfiguration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author IvanKayukoff
 */
@Component(
    configurationPid = "com.tuneit.advisedassets.config.AdvisedAssetsConfiguration",
    immediate = true,
    property = {
        "com.liferay.portlet.display-category=category.sample",
        "com.liferay.portlet.instanceable=true",
        "javax.portlet.display-name=AdvisedAssets",
        "javax.portlet.name=advisedassets",
        "javax.portlet.security-role-ref=guest,power-user,user",
        "javax.portlet.init-param.template-path=/",
        "javax.portlet.init-param.view-template=/view.jsp",
        "javax.portlet.resource-bundle=content.Language",
        "com.liferay.portlet.css-class-wrapper=AdvisedAssets-portlet",
        "javax.portlet.supports.mime-type=text/html"
    },
    service = Portlet.class
)
public class AdvisedAssetsPortlet extends MVCPortlet {

    private volatile AdvisedAssetsConfiguration configuration;

    @Override
    public void doView(RenderRequest renderRequest,
                       RenderResponse renderResponse) throws IOException, PortletException {
        renderRequest.setAttribute(
            AdvisedAssetsConfiguration.class.getName(), configuration);
        super.doView(renderRequest, renderResponse);
    }

    @Activate
    @Modified
    protected void activate(Map<String, Object> properties) {
        configuration = ConfigurableUtil.createConfigurable(
            AdvisedAssetsConfiguration.class, properties);
    }

}
