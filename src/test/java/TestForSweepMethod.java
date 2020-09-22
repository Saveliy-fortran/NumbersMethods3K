import methods.SweepMethod;
import org.junit.*;
import exceptions.NonSquareArray;
import java.util.Arrays;

public class TestForSweepMethod {



    @Test
    public void testForSweepMethod2() throws NonSquareArray {
        double[] A = {0,-3,-5,-6,-5}; // диагональ под главной
        double[] B = {2,8,12,18,10}; // диагональ главная
        double[] C = {-1,-1,2,-4,0}; // диагональ над главной
        double[] D = {-25,72,-69,-156,20}; // правая часть

        double[] x = SweepMethod.sweepMethod( A, B, C, D);

        System.out.println(Arrays.toString(x));

        Assert.assertEquals(5, x.length);
        Assert.assertEquals(-10, x[0], 0.0001);
        Assert.assertEquals(5, x[1], 0.0001);
        Assert.assertEquals(-2, x[2], 0.0001);
        Assert.assertEquals(-10, x[3], 0.0001);
        Assert.assertEquals(-3, x[4], 0.0001);

    }

}
