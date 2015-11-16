package com.affecto.stats;

import com.affecto.stats.services.AdminService;
import com.affecto.stats.services.RenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
public class Application extends SpringBootServletInitializer {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RenderService renderService;

    @RequestMapping("/")
    public String index(final Model model) {
        model.addAllAttributes(adminService.entitiesForView());
        return "index";
    }

    @RequestMapping("/{username:[a-z0-9]+}")
    public String user(final Model model, @PathVariable("username") final String username) {
        model.addAllAttributes(adminService.entitiesForView(username));
        return "user";
    }

    @RequestMapping("/{username:[a-z0-9]+}/{dataSourceName:[a-z0-9]+}")
    public String dataSource(final Model model,
                             @PathVariable("username") final String username,
                             @PathVariable("dataSourceName") final String dataSourceName)
    {
        model.addAllAttributes(adminService.entitiesForView(username, dataSourceName));
        return "datasource";
    }

    @RequestMapping("/{username:[a-z0-9]+}/{dataSourceName:[a-z0-9]+}/{queryName:[a-z0-9]+}")
    public String query(final Model model,
                        @PathVariable("username") final String username,
                        @PathVariable("dataSourceName") final String dataSourceName,
                        @PathVariable("queryName") final String queryName)
    {
        model.addAllAttributes(adminService.entitiesForView(username, dataSourceName, queryName));
        return "query";
    }

    @RequestMapping("/_/api/{username:[a-z0-9]+}/{dataSourceName:[a-z0-9]+}/{queryName:[a-z0-9]+}")
    @ResponseBody
    public String apiRenderQuery(final Model model,
                        @PathVariable("username") final String username,
                        @PathVariable("dataSourceName") final String dataSourceName,
                        @PathVariable("queryName") final String queryName)
    {
        return renderService.renderQuery(username, dataSourceName, queryName);
    }

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
