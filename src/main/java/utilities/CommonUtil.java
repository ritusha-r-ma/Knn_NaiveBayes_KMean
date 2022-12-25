package utilities;

import kSolver.knn.inputData.KnnData;
import kSolver.commonClass.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * This class is keeping commonly used methods available for all classes.
 */
public class CommonUtil {

    public static float getDistance(boolean isEucledian, List<Float> coord1, List<Float> coord2){
        return isEucledian ? getEuclideanSqDist(coord1, coord2) : getManhDist(coord1, coord2);
    }

    public static float getEuclideanSqDist(List<Float> coord1, List<Float> coord2) {
        float distance = 0;
        for (int i=0; i<coord1.size(); i++) {
            distance += (Math.pow(coord1.get(i) - coord2.get(i), 2));
        }
        return distance;
    }

    public static float getManhDist(List<Float> coord1, List<Float> coord2) {
        float distance = 0;
        for (int i=0; i<coord1.size(); i++) {
            distance += (Math.abs(coord1.get(i) - coord2.get(i)));
        }
        return distance;
    }

    public static void printKnnResult(List<Point> prediction, KnnData data) {
        for (Point p : data.getTestCoordPoints()) {
            Point originalData = prediction.stream().filter(p::equals).collect(Collectors.toList()).get(0);
            System.out.println("want=" + p.getLabel() + " " + "got=" + originalData.getLabel());
        }
    }

    public static void printKmeansResult(List<Point> trainData , List<Point> prediction, List<Point> centroids) {
        Map<String, List<String>> ans = new TreeMap<>();
        for(int i = 1; i<=centroids.size(); i++){
            ans.put("C" + i, new ArrayList<>());
        }
        for(int i=0; i< trainData.size(); i++){
            String c = trainData.get(i).getLabel();
            List<String> l = ans.get(prediction.get(i).getLabel());
            l.add(c);
            ans.put(prediction.get(i).getLabel(), l);
        }
        for(String key : ans.keySet()){
            System.out.print(key + " = ");
            System.out.println(ans.get(key).toString());
        }
        for(Point c : centroids){
            System.out.print(c.getLabel() + " = ");
            System.out.println(c.getCoordPoints().toString());
        }
    }
}
