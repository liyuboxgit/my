package com.rthd.tinychxu.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

public class MapUtil{
	public static class StringMap extends HashMap<String,Object>{
		private static final long serialVersionUID = 1L;
		public StringMap() {
			
		}
		
		public StringMap(String[] k,Object[] v) {
			Assert.isTrue(k.length==v.length, "k-v size not consisted.");
			int i=0;
			while(i<k.length) {
				i++;
				this.put(k[i], v[i]);
			}
		}
		
		public StringMap(Map<String,Object> map) {
			this.putAll(map);
		}
		
	}
	
	
	public static List<StringMap> convert(List<Map<String,Object>> list){
		List<StringMap> ret = new ArrayList<StringMap>();
		list.forEach(el ->{
			ret.add(new StringMap(el));
		});
		return ret;
	}
}