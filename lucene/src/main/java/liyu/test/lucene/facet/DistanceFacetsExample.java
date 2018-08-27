package liyu.test.lucene.facet;

import java.io.Closeable;
import java.io.IOException;
import java.text.ParseException;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoublePoint;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.expressions.Expression;
import org.apache.lucene.expressions.SimpleBindings;
import org.apache.lucene.expressions.js.JavascriptCompiler;
import org.apache.lucene.facet.DrillDownQuery;
import org.apache.lucene.facet.DrillSideways;
import org.apache.lucene.facet.FacetResult;
import org.apache.lucene.facet.Facets;
import org.apache.lucene.facet.FacetsCollector;
import org.apache.lucene.facet.FacetsConfig;
import org.apache.lucene.facet.range.DoubleRange;
import org.apache.lucene.facet.range.DoubleRangeFacetCounts;
import org.apache.lucene.facet.taxonomy.TaxonomyReader;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.DoubleValuesSource;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.SloppyMath;

public class DistanceFacetsExample implements Closeable {
	final DoubleRange ONE_KM = new DoubleRange("< 1 km", 0.0D, true, 1.0D, false);
	final DoubleRange TWO_KM = new DoubleRange("< 2 km", 0.0D, true, 2.0D, false);
	final DoubleRange FIVE_KM = new DoubleRange("< 5 km", 0.0D, true, 5.0D, false);
	final DoubleRange TEN_KM = new DoubleRange("< 10 km", 0.0D, true, 10.0D, false);

	private final Directory indexDir = new RAMDirectory();
	private IndexSearcher searcher;
	private final FacetsConfig config = new FacetsConfig();

	public static final double ORIGIN_LATITUDE = 40.7143528D;

	public static final double ORIGIN_LONGITUDE = -74.0059731D;

	public static final double EARTH_RADIUS_KM = 6371.0087714D;

	public void index() throws IOException {
		IndexWriter writer = new IndexWriter(this.indexDir,
				new IndexWriterConfig(new WhitespaceAnalyzer()).setOpenMode(IndexWriterConfig.OpenMode.CREATE));

		Document doc = new Document();
		doc.add(new DoublePoint("latitude", new double[] { 40.759011D }));
		doc.add(new NumericDocValuesField("latitude", Double.doubleToRawLongBits(40.759011D)));
		doc.add(new DoublePoint("longitude", new double[] { -73.9844722D }));
		doc.add(new NumericDocValuesField("longitude", Double.doubleToRawLongBits(-73.9844722D)));
		writer.addDocument(doc);

		doc = new Document();
		doc.add(new DoublePoint("latitude", new double[] { 40.718266D }));
		doc.add(new NumericDocValuesField("latitude", Double.doubleToRawLongBits(40.718266D)));
		doc.add(new DoublePoint("longitude", new double[] { -74.007819D }));
		doc.add(new NumericDocValuesField("longitude", Double.doubleToRawLongBits(-74.007819D)));
		writer.addDocument(doc);

		doc = new Document();
		doc.add(new DoublePoint("latitude", new double[] { 40.7051157D }));
		doc.add(new NumericDocValuesField("latitude", Double.doubleToRawLongBits(40.7051157D)));
		doc.add(new DoublePoint("longitude", new double[] { -74.0088305D }));
		doc.add(new NumericDocValuesField("longitude", Double.doubleToRawLongBits(-74.0088305D)));
		writer.addDocument(doc);

		this.searcher = new IndexSearcher(DirectoryReader.open(writer));
		writer.close();
	}

	private DoubleValuesSource getDistanceValueSource() {
		Expression distance = null;
		try {
			distance = JavascriptCompiler.compile("haversin(40.7143528,-74.0059731,latitude,longitude)");
		} catch (ParseException pe) {
			throw new RuntimeException(pe);
		}
		SimpleBindings bindings = new SimpleBindings();
		bindings.add(new SortField("latitude", SortField.Type.DOUBLE));
		bindings.add(new SortField("longitude", SortField.Type.DOUBLE));

		return distance.getDoubleValuesSource(bindings);
	}

	public static Query getBoundingBoxQuery(double originLat, double originLng, double maxDistanceKM) {
		double originLatRadians = SloppyMath.toRadians(originLat);
		double originLngRadians = SloppyMath.toRadians(originLng);

		double angle = maxDistanceKM / 6371.0087714D;

		double minLat = originLatRadians - angle;
		double maxLat = originLatRadians + angle;

		double minLng;
		double maxLng;
		if ((minLat > SloppyMath.toRadians(-90.0D)) && (maxLat < SloppyMath.toRadians(90.0D))) {
			double delta = Math.asin(Math.sin(angle) / Math.cos(originLatRadians));
			minLng = originLngRadians - delta;
			if (minLng < SloppyMath.toRadians(-180.0D)) {
				minLng += 6.283185307179586D;
			}
			maxLng = originLngRadians + delta;
			if (maxLng > SloppyMath.toRadians(180.0D)) {
				maxLng -= 6.283185307179586D;
			}
		} else {
			minLat = Math.max(minLat, SloppyMath.toRadians(-90.0D));
			maxLat = Math.min(maxLat, SloppyMath.toRadians(90.0D));
			minLng = SloppyMath.toRadians(-180.0D);
			maxLng = SloppyMath.toRadians(180.0D);
		}

		BooleanQuery.Builder f = new BooleanQuery.Builder();

		f.add(DoublePoint.newRangeQuery("latitude", SloppyMath.toDegrees(minLat), SloppyMath.toDegrees(maxLat)),
				BooleanClause.Occur.FILTER);

		if (minLng > maxLng) {

			BooleanQuery.Builder lonF = new BooleanQuery.Builder();
			lonF.add(DoublePoint.newRangeQuery("longitude", SloppyMath.toDegrees(minLng), Double.POSITIVE_INFINITY),
					BooleanClause.Occur.SHOULD);

			lonF.add(DoublePoint.newRangeQuery("longitude", Double.NEGATIVE_INFINITY, SloppyMath.toDegrees(maxLng)),
					BooleanClause.Occur.SHOULD);

			f.add(lonF.build(), BooleanClause.Occur.MUST);
		} else {
			f.add(DoublePoint.newRangeQuery("longitude", SloppyMath.toDegrees(minLng), SloppyMath.toDegrees(maxLng)),
					BooleanClause.Occur.FILTER);
		}

		return f.build();
	}

	public FacetResult search() throws IOException {
		FacetsCollector fc = new FacetsCollector();

		this.searcher.search(new MatchAllDocsQuery(), fc);

		Facets facets = new DoubleRangeFacetCounts("field", getDistanceValueSource(), fc,
				getBoundingBoxQuery(40.7143528D, -74.0059731D, 10.0D),
				new DoubleRange[] { this.ONE_KM, this.TWO_KM, this.FIVE_KM, this.TEN_KM });

		return facets.getTopChildren(10, "field", new String[0]);
	}

	public TopDocs drillDown(DoubleRange range) throws IOException {
		DrillDownQuery q = new DrillDownQuery(null);
		final DoubleValuesSource vs = getDistanceValueSource();
		q.add("field", range.getQuery(getBoundingBoxQuery(40.7143528D, -74.0059731D, range.max), vs));
		DrillSideways ds = new DrillSideways(this.searcher, this.config, (TaxonomyReader) null) {
			protected Facets buildFacetsResult(FacetsCollector drillDowns, FacetsCollector[] drillSideways,
					String[] drillSidewaysDims) throws IOException {
				assert (drillSideways.length == 1);
				return new DoubleRangeFacetCounts("field", vs, drillSideways[0],
						new DoubleRange[] { DistanceFacetsExample.this.ONE_KM, DistanceFacetsExample.this.TWO_KM,
								DistanceFacetsExample.this.FIVE_KM, DistanceFacetsExample.this.TEN_KM });
			}
		};
		return ds.search(q, 10).hits;
	}

	public void close() throws IOException {
		this.searcher.getIndexReader().close();
		this.indexDir.close();
	}

	public static void main(String[] args) throws Exception {
		DistanceFacetsExample example = new DistanceFacetsExample();
		example.index();

		System.out.println("Distance facet counting example:");
		System.out.println("-----------------------");
		System.out.println(example.search());

		System.out.println("Distance facet drill-down example (field/< 2 km):");
		System.out.println("---------------------------------------------");
		TopDocs hits = example.drillDown(example.TWO_KM);
		System.out.println(hits.totalHits + " totalHits");

		example.close();
	}
}
