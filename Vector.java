/*
Utility class responsible for offering methods pertaining to vectors.
*/

public class Vector{


  /*returns the dot product of two vectors given in the form of double arrays*/
  public static double dotproduct(double[] v1, double[] v2) {
      double dot = 0;
      for(int i=0; i<v1.length; i++){
        dot += v1[i]*v2[i];
      }
      return dot;
  }

  /*converts from a homogeneous vector to a cartesian vector by dividing by the w component of the homogeneous vector*/
  public static double[] get_cartesian_components_of_homogeneous_vector(double[] homogeneous_v){
    double[] cartesian_v = new double[homogeneous_v.length-1];
    for(int i=0; i<cartesian_v.length; i++)
      cartesian_v[i] = homogeneous_v[i]/homogeneous_v[homogeneous_v.length-1];

    return cartesian_v;
  }

  /*converts a vector in the form of a double array to a Vertex object*/
  public static Vertex get_vertex(double[] v){
    Vertex cartesian_point = new Vertex(v[0],v[1],v[2]);
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

  /* subtracts one vector from the other and returns the resulting vector. Used in many of our calculations for shading and shadows
  to find the vector from a given point to a lightsource*/
  public static double[] subtract(double[] v1, double[] v2){
    double[] v = new double[v1.length];
    for(int i=0; i<v1.length; i++)
      v[i] = v1[i]-v2[i];

    return v;
  }

  /*scales each vertex by the given scalar multiple.*/
  public static double[] scale(double scalar, double[] v){
    double[] v_scaled = new double[v.length];
    for(int i=0; i<v.length; i++)
      v_scaled[i] = scalar*v[i];

    return v_scaled;
  }

  /*turns the given vector into a unit vector by dividing by the length returned by get_norm*/
  public static double[] get_normalized(double[] v) {
      double[] normalized = new double[v.length];
      double vector_length = get_norm(v);
      for(int i=0; i<v.length; i++)
        normalized[i] = v[i]/vector_length;

      return normalized;
  }

  /*finds the magnitude of the given vector*/
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
