package markov4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File("../output.txt")));
		Random r = new Random();
		
		Dataset d = new Dataset(5);
		d.addData(br);
		for(int i = 0; i < 100; i++) {
			System.out.println(d.getSentence(r));
		}
	}
}
