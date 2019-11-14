package liyu.test.poi.word;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.POIXMLProperties.CoreProperties;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;



/**
* 使用 POI 解析 DOCX2007 文件的内容信息
* @author yongqian.liu
* 2015-2-9
*/
public class PoiXwpfExtractContentImpl implements PoiExtractContent<XWPFDocument> {

/**
* 根据文件路径获得 Document 对象
* @param docxPath 路径
* @return Document
*/
public XWPFDocument getDocument(String docxPath) {
//xwpfDocument是专门处理word的，在poi中还有处理其他office文档的类
XWPFDocument docx = null;
try {
OPCPackage pack = POIXMLDocument.openPackage(docxPath);
docx = new XWPFDocument(pack) ;
} catch (Exception e) {
e.printStackTrace();
}
return docx;
}

/**
* 解析 word 文档的标题
* @param docx Document 对象
* @return word 文档中标题
*/
public String getTilte(XWPFDocument docx) {
String title = "\\";
List<XWPFParagraph> paras = docx.getParagraphs();
XWPFParagraph p = null;
for (int i = 0; i < paras.size(); i++) {
if(p.getText() != null && !p.getText().equals("") && !p.getText().equals("r")){
title = p.getText().trim();
break;
}
}
return title;
}

/**
* 获取 word 文档里所有文字内容（不包括图片、表格等格式的内容）
* @param docx Document 对象
* @return word 文档中文字部分全部内容
*/
public String getContent(XWPFDocument docx) {
String content = "";
try {
List<XWPFParagraph> paras = docx.getParagraphs(); 
for (XWPFParagraph para : paras) { 
content += para.getText().trim();
} 
} catch (Exception e) {
e.printStackTrace();
}
return content.replaceAll("", "");
}

/**
* 获取 word 文档里所有文字内容（不包括图片、表格等格式的内容）
* @param docxPath docx 对象路径
* @return word 文档中文字部分全部内容
*/
public String getContent(String docxPath) {
String content = "";
try {
OPCPackage opcPackage = POIXMLDocument.openPackage(docxPath);
POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
content += extractor.getText().trim();
} catch (Exception e) {
e.printStackTrace();
}
return content.replaceAll("", "");
}

/**
* 获取 word 文档里面所有图片并另存到指定目录下
* @param docx Document 对象
* @param picPath 保存图片路径
* @param suffix 后缀名
*/
public void getPictures(XWPFDocument docx, String picPath, String suffix){
List<XWPFPictureData> wpdList = docx.getAllPictures();
if(wpdList != null && wpdList.size() > 0){
for (int i = 0; i < wpdList.size(); i++) {
byte[] picByte = wpdList.get(i).getData(); //获取图片数据流
FileOutputStream fos = null;
try {
fos = new FileOutputStream(picPath + i + suffix);
} catch (FileNotFoundException e) {
e.printStackTrace();
}finally{
try {
fos.write(picByte);
} catch (IOException e) {
e.printStackTrace();
}
}
}
}
}

/**
* 获取word 文档里面所有表格
* @param doc Document 对象
*/
public void getTables(XWPFDocument docx){
Iterator<XWPFTable> tableIt = docx.getTablesIterator();
while (tableIt.hasNext()) {
XWPFTable table = tableIt.next();
String rowInfo = "";
for(int j = 0; j < table.getRows().size(); j ++){
List<XWPFTableCell> cells = table.getRow(j).getTableCells(); // 获得所有列
for (int k = 0; k < cells.size(); k++) {
rowInfo += cells.get(k).getText().trim() + ";";
}
}
System.out.println(rowInfo);
}
}

/**
* 获取word 文档中最大的字体
* @param doc Document 对象
*/
public int getMaxFontSize(XWPFDocument docx) {
int fontSize = 0;
/* List<XWPFParagraph> paraGraph = docx.getParagraphs();
for(XWPFParagraph para :paraGraph ){
List<XWPFRun> run = para.getRuns();
for(XWPFRun r : run){
int i = 0;
System.out.println("字体颜色："+r.getColor());
System.out.println("字体名称:"+r.getFontFamily());
System.out.println("字体大小："+r.getFontSize());
System.out.println("Text:"+r.getText(i++));
System.out.println("粗体？："+r.isBold());
System.out.println("斜体？："+r.isItalic());
if(fontSize < r.getFontSize()){
fontSize = r.getFontSize();
}
}
}*/

return fontSize;
}

/**
* 获取 word 文档的创建信息
* @param docPath docx路径
* @return 创建文档的信息
*/
public Map<String, String> getInfo(String docxPath) {
try {
InputStream is = new FileInputStream(docxPath);
XWPFDocument docx = new XWPFDocument(is);
XWPFWordExtractor extractor = new XWPFWordExtractor(docx);
CoreProperties coreProps = extractor.getCoreProperties();
Map<String, String> mapInfo = new HashMap<String, String>();
//mapInfo.put("category", coreProps.getCategory()); //分类
mapInfo.put("author", coreProps.getCreator()); //创建者
mapInfo.put("title", coreProps.getTitle());	//标题
mapInfo.put("subject", coreProps.getSubject()); // 主题
mapInfo.put("keyword", coreProps.getKeywords()); // 关键词
mapInfo.put("createdate", FormatTextUtil.dateFormat(coreProps.getCreated())); //创建时间
mapInfo.put("updatedate", FormatTextUtil.dateFormat(coreProps.getLastPrinted())); // 修改时间
} catch (Exception e) {
}
return null;
}
}