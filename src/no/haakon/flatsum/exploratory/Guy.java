package no.haakon.flatsum.exploratory;


public interface Guy extends GraphicEntity2D, Statistics {
	public Statistics stats();
	public void defend(Attack attack);
	public Attack makeAttack();	
	public Statistics levelUp();
	public boolean isAlive();
	public boolean isConscious();
}
