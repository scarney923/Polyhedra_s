import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;



/*
Responsible for determining properies of a polygon, given a camera position and light source position.
*/
public class Scene {

  Polyhedron polyhedron;
  double[] camera_position;
  double[] point_light_source_position;
  final int INITIAL_SCALE = 150;

  public Scene(){
    this.polyhedron = new Polyhedron("./polyhedra_data/Cube.txt");;
    this.camera_position = new double[] {0.0,0.0,10.0};
    this.point_light_source_position = new double[] {0.0,500.0,1000.0};
    set_visiblity_flags();
    set_projection(INITIAL_SCALE);


  }




    /*
    Calculates the brightness (light intensity) of a given point.

    Light intensity is calculated using the elementary lighting model:
    I = I<ambient> + I<diffuse> + I<specular>
    - I<ambient> = 35
    - I<diffuse> = 145 * L . n
    - I<specular> = 10 * R . v

    */
    public double get_point_brightness(double[] point_coordinates, double[] normal){
      double[] unit_point_to_light_source_vector = Vector.get_normalized( Vector.subtract(point_light_source_position, point_coordinates) );
      double[] unit_point_to_camera_vector = Vector.get_normalized( Vector.subtract(camera_position, point_coordinates) );

      double[] unit_normal = Vector.get_normalized(normal);
      double[] w = Vector.scale( Vector.dotproduct(unit_point_to_light_source_vector, unit_normal), unit_normal );
      double[] reflected_vector = Vector.subtract( Vector.scale(2, w), unit_point_to_light_source_vector );

      double brightness =  ( 35 ) + ( 145 * eliminate_negatives(Vector.dotproduct(unit_point_to_light_source_vector, unit_normal)) ) + ( 10 * Math.pow( Vector.dotproduct( reflected_vector, unit_point_to_camera_vector ), 4 ) );
      return brightness;
    }

    /*
    Determines whether or not a face is a back face through the following steps:
    1)
    2)
    3) cross v0, v1 and dot resulting vector with vector from point to camera position.
    If the result is greater than zero, then the angle between the vectors must be in the
    range <90 and >-90 and so the face is visible.
    */
    public void set_visiblity_flags(){

      for( Face face : polyhedron.faces ){
        face.is_visible = false;

        double[] point = { face.vertices[0].x, face.vertices[0].y, face.vertices[0].z};

        double[] v0 = { face.vertices[1].x-face.vertices[0].x, face.vertices[1].y-face.vertices[0].y, face.vertices[1].z-face.vertices[0].z };
        double[] v1 = { face.vertices[2].x-face.vertices[0].x, face.vertices[2].y-face.vertices[0].y, face.vertices[2].z-face.vertices[0].z };
        face.normal = Vector.crossproduct(v0,v1);

        if( Vector.dotproduct( face.normal, Vector.subtract(camera_position, point) ) > 0 )
          face.is_visible = true;

      }
    }


    /*

    */
    public void set_projection(int scale){
      int[] xpoints;
      int[] ypoints;

      for( Face face : polyhedron.faces ){
        xpoints = new int[ face.number_of_vertices ];
        ypoints = new int[ face.number_of_vertices ];


        for( int i=0; i<face.number_of_vertices; i++ ){
          Vertex p = face.vertices[i];

          xpoints[i] = (int)( scale*p.x/( 1-(p.z/camera_position[2]) ) );
          ypoints[i] = (int)( scale*p.y/( 1-(p.z/camera_position[2]) ) );
        }
        face.x_coords_projected = xpoints;
        face.y_coords_projected = ypoints;

      }


    }

    public void set_shadow(){
      double Vz_new = -4;
      ArrayList<Vertex> fun1 = new ArrayList<Vertex>();

      for( Face face : polyhedron.faces){
        for( Vertex vertex : face.vertices){
          double t = ( Vz_new - point_light_source_position[2] ) / ( vertex.z - point_light_source_position[2] );
          double Vx_new = point_light_source_position[0] + t*(vertex.x - point_light_source_position[0]);
          double Vy_new = point_light_source_position[1] + t*(vertex.y - point_light_source_position[1]);
          fun1.add( new Vertex(Vx_new, Vy_new, Vz_new) );


        }
      }

      //fun = QuickHull.quickHull(fun1);

    }



    private double eliminate_negatives(double value){
      if(value > 0)
        return value;
      else
        return 0;
    }




}
