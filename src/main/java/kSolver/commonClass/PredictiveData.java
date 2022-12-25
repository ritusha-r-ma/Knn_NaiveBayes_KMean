package kSolver.commonClass;

public class PredictiveData {
    float distance;
    String label;

    public PredictiveData(float dist, String l) {
        distance = dist;
        label = l;
    }

    public float getDistance() {
        return distance;
    }

    public String getLabel() {
        return label;
    }
}
