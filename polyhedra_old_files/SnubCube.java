
public class SnubCube extends Polyhedron {

  public SnubCube() {
    super.number_of_vertices = 24;
    super.number_of_faces = 38;


    PolyhedronFileReader reader = new PolyhedronFileReader("./polyhedra_data/SnubCube.txt", number_of_vertices, number_of_faces );
    super.faces = reader.generate_faces_from_file_data();


  }
}
