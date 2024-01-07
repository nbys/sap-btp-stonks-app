package nbys.stonks.json;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.sap.cds.CdsData;
import com.sap.cds.ql.CdsName;

public class CdsUnmarshallerTest {

    class TestCdsUnmarshaller extends CdsUnmarshaller {
        public String _field1;
        public int _field2;
    }

    interface TestCdsData extends CdsData {
        String FIELD1 = "field1";
        String FIELD2 = "field2";

        @CdsName(FIELD1)
        String getField1();

        void setField1(String field1);

        @CdsName(FIELD2)
        int getField2();

        void setField2(int field2);
    }

    @Test
    public void testToCDS() {
        TestCdsUnmarshaller unmarshaller = new TestCdsUnmarshaller();

        unmarshaller._field1 = "value1";
        unmarshaller._field2 = 42;

        assertDoesNotThrow(() -> {
            TestCdsData result = unmarshaller.toCDS(TestCdsData.class);
            assertEquals("value1", result.getField1());
            assertEquals(42, result.getField2());
        });
    }
}
