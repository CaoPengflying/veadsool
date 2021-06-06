package com.cpf.veadsool.constants;

/**
 * date 2020/3/22
 *
 * @author caopengflying
 */
public class StudentCreditsFlowEnum {
    public enum StatusEnum {
        NO(0, "未统计"),
        YES(1, "已统计");
        private Integer code;
        private String value;
        StatusEnum(Integer code, String value){
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
     * 获取状态名称
     * @param code
     * @return
     */
    public static String getStatusName(Integer code){
        for (StatusEnum value : StatusEnum.values()) {
            if (value.getCode().equals(code)){
                return value.getValue();
            }
        }
        return null;
    }
}
