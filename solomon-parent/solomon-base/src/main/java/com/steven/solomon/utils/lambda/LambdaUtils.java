package com.steven.solomon.utils.lambda;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collectors;

public class LambdaUtils {

  /**
   * 转list
   * @param List 数据集合
   * @param predicate 条件筛选数据
   * @param func 需要的字段
   */
  public static <T, S> List<T> toList(Collection<S> List, Predicate<S> predicate, Function<S, T> func) {
    return List.stream().filter(predicate).map(func).collect(Collectors.toList());
  }
  /**
   * 转list
   * @param List 数据集合
   * @param func 需要的字段
   */
  public static <T, S> List<T> toList(Collection<S> List, Function<S, T> func) {
    return List.stream().map(func).collect(Collectors.toList());
  }
  /**
   * 转set
   * @param List 数据集合
   * @param predicate 条件筛选数据
   * @param func 需要的字段
   */
  public static <T, S> Set<T> toSet(Collection<S> List, Predicate<S> predicate, Function<S, T> func) {
    return List.stream().filter(predicate).map(func).collect(Collectors.toSet());
  }
  /**
   * 转set
   * @param List 数据集合
   * @param func 需要的字段
   */
  public static <T, S> Set<T> toSet(Collection<S> List, Function<S, T> func) {
    return List.stream().map(func).collect(Collectors.toSet());
  }
  /**
   * 转map
   * @param List 数据集合
   * @param keyFunc 需要的字段
   */
  public static <K, T> Map<K, T> toMap(Collection<T> List, Function<T, K> keyFunc) {
    return List.stream().collect(Collectors.toMap(keyFunc, Function.identity(), (key1, key2) -> key2));
  }
  /**
   * 转map
   * @param List 数据集合
   * @param predicate 条件筛选数据
   * @param keyFunc 需要的字段
   */
  public static <K, T> Map<K, T> toMap(Collection<T> List, Predicate<T> predicate, Function<T, K> keyFunc) {
    return List.stream().filter(predicate).collect(Collectors.toMap(keyFunc, Function.identity(), (key1, key2) -> key2));
  }

  /**
   * 转map
   * @param List 数据集合
   * @param predicate 条件筛选数据
   * @param keyFunc 需要的字段
   * @param valFunc 需要的字段
   */
  public static <K, V, T> Map<K, V> toMap(Collection<T> List, Predicate<T> predicate, Function<T, K> keyFunc, Function<T, V> valFunc) {
    return List.stream().filter(predicate).collect(Collectors.toMap(keyFunc, valFunc, (key1, key2) -> key2));
  }
  /**
   * 转map
   * @param List 数据集合
   * @param keyFunc 需要的字段
   * @param valFunc 需要的字段
   */
  public static <K, V, T> Map<K, V> toMap(Collection<T> List, Function<T, K> keyFunc, Function<T, V> valFunc) {
    return List.stream().collect(Collectors.toMap(keyFunc, valFunc, (key1, key2) -> key2));
  }
  /**
   * 分组
   * @param list 数据集合
   * @param predicate 条件筛选数据
   * @param groupByFunc 分组需要字段
   */
  public static <K, T> Map<K, List<T>> groupBy(Collection<T> list, Predicate<T> predicate, Function<T, K> groupByFunc) {
    return list.stream().filter(predicate).collect(Collectors.groupingBy(groupByFunc));
  }
  /**
   * 分组
   * @param list 数据集合
   * @param groupByFunc 分组需要字段
   */
  public static <K, T> Map<K, List<T>> groupBy(Collection<T> list, Function<T, K> groupByFunc) {
    return list.stream().collect(Collectors.groupingBy(groupByFunc));
  }
  /**
   * 汇总
   * @param list 数据集合
   * ram func 分组汇总相加的字段
   */
  public static <T> Integer sum(Collection<T> list, ToIntFunction<T> func) {
    return list.stream().mapToInt(func).sum();
  }
  /**
   * 汇总
   * @param list 数据集合
   * @param predicate 条件筛选数据
   * @param func 分组汇总相加的字段
   */
  public static <T> Integer sum(Collection<T> list, Predicate<T> predicate, ToIntFunction<T> func) {
    return list.stream().filter(predicate).mapToInt(func).sum();
  }
  /**
   * 汇总
   * @param list 数据集合
   * @param func 分组汇总相加的字段
   */
  public static <T> Long sum(Collection<T> list, ToLongFunction<T> func) {
    return list.stream().mapToLong(func).sum();
  }
  /**
   * 汇总
   * @param list 数据集合
   * @param predicate 条件筛选数据
   * @param func 分组汇总相加的字段
   */
  public static <T> Long sum(Collection<T> list, Predicate<T> predicate, ToLongFunction<T> func) {
    return list.stream().filter(predicate).mapToLong(func).sum();
  }
  /**
   * 汇总
   * @param list 数据集合
   * @param func 分组汇总相加的字段
   */
  public static <T,K> Double sum(Collection<T> list, ToDoubleFunction<T> func) {
    return list.stream().mapToDouble(func).sum();
  }
  /**
   * 汇总
   * @param list 数据集合
   * @param predicate 条件筛选数据
   * @param func 分组汇总相加的字段
   */
  public static <T> Double sum(Collection<T> list, Predicate<T> predicate, ToDoubleFunction<T> func) {
    return list.stream().filter(predicate).mapToDouble(func).sum();
  }
  /**
   * 分组统计
   * @param list 数据集合
   * @param groupByFunc 分组统计的字段
   */
  public static<K, T> Map<K, Long> groupByCount(Collection<T> list, Function<T, K> groupByFunc){
    return list.stream().collect(Collectors.groupingBy(groupByFunc,Collectors.counting()));
  }
  /**
   * 分组统计
   * @param list 数据集合
   * @param predicate 条件筛选数据
   * @param groupByFunc 分组统计的字段
   */
  public static<K, T> Map<K, Long> groupByCount(Collection<T> list, Function<T, K> groupByFunc, Predicate<T> predicate){
    return list.stream().filter(predicate).collect(Collectors.groupingBy(groupByFunc,Collectors.counting()));
  }

  /**
   * 分组汇总
   * @param list 数据集合
   * @param groupByFunc 分组字段
   * @param sumFunc  分组汇总相加的字段
   */
  public static<K, T> Map<K, Integer> groupBySum(Collection<T> list, Function<T, K> groupByFunc,ToIntFunction<T> sumFunc){
    return list.stream().collect(Collectors.groupingBy(groupByFunc,Collectors.summingInt(sumFunc)));
  }
  /**
   * 分组汇总
   * @param list 数据集合
   * @param groupByFunc 分组字段
   * @param predicate 条件筛选数据
   * @param sumFunc  分组汇总相加的字段
   */
  public static<K, T> Map<K, Integer> groupBySum(Collection<T> list, Function<T, K> groupByFunc, Predicate<T> predicate,ToIntFunction<T> sumFunc){
    return list.stream().filter(predicate).collect(Collectors.groupingBy(groupByFunc,Collectors.summingInt(sumFunc)));
  }
  /**
   * 分组汇总
   * @param list 数据集合
   * @param groupByFunc 分组字段
   * @param sumFunc  分组汇总相加的字段
   */
  public static<K, T> Map<K, Long> groupBySum(Collection<T> list, Function<T, K> groupByFunc,ToLongFunction<T> sumFunc){
    return list.stream().collect(Collectors.groupingBy(groupByFunc,Collectors.summingLong(sumFunc)));
  }
  /**
   * 分组汇总
   * @param list 数据集合
   * @param groupByFunc 分组字段
   * @param predicate 条件筛选数据
   * @param sumFunc  分组汇总相加的字段
   */
  public static<K, T> Map<K, Long> groupBySum(Collection<T> list, Function<T, K> groupByFunc, Predicate<T> predicate,ToLongFunction<T> sumFunc){
    return list.stream().filter(predicate).collect(Collectors.groupingBy(groupByFunc,Collectors.summingLong(sumFunc)));
  }
  /**
   * 分组汇总
   * @param list 数据集合
   * @param groupByFunc 分组字段
   * @param sumFunc  分组汇总相加的字段
   */
  public static<K, T> Map<K, Double> groupBySum(Collection<T> list, Function<T, K> groupByFunc,ToDoubleFunction<T> sumFunc){
    return list.stream().collect(Collectors.groupingBy(groupByFunc,Collectors.summingDouble(sumFunc)));
  }
  /**
   * 分组汇总
   * @param list 数据集合
   * @param groupByFunc 分组字段
   * @param predicate 条件筛选数据
   * @param sumFunc  分组汇总相加的字段
   */
  public static<K, T> Map<K, Double> groupBySum(Collection<T> list, Function<T, K> groupByFunc, Predicate<T> predicate,ToDoubleFunction<T> sumFunc){
    return list.stream().filter(predicate).collect(Collectors.groupingBy(groupByFunc,Collectors.summingDouble(sumFunc)));
  }

  /**
   * 交叉集
   * @param sourceList 数据集合
   * @param predicate 条件筛选数据
   */
  public static <T>List<T> cross(Collection<T> sourceList,Predicate<T> predicate){
    return sourceList.stream().filter(predicate).collect(Collectors.toList());
  }

  /**
   * 找出最大值
   * @param list 数据集合
   * ram func 分组汇总相加的字段
   */
  public static <T> Integer max(Collection<T> list, ToIntFunction<T> func) {
    return list.stream().mapToInt(func).max().getAsInt();
  }

  /**
   * 找出最大值
   * @param list 数据集合
   * @param predicate 条件筛选数据
   * @param func 分组汇总相加的字段
   */
  public static <T> Integer max(Collection<T> list, Predicate<T> predicate, ToIntFunction<T> func) {
    return list.stream().filter(predicate).mapToInt(func).max().getAsInt();
  }

  /**
   * 找出最大值
   * @param list 数据集合
   * @param func 分组汇总相加的字段
   */
  public static <T> Long max(Collection<T> list, ToLongFunction<T> func) {
    return list.stream().mapToLong(func).max().getAsLong();
  }
  /**
   * 找出最大值
   * @param list 数据集合
   * @param predicate 条件筛选数据
   * @param func 分组汇总相加的字段
   */
  public static <T> Long max(Collection<T> list, Predicate<T> predicate, ToLongFunction<T> func) {
    return list.stream().filter(predicate).mapToLong(func).max().getAsLong();
  }
  /**
   * 找出最大值
   * @param list 数据集合
   * @param func 分组汇总相加的字段
   */
  public static <T,K> Double max(Collection<T> list, ToDoubleFunction<T> func) {
    return list.stream().mapToDouble(func).max().getAsDouble();
  }
  /**
   * 找出最大值
   * @param list 数据集合
   * @param predicate 条件筛选数据
   * @param func 分组汇总相加的字段
   */
  public static <T> Double max(Collection<T> list, Predicate<T> predicate, ToDoubleFunction<T> func) {
    return list.stream().filter(predicate).mapToDouble(func).max().getAsDouble();
  }

  /**
   * 找出最小值
   * @param list 数据集合
   * ram func 分组汇总相加的字段
   */
  public static <T> Integer min(Collection<T> list, ToIntFunction<T> func) {
    return list.stream().mapToInt(func).min().getAsInt();
  }

  /**
   * 找出最小值
   * @param list 数据集合
   * @param predicate 条件筛选数据
   * @param func 分组汇总相加的字段
   */
  public static <T> Integer min(Collection<T> list, Predicate<T> predicate, ToIntFunction<T> func) {
    return list.stream().filter(predicate).mapToInt(func).min().getAsInt();
  }

  /**
   * 找出最小值
   * @param list 数据集合
   * @param func 分组汇总相加的字段
   */
  public static <T> Long min(Collection<T> list, ToLongFunction<T> func) {
    return list.stream().mapToLong(func).min().getAsLong();
  }
  /**
   * 找出最小值
   * @param list 数据集合
   * @param predicate 条件筛选数据
   * @param func 分组汇总相加的字段
   */
  public static <T> Long min(Collection<T> list, Predicate<T> predicate, ToLongFunction<T> func) {
    return list.stream().filter(predicate).mapToLong(func).min().getAsLong();
  }
  /**
   * 找出最小值
   * @param list 数据集合
   * @param func 分组汇总相加的字段
   */
  public static <T,K> Double min(Collection<T> list, ToDoubleFunction<T> func) {
    return list.stream().mapToDouble(func).min().getAsDouble();
  }
  /**
   * 找出最小值
   * @param list 数据集合
   * @param predicate 条件筛选数据
   * @param func 分组汇总相加的字段
   */
  public static <T> Double min(Collection<T> list, Predicate<T> predicate, ToDoubleFunction<T> func) {
    return list.stream().filter(predicate).mapToDouble(func).min().getAsDouble();
  }

  /**
   * 平均值
   * @param list 数据集合
   * ram func 分组汇总相加的字段
   */
  public static <T> Double average(Collection<T> list, ToIntFunction<T> func) {
    return list.stream().mapToInt(func).average().getAsDouble();
  }

  /**
   * 平均值
   * @param list 数据集合
   * @param predicate 条件筛选数据
   * @param func 分组汇总相加的字段
   */
  public static <T> Double average(Collection<T> list, Predicate<T> predicate, ToIntFunction<T> func) {
    return list.stream().filter(predicate).mapToInt(func).average().getAsDouble();
  }

  /**
   * 平均值
   * @param list 数据集合
   * @param func 分组汇总相加的字段
   */
  public static <T> Double average(Collection<T> list, ToLongFunction<T> func) {
    return list.stream().mapToLong(func).average().getAsDouble();
  }
  /**
   * 平均值
   * @param list 数据集合
   * @param predicate 条件筛选数据
   * @param func 分组汇总相加的字段
   */
  public static <T> Double average(Collection<T> list, Predicate<T> predicate, ToLongFunction<T> func) {
    return list.stream().filter(predicate).mapToLong(func).average().getAsDouble();
  }
  /**
   * 平均值
   * @param list 数据集合
   * @param func 分组汇总相加的字段
   */
  public static <T,K> Double average(Collection<T> list, ToDoubleFunction<T> func) {
    return list.stream().mapToDouble(func).average().getAsDouble();
  }
  /**
   * 平均值
   * @param list 数据集合
   * @param predicate 条件筛选数据
   * @param func 分组汇总相加的字段
   */
  public static <T> Double average(Collection<T> list, Predicate<T> predicate, ToDoubleFunction<T> func) {
    return list.stream().filter(predicate).mapToDouble(func).average().getAsDouble();
  }
}
