package com.steven.solomon.controller;

import com.steven.solomon.base.controller.BaseController;
import com.steven.solomon.base.model.BaseResponseVO;
import com.steven.solomon.config.RedisTenantsHandler;
import com.steven.solomon.exception.BaseException;
import com.steven.solomon.graphics2D.DepositReceiptService;
import com.steven.solomon.graphics2D.ReceiptService;
import com.steven.solomon.graphics2D.entity.DepositReceipt;
import com.steven.solomon.graphics2D.entity.Receipt;
import com.steven.solomon.pojo.entity.Area;
import com.steven.solomon.pojo.param.AreaListParam;
import com.steven.solomon.service.AreaService;
import com.steven.solomon.service.ICacheService;
import io.swagger.annotations.Api;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
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

  @PostMapping("/list")
  public BaseResponseVO<List<Area>> list(@RequestBody AreaListParam param) {
    return new BaseResponseVO(areaService.findByAreaCode(param));
  }
}
