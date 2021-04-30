package controller;

import model.Level;
import model.persistence.IPersistance;
import model.User;

public class AlienDefenceController {
	
	//Teilcontroller
	private GameController gameController;
	private LevelController levelController;
	private TargetController targetController;
	private AttemptController attemptController;
	//TODO UserController implementieren
	
	//Persistenz
	private IPersistance alienDefenceModel;

	public AlienDefenceController(IPersistance alienDefenceModel) {
		super();
		this.alienDefenceModel = alienDefenceModel;
		this.attemptController = new AttemptController(alienDefenceModel);
		this.levelController = new LevelController(alienDefenceModel);
		this.targetController = new TargetController(alienDefenceModel);
	}

	public IPersistance getAlienDefenceModel() {
		return alienDefenceModel;
	}

	public AttemptController getAttemptController() {
		return attemptController;
	}

	public LevelController getLevelController() {
		return levelController;
	}

	public TargetController getTargetController() {
		return targetController;
	}

	public GameController startGame(Level selectedLevel, User user) {
		this.gameController = new GameController(selectedLevel, user, this);
		return this.gameController;
	}

	
}
