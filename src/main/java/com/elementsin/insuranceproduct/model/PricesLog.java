package com.elementsin.insuranceproduct.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.elementsin.insuranceproduct.model.value.Money;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "PricesLog")
@Builder
public class PricesLog {
    @Id
    private String id;
    @CreatedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime timestamp;
    @lombok.NonNull
    private String moduleName;
    @lombok.NonNull
    private Money selectedCoverage;
    @lombok.NonNull
    private Money calculatedPrice;
}
