import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

/*
Responsible for rendering the polyhedron on screen.
*/

public class Renderer extends JPanel {

  Scene scene;
  boolean wireframe = true;
  boolean colors = false;
  boolean shades = false;
  boolean shadows = false;
  Color[] colors_map;
  int scale;



  public Renderer(Scene scene){
    this.scene = scene;
    this.scale = 150;


    setPreferredSize(new Dimension(600,450));
		setBackground(Color.black);


    colors_map = new Color[10];
    colors_map[0] = Color.BLUE;
    colors_map[1] = Color.CYAN;
    colors_map[2] = Color.GRAY;
    colors_map[3] = Color.GREEN;
    colors_map[4] = Color.MAGENTA;
    colors_map[5] = Color.ORANGE;
    colors_map[6] = Color.WHITE;
    colors_map[7] = Color.RED;
    colors_map[8] = Color.LIGHT_GRAY;
    colors_map[9] = Color.YELLOW;


  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);

		Graphics2D g2d = (Graphics2D)g;
		g2d.translate(getWidth()/2,getHeight()/2);
    g2d.scale(1,-1);

    setBackground(Color.BLACK);


    if(shadows){
      setBackground(Color.WHITE);
      show_shadows(g2d);
    }
    if(colors)
      show_colors(g2d);
    if(shades)
      show_shades(g2d, colors);
    if(wireframe)
      show_wireframe(g2d);


  }
  /*
  Explain range 0-length-2
  */
  public void show_wireframe(Graphics2D g2d){
    g2d.setPaint( Color.white );

    for( Face face : scene.polyhedron.faces )
        for( int i = 0; i < face.vertices.length - 2; i++ )
          g2d.draw( new Line2D.Double(face.x_coords_projected[i], face.y_coords_projected[i], face.x_coords_projected[i+1], face.y_coords_projected[i+1]) );



  }

  public void show_colors(Graphics2D g2d){
    for( Face face : scene.polyhedron.faces ){
      if(face.is_visible_to_camera){

        g2d.setPaint( colors_map[ face.color_number ] );
        g2d.fill( new Polygon(face.x_coords_projected, face.y_coords_projected, face.number_of_vertices) );
      }


    }
  }
  /*
  Note that:

  (i) Shades can be shown either in grayscale or in colors, depending on the 'color' flag's
  value. Colored shading is determined by taking a fraction of the color mapped to the face. In other words,
  without shading, all faces have maximum brightness. The fraction is the calculated brightness
  divided by 255 (max value of RGB component).

  (ii) Flat shading is used. Therefore, all points on a given face have the same color.

  */
  public void show_shades(Graphics2D g2d, Boolean colors){
    for( Face face : scene.polyhedron.faces ){
      if(face.is_visible_to_camera){

        double brightness = scene.get_point_brightness( face.vertices[0].get_vector_form(), face.normal );

        int red, green, blue;

        if(colors){
          red = (int) ( colors_map[ face.color_number ].getRed() * brightness / 255 );
          green = (int) ( colors_map[ face.color_number ].getGreen() * brightness / 255 );
          blue = (int) ( colors_map[ face.color_number ].getBlue() * brightness / 255 );
        }
        else{
          red = (int) brightness;
          green = (int) brightness;
          blue = (int) brightness;
        }

        Color shaded_face_color = new Color( red, green, blue );
        g2d.setPaint( shaded_face_color );
        g2d.fill( new Polygon(face.x_coords_projected, face.y_coords_projected, face.number_of_vertices) );
      }


    }
  }

  public void show_shadows(Graphics2D g2d){
    g2d.setPaint(Color.DARK_GRAY);

    scene.set_shadows();
    scene.set_projected_shadows(scale);

    for(Face face : scene.polyhedron.faces)
      g2d.fill( new Polygon(face.shadow_x_coords_projected, face.shadow_y_coords_projected , face.number_of_vertices ) );

  }



}
