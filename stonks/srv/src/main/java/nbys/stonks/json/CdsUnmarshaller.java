package nbys.stonks.json;

import static com.sap.cds.Struct.access;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sap.cds.CdsData;

public class CdsUnmarshaller implements Unmarshaller {
    protected <T> T toCDS(Class<T> clazz) throws IllegalAccessException, NoSuchFieldException {
        HashMap<String, Object> map = new HashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            String name = field.getName();
            if (!name.startsWith("_")) {
                continue;
            }

            map.put(name.substring(1), this.getClass()
                    .getDeclaredField(name.toLowerCase()).get(this));
        }

        return access(map).as(clazz);
    }

    @Override
    public List<CdsData> unmarshal(String jsonString) throws JsonProcessingException, JsonMappingException {
        throw new UnsupportedOperationException("Unimplemented method 'unmarshal'");
    }
}