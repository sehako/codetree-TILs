import java.util.*;
import java.io.*;

public class Main {
	static int n, m, answer;
	static int[][] map;
	static boolean[][] check;
	static int[] dr = {-1, 0, 1, 0};
	static int[] dc = {0, 1, 0, -1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		map = new int[n][m];
		check = new boolean[n][m];
		
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < m; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				check[i][j] = true;
				makeTromino(0, i, j, map[i][j]);
				check[i][j] = false;
			}
		}
		
		System.out.println(answer);
		
	}
	
	public static void makeTromino(int depth, int r, int c, int sum) {
		if (depth == 2) {
			answer = Math.max(answer, sum);
			return;
		}
		
		int nr, nc;
		for (int i = 0; i < 4; i++) {
			nr = r + dr[i];
			nc = c + dc[i];
			if (isPossible(nr, nc) && !check[nr][nc]) {
				check[nr][nc] = true;
				makeTromino(depth + 1, nr, nc, sum + map[nr][nc]);
				check[nr][nc] = false;
			}
		}
	}
	
	public static boolean isPossible(int r, int c) {
		return r >= 0 && r < n && c >= 0 && c < m;
	}
}