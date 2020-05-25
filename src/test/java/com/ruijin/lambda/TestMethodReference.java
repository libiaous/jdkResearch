package com.ruijin.lambda;

import static org.junit.Assert.*;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestMethodReference {

    @Test
    public void testCapitalizeMethod() {
        List<String> inputList = Arrays.asList("hello", "baeldung", "readers!");
        inputList.forEach(x -> StringUtils.capitalize(x));
    }

    //reference to static method
    @Test
    public void testCapitalizeMethodWithStaticMethodReference() {
        List<String> inputList = Arrays.asList("hello", "baeldung", "readers!");
        inputList.forEach(StringUtils::capitalize);
    }

    @Test
    public void testBicycleComparator() {
        BicycleComparator bikeFrameSizeComparator = new BicycleComparator();
        List<Bicycle> list = Bicycle.getBicycleList(10).stream().sorted((a, b) -> bikeFrameSizeComparator.compare(a, b)).collect(Collectors.toList());
        System.out.println(Arrays.toString(list.toArray()));
    }

    //Reference to an Instance Method of a Particular Object
    @Test
    public void testBicycleComparatorWithObjectMethodReference() {
        BicycleComparator bikeFrameSizeComparator = new BicycleComparator();
        List<Bicycle> list = Bicycle.getBicycleList(10).stream().sorted(
                bikeFrameSizeComparator::compare
        ).collect(Collectors.toList());
        System.out.println(Arrays.toString(list.toArray()));
    }

    @Test
    public void testIntegerSort() {
        List<String> inputList = Arrays.asList("hello", "world", "java", "is", "best");
        List<String> outputList = inputList.stream().map(x -> x.toUpperCase()).collect(Collectors.toList());
        System.out.println(Arrays.toString(outputList.toArray()));
    }

    //Reference to an Instance Method of an Arbitrary Object of a Particular Type.Instance method from class type
    @Test
    public void testIntegerSortWithArbitraryObjectOfParticularType() {
        List<String> inputList = Arrays.asList("hello", "world", "java", "is", "best");
        List<String> outputList = inputList.stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(Arrays.toString(outputList.toArray()));
    }

    @Test
    public void testNewObject() {
        List<String> bikeBrands = Arrays.asList("Giant", "Scott", "Trek", "GT");
        List<Bicycle> newBicycleList = bikeBrands.stream().map(brand -> new Bicycle(brand)).collect(Collectors.toList());
        System.out.println(Arrays.toString(newBicycleList.toArray()));
    }

    @Test
    public void testNewObjectWithMethodReference() {
        List<String> bikeBrands = Arrays.asList("Giant", "Scott", "Trek", "GT");
        List<Bicycle> newBicycleList = bikeBrands.stream().map(Bicycle::new).collect(Collectors.toList());
        System.out.println(Arrays.toString(newBicycleList.toArray()));
    }

    @Test
    public void testNewObjectWithMethodReference2() {
        List<Integer> list = IntStream.range(1, 10).boxed().collect(Collectors.toCollection(ArrayList::new));
        System.out.println(Arrays.toString(list.toArray()));
    }

    @Test
    public void testNewObjectWithMethodReference3() {
        List<String> bikeBrands = Arrays.asList("Giant", "Scott", "Trek", "GT");
        List<Bicycle> newBicycleList = bikeBrands.stream().map(brand -> new Bicycle(brand, brand)).collect(Collectors.toList());
        System.out.println(Arrays.toString(newBicycleList.toArray()));
    }

    @Test
    public void testNewObjectWithMethodReference4() {
        Consumer<String> d = System.out::println;
        d.accept("Hello");
    }

    @Test
    public void testNewObjectWithMethodReference5() {
        Function<String, Bicycle> f1 = Bicycle::new;
        BiFunction<String, String, Bicycle> f2 = Bicycle::new;
        Consumer<Bicycle> myPrint = System.out::println;

        Bicycle b1 = f1.apply("fever");
        Bicycle b2 = f2.apply("phoenix", "vendor");

        myPrint.accept(b1);
        myPrint.accept(b2);
    }
}
