import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JTimeOutRule {
    @Test(timeout = 20 * 1000)
    public void t() {
        for (int i = 0; i < 1000000; i++) {
            if (i % 100000 == 0) {
                System.out.println(i);

            }
        }
    }
}

