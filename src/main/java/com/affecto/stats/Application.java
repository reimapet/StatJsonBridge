package com.affecto.stats;

import com.affecto.stats.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class Application extends SpringBootServletInitializer {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/")
    public String index(final Model model) {
        model.addAttribute("");
        return "index";
    }

    @RequestMapping("/{user:[a-z0-9]+}")
    public String user(final Model model, @PathVariable("user") final String user) {
        return "user";
    }

    @RequestMapping("/{user:[a-z0-9]+}/{dataSource:[a-z0-9]+}")
    public String dataSource(final Model model,
                             @PathVariable("user") final String user,
                             @PathVariable("dataSource") final String dataSource)
    {
        return "datasource";
    }

    @RequestMapping("/{user:[a-z0-9]+}/{dataSource:[a-z0-9]+}/{query:[a-z0-9]+}")
    public String query(final Model model,
                        @PathVariable("user") final String user,
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
