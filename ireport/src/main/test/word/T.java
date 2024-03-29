package word;


  
import java.io.File;  
import java.io.FileOutputStream;  
import java.math.BigInteger;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;

  
  
/** 
 * Created by zhouhs on 2017/1/9. 
 */  
public class T {  
  
    public static void main(String[] args)throws Exception {  
        /*//Blank Document  
        XWPFDocument document= new XWPFDocument();  
  
        //Write the Document in file system  
        FileOutputStream out = new FileOutputStream(new File("c:\\create_table.docx"));  
  
  
        //添加标题  
        XWPFParagraph titleParagraph = document.createParagraph();  
        //设置段落居中  
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);  
  
        XWPFRun titleParagraphRun = titleParagraph.createRun();  
        titleParagraphRun.setText("Java PoI");  
        //titleParagraphRun.setColor("000000");  
        titleParagraphRun.setFontSize(20);  
  
  
        //段落  
        XWPFParagraph firstParagraph = document.createParagraph();  
        XWPFRun run = firstParagraph.createRun();  
        run.setText("Java POI 生成word文件。");  
        //run.setColor("696969");  
        run.setFontSize(16);  
  
        //设置段落背景颜色  
        //CTShd cTShd = run.getCTR().addNewRPr().addNewShd();  
        //cTShd.setVal(STShd.CLEAR);  
        //cTShd.setFill("97FFFF");  
  
  
        //换行  
        XWPFParagraph paragraph1 = document.createParagraph();  
        XWPFRun paragraphRun1 = paragraph1.createRun();  
        paragraphRun1.setText("\r");  
  
  
        //基本信息表格  
        XWPFTable infoTable = document.createTable();  
        //去表格边框  
        infoTable.getCTTbl().getTblPr().unsetTblBorders();  
  
  
        //列宽自动分割  
        CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();  
        infoTableWidth.setType(STTblWidth.DXA);  
        infoTableWidth.setW(BigInteger.valueOf(9072));  
  
  
        //表格第一行  
        XWPFTableRow infoTableRowOne = infoTable.getRow(0);  
        infoTableRowOne.getCell(0).setText("职位");  
        infoTableRowOne.addNewTableCell().setText(": Java 开发工程师");  
  
        //表格第二行  
        XWPFTableRow infoTableRowTwo = infoTable.createRow();  
        infoTableRowTwo.getCell(0).setText("姓名");  
        infoTableRowTwo.getCell(1).setText(": seawater");  
  
        //表格第三行  
        XWPFTableRow infoTableRowThree = infoTable.createRow();  
        infoTableRowThree.getCell(0).setText("生日");  
        infoTableRowThree.getCell(1).setText(": xxx-xx-xx");  
  
        //表格第四行  
        XWPFTableRow infoTableRowFour = infoTable.createRow();  
        infoTableRowFour.getCell(0).setText("性别");  
        infoTableRowFour.getCell(1).setText(": 男");  
  
        //表格第五行  
        XWPFTableRow infoTableRowFive = infoTable.createRow();  
        infoTableRowFive.getCell(0).setText("现居地");  
        infoTableRowFive.getCell(1).setText(": xx");  
  
  
        //两个表格之间加个换行  
        XWPFParagraph paragraph = document.createParagraph();  
        XWPFRun paragraphRun = paragraph.createRun();  
        paragraphRun.setText("\r");  
  
  
  
        //工作经历表格  
        XWPFTable ComTable = document.createTable();  
  
  
        //列宽自动分割  
        CTTblWidth comTableWidth = ComTable.getCTTbl().addNewTblPr().addNewTblW();  
        comTableWidth.setType(STTblWidth.DXA);  
        comTableWidth.setW(BigInteger.valueOf(9072));  
  
        //表格第一行  
        XWPFTableRow comTableRowOne = ComTable.getRow(0);  
        comTableRowOne.getCell(0).setText("开始时间");  
        comTableRowOne.addNewTableCell().setText("结束时间");  
        comTableRowOne.addNewTableCell().setText("公司名称");  
        comTableRowOne.addNewTableCell().setText("title");  
  
        //表格第二行  
        XWPFTableRow comTableRowTwo = ComTable.createRow();  
        comTableRowTwo.getCell(0).setText("2016-09-06");  
        comTableRowTwo.getCell(1).setText("至今");  
        comTableRowTwo.getCell(2).setText("seawater");  
        comTableRowTwo.getCell(3).setText("Java开发工程师");  
  
        //表格第三行  
        XWPFTableRow comTableRowThree = ComTable.createRow();  
        comTableRowThree.getCell(0).setText("2016-09-06");  
        comTableRowThree.getCell(1).setText("至今");  
        comTableRowThree.getCell(2).setText("seawater");  
        comTableRowThree.getCell(3).setText("Java开发工程师");  
  
  
        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();  
        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr);  
  
        //添加页眉  
        CTP ctpHeader = CTP.Factory.newInstance();  
        CTR ctrHeader = ctpHeader.addNewR();  
        CTText ctHeader = ctrHeader.addNewT();  
        String headerText = "Java POI create MS word file.";  
        ctHeader.setStringValue(headerText);  
        XWPFParagraph headerParagraph = new XWPFParagraph(ctpHeader, document);  
        //设置为右对齐  
        headerParagraph.setAlignment(ParagraphAlignment.RIGHT);  
        XWPFParagraph[] parsHeader = new XWPFParagraph[1];  
        parsHeader[0] = headerParagraph;  
        policy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, parsHeader);  
  
  
        //添加页脚  
        CTP ctpFooter = CTP.Factory.newInstance();  
        CTR ctrFooter = ctpFooter.addNewR();  
        CTText ctFooter = ctrFooter.addNewT();  
        String footerText = "http://blog.csdn.net/zhouseawater";  
        ctFooter.setStringValue(footerText);  
        XWPFParagraph footerParagraph = new XWPFParagraph(ctpFooter, document);  
        headerParagraph.setAlignment(ParagraphAlignment.CENTER);  
        XWPFParagraph[] parsFooter = new XWPFParagraph[1];  
        parsFooter[0] = footerParagraph;  
        policy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, parsFooter);  
  
  
        document.write(out);  
        out.close();  
        System.out.println("create_table document written success.");  */
    	
    	
    	CustomXWPFDocument document = new CustomXWPFDocument();
    	
        //设置页边距
        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        CTPageMar pageMar = sectPr.addNewPgMar();
        pageMar.setLeft(BigInteger.valueOf(1000L));
        pageMar.setTop(BigInteger.valueOf(1440L));
        pageMar.setRight(BigInteger.valueOf(1000L));
        pageMar.setBottom(BigInteger.valueOf(1440L));
        //标题
        setTitle(document);
        
        FileOutputStream out = null;
        out = new FileOutputStream(new File("C:/t.docx"));
       
        document.write(out);
    }  
  
    public static void setTitle(CustomXWPFDocument document){
        XWPFParagraph titleParagraph = document.createParagraph();//添加标题
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);//设置段落居中
        titleParagraph.setSpacingAfter(200);
        //标题
        XWPFRun titleParagraphRun = titleParagraph.createRun();
        titleParagraphRun.setText("发票风险评估报告");
        titleParagraphRun.setFontSize(18);
        titleParagraphRun.setBold(true);//加粗
    }
}  
