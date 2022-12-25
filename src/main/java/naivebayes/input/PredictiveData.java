package naivebayes.input;

import java.util.List;
import java.util.Objects;

/**
 * This class is responsible to predictive label and points which can be used later to calculate precision & recall
 */
public class PredictiveData {

    List<String> attributes;
    String predictiveLabel;

    public PredictiveData(List<String> rowAttr, String label) {
        attributes = rowAttr;
        predictiveLabel = label;
    }

    public String getPredictiveLabel() {
        return predictiveLabel;
    }

    public boolean equals(List<String> data) {
        if (data.size() != this.attributes.size()) {
            return false;
        }

        for (int i=0; i< data.size() - 1; i++) {
            if (!Objects.equals(this.attributes.get(i), data.get(i))) {
                return false;
            }
        }

        return true;
    }
}
