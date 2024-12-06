package ru.tinkoff.trade.hibernate.uuid.strategy;

import java.util.UUID;

public interface ValueGenerator {
  UUID generateUuid();
}
