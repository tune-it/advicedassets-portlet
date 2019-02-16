package com.tuneit.advisedassets;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * This class represents advised banner.
 *
 * @author tegoo
 */
public class AdvisedBanner extends AdvisedAsset {

    public AdvisedBanner(AssetEntry assetEntry, DLFileEntry dlFileEntry, int favor) {
        super(assetEntry, favor);
        this.dlFileEntry = dlFileEntry;
    }

    public AdvisedBanner(AssetEntry assetEntry, DLFileEntry dlFileEntry) {
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
