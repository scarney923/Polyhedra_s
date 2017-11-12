import java.awt.Color;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;



public class PolyDataFileParser {

  private Path fFilePath;
  private Scanner scanner;




  public PolyDataFileParser(String aFileName){
    fFilePath = Paths.get(aFileName);
    try{
      scanner =  new Scanner(fFilePath);
    } catch(IOException ex){

    }

  }



  public String get_polyhedron_name(){
    return scanner.nextLine();
  }

  public int get_number_of_vertices(){
    scanner.nextLine();
    return scanner.nextInt();

  }

  public int get_number_of_faces(){
    scanner.nextLine();
    return scanner.nextInt();

  }

  public Face[] get_faces(int number_of_vertices, int number_of_faces){

    Face[] faces = new Face[ number_of_faces ];
    String n;
    for(int i=0; i<2; i++)
      scanner.nextLine();

    double[][] vertices = new double[number_of_vertices][3];

    double[] vertex;
    double x;
    double y;
    double z;

     for(int i=0; i<number_of_vertices; i++ ){
      x = parse_decimal(scanner.next());
      y = parse_decimal(scanner.next());
      z = parse_decimal(scanner.next());
      vertex = new double[] {x, y, z};
      vertices[i] = vertex;
      scanner.nextLine();
    }

    scanner.nextLine();
    int which_vertice;
    int color_number;
    Point3D[] face_vertices;

    for(int i = 0; i < number_of_faces; i++ ){
        String line = scanner.nextLine();
        String[] tokens = line.split(" ");
        int number_of_face_vertices = tokens.length-1; //the last number in line is color

        face_vertices = new Point3D[number_of_face_vertices];

        for(int k = 0; k < number_of_face_vertices; k++){
           which_vertice = Integer.parseInt( tokens[k] );
           face_vertices[ k ] = new Point3D( vertices[which_vertice]);
        }
      color_number = Integer.parseInt( tokens[ number_of_face_vertices ] ); //
      faces[ i ] = new Face( number_of_face_vertices, color_number, face_vertices );
    }
    return faces;
  }

  public double parse_decimal(String next){

   if( next.contains("E") )
      return 0;
   return Double.parseDouble(next);
  }

}
