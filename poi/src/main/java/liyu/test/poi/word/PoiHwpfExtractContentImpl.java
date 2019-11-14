package liyu.test.poi.word;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.model.StyleDescription;
import org.apache.poi.hwpf.model.StyleSheet;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;


/**
* 使用 POI 解析 DOC2003 文件的内容信息
* @author yongqian.liu
* 2015-2-9
*/
public class PoiHwpfExtractContentImpl implements PoiExtractContent<HWPFDocument> {

/**
* 根据文件路径获得 Document 对象
* @param docPath 路径
* @return Document
*/
public HWPFDocument getDocument(String docPath) {
// hwpfDocument 是专门处理 word 的，在 poi 中还有处理其他 office 文档的类
HWPFDocument doc = null;
try {
doc = new HWPFDocument(new FileInputStream(docPath));
} catch (Exception e) {
e.printStackTrace();
}
return doc;
}

/**
* 解析 word 文档的标题
* @param doc Document 对象
* @return 标题
*/
public String getTilte(HWPFDocument doc) {
String title = "\\";
Range range = doc.getRange();
Paragraph p = null;
for (int i = 0; i < range.numParagraphs(); i++) {
p = range.getParagraph(i);
if(p.text() != null && !p.text().equals("") && !p.text().equals("r")){
title = p.text().trim();
break;
}
}
return title;
}

/**
* 获取 word 文档里所有文字内容（不包括图片、表格等格式的内容）
* @param doc Document 对象
* @return word 文档中文字部分全部内容
*/
public String getContent(HWPFDocument doc){
String content = "";
try {
content = doc.getText().toString().trim();
} catch (Exception e) {
e.printStackTrace();
}
return content.replaceAll("", "");
}

/**
* 获取 word 文档里所有文字内容（不包括图片、表格等格式的内容）
* @param docPath doc 对象路径
* @return word 文档中文字部分全部内容
*/
public String getContent(String docPath) {
StringBuffer strBuff = new StringBuffer("");
try {
WordExtractor extractor = new WordExtractor(new FileInputStream(docPath));
//extractor.getTextFromPieces();
String [] strArray = extractor.getParagraphText();
for(int i = 0; i < strArray.length; ++i) {
strBuff.append(strArray[i].trim());
}
} catch (Exception e) {
e.printStackTrace();
}
return strBuff.toString().replaceAll("", "");
}

/**
* 获取 word 文档里面所有图片并另存到指定目录下
* @param doc Document 对象
* @param picPath 保存图片路径
* @param suffix 后缀名
*/
public void getPictures(HWPFDocument doc, String picPath, String suffix) {
Range range = doc.getRange();
byte[] dataStream = doc.getDataStream();
int numChar = range.numCharacterRuns();
PicturesTable pTable = new PicturesTable(doc, dataStream, dataStream);
for (int i = 0; i < numChar; ++i) {
CharacterRun cuRun = range.getCharacterRun(i);
boolean hasPic = pTable.hasPicture(cuRun);
if (hasPic) {
Picture picture = pTable.extractPicture(cuRun, true);
try {
picture.writeImageContent(new FileOutputStream(picPath + i + suffix));
} catch (Exception e) {
e.printStackTrace();
}
}
}
}

/**
* 获取word 文档里面所有表格
* @param doc Document 对象
*/
public void getTables(HWPFDocument doc){
Range range = doc.getRange();
TableIterator tableIt = new TableIterator(range);
while (tableIt.hasNext()) {
Table table = (Table)tableIt.next();
for(int j=0;j<table.numRows();j++){
TableRow tr = table.getRow(j);
String content = "";
for(int i=0;i<tr.numCells();i++){
TableCell cell = tr.getCell(i);
for(int m=0;m<cell.numParagraphs();m++){ //获取单元格内容
Paragraph para = cell.getParagraph(m);
content += para.text().trim() + ";";
}
}
System.out.println(content);
}
}
}


/**
* 获取文章中所有标题集合
* @param doc Document
* @return
*/
public List<String> getTitleList(HWPFDocument doc){

Range range = doc.getRange();
byte[] dataStream = doc.getDataStream();
int numP = range.numParagraphs();
List<String> titleList = new ArrayList<String>();

PicturesTable pTable = new PicturesTable(doc, dataStream, dataStream);
for(int i=0;i<numP;i++){
Range curRange = range.getParagraph(i);
Paragraph paragraph = range.getParagraph(i);
CharacterRun cr = curRange.getCharacterRun(0);
if(pTable.hasPicture(cr)){ //图片
continue;
}else{
char currentChar = 0;
for(int k=0;k<cr.text().length();k++){
currentChar = cr.text().charAt(k);
if(currentChar != Const.SPACE_ASCII){
break;
}
}

if(currentChar == Const.ENTER_ASCII){ //回车符
continue;
}else if(currentChar == Const.SPACE_ASCII){ //空格符
continue;
}else if(currentChar == Const.TABULATION_ASCII){ //水平制表符
continue;
}
}

int numStyles = doc.getStyleSheet().numStyles();
int styleIndex = paragraph.getStyleIndex();
if (numStyles > styleIndex) {
StyleSheet style_sheet = doc.getStyleSheet();
StyleDescription style = style_sheet.getStyleDescription(styleIndex);
String styleName = style.getName();

if(styleName!=null&&styleName.contains("标题")){
titleList.add(paragraph.text().trim());
System.out.println(paragraph.text().trim());
}
}
}
return titleList;
}

/**
* 获取整篇文章中所有标题样式名称
* @param doc Document
* @return
*/
public Set<String> getTitleStyleNameSet(HWPFDocument doc){
Range range = doc.getRange();
byte[] dataStream = doc.getDataStream();
int numP = range.numParagraphs();
Set<String> titNameSet = new HashSet<String>();

PicturesTable pTable = new PicturesTable(doc, dataStream, dataStream);
for(int i=0;i<numP;i++){
Range curRange = range.getParagraph(i);
Paragraph paragraph = range.getParagraph(i);
CharacterRun cr = curRange.getCharacterRun(0);
if(pTable.hasPicture(cr)){ //图片
continue;
}else{
char currentChar = 0;
for(int k=0;k<cr.text().length();k++){
currentChar = cr.text().charAt(k);
if(currentChar != Const.SPACE_ASCII){
break;
}
}
if(currentChar == Const.ENTER_ASCII){ //回车符
continue;
}else if(currentChar == Const.SPACE_ASCII){ //空格符
continue;
}else if(currentChar == Const.TABULATION_ASCII){ //水平制表符
continue;
}
}
int numStyles = doc.getStyleSheet().numStyles();
int styleIndex = paragraph.getStyleIndex();
if (numStyles > styleIndex) {
StyleSheet style_sheet = doc.getStyleSheet();
StyleDescription style = style_sheet.getStyleDescription(styleIndex);
String styleName = style.getName();
if(styleName!=null&&styleName.contains("标题")){
if(styleName.contains(",")){
styleName = getFirstStyleName(styleName);
}
titNameSet.add(styleName);
}
}
}
return titNameSet;
}

/**
* 处理标题样式名称的特殊格式，如：“标题 3,标题 3 Char,标题 3 Char Char” ,只获取“标题 3”
* @param styleName 需进行处理的标题样式 ，如"标题 3,标题 3 Char,标题 3 Char Char”
* @return
*/
private String getFirstStyleName(String styleName){
if ((styleName != null) && (styleName.length() > 0)) { 
int styleLeng = styleName.split(",").length;
if(styleLeng>1){
int comma = styleName.indexOf(",");
if(comma>-1&&(comma<styleName.length())){
return styleName.substring(0,comma);
}
}
} 
return styleName;
}

/**
* 获取当前文章中最大标题样式名称，如“标题1”
* @param doc Document
* @return
*/
public String getMaxTitleStyleName(HWPFDocument doc){
Set<String> titNameSet = getTitleStyleNameSet(doc);
Iterator<String> it = titNameSet.iterator();
List<Integer> tempLst = new ArrayList<Integer>();
while(it.hasNext()){
String titName = it.next(); //得到“标题 1”、“标题 2”
try {
int curStyleName = Integer.parseInt(titName.substring(2).trim());
tempLst.add(curStyleName);
} catch (NumberFormatException e) {
continue;
}
}
int max = (tempLst.size()==0?0:tempLst.get(0));
for(int i=0;i<tempLst.size();i++){
int curSize = tempLst.get(i);
if(curSize<max){
max = curSize;
}
}
if(max==0){
return ""; //文章中不包含任何标题 
}
return "标题 "+max;
}

/**
* 获取word 文档中最大的字体
* @param doc Document 对象
*/
public int getMaxFontSize(HWPFDocument doc) {
int fontSize = 0;
try {
Range range = doc.getRange();
for (int i = 0; i < range.numParagraphs(); i++) {
Paragraph poiPara = range.getParagraph(i);
int j = 0;
while (true) {
CharacterRun run = poiPara.getCharacterRun(j++);
if(fontSize < run.getFontSize()) {
fontSize = run.getFontSize();
}//字体大小
if (run.getEndOffset() == poiPara.getEndOffset()) {
break;
}
}
}
} catch (Exception e) {
e.printStackTrace();
}
return fontSize;
}

/**
* 获取 word 文档的创建信息
* @param docPath doc路径
* @return 创建文档的信息
*/
public Map<String, String> getInfo(String docPath) {
try {
InputStream is = new FileInputStream(docPath); 
WordExtractor extractor = new WordExtractor(is);
SummaryInformation info = extractor.getSummaryInformation();
Map<String, String> mapInfo = new HashMap<String, String>();
mapInfo.put("author", info.getAuthor()); // 作者
mapInfo.put("title", info.getTitle()); // 标题
mapInfo.put("subject", info.getSubject()); // 主题
mapInfo.put("keyword", info.getKeywords()); // 关键词
mapInfo.put("createdate", FormatTextUtil.dateFormat(info.getCreateDateTime())); // 创建时间
mapInfo.put("updatedate", FormatTextUtil.dateFormat(info.getLastSaveDateTime())); // 修改时间
} catch (Exception e) {
}
return null;
}
}
