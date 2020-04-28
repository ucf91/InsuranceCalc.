package com.elementsin.insuranceproduct.configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import com.elementsin.insuranceproduct.changesets.DatabaseChangelog;
import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Configuration
@EnableMongoAuditing
public class Configurations {
	@Value("${spring.data.mongodb.uri}")
	private String MONGO_URI;
	@Value("${spring.data.mongodb.database}")
	private String SPRING_BOOT_MONGO_DB;

	@Bean("mongock-spring-boot")
	public Mongock mongockSpringBoot() {
		MongoClient mongoclient = new MongoClient(new MongoClientURI(MONGO_URI));
		return new SpringMongockBuilder(mongoclient, SPRING_BOOT_MONGO_DB, DatabaseChangelog.class.getPackage().getName())
				.setLockQuickConfig()
				.build();
	}

	@Bean(name = "fireAndForgetExecutor")
	public Executor asyncExecutor(){
		Executor executor = Executors.newCachedThreadPool();
		return executor;
	}

}
