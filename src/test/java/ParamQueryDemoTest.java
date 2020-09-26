import condition.groupby.GroupBy;
import condition.limit.Limit;
import condition.orderby.OrderBy;
import condition.where.Where;
import datamodel.TestData;
import execute.SqlFormatQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

/**
 * 主功能单元测试
 */
@RunWith(Parameterized.class)
public class ParamQueryDemoTest {

    private Where where;

    private GroupBy groupBy;

    private OrderBy orderBy;

    private Limit limit;

    public ParamQueryDemoTest(Where where, GroupBy groupBy, OrderBy orderBy, Limit limit) {
        this.where = where;
        this.groupBy = groupBy;
        this.orderBy = orderBy;
        this.limit = limit;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getParams() {
        /**
         * 构建测试where （fieldA = 1 or fieldB = 2）and（fieldC>0 or fieldD<10 and fieldE =1）’
         */
        Where where = SqlFormatQuery.ConditionBuilder.buildWhere()
                .left().eq("fieldA", "1").or().eq("fieldB", "2").right()
                .and().left().gt("fieldC", "0").or().lt("fieldD", "10").and().eq("fieldE", "1").right();
        /**
         * 构建测试group(返回List场景下不匹配聚合结果，未做具体实现)
         */
        GroupBy groupBy = SqlFormatQuery.ConditionBuilder.buildGroupBy();
        /**
         * 构建测试order fieldA aes，fieldB desc,fieldC aes;
         */
        OrderBy orderBy = SqlFormatQuery.ConditionBuilder.buildOrderBy().aes("fieldA").desc("fieldB").aes("fieldC");

        /**
         * 构建测试limit limit (2,5);
         */
        Limit limit = SqlFormatQuery.ConditionBuilder.buildLimit().limit(5);

        /**
         * 条件分情况测试
         */
        return Arrays.asList(new Object[][]{
                {where, null, null, null},
                {null, groupBy, null, null},
                {null, null, orderBy, null},
                {null, null, null, limit},
                {where, groupBy, orderBy, limit}
        });
    }

    @Test
    public void test() {
        /**
         * 创建测试参数
         */
        List<Object> input = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            String fieldA = Integer.valueOf(random.nextInt(100)).toString();
            String fieldB = Integer.valueOf(random.nextInt(100)).toString();
            String fieldC = Integer.valueOf(random.nextInt(100)).toString();
            String fieldD = Integer.valueOf(random.nextInt(100)).toString();
            String fieldE = Integer.valueOf(random.nextInt(100)).toString();
            input.add(new TestData(fieldA, fieldB, fieldC, fieldD, fieldE));
        }
        System.out.println(String.format("输入数据：%s", input));
        /**
         * 生成测试结果
         */
        List<Object> output = SqlFormatQuery.query(input, where, groupBy, orderBy, limit);
        System.out.println(String.format("输出数据：%s", output));
    }
}
