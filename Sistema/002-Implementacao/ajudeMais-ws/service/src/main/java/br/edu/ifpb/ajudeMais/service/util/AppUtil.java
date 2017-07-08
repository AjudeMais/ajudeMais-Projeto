package br.edu.ifpb.ajudeMais.service.util;

import java.util.Collection;
import java.util.UUID;

public class AppUtil {

	/**
	 * Gets the uuid.
	 *
	 * @return the uuid
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Verifica se uma coleção esta vazia.
	 *
	 * @param collection
	 *            the collection
	 * @return true, if is collection empty
	 */
	private static boolean isCollectionEmpty(Collection<?> collection) {
		if (collection == null || collection.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * Verifica se um objeto é vazio
	 *
	 * @param object
	 */
	public static boolean isObjectEmpty(Object object) {
		if (object == null)
			return true;
		else if (object instanceof String) {
			if (((String) object).trim().length() == 0) {
				return true;
			}
		} else if (object instanceof Collection) {
			return isCollectionEmpty((Collection<?>) object);
		}
		return false;
	}
}
