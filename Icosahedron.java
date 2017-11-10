

public class Icosahedron extends Polyhedron {

  public Icosahedron() {
    super.number_of_vertices = 12;
    super.number_of_faces = 20;
    super.number_of_face_vertices = 3;


    PolyhedronFileReader reader = new PolyhedronFileReader("./polyhedra_data/Icosahedron.txt", 12, 20, 3 );
    super.faces = reader.generate_faces_from_file_data();


  }
}
