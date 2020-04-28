package com.elementsin.insuranceproduct.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.elementsin.insuranceproduct.model.value.Money;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "InsuranceModules")
// since all the attributes required , It will make code smell If I defined a constructor with them In case of any additional attributes will be required
// so It's better using builder Pattern with lombok and make sure that every attribute will be initialized while building the object
@Builder
public class InsuranceModule {
    @Id
    private String id;
    @NotNull(message = "module name can not be null")
    @lombok.NonNull
    //It's a good idea to make index for this attribute because It's very likely to be very demanded
    //so this will enhance query performance
    @Indexed(unique = true)
    private String moduleName;
    @NotNull(message = "min coverage can not be null")
    // this annotation make lombok builder fail to compile
    // if the attribute has not been set and left with null
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
