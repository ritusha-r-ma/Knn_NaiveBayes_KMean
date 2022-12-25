package validation;

import kSolver.commonClass.Point;
import utilities.ErrorHandlingUtil;

import java.util.ArrayList;
import java.util.List;

public class ArgValidation {
    int k;
    int c;
    String trainFilePath;
    String testFilePath;
    boolean isKnnInput;
    boolean isNaiveBayesInput;
    boolean isLaplacianCorrGiven;
    boolean isKMeanInput;
    boolean isKmeansInput;
    boolean isEucledian;
    List<Point> centroids;

    public ArgValidation() {
        k = 0;
        trainFilePath = "";
        testFilePath = "";
        isKnnInput = false;
        isNaiveBayesInput = true;
        isKMeanInput = false;
        isKmeansInput = false;
        centroids = new ArrayList<>();
        isEucledian = true;
    }

    public void readArguments(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-train":
                    trainFilePath = args[++i];
                    break;
                case "-test":
                    testFilePath = args[++i];
                    break;
                case "-K":
                    k = Integer.parseInt(args[++i]);
                    if (k > 0) {
                        isKnnInput = true;
                        isLaplacianCorrGiven = false;
                        isNaiveBayesInput = false;
                    } else {
                        isNaiveBayesInput = true;
                    }
                    break;
                case "-C":
                    c = Integer.parseInt(args[++i]);
                    isLaplacianCorrGiven = true;
                    isNaiveBayesInput = true;
                    isKnnInput = false;
                    break;
                case "-d":
                    String type = args[++i];
                    if (type.equals("manh")) {
                        isEucledian = false;
                    }
                    break;
                case "-centroids":
                    int centroid = 0;
                    for (++i; i < args.length; i++) {
                        List<Float> p = new ArrayList<>();
                        String pointString = args[i].replace(" ", "");
                        String[] z = pointString.split(",");
                        for (String x : z) {
                            p.add(Float.parseFloat(x));
                        }
                        centroids.add(new Point(p, "C" + ++centroid));
                    }

                    for (int k = 1; k < centroids.size(); k++) {
                        if (centroids.get(k).getCoordPoints().size() != centroids.get(k - 1).getCoordPoints().size()) {
                            ErrorHandlingUtil.errorOccurred("Invalid Centroids: Coordinates must be of equal dimension");
                        }
                    }

                    isLaplacianCorrGiven = false;
                    isNaiveBayesInput = false;
                    isKnnInput = false;
                    isKmeansInput = true;
                    break;
            }
        }
    }

    public int getK() {
        return k;
    }

    public boolean isKnnInput() {
        return isKnnInput;
    }

    public String getTrainFilePath() {
        return trainFilePath;
    }

    public String getTestFilePath() {
        return testFilePath;
    }

    public int getC() {
        return c;
    }

    public boolean isNaiveBayesInput() {
        return isNaiveBayesInput;
    }

    public boolean isLaplacianCorrGiven() {
        return isLaplacianCorrGiven;
    }

    public boolean isKMeanInput() {
        return isKmeansInput;
    }

    public boolean isEucledian() {
        return isEucledian;
    }

    public List<Point> getCentroids() {
        return centroids;
    }
}
