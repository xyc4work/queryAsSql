package condition.limit;

import condition.AbstractCondition;

import java.util.List;

/**
 * @author ：Administrator
 * @description：limit实现
 * @date ：2020/9/26 16:27
 */
public class Limit extends AbstractCondition {
    private int start;
    private int end;

    /**
     * limit 条件起始返回下标
     * @param start
     * @param end
     * @return
     */
    public Limit limit(int start,int end){
        this.start = start;
        this.end = end+1;
        return this;
    }

    /**
     * 下标为0返回固定个数
     * @param length
     * @return
     */
    public Limit limit(int length){
        this.start = 0;
        this.end = length;
        if (end<start){
            throw new SecurityException("limit 参数非法");
        }
        return this;
    }
    /**
     * 创建实例返回
     *
     * @return
     */
    public static Limit create() {
        return new Limit();
    }

    @Override
    public List<Object> doFilter(List<Object> sourceData) {
        int size = sourceData.size();
        if (start>size-1){
            start = size-1;
        }
        if (end>size){
            end = size;
        }
        return sourceData.subList(start,end);
    }
}
