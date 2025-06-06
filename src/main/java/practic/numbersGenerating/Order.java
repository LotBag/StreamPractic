package practic.numbersGenerating;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

public class Order {
    private String product;
    private double cost;

    public Order(String product, double cost) {
        this.product = product;
        this.cost = cost;
    }

    public String getProduct() {
        return product;
    }

    public double getCost() {
        return cost;
    }

    public static Map.Entry<String, Double> getThreeMoreExpensive(List<Order> orders) {

        Map<String, List<Order>> ordersGroupedByProduct = orders.stream()
                .collect(Collectors.groupingBy(Order::getProduct));

        ordersGroupedByProduct.forEach((k, v) -> System.out.println(v));

        Map<String, Double> totalOrderCost = orders.stream()
                .collect(Collectors.groupingBy(Order::getProduct, Collectors.summingDouble(Order::getCost)));

        Map<String, Double> totalOrderCostSorted = totalOrderCost.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));

        totalOrderCostSorted.forEach((k, v) -> System.out.println(k + ": " + v));

        return totalOrderCostSorted.entrySet().stream()
                .limit(3)
                .reduce((e1, e2) -> new AbstractMap.SimpleEntry<>(
                        e1.getKey() + ", " + e2.getKey(),
                        e1.getValue() + e2.getValue()
                )).get();

    }

}

