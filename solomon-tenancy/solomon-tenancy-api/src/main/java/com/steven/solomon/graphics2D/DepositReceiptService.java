package com.steven.solomon.graphics2D;

import com.steven.solomon.code.ReceiptMinIoBucketCode;
import com.steven.solomon.date.DateTimeUtils;
import com.steven.solomon.graphics2D.entity.DepositReceipt;
import com.steven.solomon.minio.graphics2D.AbsReceiptService;
import com.steven.solomon.minio.graphics2D.entity.MinIo;
import com.steven.solomon.rmb.ConvertUpMoney;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

@Service
public class DepositReceiptService extends AbsReceiptService<DepositReceipt> {

  public DepositReceiptService(){
    //行高
    super.rowheight = 45;
    //余留上方像素
    super.startHeight = 50;
    //余留左方像素
    super.startWidth = 15;
    //图片高度
    super.imageHeight = 350;
    //图片宽度
    super.imageWidth = 1100;
  }

  @Override
  public Graphics2D drawTableLines(Graphics2D g2) {
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
    return g2;
  }

  @Override
  public Graphics2D drawDescribe(Graphics2D g2, DepositReceipt receipt) {
    int typefaceX = startWidth+50;

    g2.setFont(new Font("黑体", Font.BOLD,25)); //设置字体:字体、字号、大小
    g2.drawString("押金单收据      NO."+ DateTimeUtils.getLocalDateTimeString(DateTimeUtils.DATE_FORMATTER_YEAR), (imageWidth/2)-30, rowheight);
    g2.setFont(new Font("宋体", Font.CENTER_BASELINE, 20));
    g2.drawString("日期："+DateTimeUtils.getLocalDateTimeString(DateTimeFormatter.ofPattern("yyyy年MM月dd日")),(imageWidth - startWidth * 11) -60, startHeight+rowheight-3);
    g2.drawString("地址："+ receipt.getAddress(), startWidth, startHeight+rowheight-3);

    g2.drawString("合计人民币大写:",typefaceX-30, startHeight+rowheight*4-10);
    g2.drawString("收款人:" + receipt.getPayee(),startWidth*57, startHeight+rowheight*5-20);
    g2.setColor(new Color(0,0,205));//设置背景颜色
    g2.drawString(DateTimeUtils.getLocalDateTimeString(DateTimeFormatter.ofPattern("yyyy年MM月dd日"))+"收到"+receipt.getTenantName()+"押金:"+receipt.getDeposit().toString()+"/"+"                   钥匙押金:"+receipt.getKeyDeposit().toString()+"/",typefaceX-30, startHeight+rowheight*2-10);
    g2.drawString("水表底表读数为:"+receipt.getInitialWaterMeterReading().toString()+"                                 电费底表读数为:"+receipt.getInitialPowerMeterReading().toString(),typefaceX-30, startHeight+rowheight*3-10);
    g2.drawString(ConvertUpMoney.toChina(receipt.getDeposit().add(receipt.getKeyDeposit()).toString()),typefaceX+130, startHeight+rowheight*4-10);
    return g2;
  }

  @Override
  public Graphics2D drawData(Graphics2D g2, DepositReceipt receipt) {
    return g2;
  }

  @Override
  public MinIo upload(BufferedImage bufferedImage, DepositReceipt receipt) throws Exception {
    String fileName = DateTimeUtils.getLocalYearString()+"/"+DateTimeUtils.getLocalMonthString()+"/"+receipt.getTenantName()+"的押金单"+".jpg";
    return minioUtils.putObject(ReceiptMinIoBucketCode.DEPOSIT_RECEIPT,bufferedImage, fileName);
  }
}
