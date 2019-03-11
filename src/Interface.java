import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Interface extends Frame {

    public static void main(String[] args){
        new Interface();
    }

    private Interface(){
        super("Adaline");

        setSize(490, 350);
        setResizable(false);

        Net net = new Net();
        JTextField textField = new JTextField();
        CheckButton checkButton = new CheckButton(net, textField);

        JButton clearButton = new JButton();
        clearButton.setText("Reset");
        ActionListener aL = e -> {
            net.clearField();

            checkButton.clearText();
        };
        clearButton.addActionListener(aL);

        GridBagConstraints gBC = new GridBagConstraints();
        setLayout(new GridBagLayout());

        gBC.fill = GridBagConstraints.HORIZONTAL;
        gBC.gridx = 0;
        gBC.gridy = 0;
        add(net, gBC);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        gBC.fill = GridBagConstraints.HORIZONTAL;
        gBC.gridx = 0;
        gBC.gridy = 0;
        panel.add(clearButton, gBC);


        gBC.fill = GridBagConstraints.HORIZONTAL;
        gBC.gridx = 0;
        gBC.gridy = 1;
        panel.add(checkButton, gBC);

        gBC.fill = GridBagConstraints.HORIZONTAL;
        gBC.gridx = 0;
        gBC.gridy = 2;
        panel.add(textField, gBC);

        gBC.fill = GridBagConstraints.HORIZONTAL;
        gBC.gridx = 1;
        gBC.gridy = 0;
        add(panel, gBC);

        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });


        ChartZero chartZero = new ChartZero(net);
        chartZero.pack( );
        RefineryUtilities.centerFrameOnScreen( chartZero );
        chartZero.setVisible( true );

        ChartOne chartOne = new ChartOne(net);
        chartOne.pack( );
        RefineryUtilities.centerFrameOnScreen( chartOne );
        chartOne.setVisible( true );

        ChartTwo chartTwo = new ChartTwo(net);
        chartTwo.pack( );
        RefineryUtilities.centerFrameOnScreen( chartTwo );
        chartTwo.setVisible( true );

        ChartThree chartThree = new ChartThree(net);
        chartThree.pack( );
        RefineryUtilities.centerFrameOnScreen( chartThree );
        chartThree.setVisible( true );

        ChartFour chartFour = new ChartFour(net);
        chartFour.pack( );
        RefineryUtilities.centerFrameOnScreen( chartFour );
        chartFour.setVisible( true );

        ChartFive chartFive = new ChartFive(net);
        chartFive.pack( );
        RefineryUtilities.centerFrameOnScreen( chartFive );
        chartFive.setVisible( true );

        ChartSix chartSix = new ChartSix(net);
        chartSix.pack( );
        RefineryUtilities.centerFrameOnScreen( chartSix );
        chartSix.setVisible( true );

        ChartSeven chartSeven = new ChartSeven(net);
        chartSeven.pack( );
        RefineryUtilities.centerFrameOnScreen( chartSeven );
        chartSeven.setVisible( true );

        ChartEight chartEight = new ChartEight(net);
        chartEight.pack( );
        RefineryUtilities.centerFrameOnScreen( chartEight );
        chartEight.setVisible( true );

        ChartNine chartNine = new ChartNine(net);
        chartNine.pack( );
        RefineryUtilities.centerFrameOnScreen( chartNine );
        chartNine.setVisible( true );
    }
}
