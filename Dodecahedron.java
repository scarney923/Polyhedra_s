
public class Dodecahedron extends Polyhedron {

  public Dodecahedron() {
    super.number_of_vertices = 20;
    super.number_of_faces = 12;

    PolyhedronFileReader reader = new PolyhedronFileReader("./polyhedra_data/Dodecahedron.txt", number_of_vertices, number_of_faces);
    super.faces = reader.generate_faces_from_file_data();


  }
}
