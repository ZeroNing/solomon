package com.steven.solomon.graphics2D;

import com.steven.solomon.graphics2D.entity.Receipt;
import com.steven.solomon.utils.date.DateTimeUtils;
import com.steven.solomon.utils.rmb.ConvertUpMoney;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Service
public class ReceiptService extends AbsReceiptService <Receipt>{
  
  public ReceiptService(){
    //行高
    super.rowheight = 45;
    //余留上方像素
    super.startHeight = 50;
    //余留左方像素
    super.startWidth = 15;
    //图片高度
    super.imageHeight = 500;
    //图片宽度
    super.imageWidth = 1100;
  }

  @Override
  public Graphics2D drawTableLines(Graphics2D g2) {
    //画横线
    g2.setColor(new Color(255,0,0));
    for (int j = 0; j < 8; j++) {
      g2.drawLine(startWidth, startHeight + (j + 1) * rowheight, imageWidth - 15,
          startHeight + (j + 1) * rowheight);
    }
    // 画竖线
    int rightLine;
    for (int k = 0; k < 2; k++) {
      rightLine = startWidth + k * (imageWidth - startWidth * 2 );
      g2.drawLine(rightLine, startHeight + rowheight, rightLine,
          startHeight + 8 * rowheight);
    }
    return g2;
  }

  @Override
  public Graphics2D drawDescribe(Graphics2D g2, Receipt receipt) {
    g2.setFont(new Font("黑体", Font.BOLD,25)); //设置字体:字体、字号、大小
    g2.drawString("水电房租收据      NO."+ DateTimeUtils.getLocalDateTimeString(DateTimeUtils.DATE_FORMATTER_YEAR), (imageWidth/2)-120, rowheight);
    g2.setFont(new Font("宋体", Font.CENTER_BASELINE, 20));

    g2.drawString("地址："+ receipt.getAddress(), startWidth, startHeight+rowheight-3);
    g2.drawString("日期："+DateTimeUtils.getLocalDateTimeString(DateTimeFormatter.ofPattern("yyyy年MM月dd日")),(imageWidth - startWidth * 11) -60, startHeight+rowheight-3);
    //调整字体
    g2.setFont(new Font("宋体", Font.CENTER_BASELINE, 23));
    //第一行
    int typefaceY = startHeight+rowheight*2-10;
    int typefaceX = startWidth+50;
    g2.drawString("项目",typefaceX, typefaceY);
    int x = startWidth*10;
    int y1 = startHeight + rowheight;
    int y2 = startHeight + 4 * rowheight;
    g2.drawLine(x, y1, x,startHeight + 5 * rowheight);

    g2.drawString("上月",x+((x-typefaceX)/2)+15, typefaceY);
    g2.drawLine(x*2, y1, x*2,y2);

    g2.drawString("本月",x*2+((x-typefaceX)/2)+15, typefaceY);
    g2.drawLine(x*3, y1, x*3,y2);

    g2.drawString("实用",x*3+((x-typefaceX)/2)+15, typefaceY);
    g2.drawLine(x*4, y1, x*4,y2);

    g2.drawString("单价",x*4+((x-typefaceX)/2)+15, typefaceY);
    g2.drawLine(x*5, y1, x*5,y2);

    g2.drawString("金额",x*5+((x-typefaceX)/2)+15, typefaceY);
    g2.drawLine(x*6, y1, x*6,startHeight + 7 * rowheight);

    g2.drawString("备注",x*6+((x-typefaceX)/2)+15, typefaceY);
    //第二行

    g2.drawString("水费(吨)",typefaceX-30, startHeight+rowheight*3-10);
    g2.drawString("电费(度)",typefaceX-30, startHeight+rowheight*4-10);
    g2.drawString("房租",typefaceX-10, startHeight+rowheight*5-10);

    g2.drawString("卫生费",typefaceX-20, startHeight+rowheight*6-10);
    g2.drawLine(x, startHeight + 5 * rowheight, x,startHeight + 7 * rowheight);

    g2.drawLine(x*2, startHeight + 5 * rowheight, x*2,startHeight + 7 * rowheight);
    g2.drawString("电视费",x*2+50, startHeight+rowheight*6-10);
    g2.drawLine(x*3, startHeight + 5 * rowheight, x*3,startHeight + 6 * rowheight);

    g2.drawLine(x*4, startHeight + 5 * rowheight, x*4,startHeight + 6 * rowheight);
    g2.drawString("网费",x*4+50, startHeight+rowheight*6-10);

    g2.drawString("管理费",typefaceX-20, startHeight+rowheight*7-10);

    g2.drawString("其他",x*2+50, startHeight+rowheight*7-10);
    g2.drawLine(x*3, startHeight + 6 * rowheight, x*3,startHeight + 7 * rowheight);
    g2.drawString("",x*3+50, startHeight+rowheight*7-10);

    g2.drawString("合计人民币大写:",typefaceX, startHeight+rowheight*8-10);
    g2.drawString("收款人:" + receipt.getPayee(),startWidth*56, startHeight+rowheight*9-20);
    return g2;
  }

  @Override
  public Graphics2D drawData(Graphics2D g2, Receipt receipt) {

    BigDecimal waterRatePrice = receipt.getWaterRatePrice();
    BigDecimal powerRatePrice = receipt.getPowerRatePrice();

    //实际用的水费
    Long practicalTonnage = receipt.getMonthTonnage() - receipt.getLastMonthTonnage();
    //实际用的电费
    Long practicalMonthDegree = receipt.getMonthDegree() - receipt.getLastMonthDegree();

    int x = startWidth*10;
    int typefaceX = startWidth+50;
    g2.drawLine(x*5, startHeight + 5 * rowheight, x*5,startHeight + 6 * rowheight);


    g2.setColor(new Color(0,0,205));//设置背景颜色

    g2.drawString(receipt.getLastMonthTonnage().toString(),x+((x-typefaceX)/2)+15, startHeight+rowheight*3-10);
    g2.drawString(receipt.getLastMonthDegree().toString(),x+((x-typefaceX)/2)+15, startHeight+rowheight*4-10);
    g2.drawString(receipt.getMonthTonnage().toString(),x*2+((x-typefaceX)/2)+15, startHeight+rowheight*3-10);
    g2.drawString(receipt.getMonthDegree().toString(),x*2+((x-typefaceX)/2)+15, startHeight+rowheight*4-10);

    g2.drawString(practicalTonnage.toString(),x*3+((x-typefaceX)/2)+15, startHeight+rowheight*3-10);
    g2.drawString(practicalMonthDegree.toString(),x*3+((x-typefaceX)/2)+15, startHeight+rowheight*4-10);
    g2.drawString(waterRatePrice.toString(),x*4+((x-typefaceX)/2)+15, startHeight+rowheight*3-10);
    g2.drawString(powerRatePrice.toString(),x*4+((x-typefaceX)/2)+15, startHeight+rowheight*4-10);
    g2.drawString(waterRatePrice.multiply(new BigDecimal(practicalTonnage)).setScale(2,BigDecimal.ROUND_HALF_UP).toString(),x*5+((x-typefaceX)/2)+15, startHeight+rowheight*3-10);
    g2.drawString(powerRatePrice.multiply(new BigDecimal(practicalMonthDegree)).setScale(2,BigDecimal.ROUND_HALF_UP).toString(),x*5+((x-typefaceX)/2)+15, startHeight+rowheight*4-10);
    g2.drawString("¥ "+new BigDecimal(receipt.getRent()).setScale(2,BigDecimal.ROUND_HALF_UP).toString()+"/",typefaceX*5, startHeight+rowheight*5-10);
    g2.drawString(receipt.getSanitationFee().toString(),x+50, startHeight+rowheight*6-10);
    g2.drawString(receipt.getTVFee().toString(),x*3+50, startHeight+rowheight*6-10);


    g2.drawString(receipt.getInternetFee().toString(),x*5+50, startHeight+rowheight*6-10);
    g2.drawString(receipt.getManagementFee().toString(),x+50, startHeight+rowheight*7-10);
    g2.drawString(ConvertUpMoney.toChina(receipt.getRent()),typefaceX+200, startHeight+rowheight*8-10);

    return g2;
  }

  @Override
  public void upload(BufferedImage bufferedImage,Receipt receipt) throws Exception {
//    minioUtils.putObject("receipt",bufferedImage, DateTimeUtils.getLocalYearString()+"/"+DateTimeUtils.getLocalMonthString()+"/"+receipt.getTenantName()+"的当月水电房租收据"+".jpg",
//        FileTypeUtils.getFileType(bufferedImage));
    ImageIO.write(bufferedImage,"JPEG",new FileOutputStream("D:/"+"2"+".jpg"));//保存图片 JPEG表示保存格式

  }
}
