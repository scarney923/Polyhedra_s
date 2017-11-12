
public class GreatIcosahedron extends Polyhedron {

  public GreatIcosahedron() {
    super.number_of_vertices = 92;
    super.number_of_faces = 240;

    PolyhedronFileReader reader = new PolyhedronFileReader("./polyhedra_data/GreatIcosahedron.txt", number_of_vertices, number_of_faces );
    super.faces = reader.generate_faces_from_file_data();


  }
}
