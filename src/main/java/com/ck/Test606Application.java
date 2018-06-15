package com.ck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ResourceLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Test606Application {

	public static void main(String[] args) {
		SpringApplication.run(Test606Application.class, args);
	}
/*public static ConfigurableApplicationContext run(Object source, String[] args) {
	return run(new Object[] { source }, args);
}

	public static ConfigurableApplicationContext run(Object[] sources, String[] args) {
		List<Object> sourcesExt = new ArrayList<>(Arrays.asList(sources));
		Class[] objectClass = new Class[sources.length];
		for(int i=0;i<sources.length;i++){
			objectClass[i] = (Class) sources[i];
		}
		SpringApplication app = new SpringApplication(objectClass);
		return app.run(args);
	}

	public static void main(String[] args) throws InterruptedException {
		run(Test606Application.class, args);
	}*/
}
