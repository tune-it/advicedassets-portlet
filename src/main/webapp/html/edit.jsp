<%@page import="java.util.*"%>
<%@page import="javax.portlet.*"%>
<%@page import="com.liferay.portal.model.Portlet"%>
<%@page import="com.liferay.portal.service.PortalPreferencesLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.PortletLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.Constants" %> 
<%@page import="com.liferay.portal.kernel.util.*"%>
<%@page import="com.liferay.portal.kernel.language.LanguageUtil" %> 
<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="com.tuneit.advicedassets.AdvicedAssetsConfig" %>

<%@taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@taglib uri="http://java.sun.com/portlet" prefix="portlet" %> 
<%@taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %> 

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
		prefs = PortletPreferencesFactoryUtil.getPortletPreferences(request, id);
		break;
	}
}

if(prefs != null) {
	maxNumber = PrefsParamUtil.getInteger(
			prefs, request, AdvicedAssetsConfig.maxAssetsListSize, AdvicedAssetsConfig.maxAssetsListSizeDefault);
	minFavor = PrefsParamUtil.getInteger(
			prefs, request, AdvicedAssetsConfig.minFavor, AdvicedAssetsConfig.minFavorDefault);
	maxBannersNumber = PrefsParamUtil.getInteger(
			prefs, request, AdvicedAssetsConfig.maxBannersNumber, AdvicedAssetsConfig.maxBannersNumberDefault);
	tag = prefs.getValue(AdvicedAssetsConfig.bannersTagRegexp, AdvicedAssetsConfig.bannersTagRegexpDefault);
	repeatBanner = PrefsParamUtil.getBoolean(
			prefs, request, AdvicedAssetsConfig.repeatBanner, AdvicedAssetsConfig.repeatBannerDefault);
}
%>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" 
method="post" name="<portlet:namespace />fm"> 

<input name="<portlet:namespace /><%=Constants.CMD%>" 
type="hidden" value="<%=Constants.UPDATE%>" /> 

<p><%= LanguageUtil.get(pageContext, "edit.maxassetslistsize") %></p>
<p>
<input 
type="number" 
name='<portlet:namespace/><%=AdvicedAssetsConfig.maxAssetsListSize %>' 
min="0" 
max="50"
value="<%=maxNumber%>"/>
</p>
<br />

<p><%= LanguageUtil.get(pageContext, "edit.maxbannersnumber") %></p>
<p>
<input 
type="number" 
name='<portlet:namespace/><%=AdvicedAssetsConfig.maxBannersNumber %>' 
min="0" 
max="50"
value="<%=maxBannersNumber%>"/>
</p>
<br />

<p><%= LanguageUtil.get(pageContext, "edit.minfavor") %></p>
<p>
<input 
type="number" 
name='<portlet:namespace/><%=AdvicedAssetsConfig.minFavor %>' 
min="0" 
max="50"
value="<%=minFavor%>"/>
</p>
<br />

<p><%= LanguageUtil.get(pageContext, "edit.bannerstagregexp") %></p>
<p>
<input 
name='<portlet:namespace/><%=AdvicedAssetsConfig.bannersTagRegexp %>' 
value="<%=tag %>"/>
</p>
<br/>

<p><%= LanguageUtil.get(pageContext, "edit.repeatbanner") %></p>
<p>
<input 
type="checkbox" 
name='<portlet:namespace/><%=AdvicedAssetsConfig.repeatBanner %>' <%if(repeatBanner) {%>checked<%}%> />
</p>
<br />

<input 
type="button" 
value="<%= LanguageUtil.get(pageContext, "edit.save") %>" 
onClick="submitForm(document.<portlet:namespace />fm);" /> 
</form>