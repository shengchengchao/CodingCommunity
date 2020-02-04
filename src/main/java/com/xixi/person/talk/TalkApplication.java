package com.xixi.person.talk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.xixi.person.talk.mapper")
@EnableCaching
public class TalkApplication {

	public static void main(String[] args) {
		SpringApplication.run(TalkApplication.class, args);
	}

}
