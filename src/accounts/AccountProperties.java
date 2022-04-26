package accounts;

import java.util.Random;

public enum AccountProperties {
	BRONZE(4.5f, 6, 5, 7.5f, 10000, 2500), SILVER(3, 4.5f, 3.8f, 5, 20000, 4000),
	GOLD(1.5f, 3, 1.75f, 3.8f, 50000, 6000), TITANIUM(0, 0, 0, 0, 100000, 100000);

	double minInterstRate, maxInterstRate, minFee, maxFee;
	int maxLoan, maxWithdraw;

	AccountProperties(double minInterstRate, double maxInterstRate, double minFee, double maxFee, int maxLoan,
			int maxWithdraw) {
		this.maxFee = maxFee;
		this.maxInterstRate = maxInterstRate;
		this.maxLoan = maxLoan;
		this.maxWithdraw = maxWithdraw;
		this.minFee = minFee;
		this.minInterstRate = minInterstRate;


	}

	static AccountProperties getAccountType(double income) {
		if (income < 10000)
			return BRONZE;
		else if (income < 20000)
			return SILVER;
		return GOLD;
	}
	
	protected double setInterstRate() {
		Random random = new Random();
		// get a random number between the range of min and max
		double rand = random.doubles(minInterstRate, maxInterstRate).findFirst().getAsDouble();
		// set 2 numbers after the point
		rand = (Math.round(rand * 100.0) / 100.0);
		return rand;
	}

	protected double setFeeOperation() {
		// get a random number between the range of min and max
		Random random = new Random();
		// set 2 numbers after the point
		double rand = random.doubles(minFee, maxFee).findFirst().getAsDouble();
		rand = (Math.round(rand * 100.0) / 100.0);
		return rand;
	}

}
