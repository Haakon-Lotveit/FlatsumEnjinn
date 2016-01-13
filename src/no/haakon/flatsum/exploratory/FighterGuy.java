package no.haakon.flatsum.exploratory;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

public class FighterGuy implements Guy {

	private final BufferedImage gfx;
	private Random dice;
	private int level;
	private int health;
	private int attack;
	private int damage;
	private int defence;
	
	public FighterGuy(BufferedImage gfx) {
		this.gfx = gfx;
		this.level = 0;
		this.dice = new Random();
		levelUp();
	}
	
	private int roll() {
		return dice.nextInt(20) + 1;
	}
	
	@Override
	public void defend(Attack attack) {
		int defenceRoll = roll() + defence;
		if(attack.hit() > defenceRoll) {
			health -= attack.damage();
		}		
	}

	@Override
	public Attack makeAttack() {
		final int attackRoll = roll() + attack;
		final int damage = this.damage;
		
		return new Attack() {
			
			@Override
			public int hit() {
				return attackRoll;
			}
			
			@Override
			public int damage() {
				return damage;
			}
		};
	}

	@Override
	public int level() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int defence() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int attack() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int damage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Image getSprite() {
		return gfx;
	}

	@Override
	public Statistics levelUp() {
		++level;
		health = level * 8;
		attack = level * 7;
		damage = level * 3;
		defence = level * 2;
		
		return this.stats();
	}

	@Override
	public Statistics stats() {
		return new FinalStats(this);
	}
	
	public String toString() {
		return String.format("FighterGuy LVL:%d HP:%d ATK:%d DMG:%d DEF:%d", level, health, attack, damage, defence);
	}
	
	@Override
	public boolean isAlive() {
		return health > -10;
	}

	@Override
	public boolean isConscious() {
		return health() > 0;
	};
	
	@Override
	public int health() {
		return health;
	}
	
	public static class FinalStats implements Statistics {
		private final int level;
		private final int defence;
		private final int health;
		private final int attack;
		private final int damage;

		public FinalStats(FighterGuy guy) {
			this.level = guy.level;
			this.defence = guy.defence;              
			this.health = guy.health;
			this.attack = guy.attack;
			this.damage = guy.damage;
			
		}
		
		@Override
		public int level() {
			return level;
		}

		@Override
		public int defence() {
			return defence;
		}

		@Override
		public int health() {
			return health;
		}

		@Override
		public int attack() {
			return attack;
		}

		@Override
		public int damage() {
			return damage;
		}
		
	}
	public static void main(String[] args) {
		Guy p1 = new FighterGuy(null);
		Guy p2 = new FighterGuy(null);
		boolean p1Turn = true;
		
		while(p1.isConscious() && p2.isConscious()) {
			System.out.printf("P1: %s%nP2: %s%n", p1.stats().debugString(), p2.stats().debugString());						
			if(p1Turn) {
				p2.defend(p1.makeAttack());
			}
			else {
				p1.defend(p2.makeAttack());
			}
			p1Turn = !p1Turn;
		}
		
		if(p1.isConscious()) {
			System.out.println("P1 won");
		}
		else {
			System.out.println("P2 won");
		}
	}
}
