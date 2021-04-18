package com.company;
import java.util.*;

public class Main {

    public static boolean checkSyntax(String in){
        int ocb = 0;
        int symbolStreak = 0;
        int operandStreak = 0;
        int operandSymbolNumber = 0;
        int symbolQuantity = 0;
        int brackets = 0;
        int negativeStreak = 0;
        boolean check = true;
        for (int i = 0; i < in.length(); i++){
            while(in.charAt(i) == ' '){
                i++;
            }
            if(in.charAt(i) == '('){
                if (symbolStreak == 1 && operandStreak == 0){
                    return false;
                }
                ocb++;
                brackets++;
                check = true;
                symbolStreak = 0;
                operandStreak = 0;
                operandSymbolNumber = 0;
            } else {
                check = false;
            }
            if(in.charAt(i) == ')' && !check){
                ocb--;
                check = true;
                symbolStreak = 1;
            }
            int symbolNumber = (int) in.charAt(i);
            if(symbolNumber < 91 && 64 < symbolNumber && !check){
                check = true;
                negativeStreak = 0;
                if (operandStreak == 0){
                    symbolStreak++;
                    symbolQuantity++;
                    operandSymbolNumber = symbolNumber;
                } else {
                    operandStreak--;
                    symbolStreak--;
                    symbolQuantity++;
                    if (symbolNumber ==operandSymbolNumber){
                        return false;
                    }
                }
            }
            if (symbolNumber == 49 && !check){
                check = true;
                if (operandStreak == 0){
                    symbolStreak++;
                    symbolQuantity++;
                    operandSymbolNumber = symbolNumber;
                } else {
                    operandStreak--;
                    symbolStreak--;
                    symbolQuantity++;
                    if (symbolNumber ==operandSymbolNumber){
                        return false;
                    }
                }
            }
            if (symbolNumber == 48 && !check){
                check = true;
                if (operandStreak == 0){
                    symbolStreak++;
                    symbolQuantity++;
                    operandSymbolNumber = symbolNumber;
                } else {
                    operandStreak--;
                    symbolStreak--;
                    symbolQuantity++;
                    if (symbolNumber ==operandSymbolNumber){
                        return false;
                    }
                }
            }
            if (symbolNumber == 45 && !check) {
                i++;
                symbolNumber = (int) in.charAt(i);
                if (symbolNumber == 62){
                    check = true;
                    operandStreak++;
                } else {
                    return false;
                }
            }
            if (symbolNumber == 126 && !check){
                check = true;
                operandStreak++;
            }
            if (symbolNumber == 92 && !check) {
                i++;
                symbolNumber = (int) in.charAt(i);
                if (symbolNumber == 47){
                    check = true;
                    operandStreak++;
                } else {
                    return false;
                }
            }
            if (symbolNumber == 47 && !check) {
                i++;
                symbolNumber = (int) in.charAt(i);
                if (symbolNumber == 92){
                    check = true;
                    operandStreak++;
                } else {
                    return false;
                }
            }
            if (symbolNumber == 33 && !check){
                check = true;
                negativeStreak++;
                if (negativeStreak > 1){
                    return false;
                }
            }
            if(!check){
                return false;
            }
            if (symbolStreak > 1 || operandStreak > 1){
                return  false;
            }
            if (symbolStreak == 0 && operandStreak == 1){
                return false;
            }
        }
        if (symbolStreak == 1 && operandStreak == 1){
            return false;
        }
        if (symbolQuantity < 2){
            return false;
        }
        if(brackets == 0){
            return false;
        }
        return ocb == 0;
    }

    public static String deleteSpaces(String in){
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < in.length(); i++){
            if(in.charAt(i) != ' '){
                res.append(in.charAt(i));
            }
        }
        return res.toString();
    }

    public static String removeBrackets(String in){
        int brackets = 0;
        StringBuilder out = new StringBuilder();
        if(in.charAt(0)=='(' && in.charAt(in.length()-1) == ')') {
            brackets++;
            for (int i = 1; i < in.length(); i++) {
                if (in.charAt(i) =='('){
                    brackets++;
                }
                if (i == in.length()-1 && in.charAt(i) == ')'){
                    for (int f = 1; f < in.length()-1; f++){
                        out.append(in.charAt(f));
                    }
                    String outy = removeBrackets(out.toString());
                    return outy;
                }
                if (in.charAt(i) == ')'){
                    brackets--;
                }
                if (brackets == 0){
                    return in;
                }
            }
        }
        return in;
    }

    public static boolean isKNF(String in){
        int depth = 0;
        String formula = in;
        formula = removeBrackets(formula);
        for(int i = 0; i < formula.length(); i++){
            int ch = (int) formula.charAt(i);
            switch (ch) {
                case 40 -> depth++;
                case 41 -> depth--;
                case 92 -> {
                    if (depth == 0) {
                        return false;
                    }
                    i++;
                }
                case 47 -> {
                    if (depth >= 1) {
                        return false;
                    }
                    i++;
                }
                case 126 ->{
                    return false;
                }
                case 45 ->{
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String input;
        Scanner in = new Scanner(System.in);
        input = in.nextLine();
        if(input.equals("")){
            System.out.println("Необходимо ввести формулу!");
        }
        input = deleteSpaces(input);
        if (checkSyntax(input)) {
            if (isKNF(input)) {
                System.out.println("Формула является КНФ");
            } else {
                System.out.println("Формула не является КНФ");
            }
        } else {
            System.out.println("Некорректное выражение!");
        }
    }
}