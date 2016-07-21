package com.vv.map.interf;

/**
 * Copyright  @2016 珞珈德毅. All rights reserved.
 * <p>Description: 在线地图获取接口</p>
 * @ClassName      IDeuMapHttpCallBack
 * @Package        com.ljdy.interf
 * @Author         vvgool
 * @Time           2016/4/13
 */
public interface IDeuMapHttpCallBack {
    /**
     * 数据请求成功，并返回地图瓦片数据
     * @param response 瓦片数据
     */
    void httpConnectSucceed(byte[] response);

    /**
     * 数据请求失败，返回失败原因
     * @param strErr 失败信息描述
     */
    void httpConnectFailed(StringBuffer strErr);
}
