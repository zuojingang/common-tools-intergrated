package pers.zuo.consts.map;

import pers.zuo.entity.pojo.BasicPOJO;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author zuojingang
 * @Title: BaseConstantsBiMap
 * @Description: base constants bidirectional map
 * @date 2020/7/28 18:15
 */
public abstract class BaseConstantsBiMap<Code, Name> {

    private final Map<Code, Name> codeNameMap = new HashMap<>();
    private final Map<Name, Code> nameCodeMap = new HashMap<>();

    public static <Code, Name, T extends BaseConstantsBiMap<Code, Name>> T instance() {
        T instance = null;
        try {
            Class<?> subClass = Class.forName(new Throwable().getStackTrace()[1].getClassName());
            instance = (T) subClass.getConstructors()[0].newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * construct pairs,
     * Neither code nor name is allowed to repeat
     *
     * @return
     */
    protected abstract List<CodeNamePair<Code, Name>> constructPairs();

    /**
     * construct the unknown pair
     *
     * @return
     */
    protected abstract CodeNamePair<Code, Name> unknownPair();

    {
        constructPairs().forEach(codeNamePair -> {

            this.codeNameMap.put(codeNamePair.code, codeNamePair.name);
            this.nameCodeMap.put(codeNamePair.name, codeNamePair.code);
        });
    }

    /**
     * get a copy of all pairs
     *
     * @return
     */
    public List<CodeNamePair<Code, Name>> pairs() {
        return constructPairs().stream().map(BasicPOJO::clone).collect(Collectors.toList());
    }

    /**
     * get a copy of the unknown pair
     *
     * @return
     */
    public CodeNamePair<Code, Name> unknown() {
        return unknownPair().clone();
    }

    /**
     * get name by code
     *
     * @param code
     * @return
     */
    public Name nameByCode(Code code) {
        return Optional.ofNullable(codeNameMap.get(code)).orElse(unknownPair().name);
    }

    /**
     * get code by name
     *
     * @param name
     * @return
     */
    public Code codeByName(Name name) {
        return Optional.ofNullable(nameCodeMap.get(name)).orElse(unknownPair().code);
    }

    public static class CodeNamePair<Code, Name> extends BasicPOJO<CodeNamePair<Code, Name>> {

        private static final long serialVersionUID = -4439777350911596418L;

        public final Code code;
        public final Name name;

        public CodeNamePair(Code code, Name name) {
            this.code = code;
            this.name = name;
        }

        public static <Code, Name> CodeNamePair<Code, Name> of(Code code, Name name) {
            return new CodeNamePair<>(code, name);
        }

        public static <Code, Name> CodeNamePair<Code, Name> empty() {
            return new CodeNamePair<>(null, null);
        }
    }
}
