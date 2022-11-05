import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class MainClassTest extends MainClass {
    @Test
    public void testGetLocalNumber() {
        Assertions.assertTrue(this.getLocalNumber() == 14, "getLocalNumber doesn't return 14");
    }

    @Test
    public void testGetClassNumber() {
        Assertions.assertTrue(this.getClassNumber() > 45, "getClassNumber doesn't return number grater than 45");
    }
}
