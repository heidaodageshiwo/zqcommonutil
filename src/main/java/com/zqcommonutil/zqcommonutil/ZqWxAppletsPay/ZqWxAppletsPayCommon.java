package com.zqcommonutil.zqcommonutil.ZqWxAppletsPay;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.zqcommonutil.zqcommonutil.ZqWxAppletsPay.ZqWxAppletsPayEntity.WxAppletsPayEntity;
import org.apache.commons.lang3.StringUtils;

//微信小程序支付的公共类 @auther: zhangqiang
public class ZqWxAppletsPayCommon {

  //功能描述:封装小程序端需要的参数 直接返回给小程序调用（此方法作用）
  // 此方法只适用于微信小程序手机端支付可以调用此接口(如果是电脑调试则是二维码，手机调试则是直接支付)
  public <T> T createOrder(WxAppletsPayEntity wxAppletsPayEntity) throws WxPayException {
    WxPayUnifiedOrderRequest requests = new WxPayUnifiedOrderRequest();
    requests.setBody(wxAppletsPayEntity.getBody());
    requests.setOutTradeNo(wxAppletsPayEntity.getOutTradeNo());
    requests.setTotalFee(wxAppletsPayEntity.getTotalAmount());
    requests.setSpbillCreateIp(wxAppletsPayEntity.getIp());
    requests.setOpenid(wxAppletsPayEntity.getOpenid());
    return this.getWxPayService(wxAppletsPayEntity).createOrder(requests);
  }


  //功能描述:封装app端需要的参数 直接返回给app调用（此方法作用）
  // 此方法只适用于微信app手机端支付回调调用此方法
  public WxPayOrderNotifyResult notifyUrl(String xmlData, WxAppletsPayEntity wxPayEntity) throws WxPayException {
    final WxPayOrderNotifyResult notifyResult = this.getWxPayService(wxPayEntity).parseOrderNotifyResult(xmlData);
    return notifyResult;
  }

  //公共调用类方法
  public WxPayService getWxPayService(WxAppletsPayEntity wxPayEntity) {
    WxPayConfig payConfig = new WxPayConfig();
    payConfig.setAppId(StringUtils.trimToNull(wxPayEntity.getAppId()));
    payConfig.setMchId(StringUtils.trimToNull(wxPayEntity.getMchId()));
    payConfig.setMchKey(StringUtils.trimToNull(wxPayEntity.getMchKey()));
    payConfig.setNotifyUrl(StringUtils.trimToNull(wxPayEntity.getNotifyurl()));
    payConfig.setTradeType(StringUtils.trimToNull(wxPayEntity.getTradetype()));
    payConfig.setUseSandboxEnv(false);
    WxPayService wxPayService = new WxPayServiceImpl();
    wxPayService.setConfig(payConfig);
    return wxPayService;
  }
}
