package SecretsManagerJ2.SecretsManagerJ2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.io.File;
import java.io.FileNotFoundException;


public class EfsWrite {

   public static void main(String[] args) {
      // open file in read-write mode
      RandomAccessFile randomAccessFile = null;
      String FileName = "/mnt/received/file_to_write.txt";
	try {
		randomAccessFile = new RandomAccessFile(new File(FileName), "rw");
	} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
      try {
        // get channel
        FileChannel channel = randomAccessFile.getChannel();
        // get lock
	FileLock tryLock = channel.lock();
	String data = "This data is written from Lambda Function";
        // write to file
	randomAccessFile.write(data.getBytes());
        // release lock
        tryLock.release();
        // close file
	randomAccessFile.close();
      } catch (IOException e) {
	   e.printStackTrace();
      }
   }
}