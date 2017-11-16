import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class Polyhedron {
  String name;
  int number_of_vertices;
  int number_of_faces;
  Vertex[] vertices;
  Face[] faces;



  //constructor
  public Polyhedron(String filepath){

    PolyDataFileParser reader = new PolyDataFileParser( filepath );
    name = reader.get_polyhedron_name();
    number_of_vertices = reader.get_number_of_vertices();
    number_of_faces = reader.get_number_of_faces();

    vertices = reader.get_vertices(number_of_vertices);
    faces = reader.get_faces(number_of_vertices, number_of_faces, vertices);
  }

  public void transform(double[][] M){
    for(Vertex vertex : vertices)
      vertex.apply_transform(M);
  }
}
