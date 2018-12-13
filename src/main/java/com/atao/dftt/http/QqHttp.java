package com.atao.dftt.http;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.CookieHandler;
import java.net.CookieManager;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.atao.dftt.util.ChaoJiYing;
import com.atao.dftt.util.FileUtils;
import com.atao.dftt.util.HttpUrlConnectUtils;
import com.atao.util.StringUtils;

@Component
public class QqHttp {
	private static Logger logger = LoggerFactory.getLogger(QqHttp.class);
	private static String qq = "83260457";

	private static String verifycode() {
		String url = "https://ssl.ptlogin2.qq.com/check?regmaster=&pt_tea=2&pt_vcode=1&uin=" + qq
				+ "&appid=716027609&js_ver=10284&js_type=1&login_sig=&u1=https%3A%2F%2Fgraph.qq.com%2Foauth2.0%2Flogin_jump&r=0.8353598835065226&pt_uistyle=40&pt_jstoken=1640675896";
		String result = HttpUrlConnectUtils.httpGet(url, false);
		logger.info(result);
		result = result.replace("ptui_checkVC(", "");
		result = result.replace(")", "");
		String[] authCodes = result.split(",");
		String isYz = authCodes[0].replaceAll("'", "");
		String capCd = authCodes[1].replaceAll("'", "");
		String verifycode = "";
		if ("0".equals(isYz)) {
			verifycode = capCd;
		} else {
			/*JSONObject yzmJSON = JSONObject.parseObject(getImageCode(capCd));
			logger.info("yzmJSON={}", yzmJSON);
			if ("0".equals(yzmJSON.getString("err_no"))) {
				verifycode = yzmJSON.getString("pic_str");
			}*/
			return "";
		}
		return verifycode;
	}

	private static String getImageCode(String capCd) {
		String url = "https://ssl.captcha.qq.com/getimage?aid=716027609&r=0.6472875226754695&uin=" + qq + "&cap_cd="
				+ capCd;
		try {
			byte[] byteArr = FileUtils.getByte(url);
			return ChaoJiYing.sendPic(byteArr);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return null;
	}

	public static String qqLogin() {
		String verifycode = verifycode();
		logger.info("verifycode={}", verifycode);
		if (StringUtils.isNotBlank(verifycode)) {
			String pwd = encryptPassword(null, "wangtao", verifycode);
			logger.info("pwd={}", pwd);
			CookieManager manager = (CookieManager) CookieHandler.getDefault();
			String ptvfsession = HttpUrlConnectUtils.getCookie(manager.getCookieStore(), "ptvfsession");
			logger.info("ptvfsession={}", ptvfsession);
			String url = "https://ssl.ptlogin2.qq.com/login?u=" + qq + "&verifycode=" + verifycode
					+ "&pt_vcode_v1=0&pt_verifysession_v1=" + ptvfsession + "&p=" + pwd
					+ "&pt_randsalt=2&pt_jstoken=1921437550&u1=https%3A%2F%2Fgraph.qq.com%2Foauth2.0%2Flogin_jump&ptredirect=0&h=1&t=1&g=1&from_ui=1&ptlang=2052&action=3-10-1540888498525&js_ver=10284&js_type=1&login_sig=&pt_uistyle=40&aid=716027609&daid=383&pt_3rd_aid=100273020&";
			String result = HttpUrlConnectUtils.httpGet(url, false);
			logger.info("result={}", result);
			result = result.replace("ptuiCB(", "");
			result = result.replace(")", "");
			String[] authCodes = result.split(",");
			String callbackUrl = authCodes[2].replaceAll("'", "");
			logger.info("callbackUrl={}", callbackUrl);
			return callbackUrl;
		} else {
			logger.info("qq需要验证登陆了，不需要再登陆了");
			return "";
		}

	}

	public static String check_sig(String url) {
		String result = HttpUrlConnectUtils.httpGet(url, false);
		logger.info("check_sig={}", result);
		return result;
	}

	public static String authorize(String postData) {
		String url = "https://graph.qq.com/oauth2.0/authorize";
		String result = HttpUrlConnectUtils.httpPost(url, postData, null, null);
		logger.info("authorize={}", result);
		return result;
	}

	public static int getToken(String p_skey) {
		int hash = 5381;
		for (int i = 0, len = p_skey.length(); i < len; ++i) {
			hash += (hash << 5) + p_skey.charAt(i);
		}
		return hash & 0x7fffffff;
	}

	private static String encryptPassword(String uin, String password, String verifycode) {
		String p_result = "";
		try {
			verifycode = verifycode.toUpperCase();
			ScriptEngineManager sem = new ScriptEngineManager();
			ScriptEngine engine = sem.getEngineByName("js");
			// FileReader fr = new
			// FileReader("D:\\work\\workspace\\dftt-service\\src\\main\\resources\\login.js");
			// System.out.println(QqHttp.class.getResource("/login.js").getPath());
			// ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			Resource resource = new ClassPathResource("login.js");
			// logger.info("login.js path = {}",
			// classLoader.getResource("login.js").getPath());
			Reader fr = new InputStreamReader(resource.getInputStream());
			engine.eval(fr);
			Invocable inv = (Invocable) engine;
			p_result = inv.invokeFunction("getEncryption", password, uin, verifycode).toString();
		} catch (ScriptException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p_result;
	}

	public static void main(String[] args) {
		logger.info(QqHttp.verifycode());

	}
}
