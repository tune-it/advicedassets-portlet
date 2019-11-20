<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.tuneit.advisedassets.config.AdvisedAssetsConfigurationAction" %>

<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>

<portlet:defineObjects />

<%
Integer maxNumber = Integer.parseInt(portletPreferences.getValue(AdvisedAssetsConfigurationAction.maxAssetsListSize,
		AdvisedAssetsConfigurationAction.maxBannersNumberDefault.toString()));
Integer minFavor = Integer.parseInt(portletPreferences.getValue(AdvisedAssetsConfigurationAction.minFavor,
		AdvisedAssetsConfigurationAction.minFavorDefault.toString()));
Integer maxBannersNumber = Integer.parseInt(portletPreferences.getValue(AdvisedAssetsConfigurationAction.maxBannersNumber,
		AdvisedAssetsConfigurationAction.maxBannersNumberDefault.toString()));
String tag = portletPreferences.getValue(AdvisedAssetsConfigurationAction.bannersTagRegexp, AdvisedAssetsConfigurationAction.bannersTagRegexpDefault);
boolean repeatBanner = Boolean.parseBoolean(portletPreferences.getValue(AdvisedAssetsConfigurationAction.repeatBanner,
		AdvisedAssetsConfigurationAction.repeatBannerDefault.toString()));
%>

<div style="padding: 20px;">
	<form action="<liferay-portlet:actionURL portletConfiguration="true" />"
		  method="post" name="<portlet:namespace />fm">

		<input name="<portlet:namespace /><%=Constants.CMD%>"
			   type="hidden" value="<%=Constants.UPDATE%>" />

		<p><%= LanguageUtil.get(request, "edit.maxassetslistsize") %></p>
		<p>
			<input  type="number"
					name='<portlet:namespace/><%=AdvisedAssetsConfigurationAction.maxAssetsListSize %>'
					min="0"
					max="50"
					value="<%=maxNumber%>"/>
		</p>
		<br />

		<p><%= LanguageUtil.get(request, "edit.maxbannersnumber") %></p>
		<p>
			<input	type="number"
					name='<portlet:namespace/><%=AdvisedAssetsConfigurationAction.maxBannersNumber %>'
					min="0"
					max="50"
					value="<%=maxBannersNumber%>"/>
		</p>
		<br />

		<p><%= LanguageUtil.get(request, "edit.minfavor") %></p>
		<p>
			<input	type="number"
					name='<portlet:namespace/><%=AdvisedAssetsConfigurationAction.minFavor %>'
					min="0"
					max="50"
					value="<%=minFavor%>"/>
		</p>
		<br />

		<p><%= LanguageUtil.get(request, "edit.bannerstagregexp") %></p>
		<p>
			<input	name='<portlet:namespace/><%=AdvisedAssetsConfigurationAction.bannersTagRegexp %>'
					value="<%=tag %>"/>
		</p>
		<br/>

		<p><%= LanguageUtil.get(request, "edit.repeatbanner") %></p>
		<p>
			<input	type="checkbox"
					name='<portlet:namespace/><%=AdvisedAssetsConfigurationAction.repeatBanner %>' <%if(repeatBanner) {%>checked<%}%> />
		</p>
		<br />

		<input	type="button"
				value="<%= LanguageUtil.get(request, "edit.save") %>"
				onClick="submitForm(document.<portlet:namespace />fm);" />
	</form>
</div>
