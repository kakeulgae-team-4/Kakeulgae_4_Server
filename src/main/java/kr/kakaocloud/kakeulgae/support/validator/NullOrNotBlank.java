package kr.kakaocloud.kakeulgae.support.validator;


import jakarta.validation.Constraint;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

    @MustBeDocumented
    @Constraint(validatedBy =[NullOrNotBlankValidator::class])
    @Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
    @Retention(AnnotationRetention.RUNTIME)
    @
    class NullOrNotBlank(
        val message:String="",
        val groups:Array<KClass<*>>=[],
        val payload:Array<KClass<out Payload>>=[],
        )

    class NullOrNotBlankValidator :ConstraintValidator<NullOrNotBlank, String?>

    {
        override fun initialize(contactNumber:NullOrNotBlank){
    }
        override fun isValid(
        contactField:String ?,
        cxt:ConstraintValidatorContext ?,
    ):Boolean {
        return contactField == null || contactField.isNotBlank()
    }
    }
}
