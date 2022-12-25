package kSolver.KMean.solver;

import kSolver.KMean.inputData.KMeanData;
import kSolver.commonClass.PredictiveData;
import kSolver.commonClass.CustomComparator;
import kSolver.commonClass.Point;
import utilities.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class KMeanSolver {
    List<Point> centroids;
    List<Point> trainKMeanPoints;
    List<Point> prediction;
    boolean isEuclideanInput;

    public KMeanSolver(KMeanData data, boolean isEuclidean) {
        centroids = data.getCentroids();
        trainKMeanPoints = data.getTrainCoordPoints();
        isEuclideanInput = isEuclidean;
        prediction = new ArrayList<>();
    }

    public void solver() {
        List<Point> currentPrediction = new ArrayList<>();
        int i = 0;

        while (!isPredictionSame(prediction, currentPrediction)) {

            prediction = new ArrayList<>(currentPrediction);
            currentPrediction = new ArrayList<>();

            for (Point p : trainKMeanPoints) {
                List<PredictiveData> predData = new ArrayList<>();
                for (Point c : centroids) {
                    float dist = CommonUtil.getDistance(isEuclideanInput, c.getCoordPoints(), p.getCoordPoints());
                    predData.add(new PredictiveData(dist, c.getLabel()));
                }
                currentPrediction.add(new Point(p.getCoordPoints(), predictLabel(predData)));
            }

            generateNewCentroids(currentPrediction);
            System.out.println("----------------------  Centroid Computing : " + i + " ---------------");
            i++;
            CommonUtil.printKmeansResult(trainKMeanPoints, currentPrediction, centroids);

        }
    }

    private void generateNewCentroids(List<Point> newPrediction){
        for( Point c: centroids) {
            int n = c.getCoordPoints().size();
            int count = 0;
            List<Float> sum = new ArrayList<>();

            for(int i=0; i<n; i++){
                sum.add(0f);
            }

            for (Point p : newPrediction){
                if(p.getLabel().equals(c.getLabel())){
                    count = count + 1;
                    for (int i=0; i<n; i++) {
                        sum.set(i, sum.get(i) + p.getCoordPoints().get(i));
                    }
                }
            }

            if (count > 0) {
                for(int i=0; i<n; i++) {
                    sum.set(i, sum.get(i)/count);
                }

                c.setCoordPoints(sum);
            }
        }
    }

    private boolean isPredictionSame(List<Point> previousResult, List<Point> currentResult){
        int n = previousResult.size();
        if (n == 0) return false;
        for (int i=0; i<n; i++) {
            if (!previousResult.get(i).getLabel().equals(currentResult.get(i).getLabel())) {
                return false;
            }
        }
        return true;
    }

    private String predictLabel(List<PredictiveData> predData) {
        predData.sort(new CustomComparator());
        return predData.get(0).getLabel();
    }

    public List<Point> getPrediction() {
        return prediction;
    }

    public List<Point> getCentroids() {
        return centroids;
    }

    public List<Point> getTrainKMeanPoints() {
        return trainKMeanPoints;
    }
}

