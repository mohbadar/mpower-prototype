package af.dfi.lang.util;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
public class Utility {


    private static ObjectMapper objectMapper = new ObjectMapper();
    private static TypeFactory typeFactory = objectMapper.getTypeFactory();

    public static boolean isNullOrBlank(String key) {
        int strLen;
        if (key != null && (strLen = key.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(key.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static String objectToJson(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonToString = mapper.writeValueAsString(object);
            return jsonToString;
        } catch (Exception var3) {
            log.error("exception", var3);
            return null;
        }
    }

    public static <T1> List<T1> convertModelList(List<? extends Object> sourceClass, Class<T1> destinationClass) {
        try {
            CollectionType collectionType = typeFactory.constructCollectionType(List.class, destinationClass);
            return objectMapper.convertValue(sourceClass, collectionType);
        } catch (Exception exp) {
            log.error("[convertModelList] Exception occurred while converting model list", exp);
            return null;
        }
    }

    public static <T> T convertModel(Object sourceClass, Class<T> destinationClass) {
        try {
//            JavaType javaType = typeFactory.constructType(destinationClass);
            return objectMapper.convertValue(sourceClass, destinationClass);
        } catch (Exception exp) {
            log.error("Exception", exp);
            return null;
        }
    }

    public static Long getLongValue(String str) {
        try {
            return new Long(str);
        } catch (Exception e) {
            log.error("Error Occurred while converting String to Long value - " + str, e);
        }
        return 0L;
    }

    public static boolean getBooleanValue(String str) {
        try {
            return Boolean.valueOf(str);
        } catch (Exception e) {
            log.error("Error occurred while converting String to boolean - " + str, e);
        }
        return false;
    }

    public static boolean isNonEmpty(Collection<?> collection) {
        return (collection != null && !collection.isEmpty());
    }

    public Map<String, String> convertJsonStringToMap(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> map =mapper.readValue(jsonString, Map.class);
        return map;
    }


    public boolean isValid(String jsonString) {
        try {
            new JSONObject(jsonString);
            return true;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public JSONObject parse(String jsonString) {
        try {
            JSONObject json = new JSONObject(jsonString);
            return json;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String convertToString(JSONObject jsonObj) {
//		#TODO
        return null;
    }

    public String parseJsonObjToXFormValue(Object obj) {
        //if obj is array then it should be converted to space seperated string
        StringJoiner strJoiner = new StringJoiner(" ");

        if(obj instanceof JSONArray) {
            JSONArray array =  (JSONArray) obj;
            array.forEach(item -> {
                strJoiner.add(item.toString());
            });

        } else {
            strJoiner.add(obj.toString());
        }

        return strJoiner.toString();
    }

    public Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if(json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    private Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    private List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }


    public <T> List<T> loadObjectList(Class<T> type, String fileName) {
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            File file = new ClassPathResource(fileName).getFile();
            MappingIterator<T> readValues =
                    mapper.reader(type).with(bootstrapSchema).readValues(file);
            return readValues.readAll();
        } catch (Exception e) {
            log.error("Error occurred while loading object list from file " + fileName, e);
            return Collections.emptyList();
        }
    }

    public List<String[]> loadManyToManyRelationship(String fileName) {
        try {
            CsvMapper mapper = new CsvMapper();
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withSkipFirstDataRow(true);
            mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
            File file = new ClassPathResource(fileName).getFile();
            MappingIterator<String[]> readValues =
                    mapper.reader(String[].class).with(bootstrapSchema).readValues(file);
            return readValues.readAll();
        } catch (Exception e) {
            log.error(
                    "Error occurred while loading many to many relationship from file = " + fileName, e);
            return Collections.emptyList();
        }
    }

}
