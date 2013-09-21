package net.alxb.junitparamsdemo;

import static com.google.common.collect.Sets.newHashSet;

import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * Case-sensitive implementation of {@link UniqueCharacterCounter}
 * 
 * @author Alex Borisov
 * 
 */
public class CaseSensitiveUniqueCharacterCounter implements
		UniqueCharacterCounter {

	@Override
	public int count(String input) {
		if (StringUtils.isEmpty(input)) {
			return 0;
		}
		Set<Character> uniqueChars = getUniqueCharsSet(input);
		return uniqueChars.size();
	}

	private Set<Character> getUniqueCharsSet(String input) {
		Set<Character> uniqueChars = newHashSet();
		for (int index = 0; index < input.length(); index++) {
			uniqueChars.add(input.charAt(index));
		}
		return uniqueChars;
	}

}