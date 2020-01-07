package com.zqcommonutil.zqcommonutil.ZqAliPay.ZqAliPayEntity;

import java.io.Serializable;
import lombok.Data;

//支付宝实体 @auther: zhangqiang
@Data
public class AliPayEntity implements Serializable {

  private static final long serialVersionUID = -3623487064102750085L;
  //支付总金额
  private String totalAmount;
  //订单名称
  private String subject;
  //商品描述
  private String body;

  //OutTradeNo
  private String outTradeNo;


  //商户appid
  private String appid;
  //私钥 pkcs8格式的
  private String rsa_private_key;
  //notify_url
  private String notify_url;
  //请求网关地址
  private String url;
  //编码
  private String charset;
  //返回格式
  private String format;
  //支付宝公钥
  private String alipay_public_key;
  //RSA2
  private String signtype;

  public AliPayEntity() {
  }

  public AliPayEntity(String charset, String alipay_public_key, String signtype) {
    this.charset = charset;
    this.alipay_public_key = alipay_public_key;
    this.signtype = signtype;
  }
}
