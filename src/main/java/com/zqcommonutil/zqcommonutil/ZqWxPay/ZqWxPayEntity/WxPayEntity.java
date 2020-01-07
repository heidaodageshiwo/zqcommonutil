package com.zqcommonutil.zqcommonutil.ZqWxPay.ZqWxPayEntity;

import java.io.Serializable;
import lombok.Data;

//微信支付实体 @auther: zhangqiang
@Data
public class WxPayEntity implements Serializable {

  private static final long serialVersionUID = -3623487064102750085L;
  //支付总金额
  private Integer totalAmount;
  //ip
  private String ip;
  //商品描述
  private String body;
  //OutTradeNo
  private String outTradeNo;

  //微信公众号或者小程序等的appid
  private String appId;
  //微信支付商户号
  private String mchId;
  //微信支付商户密钥
  private String mchKey;
  //回调地址
  private String notifyurl;
  //支付格式
  private String tradetype;


  public WxPayEntity() {
  }


}
