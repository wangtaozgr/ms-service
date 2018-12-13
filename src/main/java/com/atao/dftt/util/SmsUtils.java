package com.atao.dftt.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsUtils
{
  static final String product = "Dysmsapi";
  static final String domain = "dysmsapi.aliyuncs.com";
  static final String accessKeyId = "LTAICOUbjVAjlWgP";
  static final String accessKeySecret = "nDhTBA6szhZUATIpaqdZEscxh6jbSQ";

  public static SendSmsResponse sendSms(String mobilephone, String templateCode, String json)
    throws ClientException
  {
    try
    {
      System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
      System.setProperty("sun.net.client.defaultReadTimeout", "10000");

      IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAICOUbjVAjlWgP", "nDhTBA6szhZUATIpaqdZEscxh6jbSQ");
      DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
      IAcsClient acsClient = new DefaultAcsClient(profile);

      SendSmsRequest request = new SendSmsRequest();

      request.setPhoneNumbers(mobilephone);

      request.setSignName("汪涛");

      request.setTemplateCode(templateCode);

      request.setTemplateParam(json);

      request.setOutId("123456");

      return (SendSmsResponse)acsClient.getAcsResponse(request);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }

  public static QuerySendDetailsResponse querySendDetails(String bizId)
    throws ClientException
  {
    System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
    System.setProperty("sun.net.client.defaultReadTimeout", "10000");

    IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAICOUbjVAjlWgP", "nDhTBA6szhZUATIpaqdZEscxh6jbSQ");
    DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
    IAcsClient acsClient = new DefaultAcsClient(profile);

    QuerySendDetailsRequest request = new QuerySendDetailsRequest();

    request.setPhoneNumber("17755117870");

    request.setBizId(bizId);

    SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
    request.setSendDate(ft.format(new Date()));

    request.setPageSize(Long.valueOf(10L));

    request.setCurrentPage(Long.valueOf(1L));

    QuerySendDetailsResponse querySendDetailsResponse = (QuerySendDetailsResponse)acsClient.getAcsResponse(request);

    return querySendDetailsResponse;
  }

  public static void main(String[] args) throws ClientException, InterruptedException {
    String smsJson = "{\"userName\":\"wangtaowinner\", \"name\":\"亨氏(Heinz) 超\", \"price\":\"1\", \"time\":\"20180128 20:00:00\"}";
    SendSmsResponse response = sendSms("17755117870", "SMS_122280451", smsJson);
  }
}