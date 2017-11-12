import javax.swing.*;
import java.awt.*;

public class Main2
{

    public static void main(String[] args){
		JFrame frame = new JFrame();
    JFrame cp_frame = new JFrame();


		cp_frame.setSize(500,600);

		//Sets the window to close when upper right corner clicked.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("Polyhedra Inspector");

    cp_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    cp_frame.setTitle("Controls");

    Canvas canvas = new Canvas();
    ControlPanel controlPanel = new ControlPanel(canvas);
    /*Container container = frame.getContentPane();
    container.setLayout(new BorderLayout());
    container.add(canvas);*/
    //container.add(controlPanel, BorderLayout.EAST);
    frame.add(canvas);
    cp_frame.add(controlPanel);


		//frame.pack();
		//frame.setResizable(true);
    frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

		frame.setVisible(true);
    cp_frame.setVisible(true);


    }
} // Main
