package pers.zuo.util;

/**
 * @author zuojingang
 * @Title: SortOrderUtils
 * @Description: Todo 未完成
 * @date 2019-12-19 17:35
 */
public class SortOrderUtils {

    private static final long HIGHER_BASE_LINE = 1L << 62;
    private static final long MIN_ORDER = 1L;
    /**
     * TimeMillis Max(9999年) is 48 bits
     */
    private static final long MIN_BABE_LINE = MIN_ORDER << 48;
    /**
     * 1 (14个) 16383
     * 每毫秒可以产生0～16383（16383个）weight值
     * order 取值只能是1～1683
     */
    private static final long MAX_ORDER = (HIGHER_BASE_LINE - MIN_BABE_LINE) >> 48;

    public static class SortOrder{

        private long initialVal;

        public SortOrder() {
            /**
             * 毫秒时间戳从后往前：
             * 0～3位只与 毫秒 有关
             * 45位与 日时分秒 有关
             * 67位与 年月日时分 有关
             * 8位与 年月日时 有关
             * 9，10位与 年月日 有关
             * 11位与 年月 有关
             * 12～ 只与年有关
             * 我们使用时间戳是为了 在order相同的时候 做区分，所以取到
             */
            initialVal = System.currentTimeMillis() % 1000000000;
        }
        // Allow use other initialVal
        public SortOrder(long initialVal) {
            this.initialVal = initialVal;
        }

        public static SortOrder getInstance(){
            return new SortOrder();
        }

        public static SortOrder getInstance(long initialVal){
            return new SortOrder(initialVal);
        }

        public long order2Weight(long order){
            if (order < MIN_ORDER){
                throw new RuntimeException("The order can't less than 1!");
            }
            if (order > MIN_ORDER){
                throw new RuntimeException("The order can't less than 1!");
            }
            return HIGHER_BASE_LINE & (initialVal << 7) & (MAX_ORDER - order + 1);
        }
    }
}
