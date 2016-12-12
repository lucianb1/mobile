package ro.hoptrop.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by Luci on 13-Dec-16.
 */
public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * @param object
     * @throws RuntimeException if the json cannot be created
     */
    public static String toJson(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Cannot serialize json", e);
        }
    }

    /**
     * @param content
     * @param valueType
     * @throws RuntimeException if json cannot be read
     */
    public static <T> T fromJson(String content, Class<T> valueType) {
        if (content == null) {
            return null;
        }
        try {
            return objectMapper.readValue(content, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
