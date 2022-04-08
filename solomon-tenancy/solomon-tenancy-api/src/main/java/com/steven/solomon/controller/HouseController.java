package com.steven.solomon.controller;

import com.steven.solomon.base.controller.BaseController;
import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.param.*;
import com.steven.solomon.service.HouseConfigService;
import com.steven.solomon.service.HouseService;
import com.steven.solomon.service.RoomService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/house")
public class HouseController extends BaseController {

  @Resource
  private HouseService houseService;

  @Resource
  private RoomService roomService;

  @Resource
  private HouseConfigService houseConfigService;

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

  @PostMapping("/config/save")
  public String saveConfig(@Valid @RequestBody HouseConfigSaveParam param) throws IOException, BaseException {
    houseConfigService.save(param);
    return super.responseSuccessJson();
  }

  @PostMapping("/config/update")
  public String updateConfig(@Valid @RequestBody HouseConfigUpdateParam param) throws IOException, BaseException {
    houseConfigService.update(param);
    return super.responseSuccessJson();
  }


  @PutMapping("/room/update")
  public String update(@Valid @RequestBody RoomUpdateParam params) throws IOException, BaseException {
    roomService.update(params);
    return super.responseSuccessJson();
  }

  @PostMapping("/room/page")
  public String roomPage(@Valid @RequestBody RoomPageParam params) throws IOException {
    return super.responseSuccessJson(roomService.page(params));
  }

  @PostMapping("/room/lease")
  public String lease() throws IOException {
    return super.responseSuccessJson();
  }
}
