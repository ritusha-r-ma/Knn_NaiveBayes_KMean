package kSolver.knn.inputData;

import kSolver.commonClass.Point;

import java.util.ArrayList;
import java.util.List;

public class KnnData {

    List<Point> trainCoordPoints = new ArrayList<>();
    List<Point> testCoordPoints = new ArrayList<>();

    public void addTrainData(Point point) {
        trainCoordPoints.add(point);
    }

    public void addTestData(Point point) {
        testCoordPoints.add(point);
    }

    public List<Point> getTrainCoordPoints() {
        return trainCoordPoints;
    }

    public List<Point> getTestCoordPoints() {
        return testCoordPoints;
    }
}
