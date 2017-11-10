import java.awt.Color;


public class Cube extends Polyhedron {

  public Cube(){
    super.faces = new Face[6];
    super.number_of_faces = 6;
    super.number_of_face_vertices = 4;
    super.isVariable = false; 

    //Initialize faces with their respective vertices. The vertices around each face is arranged in CLOCKWISE order.

    //left
    super.faces[0]=new Face( 4, Color.blue, new Point3D(-1,-1,-1),new Point3D(-1,1,-1 ),new Point3D(-1,1,1),new Point3D(-1,-1,1) );
    //right
    super.faces[1]=new Face( 4, Color.red, new Point3D(1,-1,-1),new Point3D(1,-1,1),new Point3D(1,1,1),new Point3D(1,1,-1));
    //bottom
    super.faces[2]=new Face( 4, Color.yellow, new Point3D(-1,-1,-1),new Point3D(-1,-1,1 ),new Point3D(1,-1,1),new Point3D(1,-1,-1) );
    //top
    super.faces[3]=new Face( 4, Color.green, new Point3D(-1,1,-1),new Point3D(1,1,-1),new Point3D(1,1,1),new Point3D(-1,1,1) );
    //back
    super.faces[4]=new Face( 4, Color.white, new Point3D(-1,-1,-1),new Point3D(1,-1,-1),new Point3D(1,1,-1),new Point3D(-1,1,-1) );
    //front
    super.faces[5]=new Face( 4, Color.pink, new Point3D(-1,-1,1),new Point3D(-1,1,1),new Point3D(1,1,1),new Point3D(1,-1,1) );

  }



}
