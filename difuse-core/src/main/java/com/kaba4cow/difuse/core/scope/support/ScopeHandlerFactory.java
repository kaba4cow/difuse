package com.kaba4cow.difuse.core.scope.support;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.system.SystemComponent;
import com.kaba4cow.difuse.core.scope.handler.ScopeHandler;
import com.kaba4cow.difuse.core.util.reflections.ConstructorScanner;

@SystemComponent
public class ScopeHandlerFactory {

	public <T extends ScopeHandler> T createScopeHandler(Class<T> type) {
		try {
			return ConstructorScanner.of(type).findNoArgsConstructor().newInstance();
		} catch (Exception exception) {
			throw new DifuseException(String.format("Could not create ScopeHandler %s", type), exception);
		}
	}

}
