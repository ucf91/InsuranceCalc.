package com.elementsin.insuranceproduct.changesets;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.elementsin.insuranceproduct.model.InsuranceModule;
import com.elementsin.insuranceproduct.model.value.Money;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;

@ChangeLog
public class DatabaseChangelog {

	@ChangeSet(order = "001", id = "insert_bike_module", author = "youssef")
	public void insertBikeModule(MongoTemplate mongoTemplate){
		InsuranceModule insuranceModule = InsuranceModule.builder()
				.moduleName("Bike")
				.risk(30.0)
				.minCoverage(Money.of(0))
				.maxCoverage(Money.of(3000))
				.build();
		mongoTemplate.save(insuranceModule);
	}

	@ChangeSet(order = "002", id = "insert_jewelry_module", author = "youssef")
	public void insertJewelryModule(MongoTemplate mongoTemplate){
		InsuranceModule insuranceModule = InsuranceModule.builder()
				.moduleName("Jewelry")
				.risk(5.0)
				.minCoverage(Money.of(500))
				.maxCoverage(Money.of(10000))
				.build();
		mongoTemplate.save(insuranceModule);
	}

	@ChangeSet(order = "003", id = "inasert_Electronics_module", author = "youssef")
	public void insertElectronicsModule(MongoTemplate mongoTemplate){
		InsuranceModule insuranceModule = InsuranceModule.builder()
				.moduleName("Electronics")
				.risk(35.0)
				.minCoverage(Money.of(500))
				.maxCoverage(Money.of(6000))
				.build();
		mongoTemplate.save(insuranceModule);
	}

	@ChangeSet(order = "004", id = "insert_Sports_Equipment_module", author = "youssef")
	public void insertSportsEquipmentModule(MongoTemplate mongoTemplate){
		InsuranceModule insuranceModule = InsuranceModule.builder()
				.moduleName("Sports Equipment")
				.risk(30.0)
				.minCoverage(Money.of(0))
				.maxCoverage(Money.of(20000))
				.build();
		mongoTemplate.save(insuranceModule);
	}



}
