package com.steven.solomon.controller;

import com.steven.solomon.base.controller.BaseController;
import com.steven.solomon.exception.BaseException;
import com.steven.solomon.graphics2D.DepositReceiptService;
import com.steven.solomon.graphics2D.ReceiptService;
import com.steven.solomon.pojo.param.AreaListParam;
import com.steven.solomon.service.AreaService;
import com.steven.solomon.service.ICaheService;
import io.swagger.annotations.Api;
import java.io.IOException;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/area")
@Api(tags  = "地图接口")
public class AreaController extends BaseController {

  @Resource
  private AreaService areaService;

  @Resource
  private ICaheService iCaheService;

  @Resource
  private DepositReceiptService depositReceiptService;

  @Resource
  private ReceiptService receiptService;

  @PostMapping("/list")
  public String list(@RequestBody AreaListParam param) throws IOException, BaseException {
    return super.responseSuccessJson(areaService.findByAreaCode(param));
  }

}
