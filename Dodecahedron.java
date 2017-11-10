
public class Dodecahedron extends Polyhedron {

  public Dodecahedron() {
    super.number_of_vertices = 20;
    super.number_of_faces = 12;
    super.number_of_face_vertices = 5;


    PolyhedronFileReader reader = new PolyhedronFileReader("./polyhedra_data/Dodecahedron.txt", 20, 12, 5 );
    super.faces = reader.generate_faces_from_file_data();


  }
}
