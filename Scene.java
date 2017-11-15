import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class Scene {

  Polyhedron polyhedron;
  double[] camera_position;
  double[] point_light_source_position;

  public Scene(){
    this.polyhedron = new Polyhedron("./polyhedra_data/Cube.txt");;
    this.camera_position = new double[] {0.0,0.0,10.0};
    this.point_light_source_position = new double[] {0.0,500.0,1000.0};
    set_visiblity_flags();
    set_projection();


  }
  /*
  TODO: do we need to make light source in the exact same position as camera?
  */
  public void set_unrestricted_shadows(){
    boolean space_transformed = false;

    double[] L = point_light_source_position;
    double[] a = Vector.get_normalized(point_light_source_position);
    double g = Math.sqrt( Math.pow(a[1], 2) + Math.pow(a[2], 2) );

    double[][] Mx =   {   {1,     0,       0},
                          {0,     a[2]/g, -a[1]/g},
                          {0,     a[1]/g,  a[2]/g} };

    double[][] Mx_inv = { {1,     0,       0},
                          {0,     a[2]/g,  a[1]/g},
                          {0,    -a[1]/g,  a[2]/g} };

    double[][] My = {     {g,     0,      -a[0]},
                          {0,     1,       0},
                          {a[0],  0,       g} };


    double[][] My_inv = { {g,     0,       a[0]},
                          {0,     1,       0},
                          {-a[0], 0,       g} };

    double[][] Mrot = AffineTransform3D.multiply(My, Mx);

    double[][] Mrot_inv = AffineTransform3D.multiply(Mx_inv, My_inv);





    if( point_light_source_position[0] != 0 || point_light_source_position[1] != 0 ){
      L = Vector.apply_transform(point_light_source_position, Mrot);
      polyhedron.transform( Mrot );
      space_transformed = true;
    }




    for ( int i = polyhedron.number_of_faces-1; i > -1; i-- ){
      if ( polyhedron.faces[i].is_visible ){
        for( int j = 0; j < i; j++ ){
          if ( polyhedron.faces[j].is_visible ){



            double[] Q = polyhedron.faces[j].vertices[0].get_vector_form();

            double[] v0 = { polyhedron.faces[j].vertices[1].x-polyhedron.faces[j].vertices[0].x, polyhedron.faces[j].vertices[1].y-polyhedron.faces[j].vertices[0].y, polyhedron.faces[j].vertices[1].z-polyhedron.faces[j].vertices[0].z };
            double[] v1 = { polyhedron.faces[j].vertices[2].x-polyhedron.faces[j].vertices[0].x, polyhedron.faces[j].vertices[2].y-polyhedron.faces[j].vertices[0].y, polyhedron.faces[j].vertices[2].z-polyhedron.faces[j].vertices[0].z };
            double[] n = Vector.crossproduct(v0,v1);

            double A = Vector.dotproduct(n, L);
            double B = Vector.dotproduct(n, Q);

            double[][] M = { { L[0]*n[0] - A + B, L[0]*n[1],         L[0]*n[2],         (-1)*L[0]*B },
                             { L[1]*n[0],         L[1]*n[1] - A + B, L[1]*n[2],         (-1)*L[1]*B },
                             { L[2]*n[0],         L[2]*n[1],         L[2]*n[2] - A + B, (-1)*L[2]*B },
                             { n[0],              n[1],              n[2],               -A         } };

            Point3D[] shadows_vertices = new Point3D[ polyhedron.faces[i].number_of_vertices ];

            for(int k = 0; k < shadows_vertices.length; k++){
              Point3D vertex = polyhedron.faces[i].vertices[k];
              double[] shadow_vertice_homogeneous = ( Vector.apply_transform(vertex.get_homogeneous_vector_form(), M) );

              shadows_vertices[ k ] = Vector.get_Point3D ( Vector.apply_transform( Vector.get_cartesian_from_homogeneous ( shadow_vertice_homogeneous ), Mrot_inv ) );

            }
            polyhedron.faces[j].shadows_unrestricted.add( shadows_vertices );



          }
        }
      }


    }
    if(space_transformed)
      polyhedron.transform(Mrot_inv);


  }

  public set_shadows(){
    for( Face face : polyhedron.faces ){
      face.shadow = new Area();
      for( Point3D shadow_unrestricted : face.shadows_unrestricted ){
        int[] shadow_unrestricted_projection_xcoords = new int[ shadow_unrestricted.length ];
        int[] shadow_unrestricted_projection_ycoords = new int[ shadow_unrestricted.length ];


        for( int p=0; p<shadow_unrestricted.length; p++ ){
          shadow_unrestricted_projection_xcoords[p] = (int)( 100*shadow_unrestricted[p].x/( 1-(shadow_unrestricted[p].z/camera_position[2]) ) );
          shadow_unrestricted_projection_xcoords[p] = (int)( 100*shadow_unrestricted[p].y/( 1-(shadow_unrestricted[p].z/camera_position[2]) ) );
        }

        Area shadow_area = new Area( new Polygon( shadow_projection_xcoords, shadow_projection_ycoords, shadows_vertices.length ) );
        Area face_area = new Area( new Polygon( face.x_coords_projected, face.y_coords_projected, face.number_of_vertices ) );
        shadow_area.intersect(face_area);
        face.shadow.add(shadow_area);
      }

    }



}



    /*
    Given the x, y coordinates of a point within the sphere of known radius,
    the z coordinate can be calculated. With the all the coordinates known,
    the brightness of given point is calculated.
    TODO: add specular light
    */
    public double get_point_brightness(double[] point_coordinates, double[] normal){
      double[] unit_point_to_light_source_vector = Vector.get_normalized( Vector.subtract(point_light_source_position, point_coordinates) );
      double[] unit_point_to_camera_vector = Vector.get_normalized( Vector.subtract(camera_position, point_coordinates) );

      double[] unit_normal = Vector.get_normalized(normal);
      double[] w = Vector.scale( Vector.dotproduct(unit_point_to_light_source_vector, unit_normal), unit_normal );
      double[] reflected_vector = Vector.subtract( Vector.scale(2, w), unit_point_to_light_source_vector );
      //
      double brightness =  ( 35 ) + ( 145 * eliminate_negatives(Vector.dotproduct(unit_point_to_light_source_vector, unit_normal)) ) + ( Math.pow( Vector.dotproduct( reflected_vector, unit_point_to_camera_vector ), 20 ) );
      return brightness;
    }

    private double eliminate_negatives(double value){
      if(value > 0)
        return value;
      else
        return 0;
    }



    public void set_visiblity_flags(){
      Arrays.sort(polyhedron.faces);
      for( Face face : polyhedron.faces ){
        face.is_visible = false;

        //choose a point on the face
        double[] point = { face.vertices[0].x, face.vertices[0].y, face.vertices[0].z};
        //get two vectors in the plane of the face
        double[] v0 = { face.vertices[1].x-face.vertices[0].x, face.vertices[1].y-face.vertices[0].y, face.vertices[1].z-face.vertices[0].z };
        double[] v1 = { face.vertices[2].x-face.vertices[0].x, face.vertices[2].y-face.vertices[0].y, face.vertices[2].z-face.vertices[0].z };
        face.normal = Vector.crossproduct(v0,v1);
        /*cross v0, v1 and dot resulting vector with vector from point to camera position.
        If the result is greater than zero, then the angle between the vectors must be in the
        range <90 and >-90 and so the face is visible.
        */
        if( Vector.dotproduct( face.normal, Vector.subtract(camera_position, point) ) > 0 )
          face.is_visible = true;

      }
    }

    public void clean_shadows(){
      for( Face face : polyhedron.faces ){
        face.shadow_unrestricted = new ArrayList<Point3D[]>;
      }
    }

    public void set_projection(){
      int[] xpoints;
      int[] ypoints;

      for( Face face : polyhedron.faces ){
        xpoints = new int[ face.number_of_vertices ];
        ypoints = new int[ face.number_of_vertices ];


        for( int i=0; i<face.number_of_vertices; i++ ){
          Point3D p = face.vertices[i];

          xpoints[i] = (int)( 100*p.x/( 1-(p.z/camera_position[2]) ) );
          ypoints[i] = (int)( 100*p.y/( 1-(p.z/camera_position[2]) ) );
        }
        face.x_coords_projected = xpoints;
        face.y_coords_projected = ypoints;

      }


    }







}
