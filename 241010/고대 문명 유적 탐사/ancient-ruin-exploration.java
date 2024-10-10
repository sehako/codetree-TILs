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
        int[][] rotateArray;
        StringBuilder sb = new StringBuilder();
        while (turn++ < K) {
            rotateArray = getArray();

            if (rotateArray == null) break;

            answer = 0;
            count = 0;
            while (true) {
                temp = getValue(rotateArray);

                if (temp == -1) break;
                answer += temp;
                fillTreasure(rotateArray);
                count++;
            }

            if (count == 0) break;

            map = rotateArray;
            sb.append(answer).append(' ');
        }

        System.out.println(sb);
    }


    static int[][] getArray() {
        int[] rotatePos = new int[3];
        int cost = 0;
        int tempValue;
        for (int i = 1; i < 3; i++) {
            for (int j = 1; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    int[][] temp = rotate(i, j, k);
                    tempValue = getValue(temp);
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
                            if (j < rotatePos[1] || i < rotatePos[0]) {
                                rotatePos[0] = i;
                                rotatePos[1] = j;
                            }
                        }
                    }
                }
            }
        }

        if (cost == 0) return null;
        return rotate(rotatePos[0], rotatePos[1], rotatePos[2]);
    }

    static int getValue(int[][] arr) {
        check = new boolean[5][5];
        int count = 0;
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                if (!check[r][c]) {
                    check[r][c] = true;
                    if (!find(arr, r, c, arr[r][c], 1)) check[r][c] = false;
                    else count++;
                }
            }
        }

        if (count != 0) return countValue();
        return -1;
    }

    static boolean find(int[][] arr, int r, int c, int num, int count) {
        boolean result = count >= 3;

        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];

            if (nr < 0 || nr >= 5 || nc < 0 || nc >= 5) continue;
            if (check[nr][nc] || arr[nr][nc] != num) continue;

            check[nr][nc] = true;
            result = find(arr, nr, nc, num, count + 1);

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

    static void fillTreasure(int[][] arr) {
        for (int c = 0; c < 5; c++) {
            for (int r = 4; r >= 0; r--) {
                if (check[r][c]) arr[r][c] = spare[treasureIdx++];
            }
        }
    }

    static int[][] rotate(int r, int c, int count) {
        int[][] result = new int[5][5];

        for (int i = 0; i < 5; i++) result[i] = Arrays.copyOf(map[i], 5);

        int[] temp = new int[8];

        int row = r - 1, col = c - 1, pos = 0;
        for (int i = 0; i < 8; i++) {
            if (i > 0 && i % 2 == 0) pos++;
            temp[i] = map[row][col];
            row += dr[pos];
            col += dc[pos];
        }

        int idx;
        if (count == 0) idx = 6;
        else if (count == 1) idx = 4;
        else idx = 2;
        row = r - 1;
        col = c - 1;
        pos = 0;
        for (int i = 0; i < 8; i++) {
            if (i > 0 && i % 2 == 0) pos++;
            result[row][col] = temp[idx % 8];
            row += dr[pos];
            col += dc[pos];
            idx = (idx + 1) % 8;
        }

        return result;
    }

}