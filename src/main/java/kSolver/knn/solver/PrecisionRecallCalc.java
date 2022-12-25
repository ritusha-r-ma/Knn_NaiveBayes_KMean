package kSolver.knn.solver;

import kSolver.commonClass.Point;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class calculates Precision and Recall values for KNN data
 */
public class PrecisionRecallCalc {

    public void printKnnAttributes(List<Point> prediction, List<Point> testData) {
        Map<String, Integer> precisionTotal = new HashMap<>();
        Map<String, Integer> recallTotal = new HashMap<>();

        for (Point p : prediction) {
            precisionTotal.put(p.getLabel(), precisionTotal.getOrDefault(p.getLabel(), 0) + 1);
        }

        for (Point p : testData) {
            recallTotal.put(p.getLabel(), recallTotal.getOrDefault(p.getLabel(), 0) + 1);
        }

        for (Map.Entry<String, Integer> value : precisionTotal.entrySet()) {
            int precCount = calculateMeasures(prediction, testData, value.getKey());
            int recallCount = calculateMeasures(testData, prediction, value.getKey());

            System.out.println("Label=" + value.getKey() + " Precision=" + precCount + "/" + value.getValue() + " Recall=" + recallCount + "/" + (recallTotal.containsKey(value.getKey()) ? recallTotal.get(value.getKey()) : "0"));
        }
    }

    private int calculateMeasures(List<Point> data1, List<Point> data2, String label) {
        int count = 0;
        for (Point p : data1) {
            if (p.getLabel().equals(label)) {
                Point predPoint = data2.stream().filter(p::equals).collect(Collectors.toList()).get(0);
                if (predPoint.getLabel().equals(p.getLabel())) {
                    count++;
                }
            }
        }
        return count;
    }
}
