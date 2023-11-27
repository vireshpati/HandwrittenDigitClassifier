import java.util.ArrayList;
import java.util.List;

public class Runner {


    public static void main(String[] args) {


        String set = "train";
        int threshold = 128;
        int k = 11;
        int dist = 0;
        int iter = 100;
        List<Integer> display = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {

            switch (args[i]) {
                case "-images", "-labels" -> set = args[++i];

                case "-threshold" -> {
                    try {
                        threshold = Integer.parseInt(args[++i]);
                    } catch (NumberFormatException ignored) {
                    }

                }
                case "-k" -> {
                    try {
                        k = Integer.parseInt(args[++i]);
                    } catch (NumberFormatException ignored) {
                    }
                }
                case "-distance" -> {
                    try {
                        dist = Integer.parseInt(args[++i]);
                    } catch (NumberFormatException ignored) {
                    }
                }
                case "-iter" -> {
                    try {
                        iter = Integer.parseInt(args[++i]);
                    } catch (NumberFormatException ignored) {
                    }
                }

                default -> display.add(Integer.parseInt(args[i]));

            }
        }

        List<Digit> trainingData = FileIntake.loadData(set, threshold);
        List<Digit> testingData = FileIntake.loadData("t10k", threshold);

        double correct = 0, total = 0;

        for (int i = 0; i < display.size(); i++) {
            System.out.println(testingData.get(display.get(i)).toString());
        }

        for (int i = 0; i < iter; i++) {

            Digit testDigit = testingData.get(i);
            int prediction = Classifier.knn(trainingData, testDigit, k, dist);

            System.out.println("Prediction: " + prediction);
            System.out.println(testDigit.toString());

            if(prediction == testDigit.label()) correct++;
            total++;

        }

        total--;
        System.out.println("Accuracy: " + correct/total*100 +"%");


    }
}
