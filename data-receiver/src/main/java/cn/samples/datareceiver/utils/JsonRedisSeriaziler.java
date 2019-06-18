package cn.samples.datareceiver.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 不使用sdr自带的json序列化工具，一切操作基于string
 * 
 * @author yinheng
 *
 */
@Component
public class JsonRedisSeriaziler {

	public static final String EMPTY_JSON = "{}";

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	protected ObjectMapper objectMapper = new ObjectMapper();

	public JsonRedisSeriaziler() {
	}

	/**
	 * java-object as json-string
	 * 
	 * @param object
	 * @return
	 */
	public String seriazileAsString(Object object) {
		if (object == null) {
			return EMPTY_JSON;
		}

		String result = null;
		try {
			result = this.objectMapper.writeValueAsString(object);
		} catch (Exception ex) {
			throw new RuntimeException("Could not write JSON: " + ex.getMessage(), ex);
		}

		return result;
	}

	/**
	 * json-string to java-object
	 * 
	 * @param str
	 * @return
	 */
	public <T> T deserializeAsObject(String str, Class<T> clazz) {
		if (str == null || clazz == null) {
			return null;
		}

		T result = null;
		try {
			result = this.objectMapper.readValue(str, clazz);
		} catch (Exception ex) {
			throw new RuntimeException("Could not write JSON: " + ex.getMessage(), ex);
		}
		return result;
	}

	/**
	 * json to list
	 * 
	 * @param str
	 * @param clazz
	 * @return
	 */
	public <T> List<T> deserializeAsList(String str, Class<T> clazz) {
		if (str == null || clazz == null) {
			return null;
		}

		TypeFactory tf = TypeFactory.defaultInstance();
		// 指定容器结构和类型（这里是ArrayList和clazz）
		List<T> list = null;
		try {
			list = objectMapper.readValue(str, tf.constructCollectionType(List.class, clazz));
		} catch (Exception ex) {
			throw new RuntimeException("Could not write JSON: " + ex.getMessage(), ex);
		}
		return list;
	}

/*	public static void main(String[] args) {
		JsonRedisSeriaziler seriaziler = new JsonRedisSeriaziler();
		System.out.println(seriaziler.seriazileAsString(1234));
		System.out.println(seriaziler.seriazileAsString(false));

		DataPackage dp = new DataPackage();
		System.out.println("dp: " + dp);

		String dpSeri = seriaziler.seriazileAsString(dp);
		System.out.println("dpSeri: " + dpSeri);

		// 默认会转成map
		Object objDeseri = seriaziler.deserializeAsObject(dpSeri, Object.class);
		System.out.println("objDeseri: " + objDeseri);

		DataPackage dpDeseri = (DataPackage) objDeseri;
		System.out.println("dpDeseri: " + dpDeseri);
	}*/
}