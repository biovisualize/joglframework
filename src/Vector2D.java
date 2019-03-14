
public class Vector2D {

	public float[] v = new float[2];

	public Vector2D() {
		v[0] = v[1] = 0;
	}

	public Vector2D(float x, float y) {
		v[0] = x;
		v[1] = y;
	}

	public void copy(Vector2D V) {
		v[0] = V.v[0];
		v[1] = V.v[1];
	}

	public float x() {
		return v[0];
	}

	public float y() {
		return v[1];
	}

	public float lengthSquared() {
		return x() * x() + y() * y();
	}

	public float length() {
		return (float) Math.sqrt(lengthSquared());
	}

	public float distance(Vector2D otherVector) {
		return diff(this, otherVector).length();
	}

	public Vector2D negated() {
		return new Vector2D(-x(), -y());
	}

	public Vector2D normalized() {
		float l = length();
		if (l > 0) {
			float k = 1 / l; // scale factor
			return new Vector2D(k * x(), k * y());
		} else {
			return new Vector2D(x(), y());
		}
	}

	// returns the dot-product of the given vectors
	static public float dot(Vector2D a, Vector2D b) {
		return a.x() * b.x() + a.y() * b.y();
	}

	// returns the sum of the given vectors
	static public Vector2D sum(Vector2D a, Vector2D b) {
		return new Vector2D(a.x() + b.x(), a.y() + b.y());
	}

	public float angleRad() {
		return (float) Math.atan2(y(), x());
	}

	// returns the difference of the given vectors
	static public Vector2D diff(Vector2D a, Vector2D b) {
		return new Vector2D(a.x() - b.x(), a.y() - b.y());
	}

	// returns the product of the given vector and scalar
	static public Vector2D mult(Vector2D a, float b) {
		return new Vector2D(a.x() * b, a.y() * b);
	}
}
