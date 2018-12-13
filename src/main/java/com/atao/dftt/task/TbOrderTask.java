package com.atao.dftt.task;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.atao.dftt.http.TbHttp;

public class TbOrderTask extends TimerTask {
	private static Logger logger = LoggerFactory.getLogger(TbHttp.class);
	private Timer timer;
	private String itemId;
	private String skuId;
	private Date startTime;

	public TbOrderTask(Timer timer, String itemId, String skuId, Date startTime) {
		super();
		this.timer = timer;
		this.itemId = itemId;
		this.skuId = skuId;
		this.startTime = startTime;
	}

	@Override
	public void run() {
		logger.info("tb启动秒杀定单任务.");
		JSONObject object = TbHttp.productTmallDetail(itemId, skuId);
		logger.info(object.toJSONString());
		long endTime = startTime.getTime() + 60000L;
		long lastTime = startTime.getTime() - new Date().getTime();
		logger.info("所有都准备好了");
		if (lastTime > 0L) {
			try {
				logger.info("时间还早,休眠:" + lastTime / 1000l + "秒.");
				Thread.sleep(lastTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		logger.info("开始抢购....");
		boolean success = false;
		while ((!success) && (endTime > new Date().getTime())) {
			try {
				success = TbHttp.confirmtmallorder(object, TbHttp.userId);
				logger.info("是否成功确认订单:" + success);
				if (!success) {
					Thread.sleep(1000L);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		boolean r = false;
		while ((!r) && (endTime > new Date().getTime())) {
			r = TbHttp.submittmallorder();
			logger.info("是否成功提交订单:" + r);
		}

		this.timer.cancel();
	}

}
