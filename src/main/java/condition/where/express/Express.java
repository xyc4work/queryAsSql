package condition.where.express;

import execute.exception.QueryException;

/**
 * @author ：Administrator
 * @description：where中单个逻辑表达式
 * @date ：2020/9/26 12:09
 */
public interface Express {
    /**
     * 逻辑表达式的真值结果
     * @param record 单条数据记录
     * @return 记录是否满足逻辑表达式
     */
    Boolean charge(Object record) throws QueryException;
}
