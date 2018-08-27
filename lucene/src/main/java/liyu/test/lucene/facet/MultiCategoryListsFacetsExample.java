package liyu.test.lucene.facet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.facet.FacetField;
import org.apache.lucene.facet.FacetResult;
import org.apache.lucene.facet.Facets;
import org.apache.lucene.facet.FacetsCollector;
import org.apache.lucene.facet.FacetsConfig;
import org.apache.lucene.facet.taxonomy.FastTaxonomyFacetCounts;
import org.apache.lucene.facet.taxonomy.TaxonomyReader;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyReader;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyWriter;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class MultiCategoryListsFacetsExample {
	private final Directory indexDir = new RAMDirectory();
	private final Directory taxoDir = new RAMDirectory();
	private final FacetsConfig config = new FacetsConfig();

	public MultiCategoryListsFacetsExample() {
		this.config.setIndexFieldName("Author", "author");
		this.config.setIndexFieldName("Publish Date", "pubdate");
		this.config.setHierarchical("Publish Date", true);
	}

	private void index() throws IOException {
		IndexWriter indexWriter = new IndexWriter(this.indexDir,
				new IndexWriterConfig(new WhitespaceAnalyzer()).setOpenMode(IndexWriterConfig.OpenMode.CREATE));

		DirectoryTaxonomyWriter taxoWriter = new DirectoryTaxonomyWriter(this.taxoDir);

		Document doc = new Document();
		doc.add(new FacetField("Author", new String[] { "Bob" }));
		doc.add(new FacetField("Publish Date", new String[] { "2010", "10", "15" }));
		indexWriter.addDocument(this.config.build(taxoWriter, doc));

		doc = new Document();
		doc.add(new FacetField("Author", new String[] { "Lisa" }));
		doc.add(new FacetField("Publish Date", new String[] { "2010", "10", "20" }));
		indexWriter.addDocument(this.config.build(taxoWriter, doc));

		doc = new Document();
		doc.add(new FacetField("Author", new String[] { "Lisa" }));
		doc.add(new FacetField("Publish Date", new String[] { "2012", "1", "1" }));
		indexWriter.addDocument(this.config.build(taxoWriter, doc));

		doc = new Document();
		doc.add(new FacetField("Author", new String[] { "Susan" }));
		doc.add(new FacetField("Publish Date", new String[] { "2012", "1", "7" }));
		indexWriter.addDocument(this.config.build(taxoWriter, doc));

		doc = new Document();
		doc.add(new FacetField("Author", new String[] { "Frank" }));
		doc.add(new FacetField("Publish Date", new String[] { "1999", "5", "5" }));
		indexWriter.addDocument(this.config.build(taxoWriter, doc));

		indexWriter.close();
		taxoWriter.close();
	}

	private List<FacetResult> search() throws IOException {
		DirectoryReader indexReader = DirectoryReader.open(this.indexDir);
		IndexSearcher searcher = new IndexSearcher(indexReader);
		TaxonomyReader taxoReader = new DirectoryTaxonomyReader(this.taxoDir);

		FacetsCollector fc = new FacetsCollector();

		FacetsCollector.search(searcher, new MatchAllDocsQuery(), 10, fc);

		List<FacetResult> results = new ArrayList<FacetResult>();

		Facets author = new FastTaxonomyFacetCounts("author", taxoReader, this.config, fc);
		results.add(author.getTopChildren(10, "Author", new String[0]));

		Facets pubDate = new FastTaxonomyFacetCounts("pubdate", taxoReader, this.config, fc);
		results.add(pubDate.getTopChildren(10, "Publish Date", new String[0]));

		indexReader.close();
		taxoReader.close();

		return results;
	}

	public List<FacetResult> runSearch() throws IOException {
		index();
		return search();
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Facet counting over multiple category lists example:");
		System.out.println("-----------------------");
		List<FacetResult> results = new MultiCategoryListsFacetsExample().runSearch();
		System.out.println("Author: " + results.get(0));
		System.out.println("Publish Date: " + results.get(1));
	}
}
