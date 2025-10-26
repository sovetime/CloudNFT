package cn.hollis.nft.turbo.base.utils;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.hibernate.validator.HibernateValidator;

import java.util.Set;


//参数校验工具，封装好的
public class BeanValidator {

    private static Validator validator = Validation
            .byProvider(HibernateValidator.class)   // 使用 Hibernate Validator 实现
            .configure()
            .failFast(true)     // 快速失败模式：遇到第一个错误就返回
            .buildValidatorFactory()
            .getValidator();

    public static void validateObject(Object object, Class<?>... groups) throws ValidationException {
        // 校验对象上的约束注解
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        // 如果有错误，取第一个错误信息抛出异常
        if (constraintViolations.stream().findFirst().isPresent()) {
            throw new ValidationException(constraintViolations.stream().findFirst().get().getMessage());
        }
    }
}