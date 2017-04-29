import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Eric on 29/04/2017.
 */
public class StreamSample {

    public static void main(String[] args) {

        // 使用Stream.forEach()迭代
        Stream<String> stream_forEach = Stream.of("I", "love", "you", "too");
        stream_forEach.forEach(str -> System.out.println(str));

        // 函数原型为Stream<T> filter(Predicate<? super T> predicate)，作用是返回一个只包含满足predicate条件元素的Stream。
        Stream<String> stream_filter = Stream.of("I", "love", "you", "too");
        stream_filter.filter(str -> str.length() == 3)
                .forEach(str -> System.out.println(str));

        //函数原型为Stream<T> distinct()，作用是返回一个去除重复元素之后的Stream。
        Stream<String> stream_distinct = Stream.of("I", "love", "you", "too", "too");
        stream_distinct.distinct()
                .forEach(str -> System.out.println(str));

        //函数原型为<R> Stream<R> map(Function<? super T,? extends R> mapper)，作用是返回一个对当前所有元素执行执行mapper之后的结果组成的Stream。
        // 直观的说，就是对每个元素按照某种操作进行转换，转换前后Stream中元素的个数不会改变，但元素的类型取决于转换之后的类型。
        Stream<String> stream_map = Stream.of("I", "love", "you", "too");
        stream_map.map(str -> str.toUpperCase())
                .forEach(str -> System.out.println(str));


        Stream<String> stream_map1 = Stream.of("I", "love", "you", "too");
        stream_map1.map(str -> str + 1)
                .forEach(str -> System.out.println(str));

        //函数原型为<R> Stream<R> flatMap(Function<? super T,? extends Stream<? extends R>> mapper)，作用是对每个元素执行mapper指定的操作，
        // 并用所有mapper返回的Stream中的元素组成一个新的Stream作为最终返回结果。
        // 说起来太拗口，通俗的讲flatMap()的作用就相当于把原stream中的所有元素都"摊平"之后组成的Stream，转换前后元素的个数和类型都可能会改变。

        Stream<List<Integer>> stream_flatMap = Stream.of(Arrays.asList(1, 2), Arrays.asList(3, 4, 5));
        stream_flatMap.flatMap(list -> list.stream())
                .forEach(i -> System.out.println(i));

        /**
         reduce操作可以实现从一组元素中生成一个值，sum()、max()、min()、count()等都是reduce操作，将他们单独设为函数只是因为常用。reduce()的方法定义有三种重写形式：

         Optional<T> reduce(BinaryOperator<T> accumulator)
         T reduce(T identity, BinaryOperator<T> accumulator)
         <U> U reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)
         虽然函数定义越来越长，但语义不曾改变，多的参数只是为了指明初始值（参数identity），或者是指定并行执行时多个部分结果的合并方式（参数combiner）。
         reduce()最常用的场景就是从一堆值中生成一个值。用这么复杂的函数去求一个最大或最小值，你是不是觉得设计者有病。其实不然，因为“大”和“小”或者“求和"有时会有不同的语义。*/

        //从一组单词中找出最长的单词。
        Stream<String> reduce_stream = Stream.of("I", "love", "you", "too");
        Optional<String> longest = reduce_stream.reduce((s1, s2) -> s1.length() >= s2.length() ? s1 : s2);
        //Optional<String> longest = stream.max((s1, s2) -> s1.length()-s2.length());
        System.out.println(longest.get());



        // 求单词长度之和
        Stream<String> sum_reduce_stream = Stream.of("I", "love", "you", "too");
                                        // 初始值　// (1)
        Integer lengthSum = sum_reduce_stream.parallel().reduce(0,
                // 累加器 // (2)
                (sum, str) -> sum+str.length(),
                // 部分和拼接器，并行执行时才会用到 // (3)
                (a, b) -> a+b);
        // int lengthSum = stream.mapToInt(str -> str.length()).sum();
        System.out.println(lengthSum);
    }
}
