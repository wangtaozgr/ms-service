package com.atao.dftt.task;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atao.dftt.http.JdHttp;
import com.atao.dftt.model.JdProduct;
import com.atao.dftt.util.SmsUtils;
import com.atao.util.DateUtils;
import com.atao.util.StringUtils;

public class JdMsOrderTask extends TimerTask {
	private static Logger logger = LoggerFactory.getLogger(JdMsOrderTask.class);
	private Timer timer;
	private JdProduct product;

	public JdMsOrderTask(Timer timer, JdProduct product) {
		this.timer = timer;
		this.product = product;
	}

	public void run() {
		logger.info("启动生成超级秒杀定单任务.");
		Date startTime = this.product.getStartTime();
		long endTime = startTime.getTime() + 60000L;
		boolean success = JdHttp.login();
		int loginNum = 0;
		while(!success && loginNum<100) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			success = JdHttp.login();
			loginNum ++;
		}
		logger.info("登陆结果.success={}",success);
		long lastTime = startTime.getTime() - new Date().getTime();
		if (lastTime > 0L) {
			try {
				logger.info("时间还早,休眠:" + lastTime / 1000L + "秒.");
				Thread.sleep(lastTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		logger.info("提前0秒开始发起请求.");
		success = false;
		String url = JdHttp.itemShowBtn(this.product.getProductId());
		while ((!success) && (endTime > new Date().getTime())) {
			try {
				if (StringUtils.isNotBlank(url)) {
					String secUrl = JdHttp.userRouting(this.product.getProductId(), url);
					if (StringUtils.isNotBlank(secUrl)) {
						success = JdHttp.submitMsOrder(this.product.getProductId(), secUrl);
						if (success) {
							String productName = this.product.getProductName();
							if (productName.length() > 10)
								productName = productName.substring(0, 10);
							String smsJson = "{\"userName\":\"wangtaowinner\", \"name\":\"" + productName
									+ "\", \"price\":\"" + this.product.getPrice() + "\", \"time\":\""
									+ DateUtils.formatDate(this.product.getStartTime(), "yyyy-MM-dd HH:mm:ss") + "\"}";
							SmsUtils.sendSms("17755117870", "SMS_122280451", smsJson);
						}
					}
				}else {
					url = JdHttp.itemShowBtn(this.product.getProductId());
				}
			} catch (Exception e) {
				e.printStackTrace();
				success = false;
			}
		}
		logger.info("结束超级秒杀定单任务");
		this.timer.cancel();
	}
}
