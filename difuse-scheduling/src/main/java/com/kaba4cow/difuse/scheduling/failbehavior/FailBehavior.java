package com.kaba4cow.difuse.scheduling.failbehavior;

import java.util.Set;

public class FailBehavior {

	private final boolean cancelOnFail;

	private final Set<Class<? extends Throwable>> throwables;

	public FailBehavior(boolean cancelOnFail, Set<Class<? extends Throwable>> throwables) {
		this.cancelOnFail = cancelOnFail;
		this.throwables = throwables;
	}

	public boolean mustCancel(Throwable throwable) {
		if (cancelOnFail) {
			if (throwables.isEmpty())
				return true;
			Class<? extends Throwable> caught = throwable.getClass();
			for (Class<? extends Throwable> allowed : throwables)
				if (allowed.isAssignableFrom(caught))
					return true;
		}
		return false;
	}

}
