package condition.orderby;

import condition.AbstractCondition;
import execute.exception.QueryException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @author ：Administrator
 * @description：排序实现
 * @date ：2020/9/26 16:26
 */
public class OrderBy extends AbstractCondition {

    List<OrderField> fields = new ArrayList();

    /**
     * 添加升序字段
     * @param fieldName
     * @return
     */
    public OrderBy aes(String fieldName){
        fields.add(new OrderField(fieldName,SortKey.AES));
        return this;
    }

    /**
     * 添加降序字段
     * @param fieldName
     * @return
     */
    public OrderBy desc(String fieldName){
        fields.add(new OrderField(fieldName,SortKey.DESC));
        return this;
    }



    /**
     * 创建实例返回
     *
     * @return
     */
    public static OrderBy create() {
        return new OrderBy();
    }

    @Override
    public List<Object> doFilter(List<Object> sourceData) {
        sourceData.sort(new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                return getCompareResultByFields(o1,o2);
            }
        });
        return sourceData;
    }

    /**
     * 按字段排序的比较逻辑
     * @param o1
     * @param o2
     * @return 等于返回0 大于返回正，小于返回负数，降序相反
     */
    private int getCompareResultByFields(Object o1,Object o2){
        //根据排序字段生成compare实现
        if (Objects.isNull(o1)){
            return -1;
        }
        if (Objects.isNull(o2)){
            return 1;
        }
        for (int i = 0; i < fields.size(); i++) {
            OrderField order = fields.get(i);
            String fieldName = order.getFieldName();
            SortKey sortKey = order.getSortKey();
            Field field = null;
            try {
                field = o1.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                String filedValue1 = (String) field.get(o1);
                String filedValue2 = (String) field.get(o2);
                if (Objects.equals(SortKey.AES,sortKey)){
                    //升序
                    int compareField = filedValue1.compareTo(filedValue2);
                    if (0==compareField){
                        //相等比较下个字段
                        continue;
                    }else {
                        return compareField;
                    }
                }else {
                    //降序
                    int compareField = filedValue1.compareTo(filedValue2);
                    if (0==compareField){
                        //相等比较下个字段
                        continue;
                    }else {
                        //取相反数
                        return 0-compareField;
                    }
                }
            } catch (NoSuchFieldException e) {
                throw new QueryException("排序字段错误");
            } catch (IllegalAccessException e) {
            }
        }
        //都一样返回相等
        return 0;
    }
}
