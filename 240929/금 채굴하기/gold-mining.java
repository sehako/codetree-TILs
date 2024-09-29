import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;

        st = new StringTokenizer(br.readLine(), " ");
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] map = new int[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int answer = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k <= n / 2 + 1; k++) {
                    int result = digGold(map, i, j, k);
                    int cost = k * k + (k + 1) * (k + 1);
                    if (cost <= result * m) answer = Math.max(answer, result);
                }
            }
        }
        System.out.println(answer);
    }

    static int digGold(int[][]map, int r, int c, int size) {
        int count = 0;
        int sr = r - size;
        int loop = size * 2 + 1;

        for (int i = 0; i < loop; i++) {
            int row = sr + i;
            if (i <= loop / 2) {
                if (row >= 0 && row < map.length) {
                    for (int j = c - i; j <= c + i; j++) {
                        if (j >= 0 && j < map.length && map[row][j] == 1) {
                            count++;
                        }
                    }
                }

            } else {
                if (row >= 0 && row < map.length) {
                    for (int j = i - loop + 1; j <= Math.abs(i - loop + 1); j++) {
                        int col = c + j;
                        if (col >= 0 && col < map.length && map[row][col] == 1) {
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }

}