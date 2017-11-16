/*
Stores the coordinates of a 3D point.
A Face object contains four such objects.
*/

public class Vertex  {
  double x;
  double y;
  double z;

  public Vertex(double x, double y, double z){
    this.x=x;
    this.y=y;
    this.z=z;
  }

  public Vertex(double[] coords){
    this.x=coords[0];
    this.y=coords[1];
    this.z=coords[2];
  }

  public double[] get_vector_form(){
    double[] v = new double[3];
    v[0] = x;
    v[1] = y;
    v[2] = z;
    return v;
  }

  public double[] get_homogeneous_vector_form(){
    double[] v = new double[4];
    v[0] = x;
    v[1] = y;
    v[2] = z;
    v[3] = 1;
    return v;
  }

  public void apply_transform(double[][] M) {
      double[] v0 = this.get_vector_form();
      double[] v = Vector.get_transformed_vector( v0 , M );
      x = v[0];
      y = v[1];
      z = v[2];
  }

  public String toString(){
    return "x: " + this.x + " y: " + this.y + " z: " + this.z;
  }



}
