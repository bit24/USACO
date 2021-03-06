import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeSet;

public class HighCardLowCard {

	public static void main(String[] args) throws IOException {
		new HighCardLowCard().execute();
	}

	int numCards;
	int numTCards;

	int[] eCards;
	int[] bCards;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("cardgame.in"));
		numCards = Integer.parseInt(reader.readLine());
		numTCards = numCards * 2;
		boolean[] eHas = new boolean[numTCards];
		eCards = new int[numCards];
		bCards = new int[numCards];
		for (int i = 0; i < numCards; i++) {
			int card = Integer.parseInt(reader.readLine()) - 1;
			eHas[card] = true;
			eCards[i] = card;
		}
		reader.close();

		int count = 0;
		for (int i = 0; i < numTCards; i++) {
			if (!eHas[i]) {
				bCards[count++] = i;
			}
		}
		// assert bCards is sorted

		computeH_Values();
		computeL_Values();

		int maxScore = Math.max(h[numCards - 1], l[0]);
		for (int i = 0; i + 1 < numCards; i++) {
			int value = h[i] + l[i + 1];
			if (maxScore < value) {
				maxScore = value;
			}
		}

		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("cardgame.out")));
		printer.println(maxScore);
		printer.close();
	}

	int[] h;
	int[] l;

	void computeH_Values() {
		TreeSet<Integer> bSet = new TreeSet<Integer>();
		for(int i : bCards){
			bSet.add(i);
		}

		h = new int[numCards];
		for (int index = 0; index < numCards; index++) {

			Integer higher = bSet.higher(eCards[index]);
			if (higher != null) {
				h[index] = index > 0 ? h[index - 1] + 1 : 1;
				bSet.remove(higher);
			} else {
				h[index] = index > 0 ? h[index - 1] : 0;
			}
		}
	}

	void computeL_Values() {
		TreeSet<Integer> bSet = new TreeSet<Integer>();
		for(int i : bCards){
			bSet.add(i);
		}

		l = new int[numCards];
		for (int index = numCards - 1; index >= 0; index--) {

			Integer lower = bSet.lower(eCards[index]);
			if (lower != null) {
				l[index] = index < numCards - 1 ? l[index + 1] + 1 : 1;
				bSet.remove(lower);
			} else {
				l[index] = index < numCards - 1 ? l[index + 1] : 0;
			}
		}
	}

}
