package datamodel;
/**
 * @author ：Administrator
 * @description：用于测试验证的数据模型
 * @date ：2020/9/26 11:20
 */
public class TestData {
    private String fieldA;
    private String fieldB;
    private String fieldC;
    private String fieldD;
    private String fieldE;

    public TestData(String fieldA, String fieldB, String fieldC, String fieldD, String fieldE) {
        this.fieldA = fieldA;
        this.fieldB = fieldB;
        this.fieldC = fieldC;
        this.fieldD = fieldD;
        this.fieldE = fieldE;
    }

    @Override
    public String toString() {
        return "TestData{" +
                "fieldA='" + fieldA + '\'' +
                ", fieldB='" + fieldB + '\'' +
                ", fieldC='" + fieldC + '\'' +
                ", fieldD='" + fieldD + '\'' +
                ", fieldE='" + fieldE + '\'' +
                '}';
    }
}
