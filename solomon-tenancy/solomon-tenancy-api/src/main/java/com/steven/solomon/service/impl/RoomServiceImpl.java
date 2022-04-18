package com.steven.solomon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.steven.solomon.base.enums.DelFlagEnum;
import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.code.TenancyErrorCode;
import com.steven.solomon.entity.House;
import com.steven.solomon.entity.HouseConfig;
import com.steven.solomon.entity.HouseConfigFloorRoom;
import com.steven.solomon.entity.Room;
import com.steven.solomon.mapper.RoomMapper;
import com.steven.solomon.param.RoomPageParam;
import com.steven.solomon.param.RoomUpdateParam;
import com.steven.solomon.service.RoomService;
import com.steven.solomon.utils.json.JackJsonUtils;
import com.steven.solomon.utils.lambda.LambdaUtils;
import com.steven.solomon.utils.verification.ValidateUtils;
import com.steven.solomon.vo.RoomVO;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.*;

@Service
@DubboService
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = false)
  public void save(HouseConfig houseConfig, House house) throws IOException {
    //获取房屋id
    String houseId = house.getId();
    List<Room> roomList = this.findByHouseId(houseId);
    if(ValidateUtils.isNotEmpty(roomList)){
      this.baseMapper.deleteBatchIds(LambdaUtils.toList(roomList,Room :: getId));
    }

    //获取总楼层数
    Integer totalFloors = house.getTotalFloors();
    //获取每层房间数量
    Integer roomNum     = house.getNum();

    Map<Integer, HouseConfigFloorRoom> map = ValidateUtils.isNotEmpty(houseConfig) ? LambdaUtils
        .toMap(JackJsonUtils.conversionClassList(houseConfig.getJson(), HouseConfigFloorRoom.class),
            HouseConfigFloorRoom::getFloor) : new HashMap<>(1);

    List<Room> saveList = new ArrayList<>();
    Room       room     = null;
    for (int num = 1; num <= totalFloors; num++) {
      HouseConfigFloorRoom houseConfigFloorRoom = map.get(num);
      int roomEndNum = ValidateUtils.isNotEmpty(houseConfigFloorRoom) ? houseConfigFloorRoom.getNum() : roomNum;
      for (int roomStartNum = 1; roomStartNum <= roomEndNum; roomStartNum++) {
        room = new Room();
        room.create("1");
        room.setType(ValidateUtils.isNotEmpty(houseConfigFloorRoom) ? houseConfigFloorRoom.getRoomType() : null);
        room.setRoomNum(num+"0" + roomStartNum);
        room.setHouseId(houseId);
        saveList.add(room);
      }
    }
    this.saveBatch(saveList);
  }

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = false)
  public void update(RoomUpdateParam params) throws BaseException {
    Room room = ValidateUtils.isEmpty(this.getById(params.getId()), TenancyErrorCode.ROOM_IS_NULL);
    room.update("1");
    this.updateById(room);
  }

  @Override
  public IPage<RoomVO> page(RoomPageParam params) {
    QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("a.house_id",params.getId());
    queryWrapper.eq("a.del_flag", DelFlagEnum.NOT_DELETE.label());
    return this.baseMapper.page(new Page<House>(params.getPageNo(), params.getPageSize()),queryWrapper);
  }

  @Override
  public List<Room> findByHouseId(String houseId) {
    LambdaQueryWrapper<Room> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(true,Room::getHouseId,houseId);
    return this.baseMapper.selectList(queryWrapper);
  }

  public static void main(String[] args) throws IOException, WriterException {
    String id = UUID.randomUUID().toString();//随机字符，这里是用雪花算法生成的字符串id
    int rowheight = 45;//行高
    int startHeight = 50;//余留上方像素
    int startWidth = 15;//余留左方像素
    int imageHeight = 360;
    int imageWidth =950;
    //得到图片缓冲区
    BufferedImage bi = new BufferedImage(imageWidth,imageHeight,BufferedImage.TYPE_INT_RGB);//INT精确度达到一定,RGB三原色，高度70,宽度150

    //得到它的绘制环境(这张图片的笔)
    //Graphics2D g2 = (Graphics2D) bi.getGraphics();

    Graphics2D g2 =bi.createGraphics();
    //设置颜色
    g2.setColor(Color.WHITE);
    g2.fillRect(0,0,imageWidth,imageHeight);//填充整张图片(其实就是设置背景颜色)

    //画横线
    for (int j = 0; j < 5; j++) {
      g2.setColor(Color.darkGray);
      g2.drawLine(startWidth, startHeight + (j + 1) * rowheight, imageWidth - 15,
              startHeight + (j + 1) * rowheight);
    }
    // 画竖线
    int rightLine;
    g2.setColor(Color.gray);
    for (int k = 0; k < 2; k++) {
      rightLine = startWidth + k * (imageWidth - startWidth * 2 );
      g2.drawLine(rightLine, startHeight + rowheight, rightLine,
              startHeight + 5 * rowheight);
    }
    g2.setColor(Color.BLACK);//设置背景颜色
    //插入二维码
    byte[] qrCode = getQRCodeImage(id,60,60);
    InputStream buffIn = new ByteArrayInputStream(qrCode, 0, qrCode.length);
    Image  bufferedImage =(Image) ImageIO.read(buffIn);
    g2.drawImage(bufferedImage,startWidth,10,null); //向图片左上插入二维码
    g2.setFont(new Font("黑体", Font.BOLD,20)); //设置字体:字体、字号、大小
    g2.drawString("XX有限责任公司收款收据      NO.", startWidth*19, rowheight);

    //以上画好了底图，开始插入票据信息

    g2.setFont(new Font("宋体", Font.CENTER_BASELINE, 14));
    //表头填充
    g2.drawString("0408601", imageWidth-startWidth*8, rowheight);
    g2.drawString("合同号：20210203xxxxxxxx", startWidth, startHeight+rowheight-3);
    g2.drawString("日期：  2021年2月3日",imageWidth - startWidth * 13, startHeight+rowheight-3);
    //调整字体
    g2.setFont(new Font("宋体", Font.CENTER_BASELINE, 17));
    //第一行填充
    g2.drawString("今日收到 张三 交来 解放路53号安居苑3栋1单元2048号 住宅保证金",startWidth*2, startHeight+rowheight*2-10);
    //第二行填充
    g2.drawString("￥：  790",imageWidth - startWidth * 13, startHeight+rowheight*3-10);
    //第三行填充
    g2.drawString("备注：  测试生成电子票据",startWidth*2, startHeight+rowheight*4-10);
    //第四行填充
    g2.drawString("缴费方式： 刷卡（ ）  转账（ ）  支付宝（●）  微信（ ）  现金（ ） ",startWidth*2, startHeight+rowheight*5-10);
    //底部填充
    g2.drawString("开票单位（盖章有效）",startWidth*2, startHeight+rowheight*6-20);
    g2.drawString("开票人：韩梅梅",startWidth*25, startHeight+rowheight*6-20);
    g2.drawString("承租人（签名）",startWidth*50, startHeight+rowheight*6-20);
    //调整字体
    g2.setFont(new Font("宋体", Font.BOLD, 18));
    g2.drawString("人民币（大写）：  漆佰玖拾圆整",startWidth*2, startHeight+rowheight*3-10);//第二行大写金额需要加粗，所以放在最后
    //因为2D画图画字体会有锯齿，而graphics2D类有抗锯齿和画笔柔顺的开关，设置如下
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    Stroke s = new BasicStroke(imageWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
    g2.setStroke(s);

    ImageIO.write(bi,"JPEG",new FileOutputStream("F:/codeCreateIMG/a"+id+".jpg"));//保存图片 JPEG表示保存格式
    //释放内存，解决文件占用问题
    bi.getGraphics().dispose();
    bi=null;
    System.gc();
  }

  //生成二维码
  // 编码格式
  private static final String CHARSET = "utf-8";
  private static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
    Hashtable hints = new Hashtable();
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
    hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
    hints.put(EncodeHintType.MARGIN, 1);
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
    ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
    MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
    byte[] pngData = pngOutputStream.toByteArray();
    return pngData;
  }
}
