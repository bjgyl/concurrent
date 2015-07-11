package chapter5;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * �ļ������ͻ���
 * �����һ���򵥵������������ߵ�����
 * @author skywalker
 *
 */
public class FileIndexClient {

	public static void main(String[] args) {
		File root = new File("XXXX");
		FileFilter filter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return true;
			}
		};
		BlockingQueue<File> queue = new ArrayBlockingQueue<File>(16);
		//5���̱߳���·��
		for(int i = 0;i < 5;i ++) {
			new Thread(new FileCrawler(queue, filter, root)).start();;
		}
		//5���߳̽�������
		for(int i = 0;i < 5;i ++) {
			new Thread(new Indexer(queue)).start();
		}
	}
	
}
