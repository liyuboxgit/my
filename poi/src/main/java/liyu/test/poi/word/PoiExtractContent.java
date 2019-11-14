package liyu.test.poi.word;

import java.util.Map;

public interface PoiExtractContent<T> {

/**
* 根据文件路径获得 Document 对象
* @param docPath 路径
* @return Document
*/
public T getDocument(String docPath);

/**
* 解析 word 文档的标题
* @param doc Document 对象
* @return word 文档中标题
*/
public String getTilte(T doc);

/**
* 获取 word 文档里所有文字内容（不包括图片、表格等格式的内容）
* @param doc Document 对象
* @return word 文档中文字部分全部内容
*/
public String getContent(T doc);

/**
* 获取 word 文档里所有文字内容（不包括图片、表格等格式的内容）
* @param docPath doc 对象路径
* @return word 文档中文字部分全部内容
*/
public String getContent(String docPath);

/**
* 获取 word 文档里面所有图片并另存到指定目录下
* @param doc Document 对象
* @param picPath 保存图片路径
* @param suffix 后缀名
*/
public void getPictures(T doc, String picPath, String suffix);

/**
* 获取word 文档里面所有表格
* @param doc Document 对象
*/
public void getTables(T doc);

/**
* 获取word 文档中最大的字体
* @param doc Document 对象
* @return 最大字体
*/
public int getMaxFontSize(T doc);

/**
* 获取 word 文档的创建信息
* @param docPath doc路径
* @return 创建文档的信息
*/
public Map<String, String> getInfo(String docPath);

}