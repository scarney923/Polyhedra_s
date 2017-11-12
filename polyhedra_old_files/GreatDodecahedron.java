

public class GreatDodecahedron extends Polyhedron {

  public GreatDodecahedron() {
    super.number_of_vertices = 32;
    super.number_of_faces = 60;

    PolyhedronFileReader reader = new PolyhedronFileReader("./polyhedra_data/GreatDodecahedron.txt", number_of_vertices, number_of_faces);
    super.faces = reader.generate_faces_from_file_data();


  }
}
