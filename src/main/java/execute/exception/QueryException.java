package execute.exception;

import javafx.beans.binding.StringExpression;

/**
 * @author ：Administrator
 * @description：统一查询异常
 * @date ：2020/9/26 12:31
 */
public class QueryException extends RuntimeException {
    public QueryException(String message) {
        super(message);
    }
}
