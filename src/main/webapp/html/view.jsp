<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.portal.kernel.util.*"%>
<%@page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@page import="com.tuneit.advicedassets.*"%>
<%@page import="javax.portlet.*"%>
<%@page import="java.util.*"%>   

<%@taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@taglib uri="http://liferay.com/tld/aui" prefix="aui" %>


<portlet:defineObjects />

<%
Advicement advicement = new Advicement();
AssetEntry catchedAsset = advicement.catchAsset(portletSession);

if(catchedAsset == null) {
	%>
	<p class="portlet-msg-info">
		<%= LanguageUtil.get(pageContext, "view.nocontent") %>
	</p>
	<%
}
else {
	boolean repeatBanner = PrefsParamUtil.getBoolean(
			portletPreferences, request, 
			AdvicedAssetsConfig.repeatBanner, AdvicedAssetsConfig.repeatBannerDefault);
	String bannerTagRegexp = PrefsParamUtil.getString(
			portletPreferences, request, 
			AdvicedAssetsConfig.bannersTagRegexp, AdvicedAssetsConfig.bannersTagRegexpDefault);
	int minFavor = PrefsParamUtil.getInteger(
			portletPreferences, request, 
			AdvicedAssetsConfig.minFavor, AdvicedAssetsConfig.minFavorDefault);
	
	advicement.setRepeatBanner(repeatBanner);
	advicement.setBannerRegexp(bannerTagRegexp);
	advicement.setMinFavor(minFavor);
	advicement.advice(catchedAsset);
	
	int n = PrefsParamUtil.getInteger(
			portletPreferences, request, 
			AdvicedAssetsConfig.maxBannersNumber, AdvicedAssetsConfig.maxBannersNumberDefault);
	ArrayList<AdvicedBanner> banners = advicement.getBanners();
	if(n > banners.size()) {
		n = banners.size();
	}
	
	for(int i = 0; i < n; i++) {
		AdvicedBanner advicedBanner = banners.get(i);
		String url = advicedBanner.formAssetURL(liferayPortletRequest, liferayPortletResponse);
		String bannerUrl = advicedBanner.getBannerUrl(liferayPortletRequest);
		%>
		<a class="adviced-banner" href="<%=url%>"><img src=<%=bannerUrl%> /></a><br />
		<%
	}
	
	
	int maxListSize = PrefsParamUtil.getInteger(
			portletPreferences, request, 
			AdvicedAssetsConfig.maxAssetsListSize, AdvicedAssetsConfig.maxAssetsListSizeDefault);
	n = maxListSize;
	ArrayList<AdvicedAsset> advicedAssets = advicement.getAdvicedAssets();
	if(n > advicedAssets.size()) {
		n = advicedAssets.size();
	}
	if((n == 0) && (maxListSize > 0)) {
		%>
		<p class="portlet-msg-info">
			<%= LanguageUtil.get(pageContext, "view.nofoundcontent") %>
		</p>
		<%
	}
	
	if(n > 0 && maxListSize > 0) {
		%>
		<h3><%= LanguageUtil.get(pageContext, "view.advicedcontent") %></h3>
		<%
	}
	
	for(int i = 0; i < n; i++)
	{
		AdvicedAsset advicedAsset = advicedAssets.get(i);
		String url = advicedAsset.formAssetURL(liferayPortletRequest, liferayPortletResponse);
		String title = advicedAsset.getTitle();
		int favor = advicedAsset.getFavor();
		%>
		<aui:a cssClass="adviced-link" href="<%=url%>"><%=title%><%-- (<%=favor%>)--%></aui:a><br />
		<%
	}
	
}
%>