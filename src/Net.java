import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Net extends JPanel {
    private int columnCount = 5;
    private int rowCount = 5;
    private List<Rectangle> cells;
    private List<Point> selectedCells;

    private Random random;
    private int matrixSize = 26;
    private int perc = 10;
    private int iterN = 100000;
    private int exampleAmount = 10;

    private ArrayList<double[]> finalWeightsArray;
    private ArrayList<Double> finalWeightsZero;
    private int[] answerArray;
    private double[][] dftList;

    private ArrayList<Double> zeroError;
    private ArrayList<Double> oneError;
    private ArrayList<Double> twoError;
    private ArrayList<Double> threeError;
    private ArrayList<Double> fourError;
    private ArrayList<Double> fiveError;
    private ArrayList<Double> sixError;
    private ArrayList<Double> sevenError;
    private ArrayList<Double> eightError;
    private ArrayList<Double> nineError;

    Net(){
        cells = new ArrayList<>(columnCount * rowCount);
        selectedCells = new ArrayList<>(columnCount * rowCount);
        setPreferredSize(new Dimension(302 , 302));

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                color(e);
            }
        };
        addMouseListener(mouseAdapter);

        random = new Random();
        zeroError = new ArrayList<>();
        oneError = new ArrayList<>();
        twoError = new ArrayList<>();
        threeError = new ArrayList<>();
        fourError = new ArrayList<>();
        fiveError = new ArrayList<>();
        sixError = new ArrayList<>();
        sevenError = new ArrayList<>();
        eightError = new ArrayList<>();
        nineError = new ArrayList<>();
        answerArray = new int[matrixSize];

        initArray();

        dftList = new double[exampleAmount][matrixSize - 1];
        finalWeightsZero = new ArrayList<>();
        finalWeightsArray = startLearn();
    }

    private void color(MouseEvent e){
        int width = 300;
        int height = 300;
        int cellWidth = width / columnCount;
        int cellHeight = height / rowCount;

        int xOffset = (width - (columnCount * cellWidth)) / 2;
        int yOffset = (height - (rowCount * cellHeight)) / 2;

        if (e.getX() >= xOffset && e.getY() >= yOffset) {
            int column = (e.getX() - xOffset) / cellWidth;
            int row = (e.getY() - yOffset) / cellHeight;

            if (column >= 0 && row >= 0 && column < columnCount && row < rowCount) {
                Point point = new Point(column, row);
                int index = point.x + (point.y * columnCount);
                if (selectedCells.contains(point)) {
                    selectedCells.remove(point);

                    answerArray[index + 1] = -1;

                } else {
                    selectedCells.add(point);

                    answerArray[index + 1] = 1;
                }
            }
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        int width = 300;
        int height = 300;
        int cellWidth = width / columnCount;
        int cellHeight = height / rowCount;

        int xOffset = (width - (columnCount * cellWidth)) / 2;
        int yOffset = (height - (rowCount * cellHeight)) / 2;

        if (cells.isEmpty()){
            for (int row = 0 ; row < rowCount; row++){
                for(int col = 0 ; col < columnCount; col++){
                    Rectangle cell = new Rectangle(
                            xOffset + (col * cellWidth),
                            yOffset + (row * cellHeight),
                            cellWidth,
                            cellHeight);
                    cells.add(cell);
                }
            }
        }

        for (Rectangle cell : cells){
            g2d.setColor(Color.WHITE);
            g2d.fill(cell);
            g2d.setColor(Color.BLACK);
            g2d.draw(cell);
        }

        if (!selectedCells.isEmpty()){
            for (Point point : selectedCells){
                int index = point.x + (point.y * columnCount);
                Rectangle cell = cells.get(index);
                g2d.setColor(Color.BLACK);
                g2d.fill(cell);
            }
        }

        g2d.dispose();
    }

    void clearField(){
        selectedCells.clear();
        initArray();
        repaint();
    }

    //region Getters
    int[] getAnswerArray() {
        return answerArray;
    }

    ArrayList<double[]> getFinalWeightsArray() {
        return finalWeightsArray;
    }

    ArrayList<Double> getFinalWeightsZero() {
        return finalWeightsZero;
    }

    double[][] getDftList() {
        return dftList;
    }

    ArrayList<Double> getZeroError() {
        return zeroError;
    }

    ArrayList<Double> getOneError() {
        return oneError;
    }

    ArrayList<Double> getTwoError() {
        return twoError;
    }

    ArrayList<Double> getThreeError() {
        return threeError;
    }

    ArrayList<Double> getFourError() {
        return fourError;
    }

    ArrayList<Double> getFiveError() {
        return fiveError;
    }

    ArrayList<Double> getSixError() {
        return sixError;
    }

    ArrayList<Double> getSevenError() {
        return sevenError;
    }

    ArrayList<Double> getEightError() {
        return eightError;
    }

    ArrayList<Double> getNineError() {
        return nineError;
    }
    //endregion

    private void initArray(){
        for (int i = 0; i < matrixSize; i++) {
            answerArray[i] = -1;
        }
    }

    private int[][] readFile(){
        int[][] example = new int[exampleAmount][matrixSize];
        int lineNum = 0;
        int temp;
        try {
            BufferedReader in = new BufferedReader(new FileReader("examples.txt"));
            String line = in.readLine();
            while ( line != null ){
                char[] a = line.toCharArray();
                for ( int i = 0 ; i < line.length(); i++){
                    temp = Integer.valueOf(String.valueOf(a[i]));
                    if ( temp == 0 ){
                        example[lineNum][i] = -1;
                    } else {
                        example[lineNum][i] = temp;
                    }
//                    example[lineNum][i] = Integer.valueOf(String.valueOf(a[i]));
                }
                lineNum++;
                line = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        example[0][0] = 0;
        return example;
    }

    private double[] weightRandom() {
        int size = 2 * matrixSize - 2;
        double[] weights = new double[size];
        for (int j = 0; j < size; j++) {
            weights[j] = random.nextDouble() * 2 - 1;
        }
        return weights;
    }

    private double weightZero(){
        return random.nextDouble() * 2 - 1;
    }

    private double[] DFT(int[] array){
        int n = matrixSize - 1;
        double[] real = new double[n];
        double[] im = new double[n];
        double[] result = new double[n];
        double alpha = ( 2 * Math.PI )/ n;

        int[] newArray = new int[n];
        if (matrixSize - 1 >= 0){
            System.arraycopy(array, 1, newArray, 0, matrixSize - 1);
        }

        for (int k = 0; k < n; k++){
            double a = k * alpha;
            for (int t = 0; t < n; t++) {
                real[k] += newArray[t] * Math.cos(a * t);
                im[k] += newArray[t] * Math.sin(a * t);
            }
            result[k] = Math.sqrt( real[k] * real[k] + im[k] * im[k]) / n;
        }
        return result;
    }

    private double[][] prepareData(int[][] exampleArray){
        double[][] dftList = new double[exampleAmount][matrixSize - 1];
        for (int i = 0; i < exampleAmount; i++) {
            dftList[i] = DFT(exampleArray[i]);
        }
        return dftList;
    }

    private double o(double[] weights, double wZero, int[] example,
                     double[] dftExample, int p, int iter){
        double sum = 0.0;
        int k = 0;

        sum += wZero * 1;
        for (int j = matrixSize - 1; j < weights.length; j++){
            sum += weights[j] * dftExample[k];
            k++;
        }

        return sum;
    }

    private ArrayList<double[]> startLearn(){
        ArrayList<double[]> finalWeightsArray = new ArrayList<>();
        int[][] exampleArray = readFile();
        dftList = prepareData(exampleArray);
        double[] finalWeights;
        double[] weights;
        double wZero;

        for (int i = 0 ; i < perc; i++){
            wZero = weightZero();
            weights = weightRandom();
            finalWeights = learn(exampleArray, weights, wZero, i);
            finalWeightsArray.add(finalWeights);
        }
        return finalWeightsArray;
    }

    private int goodAnswer(int i, int per){
        if ( i == per ){
            return 1;
        }else {
            return -1;
        }
    }

    private double[] learn(int[][] exampleArray,
                           double[] weights,
                           double wZero,
                           int percetonToLearn){
        double ni = 0.01;
        int[] example;
        double[] dftExample;
        double error = 0.0;
        int goodAnswer;
        double o;
        int rand;

        double m;

        for (int iter = 0; iter < iterN; iter++) {
            rand = random.nextInt(exampleAmount);
            example = exampleArray[rand];
            dftExample = dftList[rand];
            goodAnswer = goodAnswer(example[0], percetonToLearn);

            o = o(weights, wZero, example, dftExample, percetonToLearn, iter);

            m = ni * ( goodAnswer - o );
            wZero += m;
            int k = 0;
            for (int j = matrixSize - 1 ; j < 2 * matrixSize - 2; j++){
                weights[j] += m * dftExample[k];
                k++;
            }

            error += Math.pow((o - goodAnswer), 2);

            if ((iter % 1000) == 0 ) {
                error = error / 1000;
                if (percetonToLearn == 0 ) {
                    zeroError.add(error);
                } else if (percetonToLearn == 1) {
                    oneError.add(error);
                } else if (percetonToLearn == 2) {
                    twoError.add(error);
                } else if (percetonToLearn == 3) {
                    threeError.add(error);
                } else if (percetonToLearn == 4) {
                    fourError.add(error);
                } else if (percetonToLearn == 5) {
                    fiveError.add(error);
                } else if (percetonToLearn == 6) {
                    sixError.add(error);
                } else if (percetonToLearn == 7) {
                    sevenError.add(error);
                } else if (percetonToLearn == 8) {
                    eightError.add(error);
                } else {//( percetonToLearn == 9 ){
                    nineError.add(error);
                }
                error = 0;
            }
        }

        finalWeightsZero.add(wZero);
        return weights;
    }
}
