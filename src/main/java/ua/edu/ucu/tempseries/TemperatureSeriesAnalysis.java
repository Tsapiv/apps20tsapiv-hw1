package ua.edu.ucu.tempseries;

import java.util.function.DoubleBinaryOperator;

import lombok.Getter;
import ua.edu.ucu.tempseries.lambda.TwoArgInterface;

import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    @Getter
    private static final double LOWER_BOUND = -273.15;
    private static final int START_SIZE = 10;
    private static final double EPSILON = 1E-6;
    private double[] tempsSequence = new double[START_SIZE];
    @Getter
    private int size = 0;

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        addTemps(temperatureSeries);
    }

    public double average() {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        double total = 0;
        for (int i = 0; i < size; i++) {
            total += tempsSequence[i];
        }
        return total / size;
    }

    public double deviation() {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        double diff = 0;
        double mean = average();
        for (int i = 0; i < size; i++) {
            diff += (tempsSequence[i] - mean) * (tempsSequence[i] - mean);
        }
        return Math.sqrt(diff / (size - 1));
    }

    public double compare(DoubleBinaryOperator op) {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        double el = tempsSequence[0];
        for (int i = 1; i < size; i++) {
            el = op.applyAsDouble(tempsSequence[i], el);
        }
        return el;
    }

    public double min() {
        DoubleBinaryOperator op = Math::min;
        return compare(op);
    }

    public double max() {
        DoubleBinaryOperator op = Math::max;
        return compare(op);
    }

    public double findTempClosestToZero() {
        return findTempClosestToValue(0);
    }

    public static boolean equals(double a, double b) {
        return a == b || Math.abs(a - b) < EPSILON;
    }

    public double findTempClosestToValue(double tempValue) {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        double diff = Math.abs(tempsSequence[0] - tempValue);
        double closest = tempsSequence[0];
        for (int i = 1; i < size; i++) {
            double tempDiff = Math.abs(tempsSequence[i] - tempValue);
            if (equals(tempDiff, diff)) {
                closest = Math.max(closest, tempsSequence[i]);
            } else if (diff > tempDiff) {
                diff = tempDiff;
                closest = tempsSequence[i];
            }
        }
        return closest;
    }
    
    public double[] filter(double tempValue, TwoArgInterface op) {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        double[] tempArray = new double[size];
        int j = 0;
        for (int i = 0; i < size; i++) {
            if (op.operation(tempValue, tempsSequence[i])) {
                tempArray[j++] = tempsSequence[i];
            }
        }
        double[] resultArray = new double[j];
        System.arraycopy(tempArray, 0, resultArray, 0, j);
        return resultArray;
    }

    public double[] findTempsLessThen(double tempValue) {
        TwoArgInterface op = ((a, b) -> a > b);
        return filter(tempValue, op);
    }

    public double[] findTempsGreaterThen(double tempValue) {
        TwoArgInterface op = ((a, b) -> a < b);
        return filter(tempValue, op);
    }

    public TempSummaryStatistics summaryStatistics() {
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }

    public int addTemps(double... temps) {
        if (temps.length + size > tempsSequence.length) {
            double[] temp = new double[2 * (temps.length + size)];
            System.arraycopy(tempsSequence, 0, temp, 0, size);
            this.tempsSequence = temp;
        }
        for (int j = 0; j < temps.length; j++) {
            if (temps[j] > LOWER_BOUND) {
                tempsSequence[size + j] = temps[j];
            } else {
                throw new InputMismatchException();
            }
        }
        this.size += temps.length;
        return size;
    }
}

