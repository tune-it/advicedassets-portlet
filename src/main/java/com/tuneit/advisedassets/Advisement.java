package com.tuneit.advisedassets;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetLinkLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

import javax.portlet.PortletSession;
import java.util.*;

/**
 * Class that implements logic of searching content.
 *
 * @author Berlev Vladimir
 */
public class Advisement {

    public Advisement() {
        repeatBanner = false;
        bannerRegexp = "banner";
        advisedAssets = new ArrayList<AdvisedAsset>();
        advicedBanners = new ArrayList<AdvisedBanner>();
        minFavor = 1;
    }

    public void setRepeatBanner(boolean flag) {
        this.repeatBanner = flag;
    }

    public void setBannerRegexp(String regexp) {
        if (regexp != null) {
            bannerRegexp = regexp;
        }
    }

    public void setMinFavor(int favor) {
        this.minFavor = favor;
    }

    public boolean getRepeatBanner() {
        return this.repeatBanner;
    }

    /**
     * Method that takes asset entry from  session.
     * <p>
     * Method that takes asset entry from  session.
     * This asset entry can be used as source for advisement logic.
     * Asset entry must be stored in session by some other module.
     * The good idea is to write hook-plugin for some portlet.
     *
     * @param session
     * @return
     */
    public AssetEntry catchAsset(PortletSession session) {
        return (AssetEntry) session
                .getAttribute("LIFERAY_SHARED_AdvisedAssetsAssetEntry", PortletSession.APPLICATION_SCOPE);
    }

    /**
     * Method that implements advisement logic
     * <p>
     * This method gets all assets in system,
     * checks if asset has banner and creates AdvisedBanner instances
     * for assets with banners and AdvisedAsset instances for all other assets.
     * Asset with banner can be duplicated in AdvicedAssets list, that depends of
     * repeatBanner flag. Lists of AdvicedAssets and AdvicedBanners are sorted by
     * favor descending. Favor is metric which equals number of matching tags with
     * tags of source asset.
     *
     * @param source
     */
    public void advice(AssetEntry source) {
        advisedAssets = new ArrayList<AdvisedAsset>();
        advicedBanners = new ArrayList<AdvisedBanner>();
        Random rand = new Random();

        try {
            int start = 0;
            int end = AssetEntryLocalServiceUtil.getAssetEntriesCount();
            List<AssetEntry> assets = AssetEntryLocalServiceUtil.getAssetEntries(start, end);

            for (AssetEntry asset : assets) {
                if (!asset.equals(source) && asset.getTags().size() > 0) {
                    int favor = countTraversals(source, asset);
                    if (favor >= minFavor) {
                        AdvisedBanner[] foundBanners = getBanners(asset);
                        int bannersCount = foundBanners.length;

                        if (bannersCount > 0) {
                            AdvisedBanner banner = foundBanners[rand.nextInt(bannersCount)];
                            this.advicedBanners.add(banner);

                            if (repeatBanner) {
                                advisedAssets.add(new AdvisedAsset(asset, favor));
                            }
                        } else {
                            advisedAssets.add(new AdvisedAsset(asset, favor));
                        }
                    }
                }
            }

            Collections.sort(advisedAssets);
            Collections.reverse(advisedAssets);
            Collections.sort(advicedBanners);
            Collections.reverse(advicedBanners);
        } catch (SystemException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Check if asset entry is banner.
     * <p>
     * This method checks if asset entry is banner.
     * To be a banner asset entry must satisfy three conditions:
     * 1. It must represent DLFileEntry
     * 2. It must be an image (must have jpg or png extension)
     * 3. It must have tag which matches bannerRegexp
     *
     * @param assetEntry
     * @return
     */
    public boolean isBanner(AssetEntry assetEntry) {
        boolean flag = false;
        try {
            if (assetEntry.getClassName().matches(DLFileEntry.class.getName())) {
                DLFileEntry file = DLFileEntryLocalServiceUtil.getDLFileEntry(assetEntry.getClassPK());
                if (file.getExtension().matches("jpg|png|gif")) {
                    String[] tags = assetEntry.getTagNames();
                    for (String tag : tags) {
                        if (tag.matches(bannerRegexp)) {
                            flag = true;
                            break;
                        }
                    }
                }
            }
        } catch (PortalException | SystemException ex) {
            flag = true;
        }
        return flag;
    }

    /**
     * Get banners of asset entry
     * <p>
     * This method gets all related assets (AssetLinks)
     * and checks each asset if it is a banner.
     *
     * @param assetEntry
     * @return
     */
    AdvisedBanner[] getBanners(AssetEntry assetEntry) {
        Hashtable<Long, AdvisedBanner> banners = new Hashtable<Long, AdvisedBanner>();

        try {
            long id = assetEntry.getEntryId();
            List<AssetLink> links = AssetLinkLocalServiceUtil.getLinks(id);

            for (AssetLink link : links) {

                long id1 = link.getEntryId1();
                AssetEntry a1 = AssetEntryLocalServiceUtil.getAssetEntry(id1);
                long id2 = link.getEntryId2();
                AssetEntry a2 = AssetEntryLocalServiceUtil.getAssetEntry(id2);

                if (id1 != id && !banners.contains(id1) && isBanner(a1)) {
                    DLFileEntry file = DLFileEntryLocalServiceUtil.getDLFileEntry(a1.getClassPK());
                    AdvisedBanner banner = new AdvisedBanner(assetEntry, file);
                    banners.put(id1, banner);
                }

                if (id2 != id && !banners.contains(id1) && isBanner(a2)) {
                    DLFileEntry file = DLFileEntryLocalServiceUtil.getDLFileEntry(a2.getClassPK());
                    AdvisedBanner banner = new AdvisedBanner(assetEntry, file);
                    banners.put(id2, banner);
                }

            }

        } catch (SystemException | PortalException ex) {
            ex.printStackTrace();
        }

        return banners.values().toArray(new AdvisedBanner[0]);
    }

    /**
     * Count matching tags of two assets.
     * <p>
     * This method is used to initialize favor in advice method.
     *
     * @param a1
     * @param a2
     * @return
     */
    private int countTraversals(AssetEntry a1, AssetEntry a2) {
        int result = 0;
        try {
            String[] tags1 = a1.getTagNames();
            String[] tags2 = a2.getTagNames();
            for (String tag1 : tags1) {
                for (String tag2 : tags2) {
                    if (tag1.matches(".*" + tag2 + ".*") ||
                            tag2.matches(".*" + tag1 + ".*")) {
                        result++;
                    }
                }
            }
        } catch (SystemException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public ArrayList<AdvisedAsset> getAdvisedAssets() {
        return (ArrayList<AdvisedAsset>) this.advisedAssets.clone();
    }

    public ArrayList<AdvisedBanner> getBanners() {
        return (ArrayList<AdvisedBanner>) this.advicedBanners.clone();
    }

    private ArrayList<AdvisedAsset> advisedAssets;
    private ArrayList<AdvisedBanner> advicedBanners;
    private int minFavor;
    private boolean repeatBanner;
    private String bannerRegexp;
}
