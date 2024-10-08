import java.io.*;
import java.util.*;

public class Main {
    static int[][] costs;
    static int[] morningWork, nightWork, selected;
    static boolean[] check;
    static int n, morningCost, nightCost, answer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        n = Integer.parseInt(br.readLine());
        costs = new int[n][n];
        nightWork = new int[n / 2];
        morningWork = new int[n / 2];
        check = new boolean[n];
        selected = new int[2];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < n; j++) {
                costs[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        answer = Integer.MAX_VALUE;
        selectWork(0, 0);
        System.out.println(answer);
    }

    static void selectWork(int depth, int idx) {
        if (idx == n / 2) {
            int pos = 0;
            for (int i = 0; i < n; i++) {
                if (!check[i]) nightWork[pos++] = i;
            }

            morningCost = 0;
            nightCost = 0;
            calculate(morningWork, 0, 0, true);
            calculate(nightWork, 0, 0, false);

            answer = Math.min(Math.abs(morningCost - nightCost), answer);
            return;
        }

        for (int i = depth; i < n; i++) {
            morningWork[idx] = i;
            check[i] = true;
            selectWork(i + 1, idx + 1);
            check[i] = false;
        }
    }

    static void calculate(int[] arr, int depth, int idx, boolean isMorning) {
        if (idx == 2) {
            int value = getCost(selected[0], selected[1]);

            if (isMorning) morningCost += value;
            else nightCost += value;
            return;
        }

        for (int i = depth; i < arr.length; i++) {
            selected[idx] = arr[i];
            calculate(arr, i + 1, idx + 1, isMorning);
        }
    }

    static int getCost(int a, int b) {
        return costs[a][b] + costs[b][a];
    }

}