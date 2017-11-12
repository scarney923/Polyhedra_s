import java.awt.Color;
/*
Responsible for storing the necessary data of a face, i.e. its vertices and color.
*/
public class Face implements Comparable {
  Color color;
  Point3D[] vertices;
  int number_of_vertices;
  double[] normal;
  boolean is_visible;

  public Face(int number_of_vertices, Color color, Point3D ... points ){
    this.number_of_vertices = number_of_vertices;
    vertices = new Point3D[number_of_vertices+1];

    for(int i = 0; i < number_of_vertices; i++)
      vertices[i] = points[i];

    vertices[number_of_vertices] = vertices[0];
    this.color = color;


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
