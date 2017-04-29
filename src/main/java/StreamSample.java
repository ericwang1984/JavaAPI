import java.util.Arrays;
import java.util.List;
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

        //函数原型为<R> Stream<R> flatMap(Function<? super T,? extends Stream<? extends R>> mapper)，作用是对每个元素执行mapper指定的操作，
        // 并用所有mapper返回的Stream中的元素组成一个新的Stream作为最终返回结果。
        // 说起来太拗口，通俗的讲flatMap()的作用就相当于把原stream中的所有元素都"摊平"之后组成的Stream，转换前后元素的个数和类型都可能会改变。

        Stream<List<Integer>> stream_flatMap = Stream.of(Arrays.asList(1,2), Arrays.asList(3, 4, 5));
        stream_flatMap.flatMap(list -> list.stream())
                .forEach(i -> System.out.println(i));


    }
}
