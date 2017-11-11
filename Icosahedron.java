

public class Icosahedron extends Polyhedron {

  public Icosahedron() {
    super.number_of_vertices = 12;
    super.number_of_faces = 20;

    PolyhedronFileReader reader = new PolyhedronFileReader("./polyhedra_data/Icosahedron.txt", number_of_vertices, number_of_faces);
    super.faces = reader.generate_faces_from_file_data();


  }
}
