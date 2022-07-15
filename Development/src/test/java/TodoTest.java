import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TodoTest {

    @Test
    void Test() {
        //Todo: Test
        String s = "TODO";
        String s2 = "TOD";
        s2 += "O";
        assertEquals(s, s2);
    }
}