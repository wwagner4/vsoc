package vsoc.camps;

import java.io.Serializable;
import java.util.Iterator;

import vsoc.behaviour.*;
import vsoc.util.Retina;

public class RetinaSensorsToVector implements SensorsToVector, Serializable {

	private static final long serialVersionUID = 1L;

	public double[] apply(Sensors sensors) {
		Retinas r = sensorsToRetinas(sensors);
		return retinasToVector(r);
	}

	private Retinas sensorsToRetinas(Sensors sens) {
		Retinas re = new Retinas();
		DistDirVision ball = sens.getBall();
		if (ball != null) {
			re.retinaBall.addVision(ball.getDistance(), -ball.getDirection());
		}
		if (sens.sawFlagLeft()) {
			Iterator<DistDirVision> iter = sens.getFlagsLeft().values().iterator();
			while (iter.hasNext()) {
				DistDirVision vis = iter.next();
				re.retinaFlagLeft.addVision(vis.getDistance(), -vis.getDirection());

			}
		}
		if (sens.sawFlagOwn()) {
			Iterator<DistDirVision> iter = sens.getFlagsOwn().values().iterator();
			while (iter.hasNext()) {
				DistDirVision vis = iter.next();
				re.retinaFlagOwn.addVision(vis.getDistance(), -vis.getDirection());

			}
		}
		if (sens.sawFlagPenaltyOther()) {
			Iterator<DistDirVision> iter = sens.getFlagsOwn().values().iterator();
			while (iter.hasNext()) {
				DistDirVision vis = iter.next();
				re.retinaFlagPenaltyOther.addVision(vis.getDistance(), -vis.getDirection());
			}
		}
		if (sens.sawFlagRight()) {
			Iterator<DistDirVision> iter = sens.getFlagsOwn().values().iterator();
			while (iter.hasNext()) {
				DistDirVision vis = iter.next();
				re.retinaFlagRight.addVision(vis.getDistance(), -vis.getDirection());
			}
		}
		if (sens.sawFlagGoalOther()) {
			Iterator<DistDirVision> iter = sens.getFlagsOwn().values().iterator();
			while (iter.hasNext()) {
				DistDirVision vis = iter.next();
				re.retinaGoalOther.addVision(vis.getDistance(), -vis.getDirection());
			}
		}
		if (sens.sawPlayerOther()) {
			Iterator<DistDirVision> iter = sens.getFlagsOwn().values().iterator();
			while (iter.hasNext()) {
				DistDirVision vis = iter.next();
				re.retinaPlayerOther.addVision(vis.getDistance(), -vis.getDirection());

			}
		}
		if (sens.sawPlayerOwn()) {
			Iterator<DistDirVision> iter = sens.getFlagsOwn().values().iterator();
			while (iter.hasNext()) {
				DistDirVision vis = iter.next();
				re.retinaPlayerOwn.addVision(vis.getDistance(), -vis.getDirection());

			}
		}
		return re;
	}

	private double[] retinasToVector(Retinas retinas) {
		double[] in = new double[32];
		in[0] = retinas.retinaFlagLeft.getA();
		in[1] = retinas.retinaFlagLeft.getB();
		in[2] = retinas.retinaFlagLeft.getC();
		in[3] = retinas.retinaFlagLeft.getD();
		in[4] = retinas.retinaFlagRight.getA();
		in[5] = retinas.retinaFlagRight.getB();
		in[6] = retinas.retinaFlagRight.getC();
		in[7] = retinas.retinaFlagRight.getD();
		in[8] = retinas.retinaFlagOwn.getA();
		in[9] = retinas.retinaFlagOwn.getB();
		in[10] = retinas.retinaFlagOwn.getC();
		in[11] = retinas.retinaFlagOwn.getD();
		in[12] = retinas.retinaFlagPenaltyOther.getA();
		in[13] = retinas.retinaFlagPenaltyOther.getB();
		in[14] = retinas.retinaFlagPenaltyOther.getC();
		in[15] = retinas.retinaFlagPenaltyOther.getD();
		in[16] = retinas.retinaGoalOther.getA();
		in[17] = retinas.retinaGoalOther.getB();
		in[18] = retinas.retinaGoalOther.getC();
		in[19] = retinas.retinaGoalOther.getD();
		in[20] = retinas.retinaPlayerOther.getA();
		in[21] = retinas.retinaPlayerOther.getB();
		in[22] = retinas.retinaPlayerOther.getC();
		in[23] = retinas.retinaPlayerOther.getD();
		in[24] = retinas.retinaPlayerOwn.getA();
		in[25] = retinas.retinaPlayerOwn.getB();
		in[26] = retinas.retinaPlayerOwn.getC();
		in[27] = retinas.retinaPlayerOwn.getD();
		in[28] = retinas.retinaBall.getA();
		in[29] = retinas.retinaBall.getB();
		in[30] = retinas.retinaBall.getC();
		in[31] = retinas.retinaBall.getD();
		return in;
	}

	private static class Retinas {

		private Retina retinaFlagLeft = new Retina();

		private Retina retinaFlagRight = new Retina();

		private Retina retinaFlagOwn = new Retina();

		private Retina retinaGoalOther = new Retina();

		private Retina retinaPlayerOwn = new Retina();

		private Retina retinaPlayerOther = new Retina();

		private Retina retinaFlagPenaltyOther = new Retina();

		private Retina retinaBall = new Retina();

	}

}
