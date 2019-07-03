package liyu.test.springSecurity.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.util.NamedList;

public class SolrClient {
	public static void main(String[] args) throws SolrServerException, IOException {
		CloudSolrClient client = new CloudSolrClient.Builder().withSolrUrl("http://192.168.145.131:8993/solr").build();
		client.setDefaultCollection("user");
		SolrQuery q = new SolrQuery();
		// 开始页数
		q.setStart(0);
		// 每页显示条数
		q.setRows(10);
		// 设置查询关键字,setQuery("*") or setQuery("*:*") than all result returned.but no highlight.
		q.setQuery("des:2");
		// 开启高亮
		q.setHighlight(true);
		// 高亮字段
		q.addHighlightField("des,name");
		// 高亮单词的前缀
		q.setHighlightSimplePre("<span style='color:red'>");
		// 高亮单词的后缀
		q.setHighlightSimplePost("</span>");
		// 摘要最长100个字符
		q.setHighlightFragsize(100);
		// 查询
		QueryResponse response = client.query(q);
		// 获取高亮字段name相应结果
		SolrDocumentList list = response.getResults();

		for (SolrDocument result : list) {
			System.out.println(result.toString());
		}

		NamedList<?> highlighting = (NamedList<?>) response.getResponse().get("highlighting");
		for (int i = 0; i < highlighting.size(); i++) {
			System.out.println(highlighting.getName(i) + "：" + highlighting.getVal(i));
		}
	}
}
