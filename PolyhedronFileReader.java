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
  private int number_of_face_vertices;

  private static Color[] colors;



  public PolyhedronFileReader(String aFileName, int number_of_vertices, int number_of_faces, int number_of_face_vertices, boolean isVariable){
    fFilePath = Paths.get(aFileName);
    try{
      scanner =  new Scanner(fFilePath);
    } catch(IOException ex){

    }
    this.number_of_vertices = number_of_vertices;
    this.number_of_faces = number_of_faces;
    this.number_of_face_vertices = number_of_face_vertices;

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


  public Face[] generate_faces_from_file_data() {
    Face[] faces = new Face[ number_of_faces ];
    String n;
    for(int i=0; i<5; i++)
      System.out.println(scanner.nextLine());

    double[][] vertices = new double[number_of_vertices][3];

    double[] vertex;
    double x;
    double y;
    double z;

    for(int i=0; i<number_of_vertices; i++ ){
      x = parse(scanner.next()); 
      y = parse(scanner.next()); 
      z = parse(scanner.next()); 
      vertex = new double[] {x, y, z};
      System.out.println("X, Y, Z: " + x + y + z); 
      vertices[i] = vertex;
      scanner.nextLine();

    }

    System.out.println(scanner.nextLine());

    //    super.faces[0]=new Face( 3, Color.blue, new Point3D(0, 1, 0),new Point3D(0, 0, 1),new Point3D(1, 0, 0) );

    int which_vertice;
    int color_number;
    Point3D[] face_vertices;

    for(int i=0; i<number_of_faces; i++ ){
      face_vertices = new Point3D[ number_of_face_vertices ];

      //number_of_face_vertices nextInt
      for(int j=0; j<number_of_face_vertices; j++){
        which_vertice = scanner.nextInt();
        face_vertices[j] = new Point3D( vertices[ which_vertice ] );
      }
      color_number = scanner.nextInt(); //
      faces[i] = new Face( number_of_face_vertices, colors[ color_number ], face_vertices );
      scanner.nextLine();
    }

    return faces;
  }
  
  public double parse(String next){
  
   if(next.contains("E")){
      return 0; 
   }
   else{
      return Double.parseDouble(next); 
   
   }
  }

  public Face[] snubCube(){
    Face[] faces = new Face[ number_of_faces ];
    String n;
    for(int i=0; i<5; i++)
      System.out.println(scanner.nextLine());

    double[][] vertices = new double[number_of_vertices][];

    double[] vertex;
    double x;
    double y;
    double z;

    // for(int i=0; i<number_of_vertices; i++ ){
//       String all = scanner.nextLine(); 
//       String[] split = all.split(""); 
//       int numOfVertices = split
//       x = parse(split[0]);
//       y = parse(split[1]); 
//       z = parse(split[2]);  
//     }
    
     for(int i=0; i<number_of_vertices; i++ ){
      x = parse(scanner.next()); 
      y = parse(scanner.next()); 
      z = parse(scanner.next()); 
      vertex = new double[] {x, y, z};
      System.out.println("X, Y, Z: " + x + y + z); 
      vertices[i] = vertex;
      scanner.nextLine();
    }

    scanner.nextLine();

    //    super.faces[0]=new Face( 3, Color.blue, new Point3D(0, 1, 0),new Point3D(0, 0, 1),new Point3D(1, 0, 0) );

    int which_vertice;
    int color_number;
    Point3D[] face_vertices;

    for(int i=0; i<number_of_faces; i++ ){
      //face_vertices = new Point3D[ number_of_face_vertices ];
      //number_of_face_vertices nextInt        
        String str = scanner.nextLine();
        String[] ind = str.split(" "); 
        int numOfVertsPerFace = ind.length-1;
        
        face_vertices = new Point3D[numOfVertsPerFace]; 
        
        for(int k = 0; k<numOfVertsPerFace; k++){
           which_vertice = (int)(parse(ind[k])); 
           face_vertices[k] = new Point3D( vertices[which_vertice]);
        }
      color_number = (int)parse(ind[numOfVertsPerFace]); //
      faces[i] = new Face( numOfVertsPerFace, colors[ color_number ], face_vertices );
      //scanner.nextLine();
    }
    return faces; 
  }

}
