package org.geoshark.advicedassets;

import java.io.IOException;
import java.io.StringReader;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.service.ShoppingItemLocalServiceUtil;

/**
 * This class represents adviced asset.
 * It contains AssetEntry object of adviced asset with it's favor.
 * AdvicedAssets objects can be compared.
 * 
 * @author Berlev Vladimir
 */
public class AdvicedAsset implements Comparable<AdvicedAsset> {
	
	public AdvicedAsset(AssetEntry assetEntry) {
		this(assetEntry, 0);
	}
	
	public AdvicedAsset(AssetEntry assetEntry, int initialFavor) {
		this.assetEntry = assetEntry;
		this.favor = initialFavor;
	}
	
	public void setFavor(int favor) {
		this.favor = favor;
	}
	
	public int getFavor() {
		return this.favor;
	}
	
	/**
	 * This method forms url of adviced asset. 
	 * It uses AssetRenderer for assets, 
	 * which have own AssetRendereFactory and AssetRenderer inheritors.
	 * For others  own methods for generating URLs are used.
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String formAssetURL(LiferayPortletRequest request, LiferayPortletResponse response) {
		String url = "";
		if(assetEntry.getClassName().matches(ShoppingItem.class.getName())) {
			try {
				ShoppingItem item = ShoppingItemLocalServiceUtil.getItem(assetEntry.getClassPK());
				url = getShoppingItemUrl(request, response, item);
			} catch (PortalException e) {
				url = "";
				e.printStackTrace();
			} catch (SystemException e) {
				url = "";
				e.printStackTrace();
			}
		}
		else {
			try {
				AssetRendererFactory assetRendererFactory = 
						AssetRendererFactoryRegistryUtil
						.getAssetRendererFactoryByClassName(
								assetEntry.getClassName());
				AssetRenderer assetRenderer = assetRendererFactory
						.getAssetRenderer(assetEntry.getClassPK());
				url = assetRenderer.getURLViewInContext(request, response, "");
			}
			catch(SystemException ex) {
				url = "";
			}
			catch(PortalException ex) {
				url = "";
			}
			catch(Exception ex) {
				url = "";
			}
		}
		return url;
	}
	
	public String getTitle() {
		if(assetEntry.getClassName().matches(JournalArticle.class.getName()))
		{
			DocumentBuilderFactory f  = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			try {
				db = f.newDocumentBuilder();
				InputSource is = new InputSource();
				is.setCharacterStream(new StringReader(assetEntry.getTitle()));
				Document doc = db.parse(is);
				System.out.println(assetEntry.getTitle());
				doc.getElementsByTagName("Title");
				return doc.getElementsByTagName("Title").item(0).getTextContent();
			} catch (Exception e) {
				return "some web content";
			}
		}
		return assetEntry.getTitle();
	}
	
	public int compareTo(AdvicedAsset o) {
		return this.favor - o.favor;
	}
	
	/**
	 * Own method for generating URL for
	 * ShoppingItem instances.
	 * 
	 * @param liferayPortletRequest
	 * @param liferayPortletResponse
	 * @param item
	 * @return
	 */
	public String getShoppingItemUrl(
	        LiferayPortletRequest liferayPortletRequest,
	        LiferayPortletResponse liferayPortletResponse,
	        ShoppingItem item) {
		String url = "";
		HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(liferayPortletRequest); 
		httpRequest = PortalUtil.getOriginalServletRequest(httpRequest);
		try {
			Group grp = GroupLocalServiceUtil.getGroup(
					PortalUtil.getDefaultCompanyId(),
					"Guest");
			long plid = PortalUtil.getPlidFromPortletId(grp.getGroupId(), "34");
			PortletURL portletURL = PortletURLFactoryUtil.create(httpRequest, "34", plid ,PortletRequest.RENDER_PHASE);
			portletURL.setParameter("struts_action", "/shopping/view_item");
			portletURL.setParameter("itemSku", item.getSku());
			url = portletURL.toString();
		} catch (PortalException e) {
			url = "";
			e.printStackTrace();
		} catch (SystemException e) {
			url = "";
			e.printStackTrace();
		}
		return url;
	}
	
	protected AssetEntry assetEntry;
	protected int favor;
}
