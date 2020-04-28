package com.elementsin.insuranceproduct.mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface Entity mapper.
 * I preferred doing my own mapper rather than using libraries like dozer
 * to have more control on how to map entities to Dtos specially when you have immutable value objects like Money
 * It can be implmented either In OOP style if there is a need to be reused by other components or In Functional programming style in case it will be used in only one place
 *
 * @param <From> the type parameter
 * @param <To>   the type parameter
 */
public interface EntityMapper<From,To> {
	/**
	 * Map to to.
	 *
	 * @param from the from
	 * @return the to
	 */
	To mapTo(From from);

	/**
	 * Map list list.
	 *
	 * @param fromList the from list
	 * @return the list
	 */
	default List<To> mapList(List<From> fromList){
		List<To> toList = new ArrayList<>();
		if(fromList != null)
			fromList.forEach(from -> toList.add(mapTo(from)));

		return toList;
	}
}
