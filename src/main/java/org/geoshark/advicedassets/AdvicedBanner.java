package org.geoshark.advicedassets;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;


/**
 * This class represents adviced banner.
 * 
 * @author tegoo
 */
public class AdvicedBanner extends AdvicedAsset {
	
	public AdvicedBanner(AssetEntry assetEntry, DLFileEntry dlFileEntry, int favor) {
		super(assetEntry, favor);
		this.dlFileEntry = dlFileEntry;
	}

	public AdvicedBanner(AssetEntry assetEntry, DLFileEntry dlFileEntry) {
		super(assetEntry);
		this.dlFileEntry = dlFileEntry;
	}
	
	/**
	 * Method for generating URL of banner image.
	 * 
	 * @param request
	 * @return
	 */
	public String getBannerUrl(LiferayPortletRequest request) {
		String url = "";
		try {
			url = (
					"/documents/" + dlFileEntry.getGroupId() 
					+ "/" + dlFileEntry.getFolderId() + "/" 
							+ URLEncoder.encode(dlFileEntry.getTitle(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	protected DLFileEntry dlFileEntry;
}
