package com.kii.extension.sdk.query.condition;

import javax.xml.bind.annotation.XmlRootElement;


import org.springframework.util.StringUtils;

import com.kii.extension.sdk.query.Condition;
import com.kii.extension.sdk.query.ConditionType;

@XmlRootElement
public class Range extends SimpleCondition {
	
	
	
	public static Range great(String field,Object val){
		return new Range(field,val,false,null,false);
	}
	
	public static Range greatAndEq(String field,Object val){
		return new Range(field,val,true,null,false);
	}
	
	public static Range less(String field,Object val){
		return new Range(field,null,false,val,false);
	}
	
	public static Range lessAndEq(String field,Object val){
		return new Range(field,null,true,val,true);
	}
	

	
	public Range(){
		
	}
	
	private Range(String field,Object low,boolean withLow,Object upper,boolean withUpp){
		setField(field);
		setLowerIncluded(withLow );
		setUpperIncluded(withUpp);
		
		setLowerLimit(low);
		setUpperLimit(upper);
		
		
	}

	protected Object upperLimit;
	
	private Boolean upperIncluded;
	
	protected Object lowerLimit;
	
	private Boolean lowerIncluded;




	public Boolean isUpperIncluded() {
		return upperIncluded;
	}

	public void setUpperIncluded(Boolean upperIncluded) {
		this.upperIncluded = upperIncluded;
	}

	

	public Boolean isLowerIncluded() {
		return lowerIncluded;
	}

	public void setLowerIncluded(Boolean lowerIncluded) {
		this.lowerIncluded = lowerIncluded;
	}
	
	public Object getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(Object upperLimit) {
		this.upperLimit = upperLimit;
	}
	
	public Object getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(Object lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	@Override
	public ConditionType getType() {
		return ConditionType.range;
	}

	

}
