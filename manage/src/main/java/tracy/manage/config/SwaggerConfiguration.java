package tracy.manage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;


@Configuration
public class SwaggerConfiguration {
    @Bean
    public Docket docket() {
        //这里要配置controller包
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("tracy.manage.controller"))
                .paths(PathSelectors.any()).build()
                .apiInfo(setApiInfo());
    }
    private ApiInfo setApiInfo() {
        //作者信息
        Contact contact = new Contact("tracy",
                "https://blog.csdn.net/Tracycoder?spm=1011.2415.3001.5343",
                "1409568085@qq.com");
        //项目描述
        ApiInfo info = new ApiInfo("Claimnet管理端 Restful Api", "", "v1.0",
                "https://blog.csdn.net/Tracycoder?spm=1011.2415.3001.5343", contact,
                "Apache 2.0", "", new ArrayList<VendorExtension>());
        return info;
    }
}
