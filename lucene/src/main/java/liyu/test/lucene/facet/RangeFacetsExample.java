package liyu.test.lucene.facet;

import java.io.Closeable;
import java.io.IOException;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.facet.DrillDownQuery;
import org.apache.lucene.facet.DrillSideways;
import org.apache.lucene.facet.FacetResult;
import org.apache.lucene.facet.Facets;
import org.apache.lucene.facet.FacetsCollector;
import org.apache.lucene.facet.FacetsConfig;
import org.apache.lucene.facet.range.LongRange;
import org.apache.lucene.facet.range.LongRangeFacetCounts;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class RangeFacetsExample implements Closeable {
	private final Directory indexDir = new RAMDirectory();
	private IndexSearcher searcher;
	private final long nowSec = System.currentTimeMillis();

	final LongRange PAST_HOUR = new LongRange("Past hour", this.nowSec - 3600L, true, this.nowSec, true);
	final LongRange PAST_SIX_HOURS = new LongRange("Past six hours", this.nowSec - 21600L, true, this.nowSec, true);
	final LongRange PAST_DAY = new LongRange("Past day", this.nowSec - 86400L, true, this.nowSec, true);

	public void index() throws IOException {
		IndexWriter indexWriter = new IndexWriter(this.indexDir,
				new IndexWriterConfig(new WhitespaceAnalyzer()).setOpenMode(IndexWriterConfig.OpenMode.CREATE));

		for (int i = 0; i < 100; i++) {
			Document doc = new Document();
			long then = this.nowSec - i * 1000;

			doc.add(new NumericDocValuesField("timestamp", then));

			doc.add(new LongPoint("timestamp", new long[] { then }));
			indexWriter.addDocument(doc);
		}

		this.searcher = new IndexSearcher(DirectoryReader.open(indexWriter));
		indexWriter.close();
	}

	private FacetsConfig getConfig() {
		return new FacetsConfig();
	}

	public FacetResult search() throws IOException {
		FacetsCollector fc = new FacetsCollector();

		FacetsCollector.search(this.searcher, new MatchAllDocsQuery(), 10, fc);

		Facets facets = new LongRangeFacetCounts("timestamp", fc,
				new LongRange[] { this.PAST_HOUR, this.PAST_SIX_HOURS, this.PAST_DAY });

		return facets.getTopChildren(10, "timestamp", new String[0]);
	}

	public TopDocs drillDown(LongRange range) throws IOException {
		DrillDownQuery q = new DrillDownQuery(getConfig());

		q.add("timestamp", LongPoint.newRangeQuery("timestamp", range.min, range.max));
		return this.searcher.search(q, 10);
	}

	public DrillSideways.DrillSidewaysResult drillSideways(LongRange range) throws IOException {
		DrillDownQuery q = new DrillDownQuery(getConfig());
		q.add("timestamp", LongPoint.newRangeQuery("timestamp", range.min, range.max));

		DrillSideways.DrillSidewaysResult result = new DrillSideways(this.searcher, getConfig(), null, null) {
			protected Facets buildFacetsResult(FacetsCollector drillDowns, FacetsCollector[] drillSideways,
					String[] drillSidewaysDims) throws IOException {
				assert (drillSidewaysDims[0].equals("timestamp"));
				return new LongRangeFacetCounts("timestamp", drillSideways[0],
						new LongRange[] { RangeFacetsExample.this.PAST_HOUR, RangeFacetsExample.this.PAST_SIX_HOURS,
								RangeFacetsExample.this.PAST_DAY });
			}
		}.search(q, 10);

		return result;
	}

	public void close() throws IOException {
		this.searcher.getIndexReader().close();
		this.indexDir.close();
	}

	public static void main(String[] args) throws Exception {
		RangeFacetsExample example = new RangeFacetsExample();
		example.index();

		System.out.println("Facet counting example:");
		System.out.println("-----------------------");
		System.out.println(example.search());

		System.out.println("\n");
		System.out.println("Facet drill-down example (timestamp/Past six hours):");
		System.out.println("---------------------------------------------");
		TopDocs hits = example.drillDown(example.PAST_SIX_HOURS);
		System.out.println(hits.totalHits + " totalHits");

		System.out.println("\n");
		System.out.println("Facet drill-sideways example (timestamp/Past six hours):");
		System.out.println("---------------------------------------------");
		DrillSideways.DrillSidewaysResult sideways = example.drillSideways(example.PAST_SIX_HOURS);
		System.out.println(sideways.hits.totalHits + " totalHits");
		System.out.println(sideways.facets.getTopChildren(10, "timestamp", new String[0]));

		example.close();
	}
}
