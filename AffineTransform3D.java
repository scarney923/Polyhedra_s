
/*
Utility class that offers methods pertaining to 3D transformation matrices. Since we want
to be able to translate the cube at some point, we use homogeneous coordinates.
*/
public class AffineTransform3D {

    /*
    Currently not in use
    */
    public static double[][] get_scale_transform(double sx, double sy, double sz) {
      double[][] M = {
        { sx, 0, 0, 0 },
        { 0, sy, 0, 0 },
        { 0, 0, sz, 0 },
        { 0, 0, 0, 1 } };

      return M;
    }

    /*
    Currently not in use
    */
    public static double[][] get_translate_transform(int tx, int ty, int tz) {
      double[][] M = {
        { 1, 0, 0, tx },
        { 0, 1, 0, ty },
        { 0, 0, 1, tz },
        { 0, 0, 0, 1 } };

      return M;
    }


    /*
    Transformation matrix for rotating around z-axis
    */
    public static double[][] get_rotation_transform_zaxis(double theta){
      double[][] M = {
        { Math.cos(theta), -Math.sin(theta), 0, 0 },
        { Math.sin(theta), Math.cos(theta), 0, 0 },
        { 0, 0, 1, 0 },
        { 0, 0, 0, 1 } };

      return M;
    }

    /*
    Transformation matrix for rotating around y-axis
    */
    public static double[][] get_rotation_transform_yaxis(double theta){
      double[][] M = {
        { Math.cos(theta), 0, Math.sin(theta), 0 },
        { 0, 1, 0, 0 },
        { -Math.sin(theta), 0, Math.cos(theta), 0 },
        { 0, 0, 0, 1 } };

      return M;
    }

    /*
    Transformation matrix for rotating around x-axis
    */
    public static double[][] get_rotation_transform_xaxis(double theta){
      double[][] M = {
        { 1, 0, 0, 0 },
        { 0, Math.cos(theta), -Math.sin(theta), 0 },
        { 0, Math.sin(theta), Math.cos(theta), 0 },
        { 0, 0, 0, 1 } };

      return M;
    }
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
      double[] v = Vector.normalize(axis);
      double c = Math.cos(theta);
      double s = Math.sin(theta);
      double ax = v[0]; double ay = v[1]; double az = v[2];

      double[][] M = { { ( c+(1-c)*ax*ax ), ( (1-c)*ax*ay-s*az ), ( (1-c)*ax*az + s*ay ) },
        { (1-c)*ax*ay + s*az, c+(1-c)*ay*ay, (1-c)*ay*az - s*ax },
        { (1-c)*ax*az - s*ay, (1-c)*ay*az + s*ax, c + (1-c)*az*az } };

      return M;
    }


    /*
    Not in use. It turns out that it is not appropriate to use this transformation on (and alter the state of)
    the cube, but rather obtain the projected coordinates as temporary values within the drawing functions.
    */
    public static double[][] get_perspective_transform(double e) {


      double[][] M = {
        { 1, 0, 0, 0 },
        { 0, 1, 0, 0 },
        { 0, 0, 1, 0 },
        { 0, 0, -(1/e), 1 } };

      return M;
    }

}
