import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public abstract class Polyhedron {

  int number_of_vertices;
  int number_of_faces;
  int number_of_face_vertices;

  Face[] faces;

  public void transform(double[][] M){
    for(Face face : faces)
      for(int i=0; i<number_of_face_vertices; i++)
        face.vertices[i].apply_transform(M);
  }

  public void draw_wireframe(Graphics2D g2d){
    g2d.setPaint( Color.white );

    for( Face face : faces ){
      double x1, y1, x2, y2;
      Point3D p1, p2;

        for( int i=0; i<number_of_face_vertices; i++ ){
          p1 = face.vertices[i];
          p2 = face.vertices[i+1];
          /*since any transformation might have distorted the coordinates in their
          Cartesian form, we convert them before projecting them */
          p1.to_cartesian();
          p2.to_cartesian();

          //project vertices onto 2D space. Scaling factor is set to 100
          x1 = 100*p1.x/( 1-(p1.z/7) );
          y1 = 100*p1.y/( 1-(p1.z/7) ) ;
          x2 = 100*p2.x/( 1-(p2.z/7) );
          y2 = 100*p2.y/( 1-(p2.z/7) );

          g2d.draw( new Line2D.Double(x1, y1, x2, y2) );

        }
      }
  }

  public void draw_colored(Graphics2D g2d){
    int[] xpoints = new int[ number_of_face_vertices ];
    int[] ypoints = new int[ number_of_face_vertices ];

    /*the camera position is on the z-axis. however, it's important that it lies
    outside of the cube, i.e. that it's z coordinate > 1 */
    double[] camera_position = {0.0,0.0,7.0};

    Arrays.sort(faces);
    for( Face face : faces ){
      boolean visible = false;

      //determine if face is visible

      //choose a point on the face
      double[] point = { face.vertices[0].x, face.vertices[0].y, face.vertices[0].z};
      //get two vectors in the plane of the face
      double[] v0 = { face.vertices[1].x-face.vertices[0].x, face.vertices[1].y-face.vertices[0].y, face.vertices[1].z-face.vertices[0].z };
      double[] v1 = { face.vertices[2].x-face.vertices[0].x, face.vertices[2].y-face.vertices[0].y, face.vertices[2].z-face.vertices[0].z };
      /*cross v0, v1 and dot resulting vector with vector from point to camera position.
      If the result is greater than zero, then the angle between the vectors must be in the
      range <90 and >-90 and so the face is visible.
      */
      if( Vector.dotproduct( Vector.crossproduct(v1,v0), Vector.subtract(camera_position, point) ) > 0 )
        visible = true;

      //only if the visible flag is set to true do we go on and paint the face
      if(visible){

        //project all vertices of given face onto 2D space. Scaling factor is set to 100
        for( int i=0; i<number_of_face_vertices; i++ ){
          Point3D p = face.vertices[i];

          p.to_cartesian();

          xpoints[i] = (int)( 100*p.x/( 1-(p.z/7) ) );
          ypoints[i] = (int)( 100*p.y/( 1-(p.z/7) ) );
        }
        g2d.setPaint( face.color );
        g2d.fill( new Polygon(xpoints, ypoints, number_of_face_vertices) );
      }
    }
  }

}
