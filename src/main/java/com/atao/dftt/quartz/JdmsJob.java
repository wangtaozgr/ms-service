package com.atao.dftt.quartz;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.atao.dftt.service.JdProductWyService;

@Component
public class JdmsJob {
	@Resource
	private JdProductWyService jdProductWyService;

	/**
	 * 京东一元购秒杀
	 */
	@Scheduled(cron = "0 56 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23 * * ?")
	public void jdOneBuyMs() {
		jdProductWyService.startMsProduct();
	}

	/**
	 * 京东一元购秒杀
	 */
	@Scheduled(cron = "0 0 18,22 * * ?")
	public void oneYuanBuy() {
		jdProductWyService.oneYuanBuy();
	}
}