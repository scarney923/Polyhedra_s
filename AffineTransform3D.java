
/*
Utility class that offers methods pertaining to 3D transformation matrices.
*/
public class AffineTransform3D {


    /*
    To obtain the transformation matrix of an arbitrary axis, we "transform the space"
    such as to make the axis align with the z-axis. Then we simply use the standard transformation
    matrix for rotating around the z-axis, and finally transform the space back.
    While the resulting matrix is really a result of five matrices that have been multiplied
    together(*) below is the condenced form, using which saves us from carrying out the matrix
    multiplications ourselves.

    (*) Mfinal = MxInv MyInv Mz My Mx

    */
    public static double[][] get_rotation_transform_arb(double[] axis, double theta) {
      double[] v = Vector.get_normalized(axis);
      double c = Math.cos(theta);
      double s = Math.sin(theta);
      double ax = v[0]; double ay = v[1]; double az = v[2];

      double[][] M = { { ( c+(1-c)*ax*ax ), ( (1-c)*ax*ay-s*az ), ( (1-c)*ax*az + s*ay ) },
        { (1-c)*ax*ay + s*az, c+(1-c)*ay*ay, (1-c)*ay*az - s*ax },
        { (1-c)*ax*az - s*ay, (1-c)*ay*az + s*ax, c + (1-c)*az*az } };

      return M;
    }

}
