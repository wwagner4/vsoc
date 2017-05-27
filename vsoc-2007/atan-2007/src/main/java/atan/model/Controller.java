package atan.model;

/**
 * Interface that has to be implemented in order to control players. The methods
 * are run in a cycle whenever a see command arrives from sserver. At first
 * preInfo() is invoked. then the info*() methods are called according to what
 * kind of objects are currently seen or what other commands where received from
 * the server. At last postInfo() is called. All objects are relative to the
 * current side of the controller.
 *
 */

public interface Controller {

	void preInfo();

	void postInfo();

	Player getPlayer();

	void setPlayer(Player c);

	void infoSeeFlagRight(Flag flag, double distance, double direction);

	void infoSeeFlagLeft(Flag flag, double distance, double direction);

	void infoSeeFlagOwn(Flag flag, double distance, double direction);

	void infoSeeFlagOther(Flag flag, double distance, double direction);

	void infoSeeFlagCenter(Flag flag, double distance, double direction);

	void infoSeeFlagCornerOwn(Flag flag, double distance,
			double direction);

	void infoSeeFlagCornerOther(Flag flag, double distance,
			double direction);

	void infoSeeFlagPenaltyOwn(Flag flag, double distance,
			double direction);

	void infoSeeFlagPenaltyOther(Flag flag, double distance,
			double direction);

	void infoSeeFlagGoalOwn(Flag flag, double distance, double direction);

	void infoSeeFlagGoalOther(Flag flag, double distance,
			double direction);

	void infoSeeLine(Line line, double distance, double direction);

	void infoSeePlayerOther(int number, double distance, double direction);

	void infoSeePlayerOwn(int number, double distance, double direction);

	void infoSeeBall(double distance, double direction);

	void infoHearReferee(RefereeMessage refereeMessage);

	void infoHearPlayMode(PlayMode playMode);

	void infoHear(double direction, String message);

	void infoSenseBody(ViewQuality viewQuality, ViewAngle viewAngle, double stamina,
			double speed, double headAngle, int kickCount, int dashCount,
			int turnCount, int sayCount, int turnNeckCount);
}
