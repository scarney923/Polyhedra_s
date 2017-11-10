import javax.swing.*;
import java.awt.*;

public class Main
{

    public static void main(String[] args){
		JFrame frame = new JFrame();
		frame.setSize(800,600);

		//Sets the window to close when upper right corner clicked.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("Rotate Cube");

    Canvas canvas = new Canvas();
    ControlPanel controlPanel = new ControlPanel(canvas);
    Container container = frame.getContentPane();
    container.setLayout(new BorderLayout());
    container.add(canvas, BorderLayout.CENTER);
    container.add(controlPanel, BorderLayout.EAST);

		frame.pack();
		frame.setResizable(true);
		frame.setVisible(true);

    }
} // Main
