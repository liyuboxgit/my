package liyu.test.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.quartz.JobExecutionException;
/**
 * 
 * @ClassName: DataConversionTask 
 * @Description: 这是一种简单的任务配置
 * @author: liyu
 * @date: 2017年11月30日 下午3:17:45
 */
public class DataConversionTask{
	public void run() throws JobExecutionException {
		System.err.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "★★★★★★★★★★★");
	}
}
