package pers.zuo.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TupleUtil {

	private static String defaultSplit = "@";

	public static final short classTypeString = 0;

	public static final short classTypeInteger = 1;

	public static final short classTypeDate = 2;

	public static Range<?> transform2Range(String target, short type) {
		return transform2Range(target, type, DateUtil.ymdhmsDash.get());
	}

	public static Range<?> transform2Range(String target, short type, SimpleDateFormat dateFormat) {
		return transform2Range(target, defaultSplit, type, dateFormat);
	}

	public static Range<?> transform2Range(String target, String split, short type) {
		return transform2Range(target, split, type, DateUtil.ymdhmsDash.get());
	}

	public static Range<?> transform2Range(String target, String split, short type, SimpleDateFormat dateFormat) {
		Tuple<?, ?> transform = transform(target, split, type, type, dateFormat);
		if (transform instanceof Range) {
			return (Range<?>) transform;
		}
		return (Range<?>) Range.empty();
	}

	public static Tuple<?, ?> transform(String target, short leftType, short rightType) {
		return transform(target, defaultSplit, leftType, rightType, DateUtil.ymdhmsDash.get());
	}

	public static Tuple<?, ?> transform(String target, String split, short leftType, short rightType) {
		return transform(target, split, leftType, rightType, DateUtil.ymdhmsDash.get());
	}

	public static Tuple<?, ?> transform(String target, String split, short leftType, short rightType,
			SimpleDateFormat dateFormat) {

		if (null == target || null == split) {
			return Tuple.empty();
		}
		if ((classTypeDate == leftType || classTypeDate == rightType) && null == dateFormat) {
			return Tuple.empty();
		}

		String[] splits = target.split(split, 2);
		String leftStr = splits[0];
		String rightStr = splits.length == 2 ? splits[1] : null;

		Integer leftInt;
		Integer rightInt;
		Date leftDate = null;
		Date rightDate = null;

		switch (leftType) {
		case classTypeInteger:
			leftInt = StringUtil.isEmpty(leftStr) ? null : Integer.parseInt(leftStr);
			switch (rightType) {
			case classTypeInteger:
				rightInt = StringUtil.isEmpty(rightStr) ? null : Integer.parseInt(rightStr);
				if (null != leftInt && null != rightInt && leftInt <= rightInt) {
					return new Range<Integer>(leftInt, rightInt);
				}
				return new Tuple<Integer, Integer>(leftInt, rightInt);
			case classTypeString:
				return new Tuple<Integer, String>(leftInt, rightStr);
			case classTypeDate:
				rightDate = StringUtil.isEmpty(rightStr) ? null : DateUtil.toDate(rightStr, dateFormat);
				return new Tuple<Integer, Date>(leftInt, rightDate);
			default:
				return Tuple.empty();
			}
		case classTypeString:
			switch (rightType) {
			case classTypeInteger:
				rightInt = StringUtil.isEmpty(rightStr) ? null : Integer.parseInt(rightStr);
				return new Tuple<String, Integer>(leftStr, rightInt);
			case classTypeString:
				if (null != leftStr && null != rightStr && leftStr.compareTo(rightStr) <= 0) {
					return new Range<String>(leftStr, rightStr);
				}
				return new Tuple<String, String>(leftStr, rightStr);
			case classTypeDate:
				rightDate = StringUtil.isEmpty(rightStr) ? null : DateUtil.toDate(rightStr, dateFormat);
				return new Tuple<String, Date>(leftStr, rightDate);
			default:
				return Tuple.empty();
			}
		case classTypeDate:
			leftDate = StringUtil.isEmpty(leftStr) ? null : DateUtil.toDate(leftStr, dateFormat);
			switch (rightType) {
			case classTypeInteger:
				rightInt = StringUtil.isEmpty(rightStr) ? null : Integer.parseInt(rightStr);
				return new Tuple<Date, Integer>(leftDate, rightInt);
			case classTypeString:
				return new Tuple<Date, String>(leftDate, rightStr);
			case classTypeDate:
				rightDate = StringUtil.isEmpty(rightStr) ? null : DateUtil.toDate(rightStr, dateFormat);
				if (null != leftDate && null != rightDate && leftDate.compareTo(rightDate) <= 0) {
					return new Range<Date>(leftDate, rightDate);
				}
				return new Tuple<Date, Date>(leftDate, rightDate);
			default:
				return Tuple.empty();
			}

		default:
			return Tuple.empty();
		}
	}

	public static class Range<T> extends Tuple<T, T> {

		private static final long serialVersionUID = -2722494530401667541L;

		public Range(T left, T right) {
			super(left, right);
		}
	}

	public static class Tuple<L, R> implements Serializable {

		private static final long serialVersionUID = 7170206477023010970L;

		private final L left;

		private final R right;

		public Tuple(L left, R right) {
			super();
			this.left = left;
			this.right = right;
		}

		public L getLeft() {
			return left;
		}

		public R getRight() {
			return right;
		}

		public static <L, R> Tuple<L, R> empty() {
			return new Tuple<L, R>(null, null);
		}

		public boolean isEmpty() {
			return this.equals(empty());
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((left == null) ? 0 : left.hashCode());
			result = prime * result + ((right == null) ? 0 : right.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Tuple<?, ?> other = (Tuple<?, ?>) obj;
			if (left == null) {
				if (other.left != null)
					return false;
			} else if (!left.equals(other.left))
				return false;
			if (right == null) {
				if (other.right != null)
					return false;
			} else if (!right.equals(other.right))
				return false;
			return true;
		}
	}
}
