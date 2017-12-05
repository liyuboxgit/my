package liyu.test.framework.mybatis.page;

public class PagerHolder {
	public static final ThreadLocal<Pager<Object>> localPage = new ThreadLocal<Pager<Object>>();
	
	public static void startPager(int pageNum, int pageSize) {
        localPage.set(new Pager<Object>(pageNum, pageSize));
    }

	public static Pager<Object> endPager() {
        Pager<Object> pager = localPage.get();
        localPage.remove();
        return pager;
    }
}
