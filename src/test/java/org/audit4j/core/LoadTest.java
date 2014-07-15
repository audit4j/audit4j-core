package org.audit4j.core;

import java.util.concurrent.TimeUnit;

import org.audit4j.core.dto.EventBuilder;
import org.audit4j.core.io.AsyncAuditOutputStream;
import org.audit4j.core.io.AuditEventOutputStream;
import org.audit4j.core.io.AuditOutputStream;
import org.audit4j.core.io.AuditProcessOutputStream;

public class LoadTest {

	public void testEvents_Param2_Load100(){
		Long startTime = System.currentTimeMillis();
		
		for (int i = 0; i < 100; i++) {
			String actor = "Dummy Actor" + i;
			EventBuilder builder = new EventBuilder();
			builder.addActor(actor).addAction("myMethod" + i).addOrigin("Origin" + i).addField("myParam1Name" + i, "param1" + i).addField("myParam2Name" + i, new Integer(1));
			AuditManager manager = AuditManager.getInstance();
			manager.audit(builder.build());
		}
		
		Long endTime = System.currentTimeMillis();
		Long totalTimeInMills =  endTime-startTime;
		System.out.println("Total Time in mills: " + totalTimeInMills);
	}
	
	public void testEvents_Param10_Load10000(){
		Long startTime = System.currentTimeMillis();
		
		for (int i = 0; i < 10000; i++) {
			String actor = "Dummy Actor" + i;
			EventBuilder builder = new EventBuilder();
			builder.addActor(actor).addAction("myMethod" + i).addOrigin("Origin" + i);
			for (int j = 0; j < 10; j++) {
				builder.addField("myParam1Name" + j + i, "param1" + j + i);
			}
			
			AuditManager manager = AuditManager.getInstance();
			manager.audit(builder.build());
		}
		
		Long endTime = System.currentTimeMillis();
		Long totalTimeInMills =  endTime-startTime;
		System.out.println("Total Time in mills: " + totalTimeInMills);
	}

	
	public void testStream_Param10_Load10000(){
		Long startTime = System.currentTimeMillis();
		Context context = new Context();
		context.init();
		
		AsyncAuditOutputStream asyncStream = new AsyncAuditOutputStream(new AuditProcessOutputStream(context));
		AuditOutputStream stream = new AuditEventOutputStream(asyncStream);

		for (int i = 0; i < 10000; i++) {
			String actor = "Dummy Actor" + i;
			EventBuilder builder = new EventBuilder();
			builder.addActor(actor).addAction("myMethod" + i).addOrigin("Origin" + i);
			for (int j = 0; j < 10; j++) {
				builder.addField("myParam1Name" + j + i, "param1" + j + i);
			}
			
			stream.write(builder.build());
			Long endTime = System.currentTimeMillis();
			Long totalTimeInMills =  endTime-startTime;
			System.out.println("Total Time in mills for this: " + totalTimeInMills);
			
		}
		
		
		try {
			TimeUnit.SECONDS.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		LoadTest test = new LoadTest();
		test.testEvents_Param10_Load10000();
	}
}
