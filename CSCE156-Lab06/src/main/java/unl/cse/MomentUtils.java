package unl.cse;

import java.util.Arrays;
import java.util.List;

public class MomentUtils {
	
	/**
	 * Computes the moment of the given set of values using the
	 * specified <code>alpha</code> and <code>k</code> parameters.
	 * @param values
	 * @param alpha
	 * @param k
	 * @return
	 */
	public static double moment(List<Double> values, double alpha) {
		int k = 1;
		double answer = 0.00;
		for(double x : values) {
			double valuesAlpha = x - alpha;
			double powered = Math.pow(valuesAlpha, k);
			answer += powered;
		}
		double size = values.size();
		double n = 1 / size;
		answer = answer * n;
		return answer;
	}
	public static double moment(List<Double> values, int k) {
		double alpha = 0;
		double answer = 0.00;
		for(double x : values) {
			double valuesAlpha = x - alpha;
			double powered = Math.pow(valuesAlpha, k);
			answer += powered;
		}
		double size = values.size();
		double n = 1 / size;
		answer = answer * n;
		return answer;

	}
	
	public static double moment(List<Double> values, double alpha, int k) {
		double answer = 0.00;
		for(double x : values) {
			double valuesAlpha = x - alpha;
			double powered = Math.pow(valuesAlpha, k);
			answer += powered;
		}
		double size = values.size();
		double n = 1 / size;
		answer = answer * n;
		return answer;
	}
	
	public static double moment(List<Double> values) {
		double alpha = 0;
		int k = 1;
		double answer = 0.00;
		for(double x : values) {
			double valuesAlpha = x - alpha;
			double powered = Math.pow(valuesAlpha, k);
			answer += powered;
		}
		double size = values.size();
		double n = 1 / size;
		answer = answer * n;
		return answer;
	}
	
	public static void main(String args[]) {
		
		List<Double> numbers = Arrays.asList(-6.190000e-1, -1.042570e+0, -2.633440e-1, 
				-7.471450e-1, 2.117680e+0, 2.933850e-1, -7.245130e-1, -9.494510e-1, -1.019190e+0, 
				-9.099270e-1, -2.632650e-1, -1.074240e-1, 1.315500e+0, 6.606400e-2, 8.750930e-1, 
				-9.419930e-1, -1.050610e+0, -1.090710e+0, 3.339850e-1, 7.784820e-1);
		
		double x = moment(numbers, 1, 2);
		System.out.println(x);
		x = moment(numbers, 1.0);
		System.out.println(x);
		x = moment(numbers, 2);
		System.out.println(x);
		x = moment(numbers);
		System.out.println(x);
	}
	
}
