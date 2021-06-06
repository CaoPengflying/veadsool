package com.cpf.veadsool.constants;

/**
 * date 2020/3/22
 *
 * @author caopengflying
 */
public class RulesEnum {
    public enum RuleTypeEnum {
        attendance(1, "考勤"),
        rule(2, "奖惩");
        private Integer code;
        private String value;
        RuleTypeEnum(Integer code, String value){
            this.code = code;
            this.value = value;
        }
        public Integer getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 获取规则类型名称
     * @param code
     * @return
     */
    public static String getRuleTypeName(Integer code){
        for (RuleTypeEnum value : RuleTypeEnum.values()) {
            if (value.getCode().equals(code)){
                return value.getValue();
            }
        }
        return null;
    }
}
