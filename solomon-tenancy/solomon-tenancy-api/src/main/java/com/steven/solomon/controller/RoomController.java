package com.steven.solomon.controller;

import com.steven.solomon.base.code.error.BaseExceptionCode;
import com.steven.solomon.base.controller.BaseController;
import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.param.RoomSaveParam;
import com.steven.solomon.service.RoomService;
import com.steven.solomon.utils.i18n.I18nUtils;
import java.io.IOException;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
public class RoomController extends BaseController {

  @Resource(name = "roomServiceImpl")
  private RoomService roomService;

  @Autowired
  private ResourceBundleMessageSource resourceMessageSource;

  @PostMapping("/save")
  public String save (@Valid @RequestBody RoomSaveParam params) throws IOException, BaseException {
//    roomService.save(params);
    return super.responseSuccessJson(resourceMessageSource.getBasenameSet());
  }
}
