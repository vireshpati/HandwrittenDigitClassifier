import java.util.ArrayList;
import java.util.List;

public class Classifier {

    public static int knn(List<Digit> training, Digit candidate, int k, int distType) {


        List<Double> neighborDistances = new ArrayList<>();
        List<Digit> neighbors = new ArrayList<>();

        for (Digit digit : training) {
            double similarity = distance(candidate.data(), digit.data(), distType);

            // find first k neighbors
            if (neighborDistances.size() < k) {
                neighborDistances.add(similarity);
                neighbors.add(digit);
            } else

                // If a neighbor is an improvement , add it to the set of k
                if (similarity < max(neighborDistances)) {
                    int index = argmax(neighborDistances);
                    neighborDistances.set(index, similarity);
                    neighbors.set(index, digit);
                }

        }

        int[] digits = new int[10];

        for (Digit neighbor : neighbors) digits[neighbor.label()]++;


        return argmax(digits);

    }

    private static double distance(double[] a, double[] b, int type) {
        return switch (type) {
            case 0 -> hammingDistance(a, b);
            case 1 -> manhattanDistance(a, b);
            case 2 -> euclideanDistance(a, b);
            case 3 -> cosineSimilarity(a, b);

            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    private static double hammingDistance(double[] a, double[] b) {
        double distance = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) distance++;
        }
        return distance;


    }

    private static double manhattanDistance(double[] a, double[] b) {
        double distance = 0;
        for (int i = 0; i < a.length; i++) distance += Math.abs(b[i] - a[i]);
        return distance;
    }

    private static double euclideanDistance(double[] a, double[] b) {
        double distance = 0;
        for (int i = 0; i < a.length; i++) distance += Math.pow(b[i] - a[i], 2);
        return distance;
    }

    private static double cosineSimilarity(double[] a, double[] b) {

        double normA = 0;
        double normB = 0;
        double dotProduct = 0;

        for (int i = 0; i < a.length; i++) {
            normA += a[i] * a[i];
            normB += b[i] * b[i];
            dotProduct += a[i] * b[i];

        }
        return 1 - (dotProduct / (normA * normB));

    }

    private static int argmax(List<Double> a) {

        double max = 0;
        int index = 0;
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i) > max) {
                max = a.get(i);
                index = i;
            }
        }

        return index;
    }

    private static int argmax(int[] a) {

        double max = 0;
        int index = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > max) {
                max = a[i];
                index = i;
            }
        }

        return index;
    }

    private static double max(List<Double> a) {
        double max = 0;
        for (Double v : a) {
            if (v > max) max = v;
        }
        return max;
    }


}
