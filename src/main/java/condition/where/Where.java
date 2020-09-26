package condition.where;

import condition.AbstractCondition;
import condition.where.calculate.BooleanExpressCalculate;
import condition.where.express.EqExpress;
import condition.where.express.Express;
import condition.where.express.GtExpress;
import condition.where.express.LtExpress;
import condition.where.operator.Operators;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：Administrator
 * @description：where过滤功能实现
 * @date ：2020/9/26 11:39
 */
public class Where extends AbstractCondition {

    //记录where语句中的各个输入逻辑表达式 A=a ,A>a,A<a
    private List<Express> expressList = new ArrayList<Express>();
    //where 语句构造的真值表达式
    private StringBuilder expressStrBuilder = new StringBuilder();

    /**
     * 用于括号配对（用长度），优先级解析（用表达式序号）
     */
    //记录左括号下一个表达式序号
    private List<Integer> bracketLeft = new ArrayList<>();
    //记录右括号上一个表达式序号
    private List<Integer> bracketRight= new ArrayList<>();

    /**
     * 字段相等条件表达式
     * @param fieldName
     * @param value
     * @return
     */
    public Where eq(String fieldName,String value){
        addExpressTag();
        expressList.add(new EqExpress(fieldName,value));
        return this;
    }
    /**
     * 字段大于条件表达式
     * @param fieldName
     * @param value
     * @return
     */
    public Where gt(String fieldName,String value){
        addExpressTag();
        expressList.add(new GtExpress(fieldName,value));
        return this;
    }

    /**
     * 字段小于条件表达式
     * @param fieldName
     * @param value
     * @return
     */
    public Where lt(String fieldName,String value){
        addExpressTag();
        expressList.add(new LtExpress(fieldName,value));
        return this;
    }

    /**
     * 条件表达式关系或
     * @return
     */
    public Where or(){
        addOperatorTag(Operators.OR);
        return this;
    }

    /**
     * 条件表达式关系与
     * @return
     */
    public Where and(){
        addOperatorTag(Operators.AND);
        return this;
    }
    /**
     * 关系非
     * @return
     */
    public Where not(){
        addOperatorTag(Operators.NOT);
        return this;
    }

    /**
     * 加左括号
     * @return
     */
    public Where left(){
        addOperatorTag(Operators.LEFT);
        return this;
    }

    /**
     * 加右括号
     * @return
     */
    public Where right(){
        addOperatorTag(Operators.RIGHT);
        return this;
    }

    /**
     * 加入操作符
     */
    private void addOperatorTag(Operators operators) {
        expressStrBuilder.append(operators.getSymbol());
    }
    /**
     * 加入计算表达式占位
     */
    private void addExpressTag() {
        expressStrBuilder.append(String.format("EXPRESS[%s]",expressList.size()));
    }
    /**
     * 创建实例返回
     * @return
     */
    public static Where create() {
        return new Where();
    }

    @Override
    public List<Object> doFilter(List<Object> sourceData) {
        List<Object> result = sourceData.stream().filter(oneData -> doCharge(oneData)).collect(Collectors.toList());
        return result;
    }

    /**
     * 计算每条记录过滤真值
     * @return
     * @param oneData 单条记录
     */
    private boolean doCharge(Object oneData) {
        String calculateString =  parseExpress(oneData);
        return calculate(calculateString);
    }

    /**
     * 根据生成表达式计算结果
     * @param calculateString
     * @return
     */
    private boolean calculate(String calculateString) {
        BooleanExpressCalculate expressCalculate = new BooleanExpressCalculate(calculateString);
        return  expressCalculate.calculate();
    }

    /**
     * 根据表达式单元的逻辑结果解析出where条件的计算表达式
     * @param oneData
     * @return
     */
    private String parseExpress(Object oneData) {
        String calculateTemplate = expressStrBuilder.toString();
        String calculateString = null;
        for (int i = 0; i < expressList.size() ; i++) {
            calculateString = calculateTemplate.replace(String.format("EXPRESS[%s]",i),expressList.get(i).charge(oneData).toString());
            calculateTemplate = calculateString;
        }
        return calculateString;
    }

}
