import java.util.*;
import java.io.*;

public class Main {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		int n = Integer.parseInt(st.nextToken());
		int t = Integer.parseInt(st.nextToken());
		
		int[][] belt = new int[3][n];
		
		for (int i = 0; i < 3; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < n; j++) {
				belt[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		int count = 0;
		while (count < t) {
			int a = belt[0][n - 1];
			int b = belt[1][n - 1];
			int c = belt[2][n - 1];
			
			for (int i = 0; i < 3; i++) {
				for (int j = n - 1; j > 0; j--) {
					belt[i][j] = belt[i][j - 1];
				}
			}
			
			
			belt[0][0] = c;
			belt[1][0] = a;
			belt[2][0] = b;
			count++;
		}
		
		for (int[] temp : belt) {
			for (int num : temp) {
				System.out.print(num + " ");
			}
			System.out.println();
		}
	}
}