/**
 * $Revision: 1.12 $ $Author: wwan $ $Date: 2005/11/29 18:24:28 $ 
 */

package vsoc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import vsoc.camps.Member;
import vsoc.camps.goalgetter.GGCamp;
import vsoc.camps.goalgetter.GGMembersComparator;
import vsoc.camps.goalkeeper.GKMembersComparator;
import vsoc.server.Server;
import vsoc.util.Serializer;

/**
 * Tests issues concerning camps.
 */
public class CampTest extends TestCase {

    public void testSerialization() {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        GGCamp camp = createTestCamp("camp1");
        camp.takeOneStep();
        camp.takeOneStep();
        camp.takeOneStep();
        Serializer.current().serialize(camp, bout);
        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        GGCamp camp1 = (GGCamp) Serializer.current().deserialize(bin);
        assertEquals("Not the same numer of members.", camp.getMemberCount(),
                camp1.getMemberCount());
        for (int i = 0; i < camp.getMemberCount(); i++) {
            assertEquals("Members are not equal.", camp.getMember(i), camp1
                    .getMember(i));
        }
        Server srv = camp1.getServer();
        assertNotNull(srv);
        assertNotNull(srv.getBall());
        assertEquals(0, srv.getDelay());
        assertEquals(0, srv.getTime());
        assertEquals(51, srv.getSimObjects().size());

    }

    public void testLoadGGCampContext() {
        assertNotNull(new ClassPathXmlApplicationContext(
                "ggcamp.xml"));
    }

    private GGCamp createTestCamp(String id) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "ggcamp.xml");
        GGCamp camp = (GGCamp) ctx.getBean(id);
        return camp;
    }

    private void assertEquals(String msg, Member m1, Member m2) {
        assertEquals("kickOutPerMatch", m1.kickOutPerMatch(), m2
                .kickOutPerMatch(), 0.01);
    }

    public void testMembersComparator() {
        GGCamp camp = createTestCamp("camp1");
        ArrayList<Member> mems = new ArrayList<>();
        mems.add(createTestMember(10));
        mems.add(createTestMember(20));
        mems.add(createTestMember(40));
        mems.add(createTestMember(5));
        GGMembersComparator comp = new GGMembersComparator(
                camp.getGoalFactor(), camp.getOwnGoalFactor(), camp
                        .getKickFactor(), camp.getKickOutFactor(), -100);
        Collections.sort(mems, comp);
        {
            Member mem = (Member) mems.get(0);
            assertEquals(40.0, mem.goalsPerMatch(), 0.001);
        }
        {
            Member mem = (Member) mems.get(1);
            assertEquals(20.0, mem.goalsPerMatch(), 0.001);
        }
        {
            Member mem = (Member) mems.get(2);
            assertEquals(10.0, mem.goalsPerMatch(), 0.001);
        }
        {
            Member mem = (Member) mems.get(3);
            assertEquals(5.0, mem.goalsPerMatch(), 0.001);
        }
    }

    private Member createTestMember(int ownGoalCount) {
        Member mem = new Member();
        mem.reset();
        mem.increaseOtherGoalsCount(ownGoalCount);
        mem.increaseMatchCount();
        return mem;
    }

    public void testMembersGKComparator() {
        ArrayList<Member> mems = new ArrayList<>();
        mems.add(createGKTestMember(5));
        mems.add(createGKTestMember(1));
        mems.add(createGKTestMember(4));
        mems.add(createGKTestMember(2));
        Comparator<Member> comp = new GKMembersComparator();
        Collections.sort(mems, comp);
        {
            Member mem = (Member) mems.get(0);
            assertEquals(1.0, mem.kickPerMatch(), 0.001);
        }
        {
            Member mem = (Member) mems.get(1);
            assertEquals(0.8, mem.kickPerMatch(), 0.001);
        }
        {
            Member mem = (Member) mems.get(2);
            assertEquals(0.4, mem.kickPerMatch(), 0.001);
        }
        {
            Member mem = (Member) mems.get(3);
            assertEquals(0.2, mem.kickPerMatch(), 0.001);
        }
    }

    private Member createGKTestMember(int count) {
        Member mem = new Member();
        mem.reset();
        mem.increaseKickCount(count);
        mem.increaseMatchCount();
        mem.increaseMatchCount();
        mem.increaseMatchCount();
        mem.increaseMatchCount();
        mem.increaseMatchCount();
        return mem;
    }
}
