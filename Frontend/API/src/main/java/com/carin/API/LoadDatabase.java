package com.carin.API;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository) {

        List<Integer> a = new LinkedList<>();
        a.add(1);
        a.add(2);

        List<Integer> b = new LinkedList<>();
        b.add(2);
        b.add(3);

        Map<String, Integer> mapped = new HashMap<>();
        mapped.put("abc",19);
        mapped.put("sss",29);

        return args -> {
            log.info("Preloading " + repository.save(new Employee("Bilbo Baggins", "burglar" , a,mapped)));
            log.info("Preloading " + repository.save(new Employee("Frodo Baggins", "thief" , b,mapped)));
        };

    }
}