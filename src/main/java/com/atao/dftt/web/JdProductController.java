package com.atao.dftt.web;

import com.atao.base.controller.BaseController;
import com.atao.base.service.BaseService;

import com.atao.dftt.model.JdProduct;
import com.atao.dftt.service.JdProductWyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author twang
 */
@RestController
@RequestMapping("${adminPath}" + JdProductController.BASE_URL)
public class JdProductController extends BaseController<JdProduct> {
    public static final String BASE_URL = "/JdProduct/";

    @Autowired
    private JdProductWyService jdProductWyService;

    @Override
    protected BaseService<JdProduct> getService() {
        return jdProductWyService;
    }
}
