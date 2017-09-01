package unicorn.common.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonUtil {
	private static ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();

		// 设置FAIL_ON_EMPTY_BEANS属性，当序列化空对象不要抛异常
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		// 设置FAIL_ON_UNKNOWN_PROPERTIES属性，当JSON字符串中存在Java对象没有的属性，忽略
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * Convert Object to JsonString
	 * 
	 * @param jsonObj
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String jsonObj2Sting(Object jsonObj) throws JsonProcessingException {
		String jsonString = null;
		jsonString = objectMapper.writeValueAsString(jsonObj);
		return jsonString;
	}

	/**
	 * Convert JsonString to Simple Object
	 * 
	 * @param jsonString
	 * @param cls
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public static <T> T jsonString2SimpleObj(String jsonString, Class<T> cls)
			throws JsonParseException, JsonMappingException, IOException {
		T jsonObj = null;
		jsonObj = objectMapper.readValue(jsonString, cls);
		return jsonObj;
	}

	/**
     * Convert Object to ObjectNode
     * @param jsonString
     * @return
     * @throws JsonProcessingException
     * @throws IOException
     */
    public static ObjectNode jsonObj2ObjectNode(Object jsonObj) throws JsonProcessingException, IOException{
    	ObjectNode objnode=null;
    	String jsonString=jsonObj2Sting(jsonObj);
    	objnode=(ObjectNode) objectMapper.readTree(jsonString);
    	return objnode;
    	
    }
    /**
     * Convert ObjectNode to Object
     * @param ObjectNode
     * @return
     * @throws IOException
     */
    public static <T> T objectNode2JsonObj(ObjectNode objectNode,Class<T> cls) throws IOException {
    	String jsonString=objectNode.toString();
    	T jsonObj=jsonString2SimpleObj(jsonString,cls);
    	return jsonObj;    	
    }
    /**
     * Get JsonNode by NodeName
     * @param jsonString
     * @param nodeName
     * @return
     * @throws IOException
     */
    public static JsonNode getValueFromJsonString(ObjectNode objectNode,String nodeName) throws IOException {
    	return objectNode.findValue(nodeName);
    }

}
