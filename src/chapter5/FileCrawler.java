package chapter5;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;

/**
 * �ļ����棬��ÿһ���ļ�������������
 * P75 5-8
 * @author skywalker
 *
 */
public class FileCrawler implements Runnable {

	private final BlockingQueue<File> queue;
	private final FileFilter filter;
	//��Ŀ¼
	private final File root;
	
	public FileCrawler(BlockingQueue<File> queue, FileFilter filter, File root) {
		this.queue = queue;
		this.filter = filter;
		this.root = root;
	}

	@Override
	public void run() {
		try {
			craml(root);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
	/**
	 * ����һ����·��
	 */
	private void craml(File root) throws InterruptedException {
		if(root != null && root.isDirectory()) {
			File[] files = root.listFiles(filter);
			for(File file : files) {
				if(file.isDirectory()) {
					craml(file);
				}else {
					queue.put(file);
				}
			}
		}
	}
	
}
