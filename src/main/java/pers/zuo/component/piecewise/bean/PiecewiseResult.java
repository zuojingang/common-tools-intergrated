package pers.zuo.component.piecewise.bean;

public class PiecewiseResult<T> {

	private final T val;
	private Exception exception;

	public PiecewiseResult(T val) {
		super();
		this.val = val;
	}

	public PiecewiseResult(T val, Exception exception) {
		super();
		this.val = val;
		this.exception = exception;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public T getVal() {
		return val;
	}

	public static <T> PiecewiseResult<T> with(T val) {
		return new PiecewiseResult<T>(val);
	}

	public static <T> PiecewiseResult<T> with(T val, Exception exception) {
		return new PiecewiseResult<T>(val, exception);
	}
}
