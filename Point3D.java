/*
Stores the coordinates of a 3D point.
A Face object contains four such objects.
*/

public class Point3D{
  double x;
  double y;
  double z;
  double w;

  public Point3D(double x, double y, double z){
    this.x=x;
    this.y=y;
    this.z=z;
    this.w=1;
  }

  public Point3D(double[] coords){
    this.x=coords[0];
    this.y=coords[1];
    this.z=coords[2];
    this.w=1;
  }

  public void to_cartesian(){
    x=x/w;
    y=y/w;
    z=z/w;
    w=1;
  }
  public double[] to_vector(){
    double[] v = new double[4];
    v[0] = x;
    v[1] = y;
    v[2] = z;
    v[3] = w;
    return v;
  }

  public void apply_transform(double[][] M) {
      double[] v0 = this.to_vector();
      double[] v = Vector.apply_transform( v0 , M );
      x = v[0];
      y = v[1];
      z = v[2];
      w = v[3];
      to_cartesian();
  }

  public String toString(){
    return "x: " + this.x + " y: " + this.y + " z: " + this.z + " w: " + this.w;
  }


}
