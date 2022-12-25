package main;

import kSolver.KMean.solver.KMeanSolver;
import kSolver.knn.solver.KnnSolver;
import kSolver.knn.solver.PrecisionRecallCalc;
import naivebayes.solver.NbSolver;
import utilities.CommonUtil;
import validation.ArgValidation;
import validation.FileValidation;

public class Start {

    public static void main(String[] args) {
        ArgValidation arg = new ArgValidation();
        arg.readArguments(args);

        FileValidation fileValidation = new FileValidation(arg.isNaiveBayesInput(), arg.isKnnInput(), arg.isKMeanInput(), arg.getCentroids());

        if (arg.isKnnInput()) {
            fileValidation.readInputFile(arg.getTrainFilePath(), true);
            fileValidation.readInputFile(arg.getTestFilePath(), false);

            KnnSolver knnSolver = new KnnSolver(fileValidation.getKnnData());
            knnSolver.solver(arg.getK());
            CommonUtil.printKnnResult(knnSolver.getPrediction(), fileValidation.getKnnData());

            PrecisionRecallCalc precRecallCalc = new PrecisionRecallCalc();
            precRecallCalc.printKnnAttributes(knnSolver.getPrediction(), fileValidation.getKnnData().getTestCoordPoints());
        }

        if (arg.isNaiveBayesInput()) {
            fileValidation.readCSVData(arg.getTrainFilePath(), true);
            fileValidation.readCSVData(arg.getTestFilePath(), false);

            NbSolver nbSolver = new NbSolver();
            nbSolver.solver(fileValidation.getNbData(), fileValidation.getTotalTrainCount(), arg.isLaplacianCorrGiven(), arg.getC());
            nbSolver.calcNaiveBayesMeasures(fileValidation.nbData.getTestData());
        }

        if (arg.isKMeanInput()){
            fileValidation.readKmeansInputFile(arg.getTrainFilePath(), arg.getCentroids());
            KMeanSolver kmeansSolver = new KMeanSolver(fileValidation.kMeanData, arg.isEucledian());
            kmeansSolver.solver();
            CommonUtil.printKmeansResult(kmeansSolver.getTrainKMeanPoints(), kmeansSolver.getPrediction(), kmeansSolver.getCentroids());
        }
    }
}
