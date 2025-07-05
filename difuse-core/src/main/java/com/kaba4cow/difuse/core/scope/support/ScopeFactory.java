package com.kaba4cow.difuse.core.scope.support;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.scope.Scope;
import com.kaba4cow.difuse.core.util.reflections.ConstructorScanner;

@SystemBean
public class ScopeFactory {

	public <T extends Scope> T createScope(Class<T> type) {
		try {
			return ConstructorScanner.of(type).findNoArgsConstructor().newInstance();
		} catch (Exception exception) {
			throw new DifuseException(String.format("Could not create Scope %s", type), exception);
		}
	}

}
