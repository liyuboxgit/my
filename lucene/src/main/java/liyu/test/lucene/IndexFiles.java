package liyu.test.lucene;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
/**
 * 
 * @Description 此类描述的是：创建索引，配置docsPath和indexesPath
 * @author: you@rthdtax.com
 * @version: 2018年9月10日 上午11:07:14
 */
public class IndexFiles {
	private static String docsPath = "c:\\root\\docs";
	private static String indexesPath = "c:\\root\\indexes";
	public static void main(String[] args) {	
		Path docDir = Paths.get(docsPath, new String[0]);
		if (!Files.isReadable(docDir)) {
			System.out.println("Document directory '" + docDir.toAbsolutePath()
					+ "' does not exist or is not readable, please check the path");
			System.exit(1);
		}

		Date start = new Date();
		try {
			System.out.println("Indexing to directory '" + indexesPath + "'...");

			Directory dir = FSDirectory.open(Paths.get(indexesPath, new String[0]));
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

			iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
			//iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

			IndexWriter writer = new IndexWriter(dir, iwc);
			indexDocs(writer, docDir);

			writer.close();

			Date end = new Date();
			System.out.println(end.getTime() - start.getTime() + " total milliseconds");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void indexDocs(final IndexWriter writer, Path path) throws IOException {
		if (Files.isDirectory(path, new LinkOption[0])) {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					try {
						IndexFiles.indexDoc(writer, file, attrs.lastModifiedTime().toMillis());
					} catch (IOException localIOException) {
						localIOException.printStackTrace();
					}

					return FileVisitResult.CONTINUE;
				}
			});
		} else {
			indexDoc(writer, path, Files.getLastModifiedTime(path, new LinkOption[0]).toMillis());
		}
	}

	static void indexDoc(IndexWriter writer, Path file, long lastModified) throws IOException {
		InputStream stream = Files.newInputStream(file, new OpenOption[0]);
		Throwable localThrowable3 = null;
		try {
			Document doc = new Document();

			Field pathField = new StringField("path", file.toString(), Field.Store.YES);
			doc.add(pathField);

			doc.add(new LongPoint("modified", new long[] { lastModified }));

			doc.add(new TextField("contents",
					new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))));

			if (writer.getConfig().getOpenMode() == IndexWriterConfig.OpenMode.CREATE) {
				System.out.println("adding " + file);
				writer.addDocument(doc);

			} else {
				System.out.println("updating " + file);
				writer.updateDocument(new Term("path", file.toString()), doc);
			}
		} catch (Throwable localThrowable1) {
			localThrowable1.printStackTrace();
		} finally {
			if (stream != null)
				if (localThrowable3 != null)
					try {
						stream.close();
					} catch (Throwable localThrowable2) {
						localThrowable3.addSuppressed(localThrowable2);
					}
				else
					stream.close();
		}
	}
}
