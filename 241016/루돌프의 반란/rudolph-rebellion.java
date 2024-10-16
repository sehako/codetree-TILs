import java.util.*;
import java.io.*;

public class Main {
    static int N;
    // 8방 탐색
    static int[][] dir = {
            {-1, 0},
            {-1, 1},
            {0, 1},
            {1, 1},
            {1, 0},
            {1, -1},
            {0, -1},
            {-1, -1}
    };

    static Unit[][] map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine(), " ");

        // 게임 사전 세팅
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int P = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        int D = Integer.parseInt(st.nextToken());

        // 2는 루돒, 1은 산타
        map = new Unit[N][N];

        st = new StringTokenizer(br.readLine(), " ");
        int r = Integer.parseInt(st.nextToken()) - 1;
        int c = Integer.parseInt(st.nextToken()) - 1;
        Unit rudolf = new Unit(-1, r, c);
        map[r][c] = rudolf;

        List<Unit> santaList = new ArrayList<>(P);
        for (int i = 0; i < P; i++) {
            st = new StringTokenizer(br.readLine());
            int no = Integer.parseInt(st.nextToken());
            r = Integer.parseInt(st.nextToken()) - 1;
            c = Integer.parseInt(st.nextToken()) - 1;
            Unit temp = new Unit(no, r, c);
            map[r][c] = temp;
            santaList.add(temp);
        }
        
        Collections.sort(santaList, (e1, e2) -> Integer.compare(e1.no, e2.no));

        startGame(M, C, D, rudolf, santaList);

        StringBuilder sb = new StringBuilder();

        for (Unit santa : santaList) sb.append(santa.score).append(' ');

        System.out.println(sb);
    }

    static void startGame(int turn, int rudolfPower, int santaPower, Unit rudolf, List<Unit> santaList) {
        int cnt = 1;
        int aliveSanta = santaList.size();
        int dirIdx;
        while (cnt <= turn && aliveSanta > 0) {
            dirIdx = findDir(rudolf, santaList);

            if (dirIdx != -1) move(rudolf, dirIdx, rudolfPower, cnt, true);

            for (Unit santa : santaList) {
                if (santa.knockDown > 1000 || santa.knockDown + 2 > cnt) continue;

                dirIdx = findDir(santa, rudolf, getDist(santa.r, santa.c, rudolf.r, rudolf.c));
                if (dirIdx != -1) move(santa, dirIdx, santaPower, cnt, false);
                
                
            }
            aliveSanta = refreshSanta(santaList);
            cnt++;
        }
    }

    static int findDir(Unit rudolf, List<Unit> santaList) {
        double orgDist = Double.MAX_VALUE;
        int dirIdx = 0;
        Unit targetSanta = new Unit(-100, 51, 51);
        int nr, nc;
        for (Unit santa : santaList) {
        	if (santa.knockDown > 1000) continue;
        	
            double dist = getDist(rudolf.r, rudolf.c, santa.r, santa.c);

            if (orgDist > dist) {
                orgDist = dist;
                targetSanta = santa;
            }

            if (orgDist == dist) {
                if (targetSanta.r < santa.r) {
                    targetSanta = santa;
                } else if (targetSanta.r == santa.r && targetSanta.c < santa.c) {
                    targetSanta = santa;
                }
            }
        }
        
        for (int i = 0; i < 8; i++) {
            nr = rudolf.r + dir[i][0];
            nc = rudolf.c + dir[i][1];

            if (isImpossible(nr, nc)) continue;
            double dist = getDist(nr, nc, targetSanta.r, targetSanta.c);
            if (orgDist > dist) {
                orgDist = dist;
                dirIdx = i;
            }
        }

        return orgDist == Double.MAX_VALUE ? -1 : dirIdx;
    }

    static int findDir(Unit santa, Unit rudolf, double orgDist) {
        double minDist = orgDist;
        int dirIdx = 0;
        int nr, nc;
        for (int i = 0; i < 8; i += 2) {
            nr = santa.r + dir[i][0];
            nc = santa.c + dir[i][1];
            if (isImpossible(nr, nc)) continue;
            if ((map[nr][nc] != null && map[nr][nc].no > 0)) continue;

            double dist = getDist(nr, nc, rudolf.r, rudolf.c);

            if (minDist > dist) {
                minDist = dist;
                dirIdx = i;
            }
        }

        return minDist == orgDist ? -1 : dirIdx;
    }

    static void move(Unit target, int dirIdx, int power, int turn, boolean isRudolf) {
        map[target.r][target.c] = null;
        int nr = target.r += dir[dirIdx][0];
        int nc = target.c += dir[dirIdx][1];
        if (map[nr][nc] != null) {
            if (isRudolf) {
            	crash(map[nr][nc], dirIdx, power, turn);
            	map[target.r][target.c] = target;
            }
            else {
                int crashDir;
                if (dirIdx == 0) crashDir = 4;
                else if (dirIdx == 2) crashDir = 6;
                else if (dirIdx == 4) crashDir = 0;
                else crashDir = 2;
                crash(target, crashDir, power, turn);
            }
        } else map[target.r][target.c] = target;
    }

    static void crash(Unit target, int dirIdx, int power, int turn) {
        target.score += power;
        target.knockDown = turn;

        for (int i = 0; i < power; i++) {
            target.r += dir[dirIdx][0];
            target.c += dir[dirIdx][1];
        }

        if (isImpossible(target.r, target.c)) {
            target.knockDown = 1001;
            return;
        }
        if (map[target.r][target.c] != null) {
            multipleCrash(target.r, target.c, dirIdx);
            map[target.r][target.c] = target;
        }
        else map[target.r][target.c] = target;
    }

    // 재귀로 짜봐 임마
    static void multipleCrash(int r, int c, int dirIdx) {
        Unit target = map[r][c];
        map[r][c] = null;
        target.r += dir[dirIdx][0];
        target.c += dir[dirIdx][1];

        if (isImpossible(target.r, target.c)) {
            target.knockDown = 1001;
            return;
        }

        if (map[target.r][target.c] != null) {
            multipleCrash(target.r, target.c, dirIdx);
        }
        map[target.r][target.c] = target;

    }

    static boolean isImpossible(int r, int c) {
        return r < 0 || r >= N || c < 0 || c >= N;
    }

    static double getDist(int r1, int c1, int r2, int c2) {
        return Math.pow(r1 - r2, 2) + Math.pow(c1 - c2, 2);
    }

    static int refreshSanta(List<Unit> santaList) {
        int count = 0;

        for (Unit santa : santaList) {
            if (santa.knockDown > 1000) continue;

            santa.score++;
            count++;
        }

        return count;
    }

    static class Unit {
        int no, r, c, score, knockDown;

        public Unit(int no, int r, int c) {
            this.no = no;
            this.r = r;
            this.c = c;
            knockDown = -1;
        }

        @Override
        public String toString() {
            return String.valueOf(no + " " + knockDown);
        }
    }
}