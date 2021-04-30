package model;

import java.time.LocalDateTime;
import java.util.Vector;

public class Attempt {

	// Attribute
	private User user;
	private Level level;
	private int placement;
	private double hitsShots;
	private double hitsTargets;
	private double reactionDuration;
	private double highscorePoints;
	private LocalDateTime datetime;

	// Konstruktor
	public Attempt(int place, Level level, double hitsShots, double hitsTargets, double reactionDuration,
			double highscoreFormula, User user, LocalDateTime datetime) {
		super();
		this.placement = place;
		this.level = level;
		this.reactionDuration = reactionDuration;
		this.highscorePoints = highscoreFormula;
		this.user = user;
		this.hitsShots = hitsShots;
		this.hitsTargets = hitsTargets;
		this.datetime = datetime;	
	}

	public Vector<String> getRowVector() {
		Vector<String> v = new Vector<String>(8);
		v.addElement(String.valueOf(this.placement));
		v.addElement(this.user.getFirst_name() + " " + this.user.getSur_name());
		v.addElement(this.datetime.getDayOfMonth() +"."+this.datetime.getMonthValue() + "." + this.datetime.getYear());;
		v.addElement(this.datetime.getHour() + ":" + this.datetime.getMinute());
		v.addElement(String.valueOf((int) this.hitsTargets));
		v.addElement(String.valueOf((int) this.hitsShots));
		v.addElement(String.valueOf((int) this.reactionDuration));
		v.addElement(String.valueOf((int) this.highscorePoints));
		return v;
	}

	public Level getLevel() {
		return this.level;
	}
	
}
