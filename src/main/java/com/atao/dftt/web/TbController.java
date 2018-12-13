package com.atao.dftt.web;

import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atao.base.controller.BaseController;
import com.atao.base.service.BaseService;
import com.atao.base.util.StringUtils;
import com.atao.dftt.http.JdHttp;
import com.atao.dftt.model.JdProduct;
import com.atao.dftt.service.JdProductWyService;
import com.atao.dftt.task.TbOrderOneTask;
import com.atao.dftt.task.TbOrderTask;
import com.atao.util.DateUtils;

/**
 *
 * @author twang
 */
@RestController
@RequestMapping("/tb")
public class TbController extends BaseController<JdProduct> {
	@Autowired
	private JdProductWyService jdProductWyService;

	@Override
	protected BaseService<JdProduct> getService() {
		return jdProductWyService;
	}

	@RequestMapping("/tmmsone")
	public Object tmmsone(String id, String startTime) {
		Date startDate = DateUtils.parseDate(startTime, "yyyyMMddHHmmss");
		Timer timer = new Timer();
		TbOrderOneTask task = new TbOrderOneTask(timer, id, startDate);
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		c.add(Calendar.MINUTE, -3);
		timer.schedule(task, c.getTime());
		logger.info("tmmsone id={},startTime={}", id, startTime);
		return null;
	}

	@RequestMapping("/tmms")
	public Object tmms(String id, String skuId, String startTime) {
		Date startDate = DateUtils.parseDate(startTime, "yyyyMMddHHmmss");
		Timer timer = new Timer();
		TbOrderTask task = new TbOrderTask(timer, id, skuId, startDate);
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		c.add(Calendar.MINUTE, -3);
		timer.schedule(task, c.getTime());
		logger.info("tmms id={},startTime={}", id, startTime);
		return null;
	}

	@RequestMapping("/login")
	public void login() throws URISyntaxException {
		//boolean success = JdHttp.login();
		String url = JdHttp.itemShowBtn("1350411338");
		logger.info(url);
		String secUrl = JdHttp.userRouting("1350411338", url);
		if (StringUtils.isNotBlank(secUrl)) {
			JdHttp.submitMsOrder("1350411338", secUrl);
		}
	}
}
