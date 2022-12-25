package naivebayes.input;

import utilities.CustomPair;

import java.util.*;

public class NbData {

    List<List<CustomPair>> trainPredAttr;
    Map<String, Integer> trainClassAttr;
    List<List<String>> testData;

    public NbData() {
        trainPredAttr = new ArrayList<>();
        trainClassAttr = new HashMap<>();
        testData = new ArrayList<>();
    }

    public List<List<CustomPair>> getTrainPredAttr() {
        return trainPredAttr;
    }

    public Map<String, Integer> getTrainClassAttr() {
        return trainClassAttr;
    }

    public List<List<String>> getTestData() {
        return testData;
    }
}
