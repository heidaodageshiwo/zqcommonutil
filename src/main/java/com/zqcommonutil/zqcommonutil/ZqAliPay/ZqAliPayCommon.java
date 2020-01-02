package com.zqcommonutil.zqcommonutil.ZqAliPay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.zqcommonutil.zqcommonutil.ZqAliPay.ZqAliPayEntity.AliPayEntity;
import com.zqcommonutil.zqcommonutil.util.ZhangqiangDateUtil;

//支付宝支付的公共类 @auther: zhangq
public class ZqAliPayCommon {

  //封装app端需要的参数 直接返回给app调用
  public String getOrderString(AliPayEntity aliPayEntity) {
    String orderNo = ZhangqiangDateUtil.getCurrentDateStr();
    AlipayClient alipayClient = new DefaultAlipayClient(aliPayEntity.getUrl(),
        aliPayEntity.getAppid(), aliPayEntity.getRsa_private_key(), aliPayEntity.getFormat(),
        aliPayEntity.getCharset(), aliPayEntity.getAlipay_public_key(), aliPayEntity.getSigntype());
    AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
    AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
    model.setBody(aliPayEntity.getBody());
    model.setSubject(aliPayEntity.getSubject());
    model.setOutTradeNo(orderNo);
    model.setTimeoutExpress("90m");
    model.setTotalAmount(aliPayEntity.getTotalAmount());
    model.setProductCode("QUICK_MSECURITY_PAY");
    request.setBizModel(model);
    request.setNotifyUrl(aliPayEntity.getNotify_url());
    String orderString = "";
    try {
      AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
      orderString = response.getBody();
    } catch (AlipayApiException e) {
      e.printStackTrace();
    }
    return orderString;
  }


}
