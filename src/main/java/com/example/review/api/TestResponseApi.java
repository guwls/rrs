package com.example.review.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestResponseApi {

    @GetMapping("/test/response/string")
    public String stringResponse() {
        return "This is String";
    }

    @GetMapping("test/response/json")
    public TestRespoonseBody jsonResponse() {
        return new TestRespoonseBody("hyunjin", 27);
    }

    public static class TestRespoonseBody {
        String name;
        Integer age;

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }

        public TestRespoonseBody(String name, Integer age) {
            this.name = name;
            this.age = age;
        }
    }
}
