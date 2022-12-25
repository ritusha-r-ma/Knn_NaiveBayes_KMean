package kSolver.commonClass;

import java.util.List;
import java.util.Objects;

/**
 * This class represent Point structure to store coordinates points in case of KNN and KMean
 */
public class Point {

    List<Float> coordPoints;
    String label;

    public Point(List<Float> points, String labelId) {
       coordPoints = points;
        label = labelId;
    }

    public List<Float> getCoordPoints() {
        return coordPoints;
    }

    public String getLabel() {
        return label;
    }


    public void setCoordPoints(List<Float> coordPoints) {
        this.coordPoints = coordPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Point)) {
            return false;
        }

        if (this.coordPoints.size() != ((Point) o).getCoordPoints().size()) {
            return false;
        }

        for (int i=0; i<this.coordPoints.size(); i++)
            if (!Objects.equals(this.coordPoints.get(i), ((Point) o).getCoordPoints().get(i))) {
                return false;
            }

        return true;
    }
}
