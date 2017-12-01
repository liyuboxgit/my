/**
 * @Title: GenericDefaultPage.java
 * @Copyright (C) 2017 zstimes
 * @Description:
 * @Revision History:
 * @Revision 1.0 2017-06-13  hongtongliang
 */
 

package liyu.test.mybatis.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName: GenericDefaultPage
 * @Description: Description of this class
 * @author <a href="mailto:hongtongliang@zstax.com">hongtongliang</a>于 2017-06-13 上午10:07:47
 */

public class GenericDefaultPage<T> implements IGenericPage<T>,Serializable {
    
    /**
     * @Fields serialVersionUID : Description
     */
    
    private static final long serialVersionUID = -5818935273125906064L;

    /** 当前页数据列表 */
    private List<T> elements;

    /** 页大小（每页数据个数） */
    private int pageSize;

    /** 当前页号 */
    private int pageNo;

    /** 数据总个数 */
    private int totalCount = 0;
    
    
    /**
     * 根据当前页号、页大小（每页数据个数）、当前页数据列表、数据总个数构造分页数据对象的实例。
     * @param pageNo 当前页号
     * @param pageSize 页大小（每页数据个数）
     * @param elements 当前页数据列表
     * @param totalCount 数据总个数
     */
    public GenericDefaultPage(
            int pageNo, int pageSize, List<T> elements, int totalCount) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.elements = elements;
        this.totalCount = totalCount;
    }
    
    /**
     * 定义一空页
     *
     * @see #emptyPage()
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    public static final GenericDefaultPage EMPTY_PAGE = new GenericDefaultPage(
            0, 0, Collections.emptyList(), 0);

    /**
     * 获取一空页
     */
    @SuppressWarnings("unchecked")
    public static <E> GenericDefaultPage<E> emptyPage() {
        return (GenericDefaultPage<E>) EMPTY_PAGE;
    }
    
    public boolean isFirstPage() {
        return getPageNo()==0;
    }

    public boolean isLastPage() {
        return getPageNo()>getLastPageNo();
    }

    public boolean hasNextPage() {       
        return null==elements?false:elements.size()>getPageSize();
    }

    public boolean hasPreviousPage() {
        return getPageNo()>0;
    }

    public int getLastPageNo() {
        double total = new Integer(getTotalCount()).doubleValue();
        return (total%getPageSize()==0)?new Double(Math.floor(total/getPageSize())).intValue():(new Double(Math.floor(total/getPageSize())).intValue()+1);
    }

    public List<T> getThisPageElements() {
        return hasNextPage()?elements.subList(0, getPageSize()):elements;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getThisPageFirstElementNumber() {
        return getPageNo()*getPageSize()+1;
    }

    public int getThisPageLastElementNumber() {
        int fullPage = getThisPageFirstElementNumber() + getPageSize() - 1;
        return getTotalCount() < fullPage
                ? getTotalCount() : fullPage;
    }

    public int getNextPageNo() {
        return getPageNo()+1;
    }

    public int getPreviousPageNo() {
        return getPageNo()-1;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }
    
    /**
     * 根据页大小（每页数据个数）获取给定页号的第一条数据在总数据中的位置（从1开始）
     * @param pageNo 给定的页号
     * @param pageSize 页大小（每页数据个数）
     * @return 给定页号的第一条数据在总数据中的位置（从1开始）
     */
    public static int getStartOfPage(int pageNo, int pageSize) {
        int startIndex = (pageNo - 1) * pageSize + 1;
        if (startIndex < 1) startIndex = 1;
        return startIndex;
    }

}
