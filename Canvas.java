import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

/*
Responsible for rendering the octahedron on screen.
*/

public class Canvas extends JPanel {
  //Octahedron octahedron;
  //Dodecahedron dodecahedron;
  Polyhedron polyhedron;

  boolean wireframe = true;
  boolean colors = false;

  public Canvas(){
    setPreferredSize(new Dimension(600,450));
		setBackground(Color.black);
    //octahedron = new Octahedron();
    //dodecahedron = new Dodecahedron();
    polyhedron = new Icosahedron();
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);

		Graphics2D g2d = (Graphics2D)g;
		g2d.translate(getWidth()/2,getHeight()/2);
    g2d.scale(1,-1);
    if(colors) //draw colored octahedron?
      polyhedron.draw_colored(g2d);
    if(wireframe) //draw wirefire?
      polyhedron.draw_wireframe(g2d);


  }




}
