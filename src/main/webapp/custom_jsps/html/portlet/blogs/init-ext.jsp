<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@page import="javax.portlet.PortletSession"%>
<%@page import="javax.portlet.*"%>
<%@ page import="com.liferay.portal.kernel.search.Document" %><%@
page import="com.liferay.portal.kernel.search.Hits" %><%@
page import="com.liferay.portal.kernel.search.Indexer" %><%@
page import="com.liferay.portal.kernel.search.IndexerRegistryUtil" %><%@
page import="com.liferay.portal.kernel.search.SearchContext" %><%@
page import="com.liferay.portal.kernel.search.SearchContextFactory" %><%@
page import="com.liferay.portal.service.SubscriptionLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.model.AssetEntry" %><%@
page import="com.liferay.portlet.asset.model.AssetTag" %><%@
page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetEntryServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetTagLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.persistence.AssetEntryQuery" %><%@
page import="com.liferay.portlet.blogs.EntryContentException" %><%@
page import="com.liferay.portlet.blogs.EntrySmallImageNameException" %><%@
page import="com.liferay.portlet.blogs.EntrySmallImageSizeException" %><%@
page import="com.liferay.portlet.blogs.EntryTitleException" %><%@
page import="com.liferay.portlet.blogs.NoSuchEntryException" %><%@
page import="com.liferay.portlet.blogs.model.BlogsEntry" %><%@
page import="com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil" %><%@
page import="com.liferay.portlet.blogs.service.BlogsEntryServiceUtil" %><%@
page import="com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil" %><%@
page import="com.liferay.util.RSSUtil" %>

<portlet:defineObjects/>

<%
AssetEntry ac_assetEntry = (AssetEntry)request.getAttribute("view_entry_content.jsp-assetEntry");
PortletSession ac_ps = renderRequest.getPortletSession();
ac_ps.setAttribute("LIFERAY_SHARED_AdvicedAssetsAssetEntry", ac_assetEntry, PortletSession.APPLICATION_SCOPE);
%>