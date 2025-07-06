package com.kaba4cow.difuse.aspects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.kaba4cow.difuse.aspects.bean.CallHistory;
import com.kaba4cow.difuse.aspects.bean.TestBean;
import com.kaba4cow.difuse.core.annotation.context.DifuseContext;
import com.kaba4cow.difuse.core.test.DifuseTestExtension;
import com.kaba4cow.difuse.core.test.annotation.TestContext;

@DifuseContext
@TestContext(AspectsTest.class)
@ExtendWith(DifuseTestExtension.class)
public class AspectsTest {

	@Test
	public void methodsInvoked(TestBean bean, CallHistory callHistory) {
		bean.beanMethod();
		List<Call> calls = callHistory.getCalls();
		for (Call call : Call.values()) {
			assertTrue(calls.contains(call));
			assertEquals(call.ordinal(), calls.indexOf(call));
		}
	}

}
