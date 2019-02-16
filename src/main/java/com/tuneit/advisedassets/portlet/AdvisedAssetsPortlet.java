package com.tuneit.advisedassets.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import org.osgi.service.component.annotations.Component;

import javax.portlet.Portlet;

/**
 * @author IvanKayukoff
 */
@Component(
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
public class AdvisedAssetsPortlet extends MVCPortlet { }
