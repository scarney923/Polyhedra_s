import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

/*
Responsible for rendering the polyhedron on screen.
*/

public class Canvas extends JPanel {

  Polyhedron polyhedron;
  Scene scene;
  boolean wireframe = true;
  boolean colors = false;
  boolean shades = false;
  boolean shadows = false;


  public Canvas(){
    setPreferredSize(new Dimension(600,450));
		setBackground(Color.black);
    polyhedron = new Polyhedron("./polyhedra_data/Cube.txt");
    double[] point_light_source_position = {0.0,0.0,1000.0};
    //point_light_source_position = new double[3] {0,0,1000};
    double[] camera_position = {0.0,0.0,10.0};
    //camera_position = new double[3] {0,0,10};

    scene = new Scene(polyhedron, camera_position, point_light_source_position);


  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);

		Graphics2D g2d = (Graphics2D)g;
		g2d.translate(getWidth()/2,getHeight()/2);
    g2d.scale(1,-1);
    if(colors) //draw colored octahedron?
      scene.draw_colored(g2d);
    if(wireframe) //draw wirefire?
      scene.draw_wireframe(g2d);
    if(shades) //draw colored octahedron?
      scene.draw_shades(g2d);
    //if(wireframe)
    //    scene.draw_wireframe(g2d);


  }




}
