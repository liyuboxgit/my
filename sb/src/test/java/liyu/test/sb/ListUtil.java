package liyu.test.sb;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {
	/**
	 * 分割list
	 * @param list
	 * @param pageSize
	 * @return
	 */
	public static <T> List<List<T>> subList(List<T> list,int pageSize){
		int page = list.size()/pageSize == 0?list.size()/5:(list.size()/pageSize)+1;
		List<List<T>> ret = new ArrayList<List<T>>();
		for(int i=0;i<page;i++) {
			List<T> sub = new ArrayList<T>();
			for(int j = pageSize*i;j< pageSize*i+pageSize;j++) {
				if(j==list.size())
					break;
				sub.add(list.get(j));
			}
			ret.add(sub);
		}
		
		return ret;
	}
}