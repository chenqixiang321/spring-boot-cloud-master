package com.opay.invite.backstage.dao.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OpayMasterPupilAwardExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpayMasterPupilAwardExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andOpayIdIsNull() {
            addCriterion("opay_id is null");
            return (Criteria) this;
        }

        public Criteria andOpayIdIsNotNull() {
            addCriterion("opay_id is not null");
            return (Criteria) this;
        }

        public Criteria andOpayIdEqualTo(String value) {
            addCriterion("opay_id =", value, "opayId");
            return (Criteria) this;
        }

        public Criteria andOpayIdNotEqualTo(String value) {
            addCriterion("opay_id <>", value, "opayId");
            return (Criteria) this;
        }

        public Criteria andOpayIdGreaterThan(String value) {
            addCriterion("opay_id >", value, "opayId");
            return (Criteria) this;
        }

        public Criteria andOpayIdGreaterThanOrEqualTo(String value) {
            addCriterion("opay_id >=", value, "opayId");
            return (Criteria) this;
        }

        public Criteria andOpayIdLessThan(String value) {
            addCriterion("opay_id <", value, "opayId");
            return (Criteria) this;
        }

        public Criteria andOpayIdLessThanOrEqualTo(String value) {
            addCriterion("opay_id <=", value, "opayId");
            return (Criteria) this;
        }

        public Criteria andOpayIdLike(String value) {
            addCriterion("opay_id like", value, "opayId");
            return (Criteria) this;
        }

        public Criteria andOpayIdNotLike(String value) {
            addCriterion("opay_id not like", value, "opayId");
            return (Criteria) this;
        }

        public Criteria andOpayIdIn(List<String> values) {
            addCriterion("opay_id in", values, "opayId");
            return (Criteria) this;
        }

        public Criteria andOpayIdNotIn(List<String> values) {
            addCriterion("opay_id not in", values, "opayId");
            return (Criteria) this;
        }

        public Criteria andOpayIdBetween(String value1, String value2) {
            addCriterion("opay_id between", value1, value2, "opayId");
            return (Criteria) this;
        }

        public Criteria andOpayIdNotBetween(String value1, String value2) {
            addCriterion("opay_id not between", value1, value2, "opayId");
            return (Criteria) this;
        }

        public Criteria andPupilIdIsNull() {
            addCriterion("pupil_id is null");
            return (Criteria) this;
        }

        public Criteria andPupilIdIsNotNull() {
            addCriterion("pupil_id is not null");
            return (Criteria) this;
        }

        public Criteria andPupilIdEqualTo(String value) {
            addCriterion("pupil_id =", value, "pupilId");
            return (Criteria) this;
        }

        public Criteria andPupilIdNotEqualTo(String value) {
            addCriterion("pupil_id <>", value, "pupilId");
            return (Criteria) this;
        }

        public Criteria andPupilIdGreaterThan(String value) {
            addCriterion("pupil_id >", value, "pupilId");
            return (Criteria) this;
        }

        public Criteria andPupilIdGreaterThanOrEqualTo(String value) {
            addCriterion("pupil_id >=", value, "pupilId");
            return (Criteria) this;
        }

        public Criteria andPupilIdLessThan(String value) {
            addCriterion("pupil_id <", value, "pupilId");
            return (Criteria) this;
        }

        public Criteria andPupilIdLessThanOrEqualTo(String value) {
            addCriterion("pupil_id <=", value, "pupilId");
            return (Criteria) this;
        }

        public Criteria andPupilIdLike(String value) {
            addCriterion("pupil_id like", value, "pupilId");
            return (Criteria) this;
        }

        public Criteria andPupilIdNotLike(String value) {
            addCriterion("pupil_id not like", value, "pupilId");
            return (Criteria) this;
        }

        public Criteria andPupilIdIn(List<String> values) {
            addCriterion("pupil_id in", values, "pupilId");
            return (Criteria) this;
        }

        public Criteria andPupilIdNotIn(List<String> values) {
            addCriterion("pupil_id not in", values, "pupilId");
            return (Criteria) this;
        }

        public Criteria andPupilIdBetween(String value1, String value2) {
            addCriterion("pupil_id between", value1, value2, "pupilId");
            return (Criteria) this;
        }

        public Criteria andPupilIdNotBetween(String value1, String value2) {
            addCriterion("pupil_id not between", value1, value2, "pupilId");
            return (Criteria) this;
        }

        public Criteria andRewardIsNull() {
            addCriterion("reward is null");
            return (Criteria) this;
        }

        public Criteria andRewardIsNotNull() {
            addCriterion("reward is not null");
            return (Criteria) this;
        }

        public Criteria andRewardEqualTo(BigDecimal value) {
            addCriterion("reward =", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardNotEqualTo(BigDecimal value) {
            addCriterion("reward <>", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardGreaterThan(BigDecimal value) {
            addCriterion("reward >", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("reward >=", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardLessThan(BigDecimal value) {
            addCriterion("reward <", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardLessThanOrEqualTo(BigDecimal value) {
            addCriterion("reward <=", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardIn(List<BigDecimal> values) {
            addCriterion("reward in", values, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardNotIn(List<BigDecimal> values) {
            addCriterion("reward not in", values, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("reward between", value1, value2, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("reward not between", value1, value2, "reward");
            return (Criteria) this;
        }

        public Criteria andCreateAtIsNull() {
            addCriterion("create_at is null");
            return (Criteria) this;
        }

        public Criteria andCreateAtIsNotNull() {
            addCriterion("create_at is not null");
            return (Criteria) this;
        }

        public Criteria andCreateAtEqualTo(LocalDateTime value) {
            addCriterion("create_at =", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtNotEqualTo(LocalDateTime value) {
            addCriterion("create_at <>", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtGreaterThan(LocalDateTime value) {
            addCriterion("create_at >", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_at >=", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtLessThan(LocalDateTime value) {
            addCriterion("create_at <", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_at <=", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtIn(List<LocalDateTime> values) {
            addCriterion("create_at in", values, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtNotIn(List<LocalDateTime> values) {
            addCriterion("create_at not in", values, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_at between", value1, value2, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_at not between", value1, value2, "createAt");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andActionIsNull() {
            addCriterion("action is null");
            return (Criteria) this;
        }

        public Criteria andActionIsNotNull() {
            addCriterion("action is not null");
            return (Criteria) this;
        }

        public Criteria andActionEqualTo(Byte value) {
            addCriterion("action =", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionNotEqualTo(Byte value) {
            addCriterion("action <>", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionGreaterThan(Byte value) {
            addCriterion("action >", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionGreaterThanOrEqualTo(Byte value) {
            addCriterion("action >=", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionLessThan(Byte value) {
            addCriterion("action <", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionLessThanOrEqualTo(Byte value) {
            addCriterion("action <=", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionIn(List<Byte> values) {
            addCriterion("action in", values, "action");
            return (Criteria) this;
        }

        public Criteria andActionNotIn(List<Byte> values) {
            addCriterion("action not in", values, "action");
            return (Criteria) this;
        }

        public Criteria andActionBetween(Byte value1, Byte value2) {
            addCriterion("action between", value1, value2, "action");
            return (Criteria) this;
        }

        public Criteria andActionNotBetween(Byte value1, Byte value2) {
            addCriterion("action not between", value1, value2, "action");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andMarkTypeIsNull() {
            addCriterion("mark_type is null");
            return (Criteria) this;
        }

        public Criteria andMarkTypeIsNotNull() {
            addCriterion("mark_type is not null");
            return (Criteria) this;
        }

        public Criteria andMarkTypeEqualTo(Byte value) {
            addCriterion("mark_type =", value, "markType");
            return (Criteria) this;
        }

        public Criteria andMarkTypeNotEqualTo(Byte value) {
            addCriterion("mark_type <>", value, "markType");
            return (Criteria) this;
        }

        public Criteria andMarkTypeGreaterThan(Byte value) {
            addCriterion("mark_type >", value, "markType");
            return (Criteria) this;
        }

        public Criteria andMarkTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("mark_type >=", value, "markType");
            return (Criteria) this;
        }

        public Criteria andMarkTypeLessThan(Byte value) {
            addCriterion("mark_type <", value, "markType");
            return (Criteria) this;
        }

        public Criteria andMarkTypeLessThanOrEqualTo(Byte value) {
            addCriterion("mark_type <=", value, "markType");
            return (Criteria) this;
        }

        public Criteria andMarkTypeIn(List<Byte> values) {
            addCriterion("mark_type in", values, "markType");
            return (Criteria) this;
        }

        public Criteria andMarkTypeNotIn(List<Byte> values) {
            addCriterion("mark_type not in", values, "markType");
            return (Criteria) this;
        }

        public Criteria andMarkTypeBetween(Byte value1, Byte value2) {
            addCriterion("mark_type between", value1, value2, "markType");
            return (Criteria) this;
        }

        public Criteria andMarkTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("mark_type not between", value1, value2, "markType");
            return (Criteria) this;
        }

        public Criteria andStepJsonIsNull() {
            addCriterion("step_json is null");
            return (Criteria) this;
        }

        public Criteria andStepJsonIsNotNull() {
            addCriterion("step_json is not null");
            return (Criteria) this;
        }

        public Criteria andStepJsonEqualTo(String value) {
            addCriterion("step_json =", value, "stepJson");
            return (Criteria) this;
        }

        public Criteria andStepJsonNotEqualTo(String value) {
            addCriterion("step_json <>", value, "stepJson");
            return (Criteria) this;
        }

        public Criteria andStepJsonGreaterThan(String value) {
            addCriterion("step_json >", value, "stepJson");
            return (Criteria) this;
        }

        public Criteria andStepJsonGreaterThanOrEqualTo(String value) {
            addCriterion("step_json >=", value, "stepJson");
            return (Criteria) this;
        }

        public Criteria andStepJsonLessThan(String value) {
            addCriterion("step_json <", value, "stepJson");
            return (Criteria) this;
        }

        public Criteria andStepJsonLessThanOrEqualTo(String value) {
            addCriterion("step_json <=", value, "stepJson");
            return (Criteria) this;
        }

        public Criteria andStepJsonLike(String value) {
            addCriterion("step_json like", value, "stepJson");
            return (Criteria) this;
        }

        public Criteria andStepJsonNotLike(String value) {
            addCriterion("step_json not like", value, "stepJson");
            return (Criteria) this;
        }

        public Criteria andStepJsonIn(List<String> values) {
            addCriterion("step_json in", values, "stepJson");
            return (Criteria) this;
        }

        public Criteria andStepJsonNotIn(List<String> values) {
            addCriterion("step_json not in", values, "stepJson");
            return (Criteria) this;
        }

        public Criteria andStepJsonBetween(String value1, String value2) {
            addCriterion("step_json between", value1, value2, "stepJson");
            return (Criteria) this;
        }

        public Criteria andStepJsonNotBetween(String value1, String value2) {
            addCriterion("step_json not between", value1, value2, "stepJson");
            return (Criteria) this;
        }

        public Criteria andMasterRewardIsNull() {
            addCriterion("master_reward is null");
            return (Criteria) this;
        }

        public Criteria andMasterRewardIsNotNull() {
            addCriterion("master_reward is not null");
            return (Criteria) this;
        }

        public Criteria andMasterRewardEqualTo(BigDecimal value) {
            addCriterion("master_reward =", value, "masterReward");
            return (Criteria) this;
        }

        public Criteria andMasterRewardNotEqualTo(BigDecimal value) {
            addCriterion("master_reward <>", value, "masterReward");
            return (Criteria) this;
        }

        public Criteria andMasterRewardGreaterThan(BigDecimal value) {
            addCriterion("master_reward >", value, "masterReward");
            return (Criteria) this;
        }

        public Criteria andMasterRewardGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("master_reward >=", value, "masterReward");
            return (Criteria) this;
        }

        public Criteria andMasterRewardLessThan(BigDecimal value) {
            addCriterion("master_reward <", value, "masterReward");
            return (Criteria) this;
        }

        public Criteria andMasterRewardLessThanOrEqualTo(BigDecimal value) {
            addCriterion("master_reward <=", value, "masterReward");
            return (Criteria) this;
        }

        public Criteria andMasterRewardIn(List<BigDecimal> values) {
            addCriterion("master_reward in", values, "masterReward");
            return (Criteria) this;
        }

        public Criteria andMasterRewardNotIn(List<BigDecimal> values) {
            addCriterion("master_reward not in", values, "masterReward");
            return (Criteria) this;
        }

        public Criteria andMasterRewardBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("master_reward between", value1, value2, "masterReward");
            return (Criteria) this;
        }

        public Criteria andMasterRewardNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("master_reward not between", value1, value2, "masterReward");
            return (Criteria) this;
        }

        public Criteria andMasterTypeIsNull() {
            addCriterion("master_type is null");
            return (Criteria) this;
        }

        public Criteria andMasterTypeIsNotNull() {
            addCriterion("master_type is not null");
            return (Criteria) this;
        }

        public Criteria andMasterTypeEqualTo(Byte value) {
            addCriterion("master_type =", value, "masterType");
            return (Criteria) this;
        }

        public Criteria andMasterTypeNotEqualTo(Byte value) {
            addCriterion("master_type <>", value, "masterType");
            return (Criteria) this;
        }

        public Criteria andMasterTypeGreaterThan(Byte value) {
            addCriterion("master_type >", value, "masterType");
            return (Criteria) this;
        }

        public Criteria andMasterTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("master_type >=", value, "masterType");
            return (Criteria) this;
        }

        public Criteria andMasterTypeLessThan(Byte value) {
            addCriterion("master_type <", value, "masterType");
            return (Criteria) this;
        }

        public Criteria andMasterTypeLessThanOrEqualTo(Byte value) {
            addCriterion("master_type <=", value, "masterType");
            return (Criteria) this;
        }

        public Criteria andMasterTypeIn(List<Byte> values) {
            addCriterion("master_type in", values, "masterType");
            return (Criteria) this;
        }

        public Criteria andMasterTypeNotIn(List<Byte> values) {
            addCriterion("master_type not in", values, "masterType");
            return (Criteria) this;
        }

        public Criteria andMasterTypeBetween(Byte value1, Byte value2) {
            addCriterion("master_type between", value1, value2, "masterType");
            return (Criteria) this;
        }

        public Criteria andMasterTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("master_type not between", value1, value2, "masterType");
            return (Criteria) this;
        }

        public Criteria andMonthIsNull() {
            addCriterion("month is null");
            return (Criteria) this;
        }

        public Criteria andMonthIsNotNull() {
            addCriterion("month is not null");
            return (Criteria) this;
        }

        public Criteria andMonthEqualTo(Integer value) {
            addCriterion("month =", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotEqualTo(Integer value) {
            addCriterion("month <>", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthGreaterThan(Integer value) {
            addCriterion("month >", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthGreaterThanOrEqualTo(Integer value) {
            addCriterion("month >=", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthLessThan(Integer value) {
            addCriterion("month <", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthLessThanOrEqualTo(Integer value) {
            addCriterion("month <=", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthIn(List<Integer> values) {
            addCriterion("month in", values, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotIn(List<Integer> values) {
            addCriterion("month not in", values, "month");
            return (Criteria) this;
        }

        public Criteria andMonthBetween(Integer value1, Integer value2) {
            addCriterion("month between", value1, value2, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotBetween(Integer value1, Integer value2) {
            addCriterion("month not between", value1, value2, "month");
            return (Criteria) this;
        }

        public Criteria andDayIsNull() {
            addCriterion("day is null");
            return (Criteria) this;
        }

        public Criteria andDayIsNotNull() {
            addCriterion("day is not null");
            return (Criteria) this;
        }

        public Criteria andDayEqualTo(Integer value) {
            addCriterion("day =", value, "day");
            return (Criteria) this;
        }

        public Criteria andDayNotEqualTo(Integer value) {
            addCriterion("day <>", value, "day");
            return (Criteria) this;
        }

        public Criteria andDayGreaterThan(Integer value) {
            addCriterion("day >", value, "day");
            return (Criteria) this;
        }

        public Criteria andDayGreaterThanOrEqualTo(Integer value) {
            addCriterion("day >=", value, "day");
            return (Criteria) this;
        }

        public Criteria andDayLessThan(Integer value) {
            addCriterion("day <", value, "day");
            return (Criteria) this;
        }

        public Criteria andDayLessThanOrEqualTo(Integer value) {
            addCriterion("day <=", value, "day");
            return (Criteria) this;
        }

        public Criteria andDayIn(List<Integer> values) {
            addCriterion("day in", values, "day");
            return (Criteria) this;
        }

        public Criteria andDayNotIn(List<Integer> values) {
            addCriterion("day not in", values, "day");
            return (Criteria) this;
        }

        public Criteria andDayBetween(Integer value1, Integer value2) {
            addCriterion("day between", value1, value2, "day");
            return (Criteria) this;
        }

        public Criteria andDayNotBetween(Integer value1, Integer value2) {
            addCriterion("day not between", value1, value2, "day");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("order_id like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("order_id not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}