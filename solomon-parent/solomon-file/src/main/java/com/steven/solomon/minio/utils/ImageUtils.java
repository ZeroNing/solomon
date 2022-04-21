package com.steven.solomon.minio.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Coordinate;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * 图片处理工具类
 */
public class ImageUtils {

  /**
   * 对图片进行指定比例的压缩
   * @param filePath 图片绝对源路径
   * @param scale 图片大小 1是原图大小 0.5是原图的一半相当于长宽一半
   * @param outputQuality 图片质量 1是最高清 0是最差的
   * @param newPath 新图片的绝对路径
   */
  public static void compress(String filePath,float scale,float outputQuality,String newPath) throws IOException {
    Thumbnails.of(filePath).scale(scale).outputQuality(outputQuality).toFile(newPath);
  }

  /**
   * 对图片进行指定比例的压缩
   * @param filePath 图片绝对源路径 等于 新图片路径
   * @param scale 图片大小 1是原图大小 0.5是原图的一半相当于长宽一半
   * @param outputQuality 图片质量 1是最高清 0是最差的
   */
  public static void compress(String filePath,float scale,float outputQuality) throws IOException {
    compress(filePath,scale,outputQuality,filePath);
  }

  /**
   * 对图片按照宽高进行压缩
   * @param filePath 图片绝对源路径 等于 新图片路径
   * @param width 宽度
   * @param height 长度
   * @param outputQuality 图片质量 1是最高清 0是最差的
   * @param newPath 新图片的路径
   */
  public static void compressSize(String filePath,int width,int height,float outputQuality,String newPath) throws IOException {
    Thumbnails.of(filePath).size(width,height).outputQuality(outputQuality).toFile(newPath);
  }

  /**
   * 对图片按照宽高进行压缩
   * @param filePath 图片绝对源路径
   * @param width 宽度
   * @param height 长度
   * @param outputQuality 图片质量 1是最高清 0是最差的
   */
  public static void compressSize(String filePath,int width,int height,float outputQuality) throws IOException {
    compressSize(filePath,width,height,outputQuality,filePath);
  }

  /**
   * 旋转
   * @param filePath 图片绝对源路径
   * @param scale 图片大小 1是原图大小 0.5是原图的一半相当于长宽一半
   * @param rotate 旋转度数
   * @param newPath 新图片绝对路径
   * @throws IOException
   */
  public static final void rotate(String filePath,float scale,double rotate,String newPath) throws IOException {
    Thumbnails.of(filePath).scale(scale).rotate(rotate).outputQuality(1).toFile(newPath);
  }

  /**
   * 旋转
   * @param filePath 图片绝对源路径
   * @param scale 图片大小 1是原图大小 0.5是原图的一半相当于长宽一半
   * @param rotate 旋转度数 负数为逆旋转
   * @throws IOException
   */
  public static final void rotate(String filePath,float scale,double rotate) throws IOException {
    rotate(filePath,scale,rotate,filePath);
  }

  /**
   * 图片格式转换
   * @param filePath 图片绝对源路径
   * @param scale 图片大小 1是原图大小 0.5是原图的一半相当于长宽一半
   * @param outputFormat 转换格式
   * @param newPath 新图片绝对路径
   */
  public static final void conversion(String filePath,float scale,String outputFormat,String newPath)
      throws IOException {
    Thumbnails.of(filePath).scale(scale).outputFormat(outputFormat).toFile(newPath);
  }

  /**
   * 图片格式转换
   * @param filePath 图片绝对源路径
   * @param scale 图片大小 1是原图大小 0.5是原图的一半相当于长宽一半
   * @param outputFormat 转换格式
   */
  public static final void conversion(String filePath,float scale,String outputFormat)
      throws IOException {
    conversion(filePath,scale,outputFormat,filePath);
  }

  /**
   * 裁剪图片
   * @param filePath 图片绝对源路径
   * @param scale 图片大小 1是原图大小 0.5是原图的一半相当于长宽一半
   * @param x 裁切图片的X轴
   * @param y 裁切图片的Y轴
   * @param width 裁切图片的宽度
   * @param height 裁切图片的高度
   * @param newPath 新图片绝对路径
   * @throws IOException
   */
  public static final void tailoring(String filePath,float scale,int x,int y,int width,int height,String newPath)
      throws IOException {
    Thumbnails.of(filePath).scale(scale).sourceRegion(x,y,width,height).outputQuality(1).toFile(newPath);
  }

  /**
   * 裁剪图片
   * @param filePath 图片绝对源路径
   * @param scale 图片大小 1是原图大小 0.5是原图的一半相当于长宽一半
   * @param x 裁切图片的X轴
   * @param y 裁切图片的Y轴
   * @param width 裁切图片的宽度
   * @param height 裁切图片的高度
   * @throws IOException
   */
  public static final void tailoring(String filePath,float scale,int x,int y,int width,int height)
      throws IOException {
    tailoring(filePath,scale,x,y,width,height,filePath);
  }

  /**
   * 添加水印
   * @param filePath 图片绝对源路径
   * @param watermarkPath 水印图片路径
   * @param scale 图片大小 1是原图大小 0.5是原图的一半相当于长宽一半
   * @param positions 水印位置
   * @param transparency 透明度
   * @param newPath 新图片绝对路径
   * @param watermarkSize 水印图片大小占原图的多少
   */
  public static final void watermark(String filePath,String watermarkPath,float scale, Positions positions,float transparency,String newPath,double watermarkSize)
      throws IOException {
    BufferedImage bufferedImage = compressWatermark(filePath,watermarkPath,watermarkSize);

    Thumbnails.of(filePath).scale(scale).watermark(positions,bufferedImage,transparency).outputQuality(1).toFile(newPath);
  }

  /**
   * 添加水印
   * @param filePath 图片绝对源路径
   * @param watermarkPath 水印图片路径
   * @param scale 图片大小 1是原图大小 0.5是原图的一半相当于长宽一半
   * @param x 水印放在原图的X轴
   * @param y 水印放在原图的Y轴
   * @param transparency 透明度
   * @param newPath 新图片绝对路径
   * @param watermarkSize 水印图片大小占原图的多少
   * @throws IOException
   */
  public static final void watermark(String filePath,String watermarkPath,float scale,int x,int y,float transparency,String newPath,double watermarkSize)
      throws IOException {
    BufferedImage bufferedImage = compressWatermark(filePath,watermarkPath,watermarkSize);

    Thumbnails.of(filePath).scale(scale).watermark(new Coordinate(x,y),bufferedImage,transparency).outputQuality(1).toFile(newPath);
  }

  /**
   * 添加水印
   * @param filePath 图片绝对源路径
   * @param watermarkPath 水印图片路径
   * @param scale 图片大小 1是原图大小 0.5是原图的一半相当于长宽一半
   * @param x 水印放在原图的X轴
   * @param y 水印放在原图的Y轴
   * @param width 水印宽
   * @param height 水印高
   * @param transparency 透明度
   * @param newPath 新图片绝对路径
   * @throws IOException
   */
  public static final void watermark(String filePath,String watermarkPath,float scale,int x,int y,int width,int height,float transparency,String newPath)
      throws IOException {
    BufferedImage bufferedImage = Thumbnails.of(watermarkPath).size(width,height)
        .keepAspectRatio(false).asBufferedImage();

    Thumbnails.of(filePath).scale(scale).watermark(new Coordinate(x,y),bufferedImage,transparency).outputQuality(1).toFile(newPath);
  }

  /**
   * 压缩水印图宽高
   * @param filePath 原图
   * @param watermarkPath 水印图
   * @param watermarkSize 水印图片大小占原图的多少
   * @return 返回压缩后的水印图
   * @throws IOException
   */
  private static BufferedImage compressWatermark(String filePath, String watermarkPath, double watermarkSize) throws IOException {
    // 读取原图，获取宽高
    BufferedImage image = ImageIO.read(new File(filePath));
    // 根据比例计算新的水印图宽高
    int width = (int) (image.getWidth() * watermarkSize);
    int height = width * image.getHeight() / image.getWidth();

    BufferedImage bufferedImage = Thumbnails.of(watermarkPath).size(width,height)
        .keepAspectRatio(false).asBufferedImage();
    return bufferedImage;
  }

}
