package pers.zuo.util;

import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.commons.beanutils.BeanUtils.copyProperties;

/**
 * @author zuojingang
 * @Title: TransformUtils
 * @Description: Todo
 * @date 2019-09-18 11:02
 */
public class TransformUtil {

    public static <Original, Target> List<Target> listCopy2(final List<Original> originals, final Class<Target> targetClass) {
        if (CollectionUtils.isEmpty(originals)){
            return Collections.emptyList();
        }
        return originals.stream().map(po -> copy2(po,targetClass)).collect(Collectors.toList());
    }

    public static <Original, Target> Target copy2(Original original, Class<Target> targetClass){
        if (Objects.isNull(original)){
            return null;
        }
        Target target = null;
        try {
            target = targetClass.newInstance();
            copyProperties(target, original);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return target;
    }
}
