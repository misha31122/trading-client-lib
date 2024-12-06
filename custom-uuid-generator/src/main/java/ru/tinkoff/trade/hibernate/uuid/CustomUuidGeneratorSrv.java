package ru.tinkoff.trade.hibernate.uuid;

import static org.hibernate.generator.EventTypeSets.INSERT_ONLY;
import static org.hibernate.internal.util.ReflectHelper.getPropertyType;
import static ru.tinkoff.trade.hibernate.annotations.CustomUuidGenerator.Style.V1;
import static ru.tinkoff.trade.hibernate.annotations.CustomUuidGenerator.Style.V6;

import java.lang.reflect.Member;
import java.util.EnumSet;
import java.util.UUID;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;
import org.hibernate.generator.EventTypeSets;
import org.hibernate.generator.GeneratorCreationContext;
import org.hibernate.id.factory.spi.CustomIdGeneratorCreationContext;
import org.hibernate.type.descriptor.java.UUIDJavaType;
import org.hibernate.type.descriptor.java.UUIDJavaType.ValueTransformer;
import ru.tinkoff.trade.hibernate.uuid.strategy.UUIDSevenVersionGeneratorStrategy;
import ru.tinkoff.trade.hibernate.uuid.strategy.UUIDSixVersionGeneratorStrategy;
import ru.tinkoff.trade.hibernate.uuid.strategy.ValueGenerator;
import ru.tinkoff.trade.hibernate.annotations.CustomUuidGenerator;
import ru.tinkoff.trade.hibernate.uuid.strategy.UUIDOneVersionGeneratorStrategy;

/**
 * Generates {@link UUID}s.
 *
 * @see CustomUuidGenerator
 */
public class CustomUuidGeneratorSrv implements BeforeExecutionGenerator {

  private final ValueGenerator generator;
  private final ValueTransformer valueTransformer;

  private CustomUuidGeneratorSrv(
      CustomUuidGenerator config,
      Member idMember) {
    if (config.style() == V1) {
      generator = new UUIDOneVersionGeneratorStrategy();
    } else if (config.style() == V6) {
      generator = new UUIDSixVersionGeneratorStrategy();
    } else {
      generator = new UUIDSevenVersionGeneratorStrategy();
    }

    final Class<?> propertyType = getPropertyType(idMember);

    if (UUID.class.isAssignableFrom(propertyType)) {
      valueTransformer = UUIDJavaType.PassThroughTransformer.INSTANCE;
    } else if (String.class.isAssignableFrom(propertyType)) {
      valueTransformer = UUIDJavaType.ToStringTransformer.INSTANCE;
    } else if (byte[].class.isAssignableFrom(propertyType)) {
      valueTransformer = UUIDJavaType.ToBytesTransformer.INSTANCE;
    } else {
      throw new HibernateException(
          "Unanticipated return type [" + propertyType.getName() + "] for UUID conversion");
    }
  }

  public CustomUuidGeneratorSrv(
      CustomUuidGenerator config,
      Member idMember,
      CustomIdGeneratorCreationContext creationContext) {
    this(config, idMember);
  }

  public CustomUuidGeneratorSrv(
      CustomUuidGenerator config,
      Member member,
      GeneratorCreationContext creationContext) {
    this(config, member);
  }

  /**
   * @return {@link EventTypeSets#INSERT_ONLY}
   */
  @Override
  public EnumSet<EventType> getEventTypes() {
    return INSERT_ONLY;
  }

  @Override
  public Object generate(SharedSessionContractImplementor session, Object owner,
      Object currentValue, EventType eventType) {
    return valueTransformer.transform(generator.generateUuid());
  }
}
