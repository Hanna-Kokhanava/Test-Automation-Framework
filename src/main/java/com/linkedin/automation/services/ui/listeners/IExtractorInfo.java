package com.linkedin.automation.services.ui.listeners;

import java.util.Arrays;
import java.util.Objects;

public interface IExtractorInfo {
    default <Type> Type extractInfoByType(Class<Type> objectType, Object... additionalInfo) {
        Object infoObject = Arrays.stream(additionalInfo).
                filter(info -> info.getClass() == objectType).
                findFirst().
                orElse(null);
        return Objects.isNull(infoObject) ? null : objectType.cast(infoObject);
    }
}
