package controller;

import java.util.LinkedList;
import java.util.List;

import model.Level;
import model.Point;
import model.Target;
import model.User;
import view.menue.Highscore;

public class GameController {

	private AlienDefenceController alienDefenceController;
	private Level currentLevel;
	private User currentUser;
	private boolean hasWon;
	private HitCounter hitCounter;
	private boolean setHighscore = true;
	private long timer = 0L, starttimer = 0L, endtimer = 0L;
	private List<Target> targets;

	public GameController(Level selectedLevel, User user, AlienDefenceController alienDefenceController) {

		this.currentLevel = selectedLevel;
		this.currentUser = user;
		this.hitCounter = new HitCounter(currentLevel.getTargets().size());
		this.targets = currentLevel.getTargets();
		this.hasWon = false;
		this.alienDefenceController = alienDefenceController;
	}

	public void doLogik(long delta) {
		timer += delta;

		// time for next logic step

		// section to run once a second
		if (timer >= 1000) {
			timer -= 100;

			if (this.isAllTargetDestroyed()) {
				this.hasWon = true;
			}

			if ((this.timeleft() <= 0 || this.isHasWon()) && this.setHighscore) {

				setHighscore = false; //damit keine doppelten Eintrüge erzeugt werden

				// TODO Highscoreeintrag erzeugen Highscore >> F_user_id, F_level_id, shots,
				// hits, reaction_time
				System.out.println("Highscoreeintrag für " + currentLevel.getName() + ": " + currentUser.getFirst_name()
						+ " hat " + this.getShotsFired() + " mal auf " + this.getTargets().size()
						+ " Ziele gefeuert und " + this.getHits() + " mal getroffen.");
				System.out.println("Highscorepunkte: " + alienDefenceController.getAttemptController().calculatePoints(this.currentLevel, this.hitCounter));
				int insert_id = 0;
				// AttemptDB attemptDB =
				// this.alienDefenceController.getAlienDefenceModel().getAttemptPersistance();
				// int insert_id = attemptDB.createHighscoreEntry(currentUser.getP_user_id(),
				// currentLevel.getLevel_id(),
				// this.getShotsFired(), this.getHits(), relReactionDiffernce);

				new Highscore(this.alienDefenceController.getAttemptController(), currentLevel,
						insert_id);

			}
		}
		// steps that use only delta
	}

	// fire
	public void fireShot(int x, int y) {
		boolean isHit = false;

		// iterates through all targets and checks if a target is hit
		for (Target t : targets) {
			if (t.getHitbox().contains(new Point(x, y)) && !t.isHit()) {

				long differnce = time() - t.getTime();
				this.hitCounter.addReactionTime(differnce);

				if (differnce > 0 && (t.getTime() + t.getDuration()) > time()) {
					isHit = true;
					t.setHit(true);
				}
			}
		}

		// hit
		if (isHit) {
			this.hitCounter.hit();
		} else
			this.hitCounter.miss();

	}

	public double getAccuracy() {
		return hitCounter.getAccuracy();
	}

	public Level getCurrLevel() {
		return currentLevel;
	}

	public int getHits() {
		return this.hitCounter.getHit();
	}

	public int getShotsFired() {
		return this.hitCounter.getShots();
	}

	public List<Target> getTargets() {
		return (this.targets != null) ? this.targets : (new LinkedList<Target>());
	}

	private boolean isAllTargetDestroyed() {
		for (Target t : this.targets) {
			if (!t.isHit())

				return false;
		}
		return true;
	}

	public boolean isHasWon() {
		return this.hasWon;
	}

	public void setCurrLevel(Level currLevel) {
		this.currentLevel = currLevel;
	}

	public void setHasWon(boolean hasWon) {
		this.hasWon = hasWon;
	}

	public void startLevel() {
		this.starttimer = System.currentTimeMillis();
		this.endtimer = this.starttimer + currentLevel.getDuration();
	}

	public long time() {
		return System.currentTimeMillis() - this.starttimer;
	}

	public long timeleft() {
		return (starttimer == 0) ? this.currentLevel.getDuration()
				: (this.endtimer - System.currentTimeMillis()) / 1000;
	}

}
