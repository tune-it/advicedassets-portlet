<%@ page import="com.liferay.portal.kernel.util.*" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.tuneit.advisedassets.*" %>
<%@ page import="javax.portlet.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.liferay.asset.kernel.model.AssetEntry" %>
<%@ page import="com.liferay.blogs.kernel.model.BlogsEntry" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib prefix="aui" uri="http://liferay.com/tld/aui" %>
<%@ taglib prefix="liferay-ui" uri="http://liferay.com/tld/ui" %>

<portlet:defineObjects />

<%
    AssetEntry caughtAsset = (AssetEntry) request.getAttribute("LIFERAY_SHARED_LAYOUT_ASSET_ENTRY");
    portletSession.setAttribute("LIFERAY_SHARED_LAYOUT_ASSET_ENTRY", caughtAsset, PortletSession.APPLICATION_SCOPE);

    Advisement advisement = new Advisement();

    if (caughtAsset == null) {
	%>
	<p class="portlet-msg-info">
		<%= LanguageUtil.get(portletConfig.getResourceBundle(request.getLocale()), "view.nocontent") %>
    </p>
	<%
}
else {
	boolean repeatBanner = PrefsParamUtil.getBoolean(
			portletPreferences, request,
			AdvisedAssetsConfig.repeatBanner, AdvisedAssetsConfig.repeatBannerDefault);
	String bannerTagRegexp = PrefsParamUtil.getString(
			portletPreferences, request,
			AdvisedAssetsConfig.bannersTagRegexp, AdvisedAssetsConfig.bannersTagRegexpDefault);
	int minFavor = PrefsParamUtil.getInteger(
			portletPreferences, request,
			AdvisedAssetsConfig.minFavor, AdvisedAssetsConfig.minFavorDefault);

	advisement.setRepeatBanner(repeatBanner);
	advisement.setBannerRegexp(bannerTagRegexp);
	advisement.setMinFavor(minFavor);
	advisement.advice(caughtAsset);

	int n = PrefsParamUtil.getInteger(
			portletPreferences, request,
			AdvisedAssetsConfig.maxBannersNumber, AdvisedAssetsConfig.maxBannersNumberDefault);
	ArrayList<AdvisedBanner> banners = advisement.getBanners();
	if(n > banners.size()) {
		n = banners.size();
	}

	for (int i = 0; i < n; i++) {

		AdvisedBanner advisedBanner = banners.get(i);
		String url = advisedBanner.formAssetURL(liferayPortletRequest, liferayPortletResponse);
		String bannerUrl = advisedBanner.getBannerUrl(liferayPortletRequest);
		%>
		<a class="adviced-banner" href="<%=url%>"><img src=<%=bannerUrl%> /></a><br />
		<%
	}

	int maxListSize = PrefsParamUtil.getInteger(
			portletPreferences, request,
			AdvisedAssetsConfig.maxAssetsListSize, AdvisedAssetsConfig.maxAssetsListSizeDefault);
	n = maxListSize;
	ArrayList<AdvisedAsset> advisedAssets = advisement.getAdvisedAssets();
	if (n > advisedAssets.size()) {
		n = advisedAssets.size();
	}
	if ((n == 0) && (maxListSize > 0)) {
		%>
		<p class="portlet-msg-info">
			<%= LanguageUtil.get(portletConfig.getResourceBundle(request.getLocale()), "view.nofoundcontent") %> n is 0
        </p>
		<%
	}

	if (n > 0 && maxListSize > 0) {
		%>
		<h3><%= LanguageUtil.get(portletConfig.getResourceBundle(request.getLocale()), "view.advicedcontent") %></h3>
		<%
	}

	for (int i = 0; i < n; i++)
	{
		AdvisedAsset advisedAsset = advisedAssets.get(i);
		String url = advisedAsset.formAssetURL(liferayPortletRequest, liferayPortletResponse);
		String title = advisedAsset.getTitle();
		int favor = advisedAsset.getFavor();
		%>
		<aui:a cssClass="adviced-link" href="<%=url%>"><%=title%>(<%=favor%>)</aui:a><br />
		<%
	}
}
%>