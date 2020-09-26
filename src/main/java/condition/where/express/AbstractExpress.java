package condition.where.express;

import execute.exception.QueryException;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author ：Administrator
 * @description：表达式抽象类，公共字段方法提取
 * @date ：2020/9/26 12:14
 */
public abstract class AbstractExpress implements Express {
    //表达式中字段名
    protected String filedName;
    //表达式中字段值
    protected String value;

    public AbstractExpress(String filedName, String value) {
        this.filedName = filedName;
        this.value = value;
    }

    @Override
    public Boolean charge(Object record) throws QueryException {
        Field field = null;
        String fieldValue = null;
        try {
            field = record.getClass().getDeclaredField(filedName);
            field.setAccessible(true);
            fieldValue = (String)field.get(record);
            if (Objects.isNull(fieldValue)){
                return false;
            }
        } catch (NoSuchFieldException e) {
            throw new QueryException(String.format("记录字段%s 不存在",filedName).toString());
        } catch (IllegalAccessException e) {
            throw new QueryException(String.format("记录字段%s 无访问权限",filedName).toString());
        }
        return doCharge(fieldValue,value);
    }

    /**
     * 真值计算逻辑
     * @param fieldValue 记录中字段对应值
     * @param inputValue 传入比较的值
     * @return 表达式真值结果
     */
    public abstract boolean doCharge(String fieldValue,String inputValue);
}
