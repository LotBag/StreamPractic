package practic.aggregationAndPoolingOfResilts;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class Student {
    private String name;
    private Map<String, Integer> grades;

    public Student(String name, Map<String, Integer> grades) {
        this.name = name;
        this.grades = grades;
    }

    public Map<String, Integer> getGrades() {
        return grades;
    }

    public static ConcurrentMap<String, Double> getAverageGrades(List<Student> students) {
        Map<String, List<Integer>> subjectGrades = students.parallelStream()
                .flatMap(student -> student.getGrades().entrySet().stream())
                .collect(Collectors.groupingByConcurrent(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));

        return subjectGrades.entrySet().stream()
                .collect(Collectors.toConcurrentMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().mapToInt(Integer::intValue).average().orElse(0.0)
                ));
    }
}