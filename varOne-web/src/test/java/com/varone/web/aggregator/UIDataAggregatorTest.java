package com.varone.web.aggregator;

import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.junit.Test;

import com.varone.web.eventlog.bean.SparkEventLogBean;
import com.varone.web.eventlog.bean.SparkEventLogBean.TaskEnd;
import com.varone.web.reader.eventlog.impl.EventLogHdfsReaderImpl;
import com.varone.web.util.VarOneConfiguration;
import com.varone.web.vo.HistoryDetailStageVO;
import com.varone.web.vo.TasksVO;

public class UIDataAggregatorTest {

	@Test
	public void testgetHistoryDetialStage() throws Exception{
		VarOneConfiguration varOneConf = new VarOneConfiguration();
		Configuration config = varOneConf.loadHadoopConfiguration();
		
		
		UIDataAggregator aggregator = new UIDataAggregator();
		EventLogHdfsReaderImpl hdfsReader = new EventLogHdfsReaderImpl(config);
		SparkEventLogBean sparkEventLog = hdfsReader.getHistoryStageDetails("application_1449569227858_0664");
		
		HistoryDetailStageVO historyDetailsVO = aggregator.aggregateHistoryDetialStage(sparkEventLog);
		List<TasksVO> taskVOList = historyDetailsVO.getTasks();
		
		for(TasksVO taskVO : taskVOList){
			System.out.println(taskVO.getIndex() + "   " + taskVO.getGcTime());
			
		}
		
		/*List<TaskEnd> taskEndList = sparkEventLog.getTaskEnd();
		for(TaskEnd taskEnd : taskEndList){
			System.out.println(taskEnd.getInfo().getIndex() + "    " + taskEnd.getMetrics().getGcTime());
		}*/
		
		
	}
}
