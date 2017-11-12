import javax.swing.*;
import java.awt.*;

public class Main
{

    public static void main(String[] args){
    double[] v = {1,1,0};
    double[] v_n = Vector.normalize(v);
    for(int i=0; i<3; i++)
      System.out.println("NORM" + v_n[i]);
    JFrame frame = new JFrame();
    JFrame cp_frame = new JFrame();


		cp_frame.setSize(500,600);

		//Sets the window to close when upper right corner clicked.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("Polyhedra Fun");

    cp_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    cp_frame.setTitle("Controls");

    Scene scene = new Scene();
    Renderer canvas = new Renderer(scene);
    ControlPanel controlPanel = new ControlPanel(canvas, scene);

    frame.add(canvas);
    cp_frame.add(controlPanel);


		//frame.pack();
		//frame.setResizable(true);
    frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

		frame.setVisible(true);
    cp_frame.setVisible(true);


    }
} // Main
