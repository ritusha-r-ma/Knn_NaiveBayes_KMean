package naivebayes.solver;

import utilities.CustomPair;
import naivebayes.input.PredictiveData;
import naivebayes.input.NbData;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is responsible for solving Naive Bayes with laplacian correction (if given)
 */
public class NbSolver {
    List<PredictiveData> predictiveData;
    Map<String, Float> classifierProb;

    public NbSolver() {
        predictiveData = new ArrayList<>();
    }

    public void solver(NbData data, int totalTrainCount, boolean isLaplacianGiven, int laplacianValue) {
        classifierProb = calcClassifierProb(data.getTrainClassAttr(), totalTrainCount);

        List<List<String>> testData = data.getTestData();
        Map<String, Integer> classifierLabel = data.getTrainClassAttr();

        for (List<String> testRow : testData) {
            float maxProbability = 0;
            String predictionLabel = "";

            for (Map.Entry<String, Integer> classifierRow : classifierLabel.entrySet()) {
                float probability = classifierProb.get(classifierRow.getKey());

                for (int i = 0; i < testRow.size() - 1; i++) {
                    float calculatedProb = calcCondProb(testRow.get(i), classifierRow.getKey(), data.getTrainPredAttr().get(i), isLaplacianGiven, laplacianValue, getNumberOfUniqueValue(testData));
                    probability = probability * calculatedProb;
                }

                if (maxProbability < probability) {
                    maxProbability = probability;
                    predictionLabel = classifierRow.getKey();
                }
            }

            predictiveData.add(new PredictiveData(testRow, predictionLabel));
        }
    }

    /**
     * Return conditional probability i.e. probability of happening of data1 when data2 is already happened.
     * It also undertakes the laplacian value if provided
     * @param data1
     * @param data2
     * @param predData
     * @param isLaplacian
     * @param laplacianValue
     * @param domainOfA
     * @return
     */
    private float calcCondProb(String data1, String data2, List<CustomPair> predData, boolean isLaplacian, int laplacianValue, int domainOfA) {
        int numerator = 0;
        int denominator = 0;

        for (CustomPair value1 : predData) {
            if (value1.getKey().equals(data1) && value1.getValue().equals(data2)) {
                numerator++;
            }

            if (value1.getValue().equals(data2)) {
                denominator++;
            }
        }

        if (isLaplacian) {
            numerator = numerator + laplacianValue;
            denominator = denominator + domainOfA*laplacianValue;
        }

        return (float) numerator/denominator;
    }

    /**
     * This method returns the Domain of Ai, used in case of laplacian
     * @param testData
     * @return
     */
    private int getNumberOfUniqueValue(List<List<String>> testData) {
        Set<String> set = new HashSet<>();
        for (List<String> data1 : testData) {
            set.addAll(data1);
        }

        return set.size();
    }

    /**
     * This method returns the probability of classification attribute in Naive bayes
     * @param classifierSet
     * @param totalLineCount
     * @return
     */
    private Map<String, Float> calcClassifierProb(Map<String, Integer> classifierSet, int totalLineCount) {
        Map<String, Float> classProbMap = new HashMap<>();

        for (Map.Entry<String, Integer> value : classifierSet.entrySet()) {
            classProbMap.put(value.getKey(), (float) value.getValue()/totalLineCount);
        }

        return classProbMap;
    }

    /**
     * It calculates the naiveBayes measures i.e. precision and recall
     * @param testData
     */
    public void calcNaiveBayesMeasures(List<List<String>> testData) {
        Map<String, Integer> precisionTotal = new HashMap<>();
        Map<String, Integer> recallTotal = new HashMap<>();

        for (PredictiveData p : predictiveData) {
            precisionTotal.put(p.getPredictiveLabel(), precisionTotal.getOrDefault(p.getPredictiveLabel(), 0) + 1);
        }

        for (List<String> testRow : testData) {
            String label = testRow.get(testRow.size() - 1);
            recallTotal.put(label, recallTotal.getOrDefault(label, 0) + 1);
        }

        for (Map.Entry<String, Float> value : classifierProb.entrySet()) {
            int corrLabelledCount = calcPrecisionOfClassifier(testData, predictiveData, value.getKey());

            int precDenominator = precisionTotal.getOrDefault(value.getKey(), 0);
            int recallDenominator = recallTotal.getOrDefault(value.getKey(), 0);

            System.out.println("Label=" + value.getKey() + " Precision=" + corrLabelledCount + "/" + precDenominator + " Recall=" + corrLabelledCount + "/" + recallDenominator);
        }
    }

    /**
     * This method return the count, where classifier correctly classified the data.
     * @param testData
     * @param predictiveData
     * @param classifierLabel
     * @return
     */
    private int calcPrecisionOfClassifier(List<List<String>> testData, List<PredictiveData> predictiveData, String classifierLabel) {
        int count = 0;

        for (List<String> data : testData) {
            if (data.get(data.size() - 1).equals(classifierLabel)) {
                PredictiveData predictiveRow = predictiveData.stream().filter(p -> p.equals(data)).collect(Collectors.toList()).get(0);
                if (predictiveRow.getPredictiveLabel().equals(classifierLabel)) {
                    count++;
                }
            }
        }

        return count;
    }
}
