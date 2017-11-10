import java.awt.Color;


public class Octahedron extends Polyhedron{

  public Octahedron(){
    super.faces = new Face[8];
    super.number_of_faces = 8;
    super.number_of_vertices_per_face = 3;

    //Initialize faces with their respective vertices. The vertices around each face is arranged in CLOCKWISE order.

    //left

    super.faces[0]=new Face( 3, Color.blue, new Point3D(0, 1, 0),new Point3D(0, 0, 1),new Point3D(1, 0, 0) );
    //right
    super.faces[1]=new Face( 3, Color.red, new Point3D(0, 1, 0),new Point3D(1, 0, 0),new Point3D(0, 0, -1));
    //bottom
    super.faces[2]=new Face( 3, Color.yellow, new Point3D(0, 1, 0),new Point3D(0, 0, -1 ),new Point3D(-1, 0, 0) );
    //top
    super.faces[3]=new Face( 3, Color.green, new Point3D(0, 1, 0),new Point3D(-1, 0, 0),new Point3D(0, 0, 1) );
    //back    //front
    super.faces[4]=new Face( 3, Color.orange, new Point3D(0, -1, 0),new Point3D(1, 0, 0),new Point3D(0, 0, 1) );

    super.faces[5]=new Face( 3, Color.cyan, new Point3D(0, -1, 0),new Point3D(0, 0, 1),new Point3D(-1, 0, 0));

    super.faces[6]=new Face( 3, Color.magenta, new Point3D(0, -1, 0),new Point3D(-1, 0, 0),new Point3D(0, 0, -1));

    super.faces[7]=new Face( 3, Color.white, new Point3D(0, -1, 0),new Point3D(0, 0, -1),new Point3D(1, 0, 0) );






  }



}
