package com.superflower.common.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class CommonImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {
                GlobalExceptionHandle.class.getName(),
                SwaggerConfig.class.getName(),
                ValidatorConfig.class.getName(),
                MybatisPlusConfig.class.getName(),
                GraphicsConfig.class.getName()
        };
    }
}
