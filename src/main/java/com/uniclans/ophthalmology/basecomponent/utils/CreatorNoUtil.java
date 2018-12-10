package com.uniclans.ophthalmology.basecomponent.utils;


import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class CreatorNoUtil {

    private static AtomicInteger counter = new AtomicInteger(0);

    /**
     * 生成编号
     */
    public synchronized static String getCode() {
        if (counter.get() > 99998) {
            counter.set(0);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		String date=sdf.format(new Date());
        long returnValue = counter.incrementAndGet();
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumIntegerDigits(5);
        nf.setMaximumIntegerDigits(5);
        String tail = nf.format(returnValue).replace(",", "");
        
        return date+tail;
    }
    /**
     * 生成编号
     */
    public synchronized static String createCode() {
    	if (counter.get() > 999) {
    		counter.set(0);
    	}
    	SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
    	String date=sdf.format(new Date());
    	long returnValue = counter.incrementAndGet();
    	NumberFormat nf = NumberFormat.getNumberInstance();
    	nf.setMinimumIntegerDigits(3);
    	nf.setMaximumIntegerDigits(3);
    	String tail = nf.format(returnValue).replace(",", "");
    	
    	return date+tail;
    }
    public static void main(String[] args) {
		for(int i=0;i<100;i++){
			String a = createCode();
			System.out.println(a);
		}
	}
}