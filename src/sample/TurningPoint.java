package sample;

import org.apache.commons.math3.ode.events.EventHandler;

public class TurningPoint implements EventHandler {

    private int sign;
    private double c;
    private double d;

    public TurningPoint(double c, double d) {
        this.c = c;
        this.d = d;
    }

    @Override
    public void init(double t, double[] x, double dxdt) {
        sign = 1;
    }

    @Override
    public double g(double t, double[] x) {
        return sign * (x[0] - 30);
    }

    @Override
    public Action eventOccurred(double t, double[] x, boolean b) {
        sign = -sign;
        return Action.RESET_STATE;
    }

    @Override
    public void resetState(double t, double[] x) {
        x[0] = c;
        x[1] = x[1] + d;
    }
}
