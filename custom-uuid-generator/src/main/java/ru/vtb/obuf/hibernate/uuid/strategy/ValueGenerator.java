package ru.vtb.obuf.hibernate.uuid.strategy;

import java.util.UUID;

public interface ValueGenerator {
  UUID generateUuid();
}
