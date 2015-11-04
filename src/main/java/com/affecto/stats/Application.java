package com.affecto.stats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
@Controller
public class Application extends SpringBootServletInitializer {

    @RequestMapping("/")
    public String index(final Model model, final HttpServletRequest req) {
        return "index";
    }

    @RequestMapping("/{user:[a-z0-9]+}")
    @ResponseBody
    public String user(@PathVariable("user") String user) {
        return "User";
    }

    @RequestMapping("/{user:[a-z0-9]+}/{dataSource:[a-z0-9]+}")
    @ResponseBody
    public String dataSource(@PathVariable("user") String user, @PathVariable("dataSource") String dataSource) {
        return "dataSource";
    }

    @RequestMapping("/{user:[a-z0-9]+}/{dataSource:[a-z0-9]+}/{query:[a-z0-9]+}")
    @ResponseBody
    public String query(@PathVariable("user") final String user,
                        @PathVariable("dataSource") final String dataSource,
                        @PathVariable("query") final String query)
    {
        return "query";
    }

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
