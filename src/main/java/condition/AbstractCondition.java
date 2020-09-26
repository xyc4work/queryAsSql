package condition;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author ：Administrator
 * @description：查询条件语句的抽象类，处理一些公共逻辑
 * @date ：2020/9/26 11:31
 */
public abstract class AbstractCondition implements BaseCondition {
    /**
     * 各个查询语句待实现的具体过滤业务
     * @param sourceData 原数据
     * @return 过滤后数据
     */
    public abstract List<Object> doFilter(List<Object> sourceData);

    @Override
    public List<Object> filter(List<Object> sourceData) {
        if (Objects.isNull(sourceData)){
            return Collections.EMPTY_LIST;
        }
        if (sourceData.isEmpty()){
            return sourceData;
        }
        return doFilter(sourceData);
    }
}
