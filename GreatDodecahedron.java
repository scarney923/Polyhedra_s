

public class GreatDodecahedron extends Polyhedron {

  public GreatDodecahedron() {
    super.number_of_vertices = 32;
    super.number_of_faces = 60;
    super.number_of_face_vertices = 3;


    PolyhedronFileReader reader = new PolyhedronFileReader("./polyhedra_data/Great_Dodecahedron.txt", 20, 12, 5 );
    super.faces = reader.generate_faces_from_file_data();


  }
}
