package markov4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

public class Main {
	public static void main(String[] args) throws Exception {
		Random rnd = new Random();
		BufferedReader bri = new BufferedReader(new FileReader(new File("../output-linux.txt")));
		System.out.println("Starting");
		Dataset di = new Dataset(14, 4);
		di.addData(bri);
		
		for(int i = 0; i < 10; i++)
			System.out.println(di.getSentence(rnd).replace("\\n", "\n"));
		
		System.exit(0);
		final int toprod = 100;

		long prevtime = System.currentTimeMillis();
		Random r = new Random();
		//*/
		BufferedReader br = new BufferedReader(new FileReader(new File("../garmy_data.txt")));
		System.out.println("Starting");
		Dataset d = new Dataset(7, 2);
		d.addData(br);
		/*/
		
		Dataset d = deserialize(new File("./dataset-def.dat"));
		
		
		//*/
		System.out.println("Took " + (System.currentTimeMillis() - prevtime) + "ms to load.");
		prevtime = System.currentTimeMillis();
		for(int i = 0; i < toprod; i++) {
			System.out.println(d.getSentence(r));
		}
		long taken = System.currentTimeMillis() - prevtime;
		System.out.println("Generated " + toprod + " in " + taken + "ms (Average " + ((double)taken / (double)toprod) +"ms ea)");
		prevtime = System.currentTimeMillis();
		// serialize(d, new File("./dataset-fin.dat"));
		// System.out.println("Finished serialization in " + (System.currentTimeMillis() - prevtime));
	}

	public static <T extends Serializable> void serialize(T obj, File to) throws IOException {
		FileOutputStream fout = new FileOutputStream(to);
		FSTObjectOutput oos = new FSTObjectOutput(fout);
		oos.writeObject(obj);
		oos.close();
	}

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T deserialize(File from) throws IOException, ClassNotFoundException {
		T ret;
		FileInputStream fin = new FileInputStream(from);
		FSTObjectInput ois = new FSTObjectInput(fin);
		ret = (T) ois.readObject();
		ois.close();
		return ret;
	}
}
