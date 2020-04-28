package com.elementsin.insuranceproduct.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.elementsin.insuranceproduct.model.value.Money;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter @Setter
@Builder
@Value
public class InsuranceModuleDto {

    private String id;
    @NotNull(message = "module name can not be null")
    @lombok.NonNull
    private String moduleName;
    @NotNull(message = "min coverage can not be null")
    @lombok.NonNull
    @PositiveOrZero(message = "min coverage must be greater than or equal to zero")
    private Money minCoverage;
    @NotNull(message = "max coverage can not be null")
    @lombok.NonNull
    @Positive(message = "max coverage must be greater than zero")
    private Money maxCoverage;
    @NotNull(message = "risk can not be null")
    @lombok.NonNull
    @Positive(message = "risk can not be zero or less!")
    private Double risk;
}
