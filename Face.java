import java.awt.Color;
import java.awt.geom.*;
import java.util.*;
/*
Responsible for storing the necessary data of a face, i.e. its vertices and color.
*/
public class Face implements Comparable {
  int color_number;
  Point3D[] vertices;



  int number_of_vertices;
  double[] normal;
  boolean is_visible;
  int[] x_coords_projected;
  int[] y_coords_projected;

  ArrayList<Point3D[]> shadows_unrestricted;
  Area shadow;

  public Face(int number_of_vertices, int color_number, Point3D ... points ){
    this.number_of_vertices = number_of_vertices;
    vertices = new Point3D[number_of_vertices+1];

    for(int i = 0; i < number_of_vertices; i++)
      vertices[i] = points[i];

    vertices[number_of_vertices] = vertices[0];
    this.color_number = color_number;


  }

  public set_normal(){
    double[] v0 = { vertices[1].x-vertices[0].x, vertices[1].y-vertices[0].y, vertices[1].z-vertices[0].z };
    double[] v1 = { vertices[2].x-vertices[0].x, vertices[2].y-vertices[0].y, vertices[2].z-vertices[0].z };
    this.normal = Vector.crossproduct(v0,v1);

  }



  @Override
  public int compareTo(Object other_face) {
      double z_cummulative = 0;
      for(Point3D vertice : vertices ){
        z_cummulative += vertice.z;
      }

      double other_z_cummulative = 0;
      for(Point3D vertice : ( (Face)other_face ).vertices ){
        other_z_cummulative += vertice.z;
      }

      /* For Ascending order*/
      double result = z_cummulative/vertices.length - other_z_cummulative/vertices.length ;
      if( result > 0 )
        return 1;
      if( result < 0 )
        return -1;
      return 0;


  }

}
