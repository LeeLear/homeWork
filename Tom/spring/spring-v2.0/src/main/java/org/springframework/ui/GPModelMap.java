package com.gupaoedu.vip.framework.ui;




import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.gupaoedu.vip.framework.core.Conventions;
import com.gupaoedu.vip.framework.lang.Nullable;
import com.gupaoedu.vip.framework.util.Assert;


/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/14 20:35
 * @description：
 * @modified By：
 * @version: $
 */
public class GPModelMap extends LinkedHashMap<String, Object> {
    /**
     * Add the supplied attribute under the supplied name.
     * @param attributeName the name of the model attribute (never {@code null})
     * @param attributeValue the model attribute value (can be {@code null})
     */
    public GPModelMap addAttribute(String attributeName, @Nullable Object attributeValue) {
        Assert.notNull(attributeName, "Model attribute name must not be null");
        put(attributeName, attributeValue);
        return this;
    }

    /**
     * Add the supplied attribute to this {@code Map} using a
     * {@link com.gupaoedu.vip.framework.core.Conventions#getVariableName generated name}.
     * <p><emphasis>Note: Empty {@link Collection Collections} are not added to
     * the model when using this method because we cannot correctly determine
     * the true convention name. View code should check for {@code null} rather
     * than for empty collections as is already done by JSTL tags.</emphasis>
     * @param attributeValue the model attribute value (never {@code null})
     */
    public GPModelMap addAttribute(Object attributeValue) {
        Assert.notNull(attributeValue, "Model object must not be null");
        if (attributeValue instanceof Collection && ((Collection<?>) attributeValue).isEmpty()) {
            return this;
        }
        return addAttribute(Conventions.getVariableName(attributeValue), attributeValue);
    }
    /**
     * Copy all attributes in the supplied {@code Collection} into this
     * {@code Map}, using attribute name generation for each element.
     * @see #addAttribute(Object)
     */
    public GPModelMap addAllAttributes(@Nullable Collection<?> attributeValues) {
        if (attributeValues != null) {
            for (Object attributeValue : attributeValues) {
                addAttribute(attributeValue);
            }
        }
        return this;
    }

    /**
     * Copy all attributes in the supplied {@code Map} into this {@code Map}.
     * @see #addAttribute(String, Object)
     */
    public GPModelMap addAllAttributes(@Nullable Map<String, ?> attributes) {
        if (attributes != null) {
            putAll(attributes);
        }
        return this;
    }
}
