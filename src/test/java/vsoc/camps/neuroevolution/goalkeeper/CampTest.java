/**
 * $Revision: 1.12 $ $Author: wwan $ $Date: 2005/11/29 18:24:28 $ 
 */

package vsoc.camps.neuroevolution.goalkeeper;

import java.io.*;
import java.util.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;
import vsoc.camps.*;
import vsoc.camps.neuroevolution.VectorFunctionBehaviourController;
import vsoc.camps.neuroevolution.goalgetter.*;
import vsoc.genetic.*;
import vsoc.nn.base.*;
import vsoc.nn.feedforward.*;
import vsoc.server.Server;
import vsoc.util.Serializer;

/**
 * Tests issues concerning camps.
 */
public class CampTest extends TestCase {

	AbstractFFNetConnector nc = new TestNetConnector();

	Weighter wgt = new Weighter((float) 0.1, (float) 1.5);

	TransferManager trans = new TransferManager();

	LayerNode ln1, ln2, ln3;

	Neuron neu1, neu2, neu3;

	NeuronLayer nl1, nl2, nl3;

	FFNet n1, n2, n3, n4;

	RandomValue rv;

	RandomWgt rw;

	public void testSerialization() {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		GGCamp camp = createTestCamp("camp1");
		camp.takeOneStep();
		camp.takeOneStep();
		camp.takeOneStep();
		Serializer.current().serialize(camp, bout);
		ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
		GGCamp camp1 = (GGCamp) Serializer.current().deserialize(bin);
		assertEquals("Not the same numer of members.", camp.getMemberCount(), camp1.getMemberCount());
		for (int i = 0; i < camp.getMemberCount(); i++) {
			assertEquals("Members are not equal.", camp.getMember(i), camp1.getMember(i));
		}
		Server srv = camp1.getServer();
		assertNotNull(srv);
		assertNotNull(srv.getBall());
		assertEquals(0, srv.getDelay());
		assertEquals(0, srv.getTime());
		assertEquals(51, srv.getSimObjects().size());

	}

	public void testLoadGGCampContext() {
		assertNotNull(new ClassPathXmlApplicationContext("ggcamp.xml"));
	}

	private GGCamp createTestCamp(String id) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("ggcamp.xml");
		GGCamp camp = (GGCamp) ctx.getBean(id);
		return camp;
	}

	private void assertEquals(String msg, Member<VectorFunctionBehaviourController<VectorFunction>> m1,
			Member<VectorFunctionBehaviourController<VectorFunction>> m2) {
		assertEquals("kickOutPerMatch", m1.kickOutPerMatch(), m2.kickOutPerMatch(), 0.01);
	}

	public void testMembersComparator() {
		GGCamp camp = createTestCamp("camp1");
		ArrayList<Member<VectorFunctionBehaviourController<VectorFunction>>> mems = new ArrayList<>();
		mems.add(createTestMember(10));
		mems.add(createTestMember(20));
		mems.add(createTestMember(40));
		mems.add(createTestMember(5));
		GGMembersComparator comp = new GGMembersComparator(camp.getGoalFactor(), camp.getOwnGoalFactor(),
				camp.getKickFactor(), camp.getKickOutFactor(), -100);
		Collections.sort(mems, comp);
		{
			Member<VectorFunctionBehaviourController<VectorFunction>> mem = mems.get(0);
			assertEquals(40.0, mem.goalsPerMatch(), 0.001);
		}
		{
			Member<VectorFunctionBehaviourController<VectorFunction>> mem = mems.get(1);
			assertEquals(20.0, mem.goalsPerMatch(), 0.001);
		}
		{
			Member<VectorFunctionBehaviourController<VectorFunction>> mem = mems.get(2);
			assertEquals(10.0, mem.goalsPerMatch(), 0.001);
		}
		{
			Member<VectorFunctionBehaviourController<VectorFunction>> mem = mems.get(3);
			assertEquals(5.0, mem.goalsPerMatch(), 0.001);
		}
	}

	private Member<VectorFunctionBehaviourController<VectorFunction>> createTestMember(int ownGoalCount) {
		Member<VectorFunctionBehaviourController<VectorFunction>> mem = new Member<>();
		mem.reset();
		mem.increaseOtherGoalsCount(ownGoalCount);
		mem.increaseMatchCount();
		return mem;
	}

	public void testMembersGKComparator() {
		ArrayList<Member<VectorFunctionBehaviourController<VectorFunction>>> mems = new ArrayList<>();
		mems.add(createGKTestMember(5));
		mems.add(createGKTestMember(1));
		mems.add(createGKTestMember(4));
		mems.add(createGKTestMember(2));
		Comparator<Member<?>> comp = new GKMembersComparator();
		Collections.sort(mems, comp);
		{
			Member<VectorFunctionBehaviourController<VectorFunction>> mem = mems.get(0);
			assertEquals(1.0, mem.kickPerMatch(), 0.001);
		}
		{
			Member<VectorFunctionBehaviourController<VectorFunction>> mem = mems.get(1);
			assertEquals(0.8, mem.kickPerMatch(), 0.001);
		}
		{
			Member<VectorFunctionBehaviourController<VectorFunction>> mem = mems.get(2);
			assertEquals(0.4, mem.kickPerMatch(), 0.001);
		}
		{
			Member<VectorFunctionBehaviourController<VectorFunction>> mem = mems.get(3);
			assertEquals(0.2, mem.kickPerMatch(), 0.001);
		}
	}

	public void testCrossoverA() throws Exception {
		Iterator<Synapse> enum1, enum2, enum3;
		Synapse s1, s2, s3;
		int from1Count, from2Count, fromNoneCount;
		this.n1 = createTestNet();
		CrossoverSwitch cos = new DefaultCrossoverSwitch(5, 3);
		FFNetCrosser crosser = new FFNetCrosser();
		crosser.setCrossoverSwitch(cos);
		this.n2 = createTestNet();
		Random ran = new Random();
		this.n1.setParametersRandom(ran.nextLong());
		this.n2.setParametersRandom(ran.nextLong());
		this.n3 = crosser.newChild(n1, n2, 0.3);
		enum1 = this.n1.synapses();
		enum2 = this.n2.synapses();
		enum3 = this.n3.synapses();
		from1Count = 0;
		from2Count = 0;
		fromNoneCount = 0;
		while (enum1.hasNext()) {
			s1 = (Synapse) enum1.next();
			s2 = (Synapse) enum2.next();
			s3 = (Synapse) enum3.next();
			if (s1.getWeight() == s3.getWeight())
				from1Count++;
			else if (s2.getWeight() == s3.getWeight())
				from2Count++;
			else
				fromNoneCount++;
		}
		assertTrue(from1Count > 0);
		assertTrue(from2Count > 0);
		assertTrue(fromNoneCount > 0);
	}

	private Member<VectorFunctionBehaviourController<VectorFunction>> createGKTestMember(int count) {
		Member<VectorFunctionBehaviourController<VectorFunction>> mem = new Member<>();
		mem.reset();
		mem.increaseKickCount(count);
		mem.increaseMatchCount();
		mem.increaseMatchCount();
		mem.increaseMatchCount();
		mem.increaseMatchCount();
		mem.increaseMatchCount();
		return mem;
	}

	private FFNet createTestNet() {
		FFNet n = new FFNet();
		nc.initLayers(n);
		nc.connectNet(n);
		return n;
	}

}
