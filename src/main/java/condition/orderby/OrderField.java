package condition.orderby;

/**
 * @author ：Administrator
 * @description：排序字段封装
 * @date ：2020/9/26 17:21
 */
public class OrderField {

    private String fieldName;
    private SortKey sortKey;

    public String getFieldName() {
        return fieldName;
    }

    public SortKey getSortKey() {
        return sortKey;
    }

    public OrderField(String fieldName, SortKey sortKey) {
        this.fieldName = fieldName;
        this.sortKey = sortKey;
    }
}
