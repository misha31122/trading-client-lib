package ru.vtb.obuf.hibernate.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.hibernate.annotations.IdGeneratorType;
import org.hibernate.annotations.ValueGenerationType;
import ru.vtb.obuf.hibernate.uuid.CustomUuidGeneratorSrv;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Вспомогательная аннотация, помогает сгенерить UUID 1, 6 и 7 версии.
 * Можно проставлять над полями типа UUID и String
 */
@IdGeneratorType(CustomUuidGeneratorSrv.class)
@ValueGenerationType(generatedBy = CustomUuidGeneratorSrv.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface CustomUuidGenerator {

  /**
   * Какого типа UUID генерировать
   */
  enum Style {
    /**
     * Defaults to {@link #V7}. Generate UUID of seven version
     */
    V7,
    /**
     * Generate UUID of six version
     */
    V6,
    /**
     * Use a time-based generation strategy consistent with RFC 4122
     * version 1, but with IP address instead of MAC address.
     *
     * @implNote Can be a bottleneck, since synchronization is used when
     *           incrementing an internal counter as part of the algorithm.
     */
    V1
  }

  /**
   * Specifies which {@linkplain Style style} of UUID generation should be used.
   */
  Style style() default Style.V7;
}
