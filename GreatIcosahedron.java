
public class GreatIcosahedron extends Polyhedron {

  public GreatIcosahedron() {
    super.number_of_vertices = 92;
    super.number_of_faces = 240;
    super.number_of_face_vertices = 3;
    super.isVariable = false; 


    PolyhedronFileReader reader = new PolyhedronFileReader("./polyhedra_data/GreatIcosahedron.txt", 92, 240, 3, false );
    super.faces = reader.generate_faces_from_file_data();


  }
}
