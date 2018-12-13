package com.atao.dftt.http;

import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.atao.base.util.StringUtils;
import com.atao.dftt.util.HttpUrlConnectUtils;

public class TbHttp {
	private static Logger logger = LoggerFactory.getLogger(TbHttp.class);
	private static CookieManager manager = new CookieManager();
	static {
		manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		CookieHandler.setDefault(manager);
	}

	private static String username = "wangtaowinner"; // 明文账号
	private static String password; // 明文密码
	private static String ncoToken = ""; // eid
	private static String TPL_password_2 = "5725f8d599c9bf9bc4a67a216ba7579e2be33bd4df9a829f82327575dbd445ee2464e5496011c756651a11acb6e8f7d8e7bb7d94769b59ae25aa8cdf39ce4b634279d34313a0235b80f997202cca5e32a9cfc1293645a3d3d394ca7eb3044017e20141bdd35530248d5b6b957f974cc10aef69db67a0851f7dc481983c62c15a"; // fp
	private static String um_token = "HV02PAAZ0b080a8b726684db5be0e7440c7e229a100024";
	private static String ua = "113#KOVtGffFfl8F9f4AaNFpxybxbskPCY2+tKf6bFbOsKukV78zk5D2owPV+er6R2PD16F6bxavPoSHDTwOv2onosbxbYVQzWRD1PFZbOaYIVzMrzjokDinoYbkbY4mxWRD16fWbFiYdPf8DsDNk6CILy9xbsdWHD5o16FW10iOGokNGBqe6n6noSLc1m4KxF5D1BFfCYKCCDcuwEXo12tUIvpxznWjrbQmrjVU6Y44+lQ9weNc4USuNGvl9VHeRrieckN94KZSUQGTZIowjeUTfyM5lr/1tJC999P50mIXA+KdhaFnRJJUG95rkYG7roxgo7peX7j/NBI/R5zo+Mwo3Rb8T8ZIr9jT/7zsZYPjrL5Es5MweuFhwrVmykgUyixUcJdp4FrrAK/K559KdMYyVNKc0Z8dYMaUiYcVUUJkLev7pYiPnN7LiPiMhBxICSdaNOfh3iiXjz9C9bg3ty+SkCAbW/XdMz9DxnA5Lp96MIwr0e0brNY61hmZbO2sQxDrXWsJbq32yvK14Tahe5BGYTx3Yh0t6b/cj5PYXspszx+/Y1dVM5depOQekf9w6zEuwLZ3AC5pUgKoRHIIRRfKMGtpFG0jkY8mfI6Jsc2lqDhMBE4n4apYllxDErCVJei4rpYVc8tuQ9Ui49fEkb2yZe0zheLsyaGw";
	private static String tk = "ee0b47553177e";
	private static String cookie = "cna=Y7JdFKI+iScCAXJmhFYjZ3Lv; hng=CN%7Czh-CN%7CCNY%7C156; lid=wangtaowinner; otherx=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0; x=__ll%3D-1%26_ato%3D0; ubn=p; dnk=wangtaowinner; uc1=cookie16=W5iHLLyFPlMGbLDwA%2BdvAGZqLg%3D%3D&cookie21=VFC%2FuZ9ajCbF8%2BYOU8qecg%3D%3D&cookie15=UtASsssmOIJ0bQ%3D%3D&existShop=true&pas=0&cookie14=UoTYN4kLup5FeQ%3D%3D&cart_m=0&tag=8&lng=zh_CN; uc3=vt3=F8dByR%2FOd7py7XoYdM0%3D&id2=VAmtHbpV%2Ffg%3D&nk2=FPjang8l%2FVVh71SE0w%3D%3D&lg2=VFC%2FuZ9ayeYq2g%3D%3D; tracknick=wangtaowinner; _l_g_=Ug%3D%3D; unb=78775849; lgc=wangtaowinner; cookie1=VAKMJrvgIJz0bztoFIAKfHJM1ApX3vdwm1u35ex%2FB24%3D; login=true; cookie17=VAmtHbpV%2Ffg%3D; cookie2=6912dfcf9fa5e2b6d7961d2feac692af; _nk_=wangtaowinner; t=db85a0e5a937abfc6d68d2c5dc6b0fe0; sg=r90; csg=0a8bccff; _tb_token_=ee0b47553177e; _m_h5_tk=97d3b071133ee145ae429dfd42fc8e42_1541857711196; _m_h5_tk_enc=6877aef53c328d665d655e1182de02f3; ucn=unzbmix25g; x5sec=7b22627579323b32223a223135636538643537356261396562383630313366323264336439623662313231434a57526d393846454d6d32726636506c4c476b41526f4b4e7a67334e7a55344e446b374d513d3d227d; whl=-1%260%260%260; isg=BD09zmNsOrojB57WcXCH7qfZTJn3cm6vOnJrnf-CKBTMNlxo2i7j_fKk5CrV6onk";
	private static String sellerId;// 商品卖家id
	private static String cartId;// 购物车id
	private static String skuId;
	private static String itemId;
	private static String cartParam;
	public static String cartData;
	private static String hierarchy;
	private static String data;
	private static String linkage;
	public static String userId = "78775849";// 淘宝用户id
	private static String submitOrderUrl;// 提交订单url

	// 559256413273 3934874522113
	public static void main(String[] args) throws URISyntaxException {

	/*	JSONObject json = productTmallDetailOne("581146570399");// 580813687780
		boolean success = confirmtmallorderonly(json, userId);
		logger.info("confirm order = {} ", success);
		if (success) {
			success = submittmallorderonly();
			logger.info("submit order = {} ", success);
		}*/

		JSONObject json = productTmallDetail("580242197304", "3870427936855");
		boolean success = confirmtmallorder(json, userId);
		logger.info("confirm order = {} ", success);
		if (success) {
			success = submittmallorder();
			logger.info("submit order = {} ", success);
		}
	
	}

	public static boolean login() {
		String url = "https://login.m.taobao.com/login.htm?_input_charset=utf-8&sdkInterceptType=&ttid=h5%40iframe";
		String postData = "_tb_token_=" + tk
				+ "&ttid=h5%40iframe&action=LoginAction&event_submit_do_login=1&TPL_redirect_url=https://h5.m.taobao.com/other/loginend.html?origin=https%3A%2F%2Fh5.m.taobao.com&loginFrom=WAP_TAOBAO&style=&bind_token=&sdkInterceptType=&assets_css=&assets_js=&ssottid=&nv=false&otherLoginUrl=https%3A%2F%2Flogin.m.taobao.com%2Fmsg_login.htm%3Fttid%3Dh5%2540iframe%26redirectURL%3Dhttps%253A%252F%252Fh5.m.taobao.com%252Fother%252Floginend.html%253Forigin%253Dhttps%25253A%25252F%25252Fh5.m.taobao.com&TPL_timestamp=&TPL_password2="
				+ TPL_password_2 + "&page=loginV3&ncoSig=&ncoSessionid=&ncoToken=&TPL_username=" + username
				+ "&um_token=" + um_token + "&ua=" + ua;
		String result = HttpUrlConnectUtils.httpPost(url, postData, null, null);
		logger.info("login result={}", result);
		if (StringUtils.isNotBlank(result) && result.contains("mytaobao"))
			return true;
		return false;
	}

	/**
	 * 单品订单详细
	 * 
	 * @param productId
	 * @return
	 */
	public static JSONObject productTmallDetailOne(String productId) {
		String url = "https://detail.tmall.com/item.htm?id=" + productId;
		String content = HttpUrlConnectUtils.httpGet(url, true);
		Document all = Jsoup.parse(content);
		Elements scripts = all.getElementsByTag("script");
		String data = "";
		for (Element script : scripts) {
			if (script.html().contains("w.g_config")) {
				data = script.html();
				break;
			}
		}
		String itemId = data.substring(data.indexOf("itemId") + 8, data.indexOf("\"", data.indexOf("itemId") + 9));
		String sellerId = data.substring(data.indexOf("sellerId") + 10,
				data.indexOf("\"", data.indexOf("sellerId") + 11));
		String shopId = data.substring(data.indexOf("shopId") + 8, data.indexOf("\"", data.indexOf("shopId") + 9));
		String categoryId = data.substring(data.indexOf("categoryId") + 11,
				data.indexOf(",", data.indexOf("categoryId") + 12));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("itemId", itemId);
		jsonObj.put("sellerId", sellerId);
		jsonObj.put("shopId", shopId);
		jsonObj.put("categoryId", categoryId);
		Elements title = all.getElementsByAttributeValue("name", "title");
		Elements x_id = all.getElementsByAttributeValue("name", "x_id");
		Elements seller_id = all.getElementsByAttributeValue("name", "seller_id");
		Elements seller_nickname = all.getElementsByAttributeValue("name", "seller_nickname");
		Elements who_pay_ship = all.getElementsByAttributeValue("name", "who_pay_ship");
		Elements photo_url = all.getElementsByAttributeValue("name", "photo_url");
		Elements region = all.getElementsByAttributeValue("name", "region");
		Elements auto_post = all.getElementsByAttributeValue("name", "auto_post");
		Elements etm = all.getElementsByAttributeValue("name", "etm");
		Elements virtual = all.getElementsByAttributeValue("name", "virtual");
		Elements rootCatId = all.getElementsByAttributeValue("name", "rootCatId");
		Elements auto_post1 = all.getElementsByAttributeValue("name", "auto_post1");
		Elements buyer_from = all.getElementsByAttributeValue("name", "buyer_from");
		jsonObj.put("title", title != null ? encoder(title.val()) : "");
		jsonObj.put("x_id", x_id != null ? encoder(x_id.val()) : "");
		jsonObj.put("seller_id", seller_id != null ? encoder(seller_id.val()) : "");
		jsonObj.put("seller_nickname", seller_nickname != null ? encoder(seller_nickname.val()) : "");
		jsonObj.put("who_pay_ship", who_pay_ship != null ? encoder(who_pay_ship.val()) : "");
		jsonObj.put("photo_url", photo_url != null ? encoder(photo_url.val()) : "");
		jsonObj.put("region", region != null ? encoder(region.val()) : "");
		jsonObj.put("auto_post", auto_post != null ? encoder(auto_post.val()) : "");
		jsonObj.put("etm", etm != null ? encoder(etm.val()) : "");
		jsonObj.put("virtual", virtual != null ? encoder(virtual.val()) : "");
		jsonObj.put("rootCatId", rootCatId != null ? encoder(rootCatId.val()) : "");
		jsonObj.put("auto_post1", auto_post1 != null ? encoder(auto_post1.val()) : "");
		jsonObj.put("buyer_from", buyer_from != null ? encoder(buyer_from.val()) : "");
		String paramData = content.substring(content.indexOf("TShop.Setup(") + 12,
				content.indexOf(");", content.indexOf("TShop.Setup(") + 13));
		JSONObject paramJson = JSONObject.parseObject(paramData);
		String allow_quantity = paramJson.getJSONObject("itemDO").getString("quantity");
		String price = paramJson.getJSONObject("itemDO").getString("reservePrice");
		jsonObj.put("allow_quantity", allow_quantity);
		jsonObj.put("price", price);
		logger.info(jsonObj.toJSONString());
		return jsonObj;
	}

	private static String encoder(String s) {
		try {
			return URLEncoder.encode(s, "gb2312");
		} catch (UnsupportedEncodingException e) {
			return s;
		}
	}

	public static boolean preconfirmtmallorderonly(String itemId, String shopId) {
		String url = "https://buy.tmall.com/login/buy.do?from=itemDetail&var=login_indicator&id=" + itemId + "&shop_id="
				+ shopId + "&cart_ids=&t=" + new Date().getTime();
		String content = HttpUrlConnectUtils.httpGet(url, true);
		logger.info("logined = {}", content);
		if (StringUtils.isNotBlank(content) && content.contains("login_indicator")) {
			content = content.replaceAll("login_indicator=", "");
			return JSONObject.parseObject(content).getBooleanValue("hasLoggedIn");
		}
		return false;
	}

	/**
	 * 单品确认定单
	 * 
	 * @param param
	 * @param userId
	 * @return
	 */
	public static boolean confirmtmallorderonly(JSONObject param, String userId) {
		String url = "https://buy.tmall.com/order/confirm_order.htm?x-itemid=" + param.getString("itemId") + "&x-uid="
				+ userId;
		String postData = "title=" + param.getString("title") + "&x_id=" + param.getString("x_id") + "&seller_id="
				+ param.getString("seller_id") + "&seller_nickname=" + param.getString("seller_nickname")
				+ "&who_pay_ship=" + param.getString("who_pay_ship") + "&photo_url=" + param.getString("photo_url")
				+ "&region=" + param.getString("region") + "&auto_post=" + param.getString("auto_post") + "&etm="
				+ param.getString("etm") + "&virtual=" + param.getString("virtual") + "&rootCatId="
				+ param.getString("rootCatId") + "&auto_post1=" + param.getString("auto_post1") + "&buyer_from="
				+ param.getString("buyer_from") + "&root_refer=&item_url_refer=&allow_quantity="
				+ param.getString("allow_quantity") + "&buy_param=" + param.getString("itemId")
				+ "_1_0&quantity=1&_tb_token_=" + tk
				+ "&skuInfo=&use_cod=false&_input_charset=UTF-8&destination=340100&skuId=0&bankfrom=&from_etao=&item_id_num="
				+ param.getString("itemId") + "&item_id=" + param.getString("itemId") + "&auction_id="
				+ param.getString("itemId")
				+ "&seller_rank=0&seller_rate_sum=0&is_orginal=no&point_price=false&secure_pay=true&pay_method=%BF%EE%B5%BD%B7%A2%BB%F5&from=item_detail&buy_now="+ param.getString("price")+"&current_price="+ param.getString("price")+"&auction_type=b&seller_num_id="
				+ param.getString("sellerId") + "&activity=&chargeTypeId=";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Cookie", cookie);
		String result = HttpUrlConnectUtils.httpPost(url, postData, headers, null);
		try {
			if (result.contains("亲，小二正忙，滑动一下马上回来")) {
				logger.info("亲，小二正忙，滑动一下马上回来");
				return false;
			}
			if(result.contains("login.taobao.com")) {
				logger.info("未登陆");
				return false;
			}
			Elements scripts = Jsoup.parse(result).getElementsByTag("script");
			String data02 = "";
			for (Element script : scripts) {
				if (script.html().contains("var orderData")) {
					data02 = script.html();
					break;
				}
			}
			logger.info(data02);
			if (StringUtils.isBlank(data02)) {
				return false;
			}
			int start = data02.indexOf("{", 8);
			int end = data02.lastIndexOf("}") + 1;
			data02 = data02.substring(start, end);
			JSONObject jsonObj = JSONObject.parseObject(data02);
			hierarchy = encoder(jsonObj.getString("hierarchy"));
			JSONObject oldData = jsonObj.getJSONObject("data");
			JSONObject newData = new JSONObject();
			for (String key : oldData.keySet()) {
				if (key.contains("agencyPay_") || key.contains("anonymous_") || key.contains("ctDeliverySolution_")
						|| key.contains("deliveryMethod_") || key.contains("frontTrace_") || key.contains("memo_")
						|| key.contains("ncCheckCode_") || key.contains("postageInsurance_")
						|| key.contains("promotion_") || key.contains("submitOrder_")) {
					newData.put(key, oldData.get(key));
				}
			}
			newData.getJSONObject("submitOrder_1").getJSONObject("fields").put("ua", ua);
			data = newData.toJSONString();
			linkage = encoder(jsonObj.getString("linkage"));
			JSONObject confirmOrder = jsonObj.getJSONObject("data").getJSONObject("confirmOrder_1");
			JSONObject fields = confirmOrder.getJSONObject("fields");
			submitOrderUrl = "https://buy.tmall.com" + fields.getString("pcSubmitUrl") + "&"
					+ fields.getString("secretKey") + "=" + fields.getString("secretValue") + "&sparam1="
					+ fields.getString("sparam1") + "&sparam2=" + fields.getString("sparam2");
			logger.info(submitOrderUrl);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 提交单品订单
	 * 
	 * @return
	 */
	public static boolean submittmallorderonly() {
		String postData = "_tb_token_=" + tk
				+ "&action=order/multiTerminalSubmitOrderAction&event_submit_do_confirm=1&input_charset=utf-8&praper_alipay_cashier_domain=cashierrz29&authYiYao=&authHealth=&F_nick=&hierarchy="
				+ hierarchy + "&data=" + data + "&linkage=" + linkage;
		logger.info(postData);
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Cookie", cookie);

		String result = HttpUrlConnectUtils.httpPost(submitOrderUrl, postData, headers, "gbk");
		logger.info(result);
		try {
			if (result.contains("亲，小二正忙，滑动一下马上回来")) {
				logger.info("亲，小二正忙，滑动一下马上回来");
				return false;
			} else if (result.contains("正在创建支付宝安全链接")) {
				logger.info("订单提交成功.");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public static JSONObject productTmallDetail(String productId, String skuId) {
		String url = "https://detail.tmall.com/item.htm?id=" + productId + "&skuId=" + skuId + "";
		String content = HttpUrlConnectUtils.httpGet(url, true);
		Document all = Jsoup.parse(content);
		//logger.info(all.toString());
		Elements scripts = all.getElementsByTag("script");
		String data = "";
		for (Element script : scripts) {
			if (script.html().contains("w.g_config")) {
				data = script.html();
				break;
			}
		}
		String itemId = data.substring(data.indexOf("itemId") + 8, data.indexOf("\"", data.indexOf("itemId") + 9));
		String sellerId = data.substring(data.indexOf("sellerId") + 10,
				data.indexOf("\"", data.indexOf("sellerId") + 11));
		String shopId = data.substring(data.indexOf("shopId") + 8, data.indexOf("\"", data.indexOf("shopId") + 9));
		String categoryId = data.substring(data.indexOf("categoryId") + 11,
				data.indexOf(",", data.indexOf("categoryId") + 12));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("itemId", itemId);
		jsonObj.put("skuId", skuId);
		jsonObj.put("sellerId", sellerId);
		jsonObj.put("shopId", shopId);
		jsonObj.put("categoryId", categoryId);
		Elements title = all.getElementsByAttributeValue("name", "title");
		Elements x_id = all.getElementsByAttributeValue("name", "x_id");
		Elements seller_id = all.getElementsByAttributeValue("name", "seller_id");
		Elements seller_nickname = all.getElementsByAttributeValue("name", "seller_nickname");
		Elements who_pay_ship = all.getElementsByAttributeValue("name", "who_pay_ship");
		Elements photo_url = all.getElementsByAttributeValue("name", "photo_url");
		Elements region = all.getElementsByAttributeValue("name", "region");
		Elements auto_post = all.getElementsByAttributeValue("name", "auto_post");
		Elements etm = all.getElementsByAttributeValue("name", "etm");
		Elements virtual = all.getElementsByAttributeValue("name", "virtual");
		Elements rootCatId = all.getElementsByAttributeValue("name", "rootCatId");
		Elements auto_post1 = all.getElementsByAttributeValue("name", "auto_post1");
		Elements buyer_from = all.getElementsByAttributeValue("name", "buyer_from");
		jsonObj.put("title", title != null ? encoder(title.val()) : "");
		jsonObj.put("x_id", x_id != null ? encoder(x_id.val()) : "");
		jsonObj.put("seller_id", seller_id != null ? encoder(seller_id.val()) : "");
		jsonObj.put("seller_nickname", seller_nickname != null ? encoder(seller_nickname.val()) : "");
		jsonObj.put("who_pay_ship", who_pay_ship != null ? encoder(who_pay_ship.val()) : "");
		jsonObj.put("photo_url", photo_url != null ? encoder(photo_url.val()) : "");
		jsonObj.put("region", region != null ? encoder(region.val()) : "");
		jsonObj.put("auto_post", auto_post != null ? encoder(auto_post.val()) : "");
		jsonObj.put("etm", etm != null ? encoder(etm.val()) : "");
		jsonObj.put("virtual", virtual != null ? encoder(virtual.val()) : "");
		jsonObj.put("rootCatId", rootCatId != null ? encoder(rootCatId.val()) : "");
		jsonObj.put("auto_post1", auto_post1 != null ? encoder(auto_post1.val()) : "");
		jsonObj.put("buyer_from", buyer_from != null ? encoder(buyer_from.val()) : "");
		String paramData = content.substring(content.indexOf("TShop.Setup(") + 12,
				content.indexOf(");", content.indexOf("TShop.Setup(") + 13));
		JSONObject paramJson = JSONObject.parseObject(paramData);
		String allow_quantity = paramJson.getJSONObject("itemDO").getString("quantity");
		String price = paramJson.getJSONObject("itemDO").getString("reservePrice");
		jsonObj.put("allow_quantity", allow_quantity);
		jsonObj.put("price", price);
		logger.info(jsonObj.toJSONString());
		return jsonObj;
	}

	public static boolean confirmtmallorder(JSONObject param, String userId) {
		String url = "https://buy.tmall.com/order/confirm_order.htm?x-itemid=" + param.getString("itemId") + "&x-uid="
				+ userId;
		String postData = "title=" + param.getString("title") + "&x_id=" + param.getString("x_id") + "&seller_id="
				+ param.getString("seller_id") + "&seller_nickname=" + param.getString("seller_nickname")
				+ "&who_pay_ship=" + param.getString("who_pay_ship") + "&photo_url=" + param.getString("photo_url")
				+ "&region=" + param.getString("region") + "&auto_post=" + param.getString("auto_post") + "&etm="
				+ param.getString("etm") + "&virtual=" + param.getString("virtual") + "&rootCatId="
				+ param.getString("rootCatId") + "&auto_post1=" + param.getString("auto_post1") + "&buyer_from="
				+ param.getString("buyer_from") + "&root_refer=&item_url_refer=&allow_quantity="
				+ param.getString("allow_quantity") + "&buy_param=" + param.getString("itemId") + "_1_"
				+ param.getString("skuId") + "&quantity=1&_tb_token_=" + tk
				+ "&skuInfo=&use_cod=false&_input_charset=UTF-8&destination=340100&skuId=" + param.getString("skuId")
				+ "&bankfrom=&from_etao=&item_id_num=" + param.getString("itemId") + "&item_id="
				+ param.getString("itemId") + "&auction_id=" + param.getString("itemId")
				+ "&seller_rank=0&seller_rate_sum=0&is_orginal=no&point_price=false&secure_pay=true&pay_method=%BF%EE%B5%BD%B7%A2%BB%F5&from=item_detail&buy_now="
				+ param.getString("price") + "&current_price=" + param.getString("price")
				+ "&auction_type=b&seller_num_id=" + param.getString("sellerId") + "&activity=&chargeTypeId=";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Cookie", cookie);
		String result = HttpUrlConnectUtils.httpPost(url, postData, headers, null);
		logger.info(result);
		try {
			if (result.contains("亲，小二正忙，滑动一下马上回来")) {
				logger.info("亲，小二正忙，滑动一下马上回来");
				return false;
			}
			if(result.contains("login.taobao.com")) {
				logger.info("未登陆");
				return false;
			}
			Elements scripts = Jsoup.parse(result).getElementsByTag("script");
			String data02 = "";
			for (Element script : scripts) {
				if (script.html().contains("var orderData")) {
					data02 = script.html();
					break;
				}
			}
			logger.info(data02);
			if (StringUtils.isBlank(data02)) {
				return false;
			}
			int start = data02.indexOf("{", 8);
			int end = data02.lastIndexOf("}") + 1;
			data02 = data02.substring(start, end);
			JSONObject jsonObj = JSONObject.parseObject(data02);
			hierarchy = encoder(jsonObj.getString("hierarchy"));
			JSONObject oldData = jsonObj.getJSONObject("data");
			JSONObject newData = new JSONObject();
			for (String key : oldData.keySet()) {
				if (key.contains("agencyPay_") || key.contains("anonymous_") || key.contains("ctDeliverySolution_")
						|| key.contains("deliveryMethod_") || key.contains("frontTrace_") || key.contains("memo_")
						|| key.contains("ncCheckCode_") || key.contains("postageInsurance_")
						|| key.contains("promotion_") || key.contains("submitOrder_")) {
					newData.put(key, oldData.get(key));
				}
			}
			newData.getJSONObject("submitOrder_1").getJSONObject("fields").put("ua", ua);
			data = newData.toJSONString();
			linkage = encoder(jsonObj.getString("linkage"));
			JSONObject confirmOrder = jsonObj.getJSONObject("data").getJSONObject("confirmOrder_1");
			JSONObject fields = confirmOrder.getJSONObject("fields");
			submitOrderUrl = "https://buy.tmall.com" + fields.getString("pcSubmitUrl") + "&"
					+ fields.getString("secretKey") + "=" + fields.getString("secretValue") + "&sparam1="
					+ fields.getString("sparam1") + "&sparam2=" + fields.getString("sparam2");
			logger.info(submitOrderUrl);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean submittmallorder() {
		String postData = "_tb_token_=" + tk
				+ "&action=order/multiTerminalSubmitOrderAction&event_submit_do_confirm=1&input_charset=utf-8&praper_alipay_cashier_domain=cashierrz29&authYiYao=&authHealth=&F_nick=&hierarchy="
				+ hierarchy + "&data=" + data + "&linkage=" + linkage;
		logger.info(postData);
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Cookie", cookie);

		String result = HttpUrlConnectUtils.httpPost(submitOrderUrl, postData, headers, "gbk");
		try {
			if (result.contains("亲，小二正忙，滑动一下马上回来")) {
				logger.info("亲，小二正忙，滑动一下马上回来");
				return false;
			} else if (result.contains("正在创建支付宝安全链接")) {
				logger.info("订单提交成功.");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

}
