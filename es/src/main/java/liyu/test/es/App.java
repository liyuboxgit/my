package liyu.test.es;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

/**
 * Hello world!
 *
 */
public class App {
	public static RestHighLevelClient getClient() {
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(
						//new HttpHost("localhost", 9200, "http"), 
						new HttpHost("192.168.233.128", 9200, "http")
				)
		);

		return client;
	}

	public static void main(String[] args) {
		RestHighLevelClient client = getClient();
		IndexRequest request = new IndexRequest("megacorp","employee","2");
		
		String jsonString = "{\n" + 
				"    \"first_name\" :  \"Jane\",\n" + 
				"    \"last_name\" :   \"Smith\",\n" + 
				"    \"age\" :         32,\n" + 
				"    \"about\" :       \"I like to collect rock albums\",\n" + 
				"    \"interests\":  [ \"music\" ]\n" + 
				"}";
		
		request.source(jsonString, XContentType.JSON); 
		IndexResponse indexResponse = null;
		try {
            // 同步方式
			indexResponse = client.index(request);            
        } catch(ElasticsearchException e) {
            // 捕获，并处理异常
            //判断是否版本冲突、create但文档已存在冲突
            if (e.status() == RestStatus.CONFLICT) {
                System.out.println("冲突了，请在此写冲突处理逻辑！\n" + e.getDetailedMessage());
            }
             
            System.out.println("索引异常："+ e);
            e.printStackTrace();
         } catch (IOException e) {
			e.printStackTrace();
		}
		 if(indexResponse != null) {
             String index = indexResponse.getIndex();
             String type = indexResponse.getType();
             String id = indexResponse.getId();
             long version = indexResponse.getVersion();
             if (indexResponse.getResult() == IndexResponse.Result.CREATED) {
                 System.out.println("新增文档成功，处理逻辑代码写到这里。");
             } else if (indexResponse.getResult() == UpdateResponse.Result.UPDATED) {
                 System.out.println("修改文档成功，处理逻辑代码写到这里。");
             }
             // 分片处理信息
             ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
             if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
                 
             }
             // 如果有分片副本失败，可以获得失败原因信息
             if (shardInfo.getFailed() > 0) {
                 for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                     String reason = failure.reason(); 
                     System.out.println("副本失败原因：" + reason);
                 }
             }
         }
		 
		/*GetRequest request = new GetRequest("megacorp", "employee", "1");

		String[] includes = new String[] { "first_name", "age" };
		String[] excludes = Strings.EMPTY_ARRAY;
		FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
		request.fetchSourceContext(fetchSourceContext);

		GetResponse getResponse = null;
		try {
			// 同步请求
			getResponse = client.get(request);
		} catch (ElasticsearchException e) {
			if (e.status() == RestStatus.NOT_FOUND) {
				System.out.println("没有找到该id的文档");
			}
			if (e.status() == RestStatus.CONFLICT) {
				System.out.println("获取时版本冲突了，请在此写冲突处理逻辑！");
			}

			System.out.println("获取文档异常:" + e);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(getResponse != null) {
            String index = getResponse.getIndex();
            String type = getResponse.getType();
            String id = getResponse.getId();
            if (getResponse.isExists()) { // 文档存在
                long version = getResponse.getVersion();
                String sourceAsString = getResponse.getSourceAsString(); //结果取成 String       
                Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();  // 结果取成Map
                byte[] sourceAsBytes = getResponse.getSourceAsBytes();    //结果取成字节数组
                
                System.out.println("index:" + index + "  type:" + type + "  id:" + id);
                System.out.println(sourceAsString);
                
            } else {
            	System.out.println("没有找到该id的文档" );
            }
        }*/
		
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
