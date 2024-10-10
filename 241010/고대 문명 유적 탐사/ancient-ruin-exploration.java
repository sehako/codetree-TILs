import java.io.*;
import java.util.*;

public class Main {
    static int K, M, treasureIdx;
    static int[][] map;
    static boolean[][] check;
    static int[] spare, dr = {0, 1, 0, -1}, dc = {1, 0, -1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine(), " ");

        K = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[5][5];
        for (int i = 0; i < 5; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < 5; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        spare = new int[M];
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < M; i++) spare[i] = Integer.parseInt(st.nextToken());

        int turn = 0, answer, temp, count;
        StringBuilder sb = new StringBuilder();
        while (turn++ < K) {
            if (!rotateArray()) break;

            answer = 0;
            count = 0;
            while (true) {
                temp = getValue();

                if (temp == -1) break;
                answer += temp;
                fillTreasure();
                count++;
            }

            if (count == 0) break;

            sb.append(answer).append(' ');
        }

        System.out.println(sb);
    }


    static boolean rotateArray() {
        int[] rotatePos = new int[3];
        int cost = 0;
        int tempValue;
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                for (int k = 0; k < 4; k++) {
                    rotate(i, j, 0);
                    if (k == 3) continue;
                    tempValue = getValue();
                    if ((cost == 0 && tempValue != -1) || cost < tempValue) {
                        rotatePos[0] = i;
                        rotatePos[1] = j;
                        rotatePos[2] = k;
                        cost = tempValue;
                    } else if (cost == tempValue) {
                        if (k < rotatePos[2]) {
                            rotatePos[0] = i;
                            rotatePos[1] = j;
                            rotatePos[2] = k;
                        } else if (k == rotatePos[2]) {
                            if (j < rotatePos[1]) {
                                rotatePos[0] = i;
                                rotatePos[1] = j;
                            } else if (j == rotatePos[1]) {
                                if (i < rotatePos[0]) {
                                    rotatePos[0] = i;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (cost != 0) {
            rotate(rotatePos[0], rotatePos[1], rotatePos[2]);
            return true;
        }
        return false;
    }

    static int getValue() {
        check = new boolean[5][5];
        int count = 0;
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                if (!check[r][c]) {
                    check[r][c] = true;
                    if (!find(r, c, map[r][c], 1)) check[r][c] = false;
                    else count++;
                }
            }
        }

        if (count != 0) return countValue();
        return -1;
    }

    static boolean find(int r, int c, int num, int count) {
        boolean result = count >= 3;

        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];

            if (nr < 0 || nr >= 5 || nc < 0 || nc >= 5) continue;
            if (check[nr][nc] || map[nr][nc] != num) continue;

            check[nr][nc] = true;
            result = find(nr, nc, num, count + 1);

            if (!result) check[nr][nc] = false;
        }

        return result;
    }

    static int countValue() {
        int count = 0;
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                if (check[r][c]) count++;
            }
        }

        return count;
    }

    static void fillTreasure() {
        for (int c = 0; c < 5; c++) {
            for (int r = 4; r >= 0; r--) {
                if (check[r][c]) map[r][c] = spare[treasureIdx++];
            }
        }
    }

    static void rotate(int r, int c, int degree) {

        int[] temp = new int[8];

        int row = r - 1, col = c - 1, pos = 0;
        for (int i = 0; i < 8; i++) {
            if (i > 0 && i % 2 == 0) pos++;
            temp[i] = map[row][col];
            row += dr[pos];
            col += dc[pos];
        }

        int idx;
        if (degree == 0) idx = 6;
        else if (degree == 1) idx = 4;
        else idx = 2;
        row = r - 1;
        col = c - 1;
        pos = 0;
        for (int i = 0; i < 8; i++) {
            if (i > 0 && i % 2 == 0) pos++;
            map[row][col] = temp[idx % 8];
            row += dr[pos];
            col += dc[pos];
            idx = (idx + 1) % 8;
        }
    }
}