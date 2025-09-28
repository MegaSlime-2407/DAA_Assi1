import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

public class ClosestPairTest {
    private static CsvMetricsWriter writer;
    
    static {
        try {
            writer = new CsvMetricsWriter("metrics/test-results.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void findsClosestPairBasic() throws IOException {
        ClosestPair.Point[] points = {
            new ClosestPair.Point(0, 0),
            new ClosestPair.Point(1, 1),
            new ClosestPair.Point(2, 2),
            new ClosestPair.Point(3, 3)
        };
        Metrics m = new Metrics();
        ClosestPair.Result result = ClosestPair.findClosestPair(points, m);
        assertEquals(Math.sqrt(2), result.distance, 1e-9);
        writer.append(m, "ClosestPair_Basic", points.length);
    }
    
    @Test
    void handlesTwoPoints() {
        ClosestPair.Point[] points = {
            new ClosestPair.Point(0, 0),
            new ClosestPair.Point(3, 4)
        };
        ClosestPair.Result result = ClosestPair.findClosestPair(points);
        assertEquals(5.0, result.distance, 1e-9);
    }
    
    @Test
    void handlesThreePoints() {
        ClosestPair.Point[] points = {
            new ClosestPair.Point(0, 0),
            new ClosestPair.Point(1, 0),
            new ClosestPair.Point(0, 1)
        };
        ClosestPair.Result result = ClosestPair.findClosestPair(points);
        assertEquals(1.0, result.distance, 1e-9);
    }
    
    @Test
    void handlesIdenticalPoints() {
        ClosestPair.Point[] points = {
            new ClosestPair.Point(1, 1),
            new ClosestPair.Point(1, 1),
            new ClosestPair.Point(2, 2)
        };
        ClosestPair.Result result = ClosestPair.findClosestPair(points);
        assertEquals(0.0, result.distance, 1e-9);
    }
    
    @Test
    void handlesVerticalLine() {
        ClosestPair.Point[] points = {
            new ClosestPair.Point(0, 0),
            new ClosestPair.Point(0, 1),
            new ClosestPair.Point(0, 2),
            new ClosestPair.Point(0, 3)
        };
        ClosestPair.Result result = ClosestPair.findClosestPair(points);
        assertEquals(1.0, result.distance, 1e-9);
    }
    
    @Test
    void handlesHorizontalLine() {
        ClosestPair.Point[] points = {
            new ClosestPair.Point(0, 0),
            new ClosestPair.Point(1, 0),
            new ClosestPair.Point(2, 0),
            new ClosestPair.Point(3, 0)
        };
        ClosestPair.Result result = ClosestPair.findClosestPair(points);
        assertEquals(1.0, result.distance, 1e-9);
    }
    
    @Test
    void handlesRandomPoints() {
        ClosestPair.Point[] points = {
            new ClosestPair.Point(2, 3),
            new ClosestPair.Point(12, 30),
            new ClosestPair.Point(40, 50),
            new ClosestPair.Point(5, 1),
            new ClosestPair.Point(12, 10),
            new ClosestPair.Point(3, 4)
        };
        ClosestPair.Result result = ClosestPair.findClosestPair(points);
        assertEquals(Math.sqrt(2), result.distance, 1e-9);
    }
    
    @Test
    void handlesLargeDataset() throws IOException {
        ClosestPair.Point[] points = new ClosestPair.Point[100];
        for (int i = 0; i < 100; i++) {
            points[i] = new ClosestPair.Point(i, i);
        }
        Metrics m = new Metrics();
        ClosestPair.Result result = ClosestPair.findClosestPair(points, m);
        assertEquals(Math.sqrt(2), result.distance, 1e-9);
        writer.append(m, "ClosestPair_Large", points.length);
    }
    
    @Test
    void performanceTest() throws IOException {
        int[] sizes = {10, 20, 50, 100};
        for (int size : sizes) {
            ClosestPair.Point[] points = new ClosestPair.Point[size];
            for (int i = 0; i < size; i++) {
                points[i] = new ClosestPair.Point(Math.random() * 100, Math.random() * 100);
            }
            Metrics m = new Metrics();
            ClosestPair.findClosestPair(points, m);
            writer.append(m, "ClosestPair_Perf_" + size, size);
        }
    }
    
    @Test
    void handlesNegativeCoordinates() {
        ClosestPair.Point[] points = {
            new ClosestPair.Point(-1, -1),
            new ClosestPair.Point(-2, -2),
            new ClosestPair.Point(0, 0),
            new ClosestPair.Point(1, 1)
        };
        ClosestPair.Result result = ClosestPair.findClosestPair(points);
        assertEquals(Math.sqrt(2), result.distance, 1e-9);
    }
    
    @Test
    void handlesDecimalCoordinates() {
        ClosestPair.Point[] points = {
            new ClosestPair.Point(0.5, 0.5),
            new ClosestPair.Point(1.5, 1.5),
            new ClosestPair.Point(2.5, 2.5),
            new ClosestPair.Point(3.5, 3.5)
        };
        ClosestPair.Result result = ClosestPair.findClosestPair(points);
        assertEquals(Math.sqrt(2), result.distance, 1e-9);
    }
    
    @Test
    void invalidInputThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            ClosestPair.findClosestPair(null);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            ClosestPair.Point[] single = {new ClosestPair.Point(0, 0)};
            ClosestPair.findClosestPair(single);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            ClosestPair.findClosestPair(new ClosestPair.Point[0]);
        });
    }
    
    @Test
    void originalArrayNotModified() {
        ClosestPair.Point[] original = {
            new ClosestPair.Point(2, 3),
            new ClosestPair.Point(12, 30),
            new ClosestPair.Point(40, 50),
            new ClosestPair.Point(5, 1)
        };
        ClosestPair.Point[] copy = original.clone();
        
        ClosestPair.findClosestPair(original);
        
        for (int i = 0; i < original.length; i++) {
            assertEquals(copy[i].x, original[i].x, 1e-9);
            assertEquals(copy[i].y, original[i].y, 1e-9);
        }
    }
}
