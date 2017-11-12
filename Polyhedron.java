import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class Polyhedron {
  String name;
  int number_of_vertices;
  int number_of_faces;
  Face[] faces;

  //constructor
  public Polyhedron(String filepath){

    PolyhedronGenerator reader = new PolyhedronGenerator( filepath );
    name = reader.get_polyhedron_name();
    number_of_vertices = reader.get_number_of_vertices();
    number_of_faces = reader.get_number_of_faces();
    faces = reader.get_faces(number_of_vertices, number_of_faces);
  }

  public void transform(double[][] M){
    for(Face face : faces)
      for(int i=0; i<face.number_of_vertices; i++)
        face.vertices[i].apply_transform(M);
  }

  

}
