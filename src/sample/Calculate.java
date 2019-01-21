package sample;


import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

public class Calculate implements FirstOrderDifferentialEquations {

    private double a;
    private double b;

    private double i;

    public Calculate(double a, double b, double i) {
        this.a = a;
        this.b = b;

        this.i = i;
    }

    public double getI() {
        return i;
    }

    public void setI(double i) {
        this.i = i;
    }

    @Override
    public int getDimension() {
        return 2;
    }

    @Override
    public void computeDerivatives(double v, double[] vu, double[] dvudt) throws MaxCountExceededException, DimensionMismatchException {
        dvudt[0] = 0.04 * vu[0] * vu[0] + (5 * vu[0]) + 140 - vu[1] + i;
        dvudt[1] = a * (b * vu[0] - vu[1]);
    }
}
