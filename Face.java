import java.awt.Color;
import java.awt.geom.*;
import java.util.*;
/*
Responsible for storing the necessary data of a face, i.e. its vertices and color.
*/
public class Face implements Comparable {
  int color_number;
  Vertex[] vertices;
  int number_of_vertices;
  double[] normal;

  boolean is_visible_to_camera;
  boolean is_visible_to_lightsource;

  int[] x_coords_projected;
  int[] y_coords_projected;

  Vertex[] shadow;
  int[] shadow_x_coords_projected;
  int[] shadow_y_coords_projected;

  public Face(int number_of_vertices, int color_number, Vertex ... vertices ){
    this.number_of_vertices = number_of_vertices;
    this.vertices = new Vertex[number_of_vertices+1];

    for(int i = 0; i < number_of_vertices; i++){
      this.vertices[i] = vertices[i];

    }

    this.vertices[number_of_vertices] = this.vertices[0];
    this.color_number = color_number;



  }

  public void set_normal(){
    double[] v0 = { vertices[1].x-vertices[0].x, vertices[1].y-vertices[0].y, vertices[1].z-vertices[0].z };
    double[] v1 = { vertices[2].x-vertices[0].x, vertices[2].y-vertices[0].y, vertices[2].z-vertices[0].z };
    this.normal = Vector.crossproduct(v0,v1);

  }


  /*

  */
  @Override
  public int compareTo(Object other_face) {
      double z_cummulative = 0;
      for(Vertex vertice : vertices ){
        z_cummulative += vertice.z;
      }

      double other_z_cummulative = 0;
      for(Vertex vertice : ( (Face)other_face ).vertices ){
        other_z_cummulative += vertice.z;
      }

      /* Ascending order*/
      double result = z_cummulative/vertices.length - other_z_cummulative/vertices.length ;
      if( result > 0 )
        return 1;
      if( result < 0 )
        return -1;
      return 0;


  }

}
