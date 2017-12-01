/**
 * @Title: IGenericPage.java
 * @Copyright (C) 2017 zstimes
 * @Description:
 * @Revision History:
 * @Revision 1.0 2017-06-13  hongtongliang
 */
 

package liyu.test.mybatis.util;

import java.util.List;

/**
 * @ClassName: IGenericPage
 * @Description: Description of this class
 * @author <a href="mailto:hongtongliang@zstax.com">hongtongliang</a>于 2017-06-13 上午10:05:10
 */

public interface IGenericPage<T> {

    /**
     * 是否为第一页
     * @return 是否为第一页
     */
    public boolean isFirstPage();

    /** 
     * 是否为最后一页 
     * @return 是否为最后一页 
     */ 
    public boolean isLastPage();

    /**
     * 有无下一页 
     * @return 真(true)为有下一页，假(false)为无
     */ 
    public boolean hasNextPage();

    /**
     * 有无上一页 
     * @return 真(true)为有上一页，假(false)为无
     */ 
    public boolean hasPreviousPage();

    /** 
     * 得到最后一页的页号 
     * @return 最后一页的页号 
     */ 
    public int getLastPageNo();

    /** 
    * 获取当前页中包含的数据
    * @return 当前页中包含的数据
    */
    public List<T> getThisPageElements();

    /** 
    * 得到数据总个数
    * @return 数据总个数
    */ 
    public int getTotalCount();

    /** 
    * 获取当前页第一条记录在所有记录的编号，即数据项范围上标
    * @return 当前页第一条记录在所有记录的编号
    */
    int getThisPageFirstElementNumber();

    /** 
    * 获取当前页最后一条记录在所有记录的编号 
    * @return 当前页最后一条记录在所有记录的编号
    */
    public int getThisPageLastElementNumber();
    
    /** 
    * 得到下一页的页号 
    * @return 下一页的页号
    */ 
    public int getNextPageNo();

    /** 
    * 得到前一页的页号 
    * @return 前一页的页号
    */ 
    public int getPreviousPageNo();

    /** 
    * 得到设定的页大小（每页数据个数） 
    * @return 页大小 （每页数据个数）
    */ 
    public int getPageSize();

    /** 
    * 得到当前页的页号 
    * @return 当前页的页号 
    */ 
    public int getPageNo();
}
