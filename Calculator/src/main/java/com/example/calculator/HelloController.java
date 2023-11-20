package com.example.calculator;

import Stack.ArrayStack;

public class HelloController {
    public Double calculating(String operation) {
        ArrayStack<Double> stackDigit = new ArrayStack<Double>(20);
        ArrayStack<Character> stackOperator = new ArrayStack<Character>(20);
        for (int i = 0; i < operation.length(); i++) {
            char c = operation.charAt(i);
            if (Character.isDigit(c)) {
                StringBuilder digit = new StringBuilder();
                digit.append(c);
                if (i!=operation.length()-1 && Character.isDigit(operation.charAt(i + 1))) {
                    while (true) {
                        digit.append(operation.charAt(++i));
                        if (!Character.isDigit(operation.charAt(i + 1)))
                            break;
                    }
                }
                stackDigit.push(Double.parseDouble(String.valueOf(digit)));
            } else if (c == '(')
                stackOperator.push(c);
            else if (c == ')') {
                while (!stackOperator.isEmpty() && stackOperator.peek() != '(')
                    this.calculatingStack(stackDigit, stackOperator.pop());
                stackOperator.pop();
            } else if (!stackOperator.isEmpty() && this.giveValue(stackOperator.peek()) == this.giveValue(c) && c == '^') {
                stackOperator.push(c);
            } else if (!stackOperator.isEmpty() && this.giveValue(stackOperator.peek()) == this.giveValue(c)) {
                this.calculatingStack(stackDigit, stackOperator.pop());
                stackOperator.push(c);
            } else {
                while (!stackOperator.isEmpty() && this.giveValue(stackOperator.peek()) > this.giveValue(c))
                    this.calculatingStack(stackDigit, stackOperator.pop());
                stackOperator.push(c);
            }
        }
        while (!stackOperator.isEmpty())
            this.calculatingStack(stackDigit, stackOperator.pop());
        return stackDigit.pop();
    }

    public int giveValue(char c) {
        switch (c) {
            case '+':
            case '-':
                return 1;
            case '/':
            case '*':
                return 2;
            case '^':
                return 3;
            case '!':
                return 4;
            default:
                return 0;
        }
    }

    public void calculatingStack(ArrayStack<Double> stack, char operator) {
        double firstDigit = stack.pop();
        double secondDigit = 0;
        if (!stack.isEmpty())
            secondDigit = stack.peek();
        switch (operator) {
            case '+':
                stack.pop();
                stack.push(firstDigit + secondDigit);
                break;
            case '-':
                stack.pop();
                stack.push(firstDigit - secondDigit);
                break;
            case '*':
                stack.pop();
                stack.push(firstDigit * secondDigit);
                break;
            case '/':
                stack.pop();
                stack.push(firstDigit / secondDigit);
                break;
            case '!':
                stack.push(factorial(firstDigit));
                break;
            case '^':
                stack.pop();
                stack.push(Math.pow(secondDigit, firstDigit));
        }
    }

    public double factorial(double n) {
        if (n == 0 || n == 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }
}
