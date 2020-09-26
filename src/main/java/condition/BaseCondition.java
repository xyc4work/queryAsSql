package condition;

import java.util.List;

/**
 * @author ：Administrator
 * @description：查询条件语句基本定义
 * @date ：2020/9/26 11:26
 */
public interface BaseCondition {
    /**
     * 查询语句对数据的过滤
     * @param sourceData 待过滤原数据
     * @return 过滤后数据
     */
    List<Object> filter(List<Object> sourceData);
}
