package com.xx.utils;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName: FunctionUtils
 * @Author: xueqimiao
 * @Date: 2021/11/19 10:50
 */
public interface FunctionUtils {

    /**
     * BinaryOperator: When repeating, select the first one.
     *
     * @param <T> the type of the input and output objects to the function
     * @return function that always returns the first one.
     */
    static <T> BinaryOperator<T> selectFirstOne() {
        return (existing, replacement) -> existing;
    }

    /**
     * Comparator: This element go to the first
     *
     * @param element element
     * @param <T>     the type of the input and output objects to the function
     * @return function that always sort the element to first
     */
    static <T> Comparator<T> goToFirst(T element) {
        return (s1, s2) -> Objects.equals(s1, element) ? -1 : (Objects.equals(s2, element) ? 1 : 0);
    }

    /**
     * Return the stream of collection, null as empty stream, for lambda use
     *
     * @param collection origin collection
     * @param <T>        type
     * @return null as empty stream
     */
    static <T> Stream<T> nullAsEmptyStream(Collection<T> collection) {
        if (ValidationUtil.isEmpty(collection)) {
            return Stream.empty();
        }
        return collection.stream();
    }

    /**
     * Return the parallelStream of collection, null as empty strparallelStreameam, for lambda use
     *
     * @param collection
     * @param <T>
     * @return
     */
    static <T> Stream<T> nullAsEmptyParallelStream(Collection<T> collection) {
        if (ValidationUtil.isEmpty(collection)) {
            return Stream.empty();
        }
        return collection.parallelStream();
    }

    /**
     * batch and parallel exec function. please distinct params if necessary.
     *
     * @param originParams       origin params
     * @param parameterConverter params convert
     * @param execFunction       exec function
     * @param batchSize          batch size
     * @param <T>                origin params type
     * @param <R>                new type, exec function param
     * @param <U>                result type, exec function result
     * @return batch and parallel result
     */
    static <T, R, U> List<U> batchAndParallel(List<T> originParams,
                                              Function<List<T>, R> parameterConverter,
                                              Function<R, U> execFunction,
                                              Integer batchSize) {
        if (ValidationUtil.isEmpty(originParams)) {
            return Collections.emptyList();
        }
        //Keep in mind that the partitions are sublist views of the original collection
        List<List<T>> partition = Lists.partition(originParams, batchSize);
        return partition.parallelStream()
                .map(parameterConverter)
                .map(execFunction)
                .collect(Collectors.toList());
    }

    /**
     * 封装最常用的map函数
     *
     * @param list     集合
     * @param function 你想要进行的操作，需要带返回值，lambda写法例如：{@code i-> i.getId()或者 User::getId }
     * @param <O>      原来的集合中元素类型
     * @param <R>      map之后的集合中元素类型
     * @return 执行了map后的集合
     */
    static <O, R> List<R> map(List<O> list, Function<O, R> function) {
        return FunctionUtils.nullAsEmptyStream(list).map(function).collect(Collectors.toList());
    }

    static <O, R> Set<R> mapToSet(List<O> list, Function<O, R> function) {
        return FunctionUtils.nullAsEmptyStream(list).map(function).collect(Collectors.toSet());
    }

    /**
     * 封装了最常用的map函数的变种peek函数
     *
     * @param list     集合
     * @param function 你想要进行的操作
     * @param <E>      集合中元素类型
     * @return 执行了peek后的集合
     */
    static <E> List<E> peek(List<E> list, Consumer<E> function) {
        return FunctionUtils.nullAsEmptyStream(list).peek(function).collect(Collectors.toList());
    }

    /**
     * 封装了使用Stream过滤集合的方法
     *
     * @param list      集合
     * @param predicate 过滤的条件，lambda写法例如：{@code i-> i.getId() != null }
     * @param <E>       集合中元素类型
     * @return 过滤后的集合
     */
    static <E> List<E> filter(List<E> list, Predicate<E> predicate) {
        return FunctionUtils.nullAsEmptyStream(list).filter(predicate).collect(Collectors.toList());
    }

    /**
     * list转map
     * {@code List<实体>} 转为 {@code Map<实体属性,实体>}
     *
     * @param list        集合
     * @param keyFunction 作为key的lambda
     * @param <E>         集合类型
     * @param <K>         key的类型
     * @return {@code Map<实体属性,实体>}
     */
    static <E, K> Map<K, E> toMap(List<E> list, Function<E, K> keyFunction) {
        return FunctionUtils.nullAsEmptyStream(list).collect(HashMap::new, (m, v) -> m.put(Optional.ofNullable(v).map(keyFunction).orElse(null), v), HashMap::putAll);
    }

    static <E, K, U> Map<K, U> toMap(List<E> list, Function<E, K> keyFunction, Function<E, U> valueMapper) {
        return FunctionUtils.nullAsEmptyStream(list).collect(HashMap::new, (m, v) -> m.put(Optional.ofNullable(v).map(keyFunction).orElse(null), Optional.ofNullable(v).map(valueMapper).orElse(null)), HashMap::putAll);
    }

    /**
     * 通过实体属性分组
     * {@code List<实体>} 转为 {@code Map<实体属性,List<实体>>}
     *
     * @param list        集合
     * @param keyFunction 作为key的lambda
     * @param <E>         集合类型
     * @param <K>         key的类型
     * @return {@code Map<实体属性,List<实体>>}
     */
    static <E, K> Map<K, List<E>> group(List<E> list, Function<E, K> keyFunction) {
        if(ValidationUtil.isEmpty(keyFunction)){
            return Collections.EMPTY_MAP;
        }
        return groupThen(list, keyFunction, Collectors.toList());
    }

    /**
     * List 转 Map
     * @param list         集合
     * @param keyFunction  作为key的lambda
     * @param <E>          集合类型
     * @param <K>          key的类型
     * @return 一对一
     */
    static <E, K> Map<K, E> listToMap(List<E> list, Function<E, K> keyFunction) {
        if(ValidationUtil.isEmpty(keyFunction)){
            return Collections.EMPTY_MAP;
        }
        return FunctionUtils.nullAsEmptyStream(list).collect(HashMap::new, (m, v) -> m.put(Optional.ofNullable(v).map(keyFunction).orElse(null), v), HashMap::putAll);
    }

    /**
     * 通过实体属性分组，还能继续后续操作
     * {@code List<实体>} 转为 {@code Map<实体属性,?>}
     *
     * @param list        集合
     * @param keyFunction 作为key的lambda
     * @param downstream  可能你还想进行别的操作
     * @param <E>         集合类型
     * @param <K>         key的类型
     * @param <R>         你想进行的后续操作类型
     * @return {@code Map<实体属性,?>}
     */
    static <E, K, R> Map<K, R> groupThen(List<E> list, Function<E, K> keyFunction, Collector<E, ?, R> downstream) {
        return FunctionUtils.nullAsEmptyStream(list).collect(Collectors.groupingBy(keyFunction, downstream));
    }

}

