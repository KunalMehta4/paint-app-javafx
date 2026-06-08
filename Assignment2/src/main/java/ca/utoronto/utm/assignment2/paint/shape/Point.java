package ca.utoronto.utm.assignment2.paint.shape;

import java.io.Serializable;

/**
 * A simple 2D point storing x and y coordinates.
 * Used by shapes for position, movement, and geometry calculations.
 */

public class Point implements Serializable {
        public double x, y; // Available to our package
        public Point(double x, double y){
                this.x=x; this.y=y;
        }
}
