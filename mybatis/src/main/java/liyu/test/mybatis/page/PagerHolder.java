package liyu.test.mybatis.page;

public class PagerHolder {
	public static final ThreadLocal<Pager<Object>> localPage = new ThreadLocal<Pager<Object>>();
	
	public static void startPager(int pageNum, int pageSize) {
        localPage.set(new Pager<Object>(pageNum, pageSize));
    }

	@SuppressWarnings("unchecked")
	public static <T> Pager<T> endPager(Class<T> type) {
        Pager<Object> pager = localPage.get();
        localPage.remove();
        return (Pager<T>) pager;
    }
}
