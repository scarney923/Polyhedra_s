import java.awt.Color;


public class Octahedron extends Polyhedron{

  public Octahedron(){
    super.number_of_vertices = 6;
    super.number_of_faces = 8;

    PolyhedronFileReader reader = new PolyhedronFileReader("./polyhedra_data/Octahedron.txt", number_of_vertices, number_of_faces );
    super.faces = reader.generate_faces_from_file_data();






  }



}
