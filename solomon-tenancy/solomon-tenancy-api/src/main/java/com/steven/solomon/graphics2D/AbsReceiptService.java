package com.steven.solomon.graphics2D;

import com.steven.solomon.graphics2D.entity.BaseReceipt;
import com.steven.solomon.utils.minio.MinioUtils;
import com.steven.solomon.utils.verification.ValidateUtils;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import javax.annotation.Resource;

public abstract class AbsReceiptService <T extends BaseReceipt>{

  //行高
  public Integer rowheight;
  //余留上方像素
  public Integer startHeight;
  //余留左方像素
  public Integer startWidth;
  //图片高度
  public Integer imageHeight;
  //图片宽度
  public Integer imageWidth;

  @Resource
  protected MinioUtils minioUtils;

  public void drawReceipt(T receipt) throws Exception {
    drawReceipt(BufferedImage.TYPE_INT_RGB, Color.white,receipt);
  }

  public void drawReceipt(int imageType,Color backgroundColor, T receipt) throws Exception{
    BufferedImage bufferedImage = null;
    try {
      bufferedImage = drawImageBasics(imageType);
      Graphics2D g2 = drawBackgroundColor(bufferedImage,backgroundColor);
      g2 = drawTableLines(g2);
      g2 = drawDescribe(g2,receipt);
      g2 = drawData(g2,receipt);
      //因为2D画图画字体会有锯齿，而graphics2D类有抗锯齿和画笔柔顺的开关，设置如下
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
      g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      Stroke s = new BasicStroke(imageWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
      g2.setStroke(s);
      upload(bufferedImage);
    } finally {
      if(ValidateUtils.isNotEmpty(bufferedImage)){
        bufferedImage.getGraphics().dispose();
        bufferedImage=null;
      }
      System.gc();
    }

  }

  /**
   * 绘制图片长宽高以及原色
   */
  public BufferedImage drawImageBasics(int imageType){
    return new BufferedImage(imageWidth,imageHeight,imageType);
  }

  /**
   * 绘制图片背景颜色
   * @param bufferedImage
   * @param color
   * @return
   */
  public Graphics2D drawBackgroundColor(BufferedImage bufferedImage, Color color){
    Graphics2D g2 =bufferedImage.createGraphics();
    //设置颜色
    g2.setColor(color);
    //填充整张图片(其实就是设置背景颜色)
    g2.fillRect(0,0,bufferedImage.getWidth(),bufferedImage.getHeight());
    return g2;
  }

  /**
   * 绘制表格线条
   */
  public abstract Graphics2D drawTableLines(Graphics2D g2);

  /**
   * 绘制表格文字描述
   */
  public abstract Graphics2D drawDescribe(Graphics2D g2,T receipt);

  /**
   * 绘制动态数据
   */
  public abstract Graphics2D drawData(Graphics2D g2, T receipt);

  /**
   * 上传图片
   */
  public abstract void upload(BufferedImage bufferedImage) throws Exception;
}
