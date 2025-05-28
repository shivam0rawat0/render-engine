package lib;

public class Transform {
    public double w, x, y, z;

    // Constructor
    public Transform(double w, double x, double y, double z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Create a quaternion from an axis and angle
    public static Transform fromAxisAngle(double[] axis, double angle) {
        double halfAngle = angle / 2;
        double sinHalfAngle = Math.sin(halfAngle);
        return new Transform(
                Math.cos(halfAngle),
                axis[0] * sinHalfAngle,
                axis[1] * sinHalfAngle,
                axis[2] * sinHalfAngle);
    }

    // Multiply two quaternions
    public static Transform multiply(Transform q1, Transform q2) {
        return new Transform(
                q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z,
                q1.w * q2.x + q1.x * q2.w + q1.y * q2.z - q1.z * q2.y,
                q1.w * q2.y - q1.x * q2.z + q1.y * q2.w + q1.z * q2.x,
                q1.w * q2.z + q1.x * q2.y - q1.y * q2.x + q1.z * q2.w);
    }

    // Rotate a vector using the quaternion
    public double[] rotateVector(double[] v) {
        Transform vq = new Transform(0, v[0], v[1], v[2]);
        Transform qConjugate = new Transform(w, -x, -y, -z);
        Transform rotatedVQ = multiply(multiply(this, vq), qConjugate);
        return new double[] { rotatedVQ.x, rotatedVQ.y, rotatedVQ.z };
    }
}
