import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class Scene {

  Polyhedron polyhedron;
  double[] camera_position; //Point3D?
  double[] point_light_source_position; //Point3D?



  public Scene(){
    this.polyhedron = new Polyhedron("./polyhedra_data/Cube.txt");;
    this.camera_position = new double[] {0.0,0.0,10.0};
    this.point_light_source_position = new double[] {0.0,0.0,1000.0};
    set_visiblity_flags();
    set_projection();


  }

    /*
    Given the x, y coordinates of a point within the sphere of known radius,
    the z coordinate can be calculated. With the all the coordinates known,
    the brightness of given point is calculated.
    TODO: add specular light
    */
    public double get_point_brightness(double[] point_coordinates, double[] point_normal){
      double[] point_to_light_source_vector = Vector.normalize( Vector.subtract(point_light_source_position, point_coordinates) );
      double[] normal = Vector.normalize(point_coordinates);


      double brightness = 40 + 215 * eliminate_negatives( Vector.dotproduct(point_to_light_source_vector, normal) );
      return brightness;
    }

    private double eliminate_negatives(double value){
      if(value > 0)
        return value;
      else
        return 0;
    }

    private double specular(double[] point_coordinates){
      double[] point_to_light_source_vector = Vector.subtract(point_light_source_position, point_coordinates);
      double[] point_to_camera_vector = Vector.subtract(camera_position, point_coordinates);
      double[] w = {0,0,0}; //project w onto n
      return 0.0;
    }



    public void set_normals(){
      return;
    }

    public void set_visiblity_flags(){
      Arrays.sort(polyhedron.faces);
      for( Face face : polyhedron.faces ){
        face.is_visible = false;

        //choose a point on the face
        double[] point = { face.vertices[0].x, face.vertices[0].y, face.vertices[0].z};
        //get two vectors in the plane of the face
        double[] v0 = { face.vertices[1].x-face.vertices[0].x, face.vertices[1].y-face.vertices[0].y, face.vertices[1].z-face.vertices[0].z };
        double[] v1 = { face.vertices[2].x-face.vertices[0].x, face.vertices[2].y-face.vertices[0].y, face.vertices[2].z-face.vertices[0].z };
        face.normal = Vector.crossproduct(v0,v1);
        /*cross v0, v1 and dot resulting vector with vector from point to camera position.
        If the result is greater than zero, then the angle between the vectors must be in the
        range <90 and >-90 and so the face is visible.
        */
        if( Vector.dotproduct( face.normal, Vector.subtract(camera_position, point) ) > 0 )
          face.is_visible = true;

      }
    }

    public void set_projection(){
      int[] xpoints;
      int[] ypoints;

      for( Face face : polyhedron.faces ){
        xpoints = new int[ face.number_of_vertices ];
        ypoints = new int[ face.number_of_vertices ];


        for( int i=0; i<face.number_of_vertices; i++ ){
          Point3D p = face.vertices[i];

          p.to_cartesian();

          xpoints[i] = (int)( 100*p.x/( 1-(p.z/camera_position[2]) ) );
          ypoints[i] = (int)( 100*p.y/( 1-(p.z/camera_position[2]) ) );
        }
        face.x_coords_projected = xpoints;
        face.y_coords_projected = ypoints;

      }


    }







}
