import javax.swing.*;
import java.util.ArrayList;

class CheckButton extends JButton {

    private int matrixSize = 26;
    private JTextField textField;

    CheckButton(Net net, JTextField text){
        super("Rozpoznaj");
        this.textField = text;

        addActionListener(e -> {
            textField.setText("");

            int[] a = new int[matrixSize - 1];
            System.arraycopy(net.getAnswerArray(), 1, a,0, matrixSize - 1);

            ArrayList<double[]> fw = net.getFinalWeightsArray();
            double[][] dl = net.getDftList();
            ArrayList<Double> wZero = net.getFinalWeightsZero();

            textField.setText(check(a, fw, wZero, dl));

        });
    }

    void clearText(){
        textField.setText("");
    }

    private String check( int[] answer, ArrayList<double[]> finalWeights, ArrayList<Double> wZero, double[][] dftlist){
        double max = Integer.MIN_VALUE;
        double percep;
        int a = -1;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            percep = o( finalWeights.get(i), wZero.get(i), DFT(answer));
            System.out.println(i + ": " + percep);
            if ( percep > max && percep > 0){
                max = percep;
                a = i;
            }
        }
        sb.append(a);

        return sb.toString();
    }

    private double o( double[] weights, double wZero, double[] example ){
        double sum = 0.0;
        int k = 0;
        sum += wZero * 1;
        for (int j = matrixSize - 1; j < weights.length; j++){
            sum += weights[j] * example[k];
            k++;
        }

        return sum;
    }

    private double[] DFT(int[] array){
        int n = matrixSize - 1;
        double[] real = new double[n];
        double[] im = new double[n];
        double[] result = new double[n];
        double alpha = ( 2 * Math.PI )/ n;

        for (int k = 0; k < n; k++){
            double a = k * alpha;
            for (int t = 0; t < n; t++) {
                real[k] += array[t] * Math.cos(a * t);
                im[k] += array[t] * Math.sin(a * t);
            }
            result[k] = Math.sqrt( real[k] * real[k] + im[k] * im[k]) / n;
        }
        return result;
    }
}
