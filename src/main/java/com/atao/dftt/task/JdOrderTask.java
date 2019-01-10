package com.atao.dftt.task;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atao.dftt.http.JdHttp;
import com.atao.dftt.model.JdProduct;

public class JdOrderTask extends TimerTask {
	private static Logger logger = LoggerFactory.getLogger(JdOrderTask.class);
	private Timer timer;
	private JdProduct product;

	public JdOrderTask(Timer timer, JdProduct product) {
		this.timer = timer;
		this.product = product;
	}

	public void run() {
		logger.info(":启动秒杀定单任务.");
		boolean loginSuc = JdHttp.login();
		int loginNum = 0;
		while (!loginSuc && loginNum < 100) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			loginSuc = JdHttp.login();
			loginNum++;
		}
		logger.info("登陆结果.success={}", loginSuc);

		if (this.product.getYuyue()) {
			int r = JdHttp.submitEasybuyOrder(this.product.getProductId());
			if (r == 0) {
				JdHttp.delProduct();
				JdHttp.addProduct(this.product.getProductId());
			}
		} else {
			JdHttp.delProduct();
			JdHttp.addProduct(this.product.getProductId());
		}
		// JdHttp.selectCoupons();
		Date startTime = this.product.getStartTime();
		long endTime = startTime.getTime() + 300000L;
		double price = Double.valueOf(JdHttp.getNewPrice()).doubleValue();
		while (price == 0d && (endTime > new Date().getTime())) {
			price = Double.valueOf(JdHttp.getNewPrice()).doubleValue();
		}
		logger.info(":所有都准备好了");

		long lastTime = startTime.getTime() - new Date().getTime();
		if (lastTime > 0L) {
			try {
				logger.info(":时间还早,休眠:" + lastTime / 1000L + "秒.");
				Thread.sleep(lastTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		/*boolean start = false;// 是否有库存
		while (!start && (endTime-240000L > new Date().getTime())) {
			start = JdHttp.isStockState(product.getProductId());
		}*/
		
		boolean success = false;
		while ((!success) && (endTime > new Date().getTime())) {
			try {
				logger.info(":开始生成订单时间");
				int code = JdHttp.submitOrder();
				logger.info(":结束生成订单时间");
				if (code == 0) {
					success = true;
					break;
				}
				if (code == 61040) {
					logger.info("活动已结束，");
					success = true;
					break;
				}
				if (code == 61036) {
					logger.info("正在进行预约抢购活动，暂不支持购买");
					success = true;
					break;
				}
				if (code == 60017) {
					logger.info("您多次提交过快，请稍后再试");
				}
				if (code == 600158) {
					logger.info("商品无货!  ");
				}
				Thread.sleep(5000L);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info(":结束秒杀定单任务");
		this.timer.cancel();
	}
}
