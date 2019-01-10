package com.atao.dftt.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.atao.base.util.StringUtils;
import com.atao.dftt.util.HttpUrlConnectUtils;

public class JdHttp {
	private static Logger logger = LoggerFactory.getLogger(JdHttp.class);
	private static CookieManager manager = new CookieManager();
	static {
		manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		CookieHandler.setDefault(manager);
	}

	private static String usualAddressId = "138327122";
	private static String addressName = "汪涛";
	private static String addressDetail = "新荷北苑14栋805室";
	private static String mobile = "17755117870";
	private static String eid = "RE26DWMJKPSNQNB4IFENIC7J55MB26PCRHQIFTSSRG2ZAZXFK2RU7ZHIA2AV2W4YRRBYQZ7DS77XI6XQ6T6WWF2CSM";
	private static String fp = "2a3da9a29810cfa7a157dd5d4318df6b";
	private static String riskControl;
	private static String checkcodeTxt;
	private static String checkCodeRid;
	private static String trackId = "4d87edde1cd696bfc5a0dae075258c08";
	private static String username = "wangtaowinner";

	public static void main(String[] args) throws URISyntaxException {
		// login();
		/*
		 * URI uri = new URI("https://marathon.jd.com"); List<HttpCookie> listCookie =
		 * manager.getCookieStore().get(uri); for (HttpCookie httpCookie : listCookie) {
		 * logger.info(httpCookie.toString()); }
		 */
		/*
		 * login(); String url = itemShowBtn("1350411338"); logger.info(url); String
		 * secUrl = userRouting("1350411338", url); if (StringUtils.isNotBlank(secUrl))
		 * { boolean success = submitMsOrder("1350411338", secUrl); }
		 */
		secInit("1350411338", "https://marathon.jd.com/seckill/seckill.action?skuId=1350411338&num=1&rid=1543808598");

		/*
		 * for(int i=0;i<1000;i++) isStockState("31285843221");
		 */
		// oneYuanBuy();
		// test("https://divide.jd.com/user_routing?skuId=1356624706&sn=a5d3983c7741a17d3ef7e3eebf879dcc&from=pc");
		// submitMsOrder("34255314868",secUrl);
		// submitEasybuyOrder("1794085875");
	}

	public static boolean login() {
		JSONObject object = jdQqLoginUrl();
		String url = QqHttp.qqLogin();
		if (StringUtils.isNotBlank(url)) {
			QqHttp.check_sig(url);
		}
		String ui = "A2D358D0-39E9-4EDD-AA02-04F1E6667F84";
		logger.info("ui={}", ui);
		String p_skey = HttpUrlConnectUtils.getCookie(manager.getCookieStore(), "p_skey");
		logger.info("p_skey={}", p_skey);
		int g_tk = QqHttp.getToken(p_skey);
		logger.info("g_tk={}", g_tk);
		String postData = "response_type=code&client_id=" + object.getString("client_id") + "&redirect_uri="
				+ object.getString("redirect_uri") + "&scope=&state=" + object.getString("state")
				+ "&switch=&from_ptlogin=1&src=1&update_auth=1&openapi=80901010&g_tk=" + g_tk + "&auth_time="
				+ new Date().getTime() + "&ui=" + ui;
		logger.info("postData={}", postData);
		String jdLoginUrl = QqHttp.authorize(postData);
		if (StringUtils.isNotBlank(jdLoginUrl) && jdLoginUrl.startsWith("https://qq.jd.com")) {
			return jdLogin(jdLoginUrl);
		} else
			return false;
	}

	public static JSONObject jdQqLoginUrl() {
		String url = "https://qq.jd.com/new/qq/login.aspx";
		String result = HttpUrlConnectUtils.httpGet(url, false);
		logger.info("jdQqLoginUrl={}", result);
		JSONObject object = new JSONObject();
		result = result.replace("https://graph.qq.com/oauth2.0/authorize?", "");
		for (String item : result.split("&")) {
			String[] items = item.split("=");
			object.put(items[0], items[1]);
		}
		return object;
	}

	public static boolean jdLogin(String url) {
		String result = HttpUrlConnectUtils.httpGet(url, true);
		// logger.info(result);
		if (StringUtils.isNotBlank(result))
			return true;
		else
			return false;
	}

	public static boolean delProduct() {
		String url = "https://cart.jd.com/batchRemoveSkusFromCart.action";
		String content = HttpUrlConnectUtils.httpGet(url, false);
		logger.info(content);
		if (StringUtils.isNotBlank(content)) {
			logger.info("清空购物车成功");
			return true;
		}
		logger.info("清空购物车失败:" + content);
		return false;
	}

	public static boolean addProduct(String id) {
		String url = "https://cart.jd.com/gate.action?pid=" + id + "&pcount=1&ptype=1";
		String content = HttpUrlConnectUtils.httpGet(url, false);
		logger.info(content);
		if (StringUtils.isNotBlank(content) && (content.contains("https://cart.jd.com/addToCart.html"))) {
			logger.info("添加商品到购物车成功 productId={}", id);
			return true;
		}
		logger.info("添加商品到购物车失败:" + content);
		return false;
	}

	/**
	 * 一键购买
	 * 
	 * @param skuId
	 * @return
	 */
	public static int submitEasybuyOrder(String skuId) {
		String url = "https://easybuy.jd.com/skuDetail/newSubmitEasybuyOrder.action?callback=easybuysubmit&skuId="
				+ skuId + "&num=1&gids=&ybIds=&did=&useOtherAddr=false&_=" + new Date().getTime();
		Map<String, String> heads = new HashMap<String, String>();
		heads.put("Referer", "https://item.jd.com/" + skuId + ".html");
		String content = HttpUrlConnectUtils.httpPost(url, null, heads, null);
		logger.info(content);
		if (StringUtils.isNotBlank(content)) {
			if (content.contains("getEasyOrderInfo.action"))
				return 1;
			else if (content.contains("success.action"))
				return 0;
		}
		return -1;
	}

	/**
	 * 获得购物车中商品的价格
	 * 
	 * @return
	 */
	public static String getNewPrice() {
		String url = "https://trade.jd.com/shopping/order/getOrderInfo.action?flowId=10&rid=" + new Date().getTime();
		String content = HttpUrlConnectUtils.httpGet(url, false);
		// logger.info(content);
		if (StringUtils.isNotBlank(content)) {
			Document doc = Jsoup.parse(content);
			Element ris = doc.getElementById("riskControl");
			if (ris != null && StringUtils.isNotBlank(ris.val())) {
				riskControl = ris.val();
				Element showCheckCode = doc.getElementById("showCheckCode");
				if ("true".equals(showCheckCode.val())) {
					logger.info("提交订单需要验证码");
					Element encryptClientInfoE = doc.getElementById("encryptClientInfo");
					String encryptClientInfo = encryptClientInfoE == null ? "" : encryptClientInfoE.val();
					logger.info(username + ":提交订单需要验证码.encryptClientInfo=" + encryptClientInfo);
					checkCodeRid = (Math.random() + "_" + Math.random());
					logger.info(username + ":提交订单需要验证码.checkCodeRid=" + checkCodeRid);
					checkcodeTxt = getOrderYzm(checkCodeRid, encryptClientInfo);
					if (StringUtils.isBlank(checkcodeTxt)) {
						checkcodeTxt = getOrderYzm(checkCodeRid, encryptClientInfo);
					}
					if (StringUtils.isBlank(checkcodeTxt)) {
						checkcodeTxt = getOrderYzm(checkCodeRid, encryptClientInfo);
					}
					logger.info(username + ":提交订单需要验证码.checkcodeTxt=" + checkcodeTxt);
				} else {
					checkCodeRid = "";
					checkcodeTxt = "";
				}
				Element sumPrice = doc.getElementById("warePriceId");
				String total = sumPrice.text().replace("¥", "").replace("￥", "").trim();
				logger.info(username + ":price=" + total);
				return total;
			}
		}
		return null;
	}

	public static String getOrderYzm(String acid, String is) {
		String url = "https://captcha.jd.com/verify/image?acid=" + acid + "&srcid=trackWeb&is=" + is;
		String content = HttpUrlConnectUtils.httpGet(url, false);
		logger.info(content);
		if (StringUtils.isNotBlank(content)) {
			return null;
		}
		return null;
	}

	/**
	 * 普通购物车提交订单
	 * 
	 * @return
	 */
	public static int submitOrder() {
		String url = "https://trade.jd.com/shopping/order/submitOrder.action";
		Map<String, String> heads = new HashMap<String, String>();
		String postData = "overseaPurchaseCookies=&submitOrderParam.sopNotPutInvoice=false&submitOrderParam.trackID=TestTrackId&submitOrderParam.ignorePriceChange=0&submitOrderParam.btSupport=0&submitOrderParam.eid="+eid+"&submitOrderParam.fp="+fp+"&riskControl="
				+ riskControl + "&submitOrderParam.isBestCoupon=1&submitOrderParam.jxj=1&submitOrderParam.trackId="
				+ trackId;
		String content = HttpUrlConnectUtils.httpPost(url, postData, heads, null);
		if (StringUtils.isNotBlank(content)) {
			JSONObject jsonObj = JSONObject.parseObject(content);
			if ("0".equals(jsonObj.getString("resultCode")) && jsonObj.getBooleanValue("success")) {
				if ("验证码不正确，请重新填写".equals(jsonObj.getString("message")))
					logger.info("验证码不正确，请重新填写!  ");
				else
					logger.info("生成订单成功.请立即去支付!  ");
				return jsonObj.getIntValue("resultCode");
			} else if ("61040".equals(jsonObj.getString("resultCode"))) {
				logger.info("活动已结束，没有抢到!  ");
				return jsonObj.getIntValue("resultCode");
			} else if ("600158".equals(jsonObj.getString("resultCode"))) {
				logger.info("商品无货!  ");
				return jsonObj.getIntValue("resultCode");
			} else {
				logger.info(jsonObj.toJSONString());
				if ("61039".equals(jsonObj.getString("resultCode")))
					return 61039;
				if ("61036".equals(jsonObj.getString("resultCode")))
					return 61036;
				if ("60017".equals(jsonObj.getString("resultCode")))
					return 60017;
			}
		}
		return -1;
	}

	public static JSONObject secInit(String skuId, String secUrl) {
		String url = "https://marathon.jd.com/seckillnew/orderService/pc/init.action";
		Map<String, String> heads = new HashMap<String, String>();
		heads.put("Referer", secUrl);
		heads.put("Origin", "https://marathon.jd.com");
		heads.put("Accept", "application/json, text/plain, */*");
		String postData = "sku=" + skuId + "&num=1&isModifyAddress=false";
		String content = HttpUrlConnectUtils.httpPost(url, postData, heads, null);
		//logger.info(content);
		if (StringUtils.isNotBlank(content)) {
			JSONObject jsonObj = JSONObject.parseObject(content);
			return jsonObj;
		}
		return null;
	}

	public static boolean submitMsOrder(String productId, String secUrl) {
		JSONObject data = secInit(productId, secUrl);
		String token = data.getString("token");
		String url = "https://marathon.jd.com/seckillnew/orderService/pc/submitOrder.action?skuId=" + productId;
		Map<String, String> heads = new HashMap<String, String>();
		heads.put("Referer", secUrl);
		String postData = "skuId=" + productId + "&num=1&addressId=" + usualAddressId
				+ "&yuShou=false&isModifyAddress=false&name=" + addressName
				+ "&provinceId=14&cityId=1116&countyId=3434&townId=51628&addressDetail=" + addressDetail
				+ "&mobile=177%2A%2A%2A%2A7870&mobileKey=588ccb1fefae2bd48e16a2be5413f09a&email=&postCode=&invoiceTitle=-1&invoiceCompanyName=&invoiceContent=1&invoiceTaxpayerNO=&invoiceEmail=&invoicePhone=&invoicePhoneKey=&invoice=false&password=&codTimeType=3&paymentType=4&areaCode=&overseas=0&phone=&eid="
				+ eid + "&fp=" + fp + "&token=" + token;
		/*
		 * String postData = "orderParam.name=" + addressName +
		 * "&orderParam.addressDetail=" + addressDetail + "&orderParam.mobile=" + mobile
		 * +
		 * "&orderParam.email=&orderParam.provinceId=14&orderParam.cityId=1116&orderParam.countyId=3434&orderParam.townId=51628&orderParam.paymentType=4&orderParam.password=&orderParam.invoiceTitle=4&orderParam.invoiceContent=-1&orderParam.invoiceCompanyName=&orderParam.invoiceTaxpayerNO=&orderParam.invoiceEmail=&orderParam.invoicePhone=&orderParam.usualAddressId="
		 * + usualAddressId + "&skuId=" + productId +
		 * "&num=1&orderParam.provinceName=安徽&orderParam.cityName=合肥市&orderParam.countyName=庐阳区&orderParam.townName=城区&orderParam.codTimeType=3&orderParam.mobileKey=588ccb1fefae2bd48e16a2be5413f09a&eid="
		 * + eid + "&fp=" + fp +
		 * "&addressMD5=08f059c265703a27faefce71375bb8f9&yuyue=&yuyueAddress=0";
		 */
		String content = HttpUrlConnectUtils.httpPost(url, postData, heads, null);
		logger.info(content);
		// {"errorMessage":"很遗憾没有抢到，再接再厉哦。","orderId":0,"resultCode":90008,"skuId":0,"success":false}
		if (StringUtils.isNotBlank(content)) {
			JSONObject jsonObj = JSONObject.parseObject(content);
			return jsonObj.getBooleanValue("success");
		}
		return true;
	}

	/**
	 * 获取超级秒杀地址
	 * 
	 * @param productId
	 * @return
	 */
	public static String itemShowBtn(String productId) {
		String url = "https://itemko.jd.com/itemShowBtn?skuId=" + productId + "&from=pc&_=" + new Date().getTime();
		String content = HttpUrlConnectUtils.httpGet(url, false);
		logger.info(content);
		if ((StringUtils.isNotBlank(content)) && (content.contains("divide")))
			content = content.substring(content.indexOf("url") + 6, content.indexOf("}") - 1);
		else {
			return null;
		}
		return "https:" + content;
	}

	public static String userRouting(String productId, String msAddress) throws URISyntaxException {
		String captchaUrl = HttpUrlConnectUtils.httpGet(msAddress, false);
		logger.info("captchaUrl={}", captchaUrl);
		if (StringUtils.isNotBlank(captchaUrl)) {
			if (captchaUrl.contains("passport.jd.com")) {
				login();
				msAddress = itemShowBtn(productId);
				captchaUrl = HttpUrlConnectUtils.httpGet(msAddress, false);
				logger.info(captchaUrl);
			}
		}
		String secUrl = captcha(productId, captchaUrl);
		logger.info("secUrl={}", secUrl);
		if ((StringUtils.isNotBlank(secUrl)) && (secUrl.contains("seckill.action"))) {
			String content = HttpUrlConnectUtils.httpGet(secUrl, true);
			//logger.info(content);
			return secUrl;
		}
		return null;
	}

	public static String captcha(String productId, String captchaUrl) throws URISyntaxException {
		HttpURLConnection connection = null;
		InputStream is = null;
		StringBuilder sb = new StringBuilder();
		try {
			// 准备请求的网络地址
			URL urL = new URL(captchaUrl);
			// 调用openConnection得到网络连接，网络连接处于就绪状态
			connection = (HttpURLConnection) urL.openConnection();
			// 设置网络连接超时时间5S
			connection.setConnectTimeout(10 * 1000);
			// 设置读取超时时间
			connection.setReadTimeout(10 * 1000);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
			connection.connect();

			String newCookie = connection.getHeaderField("Set-Cookie");
			String cookieName = "seckill" + productId;
			URI sec = new URI("http://marathon.jd.com");
			List<HttpCookie> cookies = manager.getCookieStore().get(sec);
			for (HttpCookie hc : cookies) {
				if (cookieName.equals(hc.getName())) {
					manager.getCookieStore().remove(sec, hc);
				}
			}
			// 取第一个seckill+productId 的cookie值
			String cookieValue = newCookie.replace(cookieName + "=", "");
			cookieValue = cookieValue.substring(0, cookieValue.indexOf(";"));
			HttpCookie newHttpCookie = new HttpCookie(cookieName, cookieValue);
			newHttpCookie.setDomain("marathon.jd.com");
			newHttpCookie.setVersion(0);
			newHttpCookie.setPath("/");
			manager.getCookieStore().add(sec, newHttpCookie);
			// if连接请求码成功
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				is = connection.getInputStream();
				byte[] bytes = new byte[1024];
				int i = 0;
				while ((i = is.read(bytes)) != -1) {
					sb.append(new String(bytes, 0, i, "utf-8"));
				}
				is.close();
			} else if (connection.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
				sb.append(connection.getHeaderField("Location"));
			}
			return sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return null;
	}

	public static JSONObject getCjmsBuyUrl(String productId) {
		String url = "https://itemko.jd.com/itemShowBtn?skuId=" + productId + "&from=pc&_=" + new Date().getTime();
		String content = HttpUrlConnectUtils.httpGet(url, false);
		if (StringUtils.isNotBlank(content)) {
			content = content.substring(1, content.length() - 2);
			logger.info(content);
			JSONObject result = JSONObject.parseObject(content);
			return result;
		}
		return null;
	}

	public static boolean isCjms(String productId) {
		JSONObject result = getCjmsBuyUrl(productId);
		if (result == null)
			return false;
		if (StringUtils.isNotBlank(result.getString("st"))) {
			return true;
		}
		return false;
	}

	public static JSONObject stock(String productId) {
		String url = "http://c0.3.cn/stock?skuId=" + productId
				+ "&area=1_72_2799_0&cat=1315,1343,9713&buyNum=1&choseSuitSkuIds=&extraParam={\"originid\":\"1\"}&ch=1&fqsp=0";
		String content = HttpUrlConnectUtils.httpGet(url, false);
		logger.info(content);
		if (StringUtils.isNotBlank(content)) {
			JSONObject result = JSONObject.parseObject(content);
			return result;
		}
		return null;
	}

	public static boolean isStockState(String productId) {
		JSONObject json = stock(productId);
		if (json == null)
			json = stock(productId);
		if (json == null)
			json = stock(productId);
		String stockState = json.getJSONObject("stock").getString("StockState");
		logger.info(stockState);
		if ("33".equals(stockState)) {
			return true;
		}
		return false;
	}

	public static JSONObject oneYuanBuy() {
		JSONObject res = new JSONObject();
		String url = "http://api.m.jd.com/client.action?functionId=oneYuanBuy&clientVersion=7.2.0&build=62581&client=android&d_brand=Xiaomi&d_model=MI5X&osVersion=7.1.2&screen=1920*1080&partner=xiaomi001&androidId=14b5f23968b97345&installtionId=f7e0b49a0657493ca7b96d453a70d5ca&sdkVersion=25&lang=zh_CN&uuid=99001065551084-d863759220e2&area=14_1116_3434_0&networkType=wifi&wifiBssid=7a9c349940d1bb76ba8e9ac9fc9fc501&st=1540276838503&sign=8c9da634b8e6e8bd6155f5766dcf5359&sv=100";
		Map<String, String> heads = new HashMap<String, String>();
		heads.put("User-Agent", "okhttp/3.4.1");
		heads.put("Charset", "UTF-8");
		heads.put("jdc-backup",
				"pin=wangtaowinner; wskey=AAFbyb6tAECZWuXsNOAB5it85fgcVYkoQo2TxJrY-_tsED0b7PWROF68TfxhdcrKLyzvG56GU5zIUHFgVXBf0czZy16J4CfT; whwswswws=25b68771b425347be9bbeaa6ce98307bb5bc5cba82cb627587e8a1ce97;");
		heads.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		heads.put("Connection", "close");
		heads.put("cookie",
				"pin=wangtaowinner; wskey=AAFbyb6tAECZWuXsNOAB5it85fgcVYkoQo2TxJrY-_tsED0b7PWROF68TfxhdcrKLyzvG56GU5zIUHFgVXBf0czZy16J4CfT; whwswswws=25b68771b425347be9bbeaa6ce98307bb5bc5cba82cb627587e8a1ce97;");

		String postData = "body={\"shangouversion\":\"4.5\"}";
		String content = HttpUrlConnectUtils.httpPost(url, postData, heads, null);
		logger.info(content);
		JSONObject json = JSONObject.parseObject(content);
		String oneYuanActivityUrl = "";
		if (json.getIntValue("code") == 0) {
			res = json.getJSONArray("result").getJSONObject(0);
			oneYuanActivityUrl = res.getString("targetUrl");
		}
		logger.info("oneYuanActivityUrl={}", oneYuanActivityUrl);
		if (StringUtils.isNotBlank(oneYuanActivityUrl)) {
			content = HttpUrlConnectUtils.httpGet(oneYuanActivityUrl, true);
			String skuId = content.substring(content.indexOf("skuId") + 8,
					content.indexOf(",", content.indexOf("skuId")) - 1);
			System.out.println(skuId);
			res.put("skuId", skuId);
			return res;
		}
		return null;
	}

}
