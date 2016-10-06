package markov4;

import gnu.trove.map.hash.TObjectIntHashMap;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static jpkg.ngram.NGram.getPaired;
import jpkg.collection.set.MultiSet;

public class Dataset {
	public HashMap<String, MultiSet<String>> pairset = new HashMap<>();
	public MultiSet<String> starters = new MultiSet<>();
	public int lookahead;
	public int lookback;
	public int occur;
	
	public static final char END_OF_MSG = 5;
	
	public Dataset(int lookahead, int lookback) {
		this.lookahead = lookahead;
		this.lookback = lookback;
	}
	
	public Dataset(int lookahead) {
		this(lookahead, 1);
	}
	
	public void addData(BufferedReader br) {
		br.lines().forEach((str) -> { addLine(str); });
	}
	
	public void addLine(String s) {
		String[] grams = getPaired(s + END_OF_MSG, lookahead, 1);
		
		if(grams.length > 0)
			starters.add(grams[0]);
		for(int i = 0; i < grams.length; i += 2) {
			addPairing(grams[i], grams[i + 1]);
		}
	}
	
	public void addPairing(String predicate, String result) {
		occur++;
		
		MultiSet<String> element = pairset.get(predicate);
		
		if(element == null) pairset.put(predicate, element = new MultiSet<>());
		
		element.add(result);
	}
	
	public String getNext(String predicate, Random rnd) {
		MultiSet<String> p = pairset.get(predicate);
		if(p == null)
			throw new IllegalArgumentException("Predicate specified not found!");
		else
			return p.getRandomElement(rnd);
	}
	
	public String getSentence(Random rnd) {
		StringBuilder sb = new StringBuilder(starters.getRandomElement(rnd));
		while(true) {
			MultiSet<String> p = pairset.get(sb.substring(sb.length() - lookahead));
			if(p == null || sb.charAt(sb.length() - 1) == END_OF_MSG)
				break;
			else
				sb.append(p.getRandomElement(rnd));
		}
		
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return " Current lookahead: " + lookahead + "\n"
				+ " Current lookback: " + lookback + "\n"
				+ " Dataset unique/mappings: "
				+ pairset.size() + "/" + occur
				+ " (Average saturation per pairing: " + 
				(double)(int)((((double)occur) * 1000d) / (double)pairset.size()) / 1000d + ")\n "
				+ " Number of starters (unique): " + starters.size() + "\n";
	}
}
