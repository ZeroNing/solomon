package com.steven.solomon.controller;

import com.steven.solomon.base.controller.BaseController;
import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.graphics2D.DepositReceiptService;
import com.steven.solomon.graphics2D.ReceiptService;
import com.steven.solomon.graphics2D.entity.DepositReceipt;
import com.steven.solomon.graphics2D.entity.Receipt;
import com.steven.solomon.param.AreaListParam;
import com.steven.solomon.service.AreaService;
import com.steven.solomon.utils.redis.ICaheService;
import io.swagger.annotations.Api;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping("/test")
  public String test() throws Exception {
    DepositReceipt depositReceipt = new DepositReceipt();
    depositReceipt.setTenantName("11111");
    depositReceipt.setAddress("111111");
    depositReceipt.setPayee("11111111");
    depositReceiptService.drawReceipt(depositReceipt);
    Receipt receipt = new Receipt();
    receipt.setAddress("1111111111");
    receipt.setPayee("111111111111");
    receipt.setRent("111111111");
    receipt.setTenantName("1111111111");
    receiptService.drawReceipt(receipt);
    return super.responseSuccessJson();
  }
}
