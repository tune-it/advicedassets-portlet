package com.tuneit.advisedassets;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
/**
 * This class represents advised asset.
 * It contains AssetEntry object of advised asset with it's favor.
 * AdvisedAssets objects can be compared.
 *
 * @author Berlev Vladimir
 */
public class AdvisedAsset implements Comparable<AdvisedAsset> {
    private static final Log log = LogFactoryUtil.getLog(AdvisedAsset.class);

    public AdvisedAsset(AssetEntry assetEntry) {
        this(assetEntry, 0);
    }

    public AdvisedAsset(AssetEntry assetEntry, int initialFavor) {
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
     * This method forms url of advised asset.
     * It uses AssetRenderer for assets,
     * which have own AssetRendererFactory and AssetRenderer inheritors.
     * For others  own methods for generating URLs are used.
     *
     * @param request
     * @param response
     * @return
     */
    public String formAssetURL(LiferayPortletRequest request, LiferayPortletResponse response) {
        String url;
        if (assetEntry.getUrl() == null || assetEntry.getUrl().isEmpty()) {
            try {
                AssetRendererFactory assetRendererFactory =
                        AssetRendererFactoryRegistryUtil
                                .getAssetRendererFactoryByClassName(
                                        assetEntry.getClassName());
                AssetRenderer assetRenderer = assetRendererFactory
                        .getAssetRenderer(assetEntry.getClassPK());
                url = assetRenderer.getURLViewInContext(request, response, "");
            } catch (Exception ex) {
                log.warn(ex.getLocalizedMessage());
                url = "";
            }
        }
        else {
            url = assetEntry.getUrl();
        }
        return url;
    }

    public String getTitle() {
        if (assetEntry.getClassName().matches(JournalArticle.class.getName())) {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
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

    public int compareTo(AdvisedAsset o) {
        return this.favor - o.favor;
    }
    protected AssetEntry assetEntry;
    protected int favor;
}
