package kSolver.KMean.inputData;

import kSolver.commonClass.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for storing KMean Input
 */
public class KMeanData {

    List<Point> trainCoordKMeanPoints;
    List<Point> centroids;

    public KMeanData(List<Point> centroid) {
        centroids = centroid;
        trainCoordKMeanPoints = new ArrayList<>();
    }

    public void addTrainData(Point kmeansPoint) {
        trainCoordKMeanPoints.add(kmeansPoint);
    }

    public List<Point> getTrainCoordPoints() {
        return trainCoordKMeanPoints;
    }

    public List<Point> getCentroids() {
        return centroids;
    }
}

