import java.io.*;
import java.util.*;

public class Main {
    static int answer, N;
    static int[][] arr;
    static int[] dr = {-1, -1, 1, 1}, dc = {1, -1, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());

        arr = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 2; i < N; i++) {
            for (int j = 1; j < N - 1; j++) {
                int[] pos = {i, j};
                sum(pos, pos, 0, 0, 0);
            }
        }

        System.out.println(answer);
    }

    static void sum(int[] start, int[] cur, int dir, int count, int sum) {
        if (count >= 3 && start[0] == cur[0] && start[1] == cur[1]) {
            answer = Math.max(answer, sum);
            return;
        }


        int nr, nc;
        for (int i = dir; i < 4; i++) {
            nr = cur[0] + dr[i];
            nc = cur[1] + dc[i];
            if (nr >= 0 && nr < N && nc >= 0 && nc < N) {
                sum(start, new int[] {nr, nc}, i, count + 1, sum + arr[nr][nc]);
            }
        }

    }
}