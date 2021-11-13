
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.*;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class App {

	public static int[] genArray(){
		int[] genAry = new int[200000000];
		Random rand = new Random();
		for (int i=0; i<200000000; i++){
			genAry[i] = rand.nextInt(10) + 1;
		}
		return genAry;
	}

	//same logic but less numbers for memory
	private static int[] genArrayTesting(){
		int[] genAry = new int[100];
		Random rand = new Random();
		for (int i=0; i<100; i++){
			genAry[i] = rand.nextInt(10) + 1;
		}
		return genAry;
	}
	
	@Test
	@DisplayName("GenArray length test")
	public void testArrayGen() {
		assertEquals(genArrayTesting().length, 100);
	}

	@Test
	@DisplayName("GenArray range test")
	public void testRange() {
		int[] ary = genArrayTesting();
		for(int i=0;i<ary.length;i++){
			assertTrue(1 <= ary[i] && ary[i] <= 100);
		}
	}

	public static long oneThreadAdd(int[] ary){
		long sum = 0;

		long startTime = System.nanoTime();

		sum = IntStream.of(ary).sum();

		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		System.out.println("Runtime in Nano-seconds: " + totalTime);
		
		return sum;
	}

	@Test
	@DisplayName("oneThreadSum Test")
	public void testOneThreadAdd() {
		int[] ary31 = {10, 10, 5, 3, 2, 1};//31
		int[] ary50 = {10, 10, 10, 10, 9, 1};//50
		int[] ary6 = {1, 1, 1, 1, 1, 1};//6

		assertEquals(31, oneThreadAdd(ary31));
		assertEquals(50, oneThreadAdd(ary50));
		assertEquals(6, oneThreadAdd(ary6));
	}

	public static long twoThreadAdd(int[] ary){
		
		int[] a = Arrays.copyOfRange(ary, 0, (ary.length + 1)/2);
		int[] b = Arrays.copyOfRange(ary, (ary.length + 1)/2, ary.length);


		MyThread leftThread = new MyThread();
		MyThread rightThread = new MyThread();
		
		long startTime = System.nanoTime();

		// run the threads
		long test1 = leftThread.run(a);
		long test2 = rightThread.run(b);
		
		// wait for the threads to finish
		try {
			leftThread.join();
			rightThread.join();
		} catch (InterruptedException ie) {}

		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		System.out.println("Runtime in Nano-seconds Multi-2: " + totalTime);
		
		// combine the results of the two threads
		return test1 + test2;
	}

	@Test
	@DisplayName("twoThreadSum Test")
	public void testTwoThreadAdd() {
		int[] ary31 = {10, 10, 5, 3, 2, 1};//31
		int[] ary50 = {10, 10, 10, 10, 9, 1};//50
		int[] ary6 = {1, 1, 1, 1, 1, 1};//6
		int[] oddary = {5, 6, 9};//20
		int[] oneary = {5};//5

		assertEquals(31, twoThreadAdd(ary31));
		assertEquals(50, twoThreadAdd(ary50));
		assertEquals(6, twoThreadAdd(ary6));
		assertEquals(20, twoThreadAdd(oddary));
		assertEquals(5, twoThreadAdd(oneary));
	}

	public static long fourThreadAdd(int[] ary){
		
		int[] hold = Arrays.copyOfRange(ary, 0, (ary.length + 1)/2);
		int[] a = Arrays.copyOfRange(hold, 0, (hold.length + 1)/2);
		int[] b = Arrays.copyOfRange(hold, (hold.length + 1)/2, hold.length);
		hold = Arrays.copyOfRange(ary, (ary.length + 1)/2, ary.length);
		int[] c = Arrays.copyOfRange(hold, 0, (hold.length + 1)/2);
		int[] d = Arrays.copyOfRange(hold, (hold.length + 1)/2, hold.length);


		MyThread aThread = new MyThread();
		MyThread bThread = new MyThread();
		MyThread cThread = new MyThread();
		MyThread dThread = new MyThread();
		
		long startTime = System.nanoTime();

		// run the threads
		long test1 = aThread.run(a);
		long test2 = bThread.run(b);
		long test3 = cThread.run(c);
		long test4 = dThread.run(d);
		
		// wait for the threads to finish
		try {
			aThread.join();
			bThread.join();
			cThread.join();
			dThread.join();
		} catch (InterruptedException ie) {}

		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		System.out.println("Runtime in Nano-seconds Multi-4: " + totalTime);
		
		// combine the results of the two threads
		return test1 + test2 + test3 + test4;
	}

	@Test
	@DisplayName("fourThreadSum Test")
	public void testFourThreadAdd() {
		int[] ary31 = {10, 10, 5, 3, 2, 1};//31
		int[] ary50 = {10, 10, 10, 10, 9, 1};//50
		int[] ary6 = {1, 1, 1, 1, 1, 1};//6
		int[] oddary = {5, 6, 9};//20
		int[] oneary = {5};//5

		assertEquals(31, fourThreadAdd(ary31));
		assertEquals(50, fourThreadAdd(ary50));
		assertEquals(6, fourThreadAdd(ary6));
		assertEquals(20, fourThreadAdd(oddary));
		assertEquals(5, fourThreadAdd(oneary));
	}

	public static void main(String[] args) throws Exception {
		int[] ary = new int[200000000];
		long sum = -1;

		ary = genArray();

		

		sum = oneThreadAdd(ary);
		System.out.println("One thread sum: " + sum);

		sum = twoThreadAdd(ary);
		System.out.println("Two thread sum: " + sum);

		sum = fourThreadAdd(ary);
		System.out.println("Four thread sum: " + sum);

	}


}
