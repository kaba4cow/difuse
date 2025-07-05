package com.kaba4cow.difuse.core.bean.source.validator.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaba4cow.difuse.core.DifuseException;
import com.kaba4cow.difuse.core.annotation.system.SystemBean;
import com.kaba4cow.difuse.core.annotation.system.SystemDependency;
import com.kaba4cow.difuse.core.bean.source.validator.BeanSourceValidator;
import com.kaba4cow.difuse.core.system.PackageScannerPool;
import com.kaba4cow.difuse.core.util.ExecutionTimer;
import com.kaba4cow.difuse.core.util.reflections.ConstructorScanner;
import com.kaba4cow.difuse.core.util.reflections.PackageScanner;

@SystemBean
public class BeanSourceValidatorRegistrar {

	private static final Logger log = LoggerFactory.getLogger("BeanSourceValidatorRegistrar");

	@SystemDependency
	private PackageScannerPool packageScannerPool;

	@SystemDependency
	private BeanSourceValidatorRegistry beanSourceValidatorRegistry;

	public void registerBeanSourceValidators(Class<?> sourceClass) {
		log.info("Registering BeanSourceValidators...");
		ExecutionTimer timer = new ExecutionTimer().start();

		PackageScanner packageScanner = packageScannerPool.getPackageScanner(sourceClass);
		packageScanner.searchClassesOf(BeanSourceValidator.class).stream()//
				.map(this::createBeanSourceValidator)//
				.forEach(beanSourceValidatorRegistry::registerValidator);

		log.info("BeanSourceValidator registration took {} ms", timer.finish().getExecutionMillis());
	}

	private <T extends BeanSourceValidator> T createBeanSourceValidator(Class<T> type) {
		try {
			return ConstructorScanner.of(type).findNoArgsConstructor().newInstance();
		} catch (Exception exception) {
			throw new DifuseException(String.format("Could not create BeanSourceValidator %s", type), exception);
		}
	}

}
