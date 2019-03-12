package com.apollo.demo.dto;

import java.io.Serializable;

/**
 * 系统条件类
 *
 * @author g5niusx
 */
public class SystemCondition implements Serializable {

    private String configCode;
    private String channel;
    private String branchCode;

    public String getConfigCode() {
        return configCode;
    }

    public String getChannel() {
        return channel;
    }

    public String getBranchCode() {
        return branchCode;
    }

    private SystemCondition(String configCode, String channel, String branchCode) {
        this.configCode = configCode;
        this.channel = channel;
        this.branchCode = branchCode;
    }

    public static class SystemConditionBuilder {
        private String configCode;
        private String channel;
        private String branchCode;


        public static SystemConditionBuilder builder() {
            return new SystemConditionBuilder();
        }


        public SystemConditionBuilder configCode(String configCode) {
            this.configCode = configCode;
            return this;
        }

        public SystemConditionBuilder channel(String channel) {
            this.channel = channel;
            return this;
        }

        public SystemConditionBuilder branchCode(String branchCode) {
            this.branchCode = branchCode;
            return this;
        }

        public SystemCondition build() {
            return new SystemCondition(configCode, channel, branchCode);
        }
    }
}
