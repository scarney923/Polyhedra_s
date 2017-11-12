import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class Scene {

  Polyhedron polyhedron;
  double[] camera_position; //Point3D?
  double[] point_light_source_position; //Point3D?


  public Scene(Polyhedron polyhedron, double[] camera_position, double[] point_light_source_position){
    this.polyhedron = polyhedron;
    this.camera_position = camera_position;
    this.point_light_source_position = point_light_source_position;
    set_visiblity_flags();


  }

  public void set_polyhedron(Polyhedron polyhedron){
    this.polyhedron = polyhedron;
    set_visiblity_flags();
  }


    /*
    Given the x, y coordinates of a point within the sphere of known radius,
    the z coordinate can be calculated. With the all the coordinates known,
    the brightness of given point is calculated.
    */
    public double get_point_brightness(double[] point_coordinates, double[] point_normal){
      double[] point_to_light_source_vector = Vector.normalize( Vector.subtract(point_light_source_position, point_coordinates) );
      double[] normal = Vector.normalize(point_coordinates);

      /*The dotproduct gives us the cosine between the point-to-light-source-vector
      and the normal (the vectors having been normalized before). If the cosine is small, then the (absolute value of the) angle is large between
      the two vectors so the light source has less affect on the point. If the cosine turns out to be negative, then the angle
      is >90 degress (or <-90 degrees), which means that the point is in the shadow. We set the diffuse light to zero and the point is only
      affected by ambient light. The diffuse light constant (accounting for the brightness of the light source) to 200. This means that
      the diffuse light is going to be within the range 0-200, with the ambient light added to it to give the final brightness.
      */
      double brightness = 50 + 200 * eliminate_negatives( Vector.dotproduct(point_to_light_source_vector, normal) );
      return brightness;
    }

    public double eliminate_negatives(double value){
      if(value > 0)
        return value;
      else
        return 0;
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


    public void draw_wireframe(Graphics2D g2d){
      g2d.setPaint( Color.white );

      for( Face face : polyhedron.faces ){
        double x1, y1, x2, y2;
        Point3D p1, p2;

          for( int i=0; i<face.vertices.length-1; i++ ){
            p1 = face.vertices[i];
            p2 = face.vertices[i+1];
            /*since any transformation might have distorted the coordinates in their
            Cartesian form, we convert them before projecting them */
            p1.to_cartesian();
            p2.to_cartesian();

            //project vertices onto 2D space. Scaling factor is set to 100
            x1 = 100*p1.x/( 1-(p1.z/camera_position[2]) );
            y1 = 100*p1.y/( 1-(p1.z/camera_position[2]) ) ;
            x2 = 100*p2.x/( 1-(p2.z/camera_position[2]) );
            y2 = 100*p2.y/( 1-(p2.z/camera_position[2]) );

            g2d.draw( new Line2D.Double(x1, y1, x2, y2) );

          }
        }
    }

    public void draw_colored(Graphics2D g2d){
      set_visiblity_flags();
      int[] xpoints;
      int[] ypoints;


        //only if the visible flag is set to true do we go on and paint the face
      for( Face face : polyhedron.faces ){
        if(face.is_visible){

          xpoints = new int[ face.number_of_vertices ];
          ypoints = new int[ face.number_of_vertices ];
          //project all vertices of given face onto 2D space. Scaling factor is set to 100
          for( int i=0; i<face.number_of_vertices; i++ ){
            Point3D p = face.vertices[i];

            p.to_cartesian();

            xpoints[i] = (int)( 100*p.x/( 1-(p.z/camera_position[2]) ) );
            ypoints[i] = (int)( 100*p.y/( 1-(p.z/camera_position[2]) ) );
          }
          g2d.setPaint( face.color );
          g2d.fill( new Polygon(xpoints, ypoints, face.number_of_vertices) );
        }


      }
    }

    public void draw_shades(Graphics2D g2d){
      set_visiblity_flags();
      int[] xpoints;
      int[] ypoints;
      //only if the visible flag is set to true do we go on and paint the face
      for( Face face : polyhedron.faces ){
        if(face.is_visible){


          //calculate color
          int brightness = (int)get_point_brightness( face.vertices[0].to_vector(), face.normal );
          Color shaded_face_color = new Color(brightness,brightness,brightness);


          //get all face vertices
          xpoints = new int[ face.number_of_vertices ];
          ypoints = new int[ face.number_of_vertices ];

          for( int i=0; i<face.number_of_vertices; i++ ){
            Point3D p = face.vertices[i];

            p.to_cartesian();

            xpoints[i] = (int)( 100*p.x/( 1-(p.z/camera_position[2]) ) );
            ypoints[i] = (int)( 100*p.y/( 1-(p.z/camera_position[2]) ) );
          }


          //paint shaded polyhedron
          g2d.setPaint( shaded_face_color );
          g2d.fill( new Polygon(xpoints, ypoints, face.number_of_vertices) );
        }


      }
    }




}
