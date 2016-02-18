package com.varone.web.reader.metrics.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.varone.web.eventlog.bean.SparkEventLogBean;
import com.varone.web.eventlog.bean.SparkEventLogBean.BlockManager;
import com.varone.web.reader.eventlog.parser.SparkEventLogParser;

public class SparkEventLogParserTest {
	
	
	//{"Event":"SparkListenerBlockManagerAdded","Block Manager ID":{"Executor ID":"1","Host":"server-a2","Port":34086},"Maximum Memory":556038881,"Timestamp":1450686022248}
	@Test
	public void testBlockManagerTag() throws Exception{
		List<String> eventNames = new ArrayList<String>();
		eventNames.add(SparkEventLogParser.BLOCKMANAGER_ADD);
		
		SparkEventLogParser eventLogParser = new SparkEventLogParser(eventNames);
		boolean flag = eventLogParser.parseLine("{\"Event\":\"SparkListenerBlockManagerAdded\",\"Block Manager ID\":{\"Executor ID\":\"1\",\"Host\":\"server-a2\",\"Port\":34086},\"Maximum Memory\":556038881,\"Timestamp\":1450686022248}");
		SparkEventLogBean result = eventLogParser.result();
		
		List<BlockManager> blockManagerList = result.getBlockManager();
		
		for(BlockManager blockManager : blockManagerList){
			assertEquals("1", blockManager.getBlockManagerID().getId());
			assertEquals("server-a2", blockManager.getBlockManagerID().getHost());
			assertEquals(34086, blockManager.getBlockManagerID().getPort());
			assertEquals(1450686022248L, blockManager.getTimestamp());
			assertEquals(556038881L, blockManager.getMaxMemory());
		}
		
		
	}

}
