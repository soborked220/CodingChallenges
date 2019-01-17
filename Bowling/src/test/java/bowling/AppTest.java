package bowling;

import org.junit.Test;

import bowling.frame.Frame;

import static org.junit.Assert.assertEquals;

public class AppTest {

    BowlingGame game = new BowlingGame();

    @Test
    public void bowlZeros(){
        for(int i = 0; i<20; i++){
            game.roll(0);
        }
        assertEquals(0, game.score());
    }

    @Test
    public void bowlOnes(){
        for(int i = 0; i<20; i++){
            game.roll(1);
        }
        assertEquals(20, game.score());
    }

    @Test
    public void bowlFours(){
        for(int i = 0; i<20; i++){
            game.roll(4);
        }
        assertEquals(80, game.score());
    }

    @Test
    public void bowlOneSpare(){
        game.roll(5);
        game.roll(5);
        game.roll(3);
        for(int i = 0; i<17; i++){
            game.roll(0);
        }
        assertEquals(16, game.score());
    }

    @Test
    public void bowlAllSpares(){

        for(int i = 0; i<10; i++){
            game.roll(5);
            game.roll(5);
        }
        game.roll(5);
        assertEquals(150, game.score());
    }

    @Test
    public void whenStrike_SecondRollIsNull(){

        game.roll(10);
        for(int i = 0; i<9; i++){
            game.roll(0);
            game.roll(0);
        }
        Frame[] frames = game.getFrames();
        assertEquals(null, frames[0].getSecondRoll());
    }

    @Test
    public void whenStrike_NextFrameIsAdded(){

        game.roll(10);
        game.roll(2);
        game.roll(2);
        for(int i = 0; i<8; i++){
            game.roll(0);
            game.roll(0);
        }
        assertEquals(18, game.score());
    }

    // https://www.bowlinggenius.com/

    @Test
    public void whenTwoStrikesAndTwoPins_FirstFrame_AccountsForNextNextRoll(){

        game.roll(10);
        game.roll(10);
        game.roll(2);
        game.roll(0);
        for(int i = 0; i<7; i++){
            game.roll(0);
            game.roll(0);
        }
        assertEquals(36, game.score());
    }

	@Test
	public void whenThreeStrikes_FirstFrame_AccountsForSecondStrike(){

		game.roll(10);
		game.roll(10);
		game.roll(10);
		for(int i = 0; i<7; i++){
			game.roll(0);
			game.roll(0);
		}
		assertEquals(60, game.score());
	}

	@Test
	public void whenBowlOnes_FirstFrameScore_IsTwo(){

		game.roll(1);
		game.roll(1);

		Frame[] frames = game.getFrames();
		assertEquals(new Integer(2), frames[0].getPoints());
	}

	@Test
	public void whenThreeStrikes_FirstFrame_IsThirty(){

		game.roll(10);
		game.roll(10);
		game.roll(10);

		Frame[] frames = game.getFrames();
		assertEquals(new Integer(30), frames[0].getPoints());
	}

	@Test
	public void whenTwoStrikesAndTwoPins_FirstFrame_IsTwentyTwo(){

		game.roll(10);
		game.roll(10);
		game.roll(2);
		game.roll(0);

		Frame[] frames = game.getFrames();
		assertEquals(new Integer(22), frames[0].getPoints());
	}

	@Test
	public void whenBowlFirstFrame_TotalScore_IsTwo(){

		game.roll(1);
		game.roll(1);

		assertEquals(2, game.score());
	}

	@Test
	public void whenAllStrikes_ThreeHundred(){
		for(int i = 0; i<12; i++) {
			game.roll(10);
		}
		assertEquals(300, game.score());
	}

	@Test
	public void whenAllStrikes_NinthFrame_IsThirty(){
		for(int i = 0; i<12; i++) {
			game.roll(10);
		}
		Frame[] frames = game.getFrames();
		assertEquals(new Integer(30), frames[8].getPoints());
	}

	@Test
	public void whenAllZeros_TenthFrameIsOnes_IsTwo(){
		for(int i = 0; i<9; i++) {
			game.roll(0);
			game.roll(0);
		}
		game.roll(1);
		game.roll(1);
		assertEquals(2, game.score());
	}

	@Test
	public void whenAllZeros_TenthFrameIsSpareAndFive_IsFifteen(){
		for(int i = 0; i<9; i++) {
			game.roll(0);
			game.roll(0);
		}
		game.roll(5);
		game.roll(5);
		game.roll(5);
		assertEquals(15, game.score());
	}

	@Test
	public void whenAllZeros_TenthFrameIsStrikes_IsThirty(){
		for(int i = 0; i<9; i++) {
			game.roll(0);
			game.roll(0);
		}
		game.roll(10);
		game.roll(10);
		game.roll(10);
		assertEquals(30, game.score());
	}


	@Test
	public void whenAllZeros_TenthFrameIsStrikeAndSpare_IsTwenty(){
		for(int i = 0; i<9; i++) {
			game.roll(0);
			game.roll(0);
		}
		game.roll(10);
		game.roll(5);
		game.roll(5);
		assertEquals(20, game.score());
	}

	@Test
	public void whenAllZeros_NinthFrameIsStrike_TenthFrameIsStrikes_IsSixty(){
		for(int i = 0; i<8; i++) {
			game.roll(0);
			game.roll(0);
		}
		//Ninth
		game.roll(10);
		//Tenth
		game.roll(10);
		game.roll(10);
		game.roll(10);
		assertEquals(60, game.score());
	}

	//Test tenth frame logic.
	//all zeros except 10th frame.
	//normal bowl
	//strike
	//spare
}
