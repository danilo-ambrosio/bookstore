package com.sporty.bookstore.infrastructure;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

	private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	public void execute(final Runnable runnable) {
		executor.execute(runnable);
	}

}