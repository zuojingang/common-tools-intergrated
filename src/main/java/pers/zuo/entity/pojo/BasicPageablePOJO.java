package pers.zuo.entity.pojo;

import pers.zuo.entity.request.Pagable;

import java.io.*;

/**
 * @author zuojingang
 * @Title: BasicPageablePOJO
 * @Description: Todo
 * @date 2020/6/4 15:28
 */
public class BasicPageablePOJO<T> extends Pagable implements Serializable, Cloneable {

    private static final long serialVersionUID = 3256019514711723192L;

    @Override
    public T clone() {
        T clone = null;
        try {
            ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
            ObjectOutputStream objectOS = new ObjectOutputStream(byteOS);
            objectOS.writeObject(this);
            ByteArrayInputStream byteIS = new ByteArrayInputStream(byteOS.toByteArray());
            ObjectInputStream objectIS = new ObjectInputStream(byteIS);
            clone = (T) objectIS.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return clone;
    }
}
