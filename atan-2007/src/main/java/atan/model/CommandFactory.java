package atan.model;

import java.util.*;

public class CommandFactory {

	private List<String> fifo = new ArrayList<>();

	public CommandFactory() {
	}

	public void addInitCommand(String teamName, boolean isGoaly) {
		StringBuilder buf = new StringBuilder();
		if (isGoaly) {
			buf.append("(init ");
			buf.append(teamName);
			buf.append(" g)");
		} else {
			buf.append("(init ");
			buf.append(teamName);
			buf.append(")");
		}
		fifo.add(fifo.size(), buf.toString());
	}

	public void addReconnectCommand(String teamName, int num) {
		StringBuilder buf = new StringBuilder();
		buf.append("(reconnect ");
		buf.append(teamName);
		buf.append(" ");
		buf.append(num);
		buf.append(")");
		fifo.add(fifo.size(), buf.toString());
	}

	public void addCatchCommand(int direction) {
		StringBuilder buf = new StringBuilder();
		buf.append("(catch ");
		buf.append(direction);
		buf.append(")");
		fifo.add(fifo.size(), buf.toString());
	}

	public void addChangeViewCommand(ViewQuality quality, ViewAngle angle) {
		StringBuilder buf = new StringBuilder();
		buf.append("(change_view ");
		if (angle == ViewAngle.NARROW) {
			if (quality == ViewQuality.HIGH) {
				buf.append("narrow high)");
			} else if (quality == ViewQuality.LOW) {
				buf.append("narrow low)");
			}
		}
		if (angle == ViewAngle.NORMAL) {
			if (quality == ViewQuality.HIGH) {
				buf.append("normal high)");
			} else if (quality == ViewQuality.LOW) {
				buf.append("normal low)");
			}
		}
		if (angle == ViewAngle.WIDE) {
			if (quality == ViewQuality.HIGH) {
				buf.append("wide high)");
			} else if (quality == ViewQuality.LOW) {
				buf.append("wide low)");
			}
		}
		fifo.add(fifo.size(), buf.toString());
	}

	public void addDashCommand(int power) {
		StringBuilder buf = new StringBuilder();
		buf.append("(dash ");
		buf.append(power);
		buf.append(")");
		fifo.add(fifo.size(), buf.toString());
	}

	public void addKickCommand(int power, int direction) {
		StringBuilder buf = new StringBuilder();
		buf.append("(kick ");
		buf.append(power);
		buf.append(" ");
		buf.append(direction);
		buf.append(")");
		fifo.add(fifo.size(), buf.toString());
	}

	public void addMoveCommand(int x, int y) {
		StringBuilder buf = new StringBuilder();
		buf.append("(move ");
		buf.append(x);
		buf.append(" ");
		buf.append(y);
		buf.append(")");
		fifo.add(fifo.size(), buf.toString());
	}

	public void addTurnCommand(int angle) {
		StringBuilder buf = new StringBuilder();
		buf.append("(turn ");
		buf.append(angle);
		buf.append(")");
		fifo.add(fifo.size(), buf.toString());
	}

	public void addTurnNeckCommand(int angle) {
		StringBuilder buf = new StringBuilder();
		buf.append("(turn_neck ");
		buf.append(angle);
		buf.append(")");
		fifo.add(fifo.size(), buf.toString());
	}

	public void addSayCommand(String message) {
		StringBuilder buf = new StringBuilder();
		buf.append("(say ");
		buf.append(message);
		buf.append(")");
		fifo.add(fifo.size(), buf.toString());
	}

	public void addSenseBodyCommand() {
		StringBuilder buf = new StringBuilder();
		buf.append("(sense_body)");
		fifo.add(fifo.size(), buf.toString());
	}

	public void addByeCommand() {
		StringBuilder buf = new StringBuilder();
		buf.append("(bye)");
		fifo.add(fifo.size(), buf.toString());
	}

	public String next() {
		if (fifo.isEmpty())
			throw new RuntimeException("Fifo is empty");
		String cmd = (String) fifo.get(0);
		fifo.remove(0);
		return cmd;
	}

	public boolean hasNext() {
		return !fifo.isEmpty();
	}
}