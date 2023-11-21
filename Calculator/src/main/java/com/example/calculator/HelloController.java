package com.example.calculator;

import Stack.ArrayStack;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    public Double calculating(String operation, StringBuilder history) {
        try {
            if (!this.checkParentheses(operation))
                throw new Exception();
            ArrayStack<Double> stackDigit = new ArrayStack<Double>(20);
            ArrayStack<Character> stackOperator = new ArrayStack<Character>(20);
            for (int i = 0; i < operation.length(); i++) {
                char c = operation.charAt(i);
                if (c == 'P')
                    stackDigit.push(3.14);
                else if (c == 'e')
                    stackDigit.push(2.7);
                else if (c == 'L') {
                    StringBuilder calLogarithm = new StringBuilder();
                    while (true) {
                        calLogarithm.append(operation.charAt(++i));
                        if (!(i != operation.length() - 1 && (Character.isDigit(operation.charAt(i + 1)) || operation.charAt(i + 1) == '.')))
                            break;
                    }
                    stackDigit.push(Math.log10(Double.parseDouble(String.valueOf(calLogarithm))));
                } else if (c == 'S') {
                    StringBuilder calSin = new StringBuilder();
                    while (true) {
                        calSin.append(operation.charAt(++i));
                        if (!(i != operation.length() - 1 && (Character.isDigit(operation.charAt(i + 1)) || operation.charAt(i + 1) == '.')))
                            break;
                    }
                    stackDigit.push(Math.log(Double.parseDouble(String.valueOf(calSin))));
                } else if (c == 'C') {
                    i++;
                    StringBuilder calCos = new StringBuilder();
                    while (true) {
                        calCos.append(operation.charAt(++i));
                        if (!(i != operation.length() - 1 && (Character.isDigit(operation.charAt(i + 1)) || operation.charAt(i + 1) == '.')))
                            break;
                    }
                    stackDigit.push(Double.parseDouble(String.valueOf(calCos)));
                } else if (Character.isDigit(c)) {
                    StringBuilder digit = new StringBuilder();
                    digit.append(c);
                    if (i != operation.length() - 1 && (Character.isDigit(operation.charAt(i + 1)) || operation.charAt(i + 1) == '.')) {
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
                        this.calculatingStack(stackDigit, stackOperator.pop(), history);
                    stackOperator.pop();
                } else if (!stackOperator.isEmpty() && this.giveValue(stackOperator.peek()) == this.giveValue(c) && c == '^') {
                    stackOperator.push(c);
                } else if (!stackOperator.isEmpty() && this.giveValue(stackOperator.peek()) == this.giveValue(c)) {
                    this.calculatingStack(stackDigit, stackOperator.pop(), history);
                    stackOperator.push(c);
                } else {
                    while (!stackOperator.isEmpty() && this.giveValue(stackOperator.peek()) > this.giveValue(c))
                        this.calculatingStack(stackDigit, stackOperator.pop(), history);
                    stackOperator.push(c);
                }
            }
            while (!stackOperator.isEmpty())
                this.calculatingStack(stackDigit, stackOperator.pop(), history);
            return stackDigit.pop();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("wrong pattern of operation");
            alert.setTitle("ERROR");
            alert.show();
        }
        return null;
    }

    public int giveValue(char c) {
        return switch (c) {
            case '+', '-' -> 1;
            case '/', '*' -> 2;
            case '^' -> 3;
            case '!' -> 4;
            default -> 0;
        };
    }

    public void calculatingStack(ArrayStack<Double> stack, char operator, StringBuilder history1) throws Exception {
        double firstDigit = stack.pop();
        double secondDigit = 0;
        if (operator != '!' && stack.isEmpty())
            throw new Exception();
        if (!stack.isEmpty())
            secondDigit = stack.peek();
        switch (operator) {
            case '+':
                stack.pop();
                stack.push(firstDigit + secondDigit);
                history1.append(secondDigit).append("+").append(firstDigit).append("   ");
                break;
            case '-':
                stack.pop();
                stack.push(secondDigit - firstDigit);
                history1.append(secondDigit).append("-").append(firstDigit).append("   ");
                break;
            case '*':
                stack.pop();
                stack.push(firstDigit * secondDigit);
                history1.append(secondDigit).append("*").append(firstDigit).append("   ");
                break;
            case '/':
                stack.pop();
                stack.push(secondDigit / firstDigit);
                history1.append(secondDigit).append("/").append(firstDigit).append("   ");
                break;
            case '!':
                stack.push(factorial(firstDigit));
                history1.append(firstDigit).append("!").append("   ");
                break;
            case '^':
                stack.pop();
                stack.push(Math.pow(secondDigit, firstDigit));
                history1.append(secondDigit).append("^").append(firstDigit).append("   ");
        }
    }

    public double factorial(double n) {
        if (n == 0 || n == 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    public boolean checkParentheses(String str) {
        int count1 = 0;
        int count2 = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(')
                count1++;
            else if (str.charAt(i) == ')')
                count2++;
        }
        return count1 == count2;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private Label answer;
    @FXML
    private Label history;

    @FXML
    private TextField textOperation;

    @FXML
    void E2(ActionEvent event) {
        this.textOperation.appendText("e");
    }

    @FXML
    void PI(ActionEvent event) {
        this.textOperation.appendText("P");
    }

    @FXML
    void deleteAll(ActionEvent event) {
        this.textOperation.deleteText(0, this.textOperation.getLength());
    }

    @FXML
    void divide(ActionEvent event) {
        this.textOperation.appendText("/");
    }

    @FXML
    void dot(ActionEvent event) {
        this.textOperation.appendText(".");
    }

    @FXML
    void eight(ActionEvent event) {
        this.textOperation.appendText("8");
    }

    @FXML
    void equalSign(ActionEvent event) {
        try {
            StringBuilder str = new StringBuilder();
            double answer = this.calculating(this.textOperation.getText(), str);
            double num = answer;
            DecimalFormat df = new DecimalFormat("#.####");
            this.answer.setText("Answer: " + df.format(answer));
            this.history.setText(String.valueOf(str));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("wrong pattern of operation");
            alert.setTitle("ERROR");
            alert.show();
        }
    }

    @FXML
    void exponent(ActionEvent event) {
        this.textOperation.appendText("^");
    }

    @FXML
    void fac(ActionEvent event) {
        this.textOperation.appendText("!");
    }

    @FXML
    void five(ActionEvent event) {
        this.textOperation.appendText("5");
    }

    @FXML
    void four(ActionEvent event) {
        this.textOperation.appendText("4");
    }

    @FXML
    void minus(ActionEvent event) {
        this.textOperation.appendText("-");
    }

    @FXML
    void multiplication(ActionEvent event) {
        this.textOperation.appendText("*");
    }

    @FXML
    void nine(ActionEvent event) {
        this.textOperation.appendText("9");
    }

    @FXML
    void one(ActionEvent event) {
        this.textOperation.appendText("1");
    }

    @FXML
    void plus(ActionEvent event) {
        this.textOperation.appendText("+");
    }

    @FXML
    void seven(ActionEvent event) {
        this.textOperation.appendText("7");
    }

    @FXML
    void six(ActionEvent event) {
        this.textOperation.appendText("6");
    }

    @FXML
    void three(ActionEvent event) {
        this.textOperation.appendText("3");
    }

    @FXML
    void two(ActionEvent event) {
        this.textOperation.appendText("2");
    }

    @FXML
    void zero(ActionEvent event) {
        this.textOperation.appendText("0");
    }

    @FXML
    void log(ActionEvent event) {
        this.textOperation.appendText("L");
    }

    @FXML
    void sin(ActionEvent event) {
        this.textOperation.appendText("S");
    }

    @FXML
    void cos(ActionEvent event) {
        this.textOperation.appendText("C");
    }

}
