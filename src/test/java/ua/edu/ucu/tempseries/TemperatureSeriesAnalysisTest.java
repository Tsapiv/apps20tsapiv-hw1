package ua.edu.ucu.tempseries;

import org.junit.Test;

import java.util.InputMismatchException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TemperatureSeriesAnalysisTest {

    @Test(expected = InputMismatchException.class)
    public void testCreationWithInvalidTemps() {
        double[] temperatureSeries = {100.0, -273.15, 36.6};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
    }

    @Test
    public void testAverageWithOneElementArray() {
        // setup input data and expected result
        double[] temperatureSeries = {-1.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = -1.0;

        // call tested method
        double actualResult = seriesAnalysis.average();

        // compare expected result with actual result
        assertEquals(expResult, actualResult, 0.00001);
    }

    //    @Ignore
    @Test(expected = IllegalArgumentException.class)
    public void testAverageWithEmptyArray() {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        seriesAnalysis.average();
    }

    //    @Ignore
    @Test
    public void testAverage() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 1.0;

        double actualResult = seriesAnalysis.average();

        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeviationWithEmptyArray() {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        seriesAnalysis.deviation();

    }

    @Test
    public void testDeviation() {
        double[] temperatureSeries = {1.0, 1.0, 1.0, 1.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 0.0;

        double actualResult = seriesAnalysis.deviation();

        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinWithEmptyArray() {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        seriesAnalysis.min();

    }

    @Test
    public void testMin() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0, -6.0, -6.000000001};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = -6.000000001;

        double actualResult = seriesAnalysis.min();

        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaxWithEmptyArray() {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        seriesAnalysis.max();

    }

    @Test
    public void testMax() {
        double[] temperatureSeries = {3.0, 6.0, 1.0, 5.0, -6.0, 6.000000001};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 6.000000001;

        double actualResult = seriesAnalysis.max();

        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindTempClosestToZeroWithEmptyArray() {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        seriesAnalysis.findTempClosestToZero();
    }

    @Test
    public void testFindTempClosestToZero() {
        double[] temperatureSeries = {3.0, 6.0, 1.0, 5.0, -4.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // positive number is closer to zero
        double expResult1 = 1.0;
        double actualResult1 = seriesAnalysis.findTempClosestToZero();
        assertEquals(expResult1, actualResult1, 0.00001);

        // negative number is closer to zero
        seriesAnalysis.addTemps(-0.5);
        double expResult2 = -0.5;
        double actualResult2 = seriesAnalysis.findTempClosestToZero();
        assertEquals(expResult2, actualResult2, 0.00001);

        // from 2 equally close to zero numbers pick the positive one
        seriesAnalysis.addTemps(0.5);
        double expResult3 = 0.5;
        double actualResult3 = seriesAnalysis.findTempClosestToZero();
        assertEquals(expResult3, actualResult3, 0.00001);

        // zero is closest to zero
        seriesAnalysis.addTemps(0.0);
        double expResult4 = 0.0;
        double actualResult4 = seriesAnalysis.findTempClosestToZero();
        assertEquals(expResult4, actualResult4, 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindTempClosestToValueWithEmptyArray() {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        seriesAnalysis.findTempClosestToValue(10.0);
    }

    @Test
    public void testFindTempClosestToValue() {
        double[] temperatureSeries = {3.0, 6.0, 1.0, 5.0, -4.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // number is present in the array
        double expResult1 = -4.0;
        double actualResult1 = seriesAnalysis.findTempClosestToValue(-4.0);
        assertEquals(expResult1, actualResult1, 0.00001);

        // for negative number
        double expResult2 = -4.0;
        double actualResult2 = seriesAnalysis.findTempClosestToValue(-2.0);
        assertEquals(expResult2, actualResult2, 0.00001);

        // for positive number
        double expResult3 = 5.0;
        double actualResult3 = seriesAnalysis.findTempClosestToValue(5.49999);
        assertEquals(expResult3, actualResult3, 0.00001);

        // from 2 equally close to given numbers pick the bigger one
        double expResult4 = 3.0;
        double actualResult4 = seriesAnalysis.findTempClosestToValue(2.0);
        assertEquals(expResult4, actualResult4, 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindTempsLessThenWithEmptyArray() {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        seriesAnalysis.findTempsLessThen(10.0);
    }

    @Test
    public void testFindTempsLessThen() {
        double[] temperatureSeries = {3.0, 6.0, 1.0, 5.0, -6.0, 2.0, -4.0, 4.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        double[] expResult1 = {-6.0, -4.0};
        double[] actualResult1 = seriesAnalysis.findTempsLessThen(0);
        assertArrayEquals(expResult1, actualResult1, 0.0);

        double[] actualResult2 = seriesAnalysis.findTempsLessThen(100);
        assertArrayEquals(temperatureSeries, actualResult2, 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindTempsGreaterThenWithEmptyArray() {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        seriesAnalysis.findTempsGreaterThen(10.0);
    }

    @Test
    public void testFindTempsMoreThen() {
        double[] temperatureSeries = {3.0, 6.0, 1.0, 5.0, -6.0, 2.0, -4.0, 4.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        double[] expResult1 = {3.0, 6.0, 1.0, 5.0, 2.0, 4.0};
        double[] actualResult1 = seriesAnalysis.findTempsGreaterThen(0);
        assertArrayEquals(expResult1, actualResult1, 0.0);

        double[] actualResult2 = seriesAnalysis.findTempsGreaterThen(TemperatureSeriesAnalysis.getLOWER_BOUND());
        assertArrayEquals(temperatureSeries, actualResult2, 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSummaryStatisticsWithEmptyArray() {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        seriesAnalysis.summaryStatistics();
    }

    @Test
    public void testSummaryStatistics() {
        double[] temperatureSeries = {3.0, 6.0, 1.0, 5.0, -6.0, 2.0, -4.0, 4.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        TempSummaryStatistics actualResult = seriesAnalysis.summaryStatistics();
        double expAverage = seriesAnalysis.average();
        double expDeviation = seriesAnalysis.deviation();
        double expMin = seriesAnalysis.min();
        double expMax = seriesAnalysis.max();

        assertEquals(expAverage, actualResult.getAvgTemp(), 0.00001);
        assertEquals(expDeviation, actualResult.getDevTemp(), 0.00001);
        assertEquals(expMin, actualResult.getMinTemp(), 0.00001);
        assertEquals(expMax, actualResult.getMaxTemp(), 0.00001);
    }

    @Test(expected = InputMismatchException.class)
    public void testAddTempsWithTempBelowAbsoluteZero() {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        seriesAnalysis.addTemps(2.0, -45.0, -273.15, 36.6);
    }

    @Test
    public void testAddTemps() {
        double[] temperatureSeries = {100.0, 36.6, 2.0, -45.0, 100.0, 36.6};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        seriesAnalysis.addTemps(2.0, -45.0, 100.0, 36.6, 2.0, -45.0);
        assertEquals(seriesAnalysis.getSize(), 12);

        seriesAnalysis.addTemps(23.0, -12.0);
        assertEquals(seriesAnalysis.getSize(), 14);
    }

}
