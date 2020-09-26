package condition.where.express;

import java.util.Objects;

/**
 * @author ：Administrator
 * @description：大于表达式
 * @date ：2020/9/26 12:13
 */
public class GtExpress extends AbstractExpress {
    public GtExpress(String filedName, String value) {
        super(filedName, value);
    }

    @Override
    public boolean doCharge(String fieldValue, String inputValue) {
        return Objects.compare(fieldValue,inputValue,String::compareTo)>0;
    }
}
