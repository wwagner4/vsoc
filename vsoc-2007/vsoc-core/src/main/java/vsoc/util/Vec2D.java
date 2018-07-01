package vsoc.util;

import java.io.Serializable;

/**
 * A two dimensional vector.
 */

public class Vec2D implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private double x;

    private double y;

    private static final double DEG_TO_RAD = Math.PI / 180.0;

    private static final double RAD_TO_DEG = 180.0 / Math.PI;

    public static double degToRad(double x) {
        return x * DEG_TO_RAD;
    }

    public static double radToDeg(double x) {
        return x * RAD_TO_DEG;
    }

    public Vec2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2D(double angle) {
        this.x = Math.cos(angle);
        this.y = Math.sin(angle);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    /**
     * Returns a new vector this.x where this.x = this + v.
     */
    public Vec2D add(Vec2D v) {
        return new Vec2D(this.x + v.getX(), this.y + v.getY());
    }

    public Vec2D mul(double s) {
        return new Vec2D(this.x * s, this.y * s);
    }

    /**
     * Returns a new vector this.x where this.x = this - v.
     */
    public Vec2D sub(Vec2D v) {
        return new Vec2D(this.x - v.x, this.y - v.y);
    }

    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    /**
     * Two vectors are equal if their components this.x and y are equal.
     */
    public boolean equals(Object o) {
        if (o instanceof Vec2D) {
            Vec2D v = (Vec2D) o;
            return (this.x == v.getX()) && (this.y == v.getY());
        }
        return false;
    }

    /**
     * Returns a new vector that is rotated for angle
     */
    public Vec2D rot(double angle) {
        double cos = Math.cos(-angle);
        double sin = Math.sin(-angle);
        double x1 = this.x * cos - this.y * sin;
        double y1 = this.x * sin + this.y * cos;
        return new Vec2D(x1, y1);
    }

    /**
     * Rotates for angle
     */
    public Vec2D rotInternal(double angle) {
        double cos = Math.cos(-angle);
        double sin = Math.sin(-angle);
        double xh = this.x * cos - this.y * sin;
        this.y = this.x * sin + this.y * cos;
        this.x = xh;
        return this;
    }

    /**
     * Transforms a vector to a new coordinate system. ori vector defines the
     * new origin.
     */
    public Vec2D trans(Vec2D ori, double angle) {
        return sub(ori).rotInternal(angle);
    }

    /**
     * Creates a new negated vector. (1, 1) -> (-1, -1).
     */
    public Vec2D neg() {
        return new Vec2D(-this.x, -this.y);
    }

    /**
     * Creates a new unit vector from this.
     */
    public Vec2D unit() {
        double l = length();
        return new Vec2D(this.x / l, this.y / l);
    }

    /**
     * Returns true if this is in the sector +/- 45 deg from the this.x-axis.
     */
    public boolean isInSec() {
        return Math.abs(this.y) <= this.x;
    }

    /**
     * The angle between a vector and the this.x-axis
     */
    public double phi() {
        double re = 0.0;
        if (this.x < 0)
            re = Math.atan(this.y / this.x) + Math.PI;
        else if (this.x > 0)
            re = Math.atan(this.y / this.x);
        else if (this.y < 0) {
            re = -Math.PI / 2.0;
        } else if (this.y > 0) {
            re = Math.PI / 2.0;
        }
        return re;
    }

    public String toString() {
        return "(" + Math.round(this.x * 1000.0) / 1000.0 + ","
                + Math.round(this.y * 1000.0) / 1000.0 + ")";
    }

    public boolean isNorthOfHorizontalLine(double line) {
        return this.y > line;
    }

    public boolean isSouthOfHorizontalLine(double line) {
        return this.y < line;
    }

    public boolean isEastOfVerticalLine(double line) {
        return this.x > line;
    }

    public boolean isWestOfVerticalLine(double line) {
        return this.x < line;
    }

    public boolean isNorthOfPoint(Vec2D point) {
        return this.y > point.getY();
    }

    public boolean isSouthOfPoint(Vec2D point) {
        return this.y < point.getY();
    }

    public boolean isEastOfPoint(Vec2D point) {
        return this.x > point.getX();
    }

    public boolean isWestOfPoint(Vec2D point) {
        return this.x < point.getX();
    }

    public boolean isConnectionLineNorthOfPoint(Vec2D conn, Vec2D other) {
        if (this.isEastOfPoint(other)) {
            if (conn.isEastOfPoint(other))
                throw new IllegalArgumentException(
                        "Point and connection poit may not be on the same side\n"
                                + "point: " + this.toString() + " conn: "
                                + conn.toString());
            return isCLNPEastWest(conn, other);
        }
        if (conn.isWestOfPoint(other))
            throw new IllegalArgumentException(
                    "Point and connection poit may not be on the same side\n"
                            + "point: " + this.toString() + " conn: "
                            + conn.toString());
        return isCLNPEastWest(conn, other);
    }

    private boolean isCLNPEastWest(Vec2D conn, Vec2D other) {
        if (this.isNorthOfPoint(other)) {
            if (conn.isNorthOfPoint(other))
                return true;
            return this.isCLNPDetailed(conn, other);
        }
        if (conn.isSouthOfPoint(other))
            return false;
        return this.isCLNPDetailed(conn, other);
    }

    private boolean isCLNPDetailed(Vec2D conn, Vec2D other) {
        double x1 = this.x - other.getX();
        double y1 = this.y - other.getY();
        double x2 = conn.getX() - other.getX();
        double y2 = conn.getY() - other.getY();
        return (y1 / x1) > (y2 / x2);
    }

    public boolean isConnectionLineSouthOfPoint(Vec2D connectionPoint,
            Vec2D otherPoint) {
        return !isConnectionLineNorthOfPoint(connectionPoint, otherPoint);
    }
}