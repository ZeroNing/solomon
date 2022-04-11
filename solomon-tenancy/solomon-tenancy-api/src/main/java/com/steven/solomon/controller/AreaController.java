package com.steven.solomon.controller;

import com.steven.solomon.base.controller.BaseController;
import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.base.model.BaseMq;
import com.steven.solomon.base.model.rabbitMQ.RabbitMqModel;
import com.steven.solomon.param.AreaListParam;
import com.steven.solomon.service.AreaService;
import com.steven.solomon.utils.date.DateTimeUtils;
import com.steven.solomon.utils.rabbit.MqService;
import io.swagger.annotations.Api;
import java.io.IOException;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/area")
@Api(tags  = "地图接口")
public class AreaController extends BaseController {

  @Resource
  private AreaService areaService;

  @PostMapping("/list")
  public String list(AreaListParam param) throws IOException, BaseException {
    return super.responseSuccessJson(areaService.findByAreaCode(param));
  }
}
