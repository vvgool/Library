package com.vv.map.provider.wmts;

import java.util.List;

/**
 * Copyright  @2016 珞珈德毅. All rights reserved.
 * <p>Description: WMTS解析后的对象信息</p>
 * @ClassName      WMTSInfo
 * @Package        com.ljdy.provider.wmts
 * @Author         vvgool
 * @Time           2016/5/11
 */
public class WMTSInfo {
    public String Layer;
    public String Style;
    public String Format;
    public List<String> TileMatrixSet;
    public String ServiceVersion;
}
