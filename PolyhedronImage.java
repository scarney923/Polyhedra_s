

public class PolyhedronImage{
  BufferedImage image;
  ArrayList<Point2D.Double> polyhedron_points;
  Point3D[] point_light_source;


  public PolyhedronImage(canvas_width, canvas_height, ){
    image = new BufferedImage(canvas_width, canvas_height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = image.createGraphics();
    g2d.translate(getWidth()/2,getHeight()/2);
    g2d.scale(1,-1);


    this.canvas_width = canvas_width;
    this.canvas_height = canvas_height;
    sphere_points = new ArrayList<Point2D.Double>();
    this.sphere_radius = sphere_radius;
    generate_sphere_points();


  }

  public BufferedImage getBufferedImage(){
      return image;
    }
    /*
    Given a radius, the sphere points are calculated and added to the sphere_points arraylist.
    */
    public void generate_sphere_points(){
      for(int y = -sphere_radius; y<sphere_radius; y++){
        double x_positive = Math.sqrt( Math.pow(sphere_radius,2) - Math.pow(y,2) );
        double x_negative = (-1) * x_positive;
        for(double x = x_negative; x<x_positive; x++){
          sphere_points.add( new Point2D.Double( x, y ) );
        }
      }
    }

    /*
    Given the x, y coordinates of a point within the sphere of known radius,
    the z coordinate can be calculated. With the all the coordinates known,
    the brightness of given point is calculated.
    */
    public double calculate_light_brightness(double x, double y){
      double z = Math.sqrt( Math.pow(sphere_radius, 2) - Math.pow(x, 2) - Math.pow(y, 2) );
      double[] point_coordinates = {x,y,z};
      double[] point_to_light_source_vector = Vector.normalize( Vector.subtract(light_source_coordinates, point_coordinates) );
      double[] normal = Vector.normalize(point_coordinates);

      /*The dotproduct gives us the cosine between the point-to-light-source-vector
      and the normal (the vectors having been normalized before). If the cosine is small, then the (absolute value of the) angle is large between
      the two vectors so the light source has less affect on the point. If the cosine turns out to be negative, then the angle
      is >90 degress (or <-90 degrees), which means that the point is in the shadow. We set the diffuse light to zero and the point is only
      affected by ambient light. The diffuse light constant (accounting for the brightness of the light source) to 200. This means that
      the diffuse light is going to be within the range 0-200, with the ambient light added to it to give the final brightness.
      */
      double brightness = 10 + 200* eliminate_negatives( Vector.dotproduct(point_to_light_source_vector, normal) );
      return brightness;
    }

    public double eliminate_negatives(double value){
      if(value > 0)
        return value;
      else
        return 0;
    }
    /*
    Shades the sphere. I.e., for each pixel within sphere, the color is set depending on its position and the position of the lightsource.
    */
    public void shade_polyhedron( ){
      for( Face visible_face : visible_faces ){

        for( Point3D vertice : visible.vertices )
        double brightness = calculate_light_brightness( point.getX(), point.getY() );

        Color color = new Color((int)brightness, (int)brightness, (int)brightness);
        image.setRGB( (int)( point.getX()+canvas_width/2 ), (int)( point.getY()+canvas_height/2 ), color.getRGB() );
      }

    }

    public void draw_wireframe(Graphics2D g2d){
      g2d.setPaint( Color.white );

      for( Face face : faces ){
        double x1, y1, x2, y2;
        Point3D p1, p2;

          for( int i=0; i<number_of_face_vertices; i++ ){
            p1 = face.vertices[i];
            p2 = face.vertices[i+1];
            /*since any transformation might have distorted the coordinates in their
            Cartesian form, we convert them before projecting them */
            p1.to_cartesian();
            p2.to_cartesian();

            //project vertices onto 2D space. Scaling factor is set to 100
            x1 = 100*p1.x/( 1-(p1.z/7) );
            y1 = 100*p1.y/( 1-(p1.z/7) ) ;
            x2 = 100*p2.x/( 1-(p2.z/7) );
            y2 = 100*p2.y/( 1-(p2.z/7) );

            g2d.draw( new Line2D.Double(x1, y1, x2, y2) );

          }
        }
    }

    public void color_polyhedron(Graphics2D g2d){
      int[] xpoints = new int[ number_of_face_vertices ];
      int[] ypoints = new int[ number_of_face_vertices ];

      /*the camera position is on the z-axis. however, it's important that it lies
      outside of the cube, i.e. that it's z coordinate > 1 */
      double[] camera_position = {0.0,0.0,7.0};

      Arrays.sort(faces);
      for( Face face : faces ){
        boolean visible = false;

        //determine if face is visible

        //choose a point on the face
        double[] point = { face.vertices[0].x, face.vertices[0].y, face.vertices[0].z};
        //get two vectors in the plane of the face
        double[] v0 = { face.vertices[1].x-face.vertices[0].x, face.vertices[1].y-face.vertices[0].y, face.vertices[1].z-face.vertices[0].z };
        double[] v1 = { face.vertices[2].x-face.vertices[0].x, face.vertices[2].y-face.vertices[0].y, face.vertices[2].z-face.vertices[0].z };
        /*cross v0, v1 and dot resulting vector with vector from point to camera position.
        If the result is greater than zero, then the angle between the vectors must be in the
        range <90 and >-90 and so the face is visible.
        */
        if( Vector.dotproduct( Vector.crossproduct(v1,v0), Vector.subtract(camera_position, point) ) > 0 )
          visible = true;

        //only if the visible flag is set to true do we go on and paint the face
        if(visible){

          //project all vertices of given face onto 2D space. Scaling factor is set to 100
          for( int i=0; i<number_of_face_vertices; i++ ){
            Point3D p = face.vertices[i];

            p.to_cartesian();

            xpoints[i] = (int)( 100*p.x/( 1-(p.z/7) ) );
            ypoints[i] = (int)( 100*p.y/( 1-(p.z/7) ) );
          }
          g2d.setPaint( face.color );
          g2d.fill( new Polygon(xpoints, ypoints, number_of_face_vertices) );
        }
      }
    }
}
