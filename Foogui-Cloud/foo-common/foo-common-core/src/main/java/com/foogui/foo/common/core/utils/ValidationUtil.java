package com.foogui.foo.common.core.utils;

import com.foogui.foo.common.core.enums.ErrorCode;
import com.foogui.foo.common.core.exception.BizException;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;


/**
 * 手动验证数据参数是否合法，常用于RPC和MQ消费，默认抛出第一个异常属性的错误信息
 *
 * @author Foogui
 * @date 2023/07/04
 */
public class ValidationUtil {

    private final static Validator VALIDATOR;

    static {
        VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 验证数据
     *
     * @param object 对象
     * @param groups 验证分组。如果不指定分组，则默认应用所有的验证规则。
     */
    public static void valid(Object object, Class<?>... groups) {

        Set<ConstraintViolation<Object>> constraintViolationSet = VALIDATOR.validate(object, groups);
        // 如果返回的集合不为空，就表示存在校验异常
        if (!CollectionUtils.isEmpty(constraintViolationSet)) {
            // 找到第一个异常
            Optional<String> errorMessage = constraintViolationSet.stream().findFirst().map(ConstraintViolation::getMessage);

            if (errorMessage.isPresent()) {
                throw new BizException(ErrorCode.VALIDATION_ERROR);
            }
        }
    }

}
