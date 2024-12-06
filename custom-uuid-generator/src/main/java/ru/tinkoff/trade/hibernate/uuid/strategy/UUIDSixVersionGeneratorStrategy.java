package ru.tinkoff.trade.hibernate.uuid.strategy;

import com.fasterxml.uuid.Generators;
import java.util.UUID;

/**
 * Генератор UUID V6.
 */
public class UUIDSixVersionGeneratorStrategy implements ValueGenerator {

	public UUIDSixVersionGeneratorStrategy() {}

	@Override
	public UUID generateUuid() {
		return Generators.timeBasedReorderedGenerator().generate();
	}

}
