
public class GStellatedDodecahedron extends Polyhedron {

  public GStellatedDodecahedron() {
    super.number_of_vertices = 32;
    super.number_of_faces = 60;
    super.number_of_face_vertices = 3;


    PolyhedronFileReader reader = new PolyhedronFileReader("./polyhedra_data/GreatStellatedDodecahedron.txt", 32, 60, 3 );
    super.faces = reader.generate_faces_from_file_data();


  }
}
