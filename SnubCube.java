
public class SnubCube extends Polyhedron {

  public SnubCube() {
    super.number_of_vertices = 24;
    super.number_of_faces = 38;
    super.number_of_face_vertices = 4;
    super.isVariable = true; 


    PolyhedronFileReader reader = new PolyhedronFileReader("./polyhedra_data/SnubCube.txt", number_of_vertices, number_of_faces, number_of_face_vertices, true );
    super.faces = reader.snubCube();


  }
}
