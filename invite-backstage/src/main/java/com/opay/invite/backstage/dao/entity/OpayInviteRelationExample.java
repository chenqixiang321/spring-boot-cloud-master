package com.opay.invite.backstage.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpayInviteRelationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpayInviteRelationExample() {
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

        public Criteria andMasterIdIsNull() {
            addCriterion("master_id is null");
            return (Criteria) this;
        }

        public Criteria andMasterIdIsNotNull() {
            addCriterion("master_id is not null");
            return (Criteria) this;
        }

        public Criteria andMasterIdEqualTo(String value) {
            addCriterion("master_id =", value, "masterId");
            return (Criteria) this;
        }

        public Criteria andMasterIdNotEqualTo(String value) {
            addCriterion("master_id <>", value, "masterId");
            return (Criteria) this;
        }

        public Criteria andMasterIdGreaterThan(String value) {
            addCriterion("master_id >", value, "masterId");
            return (Criteria) this;
        }

        public Criteria andMasterIdGreaterThanOrEqualTo(String value) {
            addCriterion("master_id >=", value, "masterId");
            return (Criteria) this;
        }

        public Criteria andMasterIdLessThan(String value) {
            addCriterion("master_id <", value, "masterId");
            return (Criteria) this;
        }

        public Criteria andMasterIdLessThanOrEqualTo(String value) {
            addCriterion("master_id <=", value, "masterId");
            return (Criteria) this;
        }

        public Criteria andMasterIdLike(String value) {
            addCriterion("master_id like", value, "masterId");
            return (Criteria) this;
        }

        public Criteria andMasterIdNotLike(String value) {
            addCriterion("master_id not like", value, "masterId");
            return (Criteria) this;
        }

        public Criteria andMasterIdIn(List<String> values) {
            addCriterion("master_id in", values, "masterId");
            return (Criteria) this;
        }

        public Criteria andMasterIdNotIn(List<String> values) {
            addCriterion("master_id not in", values, "masterId");
            return (Criteria) this;
        }

        public Criteria andMasterIdBetween(String value1, String value2) {
            addCriterion("master_id between", value1, value2, "masterId");
            return (Criteria) this;
        }

        public Criteria andMasterIdNotBetween(String value1, String value2) {
            addCriterion("master_id not between", value1, value2, "masterId");
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

        public Criteria andPupilPhoneIsNull() {
            addCriterion("pupil_phone is null");
            return (Criteria) this;
        }

        public Criteria andPupilPhoneIsNotNull() {
            addCriterion("pupil_phone is not null");
            return (Criteria) this;
        }

        public Criteria andPupilPhoneEqualTo(String value) {
            addCriterion("pupil_phone =", value, "pupilPhone");
            return (Criteria) this;
        }

        public Criteria andPupilPhoneNotEqualTo(String value) {
            addCriterion("pupil_phone <>", value, "pupilPhone");
            return (Criteria) this;
        }

        public Criteria andPupilPhoneGreaterThan(String value) {
            addCriterion("pupil_phone >", value, "pupilPhone");
            return (Criteria) this;
        }

        public Criteria andPupilPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("pupil_phone >=", value, "pupilPhone");
            return (Criteria) this;
        }

        public Criteria andPupilPhoneLessThan(String value) {
            addCriterion("pupil_phone <", value, "pupilPhone");
            return (Criteria) this;
        }

        public Criteria andPupilPhoneLessThanOrEqualTo(String value) {
            addCriterion("pupil_phone <=", value, "pupilPhone");
            return (Criteria) this;
        }

        public Criteria andPupilPhoneLike(String value) {
            addCriterion("pupil_phone like", value, "pupilPhone");
            return (Criteria) this;
        }

        public Criteria andPupilPhoneNotLike(String value) {
            addCriterion("pupil_phone not like", value, "pupilPhone");
            return (Criteria) this;
        }

        public Criteria andPupilPhoneIn(List<String> values) {
            addCriterion("pupil_phone in", values, "pupilPhone");
            return (Criteria) this;
        }

        public Criteria andPupilPhoneNotIn(List<String> values) {
            addCriterion("pupil_phone not in", values, "pupilPhone");
            return (Criteria) this;
        }

        public Criteria andPupilPhoneBetween(String value1, String value2) {
            addCriterion("pupil_phone between", value1, value2, "pupilPhone");
            return (Criteria) this;
        }

        public Criteria andPupilPhoneNotBetween(String value1, String value2) {
            addCriterion("pupil_phone not between", value1, value2, "pupilPhone");
            return (Criteria) this;
        }

        public Criteria andMasterPhoneIsNull() {
            addCriterion("master_phone is null");
            return (Criteria) this;
        }

        public Criteria andMasterPhoneIsNotNull() {
            addCriterion("master_phone is not null");
            return (Criteria) this;
        }

        public Criteria andMasterPhoneEqualTo(String value) {
            addCriterion("master_phone =", value, "masterPhone");
            return (Criteria) this;
        }

        public Criteria andMasterPhoneNotEqualTo(String value) {
            addCriterion("master_phone <>", value, "masterPhone");
            return (Criteria) this;
        }

        public Criteria andMasterPhoneGreaterThan(String value) {
            addCriterion("master_phone >", value, "masterPhone");
            return (Criteria) this;
        }

        public Criteria andMasterPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("master_phone >=", value, "masterPhone");
            return (Criteria) this;
        }

        public Criteria andMasterPhoneLessThan(String value) {
            addCriterion("master_phone <", value, "masterPhone");
            return (Criteria) this;
        }

        public Criteria andMasterPhoneLessThanOrEqualTo(String value) {
            addCriterion("master_phone <=", value, "masterPhone");
            return (Criteria) this;
        }

        public Criteria andMasterPhoneLike(String value) {
            addCriterion("master_phone like", value, "masterPhone");
            return (Criteria) this;
        }

        public Criteria andMasterPhoneNotLike(String value) {
            addCriterion("master_phone not like", value, "masterPhone");
            return (Criteria) this;
        }

        public Criteria andMasterPhoneIn(List<String> values) {
            addCriterion("master_phone in", values, "masterPhone");
            return (Criteria) this;
        }

        public Criteria andMasterPhoneNotIn(List<String> values) {
            addCriterion("master_phone not in", values, "masterPhone");
            return (Criteria) this;
        }

        public Criteria andMasterPhoneBetween(String value1, String value2) {
            addCriterion("master_phone between", value1, value2, "masterPhone");
            return (Criteria) this;
        }

        public Criteria andMasterPhoneNotBetween(String value1, String value2) {
            addCriterion("master_phone not between", value1, value2, "masterPhone");
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

        public Criteria andCreateAtEqualTo(Date value) {
            addCriterion("create_at =", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtNotEqualTo(Date value) {
            addCriterion("create_at <>", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtGreaterThan(Date value) {
            addCriterion("create_at >", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtGreaterThanOrEqualTo(Date value) {
            addCriterion("create_at >=", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtLessThan(Date value) {
            addCriterion("create_at <", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtLessThanOrEqualTo(Date value) {
            addCriterion("create_at <=", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtIn(List<Date> values) {
            addCriterion("create_at in", values, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtNotIn(List<Date> values) {
            addCriterion("create_at not in", values, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtBetween(Date value1, Date value2) {
            addCriterion("create_at between", value1, value2, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtNotBetween(Date value1, Date value2) {
            addCriterion("create_at not between", value1, value2, "createAt");
            return (Criteria) this;
        }

        public Criteria andMasterParentIdIsNull() {
            addCriterion("master_parent_id is null");
            return (Criteria) this;
        }

        public Criteria andMasterParentIdIsNotNull() {
            addCriterion("master_parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andMasterParentIdEqualTo(String value) {
            addCriterion("master_parent_id =", value, "masterParentId");
            return (Criteria) this;
        }

        public Criteria andMasterParentIdNotEqualTo(String value) {
            addCriterion("master_parent_id <>", value, "masterParentId");
            return (Criteria) this;
        }

        public Criteria andMasterParentIdGreaterThan(String value) {
            addCriterion("master_parent_id >", value, "masterParentId");
            return (Criteria) this;
        }

        public Criteria andMasterParentIdGreaterThanOrEqualTo(String value) {
            addCriterion("master_parent_id >=", value, "masterParentId");
            return (Criteria) this;
        }

        public Criteria andMasterParentIdLessThan(String value) {
            addCriterion("master_parent_id <", value, "masterParentId");
            return (Criteria) this;
        }

        public Criteria andMasterParentIdLessThanOrEqualTo(String value) {
            addCriterion("master_parent_id <=", value, "masterParentId");
            return (Criteria) this;
        }

        public Criteria andMasterParentIdLike(String value) {
            addCriterion("master_parent_id like", value, "masterParentId");
            return (Criteria) this;
        }

        public Criteria andMasterParentIdNotLike(String value) {
            addCriterion("master_parent_id not like", value, "masterParentId");
            return (Criteria) this;
        }

        public Criteria andMasterParentIdIn(List<String> values) {
            addCriterion("master_parent_id in", values, "masterParentId");
            return (Criteria) this;
        }

        public Criteria andMasterParentIdNotIn(List<String> values) {
            addCriterion("master_parent_id not in", values, "masterParentId");
            return (Criteria) this;
        }

        public Criteria andMasterParentIdBetween(String value1, String value2) {
            addCriterion("master_parent_id between", value1, value2, "masterParentId");
            return (Criteria) this;
        }

        public Criteria andMasterParentIdNotBetween(String value1, String value2) {
            addCriterion("master_parent_id not between", value1, value2, "masterParentId");
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

        public Criteria andMarkTypeEqualTo(Boolean value) {
            addCriterion("mark_type =", value, "markType");
            return (Criteria) this;
        }

        public Criteria andMarkTypeNotEqualTo(Boolean value) {
            addCriterion("mark_type <>", value, "markType");
            return (Criteria) this;
        }

        public Criteria andMarkTypeGreaterThan(Boolean value) {
            addCriterion("mark_type >", value, "markType");
            return (Criteria) this;
        }

        public Criteria andMarkTypeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("mark_type >=", value, "markType");
            return (Criteria) this;
        }

        public Criteria andMarkTypeLessThan(Boolean value) {
            addCriterion("mark_type <", value, "markType");
            return (Criteria) this;
        }

        public Criteria andMarkTypeLessThanOrEqualTo(Boolean value) {
            addCriterion("mark_type <=", value, "markType");
            return (Criteria) this;
        }

        public Criteria andMarkTypeIn(List<Boolean> values) {
            addCriterion("mark_type in", values, "markType");
            return (Criteria) this;
        }

        public Criteria andMarkTypeNotIn(List<Boolean> values) {
            addCriterion("mark_type not in", values, "markType");
            return (Criteria) this;
        }

        public Criteria andMarkTypeBetween(Boolean value1, Boolean value2) {
            addCriterion("mark_type between", value1, value2, "markType");
            return (Criteria) this;
        }

        public Criteria andMarkTypeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("mark_type not between", value1, value2, "markType");
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