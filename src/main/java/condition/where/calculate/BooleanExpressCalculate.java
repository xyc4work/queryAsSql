package condition.where.calculate;

import condition.where.operator.Operators;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author ：Administrator
 * @description：计算boolean表达式
 * @date ：2020/9/26 15:26
 */
public class BooleanExpressCalculate {
    //运算式解析
    private List<String> expStrList = new ArrayList<String>();
    //存放逆波兰表达式
    private List<String> rpnStrList;

    public BooleanExpressCalculate(String express) {
        StringTokenizer st = new StringTokenizer(express, Operators.getDelim(), true);
        while (st.hasMoreElements()) {
            this.expStrList.add(st.nextElement().toString().trim());
        }
        this.rpnStrList = transferRPN(this.expStrList);
    }

    /**
     * 按照类的缺省参数进行计算
     *
     * @return
     */
    public Boolean calculate() {
        return Boolean.valueOf(this.calculate(this.rpnStrList));
    }

    /**
     * 表达式中操作符优先级
     *
     * @param operator
     * @return
     */
    private int precedence(String operator) {
        char sign = operator.charAt(0);
        switch (sign) {
            case '|':
                return 1;
            case '&':
                return 2;
            case '!':
                return 3;
            case '(':
            case ')':
            default:
                return 0;
        }
    }

    /**
     * 转化逆波兰表达式
     *
     * @param strList
     * @return
     */
    public List<String> transferRPN(List<String> strList) {
        List<String> returnList = new ArrayList<String>();
        //操作符栈
        Stack stack = new Stack();
        strList.stream().forEach(each -> {
            if (isBoolean(each)) {
                returnList.add(each);
            } else {
                if (each.equals(Operators.LEFT.getSymbol())) {
                    //'('直接入栈
                    stack.push(each);
                } else if (each.equals(Operators.RIGHT.getSymbol())) {
                    //')'进行出栈操作，直到栈为空或者遇到第一个左括号
                    while (!stack.isEmpty()) {
                        //将栈顶字符串做出栈操作
                        String stackTop = stack.pop();
                        if (!stackTop.equals(Operators.LEFT.getSymbol())) {
                            //如果不是左括号，则将字符串直接放到逆波兰链表的最后
                            returnList.add(stackTop);
                        } else {
                            //如果是左括号，退出循环操作
                            break;
                        }
                    }
                } else {
                    if (stack.isEmpty()) {
                        //如果栈内为空,将当前字符串直接压栈
                        stack.push(each);
                    } else {
                        //栈不空,比较运算符优先级顺序
                        if (precedence(stack.top()) >= precedence(each)) {
                            //如果栈顶元素优先级大于当前元素优先级则将字符串直接放到逆波兰链表的最后
                            while (!stack.isEmpty() && precedence(stack.top()) >= precedence(each)) {
                                returnList.add(stack.pop());
                            }
                        }
                        stack.push(each);
                    }
                }
            }
        });
        //如果栈不为空，则将栈中所有元素出栈放到逆波兰链表的最后
        while (!stack.isEmpty()) {
            returnList.add(stack.pop());
        }
        return returnList;
    }

    /**
     * 是否为boolean值
     *
     * @param s
     * @return
     */
    private boolean isBoolean(String s) {
        return (s != null) && s.equalsIgnoreCase("true")||
                (s != null) && s.equalsIgnoreCase("false");
    }

    /**
     * 计算生成的逆波兰表达式
     *
     * @param rpnList
     * @return
     */
    public Boolean calculate(List<String> rpnList) {
        Stack booleanStack = new Stack();
        rpnList.stream().forEach(rpnTemp -> {
            if (isBoolean(rpnTemp)) {
                booleanStack.push(rpnTemp);
            } else {
                Boolean result = false;
                if (rpnTemp.equals(Operators.AND.getSymbol())) {
                    Boolean booleanLeft = Boolean.valueOf(booleanStack.pop());
                    Boolean booleanRight = Boolean.valueOf(booleanStack.pop());
                    result = Boolean.logicalAnd(booleanLeft, booleanRight);
                } else if (rpnTemp.equals(Operators.OR.getSymbol())) {
                    Boolean booleanLeft = Boolean.valueOf(booleanStack.pop());
                    Boolean booleanRight = Boolean.valueOf(booleanStack.pop());
                    result = Boolean.logicalOr(booleanLeft, booleanRight);
                } else if (rpnTemp.equals(Operators.NOT.getSymbol())) {
                    Boolean booleanRight = Boolean.valueOf(booleanStack.pop());
                    result = !booleanRight;
                }
                booleanStack.push(result.toString());
            }
        });
        return Boolean.valueOf(booleanStack.pop());
    }

    /**
     * 用链表做一个用于计算表达式的栈数据结构
     */
    private class Stack {
        LinkedList<String> stackNodes = new LinkedList<String>();

        public Stack() {
        }

        /**
         * 入栈
         *
         * @param expression
         */
        public void push(String expression) {
            stackNodes.addLast(expression);
        }

        /**
         * 出栈
         *
         * @return
         */
        public String pop() {
            return stackNodes.removeLast();
        }

        /**
         * 获取栈顶元素
         *
         * @return
         */
        public String top() {
            return stackNodes.getLast();
        }

        /**
         * 栈是否为空
         *
         * @return
         */
        public boolean isEmpty() {
            return stackNodes.isEmpty();
        }
    }
}
