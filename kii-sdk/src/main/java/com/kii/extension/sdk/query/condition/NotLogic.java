package com.kii.extension.sdk.query.condition;

import com.kii.extension.sdk.query.Condition;
import com.kii.extension.sdk.query.ConditionType;

public class NotLogic extends LogicCol {

	@Override
	public ConditionType getType() {
		return ConditionType.not;
	}

	private Condition clause;

	public Condition getClause() {
		return clause;
	}

	public void setClause(Condition clause) {
		this.clause = clause;
	}

	@Override
	public LogicCol addClause(Condition clause) {
		this.clause=clause;
		return this;
	}
	
	
}

