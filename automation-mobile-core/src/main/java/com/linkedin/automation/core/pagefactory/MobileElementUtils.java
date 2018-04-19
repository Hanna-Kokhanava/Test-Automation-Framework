package com.linkedin.automation.core.pagefactory;

import com.linkedin.automation.core.annotation.Name;
import com.linkedin.automation.page_elements.block.MobileBlock;
import com.linkedin.automation.page_elements.element.WrappedMobileElement;
import org.apache.commons.lang3.text.WordUtils;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Function;

/**
 * Contains utility methods used in decorator.
 */
public class MobileElementUtils {

    public static boolean isWebElement(Class<?> clazz) {
        return WebElement.class.isAssignableFrom(clazz);
    }

    public static boolean isWrappedMobileElement(Class<?> clazz) {
        return WrappedMobileElement.class.isAssignableFrom(clazz);
    }

    public static boolean isBlockElement(Class<?> clazz) {
        return MobileBlock.class.isAssignableFrom(clazz);
    }

    public static boolean isListOf(Field field, Function<Class, Boolean> classCheckFunction) {
        if (!isParametrizedList(field)) {
            return false;
        }
        Class listParameterClass = getGenericParameterClass(field);
        return classCheckFunction.apply(listParameterClass);
    }

    public static Class getGenericParameterClass(Field field) {
        Type genericType = field.getGenericType();
        return (Class) ((ParameterizedType) genericType).getActualTypeArguments()[0];
    }

    private static boolean isParametrizedList(Field field) {
        return List.class.isAssignableFrom(field.getType()) && field.getGenericType() instanceof ParameterizedType;
    }

    public static String getElementName(Field field) {
        if (field.isAnnotationPresent(Name.class)) {
            return field.getAnnotation(Name.class).value();
        }
        if (field.getType().isAnnotationPresent(Name.class)) {
            return field.getType().getAnnotation(Name.class).value();
        }
        return splitCamelCase(field.getName());
    }

    private static String splitCamelCase(String camel) {
        return WordUtils.capitalizeFully(camel.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        ));
    }
}
