package com.zqcommonutil.zqcommonutil.ZqWxPay;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.zqcommonutil.zqcommonutil.ZqWxPay.ZqWxPayEntity.WxPayEntity;
import com.zqcommonutil.zqcommonutil.util.DateUtil;
import org.apache.commons.lang3.StringUtils;

//微信支付的公共类 @auther: zhangqiang
public class ZqWxPayCommon {

  //功能描述:封装app端需要的参数 直接返回给app调用（此方法作用）
  // 此方法只适用于微信app手机端支付可以调用此接口
  public <T> T createOrder(WxPayEntity wxPayEntity) throws WxPayException {
    WxPayUnifiedOrderRequest requests = new WxPayUnifiedOrderRequest();
    requests.setBody(wxPayEntity.getBody());
    requests.setOutTradeNo(wxPayEntity.getOutTradeNo());
    requests.setTotalFee(wxPayEntity.getTotalAmount());
    requests.setSpbillCreateIp(wxPayEntity.getIp());
    return this.getWxPayService(wxPayEntity).createOrder(requests);
  }


  //功能描述:封装app端需要的参数 直接返回给app调用（此方法作用）
  // 此方法只适用于微信app手机端支付回调调用此方法
  public WxPayOrderNotifyResult notifyUrl(String xmlData, WxPayEntity wxPayEntity) throws WxPayException {
    final WxPayOrderNotifyResult notifyResult = this.getWxPayService(wxPayEntity).parseOrderNotifyResult(xmlData);
    return notifyResult;
  }

  //公共调用类方法
  public WxPayService getWxPayService(WxPayEntity wxPayEntity) {
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
