package com.kaba4cow.difuse.aspects.bean;

import java.util.ArrayList;
import java.util.List;

import com.kaba4cow.difuse.aspects.Call;
import com.kaba4cow.difuse.core.annotation.system.Accessible;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;

@Accessible
@SystemBean
public class CallHistory {

	private final List<Call> calls = new ArrayList<>();

	public void addCall(Call call) {
		calls.add(call);
	}

	public List<Call> getCalls() {
		return calls;
	}

}
