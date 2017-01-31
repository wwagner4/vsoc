package vsoc.test;

import vsoc.genetic.CrossoverSwitch;
import vsoc.genetic.Mutator;
import junit.framework.*;
//import bsoc.util.*;

/**
* Testcases for the package bsoc.genetic
*/
public class GeneticTest extends TestCase {

        GeneticTest (String s) {
                super (s);
        }

        public static Test suite () {
                TestSuite s = new TestSuite ();
                s.addTest (new GeneticTest ("testMutatorMR0"));
                s.addTest (new GeneticTest ("testMutatorMR1"));
                s.addTest (new GeneticTest ("testMutatorMR05"));
                s.addTest (new GeneticTest ("testMutatorMR09"));
                s.addTest (new GeneticTest ("testMutatorMR09"));
                s.addTest (new GeneticTest ("testCrossoverSwitchA"));
                s.addTest (new GeneticTest ("testCrossoverSwitchB"));
                return s;
        }

        protected void setUp () {
            // nothing to do
        }

	public void testCrossoverSwitchA () {
		CrossoverSwitch cs = new CrossoverSwitch (40, 10);
		int len, maxLen, minLen;
		boolean prev, act;

		prev = cs.takeA();
		maxLen = 0;
		minLen = 10000;
		len = 0;
		for (int i=0; i<10000; i++) {
			act = cs.takeA();
			if (act != prev) {
				if (len > maxLen) maxLen = len;
				if (len < minLen) minLen = len;
				len = 0;
			}
			len++;
			prev = act;
		}
		assertTrue (maxLen >= 50);
		assertTrue (minLen <= 30);
        }

	public void testCrossoverSwitchB () {
		CrossoverSwitch cs = new CrossoverSwitch (100, 50);
		int len, maxLen, minLen;
		boolean prev, act;

		prev = cs.takeA();
		maxLen = 0;
		minLen = 10000;
		len = 0;
		for (int i=0; i<100000; i++) {
			act = cs.takeA();
			if (act != prev) {
				if (len > maxLen) maxLen = len;
				if (len < minLen) minLen = len;
				len = 0;
			}
			len++;
			prev = act;
		}
		assertTrue (maxLen >= 150);
		assertTrue (minLen <= 50);
        }

	public void testMutatorMR0 () throws Exception {
		Mutator m = new Mutator (0);

		for (int i=0; i<100000; i++) {
			assertTrue (m.isMutation() == false);
		}
        }

	public void testMutatorMR1 ()  throws Exception {
		Mutator m = new Mutator (1000000);

		for (int i=0; i<100000; i++) {
			assertTrue (m.isMutation() == true);
		}
        }

	public void testMutatorMR05 ()  throws Exception {
		Mutator m = new Mutator (500000);
		int trueCount = 0;
		int falseCount = 0;

		for (int i=0; i<100000; i++) {
			if (m.isMutation()) trueCount++;
			else falseCount++;
		}
		assertTrue ((trueCount > 50000 - 500) && (trueCount < 50000 + 500));
		assertTrue ((falseCount > 50000 - 500) && (falseCount < 50000 + 500));
        }
	public void testMutatorMR01 ()  throws Exception {
		Mutator m = new Mutator (100000);
		int trueCount = 0;
		int falseCount = 0;

		for (int i=0; i<100000; i++) {
			if (m.isMutation()) trueCount++;
			else falseCount++;
		}
		assertTrue ((trueCount > 10000 - 500) && (trueCount < 10000 + 500));
		assertTrue ((falseCount > 90000 - 500) && (falseCount < 90000 + 500));
        }
	public void testMutatorMR09 ()  throws Exception {
		Mutator m = new Mutator (900000);
		int trueCount = 0;
		int falseCount = 0;

		for (int i=0; i<100000; i++) {
			if (m.isMutation()) trueCount++;
			else falseCount++;
		}
		assertTrue ((trueCount > 90000 - 500) && (trueCount < 90000 + 500));
		assertTrue ((falseCount > 10000 - 500) && (falseCount < 10000 + 500));
        }

}
