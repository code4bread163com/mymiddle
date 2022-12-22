package zuul.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.ArrayType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.lang.reflect.Type;


/**
 * JSON工具类
 *
 * @author linjun
 */
@Slf4j
public class JsonUtils {

    /**
     * 统一全局配置：使用当前JsonUtils类的、以及SpringIoC中注入的ObjectMapper，都按这个Builder的配置来创建实例。
     */
    public static final Jackson2ObjectMapperBuilder OBJECT_MAPPER_BUILDER = new Jackson2ObjectMapperBuilder()
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            // 约束BigDecimal不使用科学计数法。
            .featuresToEnable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN)
            // 约束Float字段统一用BigDecimal来处理
            .featuresToEnable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
            //.deserializerByType(BigDecimal.class,new NoNullBigDecimalDeserializer())
            ;

    private static ObjectMapper objectMapper = OBJECT_MAPPER_BUILDER.build();

    private JsonUtils() {
        throw new UnsupportedOperationException();
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error("Json字符串反序列化发生异常。 json:{}, clazz:{}", json, clazz);
            throw new JsonDeserializationException(json, clazz, e);
        }
    }

    public static <T> T toObjct(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (IOException e) {
            log.error("Json字符串反序列化发生异常。 json:{}, typeReference:{}", json, typeReference);
            throw new JsonDeserializationException(json, typeReference.getType(), e);
        }
    }

    public static <T> T[] stringJsonToArray(String stringListJson, Class<T> tClass) {
        try {
            ArrayType arrayType = objectMapper.getTypeFactory().constructArrayType(tClass);
            return objectMapper.readValue(stringListJson, arrayType);
        } catch (Exception e) {
            log.error("objectToJson exception: {}", e.getMessage(), e);
        }
        return null;
    }

    public static String toString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("JSON序列化发生异常。 object:{}", object);
            throw new JsonSerializationException(object, e);
        }
    }

    public static class JsonSerializationException extends BaseCfcException {

        private transient final Object object;

        public JsonSerializationException(Object object, Throwable cause) {
            super(cause);
            this.object = object;
        }

        @Override
        public String getRetCode() {
            return "JSON_EXCEPTION";
        }

        @Override
        public String getRetMsg() {
            return "JSON反序列化发生异常";
        }

        @Override
        public String getLocalizedMessage() {
            return "JSON序列化发生异常。序列化对象是：" + object;
        }
    }


    public static class JsonDeserializationException extends BaseCfcException {

        private final transient Object json;
        private final transient String clazzName;

        public JsonDeserializationException(Object json, Class<?> clazz, Throwable cause) {
            super(cause);
            this.json = json;
            this.clazzName = clazz.getName();
        }

        public JsonDeserializationException(Object json, Type type, Throwable cause) {
            super(cause);
            this.json = json;
            this.clazzName = type.getTypeName();
        }


        @Override
        public String getRetCode() {
            return "JSON_EXCEPTION";
        }

        @Override
        public String getRetMsg() {
            return "JSON反序列化发生异常";
        }

        @Override
        public String getLocalizedMessage() {
            return "JSON反序列化发生异常。目标类是：" + clazzName
                    + "； json串是：" + json;
        }
    }
}
