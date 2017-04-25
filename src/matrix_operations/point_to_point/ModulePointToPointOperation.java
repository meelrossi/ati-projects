package matrix_operations.point_to_point;

public class ModulePointToPointOperation extends PointToPointOperation {

	@Override
	public double pointToPoint(double value1, double value2) {
		return Math.sqrt(Math.pow(value1, 2) + Math.pow(value2, 2));
	}

}
