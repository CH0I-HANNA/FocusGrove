package com.focusglove.api;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		// .env 파일 로드 및 시스템 프로퍼티로 설정
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing() // 파일이 없어도 에러 내지 않음 (운영 환경 대비)
				.load();

		dotenv.entries().forEach(entry ->
				System.setProperty(entry.getKey(), entry.getValue())
		);
		SpringApplication.run(Application.class, args);
	}

}
