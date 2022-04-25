package accounts;

import java.time.LocalDateTime;

public class ActivityData {
	protected ActivityName activityName;
	protected double balanceChange;
	protected LocalDateTime timeStamp;
	protected String info;

	public ActivityData(ActivityName activityName, double balanceChange, LocalDateTime timeStamp) {
		this.activityName = activityName;
		this.balanceChange = balanceChange;
		this.timeStamp = timeStamp;
	}

	public ActivityName getActivityName() {
		return activityName;
	}

	public double getBalanceChange() {
		return balanceChange;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

}
