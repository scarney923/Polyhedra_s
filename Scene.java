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
    this.point_light_source_position = new double[] {0.0,0.0,1000.0};
    set_visiblity_flags();
    set_projection();


  }
  /*
  TODO: do we need to make light source in the exact same position as camera?
  */
  public void set_shadows(){
    boolean isTransformed = false;
    double thetaY = 0;
    double thetaX = 0;


    if(point_light_source_position[0] != 0 && point_light_source_position[1] != 0){
      double[] unitLightSource = Vector.normalize(point_light_source_position);
      double[] zAxis = {0, 0, 1};
      thetaY = Math.toRadians(Math.acos(Vector.dotproduct(point_light_source_position, zAxis)/Vector.get_norm(point_light_source_position)));
      double[] tempLightLoc0 = {point_light_source_position[0], 0, point_light_source_position[2]};
      thetaX =  Math.toRadians(Math.acos(Vector.dotproduct(tempLightLoc0, zAxis)/Vector.get_norm(tempLightLoc0)));
      double[][] M = AffineTransform3D.multiply(AffineTransform3D.get_rotation_transform_yaxis(thetaY), AffineTransform3D.get_rotation_transform_xaxis(thetaX));
      polyhedron.transform( M );
      Vector.apply_transform(point_light_source_position, M);
      for(int i =0; i< 3; i++)
        System.out.println(point_light_source_position[i]);

      double[] tempLightLoc1 = {0, 0, point_light_source_position[2]};
      isTransformed = true;
    }
    //step 2: set visiblity flags (should already be set)

    Arrays.sort(polyhedron.faces);
    set_visiblity_flags();
    for ( int i = polyhedron.number_of_faces-1; i > -1; i-- )
      if ( polyhedron.faces[i].is_visible ){
      //TODO change for loop to ascending
        for( int j = 0; j < i; j++ ){

          Point3D Q = polyhedron.faces[j].vertices[0];
          double[] Q_v = Q.get_vector_form();
          double[] n = polyhedron.faces[j].normal;
          double[] L = point_light_source_position;
          double A = Vector.dotproduct(n, L);
          double B = Vector.dotproduct(n, Q_v);
          Point3D[] shadows_vertices = new Point3D[ polyhedron.faces[i].number_of_vertices ];

          double[][] M = { { L[0]*n[0] - A + B, L[0]*n[1],         L[0]*n[2],         (-1)*L[0]*B },
                           { L[1]*n[0],         L[1]*n[1] - A + B, L[1]*n[2],         (-1)*L[1]*B },
                           { L[2]*n[0],         L[2]*n[1],         L[2]*n[2] - A + B, (-1)*L[2]*B },
                           { n[0],              n[1],              n[2],               -A         } };


          //for( Point3D vertex : polyhedron.faces[i].vertices){
          for(int k = 0; k < shadows_vertices.length; k++){
            Point3D vertex = polyhedron.faces[i].vertices[k];
            double[] shadow_vertice_homogeneous = ( Vector.apply_transform(vertex.get_homogeneous_vector_form(), M) );
            shadows_vertices[ k ] = Vector.get_Point3D_from_homogeneous(shadow_vertice_homogeneous);

          }

          int[] shadow_projection_xcoords = new int[ shadows_vertices.length ];
          int[] shadow_projection_ycoords = new int[ shadows_vertices.length ];


          for( int p=0; p<shadows_vertices.length; p++ ){
            shadow_projection_xcoords[p] = (int)( 100*shadows_vertices[p].x/( 1-(shadows_vertices[p].z/camera_position[2]) ) );
            shadow_projection_ycoords[p] = (int)( 100*shadows_vertices[p].y/( 1-(shadows_vertices[p].z/camera_position[2]) ) );
          }

          Area shadow_area = new Area( new Polygon( shadow_projection_xcoords, shadow_projection_ycoords, shadows_vertices.length ) );
          Area face_area = new Area( new Polygon( polyhedron.faces[j].x_coords_projected, polyhedron.faces[j].y_coords_projected, polyhedron.faces[j].number_of_vertices ) );
          face_area.intersect(shadow_area);
          polyhedron.faces[j].shadow = shadow_area;

        }

          if(isTransformed){
          thetaX = Math.toRadians(-1*thetaX);
          polyhedron.transform( AffineTransform3D.get_rotation_transform_xaxis(thetaX));
          thetaY = Math.toRadians(-1*thetaY);
          polyhedron.transform( AffineTransform3D.get_rotation_transform_xaxis(thetaY));
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
      double[] unit_point_to_light_source_vector = Vector.normalize( Vector.subtract(point_light_source_position, point_coordinates) );
      double[] unit_point_to_camera_vector = Vector.normalize( Vector.subtract(camera_position, point_coordinates) );

      double[] unit_normal = Vector.normalize(normal);
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

    //normals are set in set_visiblity_flags. should it be done separately?
    public void set_normals(){
      return;
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
