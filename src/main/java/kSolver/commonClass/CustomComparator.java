package kSolver.commonClass;

import java.util.Comparator;

/**
 * A Custom Comparator to sort List of PredictiveDataTypes
 */
public class CustomComparator implements Comparator<PredictiveData> {

    @Override
    public int compare(PredictiveData o1, PredictiveData o2) {
        return (int) (o1.getDistance() - o2.getDistance());
    }
}
