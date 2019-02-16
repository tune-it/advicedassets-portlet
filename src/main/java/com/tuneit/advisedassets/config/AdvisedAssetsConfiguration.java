package com.tuneit.advisedassets.config;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(id = "com.tuneit.advisedassets.config.AdvisedAssetsConfiguration")
public interface AdvisedAssetsConfiguration {
    @Meta.AD(required = false, deflt = "5")
    public Integer maxAssetsListSize();

    @Meta.AD(required = false, deflt = "1")
    public Integer maxBannersNumber();

    @Meta.AD(required = false, deflt = "banner")
    public String bannersTagRegexp();

    @Meta.AD(required = false, deflt = "false")
    public Boolean repeatBanner();

    @Meta.AD(required = false, deflt = "1")
    public Integer minFavor();
}
