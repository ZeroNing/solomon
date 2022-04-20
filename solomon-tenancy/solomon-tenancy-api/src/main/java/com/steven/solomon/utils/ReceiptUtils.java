package com.steven.solomon.utils;

import com.steven.solomon.entity.DepositReceipt;
import com.steven.solomon.entity.Receipt;
import com.steven.solomon.utils.date.DateTimeUtils;
import com.steven.solomon.utils.rmb.ConvertUpMoney;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import javax.imageio.ImageIO;

public class ReceiptUtils {

  public static void main(String[] args) throws IOException {
    DepositReceipt depositReceipt = new DepositReceipt();
    depositReceipt.setPayee("11111");
    depositReceipt.setTenantName("11111");
    depositReceipt.setAddress("1111111111111");
    depositReceipt(depositReceipt);
  }

  public static void depositReceipt(DepositReceipt depositReceipt) throws IOException {
    String id = "1";//随机字符，这里是用雪花算法生成的字符串id
    int rowheight = 45;//行高
    int startHeight = 50;//余留上方像素
    int startWidth = 15;//余留左方像素
    int imageHeight = 350;
    int imageWidth =1100;

    int typefaceX = startWidth+50;

    //得到图片缓冲区
    BufferedImage bi = new BufferedImage(imageWidth,imageHeight,BufferedImage.TYPE_INT_RGB);//INT精确度达到一定,RGB三原色，高度70,宽度150

    //得到它的绘制环境(这张图片的笔)
    //Graphics2D g2 = (Graphics2D) bi.getGraphics();

    Graphics2D g2 =bi.createGraphics();
    //设置颜色
    g2.setColor(Color.WHITE);
    g2.fillRect(0,0,imageWidth,imageHeight);//填充整张图片(其实就是设置背景颜色)

    //画横线
    g2.setColor(new Color(255,0,0));
    for (int j = 0; j < 4; j++) {
      g2.drawLine(startWidth, startHeight + (j + 1) * rowheight, imageWidth - 15,
          startHeight + (j + 1) * rowheight);
    }
    // 画竖线
    int rightLine;
    for (int k = 0; k < 2; k++) {
      rightLine = startWidth + k * (imageWidth - startWidth * 2 );
      g2.drawLine(rightLine, startHeight + rowheight, rightLine,
          startHeight + 4 * rowheight);
    }

    g2.setFont(new Font("黑体", Font.BOLD,25)); //设置字体:字体、字号、大小
    g2.drawString("押金单收据      NO."+ DateTimeUtils.getLocalDateTimeString(DateTimeUtils.DATE_FORMATTER_YEAR), (imageWidth/2)-50, rowheight);
    g2.setFont(new Font("宋体", Font.CENTER_BASELINE, 20));
    g2.drawString("日期："+DateTimeUtils.getLocalDateTimeString(DateTimeFormatter.ofPattern("yyyy年MM月dd日")),(imageWidth - startWidth * 11) - 60, startHeight+rowheight-3);
    g2.drawString("地址："+ depositReceipt.getAddress(), startWidth, startHeight+rowheight-3);

    g2.drawString("合计人民币大写:",typefaceX-30, startHeight+rowheight*4-10);
    g2.drawString("收款人:" + depositReceipt.getPayee(),startWidth*63, startHeight+rowheight*5-20);
    g2.setColor(new Color(0,0,205));//设置背景颜色
    g2.drawString(DateTimeUtils.getLocalDateTimeString(DateTimeFormatter.ofPattern("yyyy年MM月dd日"))+"收到"+depositReceipt.getTenantName()+"押金:"+depositReceipt.getDeposit().toString()+"/"+"                   钥匙押金:"+depositReceipt.getKeyDeposit().toString()+"/",typefaceX-30, startHeight+rowheight*2-10);
    g2.drawString("水表底表读数为:"+depositReceipt.getInitialWaterMeterReading().toString()+"                                 电费底表读书为:"+depositReceipt.getInitialPowerMeterReading().toString(),typefaceX-30, startHeight+rowheight*3-10);
    g2.drawString(ConvertUpMoney.toChina(depositReceipt.getDeposit().add(depositReceipt.getKeyDeposit()).toString()),typefaceX+130, startHeight+rowheight*4-10);


    //因为2D画图画字体会有锯齿，而graphics2D类有抗锯齿和画笔柔顺的开关，设置如下
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    Stroke s = new BasicStroke(imageWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
    g2.setStroke(s);
    ImageIO.write(bi,"JPEG",new FileOutputStream("D:/"+id+".jpg"));//保存图片 JPEG表示保存格式
    //释放内存，解决文件占用问题
    bi.getGraphics().dispose();
    bi=null;
    System.gc();
  }

  public static void receipt(Receipt receipt) throws IOException {
    String id = UUID.randomUUID().toString();//随机字符，这里是用雪花算法生成的字符串id
    int rowheight = 45;//行高
    int startHeight = 50;//余留上方像素
    int startWidth = 15;//余留左方像素
    int imageHeight = 500;
    int imageWidth =1100;
    //得到图片缓冲区
    BufferedImage bi = new BufferedImage(imageWidth,imageHeight,BufferedImage.TYPE_INT_RGB);//INT精确度达到一定,RGB三原色，高度70,宽度150

    //得到它的绘制环境(这张图片的笔)
    //Graphics2D g2 = (Graphics2D) bi.getGraphics();

    Graphics2D g2 =bi.createGraphics();
    //设置颜色
    g2.setColor(Color.WHITE);
    g2.fillRect(0,0,imageWidth,imageHeight);//填充整张图片(其实就是设置背景颜色)

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
    g2.setFont(new Font("黑体", Font.BOLD,25)); //设置字体:字体、字号、大小
    g2.drawString("水电房租收据      NO."+ DateTimeUtils.getLocalDateTimeString(DateTimeUtils.DATE_FORMATTER_YEAR), (imageWidth/2)-120, rowheight);
    g2.setFont(new Font("宋体", Font.CENTER_BASELINE, 20));

    g2.drawString("地址："+ receipt.getAddress(), startWidth, startHeight+rowheight-3);
    g2.drawString("日期："+DateTimeUtils.getLocalDateTimeString(DateTimeFormatter.ofPattern("yyyy年MM月dd日")),(imageWidth - startWidth * 11) - 10, startHeight+rowheight-3);
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
    BigDecimal waterRatePrice = receipt.getWaterRatePrice();
    BigDecimal powerRatePrice = receipt.getPowerRatePrice();

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
    g2.drawString("收款人:" + receipt.getPayee(),startWidth*60, startHeight+rowheight*9-20);
    g2.setColor(new Color(0,0,205));//设置背景颜色

    g2.drawString(ConvertUpMoney.toChineseNum(receipt.getRent()),typefaceX+200, startHeight+rowheight*8-10);
    g2.drawString(receipt.getManagementFee().toString(),x+50, startHeight+rowheight*7-10);
    g2.drawLine(x*5, startHeight + 5 * rowheight, x*5,startHeight + 6 * rowheight);
    g2.drawString(receipt.getInternetFee().toString(),x*5+50, startHeight+rowheight*6-10);
    g2.drawString(receipt.getLastMonthTonnage().toString(),x+((x-typefaceX)/2)+15, startHeight+rowheight*3-10);
    g2.drawString(receipt.getLastMonthDegree().toString(),x+((x-typefaceX)/2)+15, startHeight+rowheight*4-10);
    g2.drawString(receipt.getMonthTonnage().toString(),x*2+((x-typefaceX)/2)+15, startHeight+rowheight*3-10);
    g2.drawString(receipt.getMonthDegree().toString(),x*2+((x-typefaceX)/2)+15, startHeight+rowheight*4-10);
    //实际用的水费
    Long practicalTonnage = receipt.getMonthTonnage() - receipt.getLastMonthTonnage();
    //实际用的电费
    Long practicalMonthDegree = receipt.getMonthDegree() - receipt.getLastMonthDegree();
    g2.drawString(practicalTonnage.toString(),x*3+((x-typefaceX)/2)+15, startHeight+rowheight*3-10);
    g2.drawString(practicalMonthDegree.toString(),x*3+((x-typefaceX)/2)+15, startHeight+rowheight*4-10);
    g2.drawString(waterRatePrice.toString(),x*4+((x-typefaceX)/2)+15, startHeight+rowheight*3-10);
    g2.drawString(powerRatePrice.toString(),x*4+((x-typefaceX)/2)+15, startHeight+rowheight*4-10);
    g2.drawString(waterRatePrice.multiply(new BigDecimal(practicalTonnage)).setScale(2,BigDecimal.ROUND_HALF_UP).toString(),x*5+((x-typefaceX)/2)+15, startHeight+rowheight*3-10);
    g2.drawString(powerRatePrice.multiply(new BigDecimal(practicalMonthDegree)).setScale(2,BigDecimal.ROUND_HALF_UP).toString(),x*5+((x-typefaceX)/2)+15, startHeight+rowheight*4-10);
    g2.drawString(receipt.getRent(),typefaceX*5, startHeight+rowheight*5-10);
    g2.drawString(receipt.getSanitationFee().toString(),x+50, startHeight+rowheight*6-10);
    g2.drawString(receipt.getTVFee().toString(),x*3+50, startHeight+rowheight*6-10);

    //    //因为2D画图画字体会有锯齿，而graphics2D类有抗锯齿和画笔柔顺的开关，设置如下
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    Stroke s = new BasicStroke(imageWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
    g2.setStroke(s);
    ImageIO.setUseCache(false);
    ImageIO.write(bi,"JPEG",new FileOutputStream("http://106.52.186.166:8001/usr/etc/solomon/tenancy/receipt/2022/04/"+id+".jpg"));//保存图片 JPEG表示保存格式
    //释放内存，解决文件占用问题
    bi.getGraphics().dispose();
    bi=null;
    System.gc();
  }
}
