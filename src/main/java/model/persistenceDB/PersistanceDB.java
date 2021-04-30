package model.persistenceDB;

import model.persistence.IAttemptPersistance;
import model.persistence.ILevelPersistance;
import model.persistence.IPersistance;
import model.persistence.ITargetPersistance;
import model.persistence.IUserPersistance;

public class PersistanceDB implements IPersistance{

	private LevelDB levelDB;
	private UserDB userDB;
	private AttemptDB attemptDB;
	private TargetDB targetDB;

	public PersistanceDB() {
		AccessDB dbAccess = new AccessDB();
		this.levelDB = new LevelDB(dbAccess);
		this.userDB = new UserDB(dbAccess);
		this.attemptDB = new AttemptDB(dbAccess);
		this.targetDB = new TargetDB(dbAccess);
	}

	@Override
	public IAttemptPersistance getAttemptPersistance() {
		return this.attemptDB;
	}

	@Override
	public ILevelPersistance getLevelPersistance() {
		return this.levelDB;
	}

	@Override
	public ITargetPersistance getTargetPersistance() {
		return this.targetDB;
	}

	@Override
	public IUserPersistance getUserPersistance() {
		return this.userDB;
	}
}
