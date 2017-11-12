/*
Stores the coordinates of a 3D point.
A Face object contains four such objects.
*/

public class Point3D{
  double x;
  double y;
  double z;

  public Point3D(double x, double y, double z){
    this.x=x;
    this.y=y;
    this.z=z;
  }

  public Point3D(double[] coords){
    this.x=coords[0];
    this.y=coords[1];
    this.z=coords[2];
  }

  public double[] to_vector(){
    double[] v = new double[3];
    v[0] = x;
    v[1] = y;
    v[2] = z;
    return v;
  }

  public void apply_transform(double[][] M) {
      double[] v0 = this.to_vector();
      double[] v = Vector.apply_transform( v0 , M );
      x = v[0];
      y = v[1];
      z = v[2];
  }

  public String toString(){
    return "x: " + this.x + " y: " + this.y + " z: " + this.z;
  }


}
