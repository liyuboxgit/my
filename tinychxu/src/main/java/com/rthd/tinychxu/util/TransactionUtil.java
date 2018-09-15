package com.rthd.tinychxu.util;

import java.util.function.Consumer;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@FunctionalInterface
public interface TransactionUtil extends Consumer<Object>{
	public static Exception doInOneTransaction(TransactionTemplate transactionTemplate,TransactionUtil c) {
		Exception ret = transactionTemplate.execute(new TransactionCallback<Exception>() {
		    public Exception doInTransaction(TransactionStatus status) {
		    	Exception result = null;
		        try {
		        	c.accept(null);
		        } catch (Exception ex) {
		            status.setRollbackOnly();
		            result = ex;
		        }
		        return result;
		    }
		});
		
		return ret;
	}
}
