import java.util.*;
import java.io.*;
import java.lang.*;

public class GlobalAlign {

	static int d = -10;
	static int e = -5;
	// BLOSUM50 matrix
	static final int[][] blosum50 = {
		{ 5, -2, -1, -2, -1, -1, -1,  0, -2, -1, -2, -1, -1, -3, -1,  1,  0, -3, -2,  0, -2, -2, -1, -1, -5},
		{-2,  7, -1, -2, -4,  1,  0, -3,  0, -4, -3,  3, -2, -3, -3, -1, -1, -3, -1, -3, -1, -3,  0, -1, -5},
		{-1, -1,  7,  2, -2,  0,  0,  0,  1, -3, -4,  0, -2, -4, -2,  1,  0, -4, -2, -3,  5, -4,  0, -1, -5},
		{-2, -2,  2,  8, -4,  0,  2, -1, -1, -4, -4, -1, -4, -5, -1,  0, -1, -5, -3, -4,  6, -4,  1, -1, -5},
		{-1, -4, -2, -4, 13, -3, -3, -3, -3, -2, -2, -3, -2, -2, -4, -1, -1, -5, -3, -1, -3, -2, -3, -1, -5},
		{-1,  1,  0,  0, -3,  7,  2, -2,  1, -3, -2,  2,  0, -4, -1,  0, -1, -1, -1, -3,  0, -3,  4, -1, -5},
		{-1,  0,  0,  2, -3,  2,  6, -3,  0, -4, -3,  1, -2, -3, -1, -1, -1, -3, -2, -3,  1, -3,  5, -1, -5},
		{ 0, -3,  0, -1, -3, -2, -3,  8, -2, -4, -4, -2, -3, -4, -2,  0, -2, -3, -3, -4, -1, -4, -2, -1, -5},
		{-2,  0,  1, -1, -3,  1,  0, -2, 10, -4, -3,  0, -1, -1, -2, -1, -2, -3,  2, -4,  0, -3,  0, -1, -5},
		{-1, -4, -3, -4, -2, -3, -4, -4, -4,  5,  2, -3,  2,  0, -3, -3, -1, -3, -1,  4, -4,  4, -3, -1, -5},
		{-2, -3, -4, -4, -2, -2, -3, -4, -3,  2,  5, -3,  3,  1, -4, -3, -1, -2, -1,  1, -4,  4, -3, -1, -5},
		{-1,  3,  0, -1, -3,  2,  1, -2,  0, -3, -3,  6, -2, -4, -1,  0, -1, -3, -2, -3,  0, -3,  1, -1, -5},
		{-1, -2, -2, -4, -2,  0, -2, -3, -1,  2,  3, -2,  7,  0, -3, -2, -1, -1,  0,  1, -3,  2, -1, -1, -5},
		{-3, -3, -4, -5, -2, -4, -3, -4, -1,  0,  1, -4,  0,  8, -4, -3, -2,  1,  4, -1, -4,  1, -4, -1, -5},
		{-1, -3, -2, -1, -4, -1, -1, -2, -2, -3, -4, -1, -3, -4, 10, -1, -1, -4, -3, -3, -2, -3, -1, -1, -5},
		{ 1, -1,  1,  0, -1,  0, -1,  0, -1, -3, -3,  0, -2, -3, -1,  5,  2, -4, -2, -2,  0, -3,  0, -1, -5},
		{ 0, -1,  0, -1, -1, -1, -1, -2, -2, -1, -1, -1, -1, -2, -1,  2,  5, -3, -2,  0,  0, -1, -1, -1, -5},
		{-3, -3, -4, -5, -5, -1, -3, -3, -3, -3, -2, -3, -1,  1, -4, -4, -3, 15,  2, -3, -5, -2, -2, -1, -5},
		{-2, -1, -2, -3, -3, -1, -2, -3,  2, -1, -1, -2,  0,  4, -3, -2, -2,  2,  8, -1, -3, -1, -2, -1, -5},
		{ 0, -3, -3, -4, -1, -3, -3, -4, -4,  4,  1, -3,  1, -1, -3, -2,  0, -3, -1,  5, -3,  2, -3, -1, -5},
		{-2, -1,  5,  6, -3,  0,  1, -1,  0, -4, -4,  0, -3, -4, -2,  0,  0, -5, -3, -3,  6, -4,  1, -1, -5},
		{-2, -3, -4, -4, -2, -3, -3, -4, -3,  4,  4, -3,  2,  1, -3, -3, -1, -2, -1,  2, -4,  4, -3, -1, -5},
		{-1,  0,  0,  1, -3,  4,  5, -2,  0, -3, -3,  1, -1, -4, -1,  0, -1, -2, -2, -3,  1, -3,  5, -1, -5},
		{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -5},
		{-5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5,  1}
	};
	// BLOSUM62 matrix
	static final int[][] blosum62 = {
		{ 4, -1, -2, -2,  0, -1, -1,  0, -2, -1, -1, -1, -1, -2, -1,  1,  0, -3, -2,  0, -2, -1, -1, -1, -4},
		{-1,  5,  0, -2, -3,  1,  0, -2,  0, -3, -2,  2, -1, -3, -2, -1, -1, -3, -2, -3, -1, -2,  0, -1, -4},
		{-2,  0,  6,  1, -3,  0,  0,  0,  1, -3, -3,  0, -2, -3, -2,  1,  0, -4, -2, -3,  4, -3,  0, -1, -4},
		{-2, -2,  1,  6, -3,  0,  2, -1, -1, -3, -4, -1, -3, -3, -1,  0, -1, -4, -3, -3,  4, -3,  1, -1, -4},
		{ 0, -3, -3, -3,  9, -3, -4, -3, -3, -1, -1, -3, -1, -2, -3, -1, -1, -2, -2, -1, -3, -1, -3, -1, -4},
		{-1,  1,  0,  0, -3,  5,  2, -2,  0, -3, -2,  1,  0, -3, -1,  0, -1, -2, -1, -2,  0, -2,  4, -1, -4},
		{-1,  0,  0,  2, -4,  2,  5, -2,  0, -3, -3,  1, -2, -3, -1,  0, -1, -3, -2, -2,  1, -3,  4, -1, -4},
		{ 0, -2,  0, -1, -3, -2, -2,  6, -2, -4, -4, -2, -3, -3, -2,  0, -2, -2, -3, -3, -1, -4, -2, -1, -4},
		{-2,  0,  1, -1, -3,  0,  0, -2,  8, -3, -3, -1, -2, -1, -2, -1, -2, -2,  2, -3,  0, -3,  0, -1, -4},
		{-1, -3, -3, -3, -1, -3, -3, -4, -3,  4,  2, -3,  1,  0, -3, -2, -1, -3, -1,  3, -3,  3, -3, -1, -4},
		{-1, -2, -3, -4, -1, -2, -3, -4, -3,  2,  4, -2,  2,  0, -3, -2, -1, -2, -1,  1, -4,  3, -3, -1, -4},
		{-1,  2,  0, -1, -3,  1,  1, -2, -1, -3, -2,  5, -1, -3, -1,  0, -1, -3, -2, -2,  0, -3,  1, -1, -4},
		{-1, -1, -2, -3, -1,  0, -2, -3, -2,  1,  2, -1,  5,  0, -2, -1, -1, -1, -1,  1, -3,  2, -1, -1, -4},
		{-2, -3, -3, -3, -2, -3, -3, -3, -1,  0,  0, -3,  0,  6, -4, -2, -2,  1,  3, -1, -3,  0, -3, -1, -4},
		{-1, -2, -2, -1, -3, -1, -1, -2, -2, -3, -3, -1, -2, -4,  7, -1, -1, -4, -3, -2, -2, -3, -1, -1, -4},
		{ 1, -1,  1,  0, -1,  0,  0,  0, -1, -2, -2,  0, -1, -2, -1,  4,  1, -3, -2, -2,  0, -2,  0, -1, -4},
		{ 0, -1,  0, -1, -1, -1, -1, -2, -2, -1, -1, -1, -1, -2, -1,  1,  5, -2, -2,  0, -1, -1, -1, -1, -4},
		{-3, -3, -4, -4, -2, -2, -3, -2, -2, -3, -2, -3, -1,  1, -4, -3, -2, 11,  2, -3, -4, -2, -2, -1, -4},
		{-2, -2, -2, -3, -2, -1, -2, -3,  2, -1, -1, -2, -1,  3, -3, -2, -2,  2,  7, -1, -3, -1, -2, -1, -4},
		{ 0, -3, -3, -3, -1, -2, -2, -3, -3,  3,  1, -2,  1, -1, -2, -2,  0, -3, -1,  4, -3,  2, -2, -1, -4},
		{-2, -1,  4,  4, -3,  0,  1, -1,  0, -3, -4,  0, -3, -3, -2,  0, -1, -4, -3, -3,  4, -3,  0, -1, -4},
		{-1, -2, -3, -3, -1, -2, -3, -4, -3,  3,  3, -3,  2,  0, -3, -2, -1, -2, -1,  2, -3,  3, -3, -1, -4},
		{-1,  0,  0,  1, -3,  4,  4, -2,  0, -3, -3,  1, -1, -3, -1,  0, -1, -2, -2, -2,  0, -3,  4, -1, -4},
		{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -4},
		{-4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4,  1}
	};

	// Negative infinity
	static int NEG_INF = -99999999;

	public static void main(String[] args) {
		if (args.length != 4) {
			System.out.println("Usage: java GlobalAlign QueryFile FastaFile GapOpeningPenalty GapExtensionPenalty.");
			System.out.println("Gap penalties should be input as positive integers.");
			System.exit(-1);
		}

		// Initialize variables
		String reference = "";
		String temp = "";
		ArrayList<FastaSeq> fastas = new ArrayList<FastaSeq>();
		d = Integer.parseInt(args[2]);
		e = Integer.parseInt(args[3]);

		// Load input files
		File referenceFile = new File(args[0]);
		File fastaFile = new File(args[1]);

		// Read the reference file
		try {
			Scanner sc = new Scanner(referenceFile);
			reference = sc.nextLine();
		} catch (Exception e) {
			System.out.println("Reference file not found.");
			System.exit(0);
		}

		// Parse all of the fasta sequences into the Fasta array
		try {
			Scanner sc = new Scanner(fastaFile);
			temp = sc.nextLine();
			temp = temp.substring(1);
			int counter = 0;
			FastaSeq fasta = new FastaSeq(counter, temp);
			while (sc.hasNextLine()) {
				temp = sc.nextLine();
				if (temp.startsWith(">")) {
					fastas.add(fasta);
					counter++;
					temp = temp.substring(1);
					fasta = fasta = new FastaSeq(counter, temp);
				} else {
					fasta.sequence += temp;
				}
			}
			counter++;
			fastas.add(fasta);			
		} catch (Exception e) {
			System.out.println("Fasta file not found.");
			System.exit(0);
		}

		// Begin aligning each fasta sequence to the reference
		for (int i = 0; i < fastas.size(); i++) {
			FastaSeq f = fastas.get(i);

			// Declare DP matrices
			int[][] m = new int[reference.length() + 1][f.sequence.length() + 1];
			int[][] gx = new int[reference.length() + 1][f.sequence.length() + 1];
			int[][] gy = new int[reference.length() + 1][f.sequence.length() + 1];

			// Initialize the matrices
			for (int k = 0; k <= reference.length(); k++) {
				m[k][0] = NEG_INF;
				gy[k][0] = NEG_INF;
			}
			for (int k = 0; k <= f.sequence.length(); k++) {
				m[0][k] = NEG_INF;
				gx[0][k] = NEG_INF;
			}
			for (int k = 1; k <= reference.length(); k++) {
				gx[k][0] = d + ((k-1)*e);
			}
			for (int k = 1; k <= f.sequence.length(); k++) {
				gy[0][k] = d + ((k-1)*e);
			}
			m[0][0] = 0;

			// Begin filling the matrices using affine gap penalty
			for (int k = 1; k <= reference.length(); k++) {
				for (int j = 1; j <= f.sequence.length(); j++) {
					m[k][j] = getScore(reference.charAt(k-1), f.sequence.charAt(j-1)) + Math.max(m[k - 1][j - 1], Math.max(gx[k - 1][j - 1], gy[k - 1][j - 1]));
					gx[k][j] = Math.max(m[k - 1][j] + d, Math.max(gx[k - 1][j] + e, gy[k - 1][j] + d));
					gy[k][j] = Math.max(m[k][j - 1] + d, Math.max(gx[k][j - 1] + d, gy[k][j - 1] + e));
				}
			}

			// Set score to the maximum value from the 3 DP matrices
			f.score = Math.max(m[reference.length()][f.sequence.length()], Math.max(gx[reference.length()][f.sequence.length()], gy[reference.length()][f.sequence.length()]));
		}

		// Sort the fastas according to their scores
		// FastaSeq implements Comparable, which enables sorting via Java Collections
		Collections.sort(fastas);

		// Print the result
		for (int i = 0; i < 3; i++) {
			FastaSeq f = fastas.get(i);
			System.out.print("Index=" + f.index);
			System.out.print(" Name=" + f.name);
			System.out.println(" Score=" + f.score);
			System.out.println("");	
		}
		
	}

	// Given 2 amino acids, return the BLOSUM50 score of the amino acids
	public static int getScore(char a, char b) {
		int first = 0;
		int second = 0;
		switch(a) {
			case 'A': first = 0; break;
			case 'R': first = 1; break;
			case 'N': first = 2; break;
			case 'D': first = 3; break;
			case 'C': first = 4; break;
			case 'Q': first = 5; break;
			case 'E': first = 6; break;
			case 'G': first = 7; break;
			case 'H': first = 8; break;
			case 'I': first = 9; break;
			case 'L': first = 10; break;
			case 'K': first = 11; break;
			case 'M': first = 12; break;
			case 'F': first = 13; break;
			case 'P': first = 14; break;
			case 'S': first = 15; break;
			case 'T': first = 16; break;
			case 'W': first = 17; break;
			case 'Y': first = 18; break;
			case 'V': first = 19; break;
			case 'B': first = 20; break;
			case 'J': first = 21; break;
			case 'Z': first = 22; break;
			case 'X': first = 23; break;
		}
		switch(b) {
			case 'A': second = 0; break;
			case 'R': second = 1; break;
			case 'N': second = 2; break;
			case 'D': second = 3; break;
			case 'C': second = 4; break;
			case 'Q': second = 5; break;
			case 'E': second = 6; break;
			case 'G': second = 7; break;
			case 'H': second = 8; break;
			case 'I': second = 9; break;
			case 'L': second = 10; break;
			case 'K': second = 11; break;
			case 'M': second = 12; break;
			case 'F': second = 13; break;
			case 'P': second = 14; break;
			case 'S': second = 15; break;
			case 'T': second = 16; break;
			case 'W': second = 17; break;
			case 'Y': second = 18; break;
			case 'V': second = 19; break;
			case 'B': second = 20; break;
			case 'J': second = 21; break;
			case 'Z': second = 22; break;
			case 'X': second = 23; break;
		}
		return blosum62[first][second];
	}

	// Object representation of a fasta sequence
	public static class FastaSeq implements Comparable<FastaSeq> {
		public int index;
		public String name;
		public String sequence;
		public int score;

		public FastaSeq(int i, String n) {
			index = i;
			name = n;
			sequence = "";
		}

		public int compareTo(FastaSeq other) {
			return (other.score - score);
		}
	}
}