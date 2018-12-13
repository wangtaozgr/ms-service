package com.atao.dftt.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.atao.base.mapper.BaseMapper;
import com.atao.base.service.BaseService;
import com.atao.base.util.BeanUtils;
import com.atao.base.util.StringUtils;
import com.atao.dftt.api.vo.DataVo;
import com.atao.dftt.mapper.DfToutiaoUserMapper;
import com.atao.dftt.model.DfToutiaoUser;

import sun.security.util.ResourcesMgr;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

/**
 *
 * @author twang
 */
@Service
public class DfToutiaoUserWyService extends BaseService<DfToutiaoUser> {
	@Resource
	private DfToutiaoUserMapper dfToutiaoUserMapper;

	@Override
	public BaseMapper<DfToutiaoUser> getMapper() {
		return dfToutiaoUserMapper;
	}

	public List<DfToutiaoUser> queryList(DfToutiaoUser dftt) {
		return super.queryList(dftt, null);
	}

	@Transactional
	public String saveDftt(DfToutiaoUser dftt) throws IllegalAccessException, InvocationTargetException {
		logger.info("开始保存设备信息。。。");
		logger.info("imei={}|device={}", dftt.getImei(), dftt.getDevice());
		logger.info("info={}", dftt.getInfo());
		String msg = "";
		if (dftt.getId() != null) {
			super.updateBySelect(dftt);
		} else if (StringUtils.isNotBlank(dftt.getAccid())) {
			DfToutiaoUser t = new DfToutiaoUser();
			t.setAccid(dftt.getAccid());
			t = super.queryOne(t, null);
			logger.info("t是否为空." + t);
			if (t != null) {
				if (t.getError() == 1l) {
					msg = "安装作弊软件";
				}
				BeanUtils.copy(dftt, t);
				super.updateBySelect(t);
			} else {
				super.insert(dftt);
			}
		} else if (StringUtils.isNotBlank(dftt.getImei()) && StringUtils.isNotBlank(dftt.getDevice())) {
			DfToutiaoUser t = new DfToutiaoUser();
			t.setImei(dftt.getImei());
			t.setDevice(dftt.getDevice());
			t = super.queryOne(t, null);
			if (t != null) {
				if (t.getError() == 1l) {
					msg = "安装作弊软件";
				}
				BeanUtils.copy(dftt, t);
				super.updateBySelect(t);
			} else {
				super.insert(dftt);
			}
		} else {
			super.insert(dftt);
		}
		return msg;
	}

	@Transactional
	public String bindAccId(String accId, String device, String imei) {
		String msg = "";
		DfToutiaoUser t = new DfToutiaoUser();
		t.setImei(imei);
		t.setDevice(device);
		t = super.queryOne(t, null);
		if (t != null) {
			if (t.getError() == 1l) {
				msg = "安装作弊软件";
			}
			t.setAccid(accId);
			super.updateBySelect(t);
		}
		return msg;
	}

	@Transactional
	public String loginReturnMsg(String result, String device, String imei) {
		logger.info("device={}|imei={}|result= {}", device, imei, result);
		String msg = "";
		DfToutiaoUser t = new DfToutiaoUser();
		t.setImei(imei);
		t.setDevice(device);
		t = super.queryOne(t, null);
		t.setResult(result);
		super.updateBySelect(t);
		JSONObject r = JSONObject.parseObject(result);
		JSONObject sm = r.getJSONObject("data").getJSONObject("sm_arr");
		if ("安装作弊软件".equals(sm.getString("description")) || "虚拟机".equals(sm.getString("description"))) {
			msg = sm.getString("description");
			t.setError(1l);
		}
		logger.info("msg={}", msg);
		super.updateBySelect(t);
		return msg;
	}

	@Override
	public Weekend<DfToutiaoUser> genSqlExample(DfToutiaoUser t) {
		Weekend<DfToutiaoUser> w = super.genSqlExample(t);
		WeekendCriteria<DfToutiaoUser, Object> c = w.weekendCriteria();
		if (StringUtils.isNotBlank(t.getAccid())) {
			c.andEqualTo(DfToutiaoUser::getAccid, t.getAccid());
		}
		if (StringUtils.isNotBlank(t.getImei())) {
			c.andEqualTo(DfToutiaoUser::getImei, t.getImei());
		}
		if (StringUtils.isNotBlank(t.getDevice())) {
			c.andEqualTo(DfToutiaoUser::getDevice, t.getDevice());
		}
		if (StringUtils.isNotBlank(t.getDeviceId())) {
			c.andEqualTo(DfToutiaoUser::getDeviceId, t.getDeviceId());
		}
		w.and(c);
		return w;
	}

}
