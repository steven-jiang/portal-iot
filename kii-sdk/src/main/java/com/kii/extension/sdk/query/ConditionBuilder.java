package com.kii.extension.sdk.query;

import java.util.Arrays;
import java.util.List;

import com.kii.extension.sdk.query.condition.All;
import com.kii.extension.sdk.query.condition.AndLogic;
import com.kii.extension.sdk.query.condition.Equal;
import com.kii.extension.sdk.query.condition.FieldExist;
import com.kii.extension.sdk.query.condition.InCollect;
import com.kii.extension.sdk.query.condition.LogicCol;
import com.kii.extension.sdk.query.condition.NotLogic;
import com.kii.extension.sdk.query.condition.OrLogic;
import com.kii.extension.sdk.query.condition.PrefixLike;
import com.kii.extension.sdk.query.condition.Range;


public class ConditionBuilder {
	
	private LogicCol clauses;
	
	private Condition condition;

	private void fill(Condition newCond){
		if(clauses==null&&condition==null){
			condition=newCond;
		}else if(clauses!=null){
			clauses.addClause(newCond);
		}else if(condition!=null){
			clauses=new AndLogic();
			clauses.addClause(condition);
			clauses.addClause(newCond);
			condition=null;
		}
	}
	
	
	public ConditionBuilder equal(String field, Object value) {
		Equal eq = new Equal(field,value);

		fill(eq);
		return this;
	}

	public ConditionBuilder fieldExist(String field, FieldType type) {
		FieldExist q = new FieldExist();
		q.setField(field);
		q.setFieldType(type);

		fill(q);
		return this;
	}

	public ConditionBuilder In(String field, List<Object> objList) {
		InCollect q = new InCollect();
		q.setField(field);
		q.setValues(objList);

		fill(q);
		return this;
	}

	public ConditionBuilder In(String field, String[] objArray) {
		InCollect q = new InCollect();
		q.setField(field);


		q.setValues(Arrays.asList(objArray));

		fill(q);
		return this;
	}

	public ConditionBuilder prefixLike(String field, String value) {
		PrefixLike q = new PrefixLike();
		q.setField(field);
		q.setPrefix(value);

		fill(q);
		return this;
	}

	private <T> ConditionBuilder range(String field, T lower, Boolean withLower,
			T upper, Boolean withUpper) {

		T sign = lower;
		if (lower == null) {
			sign = upper;
		}

		Range q = new Range();
		q.setField(field);
		if (upper != null) {
			q.setUpperLimit(upper);
		}
		q.setUpperIncluded(withUpper);
		q.setLowerIncluded(withLower);
		if (lower != null) {
			q.setLowerLimit(lower);
		}
		fill(q);

		return this;
	}

//	public ConditionBuilder addSubClause(ConditionBuilder...  logic) {
//		for(ConditionBuilder b:logic){
//			fill(b.condition);
//		}
//		return this;
//	}

	public <T> ConditionBuilder Less(String field, T value) {
		return range(field, null, null, value, null);

	}

	public <T> ConditionBuilder great(String field, T value) {
		return range(field, value, null, null, null);
	}

	public <T> ConditionBuilder lessAndEq(String field, T value) {
		return range(field, null, null, value, true);
	}

	public <T> ConditionBuilder greatAndEq(String field, T value) {
		return range(field, value, true, null, null);
	}

	public <T> ConditionBuilder betweenIn(String field, T lower, boolean withLower,
			T upper, boolean withUpper) {
		return range(field, lower, withLower, upper, withUpper);
	}

	
	public static ConditionBuilder andCondition() {
		ConditionBuilder cb=new ConditionBuilder();
		cb.clauses=new AndLogic();
		return cb;
	}

	public static ConditionBuilder orCondition() {
		ConditionBuilder cb=new ConditionBuilder();
		cb.clauses=new OrLogic();
		return cb;
	}


	/**
	 * create simple condition (no logic oper)
	 * @return
	 */
	public static ConditionBuilder newCondition() {
		ConditionBuilder cb=new ConditionBuilder();
		return cb;
	}
	
	/**
	 * create a conition with not oper
	 * @return
	 */
	public static ConditionBuilder notCondition() {
		ConditionBuilder cb=new ConditionBuilder();
		cb.clauses=new NotLogic();
		return cb;
	}

	
	public QueryBuilder getFinalCondition() {
		if (clauses==null) {
			return new QueryBuilder(condition);
		} else {
			return new QueryBuilder(clauses);
		}
	}


	public static Condition getAll() {
		return new All();
	}
}
