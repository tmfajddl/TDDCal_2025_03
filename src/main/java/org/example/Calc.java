package org.example;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Calc {

    public static boolean debug = true;
    public static int runCallCount = 0;

    public static int run(String exp) {
        runCallCount++;
        exp = exp.replaceAll("- ", "+ -"); // -를 +로 바꿈
        exp = exp.trim(); // 양 옆의 쓸데없는 공백 제거

        // 괄호 제거
        exp = stripOuterBrackets(exp);

        // 만약에 -( 패턴이라면, 내가 갖고있는 코드는 해석할 수 없으므로 해석할 수 있는 형태로 수정
        int[] pos = null;
        while ((pos = findCaseMinusBracket(exp)) != null) { // -소괄호 부분을 추출한 배열이 null이 아니라면
            exp = changeMinusBracket(exp, pos[0], pos[1]); // 소괄호 부분만 추출
        }

        exp = stripOuterBrackets(exp); // 괄호 해제

        if (debug) {
            System.out.printf("exp(%d) : %s\n", runCallCount, exp);
        }

        // 단일항이 들어오면 바로 리턴
        if (!exp.contains(" ")) {
            return Integer.parseInt(exp);
        }
        // 어떤 케이스인지 파악
        boolean needToMulti = exp.contains(" * ");
        boolean needToPlus = exp.contains(" + ") || exp.contains(" - ");
        boolean needToSplit = exp.contains("(") || exp.contains(")");
        boolean needToCompound = needToMulti && needToPlus;
        //()가 존재할 경우 괄호를 기준으로 2부분으로 나누어줌
        if (needToSplit) {
            int splitPointIndex = findSplitPointIndex(exp);

            String firstExp = exp.substring(0, splitPointIndex);
            String secondExp = exp.substring(splitPointIndex + 1);

            char operator = exp.charAt(splitPointIndex);

            exp = Calc.run(firstExp) + " " + operator + " " + Calc.run(secondExp);

            return Calc.run(exp);
        } else if (needToCompound) {
            String[] bits = exp.split(" \\+ ");

            String newExp = Arrays.stream(bits).mapToInt(Calc::run).mapToObj(e -> e + "").collect(Collectors.joining(" + "));

            return run(newExp);
        }

        if (needToPlus) {

            String[] bits = exp.split(" \\+ ");

            int sum = 0;

            for (int i = 0; i < bits.length; i++) {
                sum += Integer.parseInt(bits[i]);
            }

            return sum;
        } else if (needToMulti) {
            String[] bits = exp.split(" \\* ");

            int sum = 1;

            for (int i = 0; i < bits.length; i++) {
                sum *= Integer.parseInt(bits[i]);
            }

            return sum;
        }

        throw new RuntimeException("해석 불가 : 올바른 계산식이 아니야");
    }
    // -()를 () * -1 로 바꾸어서 계산하는 함수
    private static String changeMinusBracket(String exp, int startPos, int endPos) {
        String head = exp.substring(0, startPos); //-괄호 앞부분
        String body = "(" + exp.substring(startPos + 1, endPos + 1) + " * -1)"; // -괄호 부분을 *1로 치환
        String tail = exp.substring(endPos + 1); // -괄호 뒷부분

        exp = head + body + tail; //셋을 다시 연결

        return exp; //exp 리턴
    }
    //-괄호를 찾는 함수
    private static int[] findCaseMinusBracket(String exp) {
        for (int i = 0; i < exp.length() - 1; i++) {
            if (exp.charAt(i) == '-' && exp.charAt(i + 1) == '(') {
                // -소괄호 발견한다면 소괄호의 위치를 알아낸다.

                int bracketsCount = 1;

                for (int j = i + 2; j < exp.length(); j++) {
                    char c = exp.charAt(j);

                    if (c == '(') {
                        bracketsCount++;
                    } else if (c == ')') {
                        bracketsCount--;
                    }

                    if (bracketsCount == 0) {
                        return new int[]{i, j};
                    }
                }
            }
        }

        return null;
    }
    // 자를 부분을 찾는 함수
    private static int findSplitPointIndex(String exp) {
        int index = findSplitPointIndexBy(exp, '+');

        if (index >= 0) return index;

        return findSplitPointIndexBy(exp, '*');
    }
    //연산자릐 인덱스를 찾는 함수
    private static int findSplitPointIndexBy(String exp, char findChar) {
        int bracketsCount = 0;

        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);

            if (c == '(') {
                bracketsCount++;
            } else if (c == ')') {
                bracketsCount--;
            } else if (c == findChar) {
                if (bracketsCount == 0) return i;
            }
        }
        return -1;
    }
    // 가장 바깥쪽의 ()제거
    private static String stripOuterBrackets(String exp) {
        if (exp.charAt(0) == '(' && exp.charAt(exp.length() - 1) == ')') { //괄호가 처음과 끝에 존재한다면
            int bracketsCount = 0; //괄호의 짝을 찾겠다.

            for (int i = 0; i < exp.length(); i++) {
                if (exp.charAt(i) == '(') {
                    bracketsCount++;
                } else if (exp.charAt(i) == ')') {
                    bracketsCount--;
                }

                if (bracketsCount == 0) { // 괄호 짝을찾았을 떄 그게 식의 마지막이라면
                    if (exp.length() == i + 1) {
                        return stripOuterBrackets(exp.substring(1, exp.length() - 1)); // 그괄호를 벗기고 나오겠다.
                    }

                    return exp; //마지막이 아니라면 exp 리턴
                }
            }
        }

        return exp; //exp 리턴
    }
}