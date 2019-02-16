<%@ page import="java.util.*" %>
<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="com.liferay.portal.kernel.util.*" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.tuneit.advisedassets.AdvisedAssetsConfig" %>
<%@ page import="com.liferay.portal.kernel.service.PortletLocalServiceUtil" %>
<%@ page import="com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil" %>
<%@ page import="com.liferay.portal.kernel.model.Portlet" %>
<%@ page import="javax.portlet.PortletPreferences" %>
<%@ page import="com.liferay.portal.kernel.exception.PortalException" %>

<%--<%@ taglib prefix="portlet" uri="http://xmlns.jcp.org/portlet_3_0" %>--%>
<%--<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>--%>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>

<portlet:defineObjects />

<%

Integer maxNumber = null;
Integer minFavor = null;
Integer maxBannersNumber = null;
String tag = null;
boolean repeatBanner = false;

List<Portlet> portlets = PortletLocalServiceUtil.getPortlets();
PortletPreferences prefs = null;
for(Portlet portlet : portlets) {
	String id = portlet.getPortletId();
	if(id.matches(".*AdvicedAssets.*")) {
		// FIXME here used javax.portlet.PortletPreferences
        // instead of com.liferay.portal.kernel.model.PortletPreferences
		try {
			prefs = PortletPreferencesFactoryUtil.getPortletPreferences(request, id);
		} catch (PortalException e) {
			System.out.println("Caught PortalException in edit.jsp, line ~ 36");
		}
		break;
	}
}

if(prefs != null) {
	maxNumber = PrefsParamUtil.getInteger(
			prefs, request, AdvisedAssetsConfig.maxAssetsListSize, AdvisedAssetsConfig.maxAssetsListSizeDefault);
	minFavor = PrefsParamUtil.getInteger(
			prefs, request, AdvisedAssetsConfig.minFavor, AdvisedAssetsConfig.minFavorDefault);
	maxBannersNumber = PrefsParamUtil.getInteger(
			prefs, request, AdvisedAssetsConfig.maxBannersNumber, AdvisedAssetsConfig.maxBannersNumberDefault);
	tag = prefs.getValue(AdvisedAssetsConfig.bannersTagRegexp, AdvisedAssetsConfig.bannersTagRegexpDefault);
	repeatBanner = PrefsParamUtil.getBoolean(
			prefs, request, AdvisedAssetsConfig.repeatBanner, AdvisedAssetsConfig.repeatBannerDefault);
}
%>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />"
method="post" name="<portlet:namespace />fm">

<input name="<portlet:namespace /><%=Constants.CMD%>"
type="hidden" value="<%=Constants.UPDATE%>" />

<p><%= LanguageUtil.get(request, "edit.maxassetslistsize") %></p>
<p>
<input
type="number"
name='<portlet:namespace/><%=AdvisedAssetsConfig.maxAssetsListSize %>'
min="0"
max="50"
value="<%=maxNumber%>"/>
</p>
<br />

<p><%= LanguageUtil.get(request, "edit.maxbannersnumber") %></p>
<p>
<input
type="number"
name='<portlet:namespace/><%=AdvisedAssetsConfig.maxBannersNumber %>'
min="0"
max="50"
value="<%=maxBannersNumber%>"/>
</p>
<br />

<p><%= LanguageUtil.get(request, "edit.minfavor") %></p>
<p>
<input
type="number"
name='<portlet:namespace/><%=AdvisedAssetsConfig.minFavor %>'
min="0"
max="50"
value="<%=minFavor%>"/>
</p>
<br />

<p><%= LanguageUtil.get(request, "edit.bannerstagregexp") %></p>
<p>
<input
name='<portlet:namespace/><%=AdvisedAssetsConfig.bannersTagRegexp %>'
value="<%=tag %>"/>
</p>
<br/>

<p><%= LanguageUtil.get(request, "edit.repeatbanner") %></p>
<p>
<input
type="checkbox"
name='<portlet:namespace/><%=AdvisedAssetsConfig.repeatBanner %>' <%if(repeatBanner) {%>checked<%}%> />
</p>
<br />

<input
type="button"
value="<%= LanguageUtil.get(request, "edit.save") %>"
onClick="submitForm(document.<portlet:namespace />fm);" />
</form>