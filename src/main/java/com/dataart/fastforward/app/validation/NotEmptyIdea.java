package com.dataart.fastforward.app.validation;

import com.dataart.fastforward.app.dto.IdeaDTO;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author logariett on 27.12.2016.
 */
@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NotEmptyIdea.NotEmptyIdeaValidator.class)
@Documented
public @interface NotEmptyIdea {

    String message() default "error.validation.notemptyidea_violation";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class NotEmptyIdeaValidator implements ConstraintValidator<NotEmptyIdea, IdeaDTO> {

        private NotEmptyIdea notEmptyIdea;

        @Override
        public void initialize(NotEmptyIdea notEmptyIdea) {
            this.notEmptyIdea = notEmptyIdea;
        }

        @Override
        public boolean isValid(IdeaDTO ideaDTO, ConstraintValidatorContext constraintValidatorContext) {

            if (StringUtils.isNoneBlank(ideaDTO.getIdeaName())) {
                return true;
            }

            if (StringUtils.isNoneBlank(ideaDTO.getIdeaText())) {
                return true;
            }

            if (ideaDTO.getAttachments().length > 0) {
                return true;
            }

            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(notEmptyIdea.message()).addConstraintViolation();
            return false;
        }
    }
}
