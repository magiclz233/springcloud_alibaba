package com.magic.system.config;

import com.magic.security.constants.SecurityConstants;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author magic_lz
 * @version 1.0
 * @date 2020/9/2 22:44
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public Docket createRestApi() {
        ParameterBuilder builder = new ParameterBuilder();

        List<Parameter> pars = new ArrayList<>();

        builder.name(SecurityConstants.TOKEN_HEADER).description("user token")
                .modelRef(new ModelRef("string")).parameterType("header")
                // header 中的 ticket参数非必填, null也可以
                .required(false).build();
        // 根据每个方法名也知道当前方法设置什么参数
        pars.add(builder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.magic"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Security JWT Guide")
                .build();
    }
}
