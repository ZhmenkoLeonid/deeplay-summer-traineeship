import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.zhmenko.deeplay.Solution;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class SolutionTest {
    static File configFile;

    @BeforeAll
    static void setup() {
        configFile = new File("config.json");
    }

    @Test
    void primaryCase() {
        assertEquals(10, Solution.getResult("STWSWTPPTPTTPWPP", "Human", configFile));
    }

    @Test
    void badEntityCase() {
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> Solution.getResult("STWSWTPPTPTTPWPP", "NotExistingEntityName", configFile));

        assertTrue(exception.getMessage().contains("Не найдено существа"));
    }

    @Test
    void badInputConfigCase() {
        File brokenConfigFile = new File("broken-config.json");
        RuntimeJsonMappingException exception = assertThrows(RuntimeJsonMappingException.class,
                () -> Solution.getResult("STWSWTPPTPTTPWPP", "Human", brokenConfigFile));

        assertEquals(exception.getMessage(), "Не получилось считать свойства из файла!");
    }

    @Test
    void customConfigCase() {
        //    10 20  25  50
        //    M  B   C   X
        File customConfigFile = new File("custom-config.json");
        //    B       X       C       B
        //    |
        //    B(20) - M(10) - B(20) - M(10)
        //                            |
        //    C       M       X       M(10)
        //                            |
        //    C       B       B       C(25)
        assertEquals(95,Solution.getResult("BXCBBMBMCMXMCBBC", "DeeplayIntern", customConfigFile));
        //    B       X        C       B
        //    |
        //    B(20) - M(10)    B       M
        //            |
        //    C       M(10)    X       M
        //            |
        //    C       M(10) -  M(10) - C(25)
        assertEquals(85, Solution.getResult("BXCBBMBMCMXMCMMC", "DeeplayIntern", customConfigFile));
    }
}
