package com.atao.dftt.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URLDecoder;
import java.util.Date;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.alibaba.fastjson.JSONObject;
import com.atao.dftt.util.HttpUrlConnectUtils;

public class JdMiaoshaService {
	public static String qq = "83260457";

	public static void main(String[] args) throws UnsupportedEncodingException {
		CookieManager manager = new CookieManager();
		manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		CookieHandler.setDefault(manager);
		/*
		 * String verifycode = verifycode(); System.out.println(verifycode); String p =
		 * encryptPassword(null, "wangtao", verifycode); System.out.println(p); String
		 * ptvfsession = HttpUrlConnectUtils.getCookie(manager.getCookieStore(),
		 * "ptvfsession"); System.out.println(ptvfsession);
		 */
		JSONObject object = jdQqLoginUrl();
		String url = qqLogin();
		HttpUrlConnectUtils.printCookie(manager.getCookieStore());
		check_sig(url);
		HttpUrlConnectUtils.printCookie(manager.getCookieStore());
		// String ui = HttpUrlConnectUtils.getCookie(manager.getCookieStore(), "ui");
		String ui = "A2D358D0-39E9-4EDD-AA02-04F1E6667F84";
		System.out.println(ui);
		String p_skey = HttpUrlConnectUtils.getCookie(manager.getCookieStore(), "p_skey");
		System.out.println(p_skey);
		int g_tk = getToken(p_skey);
		System.out.println(g_tk);
		String postData = "response_type=code&client_id=" + object.getString("client_id") + "&redirect_uri="
				+ object.getString("redirect_uri") + "&scope=&state=" + object.getString("state")
				+ "&switch=&from_ptlogin=1&src=1&update_auth=1&openapi=80901010&g_tk="+g_tk+"&auth_time="
				+ new Date().getTime() + "&ui=" + ui;
		// postData = URLDecoder.decode(postData,"UTF-8");
		System.out.println(postData);

		// HttpUrlConnectUtils.printCookie(manager.getCookieStore());
		String jdLoginUrl = authorize(postData);
		jdLogin(jdLoginUrl);
		HttpUrlConnectUtils.printCookie(manager.getCookieStore());
	}

	public static String jdLogin(String url) {
		String result = HttpUrlConnectUtils.httpGet(url, false);
		System.out.println(result);
		return result;
	}
	
	private static int getToken(String p_skey) {
		int hash = 5381;
		for (int i = 0, len = p_skey.length(); i < len; ++i) {
			hash += (hash << 5) + p_skey.charAt(i);
		}
		return hash & 0x7fffffff;
	}

	// ---------------jd---------------------------------

	public static JSONObject jdQqLoginUrl() {
		String url = "https://qq.jd.com/new/qq/login.aspx";
		String result = HttpUrlConnectUtils.httpGet(url, false);
		System.out.println(result);
		JSONObject object = new JSONObject();
		// https://graph.qq.com/oauth2.0/authorize?response_type=code&state=AD7301353E406C96DB8F8450908F62B6C0E917B3A28730D93B66885A77EA44FE0060CA87DEF866437A92096D88DFE295&client_id=100273020&redirect_uri=https%3A%2F%2Fqq.jd.com%2Fnew%2Fqq%2Fcallback.action%3Fview%3Dnull%26uuid%3D154def734ff949adaa0559ed6c2c0859
		result = result.replace("https://graph.qq.com/oauth2.0/authorize?", "");
		for (String item : result.split("&")) {
			String[] items = item.split("=");
			object.put(items[0], items[1]);
		}
		return object;
	}

	public static String authorize(String postData) {
		String url = "https://graph.qq.com/oauth2.0/authorize";
		String result = HttpUrlConnectUtils.httpPost(url, postData, null, null);
		System.out.println(result);
		return result;
	}

	// ---------------qq-------------------------------
	public static String verifycode() {
		String url = "https://ssl.ptlogin2.qq.com/check?regmaster=&pt_tea=2&pt_vcode=1&uin=83260457&appid=716027609&js_ver=10284&js_type=1&login_sig=&u1=https%3A%2F%2Fgraph.qq.com%2Foauth2.0%2Flogin_jump&r=0.45802674210867944&pt_uistyle=40&pt_jstoken=1921437550";
		String result = HttpUrlConnectUtils.httpGet(url, false);
		result = result.replace("ptui_checkVC(", "");
		result = result.replace(")", "");
		String[] authCodes = result.split(",");
		String verifycode = authCodes[1].replaceAll("'", "");
		String uin = authCodes[2].replaceAll("'", "");
		return verifycode;
	}

	public static String qqLogin() {
		String verifycode = verifycode();
		System.out.println(verifycode);
		String pwd = encryptPassword(null, "wangtao", verifycode);
		System.out.println(pwd);
		CookieManager manager = (CookieManager) CookieHandler.getDefault();
		String ptvfsession = HttpUrlConnectUtils.getCookie(manager.getCookieStore(), "ptvfsession");
		System.out.println(ptvfsession);
		String url = "https://ssl.ptlogin2.qq.com/login?u=" + qq + "&verifycode=" + verifycode
				+ "&pt_vcode_v1=0&pt_verifysession_v1=" + ptvfsession + "&p=" + pwd
				+ "&pt_randsalt=2&pt_jstoken=1921437550&u1=https%3A%2F%2Fgraph.qq.com%2Foauth2.0%2Flogin_jump&ptredirect=0&h=1&t=1&g=1&from_ui=1&ptlang=2052&action=3-10-1540888498525&js_ver=10284&js_type=1&login_sig=&pt_uistyle=40&aid=716027609&daid=383&pt_3rd_aid=100273020&";
		String result = HttpUrlConnectUtils.httpGet(url, false);
		System.out.println(result);
		result = result.replace("ptuiCB(", "");
		result = result.replace(")", "");
		String[] authCodes = result.split(",");
		String callbackUrl = authCodes[2].replaceAll("'", "");
		System.out.println(callbackUrl);
		return callbackUrl;
	}

	public static String check_sig(String url) {
		String result = HttpUrlConnectUtils.httpGet(url, false);
		System.out.println(result);
		return result;
	}

	private static String encryptPassword(String uin, String password, String verifycode) {
		String p_result = "";
		try {
			ScriptEngineManager sem = new ScriptEngineManager();
			ScriptEngine engine = sem.getEngineByName("js");
			FileReader fr = new FileReader("D:\\work\\workspace\\dftt-service\\src\\main\\resources\\login.js");
			engine.eval(fr);
			Invocable inv = (Invocable) engine;
			p_result = inv.invokeFunction("getEncryption", password, uin, verifycode).toString();
		} catch (ScriptException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		return p_result;
	}

}
