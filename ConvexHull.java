import java.util.*;

class ConvexHull {
     
    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    // 0 --> p, q and r are colinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    public static int orientation(Point3D p, Point3D q, Point3D r)
    {
        int val = (int)((q.y - p.y) * (r.x - q.x) -
                  (q.x - p.x) * (r.y - q.y));
      
        if (val == 0) return 0;  // collinear
        return (val > 0)? 1: 2; // clock or counterclock wise
    }
     
    // Prints convex hull of a set of n points.
    public static ArrayList<Point3D> convexHull(ArrayList<Point3D> points, int n)
    {
        
        // There must be at least 3 points
        if (n < 3) return points;
      
        // Initialize Result
        ArrayList<Point3D> hull = new ArrayList<Point3D>();
      
        // Find the leftmost point
        int l = 0;
        for (int i = 1; i < n; i++)
            if (points.get(i).x < points.get(l).x)
                l = i;
      
        // Start from leftmost point, keep moving 
        // counterclockwise until reach the start point
        // again. This loop runs O(h) times where h is
        // number of points in result or output.
        int p = l, q;
        do
        {
            // Add current point to result
            hull.add(points.get(p));
      
            // Search for a point 'q' such that 
            // orientation(p, x, q) is counterclockwise 
            // for all points 'x'. The idea is to keep 
            // track of last visited most counterclock-
            // wise point in q. If any point 'i' is more 
            // counterclock-wise than q, then update q.
            q = (p + 1) % n;
             
            for (int i = 0; i < n; i++)
            {
               // If i is more counterclockwise than 
               // current q, then update q
               if (orientation(points.get(p),points.get(i), points.get(q))
                                                   == 2)
                   q = i;
            }
      
            // Now q is the most counterclockwise with
            // respect to p. Set p as q for next iteration, 
            // so that q is added to result 'hull'
            p = q;
      
        } while (p != l);  // While we don't come to first 
                           // point
      
        // Print Result
        return hull; 
  }
}
     