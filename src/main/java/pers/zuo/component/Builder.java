package pers.zuo.component;

import java.util.Objects;

/**
 * @author 左金剛
 * @Title: Builder
 * @Description: This is a common builder to create a bean,
 * the generic type must have a default construct,
 * otherwise will throw a exception
 * @date 2019/5/20 18:59
 */
public class Builder<T> {

    private Class<T> clazz;

    private ValidFields<T> validFields;

    private Builder(Class<T> clazz) {
        this.clazz = clazz;
    }

    private Builder(Class<T> clazz, ValidFields<T> validFields) {
        this.clazz = clazz;
        this.validFields = validFields;
    }

    public static <T> Builder<T> create(Class<T> clazz) {
        return new Builder<>(clazz);
    }

    public static <T> Builder<T> create(Class<T> clazz, ValidFields<T> validFields) {
        return new Builder<>(clazz, validFields);
    }

    public Builder<T> addValid(ValidFields<T> valid){
        this.validFields = valid;
        return this;
    }

    public T build(FillField<T> fillField) {
        T instance = null;
        try {

            instance = this.clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {

            throw new RuntimeException(e.getMessage());
        }
        fillField.fill(instance);

        if (Objects.isNull(validFields)){

            return instance;
        }
        boolean validRet = this.validFields.valid(instance);
        if (validRet){

            return instance;
        }
        throw new RuntimeException(String.format("instance fields valid error, Class is %s", instance.getClass().getName()));
    }

    public interface FillField<T> {

        void fill(T instance);
    }

    public interface ValidFields<T> {

        boolean valid(T instanse);
    }

}
