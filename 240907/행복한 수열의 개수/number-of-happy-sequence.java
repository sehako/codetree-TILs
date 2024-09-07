import java.util.*;
import java.io.*;

public class Main {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		
		int[][] arr = new int[n][n];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < n; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		int answer = 0;
		int[] temp = new int[n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				temp[j] = arr[i][j];
			}
			
			int cur = temp[0];
			int cnt = 1;
			for (int j = 1; j < n; j++) {
				if (cur == temp[j]) {
					cnt++;
				}
				else {
					cur = temp[j];
					cnt = 1;
				}
				if (cnt >= m) {
					answer++;
					break;
				}
			}
		}
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				temp[j] = arr[j][i];
			}
			
			int cur = temp[0];
			int cnt = 1;
			for (int j = 1; j < n; j++) {
				if (cur == temp[j]) {
					cnt++;
				}
				else {
					cur = temp[j];
					cnt = 1;
				}
				if (cnt >= m) {
					answer++;
					break;
				}
			}
		}
		
		System.out.println(answer);
	}
}