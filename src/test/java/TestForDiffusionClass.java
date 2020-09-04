import equations.Diffusion;
import org.junit.*;

public class TestForDiffusionClass {
    @Test
    public void testComparison(){
        Diffusion test = new Diffusion(1, 100, 0, 1, 0.05, 0.5, 1);
    }
    @Before
    public void cleanRefuse(){
        System.gc();
    }

}
