package pers.zuo.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.commons.beanutils.BeanUtils.copyProperties;
import static org.apache.commons.beanutils.BeanUtils.copyProperty;

/**
 * @author zuojingang
 * @Title: TransformUtils
 * @Description: Todo
 * @date 2019-09-18 11:02
 */
public class TransformUtil {

    /**
     * 逐个拷贝给定的List originals 至 目标类targetClass 的实例中，并组装List返回
     * @param originals
     * @param targetClass
     * @param <Original>
     * @param <Target>
     * @return
     */
    public static <Original, Target> List<Target> listCopy2(final List<Original> originals, final Class<Target> targetClass) {
        if (CollectionUtils.isEmpty(originals)){
            return Collections.emptyList();
        }
        return originals.stream().map(po -> copy2(po,targetClass)).collect(Collectors.toList());
    }

    /**
     * 将源对象original 的属性值拷贝至 目标类targetClass 的实例中
     * @param original
     * @param targetClass
     * @param <Original>
     * @param <Target>
     * @return
     */
    public static <Original, Target> Target copy2(Original original, Class<Target> targetClass){
        if (Objects.isNull(original) || Objects.isNull(targetClass)) {
            return null;
        }
        Target target = null;
        try {
            target = targetClass.newInstance();
            copyProperties(target, original);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 将源对象original 的属性值拷贝至 目标对象target 中
     * @param original
     * @param target
     * @param <Original>
     * @param <Target>
     */
    public static <Original, Target> void copy2(Original original, Target target) {
        if (Objects.isNull(original) || Objects.isNull(target)) {
            return;
        }
        try {
            copyProperties(target, original);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method will ignore null field of origin or any field in origin but target doesn't contain.
     *
     * @param original
     * @param target
     * @param <Original>
     * @param <Target>
     */
    public static <Original, Target> void copy2IgnoreNull(Original original, Target target) {
        if (Objects.isNull(original) || Objects.isNull(target)) {
            return;
        }
        try {
            List<Field> fields = ReflectUtils.getAllFieldsThenSetAccessible(original.getClass());
            List<String> targetFieldNames = ReflectUtils.getAllFieldsName(target.getClass());
            for (Field field : fields) {
                int fieldModifiers = field.getModifiers();
                String fieldName = field.getName();
                Object fieldVal = field.get(original);
                if (Modifier.isStatic(fieldModifiers) || Objects.isNull(fieldVal) || !targetFieldNames.contains(fieldName)) {
                    continue;
                }
                copyProperty(target, fieldName, fieldVal);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
