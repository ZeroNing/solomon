package com.steven.solomon.utils.lambda;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

public class LambdaUtils {

  public static <T, S> List<T> toList(Collection<S> List, Predicate<S> predicate, Function<S, T> func) {
    return List.stream().filter(predicate).map(func).collect(Collectors.toList());
  }

  public static <T, S> List<T> toList(Collection<S> List, Function<S, T> func) {
    return List.stream().map(func).collect(Collectors.toList());
  }

  public static <T, S> Set<T> toSet(Collection<S> List, Predicate<S> predicate, Function<S, T> func) {
    return List.stream().filter(predicate).map(func).collect(Collectors.toSet());
  }

  public static <T, S> Set<T> toSet(Collection<S> List, Function<S, T> func) {
    return List.stream().map(func).collect(Collectors.toSet());
  }

  public static <K, T> Map<K, T> toMap(Collection<T> List, Function<T, K> keyFunc) {
    return List.stream().collect(Collectors.toMap(keyFunc, Function.identity(), (key1, key2) -> key2));
  }

  public static <K, T> Map<K, T> toMap(Collection<T> List, Predicate<T> predicate, Function<T, K> keyFunc) {
    return List.stream().filter(predicate).collect(Collectors.toMap(keyFunc, Function.identity(), (key1, key2) -> key2));
  }

  public static <K, V, T> Map<K, V> toMap(Collection<T> List, Predicate<T> predicate, Function<T, K> keyFunc, Function<T, V> valFunc) {
    return List.stream().filter(predicate).collect(Collectors.toMap(keyFunc, valFunc, (key1, key2) -> key2));
  }

  public static <K, V, T> Map<K, V> toMap(Collection<T> List, Function<T, K> keyFunc, Function<T, V> valFunc) {
    return List.stream().collect(Collectors.toMap(keyFunc, valFunc, (key1, key2) -> key2));
  }

  public static <K, T> Map<K, List<T>> groupBy(Collection<T> list, Predicate<T> predicate, Function<T, K> groupByFunc) {
    return list.stream().filter(predicate).collect(Collectors.groupingBy(groupByFunc));
  }

  public static <K, T> Map<K, List<T>> groupBy(Collection<T> list, Function<T, K> groupByFunc) {
    return list.stream().collect(Collectors.groupingBy(groupByFunc));
  }

  public static <T> Integer sum(Collection<T> list, ToIntFunction<T> func) {
    return list.stream().mapToInt(func).sum();
  }

  public static <T> Integer sum(Collection<T> list, Predicate<T> predicate, ToIntFunction<T> func) {
    return list.stream().filter(predicate).mapToInt(func).sum();
  }

  public static <T> Long sum(Collection<T> list, ToLongFunction<T> func) {
    return list.stream().mapToLong(func).sum();
  }

  public static <T> Long sum(Collection<T> list, Predicate<T> predicate, ToLongFunction<T> func) {
    return list.stream().filter(predicate).mapToLong(func).sum();
  }

  public static <T> Double sum(Collection<T> list, ToDoubleFunction<T> func) {
    return list.stream().mapToDouble(func).sum();
  }

  public static <T> Double sum(Collection<T> list, Predicate<T> predicate, ToDoubleFunction<T> func) {
    return list.stream().filter(predicate).mapToDouble(func).sum();
  }

  public static<K, T> Map<K, Long> groupByCount(Collection<T> list, Function<T, K> groupByFunc){
    return list.stream().collect(Collectors.groupingBy(groupByFunc,Collectors.counting()));
  }

  public static<K, T> Map<K, Long> groupByCount(Collection<T> list, Function<T, K> groupByFunc, Predicate<T> predicate){
    return list.stream().filter(predicate).collect(Collectors.groupingBy(groupByFunc,Collectors.counting()));
  }

  public static<K, T> Map<K, Integer> groupBySum(Collection<T> list, Function<T, K> groupByFunc,ToIntFunction<T> sumFunc){
    return list.stream().collect(Collectors.groupingBy(groupByFunc,Collectors.summingInt(sumFunc)));
  }

  public static<K, T> Map<K, Integer> groupBySum(Collection<T> list, Function<T, K> groupByFunc, Predicate<T> predicate,ToIntFunction<T> sumFunc){
    return list.stream().filter(predicate).collect(Collectors.groupingBy(groupByFunc,Collectors.summingInt(sumFunc)));
  }

  public static<K, T> Map<K, Long> groupBySum(Collection<T> list, Function<T, K> groupByFunc,ToLongFunction<T> sumFunc){
    return list.stream().collect(Collectors.groupingBy(groupByFunc,Collectors.summingLong(sumFunc)));
  }

  public static<K, T> Map<K, Long> groupBySum(Collection<T> list, Function<T, K> groupByFunc, Predicate<T> predicate,ToLongFunction<T> sumFunc){
    return list.stream().filter(predicate).collect(Collectors.groupingBy(groupByFunc,Collectors.summingLong(sumFunc)));
  }

  public static<K, T> Map<K, Double> groupBySum(Collection<T> list, Function<T, K> groupByFunc,ToDoubleFunction<T> sumFunc){
    return list.stream().collect(Collectors.groupingBy(groupByFunc,Collectors.summingDouble(sumFunc)));
  }

  public static<K, T> Map<K, Double> groupBySum(Collection<T> list, Function<T, K> groupByFunc, Predicate<T> predicate,ToDoubleFunction<T> sumFunc){
    return list.stream().filter(predicate).collect(Collectors.groupingBy(groupByFunc,Collectors.summingDouble(sumFunc)));
  }

  public static <T>List<T> intersection(Collection<T> sourceList,Predicate<T> predicate){
    return sourceList.stream().filter(predicate).collect(Collectors.toList());
  }

  public static <T>List<T> reduce(Collection<T> sourceList,Predicate<T> predicate){
    return sourceList.stream().filter(predicate).collect(Collectors.toList());
  }
}
