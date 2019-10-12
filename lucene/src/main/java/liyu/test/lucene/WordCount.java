package liyu.test.lucene;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.LeafReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class WordCount {
    static Directory directory;
    // 创建分词器
    static Analyzer analyzer = new IKAnalyzer();
    static IndexWriterConfig config = new IndexWriterConfig(analyzer);
    static IndexWriter writer;
    static IndexReader reader;
    static {
        // 指定索引存放目录以及配置参数
        try {
            File file = new File(System.getProperty("user.home")+File.separator+"luceneIndex");
            if (!file.exists()) {
                file.mkdirs();
            } else {
                try {
                    file.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
     
            directory = FSDirectory.open(file.toPath());
            writer = new IndexWriter(directory, config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        indexCreate();
        Map<String, Long> map = getTotalFreqMap();
        Map<String, Long> sortMap = sortMapByValue(map);
        Set<Entry<String, Long>> entrySet = sortMap.entrySet();
        Iterator<Entry<String, Long>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Entry<String, Long> entry = iterator.next();
            System.out.println(entry.getKey() + "----" + entry.getValue());
        }

    }

    /**
     * 创建索引
     */
    public static void indexCreate() {
        
        // 将采集的数据封装到Document中
        Document doc = new Document();
        FieldType ft = new FieldType();
        ft.setIndexOptions(IndexOptions.DOCS_AND_FREQS);
        ft.setStored(true);
        ft.setStoreTermVectors(true);
        ft.setTokenized(true);
        // ft.setStoreTermVectorOffsets(true);
        // ft.setStoreTermVectorPositions(true);

        // 读取文件内容(小文件,readFully)
        /*File content = new File("work"+File.separator+"twitter.txt");
        try {
            byte[] buffer = new byte[(int) content.length()];
            IOUtils.readFully(new FileInputStream(content), buffer);
            doc.add(new Field("twitter", new String(buffer), ft));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        
        // 读取String
        try {
            String cont = "滚滚长江东逝水，浪花淘尽英雄，是非成败转头空，青山依旧在，几度夕阳红。";
            doc.add(new Field("twitter", cont, ft));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 生成索引
        try {
            writer.addDocument(doc);
            // 关闭
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得词频map
     * 
     * @throws ParseException
     */
    public static Map<String, Long> getTotalFreqMap() {
        Map<String, Long> map = new HashMap<String, Long>();
        try {
            reader = DirectoryReader.open(directory);
            List<LeafReaderContext> leaves = reader.leaves();
            for (LeafReaderContext leafReaderContext : leaves) {
                LeafReader leafReader = leafReaderContext.reader();

                Terms terms = leafReader.terms("twitter");

                TermsEnum iterator = terms.iterator();

                BytesRef term = null;

                while ((term = iterator.next()) != null) {
                    String text = term.utf8ToString();
                    map.put(text, iterator.totalTermFreq());
                }

            }
            reader.close();
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用 Map按value进行排序
     * 
     * @param map
     * @return
     */
    public static Map<String, Long> sortMapByValue(Map<String, Long> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<String, Long> sortedMap = new LinkedHashMap<String, Long>();

        List<Map.Entry<String, Long>> entryList = new ArrayList<Map.Entry<String, Long>>(oriMap.entrySet());
        Collections.sort(entryList, new MapValueComparator());

        Iterator<Map.Entry<String, Long>> iter = entryList.iterator();
        Map.Entry<String, Long> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }
}

class MapValueComparator implements Comparator<Map.Entry<String, Long>> {

    public int compare(Entry<String, Long> me1, Entry<String, Long> me2) {
        if (me1.getValue() == me2.getValue()) {
            return 0;
        }
        return me1.getValue() > me2.getValue() ? -1 : 1;
        // return me1.getValue().compareTo(me2.getValue());
    }
}
