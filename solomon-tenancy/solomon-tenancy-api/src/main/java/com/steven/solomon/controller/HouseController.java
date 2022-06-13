package com.steven.solomon.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.steven.solomon.base.controller.BaseController;
import com.steven.solomon.base.model.BaseResponseVO;
import com.steven.solomon.exception.BaseException;
import com.steven.solomon.pojo.param.HouseConfigSaveParam;
import com.steven.solomon.pojo.param.HouseConfigUpdateParam;
import com.steven.solomon.pojo.param.HouseGetParam;
import com.steven.solomon.pojo.param.HouseInitParam;
import com.steven.solomon.pojo.param.HousePageParam;
import com.steven.solomon.pojo.param.HouseSaveParam;
import com.steven.solomon.pojo.param.HouseUpdateParam;
import com.steven.solomon.pojo.param.RoomPageParam;
import com.steven.solomon.pojo.param.RoomUpdateParam;
import com.steven.solomon.pojo.vo.HouseVO;
import com.steven.solomon.pojo.vo.RoomVO;
import com.steven.solomon.service.HouseConfigService;
import com.steven.solomon.service.HouseService;
import com.steven.solomon.service.RoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/house")
@Api(tags  = "房屋接口")
public class HouseController extends BaseController {

  @Resource
  private HouseService houseService;

  @Resource
  private RoomService roomService;

  @Resource
  private HouseConfigService houseConfigService;

  @PostMapping("/save")
  @ApiOperation(value="房屋保存接口")
  public BaseResponseVO<String> save (@Valid @RequestBody HouseSaveParam params) throws IOException, BaseException {
    return new BaseResponseVO(houseService.save(params));
  }

  @PutMapping("/update")
  @ApiOperation(value="房屋更新接口")
  public BaseResponseVO update(@Valid @RequestBody HouseUpdateParam params) throws IOException, BaseException {
    houseService.update(params);
    return new BaseResponseVO();
  }

  @PostMapping("/page")
  @ApiOperation(value="房屋分页接口")
  public BaseResponseVO<IPage<HouseVO>> page(@Valid @RequestBody HousePageParam params) {
    return new BaseResponseVO(houseService.page(params));
  }

  @PostMapping("/get")
  @ApiOperation(value="根据id获取房屋接口")
  public BaseResponseVO<HouseVO> get(@Valid @RequestBody HouseGetParam params) {
    return new BaseResponseVO(houseService.get(params));
  }

  @PostMapping("/init")
  @ApiOperation(value="房屋初始化接口")
  public BaseResponseVO init(@Valid @RequestBody HouseInitParam params) throws IOException, BaseException {
    houseService.init(params);
    return new BaseResponseVO();
  }

  @GetMapping("/config/list")
  @ApiOperation(value="房屋配置列表接口")
  public BaseResponseVO<List<Map<String,Object>>> configList() {
    return new BaseResponseVO(houseConfigService.findTypeEnumList());
  }

  @PostMapping("/config/save")
  @ApiOperation(value="房屋配置保存接口")
  public BaseResponseVO saveConfig(@Valid @RequestBody HouseConfigSaveParam param) throws IOException, BaseException {
    houseConfigService.save(param);
    return new BaseResponseVO();
  }

  @PostMapping("/config/update")
  @ApiOperation(value="房屋配置更新接口")
  public BaseResponseVO updateConfig(@Valid @RequestBody HouseConfigUpdateParam param) throws IOException, BaseException {
    houseConfigService.update(param);
    return new BaseResponseVO();
  }

  @PutMapping("/room/update")
  @ApiOperation(value="房屋房间更新接口")
  public BaseResponseVO update(@Valid @RequestBody RoomUpdateParam params) throws BaseException {
    roomService.update(params);
    return new BaseResponseVO();
  }

  @PostMapping("/room/page")
  @ApiOperation(value="房屋房间分页接口")
  public BaseResponseVO<IPage<RoomVO>> roomPage(@Valid @RequestBody RoomPageParam params) {
    return new BaseResponseVO(roomService.page(params));
  }

  @PostMapping("/room/lease")
  @ApiOperation(value="房屋房间租赁接口")
  public BaseResponseVO lease() {
    return new BaseResponseVO();
  }
}
