package com.atao.dftt.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.atao.base.mapper.BaseMapper;
import com.atao.base.service.BaseService;
import com.atao.base.util.StringUtils;
import com.atao.dftt.http.JdHttp;
import com.atao.dftt.mapper.JdProductMapper;
import com.atao.dftt.model.JdProduct;
import com.atao.dftt.task.JdMsOrderTask;
import com.atao.dftt.task.JdOrderTask;

import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

/**
 *
 * @author twang
 */
@Service
public class JdProductWyService extends BaseService<JdProduct> {

	@Resource
	private JdProductMapper jdProductMapper;

	@Override
	public BaseMapper<JdProduct> getMapper() {
		return jdProductMapper;
	}

	public void oneYuanBuy() {
		logger.info("开始解析一元购商品。");
		JSONObject json = JdHttp.oneYuanBuy();
		String skuId = json.getString("skuId");
		Date startTime = json.getDate("startTime");
		JdProduct p = new JdProduct();
		p.setProductId(skuId);
		p.setStartTime(startTime);
		JdProduct jdProduct = super.queryOne(p, null);
		if (jdProduct == null) {
			jdProduct = new JdProduct();
			jdProduct.setProductId(skuId);
			jdProduct.setProductName("每天10点商品");
			jdProduct.setMsType(Integer.valueOf(0));
			jdProduct.setPrice(1d);
			jdProduct.setStartTime(startTime);
			jdProduct.setYuyue(false);
			jdProduct.setResult(false);
			super.insert(jdProduct);
		}
	}

	public void startMsProduct() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.HOUR, 1);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		Date startTime = c.getTime();
		JdProduct t = new JdProduct();
		t.setStartTime(startTime);
		List<JdProduct> products = super.queryList(t, null);
		if (products != null && products.size() > 0) {
			JdProduct product = products.get(0);
			if (product.getMsType().intValue() == 2) {
				Timer timer = new Timer();
				/*
				 * JdMobileLoginUtil mobileJd = JdMobileLoginUtil.getInstance(jdUser);
				 * JdCouponTask task = new JdCouponTask(timer, mobileJd, product);
				 * timer.schedule(task, new Date());
				 */
			} else {
				boolean cjms = JdHttp.isCjms(product.getProductId());
				product.setMsType(Integer.valueOf(cjms ? 1 : 0));
				super.updateBySelect(product);
				if (cjms) {
					Timer timer2 = new Timer();
					JdMsOrderTask task2 = new JdMsOrderTask(timer2, product);
					timer2.schedule(task2, new Date());
				} else {
					Timer timer = new Timer();
					JdOrderTask task = new JdOrderTask(timer, product);
					timer.schedule(task, new Date());
				}
			}

		}
	}

	@Override
	public Weekend<JdProduct> genSqlExample(JdProduct t) {
		Weekend<JdProduct> w = super.genSqlExample(t);
		WeekendCriteria<JdProduct, Object> c = w.weekendCriteria();
		if (StringUtils.isNotBlank(t.getProductId())) {
			c.andEqualTo(JdProduct::getProductId, t.getProductId());
		}
		if (t.getMsType() != null) {
			c.andEqualTo(JdProduct::getMsType, t.getMsType());
		}
		if (t.getYuyue() != null) {
			c.andEqualTo(JdProduct::getYuyue, t.getYuyue());
		}
		if (t.getStartTime() != null) {
			c.andEqualTo(JdProduct::getStartTime, t.getStartTime());
		}
		if (StringUtils.isNotBlank(t.getProductName())) {
			c.andEqualTo(JdProduct::getProductName, t.getProductName());
		}
		w.and(c);
		return w;
	}
}
