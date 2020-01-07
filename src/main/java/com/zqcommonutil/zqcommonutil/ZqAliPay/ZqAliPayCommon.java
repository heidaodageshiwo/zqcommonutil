package com.zqcommonutil.zqcommonutil.ZqAliPay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.zqcommonutil.zqcommonutil.ZqAliPay.ZqAliPayEntity.AliPayEntity;
import com.zqcommonutil.zqcommonutil.util.StringUtil;
import com.zqcommonutil.zqcommonutil.util.ZhangqiangDateUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

//支付宝支付的公共类 @auther: zhangqiang
public class ZqAliPayCommon {

  //封装app端需要的参数 直接返回给app调用（此方法作用）
  //此方法只适用于支付宝app手机端支付可以调用此接口
  public String getOrderString(AliPayEntity aliPayEntity) {
    AlipayClient alipayClient = new DefaultAlipayClient(aliPayEntity.getUrl(),
        aliPayEntity.getAppid(), aliPayEntity.getRsa_private_key(), aliPayEntity.getFormat(),
        aliPayEntity.getCharset(), aliPayEntity.getAlipay_public_key(), aliPayEntity.getSigntype());
    AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
    AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
    model.setBody(aliPayEntity.getBody());
    model.setSubject(aliPayEntity.getSubject());
    model.setOutTradeNo(aliPayEntity.getOutTradeNo());
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


  //封装app端需要的参数 直接返回给app调用（此方法作用）
  //此方法只适用于支付宝app手机端支付回调调用此方法
  public String notifyUrl(HttpServletRequest request, AliPayEntity entity) throws Exception {
    System.out.println("张强测试异步通知notifyUrl======start");
    Map<String, String> params = new HashMap<String, String>();
    Map<String, String[]> requestParams = request.getParameterMap();
    for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
      String name = (String) iter.next();
      String[] values = (String[]) requestParams.get(name);
      String valueStr = "";
      for (int i = 0; i < values.length; i++) {
        valueStr = (i == values.length - 1) ? valueStr + values[i]
            : valueStr + values[i] + ",";
      }
      params.put(name, valueStr);
//      System.out.println("name:" + name + ",valueStr:" + valueStr);
    }

    boolean signVerified = AlipaySignature
        .rsaCheckV1(params, entity.getAlipay_public_key(), entity.getCharset(),
            entity.getSigntype()); //调用SDK验证签名
    //商户订单号
    String out_trade_no = "";
    out_trade_no = request.getParameter("out_trade_no");
    //交易状态
    String trade_status = request.getParameter("trade_status");

    if (signVerified) { // 验证成功
      if (trade_status.equals("TRADE_FINISHED")) {
        System.out.println("TRADE_FINISHED");
      }
      if (trade_status.equals("TRADE_SUCCESS")) {
        System.out.println("TRADE_SUCCESS");
      }
      if (StringUtil.isNotEmpty(out_trade_no)) {
        System.out.println("张强测试异步通知notifyUrl======end");
        System.out.println("支付成功=====================out_trade_no============================已经支付："
            + out_trade_no);
        return out_trade_no;
      }
    } else {
      System.out.println("验证未通过");
    }
    System.out.println("张强测试异步通知notifyUrl======end");
    return out_trade_no;
  }
}
