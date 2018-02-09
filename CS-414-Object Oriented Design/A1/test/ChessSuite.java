package A1.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

@RunWith(Suite.class)

@Suite.SuiteClasses({
   BishopTest.class,
   ChessBoardTest.class,
   KingTest.class,
   KnightTest.class,
   PawnTest.class,
   QueenTest.class,
   RookTest.class
})

public class ChessSuite {   
	public static void main(String[] args) {
      Result result = JUnitCore.runClasses(ChessSuite.class);

      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
		
      System.out.println(result.wasSuccessful());
    }
}