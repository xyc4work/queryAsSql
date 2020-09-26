package condition.where.operator;

/**
 * @author ：Administrator
 * @description：操作符枚举
 * @date ：2020/9/26 14:10
 */
public enum Operators {
    AND("&"),
    OR("|"),
    LEFT("("),
    RIGHT(")"),
    NOT("!");
    private final String symbol;

    Operators(String operatorSymbol) {
        this.symbol = operatorSymbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static String getDelim() {
        return "&!|()";
    }

}


