package exchange;

import java.util.ArrayList;
import java.util.List;

/**
 * The class Exchange allows to convert an amount in euro.
 * 
 * Version 5.
 */

public class Exchange {

	/*
	 * This version uses the exceptions for a better treatment of exceptional
	 * cases.
	 * It uses a package for a better encapsulation.
	 * 
	 * Some problems of the previous version are still present :
	 * - problem on the effenciency of the method searchCurrency(String) ;
	 * - it is always possible to modify the rate of a currency, when it would be
	 * some time impossible.
	 */

	private static final List<Currency> CURRENCIES = new ArrayList<Currency>();

	static {
		String[] names = { "Franc", "Mark", "Dollard", "Euro" };
		double[] rates = { 6.55957, 1.95583, 1.2713, 1. };

		for (int i = 0; i < names.length; ++i) {
			try {
				addCurrency(names[i], rates[i]);
			} catch (ExistingCurrencyException e) {
				throw new Error(e.getMessage());
			}
		}
	}

	public static Currency currency(String name)
			throws UnknownCurrencyException {
		for (Currency c : CURRENCIES) {
			if (c.name().equals(name)) {
				return c;
			}
		}
		throw new UnknownCurrencyException(name);
	}

	public static boolean processedCurrency(String name) {
		try {
			currency(name);
			return true;
		} catch (UnknownCurrencyException e) {
			return false;
		}
	}

	public static Currency[] processedCurrencies() {
		return CURRENCIES.toArray(new Currency[CURRENCIES.size()]);
	}

	public static void addCurrency(String name, double exchangeRate)
			throws ExistingCurrencyException {
		try {
			Currency c = currency(name);
			throw new ExistingCurrencyException(c);
		} catch (UnknownCurrencyException e) {
			CURRENCIES.add(new Currency(name, exchangeRate));
		}
	}
}
