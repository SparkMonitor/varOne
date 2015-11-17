/**
 * 
 */
package com.haredb.sparkmonitor.reader.metrics.stub;

/**
 * @author allen
 *
 */
public class RpcThreadListener {
	private final static int MIN_THREAD_COUNT = 0;
	private int count;
	
	public RpcThreadListener(){
		this.count = 0;
	}
	
	public synchronized void increaseRpcThreadCount(){
		this.count++;
	}
	
	public synchronized void decreaseRpcThreadCount(){
		this.count--;
		if(this.count == MIN_THREAD_COUNT) notify();
	}
	
	public synchronized boolean isAllComplete(){
		return this.count == MIN_THREAD_COUNT;
	}
}
