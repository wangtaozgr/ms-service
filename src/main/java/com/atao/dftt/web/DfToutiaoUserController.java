package com.atao.dftt.web;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atao.base.controller.BaseController;
import com.atao.base.service.BaseService;
import com.atao.dftt.model.DfToutiaoUser;
import com.atao.dftt.service.DfToutiaoUserWyService;

/**
 *
 * @author twang
 */
@RestController
@RequestMapping("${adminPath}" + DfToutiaoUserController.BASE_URL)
public class DfToutiaoUserController extends BaseController<DfToutiaoUser> {
	public static final String BASE_URL = "/DfToutiaoUser/";

	@Autowired
	private DfToutiaoUserWyService dfToutiaoUserWyService;

	@Override
	protected BaseService<DfToutiaoUser> getService() {
		return dfToutiaoUserWyService;
	}

	@RequestMapping("/queryList")
	public List<DfToutiaoUser> queryList(DfToutiaoUser dftt) {
		return dfToutiaoUserWyService.queryList(dftt);
	}

	@RequestMapping("/save")
	public String save(DfToutiaoUser dftt) {
		try {
			return dfToutiaoUserWyService.saveDftt(dftt);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return "111";
	}

	@RequestMapping("/bindAccId")
	public String bindAccId(String accId, String device, String imei)
			throws IllegalAccessException, InvocationTargetException {
		return dfToutiaoUserWyService.bindAccId(accId, device, imei);
	}

	@RequestMapping("/loginReturnMsg")
	public String loginReturnMsg(String result, String device, String imei)
			throws IllegalAccessException, InvocationTargetException {
		return dfToutiaoUserWyService.loginReturnMsg(result, device, imei);
	}
}
