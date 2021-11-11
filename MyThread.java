import java.util.stream.IntStream;

public class MyThread extends Thread {

	public long run(int[] ary){
	  long sum = 0;

	  sum = IntStream.of(ary).sum();
	  
	  return sum;
	}
 }