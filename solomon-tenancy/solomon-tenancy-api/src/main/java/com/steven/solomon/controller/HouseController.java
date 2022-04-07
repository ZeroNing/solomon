package com.steven.solomon.controller;

import com.steven.solomon.base.controller.BaseController;
import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.param.HouseGetParam;
import com.steven.solomon.param.HouseInitParam;
import com.steven.solomon.param.HousePageParam;
import com.steven.solomon.param.HouseSaveParam;
import com.steven.solomon.param.HouseUpdateParam;
import com.steven.solomon.service.HouseService;
import java.io.IOException;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/house")
public class HouseController extends BaseController {

  @Resource(name = "houseServiceImpl")
  private HouseService houseService;

  @PostMapping("/save")
  public String save (@Valid @RequestBody HouseSaveParam params) throws IOException, BaseException {
    return super.responseSuccessJson(houseService.save(params));
  }

  @PutMapping("/update")
  public String update(@Valid @RequestBody HouseUpdateParam params) throws IOException, BaseException {
    houseService.update(params);
    return super.responseSuccessJson();
  }

  @PostMapping("/page")
  public String page(@Valid @RequestBody HousePageParam params) throws IOException,BaseException {
    return super.responseSuccessJson(houseService.page(params));
  }

  @PostMapping("/get")
  public String get(@Valid @RequestBody HouseGetParam params) throws IOException,BaseException {
    return super.responseSuccessJson(houseService.get(params));
  }

  @PostMapping("/init")
  public String init(@Valid @RequestBody HouseInitParam params) throws IOException, BaseException {
    houseService.init(params);
    return super.responseSuccessJson();
  }
}
