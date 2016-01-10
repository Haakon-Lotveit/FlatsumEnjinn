package no.haakon.flatsum.exploratory;

public interface Statistics {
	public int level();
	
	public int defence();
	public int health();
	
	public int attack();
	public int damage();
	
	public default String debugString() {
	   return String.format("Level: %d%nDefence: %d%nHealth: %d%nAttack: %d%nDamage: %d%n",
			  level(), defence(), health(), attack(), damage());
	}
	
}
