import java.awt.Color;
/*
Responsible for storing the necessary data of a face, i.e. its vertices and color.
*/
public class Face{
  Color color;
  Point3D[] vertices;

  public Face(int number_of_vertices, Color color, Point3D ... points ){

    vertices = new Point3D[number_of_vertices+1];

    for(int i = 0; i < number_of_vertices; i++)
      vertices[i] = points[i];

    vertices[number_of_vertices] = vertices[0];
    this.color = color;


  }
}
