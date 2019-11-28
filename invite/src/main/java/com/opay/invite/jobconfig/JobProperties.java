package com.opay.invite.jobconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * job配置类
 */
@ConfigurationProperties(prefix = "opay.settings")
public class JobProperties {
    private Map<String, JobItem> jobs;

    public Map<String, JobItem> getJobs() {
        return jobs;
    }

    public void setJobs(Map<String, JobItem> jobs) {
        this.jobs = jobs;
    }

    public static class JobItem {
        private String expression;
        private String jobClass;

        public String getExpression() {
            return expression;
        }

        public void setExpression(String expression) {
            this.expression = expression;
        }

        public String getJobClass() {
            return jobClass;
        }

        public void setJobClass(String jobClass) {
            this.jobClass = jobClass;
        }
    }
}
