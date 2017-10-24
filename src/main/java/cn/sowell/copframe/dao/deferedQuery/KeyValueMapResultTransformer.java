package cn.sowell.copframe.dao.deferedQuery;

import java.util.HashMap;
import java.util.Map;

public class KeyValueMapResultTransformer<K, V> extends ColumnMapResultTransformer<Void> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 757592956506859643L;
	private final Map<K, V> map;
	private final Function<SimpleMapWrapper, K> keyGetter;
	private final Function<SimpleMapWrapper, V> valueGetter;

	/**
	 * 
	 * @param map
	 * @param keyGetter
	 * @param valueGetter
	 * @return
	 */
	public static <K, V> KeyValueMapResultTransformer<K, V> build(Map<K, V> map,  
			Function<SimpleMapWrapper, K> keyGetter, 
			Function<SimpleMapWrapper, V> valueGetter){
		return new KeyValueMapResultTransformer<K, V>(map, keyGetter, valueGetter);
	}
	/**
	 * 
	 * @param keyGetter
	 * @param valueGetter
	 * @return
	 */
	public static <K, V> KeyValueMapResultTransformer<K, V> build(
			Function<SimpleMapWrapper, K> keyGetter, 
			Function<SimpleMapWrapper, V> valueGetter){
		Map<K, V> map = new HashMap<K, V>();
		return build(map, keyGetter, valueGetter);
	}
	
	
	private KeyValueMapResultTransformer(Map<K, V> map, 
			Function<SimpleMapWrapper, K> keyGetter, 
			Function<SimpleMapWrapper, V> valueGetter) {
		this.map = map;
		this.keyGetter = keyGetter;
		this.valueGetter = valueGetter;
	}
	
	@Override
	protected Void build(SimpleMapWrapper mapWrapper) {
		K key = this.keyGetter.apply(mapWrapper);
		if(key != null){
			map.put(key, this.valueGetter.apply(mapWrapper));
		}
		return null;
	}
	
	/**
	 * 需要在query执行了list之后，map的内容才会更新
	 * @return
	 */
	public Map<K, V> getMap() {
		return map;
	}
	
	
}
