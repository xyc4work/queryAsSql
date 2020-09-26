package condition.groupby;

import condition.AbstractCondition;

import java.util.Arrays;
import java.util.List;

/**
 * @author ：Administrator
 * @description：group过滤功能实现
 * @date ：2020/9/26 16:25
 */
public class GroupBy extends AbstractCondition {

    //分组字段
    private List<String> groupList;

    /**
     * 添加分组字段
     * @param fields
     * @return
     */
    public GroupBy field(String ... fields){
        groupList = Arrays.asList(fields);
        return this;
    }

    /**
     * 创建实例返回
     * @return
     */
    public static GroupBy create() {
        return new GroupBy();
    }

    @Override
    public List<Object> doFilter(List<Object> sourceData) {
        //分组为聚合结果，该场景暂未实现
        // TODO: 2020/9/26  
        return sourceData;
    }
}
