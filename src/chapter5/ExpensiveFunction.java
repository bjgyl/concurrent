package chapter5;

import java.math.BigInteger;

/**
 * P86 5-16��ʾһ������ļ�������
 * @author skywalker
 *
 */
public class ExpensiveFunction implements Computable<BigInteger, String> {

	@Override
	public BigInteger compute(String arg) {
		return new BigInteger(arg);
	}

}
