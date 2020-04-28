package com.elementsin.insuranceproduct.dto;

import java.time.LocalDateTime;

import com.elementsin.insuranceproduct.model.value.Money;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Value
public class PricesLogDto {
	private String id;
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime timestamp;
	@lombok.NonNull
	private String moduleName;
	@lombok.NonNull
	private Money selectedCoverage;
	@lombok.NonNull
	private Money calculatedPrice;
}
