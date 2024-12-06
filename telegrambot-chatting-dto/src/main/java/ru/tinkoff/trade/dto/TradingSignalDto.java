package ru.tinkoff.trade.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "signalType",
    "ticker",
    "figi",
    "companyName"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradingSignalDto implements Serializable {

  @Serial
  private static final long serialVersionUID = 7970112988030613022L;

  @JsonProperty("signalType")
  @JsonPropertyDescription("long -  покупке в long, short -  покупке в шорт")
  private SignalType signalType;

  @JsonProperty("ticker")
  @JsonPropertyDescription("тикер компании")
  String ticker;

  @JsonProperty("figi")
  @JsonPropertyDescription("figi компании, по нему можно найти компанию в базе")
  String figi;

  @JsonProperty("companyName")
  @JsonPropertyDescription("название компании")
  String companyName;

  public enum SignalType {

    SHORT("short"),
    LONG("long");
    private final String value;
    private final static Map<String, SignalType> CONSTANTS = new HashMap<>();

    static {
      for (SignalType c : values()) {
        CONSTANTS.put(c.value, c);
      }
    }

    SignalType(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }

    @JsonValue
    public String value() {
      return this.value;
    }

    @JsonCreator
    public static SignalType fromValue(String value) {
      SignalType constant = CONSTANTS.get(value);
      if (constant == null) {
        throw new IllegalArgumentException(value);
      } else {
        return constant;
      }
    }

  }


}
