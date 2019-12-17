package com.opay.invite.backstage.dao.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LuckDrawInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LuckDrawInfoExample() {
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

        public Criteria andOpayNameIsNull() {
            addCriterion("opay_name is null");
            return (Criteria) this;
        }

        public Criteria andOpayNameIsNotNull() {
            addCriterion("opay_name is not null");
            return (Criteria) this;
        }

        public Criteria andOpayNameEqualTo(String value) {
            addCriterion("opay_name =", value, "opayName");
            return (Criteria) this;
        }

        public Criteria andOpayNameNotEqualTo(String value) {
            addCriterion("opay_name <>", value, "opayName");
            return (Criteria) this;
        }

        public Criteria andOpayNameGreaterThan(String value) {
            addCriterion("opay_name >", value, "opayName");
            return (Criteria) this;
        }

        public Criteria andOpayNameGreaterThanOrEqualTo(String value) {
            addCriterion("opay_name >=", value, "opayName");
            return (Criteria) this;
        }

        public Criteria andOpayNameLessThan(String value) {
            addCriterion("opay_name <", value, "opayName");
            return (Criteria) this;
        }

        public Criteria andOpayNameLessThanOrEqualTo(String value) {
            addCriterion("opay_name <=", value, "opayName");
            return (Criteria) this;
        }

        public Criteria andOpayNameLike(String value) {
            addCriterion("opay_name like", value, "opayName");
            return (Criteria) this;
        }

        public Criteria andOpayNameNotLike(String value) {
            addCriterion("opay_name not like", value, "opayName");
            return (Criteria) this;
        }

        public Criteria andOpayNameIn(List<String> values) {
            addCriterion("opay_name in", values, "opayName");
            return (Criteria) this;
        }

        public Criteria andOpayNameNotIn(List<String> values) {
            addCriterion("opay_name not in", values, "opayName");
            return (Criteria) this;
        }

        public Criteria andOpayNameBetween(String value1, String value2) {
            addCriterion("opay_name between", value1, value2, "opayName");
            return (Criteria) this;
        }

        public Criteria andOpayNameNotBetween(String value1, String value2) {
            addCriterion("opay_name not between", value1, value2, "opayName");
            return (Criteria) this;
        }

        public Criteria andOpayPhoneIsNull() {
            addCriterion("opay_phone is null");
            return (Criteria) this;
        }

        public Criteria andOpayPhoneIsNotNull() {
            addCriterion("opay_phone is not null");
            return (Criteria) this;
        }

        public Criteria andOpayPhoneEqualTo(String value) {
            addCriterion("opay_phone =", value, "opayPhone");
            return (Criteria) this;
        }

        public Criteria andOpayPhoneNotEqualTo(String value) {
            addCriterion("opay_phone <>", value, "opayPhone");
            return (Criteria) this;
        }

        public Criteria andOpayPhoneGreaterThan(String value) {
            addCriterion("opay_phone >", value, "opayPhone");
            return (Criteria) this;
        }

        public Criteria andOpayPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("opay_phone >=", value, "opayPhone");
            return (Criteria) this;
        }

        public Criteria andOpayPhoneLessThan(String value) {
            addCriterion("opay_phone <", value, "opayPhone");
            return (Criteria) this;
        }

        public Criteria andOpayPhoneLessThanOrEqualTo(String value) {
            addCriterion("opay_phone <=", value, "opayPhone");
            return (Criteria) this;
        }

        public Criteria andOpayPhoneLike(String value) {
            addCriterion("opay_phone like", value, "opayPhone");
            return (Criteria) this;
        }

        public Criteria andOpayPhoneNotLike(String value) {
            addCriterion("opay_phone not like", value, "opayPhone");
            return (Criteria) this;
        }

        public Criteria andOpayPhoneIn(List<String> values) {
            addCriterion("opay_phone in", values, "opayPhone");
            return (Criteria) this;
        }

        public Criteria andOpayPhoneNotIn(List<String> values) {
            addCriterion("opay_phone not in", values, "opayPhone");
            return (Criteria) this;
        }

        public Criteria andOpayPhoneBetween(String value1, String value2) {
            addCriterion("opay_phone between", value1, value2, "opayPhone");
            return (Criteria) this;
        }

        public Criteria andOpayPhoneNotBetween(String value1, String value2) {
            addCriterion("opay_phone not between", value1, value2, "opayPhone");
            return (Criteria) this;
        }

        public Criteria andPrizeIsNull() {
            addCriterion("prize is null");
            return (Criteria) this;
        }

        public Criteria andPrizeIsNotNull() {
            addCriterion("prize is not null");
            return (Criteria) this;
        }

        public Criteria andPrizeEqualTo(String value) {
            addCriterion("prize =", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeNotEqualTo(String value) {
            addCriterion("prize <>", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeGreaterThan(String value) {
            addCriterion("prize >", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeGreaterThanOrEqualTo(String value) {
            addCriterion("prize >=", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeLessThan(String value) {
            addCriterion("prize <", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeLessThanOrEqualTo(String value) {
            addCriterion("prize <=", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeLike(String value) {
            addCriterion("prize like", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeNotLike(String value) {
            addCriterion("prize not like", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeIn(List<String> values) {
            addCriterion("prize in", values, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeNotIn(List<String> values) {
            addCriterion("prize not in", values, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeBetween(String value1, String value2) {
            addCriterion("prize between", value1, value2, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeNotBetween(String value1, String value2) {
            addCriterion("prize not between", value1, value2, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeLevelIsNull() {
            addCriterion("prize_level is null");
            return (Criteria) this;
        }

        public Criteria andPrizeLevelIsNotNull() {
            addCriterion("prize_level is not null");
            return (Criteria) this;
        }

        public Criteria andPrizeLevelEqualTo(Integer value) {
            addCriterion("prize_level =", value, "prizeLevel");
            return (Criteria) this;
        }

        public Criteria andPrizeLevelNotEqualTo(Integer value) {
            addCriterion("prize_level <>", value, "prizeLevel");
            return (Criteria) this;
        }

        public Criteria andPrizeLevelGreaterThan(Integer value) {
            addCriterion("prize_level >", value, "prizeLevel");
            return (Criteria) this;
        }

        public Criteria andPrizeLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("prize_level >=", value, "prizeLevel");
            return (Criteria) this;
        }

        public Criteria andPrizeLevelLessThan(Integer value) {
            addCriterion("prize_level <", value, "prizeLevel");
            return (Criteria) this;
        }

        public Criteria andPrizeLevelLessThanOrEqualTo(Integer value) {
            addCriterion("prize_level <=", value, "prizeLevel");
            return (Criteria) this;
        }

        public Criteria andPrizeLevelIn(List<Integer> values) {
            addCriterion("prize_level in", values, "prizeLevel");
            return (Criteria) this;
        }

        public Criteria andPrizeLevelNotIn(List<Integer> values) {
            addCriterion("prize_level not in", values, "prizeLevel");
            return (Criteria) this;
        }

        public Criteria andPrizeLevelBetween(Integer value1, Integer value2) {
            addCriterion("prize_level between", value1, value2, "prizeLevel");
            return (Criteria) this;
        }

        public Criteria andPrizeLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("prize_level not between", value1, value2, "prizeLevel");
            return (Criteria) this;
        }

        public Criteria andPrizePoolIsNull() {
            addCriterion("prize_pool is null");
            return (Criteria) this;
        }

        public Criteria andPrizePoolIsNotNull() {
            addCriterion("prize_pool is not null");
            return (Criteria) this;
        }

        public Criteria andPrizePoolEqualTo(Integer value) {
            addCriterion("prize_pool =", value, "prizePool");
            return (Criteria) this;
        }

        public Criteria andPrizePoolNotEqualTo(Integer value) {
            addCriterion("prize_pool <>", value, "prizePool");
            return (Criteria) this;
        }

        public Criteria andPrizePoolGreaterThan(Integer value) {
            addCriterion("prize_pool >", value, "prizePool");
            return (Criteria) this;
        }

        public Criteria andPrizePoolGreaterThanOrEqualTo(Integer value) {
            addCriterion("prize_pool >=", value, "prizePool");
            return (Criteria) this;
        }

        public Criteria andPrizePoolLessThan(Integer value) {
            addCriterion("prize_pool <", value, "prizePool");
            return (Criteria) this;
        }

        public Criteria andPrizePoolLessThanOrEqualTo(Integer value) {
            addCriterion("prize_pool <=", value, "prizePool");
            return (Criteria) this;
        }

        public Criteria andPrizePoolIn(List<Integer> values) {
            addCriterion("prize_pool in", values, "prizePool");
            return (Criteria) this;
        }

        public Criteria andPrizePoolNotIn(List<Integer> values) {
            addCriterion("prize_pool not in", values, "prizePool");
            return (Criteria) this;
        }

        public Criteria andPrizePoolBetween(Integer value1, Integer value2) {
            addCriterion("prize_pool between", value1, value2, "prizePool");
            return (Criteria) this;
        }

        public Criteria andPrizePoolNotBetween(Integer value1, Integer value2) {
            addCriterion("prize_pool not between", value1, value2, "prizePool");
            return (Criteria) this;
        }

        public Criteria andReferenceIsNull() {
            addCriterion("reference is null");
            return (Criteria) this;
        }

        public Criteria andReferenceIsNotNull() {
            addCriterion("reference is not null");
            return (Criteria) this;
        }

        public Criteria andReferenceEqualTo(String value) {
            addCriterion("reference =", value, "reference");
            return (Criteria) this;
        }

        public Criteria andReferenceNotEqualTo(String value) {
            addCriterion("reference <>", value, "reference");
            return (Criteria) this;
        }

        public Criteria andReferenceGreaterThan(String value) {
            addCriterion("reference >", value, "reference");
            return (Criteria) this;
        }

        public Criteria andReferenceGreaterThanOrEqualTo(String value) {
            addCriterion("reference >=", value, "reference");
            return (Criteria) this;
        }

        public Criteria andReferenceLessThan(String value) {
            addCriterion("reference <", value, "reference");
            return (Criteria) this;
        }

        public Criteria andReferenceLessThanOrEqualTo(String value) {
            addCriterion("reference <=", value, "reference");
            return (Criteria) this;
        }

        public Criteria andReferenceLike(String value) {
            addCriterion("reference like", value, "reference");
            return (Criteria) this;
        }

        public Criteria andReferenceNotLike(String value) {
            addCriterion("reference not like", value, "reference");
            return (Criteria) this;
        }

        public Criteria andReferenceIn(List<String> values) {
            addCriterion("reference in", values, "reference");
            return (Criteria) this;
        }

        public Criteria andReferenceNotIn(List<String> values) {
            addCriterion("reference not in", values, "reference");
            return (Criteria) this;
        }

        public Criteria andReferenceBetween(String value1, String value2) {
            addCriterion("reference between", value1, value2, "reference");
            return (Criteria) this;
        }

        public Criteria andReferenceNotBetween(String value1, String value2) {
            addCriterion("reference not between", value1, value2, "reference");
            return (Criteria) this;
        }

        public Criteria andRequestidIsNull() {
            addCriterion("requestId is null");
            return (Criteria) this;
        }

        public Criteria andRequestidIsNotNull() {
            addCriterion("requestId is not null");
            return (Criteria) this;
        }

        public Criteria andRequestidEqualTo(String value) {
            addCriterion("requestId =", value, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidNotEqualTo(String value) {
            addCriterion("requestId <>", value, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidGreaterThan(String value) {
            addCriterion("requestId >", value, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidGreaterThanOrEqualTo(String value) {
            addCriterion("requestId >=", value, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidLessThan(String value) {
            addCriterion("requestId <", value, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidLessThanOrEqualTo(String value) {
            addCriterion("requestId <=", value, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidLike(String value) {
            addCriterion("requestId like", value, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidNotLike(String value) {
            addCriterion("requestId not like", value, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidIn(List<String> values) {
            addCriterion("requestId in", values, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidNotIn(List<String> values) {
            addCriterion("requestId not in", values, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidBetween(String value1, String value2) {
            addCriterion("requestId between", value1, value2, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidNotBetween(String value1, String value2) {
            addCriterion("requestId not between", value1, value2, "requestid");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(LocalDateTime value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(LocalDateTime value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(LocalDateTime value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<LocalDateTime> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
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