package com.steven.solomon.utils.db;//package com.steven.strive.utils.db;
//
//import cn.hutool.core.collection.CollectionUtil;
//import cn.hutool.core.io.FileUtil;
//import cn.hutool.core.util.ObjectUtil;
//import com.steven.strive.utils.lambda.LambdaUtils;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Map;
//
//public class DbScriptUtil {
//
//  /**
//   * 获取脚本列表
//   * @param filePath 文件目录路径
//   * @return
//   */
//  public static List<File> getScript(String filePath){
//    File[] files = FileUtil.ls(filePath);
//    if(ObjectUtil.isNull(files)){
//      return null;
//    }
//    List<File>       list = Arrays.asList(files);
//    Collections.sort(list, new Comparator<File>() {
//      @Override
//      public int compare(File o1, File o2) {
//        if (o1.isDirectory() && o2.isFile()) {
//          return -1;
//        }
//        if (o1.isFile() && o2.isDirectory()) {
//          return 1;
//        }
//        String o1Name = o1.getName();
//        o1Name = o1Name.substring(0, o1Name.indexOf("_"));
//        String o2Name = o2.getName();
//        o2Name = o2Name.substring(0, o2Name.indexOf("_"));
//        return o1Name.compareTo(o2Name);
//      }
//    });
//    return list;
//  }
//
//  public static void main(String[] args) {
//    String str ="V1_123.sql";
//    String str1=str.substring(0, str.indexOf("_"));
//    System.out.println(str1);
//  }
//
//  /**
//   * 执行脚本
//   * @param filePath 文件目录路径
//   * @throws IOException
//   */
//  public static void executeScript(String filePath) throws IOException {
//    List<File> files = getScript(filePath);
//    if(CollectionUtil.isEmpty(files)){
//      return;
//    }
//    //TODO 根据dbTemple查询sql执行表查询最新的一条
//    for(File file : files){
//      //TODO 根据数据库最新的一条记录判断当前文件是否需要执行
//
//      List<String> readScriptList= readScript(new BufferedReader(new InputStreamReader(new FileInputStream(file))));
//      //TODO 使用dbTemple执行语句
//
//      //TODO 创建sql执行表对象把执行的文件记录保存到数据库内
//    }
//
//  }
//
//  /**
//   * 读取文件内的内容
//   * @param br 从字符输入流中读取文本并缓冲字符，以便有效地读取字符，数组和行
//   */
//  private static List<String> readScript(BufferedReader br) throws IOException {
//    List<String>  sqlScripts = new ArrayList<>();
//    StringBuilder sqlScript  = new StringBuilder();
//    for (String line = br.readLine(); line != null; line = br.readLine()) {
//      String content = line.trim().toLowerCase();
//      if (content.isEmpty()) {
//        continue;
//      }
//      if (content.contains(";")) {
//        sqlScript.append(line);
//        sqlScripts.add(String.valueOf(sqlScript));
//        sqlScript = new StringBuilder();
//      } else {
//        sqlScript.append(line).append("\r\n");
//      }
//    }
//    if (sqlScript.length() > 0) {
//      sqlScripts.add(sqlScript.toString());
//    }
//    return sqlScripts;
//  }
//}
