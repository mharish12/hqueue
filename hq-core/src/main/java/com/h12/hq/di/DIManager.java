package com.h12.hq.di;

import com.h12.hq.IManager;
import com.h12.hq.IValidator;
import com.h12.hq.exception.ValidationException;
import com.h12.hq.AppContext;
import com.h12.hq.util.Constants;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public abstract class DIManager implements IManager, IValidator {
    private static final long serialVersionUID = 1234563424223475423L;
    private AppContext appContext;
    private ScanResult scanResult;
    private final ValidatorFactory validatorFactory;

    public DIManager() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
    }

    @Override
    public void prepare(AppContext appContext) {
        this.appContext = appContext;
        String packageToScan = appContext.getEnvironment().getProperty(Constants.PACKAGE_TO_SCAN);
        ClassGraph classGraph = new ClassGraph();
        classGraph
                .enableAllInfo()
                .acceptPackages(packageToScan);
        this.scanResult = classGraph.scan();
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
