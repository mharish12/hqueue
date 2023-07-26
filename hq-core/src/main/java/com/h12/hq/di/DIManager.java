package com.h12.hq.di;

import com.h12.hq.IManager;
import com.h12.hq.IValidator;
import com.h12.hq.exception.HQException;
import com.h12.hq.exception.ValidationException;
import com.h12.hq.AppContext;
import com.h12.hq.util.Constants;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassGraphException;
import io.github.classgraph.ScanResult;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public abstract class DIManager implements IManager, IValidator {
    private final ValidatorFactory validatorFactory;

    public DIManager() {
        this(Validation.buildDefaultValidatorFactory());
    }
    public DIManager(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }

    @Override
    public void prepare(DependencyManager dependencyManager) {
        scanPackages(dependencyManager.getAppContext());
    }

    protected void scanPackages(AppContext appContext) {
        String packageToScan = appContext.getEnvironment().getProperty(Constants.PACKAGE_TO_SCAN);
        ClassGraph classGraph = new ClassGraph();
        classGraph
                .enableAllInfo()
                .acceptPackages(packageToScan);
        try(ScanResult scanResult = classGraph.scan()) {
            appContext.setScanResult(scanResult);
        } catch (ClassGraphException ex) {
            throw new HQException(ex.getMessage(), ex);
        }
    }

    @Override
    public void validate(Object o) {
        try {
            Set<ConstraintViolation<Object>> violations =  validatorFactory.getValidator().validate(o);

            if(violations.size() > 0) {
                for (ConstraintViolation<Object> objectConstraintViolation: violations){
                }
            }
        } catch (Exception e) {
            ValidationException validationException = new ValidationException(e.getMessage());
            validationException.addSuppressed(e);
            throw e;
        }
    }

}
