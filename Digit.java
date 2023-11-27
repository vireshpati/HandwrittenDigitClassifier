
public record Digit(double[] data, int label, int number) {

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("Image #" + number + ": " + label);
        int i = 0;
        for (int j = 0; j < 28; j++) {
            string.append("\n");
            for (int k = 0; k < 28; k++) string.append(data[i++] > 0.5 ? '*' : ' ');
        }

        return string.toString();
    }


}
