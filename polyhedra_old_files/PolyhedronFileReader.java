import java.awt.Color;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;



public class PolyhedronFileReader {

  private Path fFilePath;
  private Scanner scanner;


  private int number_of_vertices;
  private int number_of_faces;

  private static Color[] colors;



  public PolyhedronFileReader(String aFileName, int number_of_vertices, int number_of_faces){
    fFilePath = Paths.get(aFileName);
    try{
      scanner =  new Scanner(fFilePath);
    } catch(IOException ex){

    }
    this.number_of_vertices = number_of_vertices;
    this.number_of_faces = number_of_faces;

    colors = new Color[10];
    colors[0] = Color.BLUE;
    colors[1] = Color.CYAN;
    colors[2] = Color.GRAY;
    colors[3] = Color.GREEN;
    colors[4] = Color.MAGENTA;
    colors[5] = Color.ORANGE;
    colors[6] = Color.WHITE;
    colors[7] = Color.RED;
    colors[8] = Color.LIGHT_GRAY;
    colors[9] = Color.YELLOW;
  }

  public double parse_decimal(String next){

   if( next.contains("E") )
      return 0;
   return Double.parseDouble(next);
  }

  public Face[] generate_faces_from_file_data(){
    Face[] faces = new Face[ number_of_faces ];
    String n;
    for(int i=0; i<5; i++)
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
      faces[ i ] = new Face( number_of_face_vertices, colors[ color_number ], face_vertices );
    }
    return faces;
  }

}
