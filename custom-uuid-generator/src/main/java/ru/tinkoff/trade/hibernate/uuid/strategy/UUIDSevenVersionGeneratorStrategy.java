package ru.tinkoff.trade.hibernate.uuid.strategy;

import com.fasterxml.uuid.Generators;
import java.util.UUID;

/**
 * Генератор UUID V7.
 */
public class UUIDSevenVersionGeneratorStrategy implements ValueGenerator {

	public UUIDSevenVersionGeneratorStrategy() {}

	@Override
	public UUID generateUuid() {
		return Generators.timeBasedEpochGenerator().generate();
	}
}
