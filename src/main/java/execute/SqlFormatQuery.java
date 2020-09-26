package execute;

import condition.groupby.GroupBy;
import condition.limit.Limit;
import condition.orderby.OrderBy;
import condition.where.Where;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author ：Administrator
 * @description：查询功能外部执行
 * @date ：2020/9/26 11:20
 */
public class SqlFormatQuery {
    /**
     *  根据传入的条件返回过滤后的结果集合,数据各个字段均按String类型处理
     * @param data 元数据 Object字段类型统一为String
     * @param where 过滤条件
     * @param groupBy 分组条件
     * @param orderBy 排序条件
     * @param limit 结果个数条件
     * @return 过滤后结果
     */
    public static List<Object> query(List<Object> data, Where where, GroupBy groupBy, OrderBy orderBy, Limit limit){
        //按语句执行顺序过滤返回数据
        if (Objects.isNull(data)){
            return Collections.EMPTY_LIST;
        }
        List<Object> afterQuery = data;
        if (Objects.nonNull(where)){
            afterQuery =  where.filter(afterQuery);
        }
        if (Objects.nonNull(groupBy)){
            afterQuery = groupBy.filter(afterQuery);
        }
        if (Objects.nonNull(orderBy)){
            afterQuery = orderBy.filter(afterQuery);
        }
        if (Objects.nonNull(limit)){
            afterQuery = limit.filter(afterQuery);
        }
        return afterQuery;
    }

    /**
     * 各个条件builder
     */
    public static class ConditionBuilder{
        /**
         * build where 条件
         * @return
         */
        public static Where buildWhere(){
            return Where.create();
        }
        /**
         * build group 条件
         * @return
         */
        public static GroupBy buildGroupBy(){
            return GroupBy.create();
        }
        /**
         * build order 条件
         * @return
         */
        public static OrderBy buildOrderBy(){
            return OrderBy.create();
        }
        /**
         * build limit 条件
         * @return
         */
        public static Limit buildLimit(){
            return Limit.create();
        }
    }
}
