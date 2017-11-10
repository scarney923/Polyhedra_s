

public class GreatDodecahedron extends Polyhedron {

  public GreatDodecahedron() {
    super.number_of_vertices = 32;
    super.number_of_faces = 60;
    super.number_of_face_vertices = 3;
    super.isVariable = false; 

    PolyhedronFileReader reader = new PolyhedronFileReader("./polyhedra_data/GreatDodecahedron.txt", 32, 60, 3, false );
    super.faces = reader.generate_faces_from_file_data();


  }
}
