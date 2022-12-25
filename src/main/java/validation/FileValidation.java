package validation;

import kSolver.KMean.inputData.KMeanData;
import kSolver.knn.inputData.KnnData;
import kSolver.commonClass.Point;
import naivebayes.input.NbData;
import utilities.CustomPair;
import utilities.ErrorHandlingUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is responsible for reading inputs and assigning input into proper format.
 */
public class FileValidation {

    public KnnData knnData;
    public NbData nbData;
    public KMeanData kMeanData;
    int totalTrainCount;

    public FileValidation(boolean isNaiveBayesInput, boolean isKnnInput, boolean isKmeansInput, List<Point> centroids) {
        if (isKnnInput) {
            knnData = new KnnData();
        }

        if (isNaiveBayesInput) {
            totalTrainCount = 0;
            nbData = new NbData();
        }

        if (isKmeansInput) {
            kMeanData = new KMeanData(centroids);
        }
    }

    public void readInputFile(String ioPath, boolean isTrainData) {
        int lineNumber = 1;
        try {
            String line;
            BufferedReader br = new BufferedReader(new FileReader(ioPath));
            while ((line = br.readLine()) != null) {
                massageInputFile(line, lineNumber, isTrainData);
                lineNumber++;
            }
        } catch (Exception e) {
            ErrorHandlingUtil.errorOccurred("Error on " + lineNumber + " : " + e.getMessage());
        }
    }

    public void readCSVData(String file, boolean isTrainData) {
        int lineNumber = 1;
        try {

            FileReader filereader = new FileReader(file);
            try (BufferedReader br = new BufferedReader(filereader)) {
                List<List<CustomPair>> predAttr = nbData.getTrainPredAttr();
                Map<String, Integer> classAttr = nbData.getTrainClassAttr();
                List<List<String>> testData = nbData.getTestData();
                String line;

                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    if (isTrainData) {
                        massageCSVData(values, predAttr, classAttr);
                        ++totalTrainCount;
                    } else {
                        testData.add(Arrays.stream(values).collect(Collectors.toList()));
                    }
                    lineNumber++;
                }
            }
        } catch (Exception e) {
            ErrorHandlingUtil.errorOccurred("Error on " + lineNumber + " : " + e.getMessage());
        }
    }

    public void readKmeansInputFile(String ioPath, List<Point> centroid) {
        int lineNumber = 1;
        try {
            String line;
            BufferedReader br = new BufferedReader(new FileReader(ioPath));
            while ((line = br.readLine()) != null) {
                massageKMeanInputFile(line, lineNumber);
                lineNumber++;
            }
        } catch (Exception e) {
            ErrorHandlingUtil.errorOccurred("Error on " + lineNumber + " : " + e.getMessage());
        }
    }

    /**
     * Refine inputs to store in proper format
     * @param line
     * @param lineNumber
     * @param isTrainData
     */
    private void massageInputFile(String line, int lineNumber, boolean isTrainData) {
        if (line.contains("#") || line.isEmpty()) {
            return;
        }

        if (line.contains(",")) {
            String[] text = line.split(",");
            int size = text.length;
            List<Float> coordList = new ArrayList<>();

            for (int i = 0; i<size - 1; i++) {
                if (text[0].matches("[A-Z a-z]")) {
                    ErrorHandlingUtil.errorOccurred(lineNumber + "of the text file contains single parameter only");
                }

                coordList.add(Float.parseFloat(text[i].trim()));
            }

            Point data = new Point(coordList, text[size - 1].trim());

            if (isTrainData) {
                knnData.addTrainData(data);
            } else {
                knnData.addTestData(data);
            }
        } else {
            ErrorHandlingUtil.errorOccurred(lineNumber + "of the text file contains bad parameter");
        }
    }

    /**
     * Refine CSV inputs to store in proper objects.
     * @param records
     * @param predAttr
     * @param classAttr
     */
    private void massageCSVData(String[] records, List<List<CustomPair>> predAttr, Map<String, Integer> classAttr) {
        int len = records.length;

        for (int i=0 ; i<len - 1; i++){
            predAttr.add(new ArrayList<>());
        }

        for (int i = 0; i<len; i++) {
            if (records[i].isEmpty()) {
                continue;
            }

            if (i == len - 1) {
                classAttr.put(records[i].trim(), classAttr.getOrDefault(records[i].trim(), 0) + 1);
            } else {
                CustomPair pair = new CustomPair(records[i].trim(), records[len - 1].trim());
                predAttr.get(i).add(pair);
            }
        }
    }

    private void massageKMeanInputFile(String line, int lineNumber) {
        if (line.contains("#") || line.isEmpty()) {
            return;
        }
        if (line.contains(",")) {
            String[] text = line.split(",");
            int size = text.length;
            List<Float> coordList = new ArrayList<>();

            for (int i = 0; i < size - 1; i++) {
                if (text[0].matches("[A-Z a-z]")) {
                    ErrorHandlingUtil.errorOccurred("Line Number: " + lineNumber + " of the text file contains single parameter only");
                }
                coordList.add(Float.parseFloat(text[i].trim()));
            }
            if(coordList.size() != kMeanData.getCentroids().get(0).getCoordPoints().size()){
                ErrorHandlingUtil.errorOccurred("Line Number: " + lineNumber + " of the text file contains more dimensions than given centroids");
            }

            Point data = new Point(coordList, text[size - 1].trim());
            kMeanData.addTrainData(data);
        }else{
            ErrorHandlingUtil.errorOccurred("Line Number: " + lineNumber + " of the text file contains bad literal ");
        }
    }

    public KnnData getKnnData() {
        return knnData;
    }

    public NbData getNbData() {
        return nbData;
    }

    public int getTotalTrainCount() {
        return totalTrainCount;
    }
}
