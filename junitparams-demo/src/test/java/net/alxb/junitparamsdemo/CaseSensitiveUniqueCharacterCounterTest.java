package net.alxb.junitparamsdemo;

import static junitparams.JUnitParamsRunner.$;
import static org.fest.assertions.Assertions.assertThat;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * Tests case-sensitive implementation of {@link UniqueCharacterCounter}
 *
 */
@RunWith(JUnitParamsRunner.class)
public class CaseSensitiveUniqueCharacterCounterTest {
	UniqueCharacterCounter counter = new CaseSensitiveUniqueCharacterCounter();
	
	@Parameters(method="provideTestData")
	@Test
	public void countUniqueCharsAndCompareToExpected(String inputString, int expectedCharsCount) {
		int result = counter.count(inputString);
		assertThat(result).isEqualTo(expectedCharsCount);
	}
	
	public Object[] provideTestData() {
		return $($("abcde", 5),
				$("", 0),
				$("aaabaabaabb", 2),
				$("demo demo", 5),
				$("demo DEMO", 9),
				$("aAbBaB", 4),
				$("222222", 1),
				$(null, 0));
	}
	
}
