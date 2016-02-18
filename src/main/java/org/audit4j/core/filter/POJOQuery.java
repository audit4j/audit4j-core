package org.audit4j.core.filter;

import java.lang.reflect.Method;

/**
 * The Class POJOQuery.
 * 
 * @param <O>
 *            the generic type
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class POJOQuery<O> {

	/** The obj. */
	private O obj;

	/** The at query. */
	private AtQuery atQuery;

	/** The field string. */
	private String fieldString;

	/** The object field. */
	private Object objectField;

	/** The type. */
	private Type type;

	/** The result. */
	private boolean result = false;

	/** The operator. */
	private Operator operator = Operator.OR;

	/**
	 * The Enum Type.
	 * 
	 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
	 */
	private enum Type {

		/** The numeric. */
		NUMERIC,
		/** The text. */
		TEXT,
		/** The boolean. */
		BOOLEAN,
		/** The datetime. */
		DATETIME,
		/** The complex. */
		COMPLEX;
	}

	/**
	 * The Enum Operator.
	 * 
	 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
	 */
	private enum Operator {
		/** The and. */
		AND,
		/** The or. */
		OR;
	}

	/**
	 * From.
	 * 
	 * @param obj
	 *            the obj
	 * @return the at query
	 */
	public AtQuery from(O obj) {
		this.obj = obj;
		atQuery = new AtQuery();
		return atQuery;
	}

	/**
	 * The Class AtQuery.
	 * 
	 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
	 */
	public class AtQuery {

		/** The field. */
		String field;

		/**
		 * With.
		 * 
		 * @param field
		 *            the field
		 * @return the at query
		 */
		public AtQuery with(String field) {
			String getterName = "get" + Character.toUpperCase(field.charAt(0)) + field.substring(1);
			try {
				@SuppressWarnings("unchecked")
				Method method = obj.getClass().getMethod(getterName);
				objectField = method.invoke(obj, (Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (objectField instanceof String) {
				fieldString = objectField.toString();
				type = Type.TEXT;
			}
			return atQuery;
		}

		/**
		 * And.
		 * 
		 * @return the at query
		 */
		public AtQuery and() {
			operator = Operator.AND;
			return atQuery;
		}

		/**
		 * Or.
		 * 
		 * @return the filter query
		 */
		public AtQuery or() {
			operator = Operator.OR;
			return atQuery;
		}

		/**
		 * Not.
		 *
		 * @param result the result
		 * @return the at query
		 */
		public AtQuery not(boolean result) {
			result = !result;
			return atQuery;
		}

		/**
		 * Eq.
		 * 
		 * @param exp
		 *            the exp
		 * @return the at query
		 */
		public AtQuery eq(String exp) {
			if (operaterProceed() && Type.TEXT.equals(type)) {
					result = fieldString.equals(exp);
			}
			return atQuery;
		}

		/**
		 * Checks if is null.
		 * 
		 * @return the at query
		 */
		public AtQuery isNull() {
			if (operaterProceed() && (Type.TEXT.equals(type) || Type.NUMERIC.equals(type) || Type.BOOLEAN.equals(type)
					|| Type.COMPLEX.equals(type) || Type.DATETIME.equals(type))) {
				result = null == fieldString;
			}
			return atQuery;
		}

		/**
		 * Contains.
		 * 
		 * @param exp
		 *            the exp
		 * @return the at query
		 */
		public AtQuery contains(String exp) {
			if (operaterProceed() && Type.TEXT.equals(type)) {
				result = fieldString.contains(exp);
			}
			return atQuery;
		}

		// TODO
		/**
		 * Contains.
		 * 
		 * @param exp
		 *            the exp
		 * @return the at query
		 */
		public AtQuery lt(String exp) {
			return atQuery;
		}

		// TODO
		/**
		 * Contains.
		 * 
		 * @param exp
		 *            the exp
		 * @return the at query
		 */
		public AtQuery gt(String exp) {
			return atQuery;
		}

		// TODO
		/**
		 * Contains.
		 * 
		 * @return the at query
		 */
		public AtQuery isNumeric() {
			return atQuery;
		}

		// TODO
		/**
		 * Contains.
		 * 
		 * @return the at query
		 */
		public AtQuery isText() {
			return atQuery;
		}

		// TODO
		/**
		 * Contains.
		 * 
		 * @return the at query
		 */
		public AtQuery isEmail() {
			return atQuery;
		}

		// TODO
		/**
		 * Regex.
		 * 
		 * @param exp
		 *            the exp
		 * @return the at query
		 */
		public AtQuery regex(String exp) {
			return atQuery;
		}

		// TODO
		/**
		 * Startswith.
		 * 
		 * @param exp
		 *            the exp
		 * @return the at query
		 */
		public AtQuery startswith(String exp) {
			return atQuery;
		}

		// TODO
		/**
		 * Endswith.
		 * 
		 * @param exp
		 *            the exp
		 * @return the at query
		 */
		public AtQuery endswith(String exp) {
			return atQuery;
		}

		// TODO
		/**
		 * Char count.
		 * 
		 * @return the counting
		 */
		public Counting charCount() {
			return null;
		}

		/**
		 * Operater proceed.
		 * 
		 * @return true, if successful
		 */
		public boolean operaterProceed() {
			if (operator.equals(Operator.AND) && !result) {
				return false;
			} else if (operator.equals(Operator.OR) && result) {
				return false;
			}
			return true;
		}

		/**
		 * Evaluate.
		 * 
		 * @return true, if successful
		 */
		public boolean evaluate() {
		    atQuery = null;
			return result;
		}
	}

	/**
	 * The Class Counting.
	 * 
	 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
	 */
	public class Counting {
		// TODO
		/**
		 * Gt.
		 * 
		 * @param exp
		 *            the exp
		 * @return the at query
		 */
		public AtQuery gt(String exp) {
			return atQuery;
		}

		// TODO
		/**
		 * Lt.
		 * 
		 * @param exp
		 *            the exp
		 * @return the at query
		 */
		public AtQuery lt(String exp) {
			return atQuery;
		}
	}

}
