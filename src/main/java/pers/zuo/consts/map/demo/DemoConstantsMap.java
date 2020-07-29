package pers.zuo.consts.map.demo;

import com.google.common.collect.Lists;
import pers.zuo.consts.map.BaseConstantsBiMap;

import java.util.List;

/**
 * @author zuojingang
 * @Title: DemoConstantsMap
 * @Description: demo
 * @date 2020/7/9 17:55
 */
public class DemoConstantsMap extends BaseConstantsBiMap<Integer, String> {

    @Override
    public List<CodeNamePair<Integer, String>> constructPairs() {
        return Lists.newArrayList(CodeNamePair.of(1, "DEMO"));
    }

    @Override
    public CodeNamePair<Integer, String> unknownPair() {
        return CodeNamePair.of(-1, "UNKNOWN");
    }
}