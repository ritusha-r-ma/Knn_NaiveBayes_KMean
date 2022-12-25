package kSolver.knn.solver;

import kSolver.commonClass.CustomComparator;
import kSolver.knn.inputData.KnnData;
import kSolver.commonClass.Point;
import kSolver.commonClass.PredictiveData;
import utilities.CommonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KnnSolver {
    List<Point> testPoints;
    List<Point> trainPoints;
    List<Point> prediction;

    public KnnSolver(KnnData data) {
        testPoints = data.getTestCoordPoints();
        trainPoints = data.getTrainCoordPoints();
        prediction = new ArrayList<>();
    }

    public void solver(int k) {
        for (Point p : testPoints) {
            List<PredictiveData> predData = new ArrayList<>();

            for (Point p1 : trainPoints) {
                float dist = CommonUtil.getManhDist(p.getCoordPoints(), p1.getCoordPoints());
                predData.add(new PredictiveData(dist, p1.getLabel()));
            }

            prediction.add(new Point(p.getCoordPoints(), predictLabel(predData, k)));
        }
    }

    private String predictLabel(List<PredictiveData> predData, int k) {
        predData.sort(new CustomComparator());

        Map<String, Float> votes = new HashMap<>();
        for (int i = 0; i < k; i++) {
            float vote = votes.getOrDefault(predData.get(i).getLabel(), 0.0f);
            votes.put(predData.get(i).getLabel(), vote + 1/predData.get(i).getDistance());
        }

        String maxVoteLabel = "";
        float max = 0;
        for (Map.Entry<String, Float> vote : votes.entrySet()) {
            if (max < vote.getValue()) {
                maxVoteLabel = vote.getKey();
                max = vote.getValue();
            }
        }

        return maxVoteLabel;
    }

    public List<Point> getPrediction() {
        return prediction;
    }
}
