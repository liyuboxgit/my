package liyu.test.yml;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class YML{
	/**
	 * 属性是一个map，可以递归，其value可以是一个简单属性如字符串；也可以是个复杂属性如YML、集合String、集合哈希map，
	 * 这里使用哈希map，做个解释，也就是处理简单话，不支持yml下有集合yml，子子孙孙无穷匮也，是不支持的 
	*/
	public Map<String,Object> prop;
	/**
	 * 层级，生成文件时用
	 */
	public int level;
	public YML(int level) {
		this.prop = new LinkedHashMap<>();
		this.level = level;
	}
	// 简单字符串
	public YML putString(String key,String val) {
		this.prop.put(key, val);
		return this;
	}
	// 集合字符串
	public void putString(String key,Collection<String> val) {
		this.prop.put(key, val);
	}
	// YML
	public YML put(String key,YML val) {
		this.prop.put(key, val);
		return val;
	}
	// 集合哈希map
	public void put(String key,@SuppressWarnings("rawtypes") Collection<Map> val) {
		this.prop.put(key, val);
	}
	// 获值
	public Object get(String key) {
		return this.prop.get(key);
	}
	// 输出文件
	public static void outputYML(YML yml) {
		Map<String, Object> root = yml.prop;
		if(root.keySet().size()>0) {
			for(String k:root.keySet()) {
				for(int i=0;i<yml.level;i++) {
					System.out.print("  ");
				}
				System.out.print(k+":");
				
				Object v = root.get(k);
				//处理string类型
				if(v instanceof String) {
					System.out.println(" "+v);
				}
				//处理集合类型
				else if(v instanceof Collection<?>) {
					@SuppressWarnings("unchecked")
					Collection<Object> set = (Collection<Object>) v; 
					if(set.size()>0) {
						Object next = set.iterator().next();
						//处理集合stringleix
						if(next instanceof String) {
							System.out.println();
							set.iterator().forEachRemaining(f->{
								for(int i=0;i<yml.level;i++) {
									System.out.print("  ");
								}
								System.out.println("  - "+f);
							});
						}
						//处理集合哈希map类型
						else if(next instanceof Map){
							System.out.println();
							set.iterator().forEachRemaining(f->{
								@SuppressWarnings("unchecked")
								Map<String,String> fm = (Map<String, String>) f;
								Set<Entry<String,String>> entrySet = fm.entrySet();
								boolean first = true;
								for(Entry<String,String> el:entrySet) {
									for(int i=0;i<yml.level;i++) {
										System.out.print("  ");
									}
									
									System.out.println(
										first 
											? "  - "+el.getKey()+": "+el.getValue() 
											: "    "+el.getKey()+": "+el.getValue() 
									);
									first = false;
								}
								
							});
						} 
					}
				}
				//处理YML类型
				else {
					System.out.println();
					outputYML((YML)v);
				}
			}
		}
	}
	//HashMap子类，方便构造HashMap，如：new M(new String[]{"name:xiao_ming","age:18"})
	public static class M extends HashMap<String,String>{
		private static final long serialVersionUID = 1L;
		public M(String[] array) {
			for(String s:array) {
				String[] splits = s.split(":");
				this.put(splits[0], splits[1]);
			}
		}
	}
}
