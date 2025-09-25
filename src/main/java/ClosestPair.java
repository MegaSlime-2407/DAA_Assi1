public class ClosestPair {
    
    public static class Point {
        public double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }
    
    public static class Result {
        public Point p1, p2;
        public double distance;
        public Result(Point p1, Point p2, double distance) {
            this.p1 = p1; this.p2 = p2; this.distance = distance;
        }
    }
    
    public static Result findClosestPair(Point[] points) {
        if (points == null || points.length < 2) throw new IllegalArgumentException("Need at least 2 points");
        Point[] px = points.clone();
        Point[] py = points.clone();
        sortByX(px);
        sortByY(py);
        return closestPair(px, py, 0, px.length - 1);
    }
    
    private static Result closestPair(Point[] px, Point[] py, int left, int right) {
        int n = right - left + 1;
        if (n <= 3) return bruteForce(px, left, right);
        
        int mid = (left + right) / 2;
        Point midPoint = px[mid];
        
        Point[] pyl = new Point[mid - left + 1];
        Point[] pyr = new Point[right - mid];
        int li = 0, ri = 0;
        for (Point p : py) {
            if (p != null && p.x <= midPoint.x && li < pyl.length) pyl[li++] = p;
            else if (p != null && ri < pyr.length) pyr[ri++] = p;
        }
        
        Result dl = closestPair(px, pyl, left, mid);
        Result dr = closestPair(px, pyr, mid + 1, right);
        Result d = dl.distance < dr.distance ? dl : dr;
        
        Point[] strip = new Point[n];
        int j = 0;
        for (Point p : py) {
            if (p != null && Math.abs(p.x - midPoint.x) < d.distance) strip[j++] = p;
        }
        
        return stripClosest(strip, j, d);
    }
    
    private static Result bruteForce(Point[] points, int left, int right) {
        double min = Double.MAX_VALUE;
        Point p1 = null, p2 = null;
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                double dist = distance(points[i], points[j]);
                if (dist < min) { min = dist; p1 = points[i]; p2 = points[j]; }
            }
        }
        return new Result(p1, p2, min);
    }
    
    private static Result stripClosest(Point[] strip, int size, Result d) {
        double min = d.distance;
        Point p1 = d.p1, p2 = d.p2;
        
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size && (strip[j].y - strip[i].y) < min; j++) {
                double dist = distance(strip[i], strip[j]);
                if (dist < min) { min = dist; p1 = strip[i]; p2 = strip[j]; }
            }
        }
        return new Result(p1, p2, min);
    }
    
    private static double distance(Point p1, Point p2) {
        double dx = p1.x - p2.x, dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    private static void sortByX(Point[] points) {
        for (int i = 1; i < points.length; i++) {
            Point key = points[i];
            int j = i - 1;
            while (j >= 0 && points[j].x > key.x) { points[j + 1] = points[j]; j--; }
            points[j + 1] = key;
        }
    }
    
    private static void sortByY(Point[] points) {
        for (int i = 1; i < points.length; i++) {
            Point key = points[i];
            int j = i - 1;
            while (j >= 0 && points[j].y > key.y) { points[j + 1] = points[j]; j--; }
            points[j + 1] = key;
        }
    }
}
