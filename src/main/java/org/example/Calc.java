package org.example;

import javax.swing.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Calc {

    public static int run(String exp) {
        // 괄호 제거
        exp = stripOuterBrackets(exp);

        // 단일항이 들어오면 바로 리턴
        if (!exp.contains(" ")) {
            return Integer.parseInt(exp);
        }

        boolean needToMulti = exp.contains("*");
        boolean needToPlus = exp.contains("+") || exp.contains("-");
        boolean needToSplit = exp.contains("(") || exp.contains(")");
        boolean needToCompound = needToPlus && needToMulti;

        if (needToSplit) {
            int bracketsCount = 0;
            int splitPointIndex = -1;
            int splitStartPointIndex = 0;

            for (int i = 0; i < exp.length(); i++) {
                if (exp.charAt(i) == '(') {
                    bracketsCount++;
                } else if (exp.charAt(i) == ')') {
                    bracketsCount--;
                }
            }
            if (bracketsCount == 0){
                for(int i = 0; i < exp.length(); i++){
                    if(exp.charAt(i) == '(')
                        splitStartPointIndex = i;
                    else if (exp.charAt(i) == ')')
                        splitPointIndex = i;
                }
            }
            if(splitStartPointIndex == 0){
                String firstExp = exp.substring(0, splitPointIndex + 1);
                String secondExp = exp.substring(splitPointIndex + 2);
                if(secondExp.contains("*")){
                    return Calc.run(firstExp) * Calc.run(secondExp);
                }

                return Calc.run(firstExp) + Calc.run(secondExp);
            }
            else{
                String firstExp = exp.substring(splitStartPointIndex, splitPointIndex + 1);
                String secondExp = exp.substring(0,splitStartPointIndex -1);
                if(secondExp.contains("*")){
                    return Calc.run(firstExp) * Calc.run(secondExp);
                }

                return Calc.run(firstExp) + Calc.run(secondExp);
            }


        } else if (needToCompound) {
            String[] bits = exp.split(" \\+ ");

            String newExp = Arrays.stream(bits)
                    .mapToInt(Calc::run)
                    .mapToObj(e -> e + "")
                    .collect(Collectors.joining(" + "));

            return run(newExp);
        }

        if (needToPlus) {
            exp = exp.replace("- ", "+ -");
            int sum = 0;
            if(!exp.contains(" + ")){
                if(exp.contains("+ ")){
                    String[] bits = exp.split("\\+ ");
                    for (int i = 1; i < bits.length; i++) {
                        sum *= Integer.parseInt(bits[i]);
                    }
                }
                else{
                    String[] bits = exp.split(" \\+");
                    sum = Integer.parseInt(bits[0]);
                }
            }
            else {
                String[] bits = exp.split(" \\+ ");

                for (int i = 0; i < bits.length; i++) {
                    sum += Integer.parseInt(bits[i]);
                }
            }

            return sum;
        } else if (needToMulti) {
            int sum = 1;
            if(!exp.contains(" * ")) {
                if(exp.contains("* ")){
                    String[] bits = exp.split("\\* ");
                    for (int i = 1; i < bits.length; i++) {
                        sum *= Integer.parseInt(bits[i]);
                    }
                }
                else{
                    String[] bits = exp.split(" \\*");
                    sum = Integer.parseInt(bits[0]);
                }
            }
            else{
                String[] bits = exp.split(" \\* ");
                for (int i = 0; i < bits.length; i++) {
                    sum *= Integer.parseInt(bits[i]);
                }
            }
            return sum;
        }

        throw new RuntimeException("해석 불가 : 올바른 계산식이 아닙니다");
    }

    private static String stripOuterBrackets(String exp) {

        int outerBracketsCount = 0;

        while (exp.charAt(outerBracketsCount) == '(' && exp.charAt(exp.length() - 1 - outerBracketsCount) == ')') {
            outerBracketsCount++;
        }

        if (outerBracketsCount == 0) return exp;

        return exp.substring(outerBracketsCount, exp.length() - outerBracketsCount);
    }

}