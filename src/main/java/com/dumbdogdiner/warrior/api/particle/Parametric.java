package com.dumbdogdiner.warrior.api.particle;

/**
 * A parametric curve is one that takes in a parameter t, and returns
 * a point in 3D space given that parameter of t. 
 * 
 * e.g. (t,0,0) between t = 0 and t = 1 will produce a straight line from
 * (0,0,0) to (1,0,0).
 * 
 * e.g. (t,t,0) between t = 1 and t = 5 will produce a straight line from
 * (1,1,0) to (5,5,0).
 * 
 * e.g. (t, t squared, 0) between t = -5 and t = 5 will produce a parabola from
 * (-5, 25, 0), down to (0,0,0), and up again to (5, 25, 0).
 * 
 * Parametrics can draw nearly any curve possible, and should be used in cases where
 * curves are required.
 */
public interface Parametric {
    double x(double t);
    double y(double t);
    double z(double t);
}
