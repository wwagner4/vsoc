package vsoc.camps.neuroevolution;

import org.apache.log4j.Logger;

import atan.model.Player;
import vsoc.behaviour.*;
import vsoc.camps.VectorFunction;

public class VectorFunctionBehaviour<V extends VectorFunction> implements Behaviour {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(VectorFunctionBehaviour.class);

	private V vectorFunction = null;
	
	private SensorsToVector sensorsToVector;
	
	public VectorFunctionBehaviour(V vectorFunction, SensorsToVector sensorsToVector) {
		super();
		this.vectorFunction = vectorFunction;
		this.sensorsToVector = sensorsToVector;
	}

	public boolean shouldBeApplied(Sensors sens) {
		return true;
	}

	public void apply(Sensors sens, Player player) {
		if (log.isDebugEnabled()) {
			debugSensors(sens);
		}
		double[] in = this.sensorsToVector.apply(sens);
		double[] out = this.vectorFunction.apply(in);
		addCommandsFromOutputLayer(out, sens, player);
	}

	private void debugSensors(Sensors sens) {
		log.debug("Sensors: --- BEGIN ---");
		log.debug("sens.ball:" + sens.getBall());
		log.debug("sens.bodyState:" + sens.getBodyState());
		log.debug("sens.flagsCenter:" + sens.getFlagsCenter());
		log.debug("sens.flagsCornerOther:" + sens.getFlagsCornerOther());
		log.debug("sens.flagsCornerOwn:" + sens.getFlagsCornerOwn());
		log.debug("sens.flagsGoalOther:" + sens.getFlagsGoalOther());
		log.debug("sens.flagsGoalOwn:" + sens.getFlagsGoalOwn());
		log.debug("sens.flagsLeft:" + sens.getFlagsLeft());
		log.debug("sens.flagsOther:" + sens.getFlagsOther());
		log.debug("sens.flagsOwn:" + sens.getFlagsOwn());
		log.debug("sens.flagsPenaltyOther:" + sens.getFlagsPenaltyOther());
		log.debug("sens.flagsPenaltyOwn:" + sens.getFlagsPenaltyOwn());
		log.debug("sens.flagsRight:" + sens.getFlagsRight());
		log.debug("sens.lines:" + sens.getLines());
		log.debug("sens.messages:" + sens.getMessages());
		log.debug("sens.playersOther:" + sens.getPlayersOther());
		log.debug("sens.playersOwn:" + sens.getPlayersOwn());
		log.debug("sens.playMode:" + sens.getPlayMode());
		log.debug("sens.refereeMessage:" + sens.getRefereeMessage());
		log.debug("Sensors: --- END ---");
	}

	public Behaviour getChild() {
		return null;
	}

	private void addCommandsFromOutputLayer(double[] out, Sensors sens, Player player) {
		addTurnCommandFromOutputLayer(out, player);
		player.dash(powerValueFromNeuroActivation(out[8]));
		if ((sawBall(sens)) && (distBall(sens) < 0.7)) {
			addKickCommandFromOutputLayer(out, player);
		}
	}

	private boolean sawBall(Sensors sens) {
		return sens.getBall() != null;
	}

	private double distBall(Sensors sens) {
		return sens.getBall().getDistance();
	}

	private void addKickCommandFromOutputLayer(double[] out, Player player) {
		double maxVal = 0.0;
		int maxIndex = 9;
		for (int i = 9; i < 16; i++) {
			double val = out[i];
			if (val > maxVal) {
				maxIndex = i;
				maxVal = val;
			}
		}
		int power = powerValueFromNeuroActivation(out[17]);
		switch (maxIndex) {
		case 9:
			player.kick(power, 60);
			break;
		case 10:
			player.kick(power, 40);
			break;
		case 11:
			player.kick(power, 20);
			break;
		case 12:
		case 13:
			player.kick(power, 0);
			break;
		case 14:
			player.kick(power, -20);
			break;
		case 15:
			player.kick(power, -40);
			break;
		case 16:
			player.kick(power, -60);
			break;
		default:
			// Nothing to do
		}
	}

	private int powerValueFromNeuroActivation(double val) {
		int index = (int) (val / 5.0);
		switch (index) {
		case 0:
			return -100;
		case 1:
			return -58;
		case 2:
			return -25;
		case 3:
			return 1;
		case 4:
			return 22;
		case 5:
			return 38;
		case 6:
			return 51;
		case 7:
			return 62;
		case 8:
			return 70;
		case 9:
			return 77;
		case 10:
			return 82;
		case 11:
			return 86;
		case 12:
			return 89;
		case 13:
			return 92;
		case 14:
			return 94;
		case 15:
			return 96;
		case 16:
			return 97;
		case 17:
			return 98;
		case 18:
		case 19:
			return 99;
		default:
			return 100;
		}
	}

	private void addTurnCommandFromOutputLayer(double[] out, Player player) {
		double maxVal = 0.0;
		int maxIndex = 0;
		for (int i = 0; i < 8; i++) {
			double val = out[i];
			if (val > maxVal) {
				maxIndex = i;
				maxVal = val;
			}
		}
		switch (maxIndex) {
		case 0:
			player.turn(50);
			break;
		case 1:
			player.turn(30);
			break;
		case 2:
			player.turn(10);
			break;
		case 3:
		case 4:
			player.turn(0);
			break;
		case 5:
			player.turn(-10);
			break;
		case 6:
			player.turn(-30);
			break;
		case 7:
			player.turn(-50);
			break;
		default:
			// Nothing to do
		}
	}

	public V getVectorFunction() {
		return this.vectorFunction;
	}

	public void setVectorFunction(V vectorFunction) {
		this.vectorFunction = vectorFunction;
	}
}
