/*
Utility class responsible for offering methods pertaining to vectors.
*/

public class Vector{


  public static double dotproduct(double[] v1, double[] v2) {
      double dot = 0;
      for(int i=0; i<v1.length; i++){
        dot += v1[i]*v2[i];
      }
      return dot;
  }

  public static double[] get_cartesian_from_homogeneous(double[] homogeneous_v){
    double[] cartesian_v = new double[homogeneous_v.length-1];
    for(int i=0; i<cartesian_v.length; i++)
      cartesian_v[i] = homogeneous_v[i]/homogeneous_v[homogeneous_v.length-1];

    return cartesian_v;
  }

  public static Point3D get_Point3D_from_homogeneous(double[] homogeneous_v){
    Point3D cartesian_point = new double[3];
    cartesian_point.x = homogeneous_v[0]/homogeneous_v[3];
    cartesian_point.y = homogeneous_v[1]/homogeneous_v[3];
    cartesian_point.z = homogeneous_v[2]/homogeneous_v[3];

    return cartesian_point;
  }
  /*
  This method is specific to 3 dimensional vectors and should be generalized to work for
  vectors of any size.
  */
  public static double[] crossproduct(double[] v1, double[] v2) {
      double[] cross = {v1[1]*v2[2]-v1[2]*v2[1], v1[2]*v2[0]-v1[0]*v2[2] , v1[0]*v2[1]-v1[1]*v2[0]};

      return cross;
  }

  public static double[] subtract(double[] v1, double[] v2){
    double[] v = new double[v1.length];
    for(int i=0; i<v1.length; i++)
      v[i] = v1[i]-v2[i];

    return v;
  }

  public static double[] scale(double scalar, double[] v){
    double[] v_scaled = new double[v.length];
    for(int i=0; i<v.length; i++)
      v_scaled[i] = scalar*v[i];

    return v_scaled;
  }

  public static double[] normalize(double[] v) {
      double[] normalized = new double[v.length];
      double vector_length = get_norm(v);
      for(int i=0; i<v.length; i++)
        normalized[i] = v[i]/vector_length;

      return normalized;
  }


  public static double get_norm(double ... a){
    double norm_squared = 0;
    for(double e : a){
      norm_squared += e*e;
    }
    return Math.sqrt(norm_squared);
  }



  //applies a linear transformation on vector
  public static double[] apply_transform(double[] v, double[][] M) {
      int m = M.length; //number of rows of M
      int n = M[0].length; //number of columns -- should be equal to length of v
      double[] v2 = new double[v.length];

      for (int i = 0; i < m; i++)
          for (int j = 0; j < n; j++)
              v2[i] += M[i][j] * v[j];

      return v2;
  }




}
