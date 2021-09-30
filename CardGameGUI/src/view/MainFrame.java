package view;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class MainFrame extends JFrame {

    /**
     * Create the frame.
     */
    public MainFrame() {
        //  set close option
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // setting position and dimension of the Frame
        setBounds(100, 100, 800, 500);

        // set layout to Border Style
        setLayout(new BorderLayout());
    }

}
