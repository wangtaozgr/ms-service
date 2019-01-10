package com.atao.dftt.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atao.base.controller.BaseController;
import com.atao.base.service.BaseService;
import com.atao.dftt.model.JdProduct;
import com.atao.dftt.service.JdProductWyService;

/**
 *
 * @author twang
 */
@RestController
@RequestMapping("/jd")
public class JdProductController extends BaseController<JdProduct> {
	public static final String BASE_URL = "/JdProduct/";

	@Autowired
	private JdProductWyService jdProductWyService;

	@Override
	protected BaseService<JdProduct> getService() {
		return jdProductWyService;
	}

	@RequestMapping("/test")
	public void test() {
		jdProductWyService.oneYuanBuy();
	}
}
