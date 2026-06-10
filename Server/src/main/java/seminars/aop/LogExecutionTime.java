package seminars.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Анотация для измерения времени выполнения метода
 * Методы помеченные этой анотацией, будут перехвачены аспектом,
 * который измерит время их выполнения и выведет результат в консоль
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecutionTime {}
