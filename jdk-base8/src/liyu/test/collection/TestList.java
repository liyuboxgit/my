package liyu.test.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestList {
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
	
	public static void main(String[] args) {
		//subList
		List<Integer> list = Arrays.asList(new Integer[] {1,2,3,4,5,6,7});
		List<List<Integer>> subList = subList(list, 3);
		subList.stream().forEach(
			e->{System.out.println(e);System.out.println("--");}
		);
	}
}
